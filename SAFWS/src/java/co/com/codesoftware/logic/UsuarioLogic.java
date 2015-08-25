package co.com.codesoftware.logic;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.UsuarioTable;
import co.com.codesoftware.persistence.enumeration.DataType;
import co.com.codesoftware.utilities.ReadFunction;
import java.util.ArrayList;

public class UsuarioLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    @SuppressWarnings("unchecked")
    public List<UsuarioTable> obtieneUsuariosApp() {
        List<UsuarioTable> usuarios = null;
        try {
            this.initOperation();
            usuarios = sesion.createQuery("from UsuarioTable ").list();
            for (UsuarioTable usuario : usuarios) {
                Query query = sesion.createQuery("from PersonaUsuarioTable  WHERE id = :idPersona");
                query.setParameter("idPersona", usuario.getIdPersona());
                usuario.setPersona(query.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @SuppressWarnings("unchecked")
    public UsuarioTable obtieneUsuarioForUser(String user) {
        UsuarioTable usuario = null;
        try {
            this.initOperation();
            Query query1 = sesion.createQuery("from UsuarioTable WHERE usuario = :user");
            query1.setParameter("user", user);
            usuario = (UsuarioTable) query1.uniqueResult();
            if (usuario != null) {
                Query query = sesion.createQuery("from PersonaUsuarioTable  WHERE id = :idPersona");
                query.setParameter("idPersona", usuario.getIdPersona());
                usuario.setPersona(query.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
    
    public boolean cambioContrasenaObligatorio(UsuarioTable usuario){
        boolean rta = true;
        List<String> response = new ArrayList<>();
        try(ReadFunction rf = new ReadFunction()) {
            rf.setNombreFuncion("US_FCAMBIO_CLAVE");
            rf.setNumParam(2);
            rf.addParametro(usuario.getUsuario(), DataType.TEXT);
            rf.addParametro(usuario.getPassword(), DataType.TEXT);
            rf.callFunctionJdbc();
            response = rf.getRespuestaPg();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rta;
    }

    public UsuarioTable obtieneUsuarioXId(Long tius_tius) {
        UsuarioTable usuario = null;
        try {
            initOperation();
            usuario = (UsuarioTable) sesion.get(UsuarioTable.class, tius_tius);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    private void initOperation() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    @Override
    public void close() throws Exception {
        tx.commit();
        sesion.close();
    }

}
