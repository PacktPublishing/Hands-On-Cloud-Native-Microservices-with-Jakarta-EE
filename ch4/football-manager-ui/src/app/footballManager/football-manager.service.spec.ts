import { TestBed, inject } from '@angular/core/testing';

import { FootballManagerService } from './football-manager.service';

describe('FootballManagerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FootballManagerService]
    });
  });

  it('should be created', inject([FootballManagerService], (service: FootballManagerService) => {
    expect(service).toBeTruthy();
  }));
});
