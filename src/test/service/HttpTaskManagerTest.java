package test.service;

import api.KVServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import service.HttpTaskManager;
import service.Managers;

import java.io.IOException;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    KVServer server;

    public HttpTaskManagerTest() throws IOException, InterruptedException {
        super(Managers.getDefault());
        server = new KVServer();
    }

    @Override
    @BeforeEach
    public void beforeEach() {
        server.start();
        generator.setCounterId(0);
        manager.removeAll();
    }

    @AfterEach
    public void makeAfterTest() {
        server.stop();
    }
}
