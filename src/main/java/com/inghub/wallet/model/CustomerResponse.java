package com.inghub.wallet.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    @Schema(description = "Name of the customer", example = "Doe")
    private String name;

    @Schema(description = "Surname of the customer", example = "John")
    private String surname;

    @Schema(description = "National identity number of the customer", example = "12345678901")
    private String tckn;
}
