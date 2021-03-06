package co.com.codesoftware.persistence.entites.tables;

import co.com.codesoftware.persistence.entity.productos.ProductoEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "in_tprpr")
public class PrecioProductoEntity implements Serializable {

    private Integer id;
    private ProductoEntity idProducto;
    private String precio;
    private Integer usuarioCrea;
    private String estado;
    private Sede idSede;
    private BigDecimal precioIva;
    private BigDecimal precioXUnidad;
    private BigDecimal precioXCien;
    private BigDecimal precioXMil;

    @Id
    @Column(name = "prpr_prpr")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "prpr_precio")
    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @JoinColumn(name = "prpr_dska")
    @ManyToOne(fetch = FetchType.LAZY)
    public ProductoEntity getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(ProductoEntity idProducto) {
        this.idProducto = idProducto;
    }

    @Column(name = "prpr_tius_crea")
    public Integer getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Integer usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    @Column(name = "prpr_estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @JoinColumn(name = "prpr_sede")
    @ManyToOne(fetch = FetchType.LAZY)
    public Sede getIdSede() {
        return idSede;
    }

    public void setIdSede(Sede idSede) {
        this.idSede = idSede;
    }
    @Column(name = "prpr_premsiva")
    public BigDecimal getPrecioIva() {
        return precioIva;
    }

    public void setPrecioIva(BigDecimal precioIva) {
        this.precioIva = precioIva;
    }
    @Column(name = "prpr_preu")
    public BigDecimal getPrecioXUnidad() {
        return precioXUnidad;
    }

    public void setPrecioXUnidad(BigDecimal precioXUnidad) {
        this.precioXUnidad = precioXUnidad;
    }
    @Column(name = "prpr_prec")
    public BigDecimal getPrecioXCien() {
        return precioXCien;
    }

    public void setPrecioXCien(BigDecimal precioXCien) {
        this.precioXCien = precioXCien;
    }
    @Column(name = "prpr_prem")
    public BigDecimal getPrecioXMil() {
        return precioXMil;
    }

    public void setPrecioXMil(BigDecimal precioXMil) {
        this.precioXMil = precioXMil;
    }
    
    

}
