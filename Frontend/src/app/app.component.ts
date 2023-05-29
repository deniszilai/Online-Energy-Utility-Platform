import { Component } from '@angular/core';
import {Device} from "./models/device.model";
import {ModalService} from "./modal/modal.service";
import {MainHandlerService} from "./main-handler.service";
import {Client} from "./models/client.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'DS_2022_30244_Zilai_Denis_Assignment_1_frontend';

  idClient:number=0;
  idDevice:number=0;

  constructor(private mainHandler:MainHandlerService,private modalService:ModalService) {
  }

  public openModal(modal: string) {
    this.modalService.open(modal);
  }

  public saveLink(){
    this.mainHandler.saveLink(this.idClient,this.idDevice).subscribe((res) => {
      this.modalService.close('add-link-modal');
    })
  }
}
