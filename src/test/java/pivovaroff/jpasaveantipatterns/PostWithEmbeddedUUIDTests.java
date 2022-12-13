package pivovaroff.jpasaveantipatterns;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.Test;
import pivovaroff.jpasaveantipatterns.domain.PostWithEmbeddedUUID;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertInsertCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;

class PostWithEmbeddedUUIDTests extends BaseTest {

    @Test
    void createWith1Request() {
        //arrange
        SQLStatementCountValidator.reset();

        //act
        PostWithEmbeddedUUID p = postWithEmbeddedUUIDService.create(
                PostWithEmbeddedUUID.buildWithPersistable("1 request for insert!"));

        //assert
        assertInsertCount(1);
        assertSelectCount(0);
    }

    @Test
    void createWith2Request() {
        //arrange
        SQLStatementCountValidator.reset();

        //act
        PostWithEmbeddedUUID p = postWithEmbeddedUUIDService.create(
                PostWithEmbeddedUUID.build("2 request for insert!"));

        //assert
        assertInsertCount(1);
        assertSelectCount(1);
    }

}
