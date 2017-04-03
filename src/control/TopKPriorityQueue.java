package control;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by zac on 4/2/17.
 */
public class TopKPriorityQueue<T> {

    PriorityQueue<T> pq;

    int size;

    public TopKPriorityQueue(int size, Comparator<T> comp) {
        pq = new PriorityQueue<>(size, comp);
        this.size = size;
    }

    public void push(T x) {
        pq.offer(x);

        /** If the size of PriorityQueue is greater than specific size,
         * poll the smallest 1 and rearrange the order.
         */
        if (pq.size() > size)
            pq.poll();
    }

    public List<T> offers() {
        LinkedList<T> list = new LinkedList<>();

        /** Ascending to descending. */
        while (!pq.isEmpty())
            list.addFirst(pq.poll());

        return list;
    }
}
