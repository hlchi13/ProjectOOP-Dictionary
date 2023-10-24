package Database;
import Dictionary.Word;
import org.jsoup.Jsoup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseDictionary {
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";
    private static final String MYSQL_URL =
            "jdbc:mysql://localhost:3306/edict";

    private static Connection connection = null;

    private static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        close(connection);
        System.out.println("Database disconnected!");
    }

    public String lookUpWord(final String target) {

        final String SQL_QUERY = "SELECT definition FROM dictionary WHERE target = ?";
        try {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASSWORD);
            System.out.println("Database connected!\n");
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        String explain = Jsoup.parse(rs.getString("definition")).text();
                        return explain;
                    } else {
                        return "No words were found";
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No words were found";
    }

    private ArrayList<Word> getWordsFromResultSet(PreparedStatement ps) throws SQLException {
        try {
            ResultSet rs = ps.executeQuery();
            try {
                ArrayList<Word> words = new ArrayList<>();
                while (rs.next()) {
                    words.add(new Word(rs.getString(2), rs.getString(3)));
                }
                return words;

            } finally {
                close(rs);
            }
        } finally {
            close(ps);
        }
    }

    public static void main(String[] args) {
        DatabaseDictionary data = new DatabaseDictionary();
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        System.out.println(data.lookUpWord(s));
    }
}

