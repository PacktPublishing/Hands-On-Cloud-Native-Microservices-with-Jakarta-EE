import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FootballManagerCreateComponent } from './football-manager-create.component';

describe('FootballManagerCreateComponent', () => {
  let component: FootballManagerCreateComponent;
  let fixture: ComponentFixture<FootballManagerCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FootballManagerCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FootballManagerCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
