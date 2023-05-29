import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client} from "./models/client.model";
import {Device} from "./models/device.model";

@Injectable({
  providedIn: 'root'
})
export class MainHandlerService {

  private readonly apiURL: string;
  constructor(private http:HttpClient) {
    this.apiURL = "http://localhost:8080/project";
  }

  public getDevices():Observable<Device[]>{
    return this.http.get(this.apiURL + "/devices") as Observable<Device[]>;
  }

  public getClients():Observable<Client[]>{
    return this.http.get(this.apiURL + "/clients") as Observable<Client[]>;
  }

  public removeDevice(id:number):Observable<Device>{
    return this.http.delete(`${this.apiURL}/devices/${id}`) as Observable<Device>;
  }

  public removeClient(id:number):Observable<Client>{
    return this.http.delete(`${this.apiURL}/clients/${id}`) as Observable<Client>;
  }

  public modifyDevice(device:Device){
    return this.http.put(`${this.apiURL}/devices/${device.id}`,device);
  }

  public modifyClient(client:Client){
    return this.http.put(`${this.apiURL}/clients/${client.id}`,client);
  }

  public saveDevice(device:Device):Observable<number>{
    return this.http.post(this.apiURL + "/devices",device) as Observable<number>;
  }

  public saveClient(client:Client):Observable<number>{
    return this.http.post(this.apiURL + "/clients",client) as Observable<number>;
  }

  public saveLink(idClient:number,idDevice:number):Observable<any>{
    return this.http.post(this.apiURL + "/link",{id:-1,idClient:idClient,idDevice:idDevice});
  }
}
