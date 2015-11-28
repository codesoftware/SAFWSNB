/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic.productos;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.ProductoGenericoEntity;
import co.com.codesoftware.persistence.entites.RespuestaPedidoEntity;
import co.com.codesoftware.persistence.entites.tables.Cliente;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.productos.PedidoEntity;
import co.com.codesoftware.persistence.entity.productos.PedidoProductoEntity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author root
 */
public class PedidosProductoLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     * metodo que inserta los productos del pedido
     *
     * @param idPedido
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
                sesion.save(item);
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
     * Metodo que consulta los pedidos y los productos de los pedidos, se mapean en el objeto necesario
     * @param idPedido
     * @return 
     */
    public RespuestaPedidoEntity consultaProductosXPedido(Integer idPedido) {
        RespuestaPedidoEntity respuesta = new RespuestaPedidoEntity();
        List<ProductoGenericoEntity> productos = new ArrayList<ProductoGenericoEntity>();
        List<PedidoProductoEntity> listaProductos = new ArrayList<PedidoProductoEntity>();
        Cliente cliente = new Cliente();
        RespuestaEntity res = new RespuestaEntity();
        try {
            initOperation();
            PedidoEntity pedido = new PedidoEntity();
            pedido = consultaPedidoFacturado(idPedido);
            if (!"FA".equalsIgnoreCase(pedido.getEstado())) {

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
    
    public List<ProductoGenericoEntity> mapeoGenericoProducto(List<Pro){
        List<ProductoGenericoEntity> respuesta = new ArrayList<ProductoGenericoEntity>();
        try {
            
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
    public void close() throws Exception {
        if (tx != null) {
            tx.commit();
        }
        if (sesion != null) {
            sesion.close();
        }
    }
}
