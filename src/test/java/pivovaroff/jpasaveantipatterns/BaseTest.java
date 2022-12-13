package pivovaroff.jpasaveantipatterns;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import pivovaroff.jpasaveantipatterns.config.DatabaseContainer;
import pivovaroff.jpasaveantipatterns.config.DatasourceProxyBeanPostProcessor;
import pivovaroff.jpasaveantipatterns.domain.event.listener.PostNameChangedEventListener;
import pivovaroff.jpasaveantipatterns.repo.PostRepository;
import pivovaroff.jpasaveantipatterns.repo.PostWithUUIDRepository;
import pivovaroff.jpasaveantipatterns.service.PostService;
import pivovaroff.jpasaveantipatterns.service.PostWithEmbeddedUUIDService;
import pivovaroff.jpasaveantipatterns.service.PostWithUUIDService;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = {
        JpaSaveAntipatternsApplication.class,
        DatasourceProxyBeanPostProcessor.class,
})
public class BaseTest {

    static {
        PostgreSQLContainer<?> postgreSQLContainer = DatabaseContainer.getInstance();
        postgreSQLContainer.start();
    }

    @Autowired
    protected PostRepository postRepository;

    @Autowired
    protected PostWithUUIDRepository postWithUUIDRepository;

    @Autowired
    protected PostService postService;

    @Autowired
    protected PostWithUUIDService postWithUUIDService;

    @Autowired
    protected PostWithEmbeddedUUIDService postWithEmbeddedUUIDService;

    @MockBean
    protected PostNameChangedEventListener postNameChangedEventListener;

    private void cleanDatabase() {
        postRepository.deleteAll();
        postWithUUIDRepository.deleteAll();
    }

    @Before("before clean DB")
    public void beforeEachTest() {
        cleanDatabase();
    }

    @After("efore clean DB")
    public void afterEachTest() {
        cleanDatabase();
    }

}
