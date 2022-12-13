package pivovaroff.jpasaveantipatterns.domain;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "post_uuid")
@Getter
@Setter
public class PostWithEmbeddedUUID implements Persistable<PostWithEmbeddedUUID.PostID> {

    @EmbeddedId
    private PostID id;
    private String title;
    @Transient
    private boolean isNew;

    public static PostWithEmbeddedUUID buildWithPersistable(String title) {
        final var post = new PostWithEmbeddedUUID();
        post.setId(new PostID(UUID.randomUUID()));
        post.setTitle(title);
        post.setNew(true);
        return post;
    }

    public static PostWithEmbeddedUUID build(String title) {
        final var post = new PostWithEmbeddedUUID();
        post.setId(new PostID(UUID.randomUUID()));
        post.setTitle(title);
        return post;
    }

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostID implements Serializable {

        @Column(name = "id")
        private UUID value;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PrePersist
    void trackNotNew() {
        this.isNew = false;
    }
}
