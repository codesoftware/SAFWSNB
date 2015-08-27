/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic;

import co.com.codesoftware.persistence.entites.facturacion.ProductoGenericoEntity;
import co.com.codesoftware.persistence.entites.tables.ProductoTable;
import co.com.codesoftware.persistence.entites.tables.RecetaTable;
import co.com.codesoftware.types.TypeProduct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class ProductosGenericosLogic {

    /**
     * Funcion encargada de realizar la busqueda de productos y recetas aptos
     * para su facturacion
     *
     * @param sede_sede
     * @return
     */
    public List<ProductoGenericoEntity> buscaProductosRecetas(Integer sede_sede) {
        List<ProductoGenericoEntity> rta = null;
        try {
            RecetaLogic recetaLogic = new RecetaLogic();
            List<RecetaTable> recetas = recetaLogic.getRecetas(null, sede_sede);
            ProductsLogic productsLogic = new ProductsLogic();
            List<ProductoTable> productos = productsLogic.buscaProductos(sede_sede);
            if (recetas != null & recetas.size() > 0) {
                if (rta == null) {
                    rta = new ArrayList<>();
                }
                for (RecetaTable receta : recetas) {
                    rta.add(this.mapeaGenericObjectReceta(receta));
                }
            }
            if (productos != null & productos.size() > 0) {
                if (rta == null) {
                    rta = new ArrayList<>();
                }
                for (ProductoTable producto : productos) {
                    rta.add(this.mapeaGenericObjectProducto(producto));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

    /**
     * Funcion con la cual mapeo de un objeto RecetaTable y lo convierto en un
     * objeto Generico
     *
     * @param receta
     * @return
     */
    private ProductoGenericoEntity mapeaGenericObjectReceta(RecetaTable receta) {
        ProductoGenericoEntity rta = new ProductoGenericoEntity();
        try {
            rta.setCodigo(receta.getCodigo());
            rta.setId(receta.getId());
            rta.setNombre(receta.getDescripcion());
            rta.setPrecio("" + receta.getPrecios().get(0).getPrecio());
            rta.setTipoProducto(TypeProduct.RECETA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

    /**
     * Funcion con la cual mapeo de un objeto ProductoTable y lo convierto en un
     * objeto Generico
     *
     * @param receta
     * @return
     */
    private ProductoGenericoEntity mapeaGenericObjectProducto(ProductoTable producto) {
        ProductoGenericoEntity rta = new ProductoGenericoEntity();
        try {
            rta.setCodigo(producto.getCodigo());
            rta.setId(producto.getId());
            rta.setNombre(producto.getDescripcion());
            rta.setPrecio("" + producto.getPrecios().get(0).getPrecio());
            rta.setTipoProducto(TypeProduct.PRODUCTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

}
