package model;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractTask {
    private final int id;
    private final String name;
    private Status status;
    private String description;
    private Type type;
    protected long duration = 0;
    protected LocalDateTime startTime;

    protected AbstractTask(int id, String name, Status status) {
        this.id = id;
        this.name = name;
        setStatus(status);
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public long getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
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
        return id + "," + type + "," + name + "," + status + "," + description + "," + duration + "," + startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask that = (AbstractTask) o;
        return id == that.id && duration == that.duration && Objects.equals(name, that.name) && status == that.status && Objects.equals(description, that.description) && type == that.type && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id;
        result = 31 * result + id;
        return result;
    }
}
