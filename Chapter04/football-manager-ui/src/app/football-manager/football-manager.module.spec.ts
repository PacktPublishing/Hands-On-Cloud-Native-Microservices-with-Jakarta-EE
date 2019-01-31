import { FootballManagerModule } from './football-manager.module';

describe('FootballManagerModule', () => {
  let footballManagerModule: FootballManagerModule;

  beforeEach(() => {
    footballManagerModule = new FootballManagerModule();
  });

  it('should create an instance', () => {
    expect(footballManagerModule).toBeTruthy();
  });
});
