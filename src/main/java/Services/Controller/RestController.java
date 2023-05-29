package Services.Controller;

import Domain.Client;
import Domain.ClientDevice;
import Domain.Device;
import Persistence.Generic.ClientDeviceRepo;
import Persistence.Generic.ClientRepo;
import Persistence.Generic.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/project")
@CrossOrigin
public class RestController {
    @Autowired
    ClientRepo clientRepo;
    @Autowired
    DeviceRepo deviceRepo;
    @Autowired
    ClientDeviceRepo clientDeviceRepo;

    @GetMapping("/clients")
    public Iterable<Client> GetClients() {
        return this.clientRepo.GetAll();
    }

    @GetMapping("/devices")
    public Iterable<Device> GetDevices() {
        return this.deviceRepo.GetAll();
    }

    @PostMapping("/clients")
    public long SaveClient(@RequestBody Client toAdd) {
        return this.clientRepo.Save(toAdd).getId();
    }

    @PostMapping("/devices")
    public long SaveDevice(@RequestBody Device toAdd) {
        return this.deviceRepo.Save(toAdd).getId();
    }

    @DeleteMapping("/clients/{cid}")
    public ResponseEntity<?> RemoveClient(@PathVariable Long cid) {
        Iterable<ClientDevice> clientDevices = this.clientDeviceRepo.GetAll();
        clientDevices.forEach(clientDevice -> {
            if (clientDevice.getIdClient() == cid) {
                this.clientDeviceRepo.Remove(clientDevice.getId());
            }
        });
        Client deleted = this.clientRepo.Remove(cid);
        if (deleted == null)
            return new ResponseEntity<>("Client not found!", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @DeleteMapping("/devices/{did}")
    public ResponseEntity<?> RemoveDevice(@PathVariable Long did) {
        Iterable<ClientDevice> clientDevices = this.clientDeviceRepo.GetAll();
        clientDevices.forEach(clientDevice -> {
            if (clientDevice.getIdDevice() == did) {
                this.clientDeviceRepo.Remove(clientDevice.getId());
            }
        });
        Device deleted = this.deviceRepo.Remove(did);
        if (deleted == null)
            return new ResponseEntity<>("Device not found!", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @PutMapping("/clients/{cid}")
    public ResponseEntity<?> UpdateClient(@PathVariable Long cid, @RequestBody Client newer) {
        Client updated = this.clientRepo.Update(cid, newer);
        if (updated == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>("Client not updated!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/devices/{did}")
    public ResponseEntity<?> UpdateDevice(@PathVariable Long did, @RequestBody Device newer) {
        Device updated = this.deviceRepo.Update(did, newer);
        if (updated == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>("Device not updated!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/link")
    public void SaveLink(@RequestBody ClientDevice toAdd) {
        if(this.clientRepo.FindById(toAdd.getIdClient())!=null && this.deviceRepo.FindById(toAdd.getIdDevice())!=null)
            this.clientDeviceRepo.Save(toAdd);
    }

}
