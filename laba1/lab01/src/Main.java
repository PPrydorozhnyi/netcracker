import analyzer.Analyzer;

/**
 * Main method
 */
public class Main {

    private static Analyzer analyzer = Analyzer.getInstance();

    public static void main(String[] args) {

        analyzer.analyze();

    }

}
