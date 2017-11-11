package sorters;

/**
 * abstract bubble sort class for subclasses
 *
 * @author P.Pridorozhny
 */
abstract class BubbleSort extends Sort {


//    public void sort(int[] array, boolean forward) {
//
//        if(dSort(array))
//            return;
//
//        List<Integer> list = Arrays.asList(ArrayUtils.toObject(array));
//        ListIterator<Integer> iteratorOuter;
//        ListIterator<Integer> iteratorInner;
//
//        for (iteratorOuter = chooseIterator(forward, list);
//             iteratorOuter.hasNext();) {
//
//            Integer outer = iteratorOuter.next();
//
//            iteratorInner = chooseIterator(forward, list);
//            iteratorInner.next();
//
//            for (;iteratorInner.hasNext();) {
//                Integer inner = iteratorInner.next();
//                if (compare(outer, inner, forward) ) {
//                    swap(iteratorOuter, outer, iteratorInner, inner);
//                }
//
//            }
//
//        }
//
//        System.out.println("-------------------");
//        for (Integer i : list)
//            System.out.println(i);
//        System.out.println("-------------------");
//
//        Integer[] ar = new Integer[list.size()];
//        list.toArray(ar);
//        array = ArrayUtils.toPrimitive(ar);
//    }
//
//    private <T> ListIterator<T> chooseIterator(boolean forward, List<T> list) {
//        return forward ? list.listIterator() : new Reversed<>(list).iterator();
//    }
//
//    /**
//     * swap method for ListIterators
//     * @param iterator1
//     * first list iterator
//     * @param value1
//     * value of the first list iterator
//     * @param iterator2
//     * second list iterator
//     * @param value2
//     * value of the second list iterator
//     * @param <T>
//     *    list param
//     */
//    private <T> void swap(ListIterator<T> iterator1, T value1, ListIterator<T> iterator2, T value2) {
//
//        //System.out.println("swap");
//        iterator1.set(value2);
//        iterator2.set(value1);
//
//        resetValues(iterator1, value1);
//        resetValues(iterator2, value2);
//
//    }
//
//    private <T> void resetValues(ListIterator<T> iterator, T value) {
//        if(iterator.hasNext()) {
//            value = iterator.next();
//            value = iterator.previous();
//        } else if(iterator.hasPrevious()) {
//            iterator.previous();
//            value = iterator.next();
//        }
//    }
//
//    private boolean compare(Integer outer, Integer inner, boolean forward) {
//        return forward ? outer > inner : outer < inner;
//    }
}
