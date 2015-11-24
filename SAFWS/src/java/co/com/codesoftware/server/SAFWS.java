package co.com.codesoftware.server;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.WebServiceContext;

import co.com.codesoftware.logic.ClienteLogic;
import co.com.codesoftware.logic.FacturacionLogic;
import co.com.codesoftware.logic.LogicLogin;
import co.com.codesoftware.logic.PantallaPrincipalFactLogic;
import co.com.codesoftware.logic.ParametrosEmpresaLogic;
import co.com.codesoftware.logic.ProductosGenericosLogic;
import co.com.codesoftware.logic.ProductsLogic;
import co.com.codesoftware.logic.RecetaLogic;
import co.com.codesoftware.logic.report.ReporteLogica;
import co.com.codesoftware.logic.SedesLogic;
import co.com.codesoftware.logic.UsuarioLogic;
import co.com.codesoftware.logic.productos.PedidosLogic;
import co.com.codesoftware.persistence.entites.facturacion.FacturaTable;
import co.com.codesoftware.persistence.entites.facturacion.Facturacion;
import co.com.codesoftware.persistence.entites.facturacion.ProductoGenericoEntity;
import co.com.codesoftware.persistence.entites.facturacion.RespuestaFacturacion;
import co.com.codesoftware.persistence.entites.tables.Cliente;
import co.com.codesoftware.persistence.entites.tables.PantallaPrincipalFacTable;
import co.com.codesoftware.persistence.entites.tables.ParametrosEmpresaTable;
import co.com.codesoftware.persistence.entites.tables.ProductoTable;
import co.com.codesoftware.persistence.entites.tables.RecetaTable;
import co.com.codesoftware.persistence.entites.tables.Sede;
import co.com.codesoftware.persistence.entites.tables.UsuarioTable;
import co.com.codesoftware.persistence.entity.administracion.RespuestaEntity;
import co.com.codesoftware.persistence.entity.productos.PedidoEntity;
import co.com.codesoftware.persistence.entity.productos.PedidoProductoEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@WebService(name = "SAFWS")
public class SAFWS {

    @Resource
    WebServiceContext context;

    /**
     * Metodo del webservice encargado de invocar la logica para el logueo de
     * los usuarios
     *
     * @param nombre
     * @param password
     * @return
     */
    @WebMethod
    public List<String> login(@XmlElement(required = true) @WebParam(name = "nombre") String nombre, @XmlElement(required = true) @WebParam(name = "password") String password) {
        LogicLogin logic = new LogicLogin();
        return logic.login(nombre, password);
    }

    /**
     * Metodo que consulta todas las sedes
     *
     * @return
     */
    @WebMethod
    public List<Sede> getSedes() {
        try (SedesLogic logic = new SedesLogic()) {
            return logic.sedeList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Metodo con el cual consulto todos los clientes del sistema
     *
     * @return
     */
    @WebMethod
    @WebResult(name = "Cliente", partName = "Cliente")
    public List<Cliente> getClientes() {
        try (ClienteLogic logic = new ClienteLogic()) {
            return logic.getListCliente(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Funcion con la cual obtengo un cliente por su cedula
     *
     * @param cedula
     * @return
     */
    @WebMethod
    @WebResult(name = "Cliente", partName = "Cliente")
    public Cliente getClienteXCedula(@XmlElement(required = true) @WebParam(name = "cedula") Long cedula) {
        try {
            ClienteLogic logic = new ClienteLogic();
            return logic.obtieneclienteXCedula(cedula);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo con el cual adiciono un cliente al sistema
     *
     * @param cliente
     * @return
     */
    @WebMethod
    public Long addCliente(@WebParam(name = "Cliente") Cliente cliente) {
        try (ClienteLogic logic = new ClienteLogic()) {
            return logic.createCliente(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Funcion con la cual actualizo un cliente en especifico
     *
     * @param cliente
     * @return
     */
    @WebMethod
    public boolean updateCliente(@WebParam(name = "Cliente") Cliente cliente) {
        try (ClienteLogic logic = new ClienteLogic()) {
            return logic.updateCliente(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo con el cual obtengo todas las recetas del sistema
     *
     * @param sede
     * @return
     */
    @WebMethod
    @WebResult(name = "RecetaTable", partName = "RecetaTable")
    public List<RecetaTable> getRecetas(@WebParam(name = "sede_sede") Integer sede) {
        try (RecetaLogic logic = new RecetaLogic()) {
            return logic.getRecetas(null, sede);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo con el cual se obtiene los productos por sede
     *
     * @param sede_sede
     * @return
     */
    @WebMethod
    @WebResult(name = "ProductoTable", partName = "ProductoTable")
    public List<ProductoTable> getProducts(@WebParam(name = "sede_sede") Integer sede_sede) {
        try (ProductsLogic logic = new ProductsLogic()) {
            return logic.buscaProductos(sede_sede);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo con el cual se obtiene un producto en especifico basandose en su
     * codigo unico
     *
     * @param dska_cod
     * @param sede_sede
     * @return
     */
    @WebMethod
    @WebResult(name = "ProductoTable", partName = "ProductoTable")
    public ProductoTable getProductForCode(@WebParam(name = "dska_cod") String dska_cod, @WebParam(name = "sede_sede") Integer sede_sede) {
        try (ProductsLogic logic = new ProductsLogic()) {
            return logic.findProductForCode(dska_cod, sede_sede);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo con el cual obtenemos productos los productos que se visualizaran
     * en la pantalla principal de facturacion
     *
     * @param sede_sede
     * @return
     */
    @WebMethod
    @WebResult(name = "PantallaPrincipalFactTable", partName = "PantallaPrincipalFactTable")
    public List<PantallaPrincipalFacTable> getProductPrincipalScreen(@WebParam(name = "sede_sede") Integer sede_sede) {
        try (PantallaPrincipalFactLogic objLogic = new PantallaPrincipalFactLogic()) {
            return objLogic.obtieneProductosPantalla(sede_sede);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Funcion con el cual obtemos las recetas que se visualizaran en la
     * pantalla principal de facturacion
     *
     * @param sede_sede
     * @return
     */
    @WebMethod
    @WebResult(name = "PantallaPrincipalFactTable", partName = "PantallaPrincipalFactTable")
    public List<PantallaPrincipalFacTable> getDishesPrincipalScreen(@WebParam(name = "sede_sede") Integer sede_sede) {
        try (PantallaPrincipalFactLogic objLogic = new PantallaPrincipalFactLogic()) {
            return objLogic.obtieneRecetasPantalla(sede_sede);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Funcion con la cual se obtienen todos los usuarios de la aplicacion
     *
     * @return
     */
    @WebResult(name = "UsuarioTable", partName = "UsuarioTable")
    public List<UsuarioTable> getUsers() {
        try (UsuarioLogic logic = new UsuarioLogic()) {
            return logic.obtieneUsuariosApp();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Funcion con la cual obtenemos los datos de un usuario en especifico por
     * medio de su usuarioF||
     *
     * @param tius_usuario
     * @return
     */
    @WebResult(name = "UsuarioTable", partName = "UsuarioTabla")
    public UsuarioTable getUserForUser(@XmlElement(required = true) @WebParam(name = "tius_usuario") String tius_usuario) {
        try (UsuarioLogic logic = new UsuarioLogic()) {
            return logic.obtieneUsuarioForUser(tius_usuario.toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Funcion con la cual buscamos las existencia de un producto en una sede
     * por medio de su id
     *
     * @param sede_sede
     * @param dska_dska
     * @return
     */
    @WebResult(name = "existencia", partName = "existencia")
    public String findExistsForSede(@XmlElement(required = true) @WebParam(name = "sede_sede") String sede_sede, @XmlElement(required = true) @WebParam(name = "dska_dska") String dska_dska) {
        try (ProductsLogic logic = new ProductsLogic()) {
            return logic.obtieneExistenciasXSede(dska_dska, sede_sede);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "-1";
        }
    }

    @WebMethod(operationName = "facturar")
    @WebResult(name = "respuestaFacturacion")
    public RespuestaFacturacion facturar(@XmlElement(required = true) @WebParam(name = "Facturacion") Facturacion facturacion) {
        RespuestaFacturacion rta = null;
        try (FacturacionLogic objLogic = new FacturacionLogic()) {
            rta = objLogic.generaFacturacion(facturacion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

    /**
     * Metodo con el cual obtengo todas las facturas registradas en el sistema
     * por sede y en un rango de fechas
     *
     * @param idSede
     * @param fInicial
     * @param fFinal
     * @return
     */
    @WebMethod(operationName = "getFacturas")
    @WebResult(name = "listaFacturas")
    public List<FacturaTable> getFacturas(@XmlElement(required = true) @WebParam(name = "fInicial") Date fInicial, @XmlElement(required = true) @WebParam(name = "fFinal") Date fFinal) {
        try (FacturacionLogic objLogic = new FacturacionLogic()) {
            return objLogic.consultaFacturas(fInicial, fFinal);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Metodo con el cual obtengo una factura teniendo como referencia su id
     * unico
     *
     * @param id
     * @return
     */
    @WebMethod(operationName = "getFacturaForId")
    @WebResult(name = "FacturaTable")
    public FacturaTable getFacturaForId(@XmlElement(required = true) @WebParam(name = "idFactura") Integer idFactura) {
        try (FacturacionLogic objLogic = new FacturacionLogic()) {
            return objLogic.getFacturaForId(idFactura);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Metodo con el cual obtengo todos los parametros empresariales de la
     * compañia
     *
     * @return
     */
    @WebMethod(operationName = "getParamsEmpresa")
    @WebResult(name = "parametros")
    public List<ParametrosEmpresaTable> getParamsEmpresa() {
        try (ParametrosEmpresaLogic objLogic = new ParametrosEmpresaLogic()) {
            return objLogic.obtienePrametrosEmpresa();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Metodo encargado de realizar el cambio obligatorio de contraseña
     *
     * @param usuario
     * @return
     */
    @WebMethod(operationName = "cambiaClaveUsuario")
    @WebResult(name = "respuesta")
    public boolean cambiaClaveUsuario(UsuarioTable usuario) {
        try (UsuarioLogic logic = new UsuarioLogic()) {
            boolean rta = logic.cambioContrasenaObligatorio(usuario);
            return rta;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo con el cual obtengo la receta por medio de su codigo unico
     *
     * @param rece_codigo
     * @param sede_sede
     * @return
     */
    @WebMethod(operationName = "getRecetaForcode")
    @WebResult(name = "RecetaTable")
    public RecetaTable getRecetaForcode(@XmlElement(required = true) @WebParam(name = "rece_codigo") String rece_codigo, @XmlElement(required = true) @WebParam(name = "rece_sede") Integer sede_sede) {
        try (RecetaLogic logic = new RecetaLogic()) {
            return logic.getRecetaForcode(rece_codigo, sede_sede);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Funcion utilizada para validar el permiso de facturacion
     *
     * @param idTius
     * @return
     */
    @WebMethod(operationName = "validaUsuarioFacturador")
    @WebResult(name = "valida")
    public boolean validaUsuarioFacturador(@XmlElement(required = true) @WebParam(name = "tius_tius") Long idTius) {
        try (UsuarioLogic logic = new UsuarioLogic()) {
            return logic.validaUsuarioFacturador(idTius);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo el cual busca los productos y recetas parametrizados en el sistema
     *
     * @param sede_sede
     * @return
     */
    @WebMethod(operationName = "findProductosAndDishes")
    @WebResult(name = "ListGeneric")
    public List<ProductoGenericoEntity> findProductosAndDishes(Integer sede_sede) {
        try {
            ProductosGenericosLogic logic = new ProductosGenericosLogic();
            return logic.buscaProductosRecetas(sede_sede);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Funcion con la cual se genera un pdf con la factura apartir de su id
     *
     * @param fact_fact
     * @return
     */
    @WebMethod(operationName = "findBillForId")
    @WebResult(name = "imagen")
    public String findBillForId(@XmlElement(required = true) @WebParam(name = "fact_fact")String fact_fact) {
        String imagen = null;
        try {
            ReporteLogica logica = new ReporteLogica();
            imagen = logica.generaPdfFactura(fact_fact);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagen;
    }
    /**
     * Metodo que consulta el valor de la facturacion por sede
     * @param sede
     * @return 
     */
    @WebMethod(operationName = "searchBoxNow")
    @WebResult(name = "cantidad")
    public BigDecimal searchBoxNow(@XmlElement(required = true) @WebParam(name = "sede")Integer sede){
        FacturacionLogic logic = new FacturacionLogic();
        return logic.validaValorCaja(sede);
    }
    
    
    /**
     * metodo que inserta un pedido al sistema
     *
     * @param parametros
     * @return
     */
    @WebMethod(operationName = "insertaPedidosProducto")
    public RespuestaEntity insertaPedidosProducto(PedidoEntity parametros) {
        RespuestaEntity respuesta = new RespuestaEntity();
        try (PedidosLogic logic = new PedidosLogic()) {
            respuesta = logic.insertaPedidos(parametros);

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setCodigoRespuesta(0);
            respuesta.setDescripcionRespuesta(e.getMessage());
            respuesta.setMensajeRespuesta("ERROR" + e.getMessage());
        }
        return respuesta;
    }

    @WebMethod(operationName = "insertaProductosXPedido")
    public RespuestaEntity insertaProductoXPedido(ArrayList<PedidoProductoEntity> productos, Integer idPedido) {
        RespuestaEntity respuesta = new RespuestaEntity();
        try (PedidosLogic logic = new PedidosLogic()) {
            respuesta = logic.insertaProductos(productos, idPedido);

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.setCodigoRespuesta(0);
            respuesta.setDescripcionRespuesta(e.getMessage());
            respuesta.setMensajeRespuesta("ERROR" + e.getMessage());
        }
        return respuesta;

    }

}
