package co.com.codesoftware.persistence.entites.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "us_tclien")
public class Cliente {

    @Id
    @Column(name = "CLIEN_CLIEN")
    private Long id;
    @Column(name = "CLIEN_CEDULA")
    private String cedula;
    @Column(name = "CLIEN_NOMBRES")
    private String nombres;
    @Column(name = "CLIEN_APELLIDOS")
    private String apellidos;
    @Column(name = "CLIEN_TELEFONO")
    private String telefono;
    @Column(name = "CLIEN_CORREO")
    private String correo;
    @Column(name = "CLIEN_DIRECCION")
    private String direccion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }



    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
