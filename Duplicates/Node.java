public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    
    //#region FIELDS
    private T value;
    private Node<T> leftChild;
    private Node<T> rightChild;
    private Node<T> parent;
    private int counter = 1;
    //#endregion

    public Node(T value)
    { this.value = value; }

    public T value()
    { return this.value; }

    public Node<T> leftChild()
    { return this.leftChild; }
    
    public Node<T> rightChild()
    { return this.rightChild; }
    
    public Node<T> parent()
    { return this.parent; }

    public int counter()
    { return this.counter; }

    public boolean hasLeftChild()
    { return this.leftChild != null; }

    public boolean hasRightChild()
    { return this.rightChild != null; }

    public boolean hasParent()
    { return this.parent != null; }

    public int children() {
        return (this.leftChild() != null ? 1 : 0) +
                (this.rightChild() != null ? 1 : 0);
    }

    public int incrementCounter()
    { return this.counter++; }

    public static <E extends Comparable<E>> void bind(Node<E> child, Node<E> parent) {
        if (child.compareTo(parent) < 0) {
            parent.leftChild = child;
            child.parent = parent;
        } else if (child.compareTo(parent) > 0) {
            parent.rightChild = child;
            child.parent = parent;
        } else
        { throw new IllegalBindException("Attempted binding of node " +  child.value + " to itself"); }
    }

    public String toString()
    { return this.value.toString(); }

    public char asChar()
    { return this.value.toString().toCharArray()[0]; }

    public int compareTo(Node<T> otherNode)
    { return this.value.compareTo(otherNode.value); }
}
