/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.server.productos;

import co.com.codesoftware.logic.ProductsLogic;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.productos.ProductoEntity;
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
    
}
