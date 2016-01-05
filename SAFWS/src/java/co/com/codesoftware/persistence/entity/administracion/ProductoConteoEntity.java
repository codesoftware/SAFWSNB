/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entity.administracion;

import co.com.codesoftware.persistence.entity.productos.ProductoEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;

/**
 *
 * @author root
 */
@Entity
@Table(name = "in_tecop")
public class ProductoConteoEntity implements Serializable {

    @Id
    @Column(name = "ecop_ecop")
    private Integer id;
    @JoinColumn(name = "ecop_copr")
    @ManyToOne(fetch = FetchType.LAZY)
    private ConteoEntity conteo;
    @JoinColumn(name = "ecop_dska")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductoEntity producto;
    @Column(name = "ecop_valor")
    private Integer cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ConteoEntity getConteo() {
        return conteo;
    }

    public void setConteo(ConteoEntity conteo) {
        this.conteo = conteo;
    }

}
