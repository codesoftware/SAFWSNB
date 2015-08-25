package co.com.codesoftware.persistence.entites.facturacion;

import java.util.List;

import co.com.codesoftware.persistence.entities.facturacion.tables.TemporalProdTable;
import co.com.codesoftware.persistence.entities.facturacion.tables.TemporalRecTable;

public class Facturacion {

	private Long					idCliente;
	private Long					idTius;
	private Long					idSede;
	private List<TemporalProdTable>	productos;
	private List<TemporalRecTable>	recetas;

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

}
