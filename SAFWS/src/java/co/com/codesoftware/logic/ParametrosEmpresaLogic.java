package co.com.codesoftware.logic;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.ParametrosEmpresaTable;

public class ParametrosEmpresaLogic implements AutoCloseable {
	private Session sesion;
	private Transaction tx;

	/**
	 * Funcion con la cual obtengo todos los parametros empresariales de la
	 * empresa
	 * 
	 * @return
	 */
	public List<ParametrosEmpresaTable> obtienePrametrosEmpresa() {
		List<ParametrosEmpresaTable> rta = null;
		try {
			initOperation();
			rta = sesion.createQuery("FROM ParametrosEmpresaTable ").list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rta;
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
