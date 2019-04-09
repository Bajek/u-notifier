package pl.ubytes.unotifier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ubytes.unotifier.model.EasyJetDate;
import pl.ubytes.unotifier.model.EasyJetResponse;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@Service
public class SchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private static final String URL = "https://www.easyjet.com/ejcms/cache15m/api/routedates/get/?destinationIata=GVA&originIata=KRK";

    private LocalDate lastCheckedDate = LocalDate.MIN;


    @Scheduled(fixedRate = 5000)
    private void checkEasyJetDates() {
        LOGGER.info("Checking EasyJet dates");
        RestTemplate restTemplate = new RestTemplate();
        EasyJetResponse easyJetResponse = restTemplate.getForObject(URL, EasyJetResponse.class);


        final EasyJetDate lastEasyJetDate = Objects.requireNonNull(easyJetResponse).getMonths().stream()
                .max(Comparator.comparing(EasyJetDate::getYearNumber).thenComparing(EasyJetDate::getMonthNumber))
                .orElseThrow(() -> new IllegalStateException("Cannot obtain date"));

        final Integer maxDay = lastEasyJetDate.getFlightDates().stream()
                .max(Integer::compare)
                .orElseThrow(() -> new IllegalStateException("Cannot obtain day"));

        final LocalDate lastDate = LocalDate.of(lastEasyJetDate.getYearNumber(), lastEasyJetDate.getMonthNumber(), maxDay);

        if (lastDate.isAfter(lastCheckedDate)) {
            lastCheckedDate = lastDate;
            LOGGER.info("New dates has been released");
        }

    }
}
