package co.com.codesoftware.persistence.entites.tables;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "us_ttius")
public class UsuarioTable {

	private Long id;
	private Integer idPersona;
	private Integer idPerfil;
	private String usuario;
	private Date fecha_registro;
	private Date ultimo_ingresa;
	private String password;
	private String passwordFuturo;
	private String cambioContra;
	private String estado;
	private Integer sede;
	private List<PersonaUsuarioTable> persona;

	@Id
	@Column(name = "tius_tius")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "tius_perf")
	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	@Column(name = "tius_usuario")
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name = "tius_contra_act")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "tius_contra_futura")
	public String getPasswordFuturo() {
		return passwordFuturo;
	}

	public void setPasswordFuturo(String passwordFuturo) {
		this.passwordFuturo = passwordFuturo;
	}

	@Column(name = "tius_cambio_contra")
	public String getCambioContra() {
		return cambioContra;
	}

	public void setCambioContra(String cambioContra) {
		this.cambioContra = cambioContra;
	}

	@Column(name = "tius_estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "tius_sede")
	public Integer getSede() {
		return sede;
	}

	public void setSede(Integer sede) {
		this.sede = sede;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "tius_fecha_registro")
	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tius_ultimo_ingreso")
	public Date getUltimo_ingresa() {
		return ultimo_ingresa;
	}

	public void setUltimo_ingresa(Date ultimo_ingresa) {
		this.ultimo_ingresa = ultimo_ingresa;
	}

	@Column(name = "tius_pers")
	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	@Transient
	public List<PersonaUsuarioTable> getPersona() {
		return persona;
	}

	public void setPersona(List<PersonaUsuarioTable> persona) {
		this.persona = persona;
	}

}
