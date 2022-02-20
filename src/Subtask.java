import java.util.ArrayList;

public class Subtask extends Task {
    String subtaskName;
    ArrayList<Subtask> subtasks;

    public Subtask(String name) {
        super();
        this.subtaskName = name;
        subtasks = new ArrayList<>();
    }
}
