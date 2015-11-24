/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic.productos;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
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
public class PedidosProductoLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     * metodo que inserta los productos del pedido
     * @param idPedido
     * @param Id
     * @param listaProductos
     * @return 
     */
    public RespuestaEntity insertaProductoPedido(ArrayList<PedidoProductoEntity> listaProductos,Integer Id){
        RespuestaEntity respuesta = new RespuestaEntity();
        try {
            this.initOperation();
            for(PedidoProductoEntity item: listaProductos ){
                item.setId(selectMaxPedidoProdcuto());
                item.setPedido(Id);
                sesion.save(item);
                respuesta.setCodigoRespuesta(1);
                respuesta.setDescripcionRespuesta("INSERTO CORRECTAMENTE LOS PRODUCTOS");
                respuesta.setMensajeRespuesta("OK");
            }
        } catch (Exception e) {
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
