/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entity.administracion.ProductosHomeEntity;
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
public class ProductosHomeLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     * Funcion que consulta las referencia por la categoria seleccionada
     *
     * @param categoriaId
     * @return
     */
    public List<ProductosHomeEntity> consultaReferenciaHome(Integer categoriaId) {
        List<ProductosHomeEntity> referencias = null;
        try {
            this.initOperation();
            Criteria crit = sesion.createCriteria(ProductosHomeEntity.class);
            crit.add(Restrictions.eq("categoria", categoriaId));
            referencias = crit.list();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return referencias;
    }

    /**
     * Funcion que incializa las transacciones de hibernate
     *
     * @throws HibernateException
     */
    private void initOperation() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    /**
     * metodo para cerrar la sesion de hibernate
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        tx.commit();
        sesion.close();
    }
}
