package model.dto.user;

import lombok.Data;

@Data
public class UpdateAddressRequest {
    private String street;
    private String number;
    private String apartment;
    private String city;
    private String postalCode;
    private String additionalInfo;
    private Boolean isMain;
}