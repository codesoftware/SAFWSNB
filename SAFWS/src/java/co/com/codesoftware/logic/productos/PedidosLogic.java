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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

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
            Integer IdPedidos = selectMaxPedido();
            entidad.setId(IdPedidos);
            sesion.save(entidad);
            respuesta.setCodigoRespuesta(IdPedidos);
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
     * @param productos
     * @param productoId
     * @return 
     */
    public RespuestaEntity insertaProductos(ArrayList<PedidoProductoEntity> productos,Integer productoId) {
        RespuestaEntity respuesta = new RespuestaEntity();
        try (PedidosProductoLogic logic = new PedidosProductoLogic();) {
            respuesta = logic.insertaProductoPedido(productos,productoId);
        } catch (Exception e) {
            tx.rollback();
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