/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entity.usuario;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "us_ttius")
public class TipoUsuarioEntity implements Serializable{
    @Id
    @Column(name = "tius_tius")
    private Integer id;
    @Column(name = "tius_usuario")
    private String usuario;
    @JoinColumn(name = "tius_perf")
    @ManyToOne(fetch = FetchType.LAZY)
    private PerfilesEntity Perfil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public PerfilesEntity getPerfil() {
        return Perfil;
    }

    public void setPerfil(PerfilesEntity Perfil) {
        this.Perfil = Perfil;
    }
    
    
}
