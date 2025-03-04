package ma.tr.docnearme.faker;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.modules.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataGeneratorConfig {


    private final UserRepository userRepository;


    private final ApplicationContext applicationContext;

    /**
     * Command line runner that generates fake users when the application starts
     * with the 'generate-data' profile active
     */
    @Bean
    @Profile("generate-data")
    public CommandLineRunner generateDataRunner() {
        return args -> {
            System.out.println("Generating fake user data...");

            // Generate 10 users of each role
            List<User> users = UserDataGenerator.createMixedRoleUsers(10);
            userRepository.saveAll(users);

            System.out.println("Successfully generated " + users.size() + " fake users!");

            // Check if running with just the generate-data profile to exit after generation
            if (isRunningStandaloneGenerator()) {
                System.out.println("Data generation complete. Shutting down...");
                SpringApplication.exit(applicationContext, () -> 0);
            }
        };
    }

    /**
     * Check if we're running only for data generation (exit after) or as part of the app startup
     */
    private boolean isRunningStandaloneGenerator() {
        for (String profile : applicationContext.getEnvironment().getActiveProfiles()) {
            if (profile.equals("dev") || profile.equals("prod")) {
                return false;
            }
        }
        return true;
    }
}
