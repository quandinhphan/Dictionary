import java.io.*;
import java.util.Scanner;

public class DictionaryCommandline {

    public static void showAllWords(Dictionary dic) {
        System.out.println(dic.toString());
    }

    public static void dictionaryBasic(Dictionary dic) {
        DictionaryManagement.insertFromFile(dic);
        DictionaryCommandline.showAllWords(dic);
    }

    public static void dictionaryAdvanced(Dictionary dic) {
        DictionaryManagement.insertFromFile(dic);
        DictionaryCommandline.showAllWords(dic);
        System.out.println(DictionaryManagement.DictionaryLookup(dic));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dictionary dictionary = new Dictionary();
        DictionaryManagement.insertFromFile(dictionary);
        dictionary.remove(dictionary.getRoot(), "else", 0);
        DictionaryCommandline.showAllWords(dictionary);
        DictionaryManagement.removeWord("finally", dictionary);
        DictionaryManagement.removeWord("final", dictionary);
        DictionaryManagement.removeWord("film", dictionary);
        DictionaryCommandline.showAllWords(dictionary);

        DictionaryManagement.dictionaryExportToFile(dictionary);

    }
}
