package pivovaroff.jpasaveantipatterns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pivovaroff.jpasaveantipatterns.domain.PostWithEmbeddedUUID;

@Repository
public interface PostWithEmbeddedUUIDRepository extends JpaRepository<PostWithEmbeddedUUID, PostWithEmbeddedUUID.PostID> {
}
