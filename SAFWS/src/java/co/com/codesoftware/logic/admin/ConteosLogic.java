/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic.admin;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author nicolas
 */
public class ConteosLogic implements AutoCloseable{
    private Session sesion;
    private Transaction tx;
    
    /**
     * Funcion con la cual inserto un producto en el conteo
     * @param copr
     * @param dska
     * @return 
     */
    public RespuestaEntity insertaProdConteo(Integer copr, Integer dska, Integer cantidad){
        RespuestaEntity rta = null;
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }
    /**
     * Funcion la cual busca el estado de un conteo
     * @return 
     */
    public String validaEstadoConteo(){
        String estado = "";
        try {
            this.initOperation();
            Query query = sesion.createQuery("select c.estado from ConteoEntity c where ");
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return estado;
    }
    private void initOperation() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

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
