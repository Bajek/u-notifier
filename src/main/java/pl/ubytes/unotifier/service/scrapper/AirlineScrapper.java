package pl.ubytes.unotifier.service.scrapper;

import java.time.ZonedDateTime;
import java.util.List;

public interface AirlineScrapper {

    ZonedDateTime getLatestDate(String fromAirport, String toAirport);
    List<String> getAirports();
    List<String> getAirportsFrom(String fromAirport);
}
