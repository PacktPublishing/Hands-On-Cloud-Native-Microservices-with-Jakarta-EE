import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FootballManagerListComponent } from './../footballManager/football-manager-list/football-manager-list.component';
import { FootballManagerCreateComponent } from './../footballManager/football-manager-create/football-manager-create.component';

const routes: Routes = [
  {path: 'footballManager', component: FootballManagerListComponent},
  {path: 'footballManager/create', component: FootballManagerCreateComponent},
  {path: 'footballManager/edit/:id', component: FootballManagerCreateComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FootballManagerRoutingModule { }
