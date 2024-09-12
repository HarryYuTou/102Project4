// author name: leyan yu 
package project5;
import java.util.Iterator;
import java.util.Stack;

public class BSTIndex implements Index {
    private TreeNode root;
    private int size;

    // TreeNode inner class to represent each node in the BST
    private class TreeNode {
        Word word;
        TreeNode left, right;

        TreeNode(Word word) {
            this.word = word;
            this.left = this.right = null;
        }
    }

    // Constructor for BSTIndex
    public BSTIndex() {
        root = null;
        size = 0;
    }

    // Add a new item to the BST
    @Override
    public void add(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        Word newWord = new Word(item);
        root = addRecursive(root, newWord);
    }

    // Helper method to recursively add a new word to the BST
    private TreeNode addRecursive(TreeNode current, Word word) {
        if (current == null) {
            size++;
            return new TreeNode(word);
        }

        int cmp = word.compareTo(current.word);
        if (cmp < 0) {
            current.left = addRecursive(current.left, word);
        } else if (cmp > 0) {
            current.right = addRecursive(current.right, word);
        } else {
            current.word.incrementCount();
        }
        return current;
    }



// Improved remove method (if necessary)
@Override
public void remove(String item) {
    root = removeRecursive(root, item);
}

private TreeNode removeRecursive(TreeNode current, String item) {
    if (current == null) {
        return null;
    }

    int cmp = item.compareTo(current.word.getWord());
    if (cmp < 0) {
        current.left = removeRecursive(current.left, item);
    } else if (cmp > 0) {
        current.right = removeRecursive(current.right, item);
    } else {
    size--;
    if (current.left == null) {
        return current.right;
    } else if (current.right == null) {
        return current.left;
    }
    TreeNode successor = findMin(current.right);
    current.word = successor.word;
    current.right = removeRecursive(current.right, successor.word.getWord());
}
    return current;
}

    // Find the minimum value node in the BST
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Get the count of a specific word in the BST
    @Override
    public int get(String item) {
        return getRecursive(root, item);
    }
    
 @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    toStringRecursive(root, sb);
    if (sb.length() > 1) {
        sb.delete(sb.length() - 2, sb.length()); // Remove trailing ", "
    }
    sb.append("]");
    return sb.toString();
}

private void toStringRecursive(TreeNode node, StringBuilder sb) {
    if (node != null) {
        toStringRecursive(node.left, sb);
        sb.append(node.word.toString()).append(", "); // Append each word with ", "
        toStringRecursive(node.right, sb);
    }
}



    // Helper method to recursively find the count of a word in the BST
    private int getRecursive(TreeNode current, String item) {
        if (current == null) {
            return -1;
        }

        int cmp = item.compareTo(current.word.getWord());
        if (cmp < 0) {
            return getRecursive(current.left, item);
        } else if (cmp > 0) {
            return getRecursive(current.right, item);
        } else {
            return current.word.getCount();
        }
    }

   @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || !(obj instanceof Index)) return false;

    Index other = (Index) obj;
    if (this.size() != other.size()) return false;

    Iterator<Word> thisIterator = this.iterator();
    Iterator<Word> otherIterator = other.iterator();

    while (thisIterator.hasNext() && otherIterator.hasNext()) {
        if (!thisIterator.next().equals(otherIterator.next())) {
            return false;
        }
    }

    // If both iterators are exhausted, the BSTs are equal
    return !(thisIterator.hasNext() || otherIterator.hasNext());
}



private boolean equalsRecursive(TreeNode a, TreeNode b) {
    if (a == null && b == null) {
        return true;
    }
    if (a == null || b == null) {
        return false;
    }
    return a.word.equals(b.word)
           && equalsRecursive(a.left, b.left)
           && equalsRecursive(a.right, b.right);
}


    // Get the total number of unique words in the BST
    @Override
    public int size() {
        return size;
    }

    // Get an iterator for the BST
    @Override
    public Iterator<Word> iterator() {
        return new BSTIterator(root);
    }

    // Iterator class for traversing the BST in in-order
    private class BSTIterator implements Iterator<Word> {
        private Stack<TreeNode> stack = new Stack<>();

        public BSTIterator(TreeNode root) {
            pushAll(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Word next() {
            TreeNode tmpNode = stack.pop();
            pushAll(tmpNode.right);
            return tmpNode.word;
        }

        // Helper method to push all left children of a node into the stack
        private void pushAll(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        // Remove the current element from the iterator
        @Override
        public void remove() {
            if (stack.isEmpty()) {
                throw new IllegalStateException("No element to remove.");
            }
            TreeNode current = stack.peek();
            if (current.left == null && current.right == null) {
                // If the current node has no children, simply pop it from the stack.
                stack.pop();
            } else {
                throw new UnsupportedOperationException("Remove operation is not supported for this case.");
                // Handle the case when the current node has children.
                // You might need to define your own logic for this case.
            }
        }
    }
}
