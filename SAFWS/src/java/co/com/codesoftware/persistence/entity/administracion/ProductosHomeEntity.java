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
@Table(name = "fa_tprodhome")
public class ProductosHomeEntity implements Serializable {

    @Id
    @Column(name = "prodhome_prodhome")
    private Integer id;
    @Column(name = "prodhome_cate")
    private Integer categoria;
    @Column(name = "prodhome_refe")
    private Integer subcategoria;
    @Column(name = "prodhome_sede")
    private Integer sede;
    @Column(name = "prodhome_rutai")
    private String rutaImagen;
    @Column(name = "prodhome_estado")
    private String estado;
    @Column(name = "prodhome_nombre")
    private String nombre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Integer subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Integer getSede() {
        return sede;
    }

    public void setSede(Integer sede) {
        this.sede = sede;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
