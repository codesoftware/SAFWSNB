/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic.productos;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entity.administracion.CantidadesEntity;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author root
 */
public class CantidadesLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     * metodo que consulta las cantidades por producto y sede
     *
     * @param sede
     * @param idProducto
     * @return
     */
    public CantidadesEntity consultaCantidad(Integer sede, Integer idProducto) {
        CantidadesEntity resultado = new CantidadesEntity();
        initOperation();
        try {
            Criteria crit = sesion.createCriteria(CantidadesEntity.class)
                    .add(Restrictions.eq("producto", idProducto)).createAlias("sede","sd").add(Restrictions.eq("sd.id", sede));
            resultado = (CantidadesEntity) crit.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Consulta cantidades en todas las sedes por producto
     * @param idProducto
     * @return 
     */
    public List<CantidadesEntity> consultaCantidades(Integer idProducto) {
        List<CantidadesEntity> resultado = null;
        initOperation();
        try {
            Criteria crit = sesion.createCriteria(CantidadesEntity.class)
                    .add(Restrictions.eq("producto", idProducto));
            resultado = crit.list();
        } catch (Exception e) {
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
