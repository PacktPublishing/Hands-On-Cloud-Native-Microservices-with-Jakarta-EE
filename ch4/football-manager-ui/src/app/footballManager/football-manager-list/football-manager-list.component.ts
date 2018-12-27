import {Component, OnInit} from '@angular/core';
import {FootballManager} from '../FootballManager';
import {FootballManagerService} from '../football-manager.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-football-manager-list',
  templateUrl: './football-manager-list.component.html',
  styleUrls: ['./football-manager-list.component.css'],
  providers: [FootballManagerService]
})
export class FootballManagerListComponent implements OnInit {

  public footballManagers: FootballManager[];

  constructor(private router: Router,
              private footballManagerService: FootballManagerService) {
  }

  ngOnInit() {
    this.getAllFootballManagers();
  }

  getAllFootballManagers() {
    this.footballManagerService.findAll().subscribe(
      footballManagers => {
        this.footballManagers = footballManagers;
      },
      err => {
        console.log(err);
      }
    );
  }

  redirectNewFootballManagerPage() {
    this.router.navigate(['/footballManager/create']);
  }

  editFootballManagerPage(footballManager: FootballManager) {
    if (footballManager) {
      this.router.navigate(['/footballManager/edit', footballManager.id]);
    }
  }

  deleteFootballManager(footballManager: FootballManager) {
    if (footballManager) {
      this.footballManagerService.deleteFootballManagerById(footballManager.id).subscribe(
        res => {
          this.getAllFootballManagers();
          this.router.navigate(['/footballManager']);
          console.log('done');
        }
      );
    }
  }

}
