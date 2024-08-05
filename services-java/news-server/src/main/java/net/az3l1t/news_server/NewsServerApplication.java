package net.az3l1t.news_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class NewsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsServerApplication.class, args);
	}

}
