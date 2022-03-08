 public class ManagerFactory  {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
     public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
     }
}
