package pivovaroff.jpasaveantipatterns.repo.custom;

public interface HibernateRepository<T> {

    <S extends T> S persist(S entity);
    <S extends T> S merge(S entity);

}
