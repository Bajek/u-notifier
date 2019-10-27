package pl.ubytes.unotifier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ubytes.unotifier.model.EasyJetDate;
import pl.ubytes.unotifier.model.EasyJetResponse;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@Service
public class SchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private static final String URL = "https://www.easyjet.com/ejcms/cache15m/api/routedates/get/?destinationIata=GVA&originIata=KRK";

    private LocalDate lastCheckedDate = LocalDate.MIN;

    private final JavaMailSender javaMailSender;

    private final String[] recipients;

    @Autowired
    public SchedulerService(JavaMailSender javaMailSender, @Value("${application.notification.recipients}") String[] recipients) {
        this.javaMailSender = javaMailSender;
        this.recipients = recipients;
    }

    @Scheduled(fixedRateString = "PT5M")
    private void checkEasyJetDates() throws MessagingException {
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
            LOGGER.info("New dates has been released");
            lastCheckedDate = lastDate;
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipients);
            helper.setPriority(1);
            helper.setSubject("Easy Jet dates are available!");
            helper.setText("Available till " + lastDate.toString() +  " go to https://www.easyjet.com");
            javaMailSender.send(message);
            LOGGER.info("Mail has been sent");

        }

    }
}
