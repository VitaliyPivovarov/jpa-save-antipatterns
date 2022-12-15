package pivovaroff.jpasaveantipatterns;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.Test;
import pivovaroff.jpasaveantipatterns.domain.PostWithUUID;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertInsertCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;

class PostWithUUIDTests extends BaseTest {


    @Test
    void createWithHibernatePersist() {
        //arrange
        SQLStatementCountValidator.reset();

        //act
        PostWithUUID p = postWithUUIDService.createWithPersist("Hi!");

        //assert
        assertInsertCount(1);
        assertSelectCount(0);
    }

    @Test
    void createWithEmPersist() {
        //arrange
        SQLStatementCountValidator.reset();

        //act
        postWithUUIDService.createWithEmPersist("Hi!");

        //assert
        assertInsertCount(1);
        assertSelectCount(0);
    }

    @Test
    void createWith2Request() {
        //arrange
        SQLStatementCountValidator.reset();

        //act
        postWithUUIDService.createWith2Req("Hi!");

        //assert
        assertInsertCount(1);
        assertSelectCount(1);
    }

    @Test
    void createWith2RequestEmMerge() {
        //arrange
        SQLStatementCountValidator.reset();
        PostWithUUID p = postWithUUIDService.createWithPersist("Hi!");

        //act
        postWithUUIDService.updateWithMerge(p, "Merge");

        //assert
        assertInsertCount(1);
        assertSelectCount(1);
    }

    @Test
    void createWithPersist2Request() {
        //arrange
        SQLStatementCountValidator.reset();
        PostWithUUID p = postWithUUIDService.createWithPersist("Hi!");

        //act
        postWithUUIDService.updateWithFlush(p.getId(), "Merge");

        //assert
        assertInsertCount(1);
        assertSelectCount(1);
    }
}
