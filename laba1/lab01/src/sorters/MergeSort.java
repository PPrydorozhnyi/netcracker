package sorters;

/**
 *merge sort is an efficient, general-purpose, comparison-based sorting algorithm
 *
 * @see <a href="https://en.wikipedia.org/wiki/Merge_sort">Merge Sort</a>
 * @see <a href="//www.hackerearth.com/practice/algorithms/sorting/merge-sort/visualize/">Merge Sort visualization</a>
 *
 * @author P.Pridorozhny
 */
public class MergeSort extends Sort {
    @Override
    public void sort(int[] array) {
        if (dSort(array))
            return;

        int elementsInA1 = array.length / 2;
        int elementsInA2 = elementsInA1;

        if ((array.length % 2) == 1) {
            elementsInA2 += 1;
        }

        int arr1[] = new int[elementsInA1];
        int arr2[] = new int[elementsInA2];

        System.arraycopy(array, 0, arr1, 0, elementsInA1);
        System.arraycopy(array, elementsInA1, arr2, 0, elementsInA2);

        sort(arr1);
        sort(arr2);

        int i = 0, j = 0, k = 0;

        while (arr1.length != j && arr2.length != k) {
            if (arr1[j] < arr2[k]) {

                array[i] = arr1[j];
                i++;
                j++;

            }
            else {

                array[i] = arr2[k];
                i++;
                k++;

            }
        }
        while (arr1.length != j) {

            array[i] = arr1[j];
            i++;
            j++;

        }
        while (arr2.length != k) {

            array[i] = arr2[k];
            i++;
            k++;

        }
    }
}
