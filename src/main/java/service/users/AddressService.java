package service.users;

import model.users.Address;
import repository.users.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // Create address
    public Address createAddress(Address address) {
        // If this is set as main, unset other main addresses
        if (address.getIsMain()) {
            List<Address> mainAddresses = addressRepository.findByUserEmail(address.getUserEmail());
            mainAddresses.forEach(addr -> {
                addr.setIsMain(false);
                addressRepository.save(addr);
            });
        }
        return addressRepository.save(address);
    }

    // Get all addresses for user
    public List<Address> getAddressesByUser(String userEmail) {
        return addressRepository.findByUserEmail(userEmail);
    }

    // Get main address for user
    public Optional<Address> getMainAddress(String userEmail) {
        return addressRepository.findByUserEmailAndIsMainTrue(userEmail);
    }

    // Update address
    public Address updateAddress(Long id, Address updatedAddress) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        address.setStreet(updatedAddress.getStreet());
        address.setNumber(updatedAddress.getNumber());
        address.setApartment(updatedAddress.getApartment());
        address.setCity(updatedAddress.getCity());
        address.setPostalCode(updatedAddress.getPostalCode());
        address.setAdditionalInfo(updatedAddress.getAdditionalInfo());

        if (updatedAddress.getIsMain() && !address.getIsMain()) {
            // Unset other main addresses
            List<Address> mainAddresses = addressRepository.findByUserEmail(address.getUserEmail());
            mainAddresses.forEach(addr -> {
                addr.setIsMain(false);
                addressRepository.save(addr);
            });
            address.setIsMain(true);
        }

        return addressRepository.save(address);
    }

    // Delete address
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}