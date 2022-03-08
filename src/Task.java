public class Task extends StandardTask {
    private String name;
    private static int counterId = 1;
    private int id = 0;
    private Status status;

    public Task(String name, Status status) {
        super(name, status);
    }


}