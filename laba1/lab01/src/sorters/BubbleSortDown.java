package sorters;

/**
 * bubble sort implementation from the last to the first element
 *
 * @author P.Pridorozhny
 */
public class BubbleSortDown extends BubbleSort {

    @Override
    public void sort(int[] array) {

        if(dSort(array))
            return;

        for (int i = array.length - 1; i > 0; i--) {

            for (int j = i - 1; j >= 0; j--) {

                if (array[i] < array[j]) {
                    swap(array, i, j);
                }
            }
        }

    }

}
