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
@Table(name = "in_trefe")
public class ReferenciaEntity implements Serializable {

    @Id
    @Column(name = "refe_refe")
    private Integer id;
    @Column(name = "refe_nombre")
    private String nombre;
    @Column(name = "refe_desc")
    private String descripcion;
    @Column(name = "refe_estado")
    private String estado;
    @Column(name = "refe_came")
    private String caracteristica1;
    @Column(name = "refe_memori")
    private String caracteristica2;
    @Column(name = "refe_pantalla")
    private String caracteristica3;
    @Column(name = "refe_cate")
    private Integer categoriaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCaracteristica1() {
        return caracteristica1;
    }

    public void setCaracteristica1(String caracteristica1) {
        this.caracteristica1 = caracteristica1;
    }

    public String getCaracteristica2() {
        return caracteristica2;
    }

    public void setCaracteristica2(String caracteristica2) {
        this.caracteristica2 = caracteristica2;
    }

    public String getCaracteristica3() {
        return caracteristica3;
    }

    public void setCaracteristica3(String caracteristica3) {
        this.caracteristica3 = caracteristica3;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

}
