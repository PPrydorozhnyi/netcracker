package sorters;

/**
 * standard bubble sort implementation from the first to the last element
 *
 * @author P.Pridorozhny
 */
public class BubbleSortUp extends BubbleSort {

    @Override
    public void sort(int[] array) {

        if(dSort(array))
            return;

        for (int i = 0; i < array.length - 1; i++) {

            for (int j = i + 1; j < array.length; j++) {

                if (array[i] > array[j]) {
                    swap(array, i, j);
                }
            }

        }

    }

//    @Override
//    public void sort(int[] array) {
//        super.sort(array, true);
//    }
}
