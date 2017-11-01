package sorters;

/**
 * abstract class for hierarchy and reflection
 *
 * @author P.Pridorozhny
 */
public abstract class Sort {

    /**
     *method sorts input array
     *
     * @param array
     * array that will be sorted
     */
    public abstract void sort(int[] array);

    /**
     *utility method for swapping two elements of the array
     *
     * {@link #swap(int[], int, int )}
     *
     * @param array
     * array which will be changed
     * @param index1
     * index of the first element to swap
     * @param index2
     * index of the second element to swap
     */
    protected void swap(int array[], int index1, int index2) {

        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    /**
     * check input parameter for null value
     *
     * @param array
     * input array
     * @return
     * true - if input is null;
     * false - if input is not null
     */
    protected boolean dSort(int[] array) {

        return array == null || array.length <= 1;

    }

}
