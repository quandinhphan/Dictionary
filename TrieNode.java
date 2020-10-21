import java.util.HashMap;

public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
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

}