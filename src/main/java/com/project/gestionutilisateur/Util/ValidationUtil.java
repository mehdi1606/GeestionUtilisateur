package com.project.gestionutilisateur.Util;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern EMAIL_REGEX = Pattern.compile(EMAIL_PATTERN);

    private static final String PHONE_PATTERN = "^[0-9+\\-\\s]{8,15}$";
    private static final Pattern PHONE_REGEX = Pattern.compile(PHONE_PATTERN);

    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_REGEX.matcher(email).matches();
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return true; // Téléphone optionnel
        }
        return PHONE_REGEX.matcher(phoneNumber).matches();
    }

    public boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        // Minimum 6 caractères
        if (password.length() < 6) {
            return false;
        }

        // Au moins une lettre et un chiffre
        boolean hasLetter = password.chars().anyMatch(Character::isLetter);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        return hasLetter && hasDigit;
    }

    public boolean isValidLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            return false;
        }

        // Entre 3 et 50 caractères, lettres, chiffres et underscore seulement
        String pattern = "^[a-zA-Z0-9_]{3,50}$";
        return Pattern.matches(pattern, login);
    }

    public String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replaceAll("\\s+", " ");
    }

    public boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        // Lettres, espaces, tirets et apostrophes uniquement
        String pattern = "^[a-zA-ZÀ-ÿ\\s\\-']{2,50}$";
        return Pattern.matches(pattern, name.trim());
    }
}
