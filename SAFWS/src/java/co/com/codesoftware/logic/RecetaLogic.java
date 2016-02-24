package co.com.codesoftware.logic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.PrecioRecetaTable;
import co.com.codesoftware.persistence.entites.tables.RecetaTable;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class RecetaLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     * Funcion encargada de realizar la logica para la obtencion de recetas del
     * sistema que tengan el precio parametrizado
     *
     * @param param
     * @param sede
     * @return
     * @throws HibernateException
     */
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
                if (precios != null & precios.size() > 0) {
                    receta.setPrecios(precios);
                    if (rta == null) {
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

    /**
     * Funcion encargada de realizar la logica para la obtencion de recetas del
     * sistema que tengan el precio parametrizado y ademas filtrado por un
     * criterio
     *
     * @param param
     * @param sede
     * @param criterio
     * @return
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<RecetaTable> getRecetasXCriterio(List<String> param, Integer sede, String criterio) throws HibernateException {
        List<RecetaTable> recetas = null;
        List<RecetaTable> rta = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(RecetaTable.class);
            crit.add(Restrictions.like("descripcion", criterio, MatchMode.ANYWHERE).ignoreCase());
            recetas = crit.list();
            //recetas = sesion.createQuery("from RecetaTable").list();
            if (recetas != null) {
                for (RecetaTable receta : recetas) {
                    String auxQuery = "from PrecioRecetaTable precio  WHERE precio.estado = 'A' and precio.idReceta = " + receta.getId() + " and precio.idSede = " + sede;
                    List<PrecioRecetaTable> precios = sesion.createQuery(auxQuery).list();
                    if (precios != null & precios.size() > 0) {
                        receta.setPrecios(precios);
                        if (rta == null) {
                            rta = new ArrayList<>();
                        }
                        rta.add(receta);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return rta;
    }

    /**
     * Funcion encargada de buscar una receta por medio de su codigo
     *
     * @param rece_codigo
     * @param sede_sede
     * @return
     */
    public RecetaTable getRecetaForcode(String rece_codigo, Integer sede_sede) {
        RecetaTable receta = null;
        try {
            initOperation();
            Query query = sesion.createQuery("from RecetaTable where codigo = :codigo ");
            query.setParameter("codigo", rece_codigo);
            receta = (RecetaTable) query.uniqueResult();
            if (receta != null) {
                Query query2 = sesion.createQuery("from PrecioRecetaTable precio  WHERE precio.estado = 'A' and precio.idReceta = :idReceta and precio.idSede = :idSede ");
                query2.setParameter("idReceta", receta.getId());
                query2.setParameter("idSede", sede_sede);
                List precios = query2.list();
                if (precios != null & precios.size() > 0) {
                    receta.setPrecios(precios);
                } else {
                    receta = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receta;
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
