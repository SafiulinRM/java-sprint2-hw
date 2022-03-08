public class Subtask extends StandardTask {
    private int epicId;

    public Subtask(String name, Status status, int epicId) {
        super(name, status);
        setEpicId(epicId);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
