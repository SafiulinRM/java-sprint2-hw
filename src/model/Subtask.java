package model;

public class Subtask extends AbstractTask {
    private int epicId;

    public Subtask(int id, String name, Status status, int epicId) {
        super(id, name, status);
        setEpicId(epicId);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
