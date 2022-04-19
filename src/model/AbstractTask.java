package model;

public abstract class AbstractTask {
    private int id;
    private String name;
    private Status status;
    private String description;
    private Type type;

    public AbstractTask(int id, String name, Status status) {
        this.id = id;
        this.name = name;
        setStatus(status);
    }

    public int getId() {
        return id;
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

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + ",";
    }
}
