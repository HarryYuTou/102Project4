// author name: leyan yu 
package project5;
import java.util.Iterator;

// A sorted linked list implementation of the Index interface
public class SortedLinkedList implements Index {
    private Node head;
    private int size;

    // Inner class to represent a node in the linked list
    private class Node {
        Word word;
        Node next;

        Node(Word word) {
            this.word = word;
            this.next = null;
        }
    }

    // Constructor for SortedLinkedList
    public SortedLinkedList() {
        head = null;
        size = 0;
    }

    // Add a word to the sorted linked list
    @Override
    public void add(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        // Create a Word object from the item
        Word newWord = new Word(item);

        // Insert in sorted order or increment count if word already exists
        if (head == null || head.word.compareTo(newWord) > 0) {
            Node newNode = new Node(newWord);
            newNode.next = head;
            head = newNode;
            size++;
        } else {
            Node current = head;
            while (current.next != null && current.next.word.compareTo(newWord) < 0) {
                current = current.next;
            }

            if (current.next != null && current.next.word.equals(newWord)) {
                current.next.word.incrementCount();
            } else {
                Node newNode = new Node(newWord);
                newNode.next = current.next;
                current.next = newNode;
                size++;
            }
        }
    }

    // Remove a word from the sorted linked list
    @Override
    public void remove(String item) {
        Node current = head;
        Node prev = null;

        // Find and remove the node with the given word
        while (current != null) {
            if (current.word.getWord().equals(item)) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                break; // Exit loop after removal
            }
            prev = current;
            current = current.next;
        }
    }

    // Get the count of a word in the sorted linked list
    @Override
    public int get(String item) {
        Node current = head;

        // Traverse the list to find the word
        while (current != null) {
            if (current.word.getWord().equals(item)) {
                return current.word.getCount();
            }
            current = current.next;
        }

        return -1; // If the word is not found
    }

    // Get the total number of unique words in the sorted linked list
    @Override
    public int size() {
        return size;
    }

    // Get an iterator for the sorted linked list
    @Override
    public Iterator<Word> iterator() {
        return new SortedLinkedListIterator();
    }

    // Iterator class for traversing the sorted linked list
    private class SortedLinkedListIterator implements Iterator<Word> {
        private Node current;
        private Node previous; // Keep track of the previous node for removal

        public SortedLinkedListIterator() {
            this.current = head;
            this.previous = null;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Word next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Word word = current.word;
            previous = current;
            current = current.next;
            return word;
        }

        @Override
        public void remove() {
            if (previous == null) {
                throw new IllegalStateException("Remove operation is not supported before calling next.");
            }
            // Remove the current node
            if (head == previous) {
                head = current;
            } else {
                previous.next = current;
            }
            size--;
            previous = null; // Reset the previous node
        }
    }
}
