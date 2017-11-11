package analyzer;

import org.openjdk.jmh.annotations.Benchmark;
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
 * reflections decorator class
 *
 * @see Analyzer
 */
class ReflectionsDecorator {

    private Reflections reflections;
    private Set<Class<? extends Sort>> subTypes;

    /**
     * constructor
     * @param sClass
     *
     * Sorter class
     */
    ReflectionsDecorator(Class sClass) {
        reflections = new Reflections("", new MethodAnnotationsScanner(), new SubTypesScanner());

        subTypes = reflections.getSubTypesOf(sClass);
    }

    /**
     * instantiate sorts
     *
     * {@link Analyzer#analyze()
     *
     * @return
     * HashSet of Sorts
     */
    HashSet<Sort> instantiate() {

        HashSet<Sort> sorters = new HashSet<>();

        if (subTypes.isEmpty()) {
            System.out.println("Empty");
            return sorters;
        }


        for (Class<? extends Sort> cl : subTypes) {

            if (!Modifier.isAbstract(cl.getModifiers())) {
                try {
                    sorters.add(cl.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


        }

        return sorters;


    }

    /**
     * gets all class which is annotated with annotationClass
     *
     * @param annotationClass
     * annotation class
     * @return
     * set of methods wich annotated
     */
    Set<Method> getMethodsAnnotatedWith(Class annotationClass) {

        return reflections.getMethodsAnnotatedWith(annotationClass);
    }

    /**
     * invoke sort
     *
     * @param algorithm
     * sort algorithm
     * @param method
     * filler method
     * @param size
     * size of array to fill
     */
    @Benchmark
    void invokeMethod(Sort algorithm, Method method, int size) {
        try {
            algorithm.sort((int[]) method.invoke(null, size));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
