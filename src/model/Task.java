package model;

import java.util.Locale;

public class Task extends AbstractTask {

    public Task(int id, String name, Status status) {
        super(id, name, status);
        this.setType(Type.TASK);
        setDescription(getType().toString().toLowerCase(Locale.ROOT) + id);
    }
}