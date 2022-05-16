package model;


import java.time.LocalDateTime;
import java.util.Locale;

public class Subtask extends AbstractTask {
    private int epicId;

    public Subtask(int id, String name, Status status, long durationMs, LocalDateTime startTime, int epicId) {
        super(id, name, status);
        this.durationMs = durationMs;
        this.startTime = startTime;
        setEpicId(epicId);
        this.setType(Type.SUBTASK);
        setDescription(getType().toString().toLowerCase(Locale.ROOT) + id);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return getId() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDescription() + "," + getDuration() + "," + getStartTime() + "," + epicId;
    }
}
