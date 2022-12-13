package pivovaroff.jpasaveantipatterns.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pivovaroff.jpasaveantipatterns.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
