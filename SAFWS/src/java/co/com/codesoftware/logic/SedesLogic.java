package co.com.codesoftware.logic;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import co.com.codesoftware.persistence.HibernateUtil;
import co.com.codesoftware.persistence.entites.tables.Sede;

public class SedesLogic implements AutoCloseable {
    private Session sesion; 
    private Transaction tx;  

	/**
	 * Metodo que mapea la lista de todas las sedes
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Sede> sedeList() throws Exception{
		List<Sede> sedes = null;
		try {
		initOperation();
		sedes =sesion.createQuery("from Sede").list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return sedes;
	}
	
	/**
	 * Funcion 	que incializa la sesion 
	 * @throws HibernateException
	 */
	 private void initOperation() throws HibernateException 
	    { 
	        sesion = HibernateUtil.getSessionFactory().openSession(); 
	        tx = sesion.beginTransaction(); 
	    }

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
	    tx.commit();
		sesion.close();		
	}  

	
}
