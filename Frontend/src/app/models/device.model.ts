export class Device{
  public id: number;
  public description: string;
  public address: string;
  public maxHConsumption: number;

  constructor(id:number, description:string, address:string,maxHConsumption:number) {
    this.id=id;
    this.description=description;
    this.address=address;
    this.maxHConsumption=maxHConsumption;
  }
}
