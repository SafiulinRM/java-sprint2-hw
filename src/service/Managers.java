package service;

import java.net.URI;

public class Managers {
    private static HttpTaskManager taskManager;
    private static HistoryManager historyManager;
private static KVTaskClient client;

    public static HttpTaskManager getDefault() {
        if (taskManager == null && client == null) {
            taskManager = new HttpTaskManager("http://localhost:8078/register");
            if (taskManager == null) {
                client = new KVTaskClient(URI.create("http://localhost:8078"));
                taskManager = client.load("зеленый");
            }
        }
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        if (historyManager == null) {
            historyManager = new InMemoryHistoryManager();
        }
        return historyManager;
    }
}
