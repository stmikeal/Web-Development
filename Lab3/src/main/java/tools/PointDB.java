package tools;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PointDB {

    private EntityManagerFactory entityManagerFactory;

    /*
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostConstruct гарантирует единождое выполнение навешанного на аннотацию метода,
    а конструтор может вызываться несколько раз. Тебе же это не надо.
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public PointDB(){
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate1");
        System.out.println("EntityManagerFactory init.");
    }

    public void add(Point point) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(point);
            entityTransaction.commit();
        } catch (Exception ex) {
            try {
                entityTransaction.rollback();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        entityManager.close();
    }

    public void clear() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query q1 = entityManager.createQuery("DELETE FROM Point");
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        q1.executeUpdate();
        entityTransaction.commit();
    }

    public List<Point> findAll(String session_id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Point> criteriaQuery = cb.createQuery(Point.class);
        Root<Point> root = criteriaQuery.from(Point.class);
        CriteriaQuery<Point> all = criteriaQuery.select(root);
        TypedQuery<Point> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
}
