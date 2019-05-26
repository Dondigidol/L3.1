package dbService.DAO;

import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UsersDAO {
    private Session session;

    public UsersDAO(Session session){
        this.session = session;
    }

    public UsersDataSet get(long id ) throws HibernateException{
        return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public Long getUserId(String login) throws HibernateException{
        Long res = null;

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UsersDataSet> criteriaQuery = builder.createQuery(UsersDataSet.class);
        Root<UsersDataSet> root = criteriaQuery.from(UsersDataSet.class);
        criteriaQuery.select(root).where(builder.equal(root.get("login"), login));
        Query<UsersDataSet> query = session.createQuery(criteriaQuery);
        switch (query.getResultList().size()){
            case 1:
                res = ((UsersDataSet) query.getResultList().get(0)).getId();
                break;
            default:
                break;
        }
        System.out.println(query.getResultList().size());
        return  res;
    }

    public long insertUser(String login, String pass) throws HibernateException{
        return (Long) session.save(new UsersDataSet(login, pass));
    }

}
