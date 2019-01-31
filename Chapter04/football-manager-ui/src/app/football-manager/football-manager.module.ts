import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FootballManagerRoutingModule } from './football-manager-routing.module';
import { FootballManagerListComponent } from '../footballManager/football-manager-list/football-manager-list.component';
import { FootballManagerCreateComponent } from '../footballManager/football-manager-create/football-manager-create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    FootballManagerRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [FootballManagerListComponent, FootballManagerCreateComponent]
})
export class FootballManagerModule { }
