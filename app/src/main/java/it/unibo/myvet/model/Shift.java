package it.unibo.myvet.model;

import java.time.LocalTime;
import java.time.DayOfWeek;

public class Shift {
    private int shiftId;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int vetId; // Reference to Vet

    // Constructor
    public Shift(int shiftId, DayOfWeek day, LocalTime startTime, LocalTime endTime, int vetId) {
        this.shiftId = shiftId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.vetId = vetId;
    }

    // Getters and Setters
    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
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

    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "shiftId=" + shiftId +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", vetId=" + vetId +
                '}';
    }
}
