package analyzer;

import exel.Exel;
import fillers.FillerAnnotation;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import sorters.Sort;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
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

    private Reflections reflections;
    private Set<Class<? extends Sort>> subTypes;
    private Set<Sort> sorters;
    private Set<Method> pathParamMethods;
    private int[] sizes = {10, 100, 1000, 10000};
    private Exel exel;

    /**
     * constructor of the analyzer class
     */
    private Analyzer() {
        reflections = new Reflections("", new MethodAnnotationsScanner(), new SubTypesScanner());

        subTypes = reflections.getSubTypesOf(Sort.class);

        sorters = new HashSet<>();

        pathParamMethods = reflections.getMethodsAnnotatedWith(FillerAnnotation.class);

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
     *
     * instantiate sorts
     * {@link #analyze()
     *
     * @param set
     * set of sorts
     */
    private void instantiate(Set<Class<? extends Sort>> set) {

        if (set.isEmpty()) {
            System.out.println("Empty");
            return;
        }


        for (Class<? extends Sort> cl : set) {

            if (!Modifier.isAbstract(cl.getModifiers())) {
                try {
                    sorters.add(cl.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


        }


    }

    /**
     * instantiate srt methods and measures algorithms running time
     *
     * {@link #algorithmTime(Sort algorithm, Method method, int size)
     * {@link #instantiate(Set<Class<? extends Sort>>)}
     */
    public void analyze() {

        boolean correctWrite;
        long time;

        instantiate(subTypes);

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

        try {
            algorithm.sort((int[]) method.invoke(null, size));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        long result = System.nanoTime();

        return result - start;
    }

}
