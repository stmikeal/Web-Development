package tools;

import bean.pointBean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PointManager {
    EntityManagerFactory entityManagerFactory;
    PointManager() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("HibernateUnit");
            System.out.println("EntityManagerFactory init.");
        }catch(Exception e){
            System.out.println("-----------------------------------------------------");
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("-----------------------------------------------------");
        }
    }

    public pointBean addPoint(double x, double y, double z, String owner) {
        System.out.println("Started addPoint");
        pointBean point = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try{
            entityTransaction.begin();
            pointBean newPoint = new pointBean(x, y, z, owner);
            entityManager.persist(newPoint);
            entityTransaction.commit();
            point = newPoint;
        } catch (Exception e) {
            System.out.println("Exception in transaction.");
            e.printStackTrace();
        }
        return point;
    }

    public String clearPoint(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try{
            entityTransaction.begin();
            List namedQuery = entityManager.createQuery(
                    "SELECT p FROM pointBean p WHERE p.owner LIKE :custName")
                    .setParameter("custName", username)
                    .getResultList();
            namedQuery.forEach(entityManager::remove);
            entityTransaction.commit();
            return "yes";
        } catch (Exception e) {
            System.out.println("Exception in transaction.");
            e.printStackTrace();
        }
        return "";

    }

    public List<pointBean> getAll(String username){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try{
            entityTransaction.begin();
            List<pointBean> namedQuery = entityManager.createQuery(
                    "SELECT p FROM pointBean p WHERE p.owner LIKE :custName")
                    .setParameter("custName", username)
                    .getResultList();
            entityTransaction.commit();
            return namedQuery;
        } catch (Exception e) {
            System.out.println("Exception in transaction.");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
