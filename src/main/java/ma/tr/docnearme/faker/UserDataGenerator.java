package ma.tr.docnearme.faker;

import com.github.javafaker.Faker;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.modules.user.UserRole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class UserDataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));
    private static final Random random = new Random();

    /**
     * Generate a single random user
     * @param role specific role or null for random
     * @return randomly generated User
     */
    public static User createRandomUser(UserRole role) {
        // Create verification code for some users
        String verificationCode = random.nextBoolean() ? generateRandomCode() : null;

        // Set expiration time for verification code
        LocalDateTime verificationCodeExpiresAt = verificationCode != null ?
                LocalDateTime.now().plusDays(1) : null;

        // Set random enabled status (weighted toward enabled)
        boolean enabled = true;

        // Set role (use provided or random if null)
        UserRole userRole = role != null ? role :
                UserRole.values()[random.nextInt(UserRole.values().length)];

        // Generate phone format like +212 6XX XXX XXX (Morocco format)
        String phoneNumber = "+212 " + (6 + random.nextInt(3)) +
                String.format("%02d", random.nextInt(100)) + " " +
                String.format("%03d", random.nextInt(1000)) + " " +
                String.format("%03d", random.nextInt(1000));

        return User.builder()
                .id(UUID.randomUUID())
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .password("$2a$10$" + faker.crypto().md5())  // Fake bcrypt hash format
                .verificationCode(verificationCode)
                .verificationCodeExpiresAt(verificationCodeExpiresAt)
                .enabled(enabled)
                .role(userRole)
                .phoneNumber(phoneNumber)
                .build();
    }

    /**
     * Generate multiple random users
     * @param count number of users to generate
     * @param role specific role or null for random roles
     * @return List of randomly generated Users
     */
    public static List<User> createRandomUsers(int count, UserRole role) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(createRandomUser(role));
        }
        return users;
    }

    /**
     * Generate a mix of users with different roles
     * @param countPerRole number of users per role
     * @return List of users with different roles
     */
    public static List<User> createMixedRoleUsers(int countPerRole) {
        List<User> users = new ArrayList<>();
        for (UserRole role : UserRole.values()) {
            users.addAll(createRandomUsers(countPerRole, role));
        }
        return users;
    }

    /**
     * Generate a 6-digit verification code
     */
    private static String generateRandomCode() {
        return String.format("%06d", random.nextInt(1000000));
    }

    /**
     * Example usage
     */
    public static void main(String[] args) {
        // Generate a single random doctor
        User doctor = createRandomUser(UserRole.MEDICINE);
        System.out.println("Random doctor: " + doctor.getName() + ", " + doctor.getEmail());

        // Generate 5 patients
        List<User> patients = createRandomUsers(5, UserRole.PATIENT);
        System.out.println("Generated " + patients.size() + " patients");

        // Generate 3 users of each role
        List<User> mixedUsers = createMixedRoleUsers(3);
        System.out.println("Generated " + mixedUsers.size() + " mixed role users");
    }
}
