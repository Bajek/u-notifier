package pl.ubytes.unotifier.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class EasyJetResponse {

    @JsonProperty("Months")
    List<EasyJetDate> months;


}
