package co.com.codesoftware.logic;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.PerfilTable;
import co.com.codesoftware.persistence.entites.tables.UsuarioTable;
import co.com.codesoftware.persistence.entity.usuario.TipoUsuarioEntity;
import co.com.codesoftware.persistence.enumeration.DataType;
import co.com.codesoftware.utilities.ReadFunction;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class UsuarioLogic implements AutoCloseable {

    private Session sesion;
    private Transaction tx;

    /**
     * Funcion con la cual se obtienen todos los usuarios de la aplicacion
     *
     * @return
     */
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

    /**
     * Funcion con la cual obtenemos un usuario por medio de su usuario
     *
     * @param user
     * @return
     */
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

    /**
     * Funcion con la cual se puede cambiar la contraseña del usuario
     *
     * @param usuario
     * @return
     */
    public boolean cambioContrasenaObligatorio(UsuarioTable usuario) {
        boolean rta = true;
        List<String> response = new ArrayList<>();
        try (ReadFunction rf = new ReadFunction()) {
            rf.setNombreFuncion("US_FCAMBIO_CLAVE");
            rf.setNumParam(2);
            rf.addParametro(usuario.getUsuario(), DataType.TEXT);
            rf.addParametro(usuario.getPassword(), DataType.TEXT);
            rf.callFunctionJdbc();
            response = rf.getRespuestaPg();
            if ("Ok".equalsIgnoreCase(response.get(0))) {
                rta = true;
            } else {
                rta = false;
            }
        } catch (Exception e) {
            rta = false;
            e.printStackTrace();
        }
        return rta;
    }

    /**
     * Funcion con la cual obtenemos el usuario por medio de su id
     *
     * @param tius_tius
     * @return
     */
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

    /**
     * Funcion con la cual evaluo si el usuario puede facturar en el sistema
     *
     * @param idUsuario
     * @return
     */
    public boolean validaUsuarioFacturador(Long idUsuario) {
        try {
            UsuarioTable usuario = obtieneUsuarioXId(idUsuario);
            if (usuario != null) {
                initOperation();
                Query query = sesion.createQuery("from PerfilTable where id = :idPerfil ");
                query.setParameter("idPerfil", usuario.getIdPerfil());
                PerfilTable perfil = (PerfilTable) query.uniqueResult();
                if (perfil != null) {
                    return perfil.getPermisos().contains(".FcCr1.");
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * funcion que consulta los permisos por usuario
     *
     * @param usuario
     * @return
     */
    public TipoUsuarioEntity consultaPermisosUsuario(String usuario) {
        TipoUsuarioEntity respuesta = new TipoUsuarioEntity();
        try {

            initOperation();
            Criteria crit = sesion.createCriteria(TipoUsuarioEntity.class)
                    .add(Restrictions.eq("usuario", usuario));
            respuesta = (TipoUsuarioEntity) crit.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    private void initOperation() {
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            if (tx == null) {
                tx = sesion.beginTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            HibernateUtil.generaNuloSesion();
            try {
                sesion = HibernateUtil.getSessionFactory().openSession();
                if (tx == null) {
                    tx = sesion.beginTransaction();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    @Override
    public void close() throws Exception {
        if (tx != null) {
            tx.commit();
        }
        if (sesion != null) {
            sesion.close();
        }
    }

}
