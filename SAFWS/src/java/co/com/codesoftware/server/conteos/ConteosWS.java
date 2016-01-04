/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.server.conteos;

import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author nicolas
 */
@WebService(serviceName = "ConteosWS")
public class ConteosWS {
    /**
     * Funcion con la cual ingreso un producto al conteo
     * @return 
     */
    public RespuestaEntity ingresaProductoConteo(){
        RespuestaEntity rta = null;
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }
}
