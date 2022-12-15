package pivovaroff.jpasaveantipatterns.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pivovaroff.jpasaveantipatterns.domain.Post;
import pivovaroff.jpasaveantipatterns.repo.PostRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post create(String title) {
        Post post = Post.build(title);
        return postRepository.save(post);
    }

    @Transactional
    public void changeTitle(Long postId, String title) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Not found!"));
        post.changeTitle(title);
        postRepository.save(post);
    }

    @Transactional
    public void changeTitleWithoutEvent(Long postId, String title) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Not found!"));
        post.changeTitle(title);
        //dirty checking
    }

    @Transactional
    public void changeTitleWithException(Long postId, String title) {
        final var post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Not found!"));
        post.changeTitle(title);
        postRepository.save(post);
        throw new RuntimeException();
    }
}
