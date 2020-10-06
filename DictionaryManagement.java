import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    private final static String FILE_TO_READ = "C:/Users/LENOVO/IdeaProjects/dictionary/src/read.txt";
    private final static String FILE_TO_WRITE = "C:/Users/LENOVO/IdeaProjects/dictionary/src/write.txt";

    public static void insertFromFile(Dictionary dic) {
        try {
            File file = new File(FILE_TO_READ);
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String m;
            while ((m = reader.readLine()) != null) {
                String[] n = m.split("\t");
                dic.setLink(new Word(n[0], n[1]));
                dic.insert(n[0]);
            }
            inputStream.close();
            inputStreamReader.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dictionaryExportToFile(Dictionary dic) {
        try {
            File file = new File(FILE_TO_WRITE);
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(dic.toString());

            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String DictionaryLookup(Dictionary dic) {
        Scanner scanner = new Scanner(System.in);
        String word = scanner.next();
        if (dic.search(word)) {
            return "|" + word + "\t\t" + "|" + dic.getLink(word);
        } else {
            return "Can't find";
        }
    }

    public static void removeWord(String word_target, Dictionary dic) {
        dic.set_remove_Link(word_target);
        dic.remove(dic.root, word_target, 0);
    }

    public static void addWord(String word_target, String word_explain, Dictionary dic) {
        dic.setLink(new Word(word_target, word_explain));
        dic.insert(word_target);
    }

    public static void change_Word_explain(String word_target, String new_word_explain, Dictionary dic) {
        dic.set_remove_Link(word_target);
        dic.setLink(new Word(word_target, new_word_explain));
    }

    public static void change_Word_target(String word_target, String new_word_target, Dictionary dic) {
        String old_word_explain = dic.getLink(word_target);
        removeWord(word_target, dic);
        addWord(new_word_target, old_word_explain, dic);
    }
}
