package dbService;

import dbService.DAO.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;


public class DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";

    private final SessionFactory sessionFactory;

    public DBService(){
        Configuration configuration = getMySqlConfiguration();// getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
        UsersDAO usersDAO = new UsersDAO(sessionFactory.openSession());
    }

    @SuppressWarnings("UnusedDeclaration")
    private Configuration getMySqlConfiguration(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/test?useTimezone=true&serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username","stepik");
        configuration.setProperty("hibernate.connection.password","Stepik19");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    @SuppressWarnings("UnusedDeclaration")
    private Configuration getH2Configuration(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);
        configuration.setProperty("hibernate.dialect", "orh.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "test");
        configuration.setProperty("hibernate.connection.password", "test");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    public UsersDataSet getUserById(long id) throws DBException{
        try{
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSet dataSet = dao.get(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public long addUser (String name, String pass) throws DBException{
        try{
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(name, pass);
            session.close();
            return id;
        } catch (HibernateException e){
            throw new DBException(e);
        }
    }

    public long getUserId(String login) throws DBException{
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            return dao.getUserId(login);
        } catch (HibernateException e){
            throw new DBException(e);
        }
    }

    public void printConnectionInfo(){
        try{
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getJdbcServices().getBootstrapJdbcConnectionAccess().obtainConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration){
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
