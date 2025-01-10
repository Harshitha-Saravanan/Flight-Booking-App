const AirlineRepository = require('./AirlineRepository');
const Airline = require('./Airline'); 


const repository = new AirlineRepository();


const airline1 = new Airline('Jet Airways');
const airline2 = new Airline('Air India');


repository.save(airline1);
repository.save(airline2);


console.log(repository.findById(1)); 
console.log(repository.findById(2)); 
console.log(repository.findById(3)); 
