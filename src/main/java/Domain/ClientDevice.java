package Domain;

import java.io.Serializable;

public class ClientDevice extends Entity<Long> implements Serializable {
    private Long idClient;
    private Long idDevice;

    public ClientDevice(Long idClient, Long idDevice) {
        this.idClient = idClient;
        this.idDevice = idDevice;
    }

    public ClientDevice() {
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Long idDevice) {
        this.idDevice = idDevice;
    }

    @Override
    public String toString() {
        return "ClientDevice{" +
                "idClient=" + idClient +
                ", idDevice=" + idDevice +
                '}';
    }
}
