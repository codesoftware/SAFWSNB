package co.com.codesoftware.persistence.entities.facturacion.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "co_ttem_fact_rece")
public class TemporalRecTable {

	private Integer id;
	private Integer idTrans;
	private Integer idReceta;
	private Integer cantidad;
	private Integer descuento;
	@Id
	@Column(name = "TEM_FACT_RECE")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TEM_RECE_TRANS")
	public Integer getIdTrans() {
		return idTrans;
	}

	public void setIdTrans(Integer idTrans) {
		this.idTrans = idTrans;
	}

	@Column(name = "TEM_RECE_RECE")
	public Integer getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(Integer idReceta) {
		this.idReceta = idReceta;
	}

	@Column(name = "TEM_RECE_CANT")
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	@Column(name="TEM_RECE_DCTO")
	public Integer getDescuento() {
		return descuento;
	}

	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}

}
