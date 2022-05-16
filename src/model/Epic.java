package model;

import java.time.LocalDateTime;
import java.util.Locale;

public class Epic extends AbstractTask {
    private LocalDateTime endTime;

    public Epic(int id, String name, Status status) {
        super(id, name, status);
        this.setType(Type.EPIC);
        setDescription(getType().toString().toLowerCase(Locale.ROOT) + id);
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setDuration(long durationMs) {
        this.durationMs += durationMs;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
