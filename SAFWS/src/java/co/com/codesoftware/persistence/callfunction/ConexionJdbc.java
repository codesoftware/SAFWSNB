package co.com.codesoftware.persistence.callfunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConexionJdbc implements AutoCloseable {

    private static ConexionJdbc conexionJdbc;
    private Connection conexion;
    private String nombrePool;
    private Context context;
    private DataSource ds;

    /**
     * Constructor de la clase en la cual obtengo el nombre del pool
     * parametrizado en el sistema
     */
    private ConexionJdbc() {
        ResourceBundle rb = ResourceBundle.getBundle("co.com.codesoftware.properties.baseProperties");
        nombrePool = rb.getString("NOMBRE_POOL");
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
            context = new InitialContext();
            ds = (DataSource) context.lookup("java:/comp/env/jdbc/" + nombrePool);
            conexion = ds.getConnection();
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
