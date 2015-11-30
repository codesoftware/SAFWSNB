package co.com.codesoftware.persistence.entites.tables;

import co.com.codesoftware.persistence.entity.productos.ProductoEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "in_tprpr")
public class PrecioProductoEntity implements Serializable {

    private Integer id;
    private ProductoEntity idProducto;
    private String precio;
    private Integer usuarioCrea;
    private String estado;
    private Sede idSede;

    @Id
    @Column(name = "prpr_prpr")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "prpr_precio")
    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @JoinColumn(name = "prpr_dska")
    @ManyToOne(fetch = FetchType.LAZY)
    public ProductoEntity getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(ProductoEntity idProducto) {
        this.idProducto = idProducto;
    }

    @Column(name = "prpr_tius_crea")
    public Integer getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Integer usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    @Column(name = "prpr_estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @JoinColumn(name = "prpr_sede")
    @ManyToOne(fetch = FetchType.LAZY)
    public Sede getIdSede() {
        return idSede;
    }

    public void setIdSede(Sede idSede) {
        this.idSede = idSede;
    }

}
