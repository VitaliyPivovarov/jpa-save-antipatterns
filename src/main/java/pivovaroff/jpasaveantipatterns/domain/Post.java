package pivovaroff.jpasaveantipatterns.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;
import pivovaroff.jpasaveantipatterns.domain.event.model.PostNameChangedModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "post")
@Setter
@Getter
@ToString
public class Post extends AbstractAggregateRoot<Post> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    public static Post build(String title) {
        Post post = new Post();
        post.setTitle(title);
        return post;
    }

    public void changeTitle(String title) {
        this.title = title;
        registerEvent(new PostNameChangedModel(id));
    }
}
