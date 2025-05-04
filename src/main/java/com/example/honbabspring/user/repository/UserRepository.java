package com.example.honbabspring.user.repository;

import com.example.honbabspring.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(org.apache.catalina.User user) {
        entityManager.persist(user);
    }

    public User findOne(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("select m from User m", User.class).getResultList();
    }

    public List<User> findByName(String name) {
        return entityManager.createQuery("select m from User m where m.username = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }
}
