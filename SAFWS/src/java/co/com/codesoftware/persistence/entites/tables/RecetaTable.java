package co.com.codesoftware.persistence.entites.tables;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "in_trece", uniqueConstraints = { @UniqueConstraint(columnNames = "rece_codigo") })
public class RecetaTable {

	private Integer id;
	private String codigo;
	private String nombre;
	private String descripcion;
	private String iva;
	private String estado;
	private Date fechaIngreso;
	private BigDecimal promedio;
	private List<PrecioRecetaTable> precios;

	@Id
	@Column(name = "rece_rece", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "rece_codigo", nullable = false)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name = "rece_nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "rece_desc")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "rece_iva")
	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	@Column(name = "rece_estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "rece_fec_ingreso")
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	@Column(name = "rece_promedio")
	public BigDecimal getPromedio() {
		return promedio;
	}

	public void setPromedio(BigDecimal promedio) {
		this.promedio = promedio;
	}
	@Transient
	public List<PrecioRecetaTable> getPrecios() {
		return precios;
	}

	public void setPrecios(List<PrecioRecetaTable> precios) {
		this.precios = precios;
	}

}
