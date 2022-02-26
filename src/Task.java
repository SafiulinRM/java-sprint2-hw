public class Task {
    private String name;
    int id = 0;
    Status status = Status.NEW;

    enum Status {
        NEW,
        IN_PROGRESS,
        DONE
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}