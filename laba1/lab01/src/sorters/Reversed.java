package sorters;

import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.ListIterator;

/**
 * Revers Iterator
 *
 * @author P.Pridorozhny
 */
public class Reversed<T> implements Iterable<T> {
    private final List<T> original;

    Reversed(List<T> original) {
        this.original = original;
    }

    public ListIterator<T> iterator() {
        final ListIterator<T> i = original.listIterator(original.size());

        return new ListIterator<T>() {

            @Override
            public boolean hasNext() {
                return i.hasPrevious();
            }
            @Override
            @NotNull
            public T next() {
                return i.previous();
            }

            @Override
            public boolean hasPrevious() {
                return i.hasNext();
            }

            @Override
            @NotNull
            public T previous() {
                return i.next();
            }

            @Override
            public int nextIndex() {
                return i.previousIndex();
            }

            @Override
            public int previousIndex() {
                return i.nextIndex();
            }

            @Override
            public void remove() {
                i.remove();
            }

            @Override
            public void set(T t) {
                i.set(t);
            }

            @Override
            public void add(T t) {
                i.add(t);
            }

        };
    }

    public static <T> Reversed<T> reversed(List<T> original) {
        return new Reversed<>(original);
    }
}


