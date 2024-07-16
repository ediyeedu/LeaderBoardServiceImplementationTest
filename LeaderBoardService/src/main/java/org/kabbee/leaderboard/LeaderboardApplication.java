package org.kabbee.leaderboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "org.kabbee.leaderboard.repository")
public class LeaderboardApplication {

    public static void main(String[] args) {

        SpringApplication.run(LeaderboardApplication.class, args);
    }

}
