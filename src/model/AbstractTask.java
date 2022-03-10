package model;

import service.IdGenerator;
import service.Status;

public abstract class AbstractTask {
    private int newId;
    private String name;
    private Status status;

    public AbstractTask(int newId, String name, Status status) {
        this.newId = newId;
        this.name = name;
        setStatus(status);
    }

    public int getId() {
        return newId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
