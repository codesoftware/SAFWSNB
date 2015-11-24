/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entity.administracion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author root
 */
@Entity
@Table(name = "in_tcate")
public class CategoriaEntity implements Serializable{
   @Id
   @Column(name="cate_cate")
   private Integer id;
   @Column(name="cate_desc")
   private String descripcion;
   @Column(name="cate_estado")
   private String estado;
   @Column(name="cate_runic")
   private String runic;
   @Column(name="cate_feven")
   private String feven;
   @Column(name="cate_sbcu")
   private Integer subcuenta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRunic() {
        return runic;
    }

    public void setRunic(String runic) {
        this.runic = runic;
    }

    public String getFeven() {
        return feven;
    }

    public void setFeven(String feven) {
        this.feven = feven;
    }

    public Integer getSubcuenta() {
        return subcuenta;
    }

    public void setSubcuenta(Integer subcuenta) {
        this.subcuenta = subcuenta;
    }
   
   

    
}
