import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FootballPlayerListComponent } from './../footballPlayer/football-player-list/football-player-list.component';
import { FootballPlayerCreateComponent } from './../footballPlayer/football-player-create/football-player-create.component';

const routes: Routes = [
  {path: 'footballPlayer', component: FootballPlayerListComponent},
  {path: 'footballPlayer/create', component: FootballPlayerCreateComponent},
  {path: 'footballPlayer/edit/:id', component: FootballPlayerCreateComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FootballPlayerRoutingModule { }
