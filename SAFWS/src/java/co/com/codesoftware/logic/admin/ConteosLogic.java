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
import co.com.codesoftware.persistence.enumeration.DataType;
import co.com.codesoftware.utilities.ReadFunction;
import java.util.ArrayList;
import java.util.List;
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
    private String llamadoFunction;
    private String idFactura;
    private String mensaje;

    
    public RespuestaEntity insProdConteo(Integer codigoConteo, String codigoProducto, Integer cantidad, String codigoBarras,String ubicacion){
        RespuestaEntity res = new RespuestaEntity();
        try {
            String descripcion = this.llamaFuncionConteo(codigoConteo, codigoProducto, cantidad, codigoBarras, ubicacion);
            res.setCodigoRespuesta(0);
            res.setDescripcionRespuesta(descripcion);
            res.setMensajeRespuesta(descripcion);
        } catch (Exception e) {
            e.printStackTrace();
            res.setCodigoRespuesta(0);
            res.setDescripcionRespuesta(e.getMessage());
            res.setMensajeRespuesta(e.toString());
            
        }
        return res;
    }
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
            if (prod != null) {
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
            if (prod != null) {
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

    /**
     * metodo el cual consulta un grupo de conteos por estado
     *
     * @param estado
     * @return
     */
    public List<ConteoEntity> consultaConteosEstado(String estado) {
        initOperation();
        if (estado == null || "".equalsIgnoreCase(estado)) {
            estado = "A";
        }
        List<ConteoEntity> respuesta = null;
        try {
            respuesta = sesion.createCriteria(ConteoEntity.class)
                    .add(Restrictions.eq("estado", estado)).list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }
    /**
     * Funcion que llama el procedimiento almacenado pra el conteo
     * @param codigoConteo
     * @param codigoProducto
     * @param cantidad
     * @param codigoBarras
     * @param ubicacion
     * @return 
     */
    public String llamaFuncionConteo(Integer codigoConteo, String codigoProducto, Integer cantidad, String codigoBarras,String ubicacion) {
        String rta = "";
        List<String> response = new ArrayList<>();
        try (ReadFunction rf = new ReadFunction()) {
            rf.setNombreFuncion("IN_VALIDACONTEO");
            rf.setNumParam(6);
            rf.addParametro("" + codigoProducto, DataType.TEXT);
            rf.addParametro("" + cantidad, DataType.INT);
            rf.addParametro("" + codigoBarras, DataType.TEXT);
            rf.addParametro("" + ubicacion, DataType.TEXT);
            rf.addParametro("0", DataType.TEXT);
            rf.addParametro(""+codigoConteo, DataType.INT);
            rf.callFunctionJdbc();
            response = rf.getRespuestaPg();
            String respuesta = response.get(0);
            if (respuesta.indexOf("Error") >= 0) {
                respuesta = respuesta.replaceAll("Error", "");
                rta = respuesta;
                llamadoFunction = "error";
            } else {             
                llamadoFunction = respuesta;
                rta = respuesta;
            }
        } catch (Exception e) {
            llamadoFunction = "error";
            rta = e.toString();
            e.printStackTrace();
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
