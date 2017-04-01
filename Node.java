/**
 * Created by Shaan on 003, 3 Apr.
 */
public class Node {
    public Node parent, l1, r1;
    public int v = -1;
    public int f = -1;
    public boolean isLeaf = false;

    public Node() {
    }

    public Node(int f, int v) {
        this.f = f;
        this.v = v;
    }

}
