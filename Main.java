import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dictionary dictionary = new Dictionary();
        DictionaryManagement.insertFromDatabase(dictionary);
        DictionaryManagement.dictionaryExportToFile(dictionary);
    }
}
