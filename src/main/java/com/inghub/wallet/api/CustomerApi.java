package com.inghub.wallet.api;

import com.inghub.wallet.exception.ApiException;
import com.inghub.wallet.exception.IllegalRequestParamException;
import com.inghub.wallet.model.CustomerCreate;
import com.inghub.wallet.model.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Tag(name = "Customer", description = "API for customer operations")
public interface CustomerApi {
    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Creates a Customer",
            description = "This operation creates a Customer entity.",
            tags = {"Customer"})
    ResponseEntity<CustomerResponse> create(@Parameter(description = "Customer to be created", required = true) CustomerCreate customerCreate);


    @GetMapping(value = "/customer/{tckn}", produces = "application/json")
    @Operation(
            summary = "Get Customer by TCKN",
            description = "This operation retrieves a Customer entity by TCKN.",
            tags = {"Customer"})
    ResponseEntity<CustomerResponse> getCustomerByTckn(@Parameter(description = "TCKN of the customer", required = true) String tckn) throws ApiException;


    @GetMapping(value = "/customer", produces = "application/json")
    @Operation(
            summary = "List Customers",
            description = "This operation lists Customer entities.",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "All data returned in one page"),
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "206", description = "Partial data returned (pagination)", headers = {
                            @Header(name = "X-Total-Count", description = "Total number of customers", schema = @Schema(type = "integer", example = "17847578")),
                            @Header(name = "X-Total-Pages", description = "Total number of pages", schema = @Schema(type = "integer", example = "15")),
                            @Header(name = "X-Current-Page", description = "Current page number", schema = @Schema(type = "integer", example = "1")),
                            @Header(name = "X-Page-Size", description = "Size of the page", schema = @Schema(type = "integer", example = "10"))
                    }),
            })
    ResponseEntity<List<CustomerResponse>> listCustomers(
            @Parameter(description = "Name of the customer to search") String name,
            @Parameter(description = "Surname of the customer to search") String surname,
            @Parameter(description = "TCKN of the customer to search") String tckn,
            @Parameter(description = "Page number. Default page number is 0") Integer page,
            @Parameter(description = "Page size. Default page size is 10") Integer size,
            HttpServletResponse response) throws ApiException;
}
