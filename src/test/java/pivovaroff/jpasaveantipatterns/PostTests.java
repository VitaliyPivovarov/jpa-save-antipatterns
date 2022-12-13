package pivovaroff.jpasaveantipatterns;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.Test;
import pivovaroff.jpasaveantipatterns.domain.Post;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PostTests extends BaseTest {

    @Test
    void create() {
        //arrange
        SQLStatementCountValidator.reset();

        //act
        postService.create(Post.build("Hello!"));

        //assert
        assertInsertCount(1);
        assertSelectCount(0);
    }

    @Test
    void changeTitleWithEvents() {
        //arrange
        Post p = postService.create(Post.build("Hello!"));

        //act
        SQLStatementCountValidator.reset();
        postService.changeTitle(p.getId(), "Events");

        //assert
        assertSelectCount(1);
        assertUpdateCount(1);

        verify(postNameChangedEventListener, times(1)).sendMessageToKafka(any());
        verify(postNameChangedEventListener, times(1)).archiveChanges(any());
        verify(postNameChangedEventListener, times(0)).rollback(any());
    }

    @Test
    void changeTitleWithoutEvents() {
        //arrange
        Post p = postService.create(Post.build("Hello!"));

        //act
        SQLStatementCountValidator.reset();
        postService.changeTitleWithoutEvent(p.getId(), "Not events");

        //assert
        assertSelectCount(1);
        assertUpdateCount(1);

        verify(postNameChangedEventListener, times(0)).sendMessageToKafka(any());
        verify(postNameChangedEventListener, times(0)).archiveChanges(any());
        verify(postNameChangedEventListener, times(0)).rollback(any());
    }


    @Test
    void changeTitleWithEventsRollback() {
        //arrange
        Post p = postService.create(Post.build("Hello!"));

        //act
        SQLStatementCountValidator.reset();
        assertThrows(RuntimeException.class, () ->
                postService.changeTitleWithException(p.getId(), "Events with exception"));

        //assert
        assertSelectCount(1);
        assertUpdateCount(0);

        verify(postNameChangedEventListener, times(0)).sendMessageToKafka(any());
        verify(postNameChangedEventListener, times(0)).archiveChanges(any());
        verify(postNameChangedEventListener, times(1)).rollback(any());
    }

}
