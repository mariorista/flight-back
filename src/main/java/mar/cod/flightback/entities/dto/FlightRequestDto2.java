package mar.cod.flightback.entities.dto;

import mar.cod.flightback.entities.Flight;
import mar.cod.flightback.entities.FlightUserConnect;

public class FlightRequestDto2 {
    private Flight flight;
    private FlightUserConnect fuc;

    public FlightRequestDto2() {
    }

    public FlightRequestDto2( Flight flight, FlightUserConnect fuc) {
        this.flight = flight;
        this.fuc = fuc;
    }


    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public FlightUserConnect getFuc() {
        return fuc;
    }

    public void setFuc(FlightUserConnect fuc) {
        this.fuc = fuc;
    }

}
