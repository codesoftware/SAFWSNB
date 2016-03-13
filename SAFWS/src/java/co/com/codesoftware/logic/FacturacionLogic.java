package co.com.codesoftware.logic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.facturacion.DetProdFacturaTable;
import co.com.codesoftware.persistence.entites.facturacion.DetReceFacturacionTable;
import co.com.codesoftware.persistence.entites.facturacion.FacturaTable;
import co.com.codesoftware.persistence.entites.facturacion.Facturacion;
import co.com.codesoftware.persistence.entites.facturacion.RespuestaFacturacion;
import co.com.codesoftware.persistence.entites.tables.Cliente;
import co.com.codesoftware.persistence.entites.tables.ParametrosEmpresaTable;
import co.com.codesoftware.persistence.entites.tables.ProductoTable;
import co.com.codesoftware.persistence.entites.tables.RecetaTable;
import co.com.codesoftware.persistence.entites.tables.UsuarioTable;
import co.com.codesoftware.persistence.entities.facturacion.tables.TemporalProdTable;
import co.com.codesoftware.persistence.entities.facturacion.tables.TemporalRecTable;
import co.com.codesoftware.persistence.enumeration.DataType;
import co.com.codesoftware.utilities.ReadFunction;
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class FacturacionLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;
    private String llamadoFunction;
    private String idFactura;
    private String mensaje;

    /**
     * Funcion encargada de realizar la logica para la realizacion de la factura
     * en el sistema
     *
     * @param facturacion
     * @return
     */
    public RespuestaFacturacion generaFacturacion(Facturacion facturacion) {
        RespuestaFacturacion rta = new RespuestaFacturacion();
        Integer idTrans = 0;
        String msn = "Validacion de datos incorrecta";
        int iterator = 0;
        try {
            if (this.validaListasRecibidad(facturacion)) {
                boolean valida = this.validaInformacionPreFacturacion(facturacion, rta);
                if (valida) {
                    initOperation();
                    idTrans = this.getSecunceTransId();
                    if (facturacion.getProductos() != null) {
                        for (TemporalProdTable producto : facturacion.getProductos()) {
                            boolean validaItem = validaItemProducto(producto);
                            if (validaItem) {
                                Integer id = this.getMaxId();
                                producto.setId(id + 1);
                                producto.setIdTrans(idTrans);
                                sesion.save(producto);
                                tx.commit();
                                iterator++;
                            }
                        }
                    }
                    if (facturacion.getRecetas() != null) {
                        for (TemporalRecTable receta : facturacion.getRecetas()) {
                            boolean validaItem = validaItemReceta(receta);
                            if (validaItem) {
                                Integer id = this.getMaxIdReceta();
                                receta.setId(id + 1);
                                receta.setIdTrans(idTrans);
                                sesion.save(receta);
                                tx.commit();
                                iterator++;
                            }
                        }

                    }
                    if (iterator > 0) {
                        if (facturacion.isDomicilio()) {
                            //Logica para enviar que la factura es domicilio
                        }
                        msn = this.llamaFuncionFacturacion(facturacion, idTrans);
                        rta.setRespuesta(llamadoFunction);
                        rta.setIdFacturacion(this.idFactura);
                        rta.setTrazaExcepcion(msn);
                    } else {
                        rta.setRespuesta("Error");
                        rta.setTrazaExcepcion("No se registro ningun producto valido por facturar");
                    }
                }
            } else {
                rta.setRespuesta("Error");
                rta.setTrazaExcepcion("Las dos listas no pueden estar vacias");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rta.setRespuesta("Error");
            rta.setTrazaExcepcion(e.toString());
        }
        return rta;
    }

    /**
     * Funcion la cual se encargara de validar la informacion enviada en la
     * facturacion antes de realizar la facturacion
     *
     * @param facturacion
     * @return
     */
    private boolean validaInformacionPreFacturacion(Facturacion facturacion, RespuestaFacturacion rta) {
        try {
            if (facturacion.getIdTius() == null) {
                rta.setRespuesta("Error");
                rta.setTrazaExcepcion("Debe registrar el id del usuario que desea realizar la operación");
                return false;
            }
            if (facturacion.getIdSede() == null) {
                rta.setRespuesta("Error");
                rta.setTrazaExcepcion("Debe registrar el id del usuario que desea realizar la operación");
                return false;
            }
            if (facturacion.getIdCliente() == null) {
                rta.setRespuesta("Error");
                rta.setTrazaExcepcion("El id del cliente llega nulo imposible procesar la solicitud");
            }
            boolean valida = this.validaCliente(facturacion.getIdCliente());
            if (!valida) {
                rta.setRespuesta("Error");
                rta.setTrazaExcepcion("El cliente al cual le desea realizar la facturacion no se encuentra en la base de datos del sistema");
                return false;
            }

            valida = this.validaUsuario(facturacion.getIdTius());
            if (!valida) {
                rta.setRespuesta("Error");
                rta.setTrazaExcepcion("El usuario que intenta hacer la operacion de facturar no se encuentra en el sistema");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Funcion encargada de validar si un cliente existe basandose en su id
     *
     * @param clien_clien
     * @return
     */
    private boolean validaCliente(Long clien_clien) {
        try (ClienteLogic clienteLogic = new ClienteLogic()) {
            Cliente cliente = clienteLogic.getCliente(clien_clien);
            if (cliente == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Funcion encargada de validar si existe un usuario basado en id del
     * usuario
     *
     * @param tius_tius
     * @return
     */
    private boolean validaUsuario(Long tius_tius) {
        try (UsuarioLogic usuaLogic = new UsuarioLogic()) {
            UsuarioTable usuario = usuaLogic.obtieneUsuarioXId(tius_tius);
            if (usuario == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Funcion la cual obtiene el id unico de la tabla temporal de facturacion
     * de productos
     *
     * @return
     */
    public Integer getMaxId() {
        Integer result = null;
        try {
            initOperation();
            result = (Integer) sesion.createQuery("select coalesce(max(id),0) from TemporalProdTable").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Funcion la cual obtiene el id unico de la tabla temporal de facturacion
     * de recetas
     *
     * @return
     */
    public Integer getMaxIdReceta() {
        Integer result = null;
        try {
            initOperation();
            result = (Integer) sesion.createQuery("select coalesce(max(id),0) from TemporalRecTable").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Funcion la cual obtiene el valor unico de transaccion el cual tendran
     * todos los items de la transaccion
     *
     * @return
     */
    public Integer getSecunceTransId() {
        Integer result = null;
        try {
            initOperation();
            result = (Integer) sesion.createSQLQuery("select cast(nextval('co_temp_tran_factu_sec')as int) ").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * Funcion la cual valida si un objeto producto de facturacion viene con los
     * datos correctos
     *
     * @return
     */
    public boolean validaItemProducto(TemporalProdTable producto) {
        try {
            if (producto.getCantidad() == null) {
                this.mensaje = "La cantidad del producto no puede ser nula";
                return false;
            }
            if (producto.getCantidad() <= 0) {
                this.mensaje = "La cantidad del producto debe ser mayor a uno";
                return false;
            }

            if (producto.getIdDska() == null) {
                this.mensaje = "El id del producto no puede ser nulo";
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Funcion la cual valida si un objeto receta de facturacion viene con los
     * datos correctos
     *
     * @return
     */
    public boolean validaItemReceta(TemporalRecTable receta) {
        try {
            if (receta.getCantidad() == null) {
                this.mensaje = "La cantidad de una receta no puede ser nula";
                return false;
            }

            if (receta.getCantidad() <= 0) {
                this.mensaje = "La cantidad del producto no puede ser menor o igual a cero";
                return false;
            }

            if (receta.getIdReceta() == null) {
                this.mensaje = "El id de la receta no puede ser nulo";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Funcion encargada de realizar el llamado a la funcion que realiza toda la
     * facturacion
     *
     * @return
     */
    public String llamaFuncionFacturacion(Facturacion objFactura, Integer idTrans) {
        String rta = "";
        List<String> response = new ArrayList<>();
        try (ReadFunction rf = new ReadFunction()) {
            rf.setNombreFuncion("FA_FACTURACION");
            rf.setNumParam(7);
            rf.addParametro("" + objFactura.getIdTius(), DataType.INT);
            rf.addParametro("" + objFactura.getIdCliente(), DataType.INT);
            rf.addParametro("" + idTrans, DataType.INT);
            rf.addParametro("" + objFactura.getIdSede(), DataType.INT);
            rf.addParametro("N/A", DataType.TEXT);
            rf.addParametro("0", DataType.BIGDECIMAL);
            rf.addParametro("0", DataType.BIGDECIMAL);
            rf.callFunctionJdbc();
            response = rf.getRespuestaPg();
            String respuesta = response.get(0);
            if (respuesta.indexOf("Error") >= 0) {
                respuesta = respuesta.replaceAll("Error", "");
                rta = respuesta;
                llamadoFunction = "error";
            } else {
                String[] aux = respuesta.split("-");
                llamadoFunction = aux[0];
                idFactura = aux[1];
            }
        } catch (Exception e) {
            llamadoFunction = "error";
            rta = e.toString();
            e.printStackTrace();
        }
        return rta;
    }

    /**
     * Funcion la cual obtendra todas las facturas que se encuentren registradas
     * en el sistema
     *
     * @param sede
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FacturaTable> consultaFacturas(Date fechaInicial, Date fechaFinal, Integer idFactura) {
        List<FacturaTable> facturas = null;
        try {
            initOperation();
            Criteria crit = sesion.createCriteria(FacturaTable.class);
            if (idFactura != null && idFactura != 0) {
                crit.add(Restrictions.eq("id", idFactura));
            } else {
                fechaFinal.setHours(23);
                fechaFinal.setMinutes(59);
                fechaFinal.setSeconds(59);
                crit.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
            }
            facturas = crit.list();
            for (FacturaTable factura : facturas) {
                if (factura != null) {
                    Query query2 = sesion.createQuery("from Cliente WHERE id = :idCliente ");
                    query2.setParameter("idCliente", factura.getIdCliente());
                    factura.setCliente((Cliente) query2.uniqueResult());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facturas;
    }

    /**
     * Funcion con la cual se obtienen las facturas por medio de una serie de
     * criterios
     *
     * @param fechaInicial
     * @param fechaFinal
     * @param idFactura
     * @param idCliente
     * @param codExterno
     * @return
     */
    public List<FacturaTable> consultaFacturasXFiltros(Date fechaInicial,
            Date fechaFinal,
            Integer idFactura,
            Long idCliente,
            String codExterno) {
        List<FacturaTable> facturas = null;
        try {
            initOperation();
            //Numero desde el cual se inicio la facturacion
            Integer iniFact = this.buscaConcecutivoFactura();
            if(idFactura == null){
                idFactura = 0;
            }
            idFactura =  idFactura - iniFact ;
            Criteria crit = sesion.createCriteria(FacturaTable.class);
            if (idFactura != null && idFactura > 0) {
                crit.add(Restrictions.eq("id", idFactura));
            } else {
                if (idCliente != null && idCliente != 0) {
                    crit.add(Restrictions.eq("idCliente", idCliente));
                }
                if (codExterno != null && !"".equalsIgnoreCase(codExterno)) {
                    Integer idProducto = this.buscaIdProductoXCodigoExterno(codExterno);
                    if(idProducto != null && idProducto != 0){
                        ArrayList<Integer> idFacturas = this.obtieneFacturasXCodigoProducto(idProducto);
                        if(idFacturas != null){
                            crit.add(Restrictions.in("id", idFacturas));
                        }
                    }
                }
                fechaFinal.setHours(23);
                fechaFinal.setMinutes(59);
                fechaFinal.setSeconds(59);
                crit.add(Restrictions.between("fecha", fechaInicial, fechaFinal));
            }
            facturas = crit.list();
            for(FacturaTable fac : facturas){
                fac.setIdFactVisual(fac.getId()+iniFact);
            }
            for (FacturaTable factura : facturas) {
                if (factura != null) {
                    Query query2 = sesion.createQuery("from Cliente WHERE id = :idCliente ");
                    query2.setParameter("idCliente", factura.getIdCliente());
                    factura.setCliente((Cliente) query2.uniqueResult());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facturas;
    }
    /**
     * Funcion con la cual busco el consecutivo de factura que se encuentra en los parametros de la aplicacion
     * @return 
     */
    public Integer buscaConcecutivoFactura(){
        Integer id = 0;
        try {
            Criteria crit = sesion.createCriteria(ParametrosEmpresaTable.class);
            crit.add(Restrictions.eq("clave","FACTURA" ));
            ParametrosEmpresaTable tabla = (ParametrosEmpresaTable) crit.uniqueResult();
            if(tabla != null){
                id = Integer.parseInt(tabla.getValor());
            }else{
                id = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    } 

    /**
     * Funcion con la cual busco el id de un producto por medio de su codigo
     * Externo
     *
     * @param codigoExterno
     * @return
     */
    public Integer buscaIdProductoXCodigoExterno(String codigoExterno) {
        Integer idDska = 0;
        try {
            Query query = sesion.createQuery("select p.id from ProductoEntity p where codigoExt = :codExterno ");
            query.setParameter("codExterno", codigoExterno);
            idDska = (Integer) query.uniqueResult(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idDska;        
    }
    /**
     * Funcion con la cual busca dentro de las facturas un producto en especifico
     * @param idDska
     * @return 
     */
    public ArrayList<Integer> obtieneFacturasXCodigoProducto(Integer idDska){
        ArrayList<Integer> lista = null;
        try {
            Query query = sesion.createQuery("select det.idFactura from DetProdFacturaTable det where idProducto = :idProducto ");
            query.setParameter("idProducto", idDska);
            lista = (ArrayList<Integer>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * metodo que consulta las facturas por fechas y sede
     *
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FacturaTable> consultaFacturasXSede(Date fechaInicial, Date fechaFinal, Integer idSede) {
        List<FacturaTable> facturas = null;
        try {
            initOperation();
            facturas = sesion.createQuery("from FacturaTable WHERE fecha BETWEEN :fechaInicial AND :fechaFinal AND idSede = :idSede ORDER BY id DESC ")
                    .setParameter("fechaInicial", fechaInicial).setParameter("fechaFinal", fechaFinal).setParameter("idSede", idSede).list();
            for (FacturaTable factura : facturas) {
                if (factura != null) {
                    Query query2 = sesion.createQuery("from Cliente WHERE id = :idCliente ");
                    query2.setParameter("idCliente", factura.getIdCliente());
                    factura.setCliente((Cliente) query2.uniqueResult());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facturas;
    }

    /**
     * Funcion con la cual se obtiene una factura por medio de su id
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public FacturaTable getFacturaForId(Integer id) {
        FacturaTable factura = null;
        boolean validaFecha = true;
        try {
            initOperation();
            factura = (FacturaTable) sesion.get(FacturaTable.class, id);
            if (factura != null) {
                Query query = sesion.createQuery("from DetProdFacturaTable where idFactura = :idFact ");
                query.setParameter("idFact", id);
                List<DetProdFacturaTable> aux = query.list();
                if (aux != null) {
                    if (!aux.isEmpty()) {
                        if (validaFecha) {
                            validaFecha = false;
                            factura.setFechaExacta(aux.get(0).getFecha());
                        }
                        for (DetProdFacturaTable detalle : aux) {
                            Query query3 = sesion.createQuery("from ProductoTable where id = :idDska ");
                            query3.setParameter("idDska", detalle.getIdProducto());
                            detalle.setProducto((ProductoTable) query3.uniqueResult());
                        }
                        factura.setDetalleProductos(aux);
                    }
                }
                //Obtengo las recetas
                Query query2 = sesion.createQuery("from DetReceFacturacionTable where idFact = :idFact ");
                query2.setParameter("idFact", id);
                List<DetReceFacturacionTable> aux2 = query2.list();
                if (aux2 != null) {
                    if (!aux2.isEmpty()) {
                        if (validaFecha) {
                            validaFecha = false;
                            factura.setFechaExacta(aux2.get(0).getFecha());
                        }
                        for (DetReceFacturacionTable receta : aux2) {
                            Query query4 = sesion.createQuery("from RecetaTable where id = :idRece ");
                            query4.setParameter("idRece", receta.getIdRece());
                            receta.setReceta((RecetaTable) query4.uniqueResult());
                        }
                        factura.setDetalleRecetas(aux2);
                    }
                }
            }
            if (factura != null) {
                Query query2 = sesion.createQuery("from Cliente WHERE id = :idCliente ");
                query2.setParameter("idCliente", factura.getIdCliente());
                factura.setCliente((Cliente) query2.uniqueResult());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factura;
    }

    private void initOperation() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    @Override
    public void close() throws Exception {
        try {
            if (sesion != null) {
                sesion.close();
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la sesion del cliente hibernate");
        }

    }

    public String getLlamadoFunction() {
        return llamadoFunction;
    }

    public void setLlamadoFunction(String llamadoFunction) {
        this.llamadoFunction = llamadoFunction;
    }

    /**
     * Funcion con la cual validamos si las dos listas estan nulas
     *
     * @param factura
     * @return
     */
    public boolean validaListasRecibidad(Facturacion factura) {
        if (factura.getProductos() == null & factura.getRecetas() == null) {
            return false;
        }
        return true;
    }

    /**
     * Funcion con la cual obtengo la suma de facturas que existen en el dia
     *
     * @param sede
     * @return
     */
    @SuppressWarnings("unchecked")
    public BigDecimal validaValorCaja(Integer sede) {
        BigDecimal rta = null;
        try {
            initOperation();
            rta = (BigDecimal) sesion.createQuery("SELECT SUM(valor) FROM FacturaTable where fecha = current_date and idSede =:sede").setParameter("sede", sede).uniqueResult();
            if (rta == null) {
                rta = new BigDecimal(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

    /**
     * Funcion encargada de realizar la logica para la realizacion de la
     * facturas avanzadas en el sistema
     *
     * @param facturacion
     * @return
     */
    public RespuestaFacturacion generaFacturacionAvanzada(Facturacion facturacion) {
        RespuestaFacturacion rta = new RespuestaFacturacion();
        Integer idTrans = 0;
        String msn = "Validacion de datos incorrecta";
        int iterator = 0;
        try {
            if (this.validaListasRecibidad(facturacion)) {
                boolean valida = this.validaInformacionPreFacturacion(facturacion, rta);
                if (valida) {
                    initOperation();
                    idTrans = this.getSecunceTransId();
                    if (facturacion.getProductos() != null) {
                        for (TemporalProdTable producto : facturacion.getProductos()) {
                            boolean validaItem = validaItemProducto(producto);
                            if (validaItem) {
                                Integer id = this.getMaxId();
                                producto.setId(id + 1);
                                producto.setIdTrans(idTrans);
                                sesion.save(producto);
                                tx.commit();
                                iterator++;
                            }
                        }
                    }
                    if (facturacion.getRecetas() != null) {
                        for (TemporalRecTable receta : facturacion.getRecetas()) {
                            boolean validaItem = validaItemReceta(receta);
                            if (validaItem) {
                                Integer id = this.getMaxIdReceta();
                                receta.setId(id + 1);
                                receta.setIdTrans(idTrans);
                                sesion.save(receta);
                                tx.commit();
                                iterator++;
                            }
                        }

                    }
                    if (iterator > 0) {
                        if (facturacion.isDomicilio()) {
                            //Logica para enviar que la factura es domicilio
                        }
                        msn = this.llamaFuncionFacturacionAvanzada(facturacion, idTrans);
                        rta.setRespuesta(llamadoFunction);
                        rta.setIdFacturacion(this.idFactura);
                        rta.setTrazaExcepcion(msn);
                    } else {
                        rta.setRespuesta("Error");
                        rta.setTrazaExcepcion("No se registro ningun producto valido por facturar");
                    }
                }
            } else {
                rta.setRespuesta("Error");
                rta.setTrazaExcepcion("Las dos listas no pueden estar vacias");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rta.setRespuesta("Error");
            rta.setTrazaExcepcion(e.toString());
        }
        return rta;
    }

    /**
     * Funcion encargada de realizar el llamado a la funcion que realiza toda la
     * facturacion
     *
     * @return
     */
    public String llamaFuncionFacturacionAvanzada(Facturacion objFactura, Integer idTrans) {
        String rta = "";
        List<String> response = new ArrayList<>();
        try (ReadFunction rf = new ReadFunction()) {
            rf.setNombreFuncion("FA_FACTURACION_X_PRECIO");
            rf.setNumParam(9);
            rf.addParametro("" + objFactura.getIdTius(), DataType.INT);
            rf.addParametro("" + objFactura.getIdCliente(), DataType.INT);
            rf.addParametro("" + idTrans, DataType.INT);
            rf.addParametro("" + objFactura.getIdSede(), DataType.INT);
            rf.addParametro("N/A", DataType.TEXT);
            rf.addParametro("0", DataType.INT);
            rf.addParametro("0", DataType.INT);
            rf.addParametro("" + objFactura.getIdPedido(), DataType.INT);
            rf.addParametro(objFactura.getReteFuente(), DataType.TEXT);

            rf.callFunctionJdbc();
            response = rf.getRespuestaPg();
            String respuesta = response.get(0);
            if (respuesta.indexOf("Error") >= 0) {
                respuesta = respuesta.replaceAll("Error", "");
                rta = respuesta;
                llamadoFunction = "error";
            } else {
                String[] aux = respuesta.split("-");
                llamadoFunction = aux[0];
                idFactura = aux[1];
            }
        } catch (Exception e) {
            llamadoFunction = "error";
            rta = e.toString();
            e.printStackTrace();
        }
        return rta;
    }

}
