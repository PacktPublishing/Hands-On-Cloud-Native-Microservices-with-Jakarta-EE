import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FootballManagerListComponent } from './football-manager-list.component';

describe('FootballManagerListComponent', () => {
  let component: FootballManagerListComponent;
  let fixture: ComponentFixture<FootballManagerListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FootballManagerListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FootballManagerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
