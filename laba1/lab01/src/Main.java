import analyzer.Analyzer;

/**
 * Main method
 */
public class Main {

    private static Analyzer analyzer = Analyzer.getInstance();

    public static void main(String[] args) {

//        BubbleSortUp bubbleSortUp = new BubbleSortUp();
//
//        int[] array = Filler.reverseSortedData(10);
//
//        for (int i = 0; i < array.length; ++i) {
//            System.out.println(array[i]);
//        }
//
//        bubbleSortUp.sort(array);
//
//        for (int i = 0; i < array.length; ++i) {
//            System.out.println(array[i]);
//        }

        analyzer.analyze();

    }

}
