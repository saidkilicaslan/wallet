package com.inghub.wallet.model;

import com.inghub.wallet.validation.UniqueTckn;
import com.inghub.wallet.validation.ValidTckn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreate {

    @Schema(description = "Name of the customer", example = "Doe" ,requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "name is mandatory")
    private String name;

    @Schema(description = "Surname of the customer", example = "John" ,requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "surname is mandatory")
    private String surname;

    @Schema(description = "National identity number of the customer", minLength = 11, maxLength = 11, example = "12345678901" ,requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "tckn is mandatory")
    @UniqueTckn
    @ValidTckn
    private String tckn;
}
