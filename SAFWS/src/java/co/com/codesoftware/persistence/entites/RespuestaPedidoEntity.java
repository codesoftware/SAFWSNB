/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entites;

import co.com.codesoftware.persistence.entites.tables.Cliente;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author root
 */
public class RespuestaPedidoEntity implements Serializable {

    private RespuestaEntity respuesta;
    private List<ProductoGenEntity> listaProductos;
    private Cliente cliente;

    public RespuestaEntity getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaEntity respuesta) {
        this.respuesta = respuesta;
    }

    public List<ProductoGenEntity> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoGenEntity> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
