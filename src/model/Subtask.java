package model;

import service.Status;

public class Subtask extends AbstractTask {
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
