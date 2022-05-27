package model;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractTask implements Comparable<AbstractTask> {
    private final int id;
    private final String name;
    private Status status;
    private String description;
    private Type type;
    protected long durationMs = 0;
    protected LocalDateTime startTime;

    protected AbstractTask(int id, String name, Status status) {
        this.id = id;
        this.name = name;
        setStatus(status);
    }

    @Override
    public int compareTo(AbstractTask o) {
        if (this.getStartTime().getYear() != o.getStartTime().getYear()) {

            return this.getStartTime().getYear() - o.getStartTime().getYear();

        } else if (this.getStartTime().getMonth() != o.getStartTime().getMonth()) {

            return this.getStartTime().getMonthValue() - o.getStartTime().getMonthValue();

        } else if (this.getStartTime().getDayOfYear() != o.getStartTime().getDayOfYear()) {

            return (this.getStartTime().getDayOfYear() - o.getStartTime().getDayOfYear());

        } else if (this.getStartTime().getHour() != o.getStartTime().getHour()) {

            return (this.getStartTime().getHour() - o.getStartTime().getHour());

        } else if (this.getStartTime().getMinute() != o.getStartTime().getMinute()) {

            return (this.getStartTime().getMinute() - o.getStartTime().getMinute());

        } else {

            return 0;
        }
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(durationMs);
    }

    public long getDuration() {
        return durationMs;
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
        return id + "," + type + "," + name + "," + status + "," + description + "," + durationMs + "," + startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask that = (AbstractTask) o;
        return id == that.id && durationMs == that.durationMs && Objects.equals(name, that.name) && status == that.status && Objects.equals(description, that.description) && type == that.type && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id;
        result = 31 * result + id;
        return result;
    }
}
