import java.util.stream.Stream;

public class CapitalGainsCalculatorApplicationTest {

    private final CapitalGainsCalculatorApplication application = new CapitalGainsCalculatorApplication();

    public static void main(String[] args) {
        CapitalGainsCalculatorApplicationTest test = new CapitalGainsCalculatorApplicationTest();
        test.testProcessAndFormatOperations_validInput();
        test.testProcessAndFormatOperations_emptyInput();
        test.testSplitOperations_multipleJsonOperations();
        test.testSplitOperations_singleJsonOperation();
        test.testSplitOperations_emptyInput();
        test.testHandleOperationError();
        test.testProcessAndFormatOperations_case1();
        test.testProcessAndFormatOperations_case2();
        test.testProcessAndFormatOperations_case3();
        test.testProcessAndFormatOperations_case4();
        test.testProcessAndFormatOperations_case5();
        test.testProcessAndFormatOperations_case6();
        test.testProcessAndFormatOperations_case7();
        test.testProcessAndFormatOperations_case8();
        test.testProcessAndFormatOperations_case9();

        System.out.println("All tests passed.");
    }

    public void testProcessAndFormatOperations_validInput() {
        String validJson = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":100}," +
                "{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":50}]";
        String result = application.processAndFormatOperations(validJson);

        assert result != null && !result.isEmpty() : "Expected non-empty result for valid input";
    }

    public void testProcessAndFormatOperations_emptyInput() {
        String emptyJson = "[]";
        String result = application.processAndFormatOperations(emptyJson);

        assert "{}".equals(result) : "Expected empty JSON result for empty input";
    }

    public void testSplitOperations_multipleJsonOperations() {
        String input = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":100}][{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":50}]";
        Stream<String> result = application.splitOperations(input);

        assert result.count() == 2 : "Expected two separate JSON operations";
    }

    public void testSplitOperations_singleJsonOperation() {
        String input = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":100}]";
        Stream<String> result = application.splitOperations(input);

        assert result.count() == 1 : "Expected one JSON operation";
    }

    public void testSplitOperations_emptyInput() {
        String input = "";
        Stream<String> result = application.splitOperations(input);

        assert result.findAny().isEmpty() : "Expected no JSON operations for empty input";
    }

    public void testHandleOperationError() {
        Exception exception = new Exception("Test exception");
        String result = CapitalGainsCalculatorApplication.handleOperationError(exception);

        assert "{}".equals(result) : "Expected empty JSON result on error handling";
    }

    public void testProcessAndFormatOperations_case1() {
        String json = "[" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":100}," +
                "{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":50}," +
                "{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":50}" +
                "]";
        String result = application.processAndFormatOperations(json);
        String expectedResult = "[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00}]";
        assert result.equals(expectedResult) : "Unexpected result: " + result;
    }

    public void testProcessAndFormatOperations_case2() {
        String json = "[" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":5000}," +
                "{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\":5000}" +
                "]";
        String result = application.processAndFormatOperations(json);
        String expectedResult = "[{\"tax\":0.00},{\"tax\":10000.00},{\"tax\":0.00}]";
        assert result.equals(expectedResult) : "Unexpected result: " + result;
    }

    public void testProcessAndFormatOperations_case3() {
        String json = "[" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":100}," +
                "{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":50}," +
                "{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":50}" +
                "] [" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":5000}," +
                "{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\":5000}" +
                "]";
        String result = application.processAndFormatOperations(json);
        String expectedResult = "[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00}][{\"tax\":0.00},{\"tax\":10000.00},{\"tax\":0.00}]";
        assert result.equals(expectedResult) : "Unexpected result: " + result;
    }

    public void testProcessAndFormatOperations_case4() {
        String json = "[" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\":5000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":3000}" +
                "]";
        String result = application.processAndFormatOperations(json);
        String expectedResult = "[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":1000.00}]";
        assert result.equals(expectedResult) : "Unexpected result: " + result;
    }

    public void testProcessAndFormatOperations_case5() {
        String json = "[" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}," +
                "{\"operation\":\"buy\", \"unit-cost\":25.00, \"quantity\":5000}," +
                "{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":10000}" +
                "]";
        String result = application.processAndFormatOperations(json);
        String expectedResult = "[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00}]";
        assert result.equals(expectedResult) : "Unexpected result: " + result;
    }

    public void testProcessAndFormatOperations_case6() {
        String json = "[" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}," +
                "{\"operation\":\"buy\", \"unit-cost\":25.00, \"quantity\":5000}," +
                "{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\":5000}" +
                "]";
        String result = application.processAndFormatOperations(json);
        String expectedResult = "[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":10000.00}]";
        assert result.equals(expectedResult) : "Unexpected result: " + result;
    }

    public void testProcessAndFormatOperations_case7() {
        String json = "[" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":2.00, \"quantity\":5000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":2000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":2000}," +
                "{\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\":1000}" +
                "]";
        String result = application.processAndFormatOperations(json);
        String expectedResult = "[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":3000.00}]";
        assert result.equals(expectedResult) : "Unexpected result: " + result;
    }

    public void testProcessAndFormatOperations_case8() {
        String json = "[" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":2.00, \"quantity\":5000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":2000}," +
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":2000}," +
                "{\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\":1000}," +
                "{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\":10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":5000}," +
                "{\"operation\":\"sell\", \"unit-cost\":30.00, \"quantity\":4350}," +
                "{\"operation\":\"sell\", \"unit-cost\":30.00, \"quantity\":650}" +
                "]";
        String result = application.processAndFormatOperations(json);
        String expectedResult = "[{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":0.00},{\"tax\":3000.00}," +
                "{\"tax\":0.00},{\"tax\":0.00},{\"tax\":3700.00},{\"tax\":0.00}]";
        assert result.equals(expectedResult) : "Unexpected result: " + result;
    }

    public void testProcessAndFormatOperations_case9() {
        String json = "[" +
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":50.00, \"quantity\":10000}," +
                "{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\":10000}," +
                "{\"operation\":\"sell\", \"unit-cost\":50.00, \"quantity\":10000}" +
                "]";
        String result = application.processAndFormatOperations(json);
        String expectedResult = "[{\"tax\":0.00},{\"tax\":80000.00},{\"tax\":0.00},{\"tax\":60000.00}]";
        assert result.equals(expectedResult) : "Unexpected result: " + result;
    }
}
