package pivovaroff.jpasaveantipatterns.domain.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import pivovaroff.jpasaveantipatterns.domain.event.model.PostNameChangedModel;

@Component
@Slf4j
public class PostNameChangedEventListener {

    /**
     *
     AFTER_COMMIT (default) is used to fire the event if the transaction has completed successfully.
     AFTER_ROLLBACK – if the transaction has rolled back
     AFTER_COMPLETION – if the transaction has completed (an alias for AFTER_COMMIT and AFTER_ROLLBACK)
     BEFORE_COMMIT is used to fire the event right before transaction commit.
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void archiveChanges(PostNameChangedModel postNameChangedModel) {
        log.info("\nArchive changes " + postNameChangedModel.id());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessageToKafka(PostNameChangedModel postNameChangedModel) {
        log.info("\nSent message to Kafka " + postNameChangedModel.id());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void rollback(PostNameChangedModel postNameChangedModel) {
        log.info("\nRollback " + postNameChangedModel.id());
    }

}
