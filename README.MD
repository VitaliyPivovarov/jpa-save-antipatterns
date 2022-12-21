# JPA SAVE ANTI-PATTERNS

## Introduction

In this article, we are going to see why Spring Data save method can invoke extra SQL queries
and how we can solve it.

A code is worth 1000 words:

```java
// ... skipped annotations
public class PostWithEmbeddedUUID {

    @EmbeddedId
    private PostID id;
    private String title;

    public static PostWithEmbeddedUUID build(String title) {
        final var post = new PostWithEmbeddedUUID();
        post.setId(new PostID(UUID.randomUUID()));
        post.setTitle(title);
        return post;
    }

    // ... skipped annotations
    public static class PostID implements Serializable {

        @Column(name = "id")
        private UUID value;
    }

}
```

```java
    @Transactional
public PostWithEmbeddedUUID create(String title){
        PostWithEmbeddedUUID post=PostWithEmbeddedUUID.build(title);
        post=postWithEmbeddedUUIDRepository.save(post);
        return post;
        }
```

```sql
Hibernate:
select postwithem0_.id as id1_1_0_, postwithem0_.title as title2_1_0_
from post_uuid postwithem0_
where postwithem0_.id = ? Hibernate:
insert into post_uuid (title, id)
values (?, ?)
```

How you can see the Hibernate executes SQL select query before SQL insert query.

## How works save method

If we open implementation of Spring Data save method we can see this code:

```java
@Transactional
@Override
public<S extends T> S save(S entity){
        Assert.notNull(entity,"Entity must not be null.");

        if(entityInformation.isNew(entity)){
        em.persist(entity);
        return entity;
        }else{
        return em.merge(entity);
        }
        }
```

We are interested in seeming the `isNew` method (we will skip the implementation of this method here)
that return the true if:

- @Version annotation presents here and equals null
- ID equals object and equals null
- ID equal primitive and equals 0

In our case we initialized ID for entity, ID is not equals null and the save method invokes
line `return em.merge(entity);`.

The `merge` method is trying to know what status of our entity. If it doesn't find the entity in PersistenceContext
it invokes a SQL select query for checking entity in a DB. And if the record is not find in a DB
the `merge` method set status `Transient` for the entity.

## How to resolve it

### Solution 1 (Persistable interface)

The entity must implements `Persistable` interface and overrides `isNew` method:

```java
// ... skipped annotations
public class PostWithEmbeddedUUID implements Persistable<PostWithEmbeddedUUID.PostID> {

    @EmbeddedId
    private PostID id;
    private String title;
    @Transient
    private boolean isNew;

    public static PostWithEmbeddedUUID build(String title) {
        final var post = new PostWithEmbeddedUUID();
        post.setId(new PostID(UUID.randomUUID()));
        post.setTitle(title);
        post.setNew(true);
        return post;

        // ... skipped annotations
        public static class PostID implements Serializable {

            @Column(name = "id")
            private UUID value;
        }
```

```sql
Hibernate:
insert into post_uuid (title, id)
values (?, ?)
```

### Solution 2 (refuse to save method)

Create the interface:

```java
public interface HibernateRepository<T> {
    <S extends T> S persist(S entity);
    <S extends T> S merge(S entity);
}
```

The entity's repository need to extend HibernateRepository:

```java

@Repository
public interface PostWithEmbeddedUUIDRepository extends JpaRepository<PostWithEmbeddedUUID, UUID>,
        HibernateRepository<PostWithEmbeddedUUID> {
}
```

And in service layer invoke 'persist' method:

```java
    @Transactional
public PostWithUUID create(String title){
        PostWithEmbeddedUUID post=PostWithEmbeddedUUID.build(title);
        post=PostWithEmbeddedUUIDRepository.persist(post);
        return post;
        }
```

```sql
Hibernate:
insert into post_uuid (title, id)
values (?, ?)
```

### Solution 3 (PersistenceContext)

Autowires `PersistenceContext` and invoke `persist` method:

```java
    @PersistenceContext
private EntityManager em;

@Transactional
public void create(String title){
        PostWithEmbeddedUUID post=PostWithEmbeddedUUID.build(title);
        em.persist(post);
        }
```

```sql
Hibernate:
insert into post_uuid (title, id)
values (?, ?)
```

### Conclusion

As you have seen, Spring Data save method can cause performance issues and
persist method will give you better control but will add complexity in the form of low API.

Thanks for reading!

