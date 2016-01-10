package co.com.codesoftware.logic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.PrecioProductoEntity;
import co.com.codesoftware.persistence.entites.tables.ProductoTable;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.productos.ProductoEntity;
import co.com.codesoftware.persistence.enumeration.DataType;
import co.com.codesoftware.utilities.ReadFunction;
import org.hibernate.Criteria;
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
            productos = sesion.createQuery("from ProductoTable").list();
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
            producto = (PrecioProductoEntity) crit.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return producto;
    }

    @Override
    public void close() throws Exception {
        tx.commit();
        sesion.close();
    }

}
