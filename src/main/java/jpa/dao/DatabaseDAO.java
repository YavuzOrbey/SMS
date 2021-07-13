package jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// the PDF didn't tell us how to access the database so I figured I make this interface which is more like a
// implementor/Service than a DAO but it's simple enough
public interface DatabaseDAO {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMS");;
    EntityManager entityManager = entityManagerFactory.createEntityManager();
     static void startDatabase(){
         entityManager.getTransaction().begin();

    }

    static void closeDatabase(){
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    static EntityManager getEntityManager() {
        return entityManager;
    }
}
