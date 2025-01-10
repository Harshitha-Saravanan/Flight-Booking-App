class FlightNotFoundError extends Error {
    constructor(message) {
        super(message); // Call the parent Error constructor with the message
        this.name = this.constructor.name; // Set the error name to the class name
        this.statusCode = 404; // HTTP status code for "Not Found"
    }
}

module.exports = FlightNotFoundError;
