package co.com.codesoftware.utilities;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.com.codesoftware.persistence.callfunction.ConexionJdbc;
import co.com.codesoftware.persistence.entites.ParamFunction;
import co.com.codesoftware.persistence.enumeration.DataType;

public class ReadFunction implements AutoCloseable {

    private static Connection        con;
    private String                   base;
    private int                      numParam;
    private String                   nombreFuncion;
    private ArrayList<ParamFunction> parameters;
    private String                   respuesta;
    private String                   rtaPg;
    private List<String>             respuestaPg;

    /**
     * Funcion en la cual se adicionan parametros a la lista
     * 
     * @param value
     * @param dataType
     * @return
     */
    public boolean addParametro(String value, DataType dataType) {
        ParamFunction param = new ParamFunction();
        param.setDataType(dataType);
        param.setName(value);
        if (parameters == null) {
            parameters = new ArrayList<ParamFunction>();
        }
        parameters.add(param);
        return true;
    }

    /**
     * Funcion con el cual creo el String base para hacer el llamado de la base
     * de datos
     * 
     * @return
     */
    public boolean createBase() {
        try {
            this.base = "select ";
            this.base += this.nombreFuncion;
            this.base += "(";
            for (int i = 0; i < numParam; i++) {
                if (i == 0) {
                    this.base += " ? ";
                } else {
                    this.base += ", ? ";
                }
            }
            this.base += ")";
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * Funcion con la cual se realiza el llamado
     * @return 
     */
    public boolean callFunctionJdbc() {
        try (ConexionJdbc objConexion = ConexionJdbc.getInstance()) {
            this.createBase();
            Connection conn = objConexion.conexion();
            if (conn != null) {

                PreparedStatement ps = conn.prepareStatement(this.base);
                int i = 1;
                for (ParamFunction parametro : this.parameters) {
                    if (parametro.getDataType().toString().equalsIgnoreCase("TEXT")) {
                        ps.setString(i, parametro.getName());
                    } else if (parametro.getDataType().toString().equalsIgnoreCase("INT")) {
                        ps.setInt(i, Integer.parseInt(parametro.getName()));
                    } else if (parametro.getDataType().toString().equalsIgnoreCase("NUMERIC")) {
                        ps.setDouble(i, Double.parseDouble(parametro.getName()));
                    } else if (parametro.getDataType().toString().equalsIgnoreCase("BIGDECIMAL")) {
                        ps.setBigDecimal(i, new BigDecimal(parametro.getName()));
                    }
                    i++;
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rtaPg = rs.getString(1);
                }
                this.ListResponsePg();
                ps.close();
                conn.close();
            } else {
                respuesta = "Error al crear la conexion ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void ListResponsePg() {
        try {
            if (rtaPg.indexOf("Error") >= 0) {
                System.out.println("Postgres envio un error " + rtaPg);
                if (respuestaPg == null) {
                    respuestaPg = new ArrayList<>();
                }
                respuestaPg.add(rtaPg);
            } else {
                rtaPg = rtaPg.replaceAll("\\(", "");
                rtaPg = rtaPg.replaceAll("\\)", "");
                respuestaPg = Arrays.asList(rtaPg.split(","));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para cerrar las conexiones
     */
    public void close() throws Exception {
        if (con != null) {
            con.close();
        }
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public int getNumParam() {
        return numParam;
    }

    public void setNumParam(int numParam) {
        this.numParam = numParam;
    }

    public String getNombreFuncion() {
        return nombreFuncion;
    }

    public void setNombreFuncion(String nombreFuncion) {
        this.nombreFuncion = nombreFuncion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public List<String> getRespuestaPg() {
        return respuestaPg;
    }

    public void setRespuestaPg(List<String> respuestaPg) {
        this.respuestaPg = respuestaPg;
    }

}
