package pivovaroff.jpasaveantipatterns.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "post_uuid")
@Getter
@Setter
public class PostWithUUID {

    @Id
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
//    @GeneratedValue(generator = "uuid2")
    private UUID id;

    private String title;

    public static PostWithUUID build(String title) {
        final var post = new PostWithUUID();
        post.setId(UUID.randomUUID());
        post.setTitle(title);
        return post;
    }

    public void changeTitle(String title) {
        this.title = title;
        //event...
    }

}
