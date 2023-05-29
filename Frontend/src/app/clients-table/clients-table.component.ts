import { Component, OnInit } from '@angular/core';
import {Device} from "../models/device.model";
import {MainHandlerService} from "../main-handler.service";
import {Client} from "../models/client.model";
import {ModalService} from "../modal/modal.service";

@Component({
  selector: 'app-clients-table',
  templateUrl: './clients-table.component.html',
  styleUrls: ['./clients-table.component.css']
})
export class ClientsTableComponent implements OnInit {

  public clients: Client[] = [];
  public username?:string;
  public password?:string;
  constructor(private mainHandler:MainHandlerService,private modalService:ModalService) {
    this.loadClients();
  }

  ngOnInit(): void {
  }

  private loadClients(){
    this.mainHandler.getClients().subscribe((res) => {
      this.clients=res;
    });
  }

  public deleteClient(id:number){
    this.mainHandler.removeClient(id).subscribe((res)=>{
      this.loadClients();
    })
  }

  public openModal(modal: string, toEdit: boolean, client?: Client) {
    this.modalService.open(modal);
    if(toEdit){
      this.username = client!.username;
      this.password = client!.password;
    }
    else{
      this.username="";
      this.password="";
    }
  }

  public updateClient(id: number) {
    const clientUpdated = new Client(id, this.username!, this.password!);
    this.mainHandler.modifyClient(clientUpdated).subscribe((res: any) => {
      const idx = this.clients.findIndex(d => d.id === id);
      if (idx !== -1)
        this.clients[idx] = clientUpdated;
    });
  }

  public saveClient(){
    const toAdd = new Client(-1,this.username!,this.password!);
    this.mainHandler.saveClient(toAdd).subscribe((res:number) => {
      toAdd.id=res;
      this.clients.push(toAdd);
      this.modalService.close('add-client-modal');
    })
  }

}
