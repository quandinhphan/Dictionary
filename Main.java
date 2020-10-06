import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dictionary dictionary = new Dictionary();
        DictionaryCommandline.dictionaryBasic(dictionary);
        dictionary.remove(dictionary.root, "else", 0);
        DictionaryManagement.addWord("fine", "on", dictionary);
        DictionaryManagement.removeWord("finally", dictionary);
        DictionaryCommandline.showAllWords(dictionary);
        DictionaryManagement.change_Word_explain("accident", "ngu", dictionary);
        DictionaryManagement.change_Word_target("accept", "acept", dictionary);
        DictionaryCommandline.showAllWords(dictionary);

        DictionaryManagement.dictionaryExportToFile(dictionary);

    }
}
