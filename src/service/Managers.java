package service;

public class Managers {
    private static HTTPTaskManager taskManager;
    private static HistoryManager historyManager;

    public static HTTPTaskManager getDefault() {
        if (taskManager == null) {
            taskManager = new HTTPTaskManager("http://localhost:8078/register");
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
