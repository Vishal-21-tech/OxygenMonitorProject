package oxy_project.OxygenMonitor.model;

public class OxygenData {
    private String city;
    private double ozone;
    private String unit;
    private String description;

    public OxygenData() {
    }

    public OxygenData(String city, double ozone, String unit, String description) {
        this.city = city;
        this.ozone = ozone;
        this.unit = unit;
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
