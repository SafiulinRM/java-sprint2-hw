package test.service;

import service.HTTPTaskManager;

public class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager> {

    public HTTPTaskManagerTest() {
        super(new HTTPTaskManager("http://localhost:8078/register"));
    }
}
