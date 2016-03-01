package co.com.codesoftware.logic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.ExistenciaXSedeTable;
import co.com.codesoftware.persistence.entites.tables.PrecioProductoEntity;
import co.com.codesoftware.persistence.entites.tables.ProductoTable;
import co.com.codesoftware.persistence.entites.tables.PromPonderaTable;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.productos.ProductoEntity;
import co.com.codesoftware.persistence.enumeration.DataType;
import co.com.codesoftware.utilities.ReadFunction;
import java.math.BigDecimal;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class ProductsLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     * Funcion con la cual se obtiene los productos parametrizados en el sistema
     * basandose en su sede
     *
     * @param sede_sede
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ProductoTable> buscaProductos(Integer sede_sede) {
        List<ProductoTable> productos = null;
        List<ProductoTable> productosRta = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(ProductoTable.class);
            productos = crit.setFirstResult(0).setMaxResults(200).list();
            for (ProductoTable producto : productos) {
                Query query1 = sesion.createQuery("from PrecioProductoTable WHERE estado = :estado and idSede = :idSede and idProducto = :idProducto  ");
                query1.setParameter("estado", "A");
                query1.setParameter("idSede", sede_sede);
                query1.setParameter("idProducto", producto.getId());
                List precio = query1.list();
                if (precio != null && precio.size() > 0) {
                    producto.setPrecios(precio);
                    if (productosRta == null) {
                        productosRta = new ArrayList<>();
                    }
                    productosRta.add(producto);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productosRta;

    }

    /**
     * Funcion con la cual busco los productos por medio de un criterio
     *
     * @param sede_sede
     * @param criterio
     * @return
     */
    public List<ProductoTable> buscaProductosXCriterio(Integer sede_sede, String criterio) {
        List<ProductoTable> productos = null;
        List<ProductoTable> productosRta = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(ProductoTable.class);
            crit.add(Restrictions.like("descripcion", criterio, MatchMode.ANYWHERE).ignoreCase());
            productos = crit.setFirstResult(0).setMaxResults(200).list();
            if (productos != null) {
                for (ProductoTable producto : productos) {
                    Query query1 = sesion.createQuery("from PrecioProductoTable WHERE estado = :estado and idSede = :idSede and idProducto = :idProducto  ");
                    query1.setParameter("estado", "A");
                    query1.setParameter("idSede", sede_sede);
                    query1.setParameter("idProducto", producto.getId());
                    List precio = query1.list();
                    if (precio != null && precio.size() > 0) {
                        producto.setPrecios(precio);
                        if (productosRta == null) {
                            productosRta = new ArrayList<>();
                        }
                        productosRta.add(producto);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productosRta;

    }

    @SuppressWarnings("unchecked")
    public ProductoTable findProductForCode(String codigo, Integer sede) {
        ProductoTable product = null;
        try {
            initOperation();
            Query query = sesion.createQuery("from ProductoTable WHERE codigo = :code");
            query.setParameter("code", codigo);
            product = (ProductoTable) query.uniqueResult();
            if (product != null) {
                Query query1 = sesion.createQuery("from PrecioProductoTable WHERE estado = :estado and idSede = :idSede and idProducto = :idProducto  ");
                query1.setParameter("estado", "A");
                query1.setParameter("idSede", sede);
                query1.setParameter("idProducto", product.getId());
                List precios = query1.list();
                if (precios != null & precios.size() > 0) {
                    product.setPrecios(precios);
                } else {
                    product = null;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public String obtieneExistenciasXSede(String dska_dska, String sede_sede) {
        List<String> response = new ArrayList<String>();
        try (ReadFunction rf = new ReadFunction()) {
            rf.setNombreFuncion("IN_OBTIENEEXIS_PROD_SEDE");
            rf.setNumParam(2);
            rf.addParametro(sede_sede, DataType.INT);
            rf.addParametro(dska_dska, DataType.INT);
            rf.callFunctionJdbc();
            response = rf.getRespuestaPg();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.get(0);
    }

    private void initOperation() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    /**
     * Metodo que consulta los productos por una referencia
     *
     * @param referencia
     * @return
     */
    public List<PrecioProductoEntity> consultaProductosXReferencia(Integer referencia) {
        List<PrecioProductoEntity> lista = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(PrecioProductoEntity.class);
            crit.createAlias("idProducto", "prod").add(Restrictions.eq("prod.referencia.id", referencia));

            lista = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * metodo que actualiza un producto por base de datos
     *
     * @param producto
     * @return
     */
    public RespuestaEntity actualizaProducto(ProductoEntity producto) {
        RespuestaEntity respuesta = new RespuestaEntity();
        try {
            initOperation();
            sesion.update(producto);
            respuesta.setCodigoRespuesta(1);
            respuesta.setDescripcionRespuesta("OK");
            respuesta.setMensajeRespuesta("OK");

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setCodigoRespuesta(0);
            respuesta.setDescripcionRespuesta(e.getMessage());
            respuesta.setMensajeRespuesta(e.toString());
        }
        return respuesta;
    }

    /**
     * Metodo que consulta toda la informacion del producto
     *
     * @param sedeId
     * @return
     */
    public List<PrecioProductoEntity> consultaProductosXSede(Integer sedeId) {
        List<PrecioProductoEntity> lista = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(PrecioProductoEntity.class).
                    createAlias("idSede", "sed").add(Restrictions.eq("sed.id", sedeId)).add(Restrictions.eq("estado", "A"));
            lista = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Metodo que consulta toda la informacion del producto
     *
     * @param sedeId
     * @return
     */
    public List<PrecioProductoEntity> consultaProductosXSedeB(Integer sedeId) {
        List<PrecioProductoEntity> lista = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(PrecioProductoEntity.class).
                    createAlias("idSede", "sed").createAlias("idProducto", "prod").
                    setFetchMode("idProducto", FetchMode.JOIN).
                    setFetchMode("idSede", FetchMode.JOIN).
                    setFetchMode("idProducto.categoria", FetchMode.JOIN).
                    setFetchMode("idProducto.referencia", FetchMode.JOIN).
                    setFetchMode("idProducto.subcuenta", FetchMode.JOIN).
                    setFetchMode("idProducto.marca", FetchMode.JOIN).
                    add(Restrictions.eq("sed.id", sedeId)).
                    add(Restrictions.eq("estado", "A"));

            lista = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * metodo que consulta el producto dependiendo del codigo
     *
     * @param codigo
     * @return
     */
    public PrecioProductoEntity consultaProductoXCodExterno(String codigo) {
        PrecioProductoEntity producto = new PrecioProductoEntity();
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(PrecioProductoEntity.class).createAlias("idProducto", "pr")
                    .add(Restrictions.eq("pr.codigoExt", codigo)).add(Restrictions.eq("estado", "A"));
            crit.setFetchMode("idProducto", FetchMode.JOIN).setFetchMode("idSede", FetchMode.JOIN);
            crit.setFetchMode("idProducto.categoria", FetchMode.JOIN).
                    setFetchMode("idProducto.referencia", FetchMode.JOIN).
                    setFetchMode("idProducto.subcuenta", FetchMode.JOIN).
                    setFetchMode("idProducto.marca", FetchMode.JOIN);
            producto = (PrecioProductoEntity) crit.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return producto;
    }

    /**
     * Funcion con la cual se busca el promedio ponderado y las existencias
     * totales de un producto
     *
     * @param idDska
     * @return
     */
    public PromPonderaTable buscaPromedioPondProd(Integer idDska) {
        PromPonderaTable rta = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(PromPonderaTable.class).add(Restrictions.eq("idDska", idDska));
            rta = (PromPonderaTable) crit.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

    /**
     * Funcion con la cual se buscan las existencias por sede de un producto
     *
     * @param idDska
     * @return
     */
    public List<ExistenciaXSedeTable> buscoExistenciaProd(Integer idDska) {
        List<ExistenciaXSedeTable> rta = null;
        try {
            initOperation();
            Query query = sesion.createQuery("select ex from ExistenciaXSedeTable ex inner join fetch ex.sede WHERE ex.idDska = :idDska");
            query.setParameter("idDska", idDska);
            rta = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

    /**
     * Funcion con la cual valido si el precio que se le dara cumple con los
     * parametros de codigo de barras
     *
     * @param idProducto
     * @param precio
     * @return
     */
    public String consultaPromPonderado(Integer idProducto, BigDecimal precio) {
        List<String> rta = new ArrayList<>();
        try (ReadFunction rf = new ReadFunction()) {
            rf.setNombreFuncion("FX_VALIDA_PROMEDIO_PONDERADO");
            rf.setNumParam(2);
            rf.addParametro("" + idProducto, DataType.INT);
            rf.addParametro("" + precio, DataType.BIGDECIMAL);
            rf.callFunctionJdbc();
            rta = rf.getRespuestaPg();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta.get(0);
    }

    /**
     * Funcion con la cual busco un producto por su codigo de barras
     *
     * @return
     */
    public List<PrecioProductoEntity> buscaProductoXCodBarras(String codBarras, Integer idSede) {
        List<PrecioProductoEntity> rta = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(PrecioProductoEntity.class);
            crit.createAlias("idProducto", "prod").createAlias("idSede", "sede");
            crit.add(Restrictions.eq("prod.codigoBarras", codBarras));
            crit.add(Restrictions.eq("estado", "A")).add(Restrictions.eq("sede.id", idSede));
            crit.setFetchMode("idProducto", FetchMode.JOIN).
                    setFetchMode("idSede", FetchMode.JOIN).
                    setFetchMode("idProducto.categoria", FetchMode.JOIN).
                    setFetchMode("idProducto.referencia", FetchMode.JOIN).
                    setFetchMode("idProducto.subcuenta", FetchMode.JOIN).
                    setFetchMode("idProducto.marca", FetchMode.JOIN);
            rta = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

    @Override
    public void close() throws Exception {
        tx.commit();
        sesion.close();
    }

}
