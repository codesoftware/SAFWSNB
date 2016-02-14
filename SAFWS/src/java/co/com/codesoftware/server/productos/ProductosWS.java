/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.server.productos;

import co.com.codesoftware.logic.ProductsLogic;
import co.com.codesoftware.persistence.entites.tables.ExistenciaXSedeTable;
import co.com.codesoftware.persistence.entites.tables.PromPonderaTable;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.productos.ProductoEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author root
 */
@WebService(serviceName = "ProductosWS")
public class ProductosWS {
    
    /**
     * metodo que actualiza un producto
     * @param producto
     * @return 
     */
    @WebMethod(operationName = "actualizaProducto")
    public RespuestaEntity actualizaProducto(@WebParam(name = "ProductoEntity")ProductoEntity producto){
        RespuestaEntity respuesta = new RespuestaEntity();
        try(ProductsLogic logica = new ProductsLogic()) {
            respuesta = logica.actualizaProducto(producto);
        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setCodigoRespuesta(0);
            respuesta.setDescripcionRespuesta(e.getMessage());
            respuesta.setMensajeRespuesta(e.toString());
        }
        return respuesta;
    }
    /**
     * Funcion con la cual busco el promedio ponderado 
     * @param idDska
     * @return 
     */
    @WebMethod(operationName = "buscaPromedioPonderadoProducto")
    public PromPonderaTable buscaPromedioPonderadoProducto(@WebParam(name = "idDska")Integer idDska){
        PromPonderaTable rta = null;
        try(ProductsLogic objLogic = new ProductsLogic()) {
            rta = objLogic.buscaPromedioPondProd(idDska);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }
    /**
     * Funcion con la cual obtengo las existencias que hay en cada sede de cada producto
     * @param idDska
     * @return 
     */
    @WebMethod(operationName = "buscaExistenciasProdu")
    public List<ExistenciaXSedeTable> buscaExistenciasProdu(@WebParam(name = "idDska")Integer idDska){
        List<ExistenciaXSedeTable> rta = null;
        try (ProductsLogic objLogic = new ProductsLogic()) {
            rta = objLogic.buscoExistenciaProd(idDska);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }
    /**
     * metodo que verifica si el precio con el que van a vender el producto no es 
     * inferior al promedio ponderado
     * @param idProducto
     * @param precio
     * @return 
     */
    
    @WebMethod(operationName = "verificaPromedioPonderado")
    public String verificaPromedioPonderado(Integer idProducto,BigDecimal precio){
        String rta = "OK";
        try {
            ProductsLogic objLogic = new ProductsLogic();
            rta = objLogic.consultaPromPonderado(idProducto, precio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }
    
}
