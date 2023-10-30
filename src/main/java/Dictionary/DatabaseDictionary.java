package Dictionary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseDictionary {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "chi26543";
    private static final String DB_NAME = "edict";
    private static final String MYSQL_URL =
            "jdbc:mysql://localhost:3306/" + DB_NAME;

    private static Connection connection = null;

    public DatabaseDictionary() {
        try {
            connectToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
     * @param html text
     * @return text
     * Ref: https://stackoverflow.com/questions/2513707/how-to-convert-html-to-text-keeping-linebreaks
     */
    public static String htmlToText(String html) {
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

    public void connectToDatabase() throws SQLException {
        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASSWORD);
        if (connection != null) {
            System.out.println("Database is connected");
        }
    }

    public String lookUpWord(final String target) {
        final String SQL_QUERY = "SELECT definition FROM dictionary WHERE target = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            try {

                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        String explain = htmlToText(rs.getString("definition"));
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

    public static void main(String[] args) {
        DatabaseDictionary data = new DatabaseDictionary();
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        System.out.println(data.lookUpWord(s));
        ArrayList<Word> list = new ArrayList<>();
    }
}

