package tools;

import bean.userBean;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Slf4j
public class LoginChecker {
    EntityManagerFactory entityManagerFactory;

    LoginChecker() {
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

    public String authorisation(String username, String password) {
        try {
            String token = "";
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                Query query = entityManager.createQuery("SELECT user from userBean user WHERE user.username = '" + username + "'");
                try {
                    query.getSingleResult();
                    token = Integer.toString(username.hashCode());
                } catch (javax.persistence.NoResultException e) {
                    System.out.println("_____________________________");
                    log.error(e.getMessage(), e);
                }
                entityTransaction.commit();
            } catch (Exception e) {
                System.out.println("_____________________________");
                log.error(e.getMessage(), e);
            }
            return token;
        } catch (Exception e) {
            System.out.println("_____________________________");
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public String register(String username, String password) {
        Files.lines(new Path(""));
        System.out.println("Trying to get token.");
        String token = "";
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        System.out.println("Correctly created managers.");
        try{
            entityTransaction.begin();
            Query query = entityManager.createQuery("SELECT user from userBean user WHERE user.username = '" + username + "'");
            try {
                query.getSingleResult();
            } catch(javax.persistence.NoResultException e) {
                entityManager.persist(new userBean(username, password));
                token = Integer.toString(username.hashCode());
            }
            entityTransaction.commit();
            System.out.println("Commit transaction.");
        } catch (Exception e) {
            System.out.println("Exception in transaction.");
            e.printStackTrace();
        }
        return token;
    }
}
