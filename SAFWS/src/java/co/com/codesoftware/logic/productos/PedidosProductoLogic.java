/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic.productos;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.ProductoGenEntity;
import co.com.codesoftware.persistence.entites.RespuestaPedidoEntity;
import co.com.codesoftware.persistence.entites.tables.Cliente;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.productos.PedidoEntity;
import co.com.codesoftware.persistence.entity.productos.PedidoProductoEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.GenericJDBCException;

/**
 *
 * @author root
 */
public class PedidosProductoLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;
    private String msn;

    /**
     * metodo que inserta los productos del pedido
     *
     * @param Id
     * @param listaProductos
     * @return
     */
    public RespuestaEntity insertaProductoPedido(ArrayList<PedidoProductoEntity> listaProductos, Integer Id) {
        RespuestaEntity respuesta = new RespuestaEntity();
        try {
             this.initOperation();
            for (PedidoProductoEntity item : listaProductos) {
                item.setId(selectMaxPedidoProdcuto());
                item.setPedido(Id);
                try {
                sesion.save(item);
                } catch (GenericJDBCException e) {
                    e.printStackTrace();
                    System.out.println("erro"+e.getCause().getMessage());
                }

                respuesta.setCodigoRespuesta(Id);
                respuesta.setDescripcionRespuesta("INSERTO CORRECTAMENTE LOS PRODUCTOS");
                respuesta.setMensajeRespuesta("OK");

            }
        } catch (Exception e) {
            respuesta.setCodigoRespuesta(0);
            respuesta.setDescripcionRespuesta(e.toString());
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
    public Integer selectMaxPedidoProdcuto() {
        Integer resultado = 1;
        try {
            Criteria crit = sesion.createCriteria(PedidoProductoEntity.class)
                    .setProjection(Projections.max("id"));
            resultado = (Integer) crit.uniqueResult() + 1;
        } catch (Exception e) {
            resultado = 1;
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Metodo que consulta los pedidos y los productos de los pedidos, se mapean
     * en el objeto necesario
     *
     * @param idPedido
     * @return
     */
    public RespuestaPedidoEntity consultaProductosXPedido(Integer idPedido) {
        RespuestaPedidoEntity respuesta = new RespuestaPedidoEntity();
        List<ProductoGenEntity> productos = new ArrayList<ProductoGenEntity>();
        List<PedidoProductoEntity> listaProductos = new ArrayList<PedidoProductoEntity>();
        Cliente cliente = new Cliente();
        RespuestaEntity res = new RespuestaEntity();
        try {
            initOperation();
            PedidoEntity pedido = new PedidoEntity();
            pedido = consultaPedidoFacturado(idPedido);
            if (!"FA".equalsIgnoreCase(pedido.getEstado())) {
                Criteria crit = sesion.createCriteria(PedidoProductoEntity.class)
                        .add(Restrictions.eq("pedido", idPedido));
                listaProductos = crit.list();
                cliente = consultaCliente(pedido.getCliente().getId().intValue());
                productos = mapeoGenericoProducto(listaProductos);
                respuesta.setCliente(cliente);
                respuesta.setListaProductos(productos);
                res.setCodigoRespuesta(1);
                res.setDescripcionRespuesta("OK");
                res.setMensajeRespuesta("OK");
                respuesta.setRespuesta(res);
            } else {
                res.setCodigoRespuesta(0);
                res.setDescripcionRespuesta("EL PEDIDO YA FUE FACTURADO");
                res.setMensajeRespuesta("ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    /**
     * metodo que consulta el pedido
     *
     * @param pedido
     * @return
     */
    public PedidoEntity consultaPedidoFacturado(Integer pedido) {
        PedidoEntity resultado = new PedidoEntity();
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(PedidoEntity.class)
                    .add(Restrictions.eq("id", pedido));
            resultado = (PedidoEntity) crit.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Funcion que consulta el objeto cliente
     *
     * @param idCliente
     * @return
     */
    public Cliente consultaCliente(Integer idCliente) {
        Cliente repuesta = new Cliente();
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(Cliente.class).add(Restrictions.eq("id", idCliente.longValue()));
            repuesta = (Cliente) crit.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repuesta;
    }

    /**
     * funcion que mapea la list de la consulta al generico
     *
     * @param listaProductos
     * @return
     */
    public List<ProductoGenEntity> mapeoGenericoProducto(List<PedidoProductoEntity> listaProductos) {
        List<ProductoGenEntity> respuesta = new ArrayList<ProductoGenEntity>();
        try {
            for (PedidoProductoEntity item : listaProductos) {
                ProductoGenEntity entidad = new ProductoGenEntity();
                entidad.setAmount(item.getCantidad());
                entidad.setCode(item.getCodigo());
                entidad.setName(item.getNombre());
                entidad.setPrice(item.getPrecio());
                entidad.setTotalPrice(item.getPrecio().multiply(new BigDecimal(item.getCantidad())));
                entidad.setType(1);
                entidad.setId(item.getProducto());
                respuesta.add(entidad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
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
    public void close() {
        try {
            if (tx != null) {
                tx.commit();
                tx = null;
            }
            if (sesion != null) {
                sesion.close();
                sesion = null;
            }
        } catch (GenericJDBCException e) {
                    e.printStackTrace();
                    System.out.println("erro"+e.getCause().getMessage());
                
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }
    
}
