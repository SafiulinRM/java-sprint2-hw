public abstract class StandardTask {
    private String name;
    private static int counterId = 1;
    private int id = 0;
    private Status status;
    public StandardTask(String name, Status status) {
        setId();
        setName(name);
        setStatus(status);
    }
    public int getId() {
        return id;
    }

    public void setId() {
        id = counterId++;
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
