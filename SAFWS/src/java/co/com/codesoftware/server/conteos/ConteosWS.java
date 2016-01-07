/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.server.conteos;

import co.com.codesoftware.logic.admin.ConteosLogic;
import co.com.codesoftware.persistence.entity.administracion.ConteoEntity;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.transformer.ProductoConteoEntityTR;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

/**
 *
 * @author nicolas
 */
@WebService(serviceName = "ConteosWS")
public class ConteosWS {

    /**
     * Funcion que inserta un producto al conteo
     *
     * @param codExterno
     * @param codProducto
     * @param codConteo
     * @param Cantidad
     * @param ubicacion
     * @param codigoBarras
     * @return
     */
    @WebMethod(operationName = "insertaProdConteo")
    @WebResult(name = "RespuestaEntity")
    public RespuestaEntity ingresaProductoConteo(@WebParam(name = "codExterno") String codExterno, @WebParam(name = "codConteo") Integer codConteo, @WebParam(name = "cantidad") Integer Cantidad, @WebParam(name = "codigoBarras") String codigoBarras,@WebParam(name = "ubicacion")String ubicacion) {
        RespuestaEntity rta = null;
        try (ConteosLogic logic = new ConteosLogic()){
            rta = logic.insProdConteo(codConteo, codExterno, Cantidad, codigoBarras, ubicacion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }
    /**
     * metodo el cual consulta los conteos por estado
     * @param estado
     * @return 
     */
    @WebMethod(operationName = "consultaconteos")
    @WebResult(name = "ConteosEntity")
    public List<ConteoEntity> consultaConteoEstado(@WebParam(name = "estado")String estado){
        List<ConteoEntity> resultado = null;
        try (ConteosLogic logic = new ConteosLogic()){
            resultado = logic.consultaConteosEstado(estado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    /**
     * metodo que consulta un producto de un conteo especifico
     * @param codExterno
     * @param conteo
     * @return 
     */
    @WebMethod(operationName = "consultaProdConteo")
    @WebResult(name = "ProductoConteoEntityTR")
    public ProductoConteoEntityTR consultaProdConteo(@WebParam(name = "codigoExterno")String codExterno,@WebParam(name = "idConteo")Integer conteo){
        ProductoConteoEntityTR rta= new ProductoConteoEntityTR();
        try(ConteosLogic logic = new ConteosLogic()) {
            rta = logic.consultaProductoConteo(conteo, codExterno);
        } catch (Exception e) {
            rta.setMensaje(e.toString());
        }
        return rta;
    }
    /**
     * Metodo que consulta todos los porductos que se han añadido al conteo
     * @param codExterno
     * @param conteo
     * @return 
     */
     @WebMethod(operationName = "consultaProductosConteo")
    @WebResult(name = "ProductoConteoEntityTR")
    public List<ProductoConteoEntityTR> consultaProductosConteo(@WebParam(name = "idConteo")Integer conteo){
        List<ProductoConteoEntityTR> rta= null;
        try(ConteosLogic logic = new ConteosLogic()) {
            rta = logic.consultaProductosConteo(conteo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }
}
