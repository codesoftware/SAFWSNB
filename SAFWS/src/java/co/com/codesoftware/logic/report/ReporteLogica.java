/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.logic.report;

import co.com.codesoftware.persistence.HibernateUtil;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author ACER
 */
public class ReporteLogica implements AutoCloseable {

    private Connection con;
    private Session session;
    private String rutaRepoServ;

    public ReporteLogica() {
        try {
            Context initCtx = new InitialContext();
            this.rutaRepoServ = (String) initCtx.lookup("java:comp/env/rutaReports");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcion con la cual se realiza un pdf con la factura solicitada teniendo
     * en cuenta el id de facturacion
     *
     * @param fact_fact
     * @return
     */
    public synchronized String generaPdfFactura(String fact_fact) {
        String documento = null;
        try {
            documento = generaJasper(fact_fact);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return documento;
    }

    /**
     * Funcion que genera el pdf del pedido
     *
     * @param pedi_pedi
     * @return
     */
    public synchronized String generaPdfPedidos(Integer pedi_pedi) {
        String pedido = pedi_pedi.toString();
        String documento = null;
        try {
            documento = generaJasperPedido(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documento;
    }

    /**
     * Funcion la cual llama al jasper para la creacion del pdf
     *
     * @param pdf
     * @return
     */
    private synchronized String generaJasper(String fact_fact) {
        String documento = null;
        try {
            this.conectionJDBC();
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("fact_fact", fact_fact);
            properties.put("SUBREPORT_DIR", rutaRepoServ);
            String imagenLogo = rutaRepoServ + "logo.png";
            properties.put("RUTA_LOGOFACT", imagenLogo);
            //JasperReport jasperReport = (JasperReport) JRLoader.loadObject("D:\\proyectos\\codeSoftware\\SAFWSNB\\SAFWS\\src\\java\\co\\com\\codesoftware\\logic\\report\\Factura.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(rutaRepoServ + "Factura.jasper");
            JasperPrint print = JasperFillManager.fillReport(jasperReport, properties, con);
            JasperExportManager.exportReportToPdfFile(print, rutaRepoServ + "prueba.pdf");
            CodificaBase64 codifica64 = new CodificaBase64();
            boolean codifico = codifica64.codificacionDocumento(rutaRepoServ + "prueba.pdf");
            if (codifico) {
                documento = codifica64.getDocumento();
                codifica64.setDocumento(null);
                File file = new File(rutaRepoServ + "prueba.pdf");
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return documento;
    }

    /**
     *
     * @param fact_fact
     * @return
     */
    private synchronized String generaJasperPedido(String pedi_pedi) {
        String documento = null;
        try {
            this.conectionJDBC();
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("IDPEDIDO", pedi_pedi);
            //JasperReport jasperReport = (JasperReport) JRLoader.loadObject("D:\\proyectos\\codeSoftware\\SAFWSNB\\SAFWS\\src\\java\\co\\com\\codesoftware\\logic\\report\\Factura.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(rutaRepoServ + "Pedido.jasper");
            JasperPrint print = JasperFillManager.fillReport(jasperReport, properties, con);
            JasperExportManager.exportReportToPdfFile(print, rutaRepoServ + "Pedido_" + pedi_pedi + ".pdf");
            CodificaBase64 codifica64 = new CodificaBase64();
            boolean codifico = codifica64.codificacionDocumento(rutaRepoServ + "Pedido_" + pedi_pedi + ".pdf");
            if (codifico) {
                documento = codifica64.getDocumento();
                codifica64.setDocumento(null);
                File file = new File(rutaRepoServ + "Pedido_" + pedi_pedi + ".pdf");
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documento;
    }
    
    /**
     * Funcion la cual llama al jasper para la creacion del pdf de la cotizacion
     *
     * @param pedido
     * @return
     */
    public synchronized String generaJasperCotizacion(Integer pedido) {
        String documento = null;
        try {
            this.conectionJDBC();
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("pedi_pedi", pedido);
            properties.put("rutaImagen", rutaRepoServ);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(rutaRepoServ + "CotizacionCarta.jasper");
            JasperPrint print = JasperFillManager.fillReport(jasperReport, properties, con);
            JasperExportManager.exportReportToPdfFile(print, rutaRepoServ + "cotizacion_"+pedido+".pdf");
            CodificaBase64 codifica64 = new CodificaBase64();
            boolean codifico = codifica64.codificacionDocumento(rutaRepoServ + "cotizacion_"+pedido+".pdf");
            if (codifico) {
                documento = codifica64.getDocumento();
                codifica64.setDocumento(null);
                File file = new File(rutaRepoServ + "cotizacion_"+pedido+".pdf");
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documento;
    }

    /**
     * Funcion que toma el objeto conexion de Hibernate y lo utiliza como un
     * objeto conexion de jdbc
     *
     * @return
     */
    private boolean conectionJDBC() {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            SessionImplementor miSessionImplementor = (SessionImplementor) session;
            this.con = miSessionImplementor.connection();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void close() throws Exception {
        try {
            this.session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
