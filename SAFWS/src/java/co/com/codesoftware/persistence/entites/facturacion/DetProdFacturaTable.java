package co.com.codesoftware.persistence.entites.facturacion;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import co.com.codesoftware.persistence.entites.tables.ProductoTable;
import java.io.Serializable;

@Entity
@Table(name = "fa_tdtpr")
public class DetProdFacturaTable implements Serializable {

	private Integer			id;
	private Integer			idProducto;
	private Integer			idFactura;
	private Date			fecha;
	private Integer			numProd;
	private Integer			cantidad;
	private BigDecimal		valorTotal;
	private BigDecimal		valorUnidad;
	private BigDecimal		valorIvaTotal;
	private BigDecimal		valorIvaUnidad;
	private BigDecimal		valorVentaUnidad;
	private BigDecimal		valorVentaTotal;
	private String			aplicaDescuento;
	private String			descuento;
	private String			estado;
	private Integer			idKardex;
	private Integer			idKardexDevolucion;
	private BigDecimal		utilidad;
	private ProductoTable	producto;

	@Id
	@Column(name = "dtpr_dtpr")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "dtpr_dska")
	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	@Column(name = "dtpr_fact")
	public Integer getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtpr_fecha")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "dtpr_num_prod")
	public Integer getNumProd() {
		return numProd;
	}

	public void setNumProd(Integer numProd) {
		this.numProd = numProd;
	}

	@Column(name = "dtpr_cant")
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name = "dtpr_vlr_pr_tot")
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Column(name = "dtpr_vlr_uni_prod")
	public BigDecimal getValorUnidad() {
		return valorUnidad;
	}

	public void setValorUnidad(BigDecimal valorUnidad) {
		this.valorUnidad = valorUnidad;
	}

	@Column(name = "dtpr_vlr_iva_tot")
	public BigDecimal getValorIvaTotal() {
		return valorIvaTotal;
	}

	public void setValorIvaTotal(BigDecimal valorIvaTotal) {
		this.valorIvaTotal = valorIvaTotal;
	}

	@Column(name = "dtpr_vlr_iva_uni")
	public BigDecimal getValorIvaUnidad() {
		return valorIvaUnidad;
	}

	public void setValorIvaUnidad(BigDecimal valorIvaUnidad) {
		this.valorIvaUnidad = valorIvaUnidad;
	}

	@Column(name = "dtpr_vlr_venta_uni")
	public BigDecimal getValorVentaUnidad() {
		return valorVentaUnidad;
	}

	public void setValorVentaUnidad(BigDecimal valorVentaUnidad) {
		this.valorVentaUnidad = valorVentaUnidad;
	}

	@Column(name = "dtpr_vlr_total")
	public BigDecimal getValorVentaTotal() {
		return valorVentaTotal;
	}

	public void setValorVentaTotal(BigDecimal valorVentaTotal) {
		this.valorVentaTotal = valorVentaTotal;
	}

	@Column(name = "dtpr_desc")
	public String getAplicaDescuento() {
		return aplicaDescuento;
	}

	public void setAplicaDescuento(String aplicaDescuento) {
		this.aplicaDescuento = aplicaDescuento;
	}

	@Column(name = "dtpr_con_desc")
	public String getDescuento() {
		return descuento;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}

	@Column(name = "dtpr_estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "dtpr_kapr")
	public Integer getIdKardex() {
		return idKardex;
	}

	public void setIdKardex(Integer idKardex) {
		this.idKardex = idKardex;
	}

	@Column(name = "dtpr_dev_kapr")
	public Integer getIdKardexDevolucion() {
		return idKardexDevolucion;
	}

	public void setIdKardexDevolucion(Integer idKardexDevolucion) {
		this.idKardexDevolucion = idKardexDevolucion;
	}

	@Column(name = "dtpr_utilidad")
	public BigDecimal getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(BigDecimal utilidad) {
		this.utilidad = utilidad;
	}
	@Transient
	public ProductoTable getProducto() {
		return producto;
	}

	public void setProducto(ProductoTable producto) {
		this.producto = producto;
	}

}
