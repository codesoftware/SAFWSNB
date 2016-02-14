/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic.productos;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.productos.PedidoEntity;
import co.com.codesoftware.persistence.entity.productos.PedidoProductoEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.GenericJDBCException;
import org.postgresql.util.PSQLException;

/**
 *
 * @author root
 */
public class PedidosLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     * Metodo que inserta el pedido
     *
     * @param entidad
     * @return
     */
    public RespuestaEntity insertaPedidos(PedidoEntity entidad) {
        RespuestaEntity respuesta = new RespuestaEntity();
        try {
            this.initOperation();
            entidad.setId(selectMaxPedido());
            sesion.save(entidad);
            respuesta.setCodigoRespuesta(entidad.getId());
            respuesta.setDescripcionRespuesta("OK");
            respuesta.setMensajeRespuesta("OK");
        } catch (Exception e) {
            respuesta.setCodigoRespuesta(0);
            respuesta.setDescripcionRespuesta(e.toString());
            respuesta.setMensajeRespuesta("ERROR");
            e.printStackTrace();
        }
        return respuesta;

    }

    /**
     * metodo en el cual se inserta productos a partir de un pedido
     *
     * @param productos
     * @param productoId
     * @return
     */
    public RespuestaEntity insertaProductos(ArrayList<PedidoProductoEntity> productos, Integer productoId) {
        RespuestaEntity respuesta = new RespuestaEntity();
        try (PedidosProductoLogic logic = new PedidosProductoLogic();) {
            respuesta = logic.insertaProductoPedido(productos, productoId);
        } 
                catch (GenericJDBCException e) {
                    e.printStackTrace();
                    System.out.println("erro"+e.getCause().getMessage());
                
        }
        catch (Exception e) {
            tx.rollback();
            System.out.println(""+e.getClass());
            respuesta.setCodigoRespuesta(0);
            respuesta.setDescripcionRespuesta(e.getMessage());
            respuesta.setMensajeRespuesta("ERROR");
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * metodo que consulta el id de la tabla
     *
     * @return
     */
    public Integer selectMaxPedido() {
        Integer resultado = 1;
        try {
            Criteria crit = sesion.createCriteria(PedidoEntity.class)
                    .setProjection(Projections.max("id"));
            resultado = (Integer) crit.uniqueResult() + 1;
        } catch (Exception e) {
            resultado = 1;
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * metodo que consulta una lista de pedidos por id
     *
     * @param estado
     * @return
     */
    public List<PedidoEntity> consultaPedidoXEstado(String estado) {
        List<PedidoEntity> respuesta = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(PedidoEntity.class).
                    add(Restrictions.eq("estado", estado)).addOrder(Order.desc("id"));
            respuesta = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Consulta de pedidos por los filtros de fecha, usuario y estado
     *
     * @param estado
     * @param idUsuario
     * @param fInicial
     * @param fFinal
     * @return
     */
    public List<PedidoEntity> consultaPedidoXFiltros(String estado, long idUsuario, Date fInicial, Date fFinal) {
        List<PedidoEntity> respuesta = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(PedidoEntity.class)
                    .createAlias("usuario", "us")
                    .add(Restrictions.ge("fecha", fInicial))
                    .add(Restrictions.lt("fecha", fFinal))
                    //.add(Restrictions.between("fecha", fInicial, fFinal))
                    .add(Restrictions.eq("us.id", idUsuario));
            if(!"".equalsIgnoreCase(estado)){
                crit.add(Restrictions.eq("estado", estado));
            }
            respuesta = crit.list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * Funcion que elimina un pedido de la base de datos
     *
     * @param idPedido
     * @return
     */
    public boolean eliminaPedidoId(Integer idPedido) {
        try {
            if (eliminaProductoPedido(idPedido)) {
                initOperation();
                Criteria crit = sesion.createCriteria(PedidoEntity.class).
                        add(Restrictions.eq("id", idPedido));
                PedidoEntity respuesta = (PedidoEntity) crit.uniqueResult();
                sesion.delete(respuesta);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * metodo que elimina los productos de un pedido
     * @param idPedido
     * @return 
     */
    public boolean eliminaProductoPedido(Integer idPedido) {
        try {

            initOperation();
            Criteria crit = sesion.createCriteria(PedidoProductoEntity.class).
                    add(Restrictions.eq("pedido", idPedido));
            List<PedidoProductoEntity> respuesta = crit.list();
            if (respuesta != null) {
                for (PedidoProductoEntity item : respuesta) {
                    sesion.delete(item);
                }
                tx.commit();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Funcion con la cual se actualiza el estado de un pedido 
     * @param estado
     * @return 
     */
    public boolean  actualizaEstadoPedido(Integer id, String estado){
        boolean rta = false;
        try {
            initOperation();
            PedidoEntity pedido = (PedidoEntity) sesion.get(PedidoEntity.class, id);
            pedido.setEstado(estado.toUpperCase());
            sesion.update(pedido);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            rta = false;
        }
        return rta;
    }

    /**
     * Funcion que inicializa la clase de hibernate
     *
     * @throws HibernateException
     */
    private void initOperation() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    /**
     * Funcion para cerrar la sesion
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        if (tx != null) {
            tx.commit();
        }
        if (sesion != null) {
            sesion.close();
        }
    }

}
