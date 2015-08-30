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
import co.com.codesoftware.persistence.entites.tables.ProductoTable;
import co.com.codesoftware.persistence.entites.tables.RecetaTable;
import co.com.codesoftware.persistence.entites.tables.UsuarioTable;
import co.com.codesoftware.persistence.entities.facturacion.tables.TemporalProdTable;
import co.com.codesoftware.persistence.entities.facturacion.tables.TemporalRecTable;
import co.com.codesoftware.persistence.enumeration.DataType;
import co.com.codesoftware.utilities.ReadFunction;

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
            e.printStackTrace();
        }
        return rta;
    }

    /**
     * Funcion la cual obtendra todas las facturas que se encuentren registradas
     * en el sistema
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FacturaTable> consultaFacturas() {
        List<FacturaTable> facturas = null;
        try {
            initOperation();
            facturas = sesion.createQuery("from FacturaTable ORDER BY id DESC ").list();
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
        try {
            initOperation();
            factura = (FacturaTable) sesion.get(FacturaTable.class, id);
            if (factura != null) {
                Query query = sesion.createQuery("from DetProdFacturaTable where idFactura = :idFact ");
                query.setParameter("idFact", id);
                List<DetProdFacturaTable> aux = query.list();
                if (aux != null) {
                    for (DetProdFacturaTable detalle : aux) {
                        Query query3 = sesion.createQuery("from ProductoTable where id = :idDska ");
                        query3.setParameter("idDska", detalle.getIdProducto());
                        detalle.setProducto((ProductoTable) query3.uniqueResult());
                    }
                    factura.setDetalleProductos(aux);
                }
                //Obtengo las recetas
                Query query2 = sesion.createQuery("from DetReceFacturacionTable where idFact = :idFact ");
                query2.setParameter("idFact", id);
                List<DetReceFacturacionTable> aux2 = query2.list();
                if (aux2 != null) {
                    for (DetReceFacturacionTable receta : aux2) {
                        Query query4 = sesion.createQuery("from RecetaTable where id = :idRece ");
                        query4.setParameter("idRece", receta.getIdRece());
                        receta.setReceta((RecetaTable) query4.uniqueResult());
                    }
                    factura.setDetalleRecetas(aux2);
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

}
