package oxy_project.OxygenMonitor.model;

public class OxygenData {
    private Long id;
    private String city;
    private double oxygenLevel;

    // Constructor
    public OxygenData(String city, double oxygenLevel) {
        this.city = city;
        this.oxygenLevel = oxygenLevel;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getOxygenLevel() { return oxygenLevel; }
    public void setOxygenLevel(double oxygenLevel) { this.oxygenLevel = oxygenLevel; }
}
