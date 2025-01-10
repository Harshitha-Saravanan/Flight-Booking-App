const AirlineAlreadyExist = require('./exceptions/AirlineAlreadyExist');
const AirlineNotFound = require('./exceptions/AirlineNotFound');
const FlightAlreadyExist = require('./exceptions/FlightAlreadyExist');
const FlightNotFound = require('./exceptions/FlightNotFound');

class AirlineService {
    constructor(airlineRepository, flightService) {
        this.airlineRepository = airlineRepository;
        this.flightService = flightService;
    }

    
    isRegistered(id) {
        const airline = this.airlineRepository.findById(id);
        return airline !== undefined; 
    }

    
    async register(airline) {
        if (this.isRegistered(airline.getId())) {
            throw new AirlineAlreadyExist(`'${airline.getName()}' already exists`);
        }

        return this.airlineRepository.save(airline);
    }

    
    async registerFlight(flight) {
        const airlineId = flight.getAirline().getId();

        if (!this.isRegistered(airlineId)) {
            throw new AirlineNotFound(`'${flight.getAirline().getName()}' Airline not found`);
        }

        
        const updatedFlight = await this.flightService.register(flight);
        updatedFlight.getAirline().addFlight(updatedFlight); 
        return updatedFlight;
    }

    
    async addService(flightId, service) {
        try {
            await this.flightService.addService(flightId, service);
        } catch (error) {
            throw new FlightNotFound(`Flight with ID ${flightId} not found`);
        }
    }
}

module.exports = AirlineService;
