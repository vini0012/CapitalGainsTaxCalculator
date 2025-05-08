package service.strategy;

import static utils.BigDecimalUtils.bigDecimalZero;

import java.math.BigDecimal;
import java.util.List;

import model.Operation;
import model.TaxResult;
import service.TaxCalculationContext;

public class OperationHandlerFactory {

    private static final BigDecimal EXEMPTION_THRESHOLD = new BigDecimal("20000.00");
    private static final TaxResult NO_TAX_RESULT = new TaxResult(bigDecimalZero());

    public static List<SellOperationHandler> createHandlers() {
        return List.of(new ExemptHandlerSell(), new LossHandlerSell());
    }

    /**
     * Handler for operations that are exempt from tax due to total value below threshold.
     */
    private static class ExemptHandlerSell implements SellOperationHandler {

        @Override
        public boolean isApplicable(Operation operation, TaxCalculationContext context) {
            return operation.calculateTotalValue().compareTo(EXEMPTION_THRESHOLD) <= 0;
        }

        @Override
        public TaxResult handle(Operation operation, TaxCalculationContext context) {
            context.accumulateLoss(calculateProfit(operation, context).abs());
            context.updateQuantityAfterSell(operation.quantity());
            return NO_TAX_RESULT;
        }
    }

    /**
     * Handler for operations that result in a loss, allowing loss to accumulate for future gains.
     */
    private static class LossHandlerSell implements SellOperationHandler {

        @Override
        public boolean isApplicable(Operation operation, TaxCalculationContext context) {
            return isLoss(calculateProfit(operation, context));
        }

        @Override
        public TaxResult handle(Operation operation, TaxCalculationContext context) {
            BigDecimal grossProfit = calculateProfit(operation, context);
            context.accumulateLoss(grossProfit);
            context.updateQuantityAfterSell(operation.quantity());
            return NO_TAX_RESULT;
        }

        private boolean isLoss(BigDecimal profit) {
            return profit.compareTo(BigDecimal.ZERO) <= 0;
        }
    }

    // Default method for calculating profit, used by both handlers
    public interface SellOperationHandler {
        boolean isApplicable(Operation operation, TaxCalculationContext context);
        TaxResult handle(Operation operation, TaxCalculationContext context);

        default BigDecimal calculateProfit(Operation operation, TaxCalculationContext context) {
            return operation.calculateGrossProfit(context.getWeightedAverage());
        }
    }
}
