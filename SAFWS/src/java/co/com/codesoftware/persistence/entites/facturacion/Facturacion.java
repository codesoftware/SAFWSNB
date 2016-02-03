package co.com.codesoftware.persistence.entites.facturacion;

import java.util.List;

import co.com.codesoftware.persistence.entities.facturacion.tables.TemporalProdTable;
import co.com.codesoftware.persistence.entities.facturacion.tables.TemporalRecTable;

public class Facturacion {

    private Long idCliente;
    private Long idTius;
    private Long idSede;
    private List<TemporalProdTable> productos;
    private List<TemporalRecTable> recetas;
    private boolean domicilio;
    private Long descuento;
    private Integer idPedido;
    //Parametro en el cual me indica si cobra retefuente o no
    private String reteFuente;

    public boolean isDomicilio() {
        return domicilio;
    }

    public void setDomicilio(boolean domicilio) {
        this.domicilio = domicilio;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdTius() {
        return idTius;
    }

    public void setIdTius(Long idTius) {
        this.idTius = idTius;
    }

    public List<TemporalProdTable> getProductos() {
        return productos;
    }

    public void setProductos(List<TemporalProdTable> productos) {
        this.productos = productos;
    }

    public List<TemporalRecTable> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<TemporalRecTable> recetas) {
        this.recetas = recetas;
    }

    public Long getIdSede() {
        return idSede;
    }

    public void setIdSede(Long idSede) {
        this.idSede = idSede;
    }

    public Long getDescuento() {
        return descuento;
    }

    public void setDescuento(Long descuento) {
        this.descuento = descuento;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getReteFuente() {
        return reteFuente;
    }

    public void setReteFuente(String reteFuente) {
        this.reteFuente = reteFuente;
    }

}
