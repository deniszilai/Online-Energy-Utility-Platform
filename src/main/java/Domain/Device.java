package Domain;

import java.io.Serializable;

public class Device  extends Entity<Long> implements Serializable {
    private String description;
    private String address;
    private float maxHConsumption;

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public float getMaxHConsumption() {
        return maxHConsumption;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMaxHConsumption(float maxHConsumption) {
        this.maxHConsumption = maxHConsumption;
    }

    public Device(String description, String address, float maxHConsumption) {
        this.description = description;
        this.address = address;
        this.maxHConsumption = maxHConsumption;
    }

    public Device(){}

    @Override
    public String toString() {
        return "Device{" +
                "description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", maxHConsumption=" + maxHConsumption +
                '}';
    }
}
