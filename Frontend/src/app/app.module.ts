import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {DevicesTableComponent} from './devices-table/devices-table.component';
import {MainHandlerService} from "./main-handler.service";
import {HttpClientModule} from "@angular/common/http";
import {ClientsTableComponent} from './clients-table/clients-table.component';
import {ModalModule} from "./modal/modal.module";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    DevicesTableComponent,
    ClientsTableComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ModalModule,
    FormsModule
  ],
  providers: [MainHandlerService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
