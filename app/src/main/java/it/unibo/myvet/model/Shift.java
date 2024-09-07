package it.unibo.myvet.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Shift {
    private DayOfWeek day;
    private int vetId;
    private LocalTime startTime;
    private LocalTime endTime;

    // Costruttore
    public Shift(DayOfWeek day, int vetId, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.vetId = vetId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter e Setter
    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "day=" + day +
                ", vetId=" + vetId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
