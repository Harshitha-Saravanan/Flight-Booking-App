const Flight = require('./Flight');
const Airline = require('./Airline'); 


const airline = new Airline('Jet Airways');


const flight = new Flight('Flight101', 'New York', 'London', 500, airline);


flight.addService('Meal');
flight.addService('WiFi');


console.log(flight.getName()); 
console.log(flight.getServices()); 
console.log(flight.toString()); 

const flight2 = new Flight('Flight101', 'New York', 'London', 500, airline);
console.log(flight.equals(flight2)); 


console.log(flight.hashCode()); 
