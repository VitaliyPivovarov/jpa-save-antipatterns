package pivovaroff.jpasaveantipatterns.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pivovaroff.jpasaveantipatterns.domain.PostWithUUID;
import pivovaroff.jpasaveantipatterns.repo.PostWithUUIDRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostWithUUIDService {

    private final PostWithUUIDRepository postWithUUIDRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public PostWithUUID createWithPersist(String title) {
        PostWithUUID post = PostWithUUID.build(title);
        post = postWithUUIDRepository.persist(post);
        return post;
    }

    @Transactional
    public void createWithEmPersist(String title) {
        PostWithUUID post = PostWithUUID.build(title);
        em.persist(post);
    }

    @Transactional
    public PostWithUUID createWith2Req(String title) {
        PostWithUUID post = PostWithUUID.build(title);
        post = postWithUUIDRepository.save(post);
        return post;
    }

    @Transactional
    public void updateWithMerge(PostWithUUID post, String title) {
        post = em.merge(post);
        post.changeTitle(title);
        //dirty checking
    }

    @Transactional
    public void updateWithFlush(UUID postId, String title) {
        final var post = postWithUUIDRepository.findById(postId).orElseThrow();
        post.changeTitle(title);
        postWithUUIDRepository.flush();
        //dirty checking
    }
}
