/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.server.conteos;

import co.com.codesoftware.logic.admin.ConteosLogic;
import co.com.codesoftware.persistence.entity.administracion.ConteoEntity;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
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
     * @param codProducto
     * @param codConteo
     * @param Cantidad
     * @param codigoBarras
     * @return
     */
    @WebMethod(operationName = "insertaProdConteo")
    @WebResult(name = "RespuestaEntity")
    public RespuestaEntity ingresaProductoConteo(@WebParam(name = "codExterno") String codExterno, @WebParam(name = "codConteo") Integer codConteo, @WebParam(name = "cantidad") Integer Cantidad, @WebParam(name = "codigoBarras") String codigoBarras) {
        RespuestaEntity rta = null;
        try (ConteosLogic logic = new ConteosLogic()){
            rta = logic.insertaProdConteo(codConteo, codExterno, Cantidad, codigoBarras);
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
}
