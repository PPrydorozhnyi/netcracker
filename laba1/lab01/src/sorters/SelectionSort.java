package sorters;

/**
 * selection sort is a sorting algorithm, specifically an in-place comparison sort
 *
 * @see <a href="http://en.wikipedia.org/wiki/Selection_sort">Selection Sort on Wikipedia</a>
 *
 * @author P.Pridorozhny
 */
public class SelectionSort extends Sort {

    @Override
    public void sort(int[] array) {

        if (dSort(array)) {
            return;
        }

        int min;

        for (int i = 0; i < array.length - 1; i++) {

            min = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {

                    min = j;

                }
            }

            if(min != i) {

                swap(array, i , min);

            }
        }

    }
}
