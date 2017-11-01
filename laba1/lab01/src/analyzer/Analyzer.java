package analyzer;

import exel.Exel;
import fillers.FillerAnnotation;
import sorters.Sort;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * class for analyzing algorithms running time
 *
 * @see sorters.Sort
 * @see sorters.BubbleSortUp BubbleSortUp
 * @see sorters.BubbleSortDown BubbleSortDown
 * @see sorters.QuickSort QuickSort
 * @see sorters.MergeSort MergeSort
 * @see sorters.SelectionSort SelectionSort
 * @see sorters.CollectionSort CollectionSort
 *
 * @see fillers.Filler Filler
 *
 *
 * @author P.Pridorozhny
 */
public class Analyzer {

    private static Analyzer analyzer;
    private ReflectionsDecorator reflectionsDecorator;

    private Set<Sort> sorters;
    private Set<Method> pathParamMethods;
    private int[] sizes = {10, 100, 1000, 10000};
    private Exel exel;

    /**
     * constructor of the analyzer class
     */
    private Analyzer() {

        reflectionsDecorator = new ReflectionsDecorator(Sort.class);

        pathParamMethods = reflectionsDecorator.getMethodsAnnotatedWith(FillerAnnotation.class);

        sorters = reflectionsDecorator.instantiate();

        exel = new Exel("Stats", pathParamMethods, sizes);
    }

    /**
     * singleton pattern getInstance
     *
     * @return
     * instance of the Analyzer class
     */
    public static Analyzer getInstance() {

        if (analyzer == null) {
            analyzer = new Analyzer();
        }

        return analyzer;
    }


    /**
     * instantiate srt methods and measures algorithms running time
     *
     * {@link #algorithmTime(Sort algorithm, Method method, int size)
     *
     */
    public void analyze() {

        boolean correctWrite;
        long time;

        for (Method method : pathParamMethods) {
            for (Sort sort : sorters) {
                for (int size : sizes) {

                    algorithmTime(sort, method, size);
                    time = algorithmTime(sort, method, size);

                    correctWrite = exel.write(sort.getClass().getName(),
                            method.getName(), time);

                    if (!correctWrite) {
                        return;
                    }

//                    System.out.println(sort.getClass().getName() + " " + method.getName() + size
//                    + " " + time);
                }
            }
        }

        exel.autoSize();
        exel.createCharts();
        exel.writeToFile();

        System.out.println("Completed");

    }

    /**
     * measures algorithm running time
     *
     * @param algorithm
     * current algorithm
     * @param method
     * filler method
     * @param size
     * size of the array
     * @return
     * algorithm time running
     */
    private long algorithmTime(Sort algorithm, Method method, int size) {
        long start = System.nanoTime();

        reflectionsDecorator.invokeMethod(algorithm, method, size);

        long result = System.nanoTime();

        return result - start;
    }

}
