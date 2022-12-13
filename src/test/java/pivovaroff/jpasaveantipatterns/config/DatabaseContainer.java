package pivovaroff.jpasaveantipatterns.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseContainer extends PostgreSQLContainer<DatabaseContainer> {

    private static final String IMAGE_VERSION = "postgres:10.6";

    private static DatabaseContainer container;

    public DatabaseContainer(String dockerImage) {
        super(dockerImage);
    }

    public static DatabaseContainer getInstance() {

        if (container == null) container = new DatabaseContainer(IMAGE_VERSION);

        return container;
    }

    @Override
    public void start() {
        super.start();

        System.setProperty("POSTGRES_HOST", container.getJdbcUrl());
        System.setProperty("POSTGRES_USERNAME", container.getUsername());
        System.setProperty("POSTGRES_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        super.stop();
    }
}