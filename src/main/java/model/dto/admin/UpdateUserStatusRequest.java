package model.dto.admin;

import lombok.Data;

@Data
public class UpdateUserStatusRequest {
    private Boolean active;
}