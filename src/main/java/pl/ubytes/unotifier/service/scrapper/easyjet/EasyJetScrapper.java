package pl.ubytes.unotifier.service.scrapper.easyjet;

import pl.ubytes.unotifier.service.scrapper.AirlineScrapper;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class EasyJetScrapper implements AirlineScrapper {
    @Override
    public ZonedDateTime getLatestDate(String fromAirport, String toAirport) {
        return null;
    }

    @Override
    public List<String> getAirports() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getAirportsFrom(String fromAirport) {
        return new ArrayList<>();
    }
}
