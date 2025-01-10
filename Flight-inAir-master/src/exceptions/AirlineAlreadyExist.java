class AirlineAlreadyExistsError extends Error {
    constructor(message) {
        super(message);
        this.name = this.constructor.name;
        this.statusCode = 400;  // Set an appropriate HTTP status code (400: Bad Request)
    }
}

module.exports = AirlineAlreadyExistsError;
