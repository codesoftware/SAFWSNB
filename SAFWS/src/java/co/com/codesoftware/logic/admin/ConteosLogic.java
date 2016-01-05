/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic.admin;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entity.administracion.ConteoEntity;
import co.com.codesoftware.persistence.entity.administracion.ProductoConteoEntity;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.productos.ProductoEntity;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author nicolas
 */
public class ConteosLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     *
     * @param codigoConteo
     * @param codigoProducto
     * @param cantidad
     * @param codigoBarras
     * @return
     */
    public RespuestaEntity insertaProdConteo(Integer codigoConteo, String codigoProducto, Integer cantidad, String codigoBarras) {
        RespuestaEntity rta = null;
        try {
            initOperation();
            if (codigoBarras != null && !"".equals(codigoBarras)) {
                rta = consultaExistenciaCodigoBarras(codigoProducto, codigoBarras, codigoConteo, cantidad);
            } else {
                rta = consultaExistenciaProductoCodExt(codigoProducto, codigoConteo, cantidad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

    public RespuestaEntity consultaExistenciaCodigoBarras(String codigoProd, String codigoBarras, Integer codigoConteo, Integer cantidad) {
        RespuestaEntity res = null;
        try {
            ProductoEntity result = (ProductoEntity) sesion.createCriteria(ProductoEntity.class)
                    .add(Restrictions.eq("codigoBarras", codigoBarras))
                    .uniqueResult();
            //si el producto existe ya con codigo de barras
            if (result != null) {
                //Se verifica si el producto existe en el conteo
                ProductoConteoEntity prdConteo = consultaExistenciaProducto(codigoBarras, codigoConteo, cantidad);
            } else {
                if (actualizaCodigoBarras(codigoBarras, codigoProd)) {
                    ProductoConteoEntity prdConteo = consultaExistenciaProducto(codigoBarras, codigoConteo, cantidad);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean actualizaCodigoBarras(String codigoBarras, String codigoExterno) {
        try {

            ProductoEntity produc = (ProductoEntity) sesion.createCriteria(ProductoEntity.class).
                    add(Restrictions.eq("codigoExt", codigoExterno)).uniqueResult();
            produc.setCodigoBarras(codigoBarras);
            sesion.update(produc);
            //tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param codigo
     * @return
     */
    public ProductoConteoEntity consultaExistenciaProducto(String codigoBarras, Integer codigoConteo, Integer cantidad) {
        ProductoConteoEntity respuesta = new ProductoConteoEntity();
        try {
            ProductoConteoEntity prod = (ProductoConteoEntity) sesion.createCriteria(ProductoConteoEntity.class).
                    createAlias("conteo", "con").createAlias("producto", "prd")
                    .add(Restrictions.eq("con.id", codigoConteo)).add(Restrictions.eq("prd.codigoBarras", codigoBarras))
                    .uniqueResult();
            if (prod !=null) {
                prod.setCantidad(prod.getCantidad() + cantidad);
                sesion.update(prod);
                //tx.commit();
            } else {
                ProductoEntity producto = (ProductoEntity) sesion.createCriteria(ProductoEntity.class).add(Restrictions.eq("codigoBarras", codigoBarras)).uniqueResult();
                ConteoEntity conteo = (ConteoEntity) sesion.createCriteria(ConteoEntity.class).add(Restrictions.eq("id", codigoConteo)).uniqueResult();
                prod = new ProductoConteoEntity();
                prod.setCantidad(cantidad);
                prod.setId(selectMaxProductoConteo());
                prod.setProducto(producto);
                prod.setConteo(conteo);
                sesion.save(prod);
                //tx.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public RespuestaEntity consultaExistenciaProductoCodExt(String codigoExt, Integer codigoConteo, Integer cantidad) {
        RespuestaEntity respuesta = new RespuestaEntity();
        try {
            ProductoConteoEntity prod = (ProductoConteoEntity) sesion.createCriteria(ProductoConteoEntity.class).
                    createAlias("conteo", "con").createAlias("producto", "prd")
                    .add(Restrictions.eq("con.id", codigoConteo)).add(Restrictions.eq("prd.codigoExt", codigoExt))
                    .uniqueResult();
            if (prod!=null) {
                prod.setCantidad(prod.getCantidad() + cantidad);
                sesion.update(prod);
                //tx.commit();
            } else {
                ProductoEntity producto = (ProductoEntity) sesion.createCriteria(ProductoEntity.class).add(Restrictions.eq("codigoExt", codigoExt)).uniqueResult();
                ConteoEntity conteo = (ConteoEntity) sesion.createCriteria(ConteoEntity.class).add(Restrictions.eq("id", codigoConteo)).uniqueResult();
                prod = new ProductoConteoEntity();
                prod.setCantidad(cantidad);
                prod.setId(selectMaxProductoConteo());
                prod.setProducto(producto);
                prod.setConteo(conteo);
                sesion.save(prod);
                //tx.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public String validaEstadoConteo() {
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

    public Integer selectMaxProductoConteo() {
        Integer resultado = 1;
        try {
            Criteria crit = sesion.createCriteria(ProductoConteoEntity.class)
                    .setProjection(Projections.max("id"));
            resultado = (Integer) crit.uniqueResult() + 1;
        } catch (Exception e) {
            resultado = 1;
            e.printStackTrace();
        }
        return resultado;
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
