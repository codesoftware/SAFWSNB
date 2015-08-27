package co.com.codesoftware.logic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.PrecioRecetaTable;
import co.com.codesoftware.persistence.entites.tables.RecetaTable;

public class RecetaLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    @SuppressWarnings("unchecked")
    public List<RecetaTable> getRecetas(List<String> param, Integer sede) throws HibernateException {
        List<RecetaTable> recetas = null;
        List<RecetaTable> rta = null;
        try {
            initOperation();
            recetas = sesion.createQuery("from RecetaTable").list();
            for (RecetaTable receta : recetas) {                
                String auxQuery = "from PrecioRecetaTable precio  WHERE precio.estado = 'A' and precio.idReceta = " + receta.getId() + " and precio.idSede = " + sede;
                List<PrecioRecetaTable> precios = sesion.createQuery(auxQuery).list();
                if(precios != null & precios.size()>0){
                    receta.setPrecios(precios);
                    if(rta == null){
                        rta = new ArrayList<>();
                    }
                    rta.add(receta);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return rta;
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
