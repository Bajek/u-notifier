package pl.ubytes.unotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UNotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(UNotifierApplication.class, args);
	}

}
