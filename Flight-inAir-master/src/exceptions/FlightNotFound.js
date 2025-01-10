class FlightNotFoundError extends Error {
    constructor(message) {
        super(message); 
        this.name = this.constructor.name; 
        this.statusCode = 404; 
    }
}

module.exports = FlightNotFoundError;
