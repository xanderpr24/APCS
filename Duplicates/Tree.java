public class Tree<T extends Comparable<T>> implements java.io.Serializable {
    private Node<T> head;
    private ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
    private HashMap<Node<T>, Integer> xPoses = new HashMap<Node<T>, Integer>();
    private HashMap<Node<T>, Integer> yPoses = new HashMap<Node<T>, Integer>();
    private char[][] grid;

    public Tree() {}

    public Tree(Node<T> head) {
        this.head = head;
        this.nodes.add(head);
    }

    /**
     * Returns the node at the head of this tree
     */
    public Node<T> head()
    { return this.head; }

    /**
     * Returns the nodes this tree includes.
     */
    public ArrayList<Node<T>> nodes()
    { return this.nodes; }

    /**
     * Add a node to the appropriate location in this tree.
     * @param toAdd The node to be added
     */
    public void add(Node<T> toAdd) {
        this.tryAdd(toAdd);
        
        Node<T> child, current = head;                                // Begin checking nodes at tree head
        
        while (true) {
            if (toAdd.value() != current.value()) {                 // If letter does not match that of current node
                child = toAdd.compareTo(current) < 0
                    ? current.leftChild()                           // If letter is less than current node's, set temp to current node's left child
                    : current.rightChild();                         // Otherwise, set temp to current node's right child

                if (child == null) { break; }                       // If left/right child does not exist, exit loop
                current = child;                                    // Dispatch change; set next node to be checked to temp

            } else {                                                // If letter is equal to that of the current node
                current.incrementCounter();                         // Increment current node's counter
                break;                                              // Exit loop
            }
        }
        if (current.compareTo(toAdd) != 0)                          // As long as node's letter does not match that of the node being checked
        { Node.bind(toAdd, current); }                              // Bind node as child of current node being checked
    }

    public String toString() {
        int height = this.generations() * 2 + 1,
        width = 3 + (int) nodes.stream().filter(Node::hasRightChild).count();
        
        grid = new char[height][width];                 // Build  grid
        for (int i = 0; i < grid.length; i++)
        { Arrays.fill(grid[i], ' '); }                  // Fill grid with empty spaces

        return addGraphicalSugar();                     // Add connecting lines between nodes and return graphical grid
    }

    private void placeNodes() {
        Node<T> current = this.head;
        int xPos = 0, yPos = 0;
        boolean hasLeft, hasRight;
        ArrayList<Node<T>> placed = new ArrayList<Node<T>>();
        helpPlace(placed, current, xPos, yPos);
        while (true) {
            hasLeft = current.hasLeftChild() && !placed.contains(current.leftChild());          // Current has left child that has not already been checked?
            hasRight = current.hasRightChild() && !placed.contains(current.rightChild());       //          "" right ""
            if (hasLeft || hasRight) {                                                  // If current node has any child
                current = hasLeft ? current.leftChild() : current.rightChild();         // Walk down from current node to child, prioritizing left
                yPos += 2;                                                              // Twice increment yPos (extra spaced used for graphical pipe)
                xPos += hasLeft ? 0 : 1;                                                // If child is a right child, increment xPos
                helpPlace(placed, current, xPos, yPos);

            } else {                                                    // If current node has no children
                if (current == this.rightmostNode()) { break; }         // Exit loop if this is the last node to check
                current = current.parent();                             // Walk up to parent
                yPos -= 2;                                              // Decrement yPos accordingly
            }
        }
    }

    private void helpPlace(
                    ArrayList<Node<T>> placed,
                    Node<T> current, 
                    int xPos, int yPos) {
        grid[yPos][xPos] = current.asChar();        // Set (x, y) to new current node
        placed.add(current);                        // Mark new current node as checked
        xPoses.put(current, xPos);                  // Define xPos of new current node
        yPoses.put(current, yPos);                  //    ""  yPos ""
    }

    private String addGraphicalSugar() {
        placeNodes();
        BinaryOperator<String> connectInRow = (c1, c2) -> c1 + (c2.equals("-") ? "---" : "   ") + c2;       // Node + connector + node
        Consumer<Node<T>> addDashRight = n -> 
            IntStream.range(xPoses.get(n), xPoses.get(n.rightChild()))
            .forEach(i -> grid[yPoses.get(n)][i + 1] = '-');
        Consumer<Node<T>> addPipeTop = n -> grid[yPoses.get(n) - 1][xPoses.get(n)] = '|';

        Function<char[], String> connectRow = r -> new String(r)
            .chars()
            .mapToObj(Character::toString)
            .reduce("", connectInRow)
            .substring(3);

        nodes.stream().filter(Node::hasRightChild).forEach(addDashRight);
        nodes.stream().filter(Node::hasParent).forEach(addPipeTop);

        // ? try Arrays.stream(grid)
        return Arrays.asList(grid).stream()                 // Stream of rows
            .map(connectRow)                                // Add horizontal line connections and trim leading whitespace
            .reduce("", (r1, r2) -> r1 + r2 + "\n");        // Add each row together with line break
    }

    /**
     * Returns all descendants on the far left or far right of a given node.
     * @param n The node whose absolute children to find
     * @param side The side to check: negative gets left children; non-negative gets right
     */
    public ArrayList<Node<T>> absoluteChildren(Node<T> n, int side) {
        ArrayList<Node<T>> descendants = new ArrayList<Node<T>>();
        while (true) {
            n = side < 0 ? n.leftChild() : n.rightChild();
            if (n == null) { break; }
            descendants.add(n);
        }
        return descendants;
    }

    public int oneChildSum() {
        return nodes.stream()
        .filter(n -> n.children() == 1)
        .map(Node::counter)
        .reduce(0, Integer::sum);
    }

    // ? Might not work
    public static <E extends Comparable<E>> Tree<E> seedTree(E... split) {
        return IntStream.range(0, split.length)
        .mapToObj(i -> new Node<E>(split[i]))
        .collect(Tree::new, Tree::add, Tree::addAll);
    }

    public static <E extends Comparable<E>> Tree<E> addAll(Tree<E> t1, Tree<E> t2) {
        t2.nodes().forEach(t1::add);
        return t1;
    }

    /**
     * If this tree does not contain a node, add it.
     * @param n The node to add
     */
    private void tryAdd(Node<T> n) {
        if (nodes.size() == 0) {                        // If this tree contains no nodes
            head = n;                                   // Set its head to this node
            nodes.add(n);                               // Add this node to this tree's list of nodes
        }
        if (!nodes.stream()                             // If stream of nodes
                .map(Node::value)                       // Converted to stream of letters
                // ? below not tested; previously working predicate: v -> v.equals(n.value())
                .anyMatch(n.value()::equals))           // Does not have a letter equal to that of the node to add
        { nodes.add(n); }                               // Add node to nodes list
    }

    private int generations() {
        Node<T> current = head;
        int highest = 0, height = 0;
        boolean hasLeft, hasRight;
        ArrayList<Node<T>> checked = new ArrayList<Node<T>>();

        while (true) {
            hasLeft = current.hasLeftChild() && !checked.contains(current.leftChild());
            hasRight = current.hasRightChild() && !checked.contains(current.rightChild());
            if (hasLeft || hasRight) {
                height++;
                current = hasLeft ? current.leftChild() : current.rightChild();
                checked.add(current);
            } else {
                if (height > highest) { highest = height; }
                if (current == this.rightmostNode()) { break; }
                if (current.hasParent()) {
                    height--;
                    current = current.parent();
                }
            }
        }
        return highest;
    }

    /**
     * @return The rightmost node of this tree, e.g., the farthest right descendant of the head, or the head if it has no right children
     * @apiNote This return is <b>not necessarily the node with the rightmost graphical representation</b>.
     */
    private Node<T> rightmostNode() {
        ArrayList<Node<T>> right = this.absoluteChildren(head, 1);
        int size = right.size();
        return size > 0 ? right.get(size - 1) : head;
    }
}
