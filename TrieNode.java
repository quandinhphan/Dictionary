import java.util.HashMap;

public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;
    int count;

    public TrieNode() {
        children = new HashMap<>();
        count = 0;
    }

    public TrieNode getChildren(char c) {
        return children.get(c);
    }

    public void setChildren(char c) {
        children.put(c, new TrieNode());
    }

    public void set_isWord(boolean isWord) {
        this.isWord = isWord;
    }

    public boolean isWord() {
        return this.isWord;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}