package co.com.codesoftware.persistence.entities.facturacion.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "CO_TTEM_FACT")
public class TemporalProdTable {

	private Integer id;
	private Integer idTrans;
	private Integer idDska;
	private Integer cantidad;
	private Integer descuento;
	
	
	@Id
	@Column(name="TEM_FACT")
	@Generated(GenerationTime.INSERT)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "tem_fact_trans")
	public Integer getIdTrans() {
		return idTrans;
	}

	public void setIdTrans(Integer idTrans) {
		this.idTrans = idTrans;
	}

	@Column(name = "tem_fact_dska")
	public Integer getIdDska() {
		return idDska;
	}

	public void setIdDska(Integer idDska) {
		this.idDska = idDska;
	}

	@Column(name = "tem_fact_cant")
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name = "tem_fact_dcto")
	public Integer getDescuento() {
		return descuento;
	}

	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}

}
