import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<StandardTask> tasksHistory = new ArrayList<>();

    @Override
    public void add(StandardTask task){
tasksHistory.add(task);
    }

    @Override
    public List<StandardTask> getHistory(){
        return tasksHistory;
    }
}