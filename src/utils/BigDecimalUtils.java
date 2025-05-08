package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

    public static BigDecimal roundToTwoDecimalPlaces(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal bigDecimalZero() {
        return roundToTwoDecimalPlaces(BigDecimal.ZERO);
    }
}
