import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {MainHandlerService} from "../main-handler.service";
import {Device} from "../models/device.model";
import {ModalService} from "../modal/modal.service";

@Component({
  selector: 'app-devices-table',
  templateUrl: './devices-table.component.html',
  styleUrls: ['./devices-table.component.css']
})
export class DevicesTableComponent implements OnInit {

  public devices: Device[] = [];
  public description?: string;
  public address?: string;
  public maxHConsumption?: number;

  constructor(private mainHandler: MainHandlerService, private modalService: ModalService) {
    this.loadDevices();
  }

  private loadDevices() {
    this.mainHandler.getDevices().subscribe((res) => {
      this.devices = res;
    });
  }

  ngOnInit(): void {
    this.description = "";
    this.address = "";
    this.maxHConsumption = -1;
  }

  public deleteDevice(id: number) {
    this.mainHandler.removeDevice(id).subscribe((res) => {
      this.loadDevices();
    })
  }

  public openModal(modal: string, toEdit: boolean, device?: Device) {
    this.modalService.open(modal);
    if(toEdit){
      this.address = device!.address;
      this.description = device!.description;
      this.maxHConsumption = device!.maxHConsumption;
    }else{
      this.address="";
      this.description="";
      this.maxHConsumption=-1;
    }
  }

  public updateDevice(id: number) {
    const deviceUpdated = new Device(id, this.description!, this.address!, this.maxHConsumption!);
    this.mainHandler.modifyDevice(deviceUpdated).subscribe((res: any) => {
      const idx = this.devices.findIndex(d => d.id === id);
      if (idx !== -1)
        this.devices[idx] = deviceUpdated;
    });
  }

  public saveDevice(){
    const toAdd = new Device(-1,this.description!,this.address!,this.maxHConsumption!);
    this.mainHandler.saveDevice(toAdd).subscribe((res:number) => {
      toAdd.id=res;
      this.devices.push(toAdd);
      this.modalService.close('add-device-modal');
    })
  }

}
