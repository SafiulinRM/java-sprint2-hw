import java.util.List;

public interface HistoryManager {
    void add(StandardTask task);
    List<StandardTask> getHistory();
}
