package net.az3l1t.alphabet_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AlphabetServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlphabetServerApplication.class, args);
	}

}
