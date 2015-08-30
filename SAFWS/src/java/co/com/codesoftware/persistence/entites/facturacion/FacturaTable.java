package co.com.codesoftware.persistence.entites.facturacion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import co.com.codesoftware.persistence.entites.tables.Cliente;

@Entity
@Table(name = "fa_tfact")
public class FacturaTable {

    private Integer id;
    private Integer idTius;
    private Date fecha;
    private Long idCliente;
    private BigDecimal valor;
    private BigDecimal vlrIva;
    private String tipoPago;
    private String idVaucher;
    private String comentarios;
    private String estado;
    private String naturaleza;
    private String devolucion;
    private Integer original;
    private BigDecimal descuento;
    private BigDecimal efectivo;
    private BigDecimal tarjeta;
    private Integer idCierre;
    private Integer idSede;
    private List<DetProdFacturaTable> detalleProductos;
    private List<DetReceFacturacionTable> detalleRecetas;
    private Cliente cliente;

    @Id
    @Column(name = "fact_fact")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "fact_tius")
    public Integer getIdTius() {
        return idTius;
    }

    public void setIdTius(Integer idTius) {
        this.idTius = idTius;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fact_fec_ini")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "fact_clien")
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    @Column(name = "fact_vlr_total")
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Column(name = "fact_vlr_iva")
    public BigDecimal getVlrIva() {
        return vlrIva;
    }

    public void setVlrIva(BigDecimal vlrIva) {
        this.vlrIva = vlrIva;
    }

    @Column(name = "fact_tipo_pago")
    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    @Column(name = "fact_id_voucher")
    public String getIdVaucher() {
        return idVaucher;
    }

    public void setIdVaucher(String idVaucher) {
        this.idVaucher = idVaucher;
    }

    @Column(name = "fact_cometarios")
    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Column(name = "fact_estado")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Column(name = "fact_naturaleza")
    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    @Column(name = "fact_devolucion")
    public String getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(String devolucion) {
        this.devolucion = devolucion;
    }

    @Column(name = "fact_original")
    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    @Column(name = "fact_vlr_dcto")
    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    @Column(name = "fact_vlr_efectivo")
    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    @Column(name = "fact_vlr_tarjeta")
    public BigDecimal getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(BigDecimal tarjeta) {
        this.tarjeta = tarjeta;
    }

    @Column(name = "fact_cierre")
    public Integer getIdCierre() {
        return idCierre;
    }

    public void setIdCierre(Integer idCierre) {
        this.idCierre = idCierre;
    }

    @Column(name = "fact_sede")
    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    @Transient
    public List<DetProdFacturaTable> getDetalleProductos() {
        return detalleProductos;
    }

    public void setDetalleProductos(List<DetProdFacturaTable> detalleProductos) {
        this.detalleProductos = detalleProductos;
    }

    @Transient
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    @Transient
    public List<DetReceFacturacionTable> getDetalleRecetas() {
        return detalleRecetas;
    }

    public void setDetalleRecetas(List<DetReceFacturacionTable> detalleRecetas) {
        this.detalleRecetas = detalleRecetas;
    }

}
