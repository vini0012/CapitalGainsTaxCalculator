package service.strategy;

import static utils.BigDecimalUtils.bigDecimalZero;

import model.Operation;
import model.TaxResult;
import service.TaxCalculationContext;

public final class BuyStrategy implements TaxCalculationStrategy {

    private static final TaxResult NO_TAX_RESULT = new TaxResult(bigDecimalZero());

    /**
     * Calculates and updates the context for a buy operation, adjusting the weighted average and total quantity of shares.
     */
    @Override
    public TaxResult calculate(Operation operation, TaxCalculationContext context) {
        if (context.isEmpty()) {
            context.setInitialWeightedAverage(operation.unitCost());
        } else {
            context.updateWeightedAverage(operation.unitCost(), operation.quantity());
        }

        context.addQuantity(operation.quantity());
        return NO_TAX_RESULT;
    }
}
