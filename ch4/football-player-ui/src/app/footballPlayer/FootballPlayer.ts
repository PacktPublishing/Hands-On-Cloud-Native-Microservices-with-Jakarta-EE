export class FootballPlayer {

  id: number;
  name: string;
  surname: string;
  age: number;
  team: string;
  position: string;
  price: number;

  constructor(id: number, name: string, surname: string, age: number, team: string, position: string, price: number) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.age = age;
    this.team = team;
    this.position = position;
    this.price = price;
  }

}
