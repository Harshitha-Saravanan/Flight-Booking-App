const Airline = require('./Airline'); 


const flight1 = { flightNumber: 'A101', destination: 'NYC' };
const flight2 = { flightNumber: 'A102', destination: 'LA' };


const airline = new Airline('Jet Airways');


airline.addFlight(flight1);
airline.addFlight(flight2);




console.log(airline.getFlights()); 

const airline2 = new Airline('Jet Airways');
console.log(airline.equals(airline2)); 


console.log(airline.hashCode()); 
