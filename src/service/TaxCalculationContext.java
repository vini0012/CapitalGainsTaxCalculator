package service;

import static utils.BigDecimalUtils.bigDecimalZero;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculationContext {

    private BigDecimal weightedAverage = BigDecimal.ZERO;
    private int totalQuantity = 0;
    private BigDecimal accumulatedLoss = BigDecimal.ZERO;

    public BigDecimal getWeightedAverage() {
        return weightedAverage;
    }

    public boolean isEmpty() {
        return totalQuantity == 0;
    }

    public void updateQuantityAfterSell(int soldQuantity) {
        this.totalQuantity -= soldQuantity;
    }

    public void addQuantity(int quantity) {
        this.totalQuantity += quantity;
    }

    public void setInitialWeightedAverage(BigDecimal newAverage) {
        this.weightedAverage = newAverage;
    }

    public void updateWeightedAverage(BigDecimal purchaseCost, int purchaseQuantity) {
        BigDecimal currentTotalCost = this.weightedAverage.multiply(BigDecimal.valueOf(this.totalQuantity));
        BigDecimal newTotalCost = currentTotalCost.add(purchaseCost.multiply(BigDecimal.valueOf(purchaseQuantity)));
        this.totalQuantity += purchaseQuantity;
        this.weightedAverage = newTotalCost.divide(BigDecimal.valueOf(this.totalQuantity), 2, RoundingMode.HALF_UP);
    }

    public void accumulateLoss(BigDecimal loss) {
        this.accumulatedLoss = this.accumulatedLoss.add(loss.abs());
    }

    public BigDecimal applyAccumulatedLoss(BigDecimal profit) {
        BigDecimal adjustedProfit = profit.subtract(accumulatedLoss);
        if (adjustedProfit.compareTo(BigDecimal.ZERO) > 0) {
            this.accumulatedLoss = BigDecimal.ZERO;
            return adjustedProfit;
        } else {
            this.accumulatedLoss = accumulatedLoss.subtract(profit);
            return bigDecimalZero();
        }
    }
}