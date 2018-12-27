import {Component, OnInit} from '@angular/core';
import {FootballPlayer} from '../FootballPlayer';
import {FootballPlayerService} from '../football-player.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-football-player-list',
  templateUrl: './football-player-list.component.html',
  styleUrls: ['./football-player-list.component.css'],
  providers: [FootballPlayerService]
})
export class FootballPlayerListComponent implements OnInit {

  public footballPlayers: FootballPlayer[];

  constructor(private router: Router,
              private footballPlayerService: FootballPlayerService) {
  }

  ngOnInit() {
    this.getAllFootballPlayers();
  }

  getAllFootballPlayers() {
    this.footballPlayerService.findAll().subscribe(
      footballPlayers => {
        this.footballPlayers = footballPlayers;
      },
      err => {
        console.log(err);
      }
    );
  }

  redirectNewFootballPlayerPage() {
    this.router.navigate(['/footballPlayer/create']);
  }

  editFootballPlayerPage(footballPlayer: FootballPlayer) {
    if (footballPlayer) {
      this.router.navigate(['/footballPlayer/edit', footballPlayer.id]);
    }
  }

  deleteFootballPlayer(footballPlayer: FootballPlayer) {
    if (footballPlayer) {
      this.footballPlayerService.deleteFootballPlayerById(footballPlayer.id).subscribe(
        res => {
          this.getAllFootballPlayers();
          this.router.navigate(['/footballPlayer']);
          console.log('done');
        }
      );
    }
  }

}
