package service.security;

import model.users.PaymentMethod;
import repository.users.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Service
@Transactional
public class CardValidationService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    // Simple encryption key
    private static final String ENCRYPTION_KEY = "PizzumBurgum2025";

    public Map<String, Object> validateCard(String cardNumber) {
        Map<String, Object> response = new HashMap<>();

        // Remove spaces and dashes
        String cleanCardNumber = cardNumber.replaceAll("[\\s-]", "");

        // Validate card number format (simple validation)
        if (!isValidCardNumber(cleanCardNumber)) {
            response.put("valid", false);
            response.put("error", "Invalid card number format");
            return response;
        }

        // Search for card in database (last 4 digits)
        String last4Digits = cleanCardNumber.substring(cleanCardNumber.length() - 4);
        String encryptedSearch = "************" + last4Digits;

        Optional<PaymentMethod> paymentMethodOpt = paymentMethodRepository.findAll().stream()
                .filter(pm -> pm.getCardNumberEncrypted().equals(encryptedSearch))
                .findFirst();

        if (paymentMethodOpt.isPresent()) {
            PaymentMethod pm = paymentMethodOpt.get();

            response.put("valid", true);
            response.put("cardHolder", pm.getCardHolder());
            response.put("cardType", pm.getType());
            response.put("expirationDate", pm.getExpirationDate());
            response.put("last4Digits", last4Digits);
            response.put("userEmail", pm.getUserEmail());

            // Card brand detection
            response.put("brand", detectCardBrand(cleanCardNumber));

        } else {
            response.put("valid", false);
            response.put("error", "Card not found in our system");
        }

        return response;
    }

    // Validate card number using Luhn algorithm
    public boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }

        // Check if all characters are digits
        if (!cardNumber.matches("\\d+")) {
            return false;
        }

        // Luhn algorithm
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

    // Detect card brand based on number
    private String detectCardBrand(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            return "VISA";
        } else if (cardNumber.startsWith("5")) {
            return "MASTERCARD";
        } else if (cardNumber.startsWith("3")) {
            return "AMERICAN_EXPRESS";
        } else if (cardNumber.startsWith("6")) {
            return "DISCOVER";
        } else {
            return "UNKNOWN";
        }
    }

    // Encrypt card number
    public String encryptCardNumber(String cardNumber) {
        String cleanNumber = cardNumber.replaceAll("[\\s-]", "");
        if (cleanNumber.length() >= 4) {
            return "************" + cleanNumber.substring(cleanNumber.length() - 4);
        }
        return "************";
    }
}