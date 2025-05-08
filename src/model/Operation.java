package model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import enums.OperationType;
import serialization.OperationDeserializer;

@JsonDeserialize(using = OperationDeserializer.class)
public record Operation(
        @JsonProperty("operation") OperationType operationType,
        @JsonProperty("unit-cost") BigDecimal unitCost,
        @JsonProperty("quantity") int quantity
) {

    public BigDecimal calculateGrossProfit(BigDecimal weightedAverage) {
        return unitCost.subtract(weightedAverage).multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateTotalValue() {
        return unitCost.multiply(BigDecimal.valueOf(quantity));
    }
}