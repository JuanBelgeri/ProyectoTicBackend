package service.users;

import model.users.PaymentMethod;
import repository.users.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    // Create payment method
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        // If this is set as main, unset other main payment methods
        if (paymentMethod.getIsMain()) {
            List<PaymentMethod> mainMethods = paymentMethodRepository.findByUserEmail(paymentMethod.getUserEmail());
            mainMethods.forEach(method -> {
                method.setIsMain(false);
                paymentMethodRepository.save(method);
            });
        }
        return paymentMethodRepository.save(paymentMethod);
    }

    // Get all payment methods for user
    public List<PaymentMethod> getPaymentMethodsByUser(String userEmail) {
        return paymentMethodRepository.findByUserEmail(userEmail);
    }

    // Get main payment method for user
    public Optional<PaymentMethod> getMainPaymentMethod(String userEmail) {
        return paymentMethodRepository.findByUserEmailAndIsMainTrue(userEmail);
    }

    // Update payment method
    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod updatedMethod) {
        PaymentMethod method = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment method not found"));

        method.setType(updatedMethod.getType());
        method.setCardHolder(updatedMethod.getCardHolder());
        method.setExpirationDate(updatedMethod.getExpirationDate());

        if (updatedMethod.getIsMain() && !method.getIsMain()) {
            // Unset other main payment methods
            List<PaymentMethod> mainMethods = paymentMethodRepository.findByUserEmail(method.getUserEmail());
            mainMethods.forEach(m -> {
                m.setIsMain(false);
                paymentMethodRepository.save(m);
            });
            method.setIsMain(true);
        }

        return paymentMethodRepository.save(method);
    }

    // Delete payment method
    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}