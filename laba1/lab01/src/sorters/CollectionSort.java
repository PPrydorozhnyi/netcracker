package sorters;

import java.util.Arrays;

/**
 * standard sort from the Java utils
 *
 * @author P.Pridorozhny
 */
public class CollectionSort extends Sort {

    @Override
    public void sort(int[] array) {

        if (dSort(array))
            return;

        Arrays.sort(array);

    }

}
