import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

import controller.CapitalGainsCalculatorController;
import model.TaxResult;

public class CapitalGainsCalculatorApplication {

    private static final CapitalGainsCalculatorController controller = new CapitalGainsCalculatorController();

    public static void main(String[] args) {
        try {
            new CapitalGainsCalculatorApplication().run();
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void run() throws Exception {
        try (BufferedReader reader = createBufferedReader()) {
            processInputLines(reader)
                    .map(this::processAndFormatOperations)
                    .forEach(System.out::println);
        }
    }

    /**
     * Configures a BufferedReader to read from standard input.
     */
    private BufferedReader createBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Processes input lines and separates JSON operations into distinct operations.
     */
    private Stream<String> processInputLines(BufferedReader reader) {
        return reader.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .flatMap(this::splitOperations);
    }

    /**
     * Splits multiple JSON operations on a line into individual operations.
     */
    protected Stream<String> splitOperations(String input) {
        return Stream.of(input.split("]\\s*\\["))
                .map(json -> json.startsWith("[") ? json : "[" + json)
                .map(json -> json.endsWith("]") ? json : json + "]");
    }

    /**
     * Safely processes JSON operations and converts them to a String representation.
     */
    protected String processAndFormatOperations(String operationsJson) {
        try {
            List<TaxResult> results = controller.processOperations(operationsJson);
            return controller.convertResultsToJson(results);
        } catch (Exception e) {
            return handleOperationError(e);
        }
    }

    /**
     * Handles operation-specific errors by returning an empty JSON.
     */
    protected static String handleOperationError(Exception e) {
        System.err.println("Error processing operation: " + e.getMessage());
        e.printStackTrace();
        return "{}";
    }

    /**
     * Handles application initialization errors.
     */
    private static void handleError(Exception e) {
        System.err.println("Error starting application: " + e.getMessage());
        e.printStackTrace();
    }
}