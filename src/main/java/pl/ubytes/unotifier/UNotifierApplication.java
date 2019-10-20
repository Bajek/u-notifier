package pl.ubytes.unotifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class UNotifierApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(UNotifierApplication.class, args);
		} catch (Exception e) {
			log.error("Cannot start application", e);
			System.exit(-1);
		}
	}

}
