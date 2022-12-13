package pivovaroff.jpasaveantipatterns.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pivovaroff.jpasaveantipatterns.domain.PostWithEmbeddedUUID;
import pivovaroff.jpasaveantipatterns.repo.PostWithEmbeddedUUIDRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostWithEmbeddedUUIDService {

    private final PostWithEmbeddedUUIDRepository postWithEmbeddedUUIDRepository;

    @Transactional
    public PostWithEmbeddedUUID create(PostWithEmbeddedUUID post) {
        post = postWithEmbeddedUUIDRepository.save(post);
        return post;
    }

}
