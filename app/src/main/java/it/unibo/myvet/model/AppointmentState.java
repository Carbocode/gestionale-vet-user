package it.unibo.myvet.model;

public class AppointmentState {
    private int stateId;
    private String stateName;

    // Costruttore
    public AppointmentState(String stateName) {
        this.stateName = stateName;
    }

    // Costruttore
    public AppointmentState(int stateId, String stateName) {
        this(stateName);
        this.stateId = stateId;
    }

    // Getter e Setter
    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return "AppointmentState{" +
                "stateId=" + stateId +
                ", stateName='" + stateName + '\'' +
                '}';
    }
}
