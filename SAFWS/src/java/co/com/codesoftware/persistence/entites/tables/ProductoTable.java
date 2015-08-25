package co.com.codesoftware.persistence.entites.tables;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "in_tdska")
public class ProductoTable {
	private Integer id;
	private Integer referenciaId;
	private String codigo;
	private String nombre;
	private String descripcion;
	private String estado;
	private List<PrecioProductoTable> precios;

	@Id
	@Column(name = "dska_dska")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "dska_refe")
	public Integer getReferenciaId() {
		return referenciaId;
	}

	public void setReferenciaId(Integer referenciaId) {
		this.referenciaId = referenciaId;
	}

	@Column(name = "dska_cod")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name = "dska_nom_prod")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "dska_desc")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "dska_estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Transient
	public List<PrecioProductoTable> getPrecios() {
		return precios;
	}

	public void setPrecios(List<PrecioProductoTable> precios) {
		this.precios = precios;
	}

}
