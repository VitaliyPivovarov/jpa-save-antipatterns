package pivovaroff.jpasaveantipatterns.repo.custom;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class HibernateRepositoryImpl<T> implements HibernateRepository<T> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public <S extends T> S persist(S entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public <S extends T> S merge(S entity) {
        return em.merge(entity);
    }
}
