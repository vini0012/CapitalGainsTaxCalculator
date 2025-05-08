package serialization;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import enums.OperationType;
import model.Operation;

public class OperationDeserializer extends JsonDeserializer<Operation> {

    @Override
    public Operation deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String operationTypeStr = node.get("operation").asText();
        OperationType operationType = OperationType.valueOf(operationTypeStr.toUpperCase());

        BigDecimal unitCost = node.get("unit-cost").decimalValue();
        int quantity = node.get("quantity").intValue();

        return new Operation(operationType, unitCost, quantity);
    }
}

