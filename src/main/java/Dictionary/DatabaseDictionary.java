package Dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDictionary {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "chi26543";
    private static final String DB_NAME = "dictionary";
    private static final String MYSQL_URL =
            "jdbc:mysql://localhost:3306/" + DB_NAME;

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

    /**
     * Convert HTML to text keeping linebreaks
     *
     * @param html text
     * @return text
     * Ref: https://stackoverflow.com/questions/2513707/how-to-convert-html-to-text-keeping-linebreaks
     */
    private static String htmlToText(String html) {
        Document document = Jsoup.parse(html);
        Element body = document.body();
        return buildStringFromNode(body).toString();
    }

    private static StringBuffer buildStringFromNode(Node node) {
        StringBuffer buffer = new StringBuffer();
        if (node instanceof TextNode) {
            TextNode textNode = (TextNode) node;
            buffer.append(textNode.text().trim());
        }
        for (Node childNode : node.childNodes()) {
            buffer.append(buildStringFromNode(childNode));
        }
        if (node instanceof Element) {
            Element element = (Element) node;
            String tagName = element.tagName();
            if ("p".equals(tagName) || "br".equals(tagName)) {
                buffer.append("\n");
            }
        }
        return buffer;
    }

    private static void connectToDatabase() throws SQLException {
        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASSWORD);
        if (connection != null) {
            System.out.println("Database is connected");
        }
    }

    /**
     * Get all the data from the databasae.
     */
    private static ArrayList<String> getAllWords() {
        final String SQL_QUERY = "SELECT * FROM tbl_edict";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    ArrayList<String> words = new ArrayList<>();
                    while (rs.next()) {
                        words.add(rs.getString(2));
                    }
                    return words;
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Transfer all word to the Trie structure.
     */
    public static void initialize() throws SQLException {
        connectToDatabase();
        System.out.println("Trie has collected all the data");
        ArrayList<String> words = getAllWords();
        for (String word : words) {
            Trie.insertWord(word);
        }
    }

    /**
     * search word from database.
     */
    public static String lookUpWord(final String target) {
        final String SQL_QUERY = "SELECT detail FROM tbl_edict WHERE word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            try {

                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        String explain = htmlToText(rs.getString("detail"));
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

    public static String lookUpWordEditWindow(final String target) {
        final String SQL_QUERY = "SELECT detail FROM tbl_edict WHERE word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            try {

                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        String explain = rs.getString("detail");
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

    /**
     * delete word from database.
     */
    public static boolean deleteWord(final String word) {
        final String SQL_QUERY = "DELETE FROM tbl_edict WHERE word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word);
            try {
                int rowsDeletion = ps.executeUpdate();
                if (rowsDeletion == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
            Trie.deleteWord(word);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update the new definition.
     */
    public static boolean updateWordDefinition(final String word, final String definition) {
        final String SQL_QUERY = "UPDATE tbl_edict SET detail = ? WHERE word = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, definition);
            ps.setString(2, word);
            try {
                int rowsUpdate = ps.executeUpdate();
                if (rowsUpdate == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Insert new word into the dtb
     */
    public static boolean insertWord(final String word, final String definition) {
        final String SQL_QUERY = "INSERT INTO tbl_edict (word, detail) VALUES (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word);
            ps.setString(2, definition);
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                return false;
            } finally {
                close(ps);
            }
            Trie.insertWord(word);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

