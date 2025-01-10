class Driver {
    static async main() {
        
        const flightRepository = new FlightRepository();
        const flightService = new FlightService(flightRepository);

        const airlineRepository = new AirlineRepository();
        const airlineService = new AirlineService(airlineRepository, flightService);

        try {
            
            const jetAir = await airlineService.register(new Airline("JetAir"));
            const delta = await airlineService.register(new Airline("Delta"));
            const indigo = await airlineService.register(new Airline("IndiGo"));

            try {
               
                const JA_DEL_BLR = await airlineService.registerFlight(new Flight("JA", "DEL", "BLR", 500, jetAir));
                const JA_BLR_LON = await airlineService.registerFlight(new Flight("JA", "BLR", "LON", 1000, jetAir));
                const JA_LON_NYC = await airlineService.registerFlight(new Flight("JA", "LON", "NYC", 2000, jetAir));

                const DL_DEL_LON = await airlineService.registerFlight(new Flight("DL", "DEL", "LON", 2000, delta));
                const DL_LON_NYC = await airlineService.registerFlight(new Flight("DL", "LON", "NYC", 2000, delta));

                const IG_LON_NYC = await airlineService.registerFlight(new Flight("IG", "LON", "NYC", 2500, indigo));
                const IG_DEL_BLR = await airlineService.registerFlight(new Flight("IG", "DEL", "BLR", 600, indigo));
                const IG_BLR_PAR = await airlineService.registerFlight(new Flight("IG", "BLR", "PAR", 800, indigo));
                const IG_PAR_LON = await airlineService.registerFlight(new Flight("IG", "PAR", "LON", 300, indigo));

                try {
                    
                    await airlineService.addService(IG_LON_NYC.getId(), "meal");
                    await airlineService.addService(IG_DEL_BLR.getId(), "meal");
                    await airlineService.addService(IG_BLR_PAR.getId(), "meal");
                    await airlineService.addService(IG_PAR_LON.getId(), "meal");
                } catch (e) {
                    console.log(e.message);
                    throw new Error(e);
                }
            } catch (e) {
                console.log(e.message);
                throw new Error(e);
            }
        } catch (e) {
            console.log(e.message);
            throw new Error(e);
        }

        
        const flightSearchService = new FlightSearchService(flightService);

        await flightSearchService.search("DEL", "NYC");
        await flightSearchService.search("DEL", "NYC", "meal");
    }
}


Driver.main().catch(console.error);
