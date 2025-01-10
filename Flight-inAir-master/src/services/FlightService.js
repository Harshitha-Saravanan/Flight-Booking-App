class FlightService {
    constructor(flightRepository) {
        this.flightRepository = flightRepository;
    }

    async register(flight) {
        const existingFlight = await this.flightRepository.find(flight);
        if (existingFlight) {
            throw new Error(`Airline: ${flight.getAirline().getName()} Flight: ${flight.getName()} already exists`);
        }

        const updatedFlight = await this.flightRepository.save(flight);

        
        await this.flightRepository.updateGraph(updatedFlight);

        return updatedFlight;
    }

    async addService(flightId, service) {
        const flight = await this.flightRepository.findById(flightId);

        if (!flight) {
            throw new Error(`Flight: ${flightId} not found`);
        }

        
        flight.addService(service);
    }

    async getAllByService(service) {
        return await this.flightRepository.getAllByService(service);
    }

    async getFlights() {
        return await this.flightRepository.getGraph();
    }
}
