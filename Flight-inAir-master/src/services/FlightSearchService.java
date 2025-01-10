class FlightSearchService {
    constructor(flightService) {
        this.flightGraph = flightService.getFlights();
    }

    search(origin, destination, ...services) {
        const serviceSet = new Set(services);
        let flightsWithMinHops = this.searchByHops(origin, destination, serviceSet);
        let totalCost, totalHops;

        
        for (let list of flightsWithMinHops) {
            console.log("Route with Minimum Hops: ");
            totalCost = 0;
            totalHops = 0;

            for (let flight of list) {
                console.log(`${flight.getOrigin()} to ${flight.getDestination()} via ${flight.getAirline().getName()} for ${flight.getFare()} `);
                totalCost += flight.getFare();
                totalHops++;
            }

            console.log(`Total Flights = ${totalHops} Total Cost = ${totalCost}`);
        }

        
        let flightsWithCheapestRoute = this.searchByCost(origin, destination, serviceSet);
        for (let list of flightsWithCheapestRoute) {
            console.log("Cheapest Route: ");
            totalCost = 0;
            totalHops = 0;

            for (let flight of list) {
                console.log(`${flight.getOrigin()} to ${flight.getDestination()} via ${flight.getAirline().getName()} for ${flight.getFare()} `);
                totalCost += flight.getFare();
                totalHops++;
            }

            console.log(`Total Flights = ${totalHops} Total Cost = ${totalCost}`);
        }
    }

    getMinimumFare(origin, destination, services) {
        const source = `null@${origin}`;
        const minHeap = new Map();
        minHeap.set(source, 0);
        const fares = new Map();
        fares.set(source, 0);

        while (minHeap.size) {
            const [currentNode, currentFare] = [...minHeap.entries()].shift();
            const currentDestination = currentNode.split("@")[1];

            if (currentDestination === destination) continue;

            for (let nbr of this.flightGraph.get(currentDestination) || []) {
                if (services.size && !this.containsAllServices(nbr.getServices(), services)) continue;

                const nextDestination = `${nbr.getAirline().getName()}@${nbr.getDestination()}`;
                const nextFare = currentFare + nbr.getFare();

                if (nextFare < fares.get(nextDestination) || !fares.has(nextDestination)) {
                    fares.set(nextDestination, nextFare);
                    minHeap.set(nextDestination, nextFare);
                }
            }
        }

        return [...fares.keys()].filter(d => d.includes(destination)).map(d => fares.get(d));
    }

    containsAllServices(flightServices, requiredServices) {
        return [...requiredServices].every(service => flightServices.has(service));
    }

    searchByCost(origin, destination, services) {
        const minimumFare = this.getMinimumFare(origin, destination, services);
        if (!minimumFare || minimumFare.length === 0) return [];

        let flights = [];
        this.searchByCostRecursive(origin, destination, minimumFare[0], services, new Set(), [], flights);

        const minHops = Math.min(...flights.map(f => f.length));
        return flights.filter(flightNodes => flightNodes.length === minHops);
    }

    searchByCostRecursive(origin, destination, minimumFare, services, visited, list, flights) {
        if (origin === destination && minimumFare === 0) {
            flights.push([...list]);
            return;
        }

        if (minimumFare <= 0) return;

        for (let nbr of this.flightGraph.get(origin) || []) {
            if (services.size && !this.containsAllServices(nbr.getServices(), services)) continue;

            if (!visited.has(nbr.getDestination())) {
                visited.add(origin);
                list.push(nbr);
                this.searchByCostRecursive(nbr.getDestination(), destination, minimumFare - nbr.getFare(), services, visited, list, flights);
                list.pop();
            }
        }
    }

    getMinimumHops(origin, destination, services) {
        const source = `null@${origin}`;
        const minHeap = new Map();
        minHeap.set(source, 0);
        const hops = new Map();
        hops.set(source, 0);

        while (minHeap.size) {
            const [currentNode, currentHops] = [...minHeap.entries()].shift();
            const currentDestination = currentNode.split("@")[1];

            if (currentDestination === destination) continue;

            for (let nbr of this.flightGraph.get(currentDestination) || []) {
                if (services.size && !this.containsAllServices(nbr.getServices(), services)) continue;

                const nextDestination = `${nbr.getAirline().getName()}@${nbr.getDestination()}`;
                const nextHops = currentHops + 1;

                if (nextHops < hops.get(nextDestination) || !hops.has(nextDestination)) {
                    hops.set(nextDestination, nextHops);
                    minHeap.set(nextDestination, nextHops);
                }
            }
        }

        return [...hops.keys()].filter(d => d.includes(destination)).map(d => hops.get(d));
    }

    searchByHops(origin, destination, services) {
        const minimumHops = this.getMinimumHops(origin, destination, services);
        if (!minimumHops || minimumHops.length === 0) return [];

        let flights = [];
        this.searchByHopsRecursive(origin, destination, minimumHops[0], services, new Set(), [], flights);

        const minFare = Math.min(...flights.map(flightNodes => flightNodes.reduce((sum, flight) => sum + flight.getFare(), 0)));
        return flights.filter(flightNodes => flightNodes.reduce((sum, flight) => sum + flight.getFare(), 0) === minFare);
    }

    searchByHopsRecursive(origin, destination, minimumHops, services, visited, list, flights) {
        if (origin === destination && minimumHops === 0) {
            flights.push([...list]);
            return;
        }

        if (minimumHops <= 0) return;

        for (let nbr of this.flightGraph.get(origin) || []) {
            if (services.size && !this.containsAllServices(nbr.getServices(), services)) continue;

            if (!visited.has(nbr.getDestination())) {
                visited.add(origin);
                list.push(nbr);
                this.searchByHopsRecursive(nbr.getDestination(), destination, minimumHops - 1, services, visited, list, flights);
                list.pop();
            }
        }
    }
}

module.exports = FlightSearchService;
