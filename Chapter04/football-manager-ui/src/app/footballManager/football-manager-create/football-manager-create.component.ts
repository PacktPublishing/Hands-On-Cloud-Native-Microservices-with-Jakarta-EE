import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {FootballManagerService} from '../football-manager.service';
import {FootballManager} from '../FootballManager';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-football-manager-create',
  templateUrl: './football-manager-create.component.html',
  styleUrls: ['./football-manager-create.component.css'],
  providers: [FootballManagerService]
})
export class FootballManagerCreateComponent implements OnInit, OnDestroy {

  id: number;
  footballManager: FootballManager;

  footballManagerForm: FormGroup;
  private sub: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private footballManagerService: FootballManagerService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
    this.footballManagerForm = new FormGroup({
      name: new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      age: new FormControl('', Validators.required),
      nickname: new FormControl('', Validators.required)
    });
    if (this.id) { // edit form
      this.footballManagerService.findById(this.id).subscribe(
        footballManager => {
          this.id = footballManager.id;
          this.footballManagerForm.patchValue({
            name: footballManager.name,
            surname: footballManager.surname,
            age: footballManager.age,
            nickname: footballManager.nickname
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
    if (this.footballManagerForm.valid) {
      if (this.id) {
        const footballManager: FootballManager = new FootballManager(this.id,
          this.footballManagerForm.controls['name'].value,
          this.footballManagerForm.controls['surname'].value,
          this.footballManagerForm.controls['age'].value,
          this.footballManagerForm.controls['nickname'].value);
        this.footballManagerService.updateFootballManager(footballManager).subscribe();
      } else {

        const footballManager: FootballManager = new FootballManager(null,
          this.footballManagerForm.controls['name'].value,
          this.footballManagerForm.controls['surname'].value,
          this.footballManagerForm.controls['age'].value,
          this.footballManagerForm.controls['nickname'].value);
        this.footballManagerService.saveFootballManager(footballManager).subscribe();
      }

    }
    this.footballManagerForm.reset();
    this.router.navigate(['/footballManager']);
  }

  redirectFootballManagerPage() {
    this.router.navigate(['/footballManager']);

  }

}
