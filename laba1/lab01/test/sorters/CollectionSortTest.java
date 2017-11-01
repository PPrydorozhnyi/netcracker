package sorters;

import fillers.Filler;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * collection sort test
 *
 * @see CollectionSort
 * @see BubbleSortUpTest
 * @see BubbleSortDownTest
 * @see MergeSortTest
 * @see QuickSortTest
 * @see SelectionSortTest
 *
 * @author P.Pridorozhny
 */
class CollectionSortTest {

    private int[] array;
    private int[] clone;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        array = Filler.randomData(100);
        clone = new int[array.length];
        System.arraycopy(array, 0, clone, 0, array.length);

    }

    @org.junit.jupiter.api.Test
    void testSort() {

        new CollectionSort().sort(array);
        Arrays.sort(clone);

        assertArrayEquals(array, clone);

    }

    @org.junit.jupiter.api.Test
    void testIntegrity() {

        new CollectionSort().sort(array);

        assertEquals(array.length, clone.length);

    }

    @org.junit.jupiter.api.Test
    void testNull() {

        new CollectionSort().sort(null);

    }

//    @org.junit.jupiter.api.Test
//    void testEmptyArray() {
//
//        new CollectionSort().sort(new int[]{});
//
//
//    }
//
//    @org.junit.jupiter.api.Test
//    void testOneElement() {
//
//        new CollectionSort().sort(new int[]{0});
//
//    }


}