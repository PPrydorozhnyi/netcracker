package fillers;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import sorters.*;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * fillers test
 *
 * @see Filler
 * @see BubbleSortDownTest
 * @see BubbleSortUpTest
 * @see CollectionSortTest
 * @see MergeSortTest
 * @see QuickSortTest
 * @see SelectionSortTest
 *
 * @author P.Pridorozhny
 */
class FillerTest {

    private int[] array;
    private int[] clone;

    private void copyAndSort() {

        clone = new int[array.length];

        System.arraycopy(array, 0, clone, 0, array.length);

        Arrays.sort(array);
    }

    @Test
    void sortedData() {

        array = Filler.sortedData(100);

        copyAndSort();

        assertArrayEquals(array, clone);

    }

//    @Test
//    void sortedDataZero() {
//
//        array = Filler.sortedData(-1);
//
//    }

    @Test
    void sortedWithoutLastData() {

        array = Filler.sortedWithoutLastData(100);

        clone = Filler.sortedData(array.length);

        assertFalse(Arrays.equals(array, clone));

    }

    @Test
    void sortedWithoutLastDataToLast() {

        array = Filler.sortedWithoutLastData(100);

        int[] tmp = Arrays.copyOf(array, array.length - 1);

        clone = Filler.sortedData(array.length - 1);

        assertArrayEquals(tmp, clone);

    }

    @Test
    void reverseSortedData() {

        array = Filler.reverseSortedData(100);

        clone = Filler.sortedData(100);

        Integer[] cloneI = ArrayUtils.toObject(clone);

        Arrays.sort(cloneI, Collections.reverseOrder());

        clone = ArrayUtils.toPrimitive(cloneI);

        assertArrayEquals(array, clone);
    }

    @Test
    void randomData() {

        array = Filler.randomData(100);

        copyAndSort();

        assertFalse(Arrays.equals(array, clone));

    }

}