package co.com.codesoftware.logic;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.Cliente;

public class ClienteLogic implements AutoCloseable {
	private Session sesion;
	private Transaction tx;

	@SuppressWarnings("unchecked")
	public List<Cliente> getListCliente(List<String> param) throws HibernateException {
		List<Cliente> clientes = null;
		try {
			initOperation();
			clientes = sesion.createQuery("from Cliente").list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			//sesion.close();
		}
		return clientes;
	}

	/**
	 * Metodo con el cual obtengo un cliente por su Id
	 * 
	 * @param id
	 * @return
	 */
	public Cliente getCliente(Long id) {
		Cliente cliente = new Cliente();
		try {
			initOperation();
			cliente = (Cliente) sesion.get(Cliente.class, id);
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return cliente;
	}

	/**
	 * Metodo con el cual actualizo un cliente
	 * 
	 * @param cliente
	 * @return
	 */
	public boolean updateCliente(Cliente cliente) {
		try {
			initOperation();
			sesion.update(cliente);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			ExceptionHibernate(e);
			return false;
		} finally {
			//sesion.close();
		}
	}

	/**
	 * Metodo que crea una entidad de cliente
	 * 
	 * @param cliente
	 * @return
	 */

	public Long createCliente(Cliente cliente) {
		try {
			initOperation();
			if (cliente.getId() == null) {
				cliente.setId(getMaxId() + 1);
			}
			sesion.save(cliente);
			tx.commit();
			return cliente.getId();
		} catch (HibernateException e) {
			ExceptionHibernate(e);
			return null;
		} finally {
			//sesion.close();
		}
	}

	/**
	 * Funcion encargade de buscar un cliente por su Cedula
	 * 
	 * @param clien_clien
	 * @return
	 */
	public Cliente obtieneclienteXCedula(Long clien_cedula) {
		Cliente cliente = null;
		try {
			initOperation();
			Query query = sesion.createQuery("FROM Cliente WHERE cedula = :cedula ");
			query.setParameter("cedula", clien_cedula);
			cliente = (Cliente) query.uniqueResult();
			if(cliente == null && clien_cedula == 0){
				cliente = new Cliente();
				cliente.setId(getMaxId() + 1);
				cliente.setApellidos("generico");
				cliente.setCedula("");
				cliente.setCorreo("generico@generico.com");
				cliente.setNombres("generico");
				cliente.setTelefono("1234567");
				sesion.save(cliente);
				tx.commit();
				obtieneclienteXCedula(new Long(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return cliente;
	}

	private void initOperation() throws HibernateException {
		sesion = HibernateUtil.getSessionFactory().openSession();
		tx = sesion.beginTransaction();
	}

	private void ExceptionHibernate(HibernateException he) throws HibernateException {
		tx.rollback();
		throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
	}

	/**
	 * Metodo que retorna el valor maximo de la columna
	 * 
	 * @return
	 */
	public Long getMaxId() {
		Long result;
		try {
			result = (Long) sesion.createQuery("select coalesce(max(id),0) from Cliente").uniqueResult();
		} catch (Exception e) {
			result = null;
		}
		return result;

	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		sesion.close();
	}

}
