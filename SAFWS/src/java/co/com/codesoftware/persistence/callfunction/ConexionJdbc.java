package co.com.codesoftware.persistence.callfunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class ConexionJdbc implements AutoCloseable {

    private static ConexionJdbc conexionJdbc;
    private Connection          conexion;
    private String              data_base;
    private String              host;
    private String              port;
    private String              user;
    private String              pass;

    /**
     * Constructor de la clase en la cual obtengo el nombre del pool
     * parametrizado en el sistema
     */
    private ConexionJdbc() {
        ResourceBundle rb = ResourceBundle.getBundle("co.com.codesoftware.properties.baseProperties");
        data_base = rb.getString("DATA_BASE");
        host = rb.getString("HOST");
        port = rb.getString("PORT");
        user = rb.getString("USER");
        pass = rb.getString("PASS");
    }

    /**
     * Funcion en la cual obtenemos la instancia del objeto ya que esta creado
     * por medio de un patron singleton
     * 
     * @return
     */
    public static ConexionJdbc getInstance() {
        if (conexionJdbc == null) {
            conexionJdbc = new ConexionJdbc();
        }
        return conexionJdbc;

    }

    /**
     * Funcion con la cual generamos y obtnemos la conexion
     * 
     * @return
     */
    public Connection conexion() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.data_base;
            conexion = DriverManager.getConnection(url, this.user, this.pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.conexion;
    }

    /**
     * Funcion con la cual cierro la conexion
     */
    @Override
    public void close() throws Exception {
        this.conexion.close();
    }

}
