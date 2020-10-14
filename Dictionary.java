import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.Character.toUpperCase;

public class Dictionary {
    public TrieNode root;
    private String all_words;
    public HashMap<String, Word> Link = new HashMap<>();

    public Dictionary() {
        this.root = new TrieNode();
    }


    public void setLink(Word word) {
        Link.put(word.getWord_target(), word);
    }

    public void set_remove_Link(String word) {
        Link.remove(word);
    }

    public Word getLink(String word) {
        return Link.get(word);
    }

    public void insert(String word) {
        TrieNode current = root;
        current.setCount(current.getCount() + 1);

        for (char l : word.toCharArray()) {
            if (current.getChildren(l) == null) {
                current.setChildren(l);
            }
            current = current.getChildren(l);
            current.setCount(current.getCount() + 1);
        }
        current.set_isWord(true);
    }


    public boolean isEmpty(TrieNode current) {
        if (current == null) {
            return false;
        }
        for (char i = ' '; i <= 'z'; i++)
            if (current.getChildren(i) != null) {
                return false;
            }
        return true;
    }

    public void print_word(TrieNode node, String word, ArrayList<String> save) {
        if (node == null) {
            return;
        }
        if (node.isWord()) {
            save.add(word);
        }
        for (char i = ' '; i <= 'z'; i++) {
            if (node.getChildren(i) != null) {
                print_word(node.getChildren(i), word + i, save);
            }
        }
    }

    public void save_word(TrieNode node, String word) {
        if (node == null) {
            return;
        }
        if (node.isWord()) {
            all_words = all_words + ("|" + (word) + "\t\t" + "|" + (getLink(word).getWord_explain()) + "\n");
        }
        for (char i = ' '; i <= 'z'; i++) {
            if (node.getChildren(i) != null) {
                save_word(node.getChildren(i), word + i);
            }
        }
    }


    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.getChildren(c) == null) {
                return false;
            } else {
                node = node.getChildren(c);
            }
        }
        return node.isWord();
    }

    public ArrayList<String> print_word_again(String word) {
        TrieNode node = root;
        ArrayList<String> save= new ArrayList<String>();
        for (char c : word.toCharArray()) {
            if (node.getChildren(c) == null) {
                System.out.println("Can't find");
                return null;
            } else {
                node = node.getChildren(c);
            }
        }
        print_word(node, word, save);
        return save;
    }

    public TrieNode remove(TrieNode root, String word, int i) {
        if (root == null) {
            return null;
        }
        if (i == word.length()) {
            if (root.isWord())
                root.set_isWord(false);

            if (isEmpty(root)) {
                root = null;
            }
            return root;
        }
        root = remove(root.getChildren(word.charAt(i)), word, i + 1);
        if (isEmpty(root) && !root.isWord()) {
            root = null;
        }
        return root;
    }

    public String toString() {
        all_words = "No | English" + "\t\t" + "| Vietnamese" + "\n";
        save_word(root, "");
        return all_words;
    }
}

