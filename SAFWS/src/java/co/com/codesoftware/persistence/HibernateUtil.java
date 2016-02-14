package co.com.codesoftware.persistence;

import javax.naming.Context;
import javax.naming.InitialContext;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();
    private static String config;

    private static SessionFactory buildSessionFactory() {
        try {
            if (sessionFactory == null) {
                config = obtieneParametroContext();
                Configuration configuration = new Configuration().configure(HibernateUtil.class.getResource(config));
                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
                serviceRegistryBuilder.applySettings(configuration.getProperties());
                ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static void generaNuloSesion(){
        sessionFactory = null;
        sessionFactory = buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public static String obtieneParametroContext() {
        
        String parametro = "";
        try {
           Context initCtx = new InitialContext();
           String envCtx = (String) initCtx.lookup("java:comp/env/Hibernate");
            parametro = envCtx;
            System.out.println("parametro" + parametro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parametro;
    }

}
