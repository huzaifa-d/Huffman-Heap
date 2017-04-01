/**
 * Created by Shaan on 003, 3 Apr.
 */
import java.lang.*;
import java.util.ArrayList;

public class dHeap {
    public int curr_size = 2;
    public ArrayList<Node> heap = new ArrayList<>();
    Node tempN = new Node();

    public dHeap()
    {
        Node n = new Node();
        //Cache optimization
        heap.add(n);
        heap.add(n);
        heap.add(n);

    }

    public void insert(Node n) {
        int i = ++curr_size;
        heap.add(tempN);
        heap.set(i, n);
        //if (i > 3 && (heap.get(i/4 + 2).f > n.f)) {
        //    heap.add(heap.get(i/4 + 2));
        //    i /= 4;
        //}
        while(i > 3 && (heap.get(i/4 + 2).f > n.f)) {
            //heap.set(i, heap.get(i/4 + 2));
            swap(i, i/4 + 2);
            i = (i/4 + 2);
        }
        //heap.set(i, n);
        //heap.add(n);
    }

    public Node getMin(){
        if(curr_size != 0)
        {
            Node n = heap.get(3);
            heap.set(3, heap.get(curr_size));
            heap.remove(curr_size);
            curr_size--;
            if (curr_size > 3)
                heapify(3);
            return n;
        }
        else
            return null;
    }

    public int smallest (int a, int b, int c, int d)
    {
        if ((a<= b) && (a<= c) && (a<= d))
            return a;
        else if ((b<= a) && (b<= c) && (b<= d))
            return b;
        else if ((c<= b) && (c<= a) && (c<= d))
            return c;
        return d;
    }


    public int smallest (int a, int b, int c)
    {
        if ((a<= b) && (a<= c))
            return a;
        else if ((b<= a) && (b<= c))
            return b;
        return c;
    }

    public int smallest (int a, int b)
    {
        if (a<= b)
            return a;
        return b;
    }


    public void heapify(int i)
    {
        int child = (i-2) * 4;
        int small = 0;
        int small_index;

        if (child > curr_size)
            return;
        if((child + 3) <= curr_size) {
            small = smallest(heap.get(child).f, heap.get(child + 1).f, heap.get(child +2).f, heap.get(child + 3).f);
        }
        else if ((child + 2) <= curr_size) {
            small = smallest(heap.get(child).f, heap.get(child + 1).f, heap.get(child +2).f);
        }
        else if ((child + 1) <= curr_size) {
            small = smallest(heap.get(child).f, heap.get(child + 1).f);
        }
        else if(child <= curr_size) {
            small = heap.get(child).f;
        }
        if (heap.get(child).f == small)
            small_index = child;
        else if (heap.get(child + 1).f == small)
            small_index = child + 1;
        else if (heap.get(child + 2).f == small)
            small_index = child + 2;
        else
            small_index = child + 3;
        if(heap.get(i).f > heap.get(small_index).f) {
                swap(i, small_index);
                    heapify(small_index);
        }
    }


    public void swap(int p, int c) {
        Node temp;
        temp = heap.get(p);
        heap.set(p, heap.get(c));
        heap.set(c, temp);
    }
}
