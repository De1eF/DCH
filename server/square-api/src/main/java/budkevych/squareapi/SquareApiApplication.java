package budkevych.squareapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "budkevych.squareapi")
public class SquareApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SquareApiApplication.class, args);
	}

}
