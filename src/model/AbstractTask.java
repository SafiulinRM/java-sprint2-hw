package model;

public abstract class AbstractTask {
    private int id;
    private String name;
    private Status status;

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

    @Override
    public String toString() {
        return "AbstractTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
