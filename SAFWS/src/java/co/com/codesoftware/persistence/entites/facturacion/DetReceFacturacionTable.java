/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entites.facturacion;

import co.com.codesoftware.persistence.entites.tables.RecetaTable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "fa_tdtre")
public class DetReceFacturacionTable {

    private Integer id;
    private Integer idRece;
    private Integer idFact;
    private Date fecha;
    private Integer cantidad;
    private BigDecimal vlrRecetasTot;
    private BigDecimal vlrRecetasUni;
    private BigDecimal vlrIvaTotal;
    private BigDecimal vlrIvaUni;
    private BigDecimal vlrVentaTotal;
    private BigDecimal vlrVentaUni;
    private BigDecimal total;
    private String descuento;
    private String conDescuento;
    private BigDecimal vlrDescuento;
    private String estado;
    private BigDecimal utilidad;
    private RecetaTable receta;

    @Id
    @Column(name = "dtre_dtre")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "dtre_rece")
    public Integer getIdRece() {
        return idRece;
    }

    public void setIdRece(Integer idRece) {
        this.idRece = idRece;
    }

    @Column(name = "dtre_fact")
    public Integer getIdFact() {
        return idFact;
    }

    public void setIdFact(Integer idFact) {
        this.idFact = idFact;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dtre_fecha")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "dtre_cant")
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Column(name = "dtre_vlr_re_tot")
    public BigDecimal getVlrRecetasTot() {
        return vlrRecetasTot;
    }

    public void setVlrRecetasTot(BigDecimal vlrRecetasTot) {
        this.vlrRecetasTot = vlrRecetasTot;
    }

    @Column(name = "dtre_vlr_uni_rece")
    public BigDecimal getVlrRecetasUni() {
        return vlrRecetasUni;
    }

    public void setVlrRecetasUni(BigDecimal vlrRecetasUni) {
        this.vlrRecetasUni = vlrRecetasUni;
    }

    @Column(name = "dtre_vlr_iva_tot")
    public BigDecimal getVlrIvaTotal() {
        return vlrIvaTotal;
    }

    public void setVlrIvaTotal(BigDecimal vlrIvaTotal) {
        this.vlrIvaTotal = vlrIvaTotal;
    }

    @Column(name = "dtre_vlr_iva_uni")
    public BigDecimal getVlrIvaUni() {
        return vlrIvaUni;
    }

    public void setVlrIvaUni(BigDecimal vlrIvaUni) {
        this.vlrIvaUni = vlrIvaUni;
    }

    @Column(name = "dtre_vlr_venta_tot")
    public BigDecimal getVlrVentaTotal() {
        return vlrVentaTotal;
    }

    public void setVlrVentaTotal(BigDecimal vlrVentaTotal) {
        this.vlrVentaTotal = vlrVentaTotal;
    }

    @Column(name = "dtre_vlr_venta_uni")
    public BigDecimal getVlrVentaUni() {
        return vlrVentaUni;
    }

    public void setVlrVentaUni(BigDecimal vlrVentaUni) {
        this.vlrVentaUni = vlrVentaUni;
    }

    @Column(name = "dtre_vlr_total")
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Column(name = "dtre_desc")
    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    @Column(name = "dtre_con_desc")
    public String getConDescuento() {
        return conDescuento;
    }

    public void setConDescuento(String conDescuento) {
        this.conDescuento = conDescuento;
    }

    @Column(name = "dtre_valor_desc")
    public BigDecimal getVlrDescuento() {
        return vlrDescuento;
    }

    public void setVlrDescuento(BigDecimal vlrDescuento) {
        this.vlrDescuento = vlrDescuento;
    }

    @Column(name = "dtre_estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Column(name = "dtre_utilidad")
    public BigDecimal getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(BigDecimal utilidad) {
        this.utilidad = utilidad;
    }

    @Transient
    public RecetaTable getReceta() {
        return receta;
    }

    public void setReceta(RecetaTable receta) {
        this.receta = receta;
    }

}
