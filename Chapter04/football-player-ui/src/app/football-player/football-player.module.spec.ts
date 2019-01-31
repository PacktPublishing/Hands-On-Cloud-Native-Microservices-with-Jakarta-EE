import { FootballPlayerModule } from './football-player.module';

describe('FootballPlayerModule', () => {
  let footballPlayerModule: FootballPlayerModule;

  beforeEach(() => {
    footballPlayerModule = new FootballPlayerModule();
  });

  it('should create an instance', () => {
    expect(footballPlayerModule).toBeTruthy();
  });
});
