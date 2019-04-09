package pl.ubytes.unotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class EasyJetDate {

    @JsonProperty("MonthNumber")
    private int monthNumber;
    @JsonProperty("YearNumber")
    private int yearNumber;
    @JsonProperty("FlightDates")
    List<Integer> flightDates;



}
