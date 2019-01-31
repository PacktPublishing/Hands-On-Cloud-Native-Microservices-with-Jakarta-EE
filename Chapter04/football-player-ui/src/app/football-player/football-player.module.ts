import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FootballPlayerRoutingModule } from './football-player-routing.module';
import { FootballPlayerListComponent } from '../footballPlayer/football-player-list/football-player-list.component';
import { FootballPlayerCreateComponent } from '../footballPlayer/football-player-create/football-player-create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    FootballPlayerRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [FootballPlayerListComponent, FootballPlayerCreateComponent]
})
export class FootballPlayerModule { }
