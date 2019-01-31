import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FootballPlayerCreateComponent } from './football-player-create.component';

describe('FootballPlayerCreateComponent', () => {
  let component: FootballPlayerCreateComponent;
  let fixture: ComponentFixture<FootballPlayerCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FootballPlayerCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FootballPlayerCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
