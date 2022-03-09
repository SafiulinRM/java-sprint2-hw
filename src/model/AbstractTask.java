package model;

import service.IdGenerator;
import service.Status;

public abstract class AbstractTask {
    IdGenerator idGenerator = new IdGenerator();

    private int newId = idGenerator.generate();
    private String name;
    private Status status;

    public AbstractTask(String name, Status status) {
        setName(name);
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
