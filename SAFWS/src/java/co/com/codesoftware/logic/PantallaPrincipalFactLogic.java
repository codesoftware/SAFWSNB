package co.com.codesoftware.logic;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.PantallaPrincipalFacTable;
import co.com.codesoftware.persistence.entites.tables.PrecioProductoTable;
import co.com.codesoftware.persistence.entites.tables.PrecioRecetaTable;
import co.com.codesoftware.persistence.entites.tables.ProductoTable;
import co.com.codesoftware.persistence.entites.tables.RecetaTable;
import co.com.codesoftware.utilities.FileManagement;
import java.util.ArrayList;
import java.util.Iterator;

public class PantallaPrincipalFactLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     * Funcion la cual obtiene los productos que se deben visualizar en la
     * pantalla principal de facturacion los cuales son parametrizados
     * previamente
     *
     * @param sede_sede
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PantallaPrincipalFacTable> obtieneProductosPantalla(Integer sede_sede) {
        List<PantallaPrincipalFacTable> productos = null;
        List<PantallaPrincipalFacTable> productosRta = null;
        try {
            initOperation();
            Query query = sesion
                    .createQuery("from PantallaPrincipalFacTable WHERE tipo = :tipo order by posicion");
            query.setParameter("tipo", "P");
            productos = query.list();
            for (PantallaPrincipalFacTable producto : productos) {
                Query query2 = sesion.createQuery("from PrecioProductoTable WHERE estado = :estado and idSede = :idSede and idProducto = :idProducto ");
                query2.setParameter("estado", "A");
                query2.setParameter("idSede", sede_sede);
                query2.setParameter("idProducto", this.obtieneIdProductoForCode(producto.getCodigo()));
                producto.setImagen(FileManagement.encodeToString(producto.getRuta(), producto.getExtension()));
                PrecioProductoTable precio = (PrecioProductoTable) query2.uniqueResult();
                if (precio != null) {
                    if (productosRta == null) {
                        productosRta = new ArrayList<>();
                    }
                    producto.setPrecio(precio.getPrecio());
                    productosRta.add(producto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productosRta;
    }

    /**
     * Funcion la cual obtiene las recetas o platos que se deben visualizar en
     * la pantalla principal de facturacion los cuales son parametrizados
     * previamente
     *
     * @param sede_sede
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PantallaPrincipalFacTable> obtieneRecetasPantalla(Integer sede_sede) {
        List<PantallaPrincipalFacTable> productos = null;
        try {
            initOperation();
            Query query = sesion.createQuery("from PantallaPrincipalFacTable WHERE tipo = :tipo");
            query.setParameter("tipo", "R");
            productos = query.list();
            Iterator<PantallaPrincipalFacTable> it = productos.iterator();
            while(it.hasNext()){
                PantallaPrincipalFacTable producto = it.next();
                 Query query2 = sesion.createQuery("from PrecioRecetaTable WHERE estado = :estado and idSede = :idSede and idReceta = :idReceta ");
                query2.setParameter("estado", "A");
                query2.setParameter("idSede", sede_sede);
                query2.setParameter("idReceta",this.obtieneIdRecetaForCode(producto.getCodigo()));
                PrecioRecetaTable precio = (PrecioRecetaTable) query2.uniqueResult();
                if(precio != null){
                    producto.setPrecio(""+precio.getPrecio());
                    producto.setImagen(FileManagement.encodeToString(producto.getRuta(), producto.getExtension()));
                }else{
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    /**
     * Funcion con la cual obtengo el id de una receta basandose en el codigo
     * del mismo
     *
     * @param rece_cod
     * @return
     */
    public Integer obtieneIdRecetaForCode(String rece_cod) {
        Integer id = 0;
        try {
            Query query = sesion.createQuery("from RecetaTable WHERE codigo = :codigo");
            query.setParameter("codigo", rece_cod);
            RecetaTable receta = (RecetaTable) query.uniqueResult();
            id = receta.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Funcion con la cual obtengo el id de una receta basandose en el codigo
     * del mismo
     *
     * @param rece_cod
     * @return
     */
    public Integer obtieneIdProductoForCode(String dska_codigo) {
        Integer id = 0;
        try {
            Query query = sesion
                    .createQuery("from ProductoTable WHERE codigo = :codigo");
            query.setParameter("codigo", dska_codigo);
            ProductoTable producto = (ProductoTable) query.uniqueResult();
            id = producto.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    private void initOperation() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    @Override
    public void close() throws Exception {
        tx.commit();
        sesion.close();
    }

}
