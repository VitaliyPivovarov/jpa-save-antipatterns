package pivovaroff.jpasaveantipatterns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pivovaroff.jpasaveantipatterns.domain.PostWithUUID;
import pivovaroff.jpasaveantipatterns.repo.custom.HibernateRepository;

import java.util.UUID;

@Repository
public interface PostWithUUIDRepository extends JpaRepository<PostWithUUID, UUID>,
        HibernateRepository<PostWithUUID> {
}
