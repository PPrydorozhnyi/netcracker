package sorters;

/**
 * quicksort or partition-exchange sort, is a fast sorting algorithm, which is using divide and conquer algorithm.
 *
 * @see <a href="http://www.java2novice.com/java-sorting-algorithms/quick-sort/">Quick Sort explanation</a>
 *
 * @author P.Pridorozhny
 */
public class QuickSort extends Sort {

    private int array[];
    private int length;

    public void sort(int[] inputArr) {

        if (inputArr == null || inputArr.length == 0) {
            return;
        }
        this.array = inputArr;
        length = inputArr.length;
        quickSort(0, length - 1);
    }

    /**
     * In each iteration, we will identify a number from left side which
     * is greater then the pivot value, and also we will identify a number
     * from right side which is less then the pivot value. Once the search
     * is done, then we exchange both numbers.
     *
     * @param lowerIndex
     * lower border for sort
     * @param higherIndex
     * higher border for sort
     */
    private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        int pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        // Divide into two arrays
        while (i <= j) {

            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(array, i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }

}
