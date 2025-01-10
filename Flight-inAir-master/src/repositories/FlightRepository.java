const FlightRepository = require('./FlightRepository');
const Flight = require('./Flight'); 


const flightRepo = new FlightRepository();


const flight1 = new Flight('Flight101', 'New York', 'London', 500, 'Jet Airways');
const flight2 = new Flight('Flight102', 'New York', 'Paris', 400, 'Jet Airways');


flightRepo.save(flight1);
flightRepo.save(flight2);


console.log(flightRepo.findById(1)); 


console.log(flightRepo.getAllByService('WiFi')); 

flightRepo.updateGraph(flight1);
console.log(flightRepo.getGraph()); 


const foundFlight = flightRepo.find(flight1);
console.log(foundFlight); 
