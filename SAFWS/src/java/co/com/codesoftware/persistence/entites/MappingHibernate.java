package co.com.codesoftware.persistence.entites;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class MappingHibernate {

	private Session session;
	private Transaction tx;
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Transaction getTx() {
		return tx;
	}
	public void setTx(Transaction tx) {
		this.tx = tx;
	}
	
}
