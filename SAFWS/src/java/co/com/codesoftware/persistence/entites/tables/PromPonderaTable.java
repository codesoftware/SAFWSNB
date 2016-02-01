/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entites.tables;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author nicolas
 */
@Entity
@Table(name = "in_tcepr")
public class PromPonderaTable {
    @Id
    @Column(name = "cepr_cepr")
    private Integer id;
    @Column(name = "cepr_dska")
    private Integer idDska;
    @Column(name = "cepr_existencia")
    private Integer existencias;
    @Column(name = "cepr_promedio_uni")
    private BigDecimal promUni;
    @Column(name = "cepr_promedio_total")
    private BigDecimal promTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDska() {
        return idDska;
    }

    public void setIdDska(Integer idDska) {
        this.idDska = idDska;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }

    public BigDecimal getPromUni() {
        return promUni;
    }

    public void setPromUni(BigDecimal promUni) {
        this.promUni = promUni;
    }

    public BigDecimal getPromTotal() {
        return promTotal;
    }

    public void setPromTotal(BigDecimal promTotal) {
        this.promTotal = promTotal;
    }

}
