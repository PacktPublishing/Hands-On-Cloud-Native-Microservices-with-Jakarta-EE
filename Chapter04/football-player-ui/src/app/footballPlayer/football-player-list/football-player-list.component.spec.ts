import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FootballPlayerListComponent } from './football-player-list.component';

describe('FootballPlayerListComponent', () => {
  let component: FootballPlayerListComponent;
  let fixture: ComponentFixture<FootballPlayerListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FootballPlayerListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FootballPlayerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
