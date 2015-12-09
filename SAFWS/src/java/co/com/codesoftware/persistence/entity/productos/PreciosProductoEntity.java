/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entity.productos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "in_tprpd")
public class PreciosProductoEntity implements Serializable{
    @Id
    @Column(name = "prpd_prpd")
    private Integer id;
    @Column(name = "prpd_dska")
    private Integer idProducto;
    @Column(name = "prpd_sede")
    private Integer sede;
    @Column(name = "prpd_preu")
    private BigDecimal precioUnidad;
    @Column(name = "prpd_prec")
    private BigDecimal precioXCien;    
    @Column(name = "prpd_prem")
    private BigDecimal precioXMil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getSede() {
        return sede;
    }

    public void setSede(Integer sede) {
        this.sede = sede;
    }

    public BigDecimal getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(BigDecimal precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public BigDecimal getPrecioXCien() {
        return precioXCien;
    }

    public void setPrecioXCien(BigDecimal precioXCien) {
        this.precioXCien = precioXCien;
    }

    public BigDecimal getPrecioXMil() {
        return precioXMil;
    }

    public void setPrecioXMil(BigDecimal precioXMil) {
        this.precioXMil = precioXMil;
    }
    
    
    
    
    
}
