package com.pan.domain.resource.payload;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record TransactionRequest(@NotBlank String transactionId, @NotBlank String customerDocument,
                                 @NotNull Integer transactionValue,
                                 @NotNull @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss", locale = "Locale.ENGLISH")
                                 LocalDateTime createdAt,
                                 @NotBlank String brand) {

}
