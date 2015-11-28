/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entites;

import co.com.codesoftware.persistence.entites.tables.Cliente;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author root
 */
public class RespuestaPedidoEntity implements Serializable{
    private RespuestaPedidoEntity respuesta;
    private List<ProductoGenericoEntity> listaProductos;
    private Cliente cliente;

    public RespuestaPedidoEntity getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaPedidoEntity respuesta) {
        this.respuesta = respuesta;
    }

    public List<ProductoGenericoEntity> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoGenericoEntity> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
}
