package control;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by zac on 4/2/17.
 *
 * This class is used for the first 3 features to find out the top 10 priority
 */
public class TopKPriorityQueue<T> {
    /** Use priority queue for default sorting when offer and poll, O(lgn) */
    PriorityQueue<T> priorityQueue;
    /** Size of the priority queue */
    int size;

    /**
     * Constructor
     *
     * @param size
     * @param comparetor
     */
    public TopKPriorityQueue(int size, Comparator<T> comparetor)
    {
        priorityQueue = new PriorityQueue<>(size, comparetor);

        this.size = size;
    }

    /**
     * Push data into TopKPriorityQueue
     *
     * @param data
     */
    public void push(T data)
    {
        priorityQueue.offer(data);
        /**
         * If the size of PriorityQueue is greater than specific size,
         * poll the smallest 1 and rearrange the order.
         */
        if (priorityQueue.size() > size)
        {
            priorityQueue.poll();
        }
    }

    /**
     * Offer the List with Descending order sorted
     *
     * @return
     */
    public List<T> offers()
    {
        LinkedList<T> list = new LinkedList<>();

        while (!priorityQueue.isEmpty())
        {   /** Add to first, ascending becomes descending. */
            list.addFirst(priorityQueue.poll());
        }
        return list;
    }
}
