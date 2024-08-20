package it.unibo.myvet.model;

public class Service {
    private int serviceId;
    private String name;

    // Constructor
    public Service(String name) {
        this.name = name;
    }

    // Constructor
    public Service(int serviceId, String name) {
        this(name);
        this.serviceId = serviceId;
    }

    // Getters and Setters
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", name='" + name + '\'' +
                '}';
    }
}
