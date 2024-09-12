// author name: leyan yu 
package project5;

public class Word implements Comparable<Word> {
    private String word;
    private int count;

    public Word(String word) {
        this.word = word;
        this.count = 1;
    }

    public int incrementCount() {
        return ++count;
    }

    public String getWord() {
        return word;
    }

    public int decrementCount() {
        // Decrement count logic
        if (count > 0) {
            return --count;
        }
        return 0;
    }


    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.format("%5d  %s", count, word);
    }

    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);
    }

    @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Word wordObj = (Word) obj;
    return word.equals(wordObj.word) && count == wordObj.count;
}


    // hashCode method should also be overridden when equals is overridden.
    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
