package mar.cod.flightback.api.pojo;

import mar.cod.flightback.utils.FlightClass;

public class FlightActionPojo {

    private Long userId;
    private Long flightId;
    private FlightClass flightClass;
    private Float price;
    private String reason;

    public FlightActionPojo() {
    }

    public FlightActionPojo(Long userId, Long flightId, FlightClass flightClass, Float price, String reason) {
        this.userId = userId;
        this.flightId = flightId;
        this.flightClass = flightClass;
        this.price = price;
        this.reason = reason;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public FlightClass getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(FlightClass flightClass) {
        this.flightClass = flightClass;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
