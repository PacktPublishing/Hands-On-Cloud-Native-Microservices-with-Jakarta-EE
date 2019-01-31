import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {FootballPlayerService} from '../football-player.service';
import {FootballPlayer} from '../FootballPlayer';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-football-player-create',
  templateUrl: './football-player-create.component.html',
  styleUrls: ['./football-player-create.component.css'],
  providers: [FootballPlayerService]
})
export class FootballPlayerCreateComponent implements OnInit, OnDestroy {

  id: number;
  footballPlayer: FootballPlayer;

  footballPlayerForm: FormGroup;
  private sub: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private footballPlayerService: FootballPlayerService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    this.footballPlayerForm = new FormGroup({
      name: new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      age: new FormControl('', Validators.required),
      team: new FormControl('', Validators.required),
      position: new FormControl('', Validators.required),
      price: new FormControl('', Validators.required)
    });
    if (this.id) { // edit form
      this.footballPlayerService.findById(this.id).subscribe(
        footballPlayer => {
          this.id = footballPlayer.id;
          this.footballPlayerForm.patchValue({
            name: footballPlayer.name,
            surname: footballPlayer.surname,
            age: footballPlayer.age,
            team: footballPlayer.team,
            position: footballPlayer.position,
            price: footballPlayer.price,
          });
        }, error => {
          console.log(error);
        }
      );

    }
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  onSubmit() {
    if (this.footballPlayerForm.valid) {
      if (this.id) {
        const footballPlayer: FootballPlayer = new FootballPlayer(this.id,
          this.footballPlayerForm.controls['name'].value,
          this.footballPlayerForm.controls['surname'].value,
          this.footballPlayerForm.controls['age'].value,
          this.footballPlayerForm.controls['team'].value,
          this.footballPlayerForm.controls['position'].value,
          this.footballPlayerForm.controls['price'].value);
        this.footballPlayerService.updateFootballPlayer(footballPlayer).subscribe();
      } else {

        const footballPlayer: FootballPlayer = new FootballPlayer(null,
          this.footballPlayerForm.controls['name'].value,
          this.footballPlayerForm.controls['surname'].value,
          this.footballPlayerForm.controls['age'].value,
          this.footballPlayerForm.controls['team'].value,
          this.footballPlayerForm.controls['position'].value,
          this.footballPlayerForm.controls['price'].value);
        this.footballPlayerService.saveFootballPlayer(footballPlayer).subscribe();
      }

    }
    this.footballPlayerForm.reset();
    this.router.navigate(['/footballPlayer']);
  }

  redirectFootballPlayerPage() {
    this.router.navigate(['/footballPlayer']);

  }

}
