package fillers;

import java.util.Random;

/**
 * filler util for arrays of integers
 *
 * @author P.Pridorozhny
 */
public class Filler {

    private static int[] resultArray;

    private Filler() {}

    /**
     * filling with sorted data
     *
     * {@link #sortedWithoutLastData(int)}
     * {@link #reverseSortedData(int)}
     * {@link #randomData(int)}
     *
     * @param size
     * size of the array
     * @return
     * array of integers
     *
     */
    @FillerAnnotation
    public static int[] sortedData(int size) {

        resultArray = new int[size];

        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = i;
        }

        return resultArray;
    }

    /**
     * filling with sorted data and last random element
     *
     * {@link #sortedData(int)}
     * {@link #reverseSortedData(int)}
     * {@link #randomData(int)}
     *
     * @param size
     * size of the array
     * @return
     * array of integers
     */
    @FillerAnnotation
    public static int[] sortedWithoutLastData(int size) {

        Random random = new Random();

        resultArray = new int[size];

        for (int i = 0; i < resultArray.length - 1; i++) {
            resultArray[i] = i;
        }

        resultArray[resultArray.length - 1] = random.nextInt();

        return resultArray;
    }

    /**
     * filling with reverse sorted data
     *
     * {@link #sortedWithoutLastData(int)}
     * {@link #sortedData(int)}
     * {@link #randomData(int)}
     *
     * @param size
     * size of the array
     * @return
     * array of integers
     */
    @FillerAnnotation
    public static int[] reverseSortedData(int size) {

        resultArray = new int[size];

        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = resultArray.length - 1 - i;
        }

        return resultArray;
    }


    /**
     * filling with random data
     *
     * {@link #sortedWithoutLastData(int)}
     * {@link #reverseSortedData(int)}
     * {@link #sortedData(int)}
     *
     * @param size
     * size of the array
     * @return
     * array of integers
     */
    @FillerAnnotation
    public static int[] randomData(int size) {
        Random random = new Random();

        resultArray = new int[size];

        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = random.nextInt();
        }

        return resultArray;
    }


}
