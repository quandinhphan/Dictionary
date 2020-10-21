import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class DictionaryManagement {
    private final static String FILE_TO_READ = "C:/Users/LENOVO/IdeaProjects/another dic/src/read.txt";
    private final static String FILE_TO_WRITE = "C:/Users/LENOVO/IdeaProjects/another dic/src/write.txt";

    public static void insertFromFile(Dictionary dic) {
        try {
            File file = new File(FILE_TO_READ);
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String m;
            while ((m = reader.readLine()) != null) {
                String[] n = m.split("\t\t|");
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

    public static void insertFromDatabase(Dictionary dic) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/data.db");
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("select * from av");
            while(rs.next()) {
                dic.setLink(new Word(rs.getString("word"), rs.getString("description"),
                        rs.getString("type"), rs.getString("pronounce")));

                dic.insert(rs.getString("word"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void removeFromDatabase(String word) throws SQLException {
        String sql = "DELETE FROM av WHERE word=?";

        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/data.db");
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, word);

        statement.executeUpdate();
    }

    public static void Update_word_FromDatabase(String word, String new_word_target) throws SQLException {
        String sql = "UPDATE av "
                + "SET word = ? "
                + "WHERE word = ? ";

        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/data.db");

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, new_word_target);
        statement.setString(2, word);

        statement.executeUpdate();
    }

    public static void Update_description_FromDatabase(String word, String new_word_explain) throws SQLException {
        String sql = "UPDATE av "
                + "SET description = ? "
                + "WHERE word = ? ";

        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/data.db");

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, new_word_explain);
        statement.setString(2, word);

        statement.executeUpdate();
    }

    public static void Update_pronounce_FromDatabase(String word, String new_word_pronounce) throws SQLException {
        String sql = "UPDATE av "
                + "SET pronounce = ? "
                + "WHERE word = ? ";

        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/data.db");

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, new_word_pronounce);
        statement.setString(2, word);

        statement.executeUpdate();
    }

    public static void Update_type_FromDatabase(String word, String new_word_type) throws SQLException {
        String sql = "UPDATE av "
                + "SET type = ? "
                + "WHERE word = ? ";

        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/data.db");

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, new_word_type);
        statement.setString(2, word);

        statement.executeUpdate();
    }

    public static void Add_to_Database(String word, String explain, String pronounce, String type) throws SQLException {
        String sql = "INSERT INTO av (word, description, pronounce, type) VALUES (?, ?, ?, ?)";

        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/data.db");
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, word);
        statement.setString(2, explain);
        statement.setString(3, pronounce);
        statement.setString(4, type);

        statement.executeUpdate();
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
            return "|" + word + "\t\t" + "|" + dic.getLink(word).getWord_explain();
        } else {
            return "Can't find";
        }
    }

    public static void removeWord(String word_target, Dictionary dic) {
        dic.set_remove_Link(word_target);
        dic.remove(dic.getRoot(), word_target, 0);
    }

    public static void addWord(String word_target, String word_explain, String word_type, String word_pronounce, Dictionary dic) {
        dic.setLink(new Word(word_target, word_explain, word_type, word_pronounce));
        dic.insert(word_target);
    }

    public static void change_Word_explain(String word_target, String new_word_explain, Dictionary dic) {
        String pronounce = dic.getLink(word_target).getWord_pronunciation();
        String type = dic.getLink(word_target).getWord_type();
        dic.set_remove_Link(word_target);
        dic.setLink(new Word(word_target, new_word_explain, pronounce, type));
    }

    public static void change_Word_pronounce(String word_target, String new_word_pronounce, Dictionary dic) {
        String explain = dic.getLink(word_target).getWord_explain();
        String type = dic.getLink(word_target).getWord_type();
        dic.set_remove_Link(word_target);
        dic.setLink(new Word(word_target, explain, new_word_pronounce, type));
    }

    public static void change_Word_type(String word_target, String new_word_type, Dictionary dic) {
        String explain = dic.getLink(word_target).getWord_explain();
        String pronounce = dic.getLink(word_target).getWord_pronunciation();
        dic.set_remove_Link(word_target);
        dic.setLink(new Word(word_target, explain, pronounce, new_word_type));
    }

    public static void change_Word_target(String word_target, String new_word_target, Dictionary dic) {
        String old_word_explain = dic.getLink(word_target).getWord_explain();
        String old_word_pronounce = dic.getLink(word_target).getWord_pronunciation();
        String old_type = dic.getLink(word_target).getWord_type();
        removeWord(word_target, dic);
        addWord(new_word_target, old_word_explain, old_type, old_word_pronounce , dic);
    }
}
