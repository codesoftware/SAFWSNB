/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entity.administracion;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nicolas
 */
@Entity
@Table(name = "in_tcopr")
public class ConteoEntity {
    @Id
    @Column(name = "copr_copr")
    private Integer id;
    @Column(name="copr_estado")
    private String estado;
    @Column(name="copr_tius")
    private Integer tius;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="copr_fecha")
    private Date fecha;
    @Column(name="copr_sede")
    private Integer sede;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="copr_fec_ini")
    private Date fechaIni;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="copr_fec_fin")
    private Date fechaFin;
    @Column(name="copr_desc")
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getTius() {
        return tius;
    }

    public void setTius(Integer tius) {
        this.tius = tius;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getSede() {
        return sede;
    }

    public void setSede(Integer sede) {
        this.sede = sede;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
