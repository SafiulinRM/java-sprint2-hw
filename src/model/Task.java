package model;

import java.time.LocalDateTime;
import java.util.Locale;

public class Task extends AbstractTask {

    public Task(int id, String name, Status status, long duration, LocalDateTime startTime) {
        super(id, name, status);
        this.duration = duration;
        this.startTime = startTime;
        this.setType(Type.TASK);
        setDescription(getType().toString().toLowerCase(Locale.ROOT) + id);
    }
}