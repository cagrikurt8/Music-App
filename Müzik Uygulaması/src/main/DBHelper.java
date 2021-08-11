package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DBHelper {
    private final String userName = "root"; // database username, default olarak root, farklı ise değiştirin.
    private final String password = "admin"; // database e bağlanırken seçilen password.
    private final String dbURL = "jdbc:mysql://localhost:3306/music"; // localhost üzerinde default olarak 3306 portuyla database bağlantı adresi. Farklı port seçildiyse değiştirin. Bağlanılan database -> music
    private Connection connection;
    private Statement stmt;
    private Statement stmt2;
    private Statement stmt3;
    private ResultSet rs;
    private ResultSet rs2;

    public DBHelper() {
        try {
            connection = DriverManager.getConnection(dbURL, userName, password);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        try {
            stmt = connection.createStatement();
            stmt2 = connection.createStatement();
            stmt3 = connection.createStatement();
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }
    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void showErrorMessage(SQLException exception) {
        System.out.println("Error : " + exception.getMessage());
        System.out.println("Error code : " + exception.getErrorCode());
    }

    // Oluşturma metodları

    public void createMusicsTable() {
        String query = "CREATE TABLE IF NOT EXISTS popmusics (" +
                "ID int(11) NOT NULL," +
                "Name varchar(50) NOT NULL," +
                "Date varchar (50) NOT NULL," +
                "Artist varchar(50) NOT NULL," +
                "Album varchar(50) NOT NULL," +
                "Genre varchar(50) NOT NULL," +
                "Listenings int(11) NOT NULL," +
                "UNIQUE (ID)," +
                "PRIMARY KEY (Name)," +
                "FOREIGN KEY (Artist) REFERENCES artists (Name));";

        String query2 = "CREATE TABLE IF NOT EXISTS jazzmusics (" +
                "ID int(11) NOT NULL," +
                "Name varchar(50) NOT NULL," +
                "Date varchar (50) NOT NULL," +
                "Artist varchar(50) NOT NULL," +
                "Album varchar(50) NOT NULL," +
                "Genre varchar(50) NOT NULL," +
                "Listenings int(11) NOT NULL," +
                "UNIQUE (ID)," +
                "PRIMARY KEY (Name)," +
                "FOREIGN KEY (Artist) REFERENCES artists (Name));";

        String query3 = "CREATE TABLE IF NOT EXISTS klasikmusics (" +
                "ID int(11) NOT NULL," +
                "Name varchar(50) NOT NULL," +
                "Date varchar (50) NOT NULL," +
                "Artist varchar(50) NOT NULL," +
                "Album varchar(50) NOT NULL," +
                "Genre varchar(50) NOT NULL," +
                "Listenings int(11) NOT NULL," +
                "UNIQUE (ID)," +
                "PRIMARY KEY (Name)," +
                "FOREIGN KEY (Artist) REFERENCES artists (Name));";
        try {
            stmt.execute("SET foreign_key_checks = 0;");
            stmt.execute("SET SQL_SAFE_UPDATES=0;");
            stmt.execute(query);
            stmt.execute(query2);
            stmt.execute(query3);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    public void createArtistsTable() {
        String query = "CREATE TABLE IF NOT EXISTS artists(" +
                "ID int(11) NOT NULL," +
                "Name varchar(50) NOT NULL," +
                "Country varchar(50) NOT NULL," +
                "UNIQUE (ID)," +
                "PRIMARY KEY (Name));";
        try {
            stmt.execute("SET foreign_key_checks = 0;");
            stmt.execute("SET SQL_SAFE_UPDATES=0;");
            stmt.execute(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users(" +
                "ID int(11) NOT NULL," +
                "UserName varchar(50) NOT NULL," +
                "Email varchar(50) NOT NULL," +
                "Password varchar(50) NOT NULL," +
                "Subscription varchar(50) NOT NULL," +
                "Country varchar(50) NOT NULL," +
                "UNIQUE (ID)," +
                "PRIMARY KEY (UserName));";

        try {
            stmt.execute("SET foreign_key_checks = 0;");
            stmt.execute("SET SQL_SAFE_UPDATES=0;");
            stmt.execute(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    public void createUserPlaylist(String userName) {
        String query = String.format("CREATE TABLE IF NOT EXISTS %spoplist(" +
                "Name varchar(50) NOT NULL," +
                "Artist varchar(50) NOT NULL," +
                "Genre varchar(50) NOT NULL," +
                "FOREIGN KEY (Name) REFERENCES popmusics (Name)," +
                "FOREIGN KEY (Artist) REFERENCES artist (Name));", userName);

        String query2 = String.format("CREATE TABLE IF NOT EXISTS %sjazzlist(" +
                "Name varchar(50) NOT NULL," +
                "Artist varchar(50) NOT NULL," +
                "Genre varchar(50) NOT NULL," +
                "FOREIGN KEY (Name) REFERENCES jazzmusics (Name)," +
                "FOREIGN KEY (Artist) REFERENCES artist (Name));", userName);

        String query3 = String.format("CREATE TABLE IF NOT EXISTS %sklasiklist(" +
                "Name varchar(50) NOT NULL," +
                "Artist varchar(50) NOT NULL," +
                "Genre varchar(50) NOT NULL," +
                "FOREIGN KEY (Name) REFERENCES klasikmusics (Name)," +
                "FOREIGN KEY (Artist) REFERENCES artist (Name));", userName);

        try {
            stmt.execute("SET foreign_key_checks = 0;");
            stmt.execute("SET SQL_SAFE_UPDATES=0;");
            stmt.execute(query);
            stmt.execute(query2);
            stmt.execute(query3);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    public void createUserAccount(int id, String userName, String email, String password, String subscription, String country) throws SQLException {
        String query = String.format("INSERT INTO users VALUES(%d, '%s', '%s', '%s', '%s', '%s');", id, userName, email, password, subscription, country);

        stmt.execute("SET foreign_key_checks = 0;");
        stmt.execute("SET SQL_SAFE_UPDATES=0;");
        stmt.execute(query);
    }

    public void createFollowingList(String userName) {
        String query = String.format("CREATE TABLE IF NOT EXISTS %sFollowingList(" +
                "UserName varchar(50)," +
                "Country varchar(50)," +
                "FOREIGN KEY (UserName) REFERENCES users (UserName));", userName);

        try {
            stmt.execute("SET foreign_key_checks = 0;");
            stmt.execute("SET SQL_SAFE_UPDATES=0;");
            stmt.execute(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    // Ekleme metodları

    public void insertIntoMusics(String query) throws SQLException {
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.execute(query);
    }

    public void insertIntoArtists(String query) throws SQLException {
        stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        stmt.execute(query);
    }

    public void insertIntoFollowingList(String userDT, String userToFollow) throws SQLException {
        String query = String.format("SELECT * FROM users WHERE UserName='%s';",userToFollow);
        try {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        try {
            while (rs.next()) {
                String name = rs.getString("UserName");
                String country = rs.getString("Country");
                String query2 = String.format("INSERT INTO %s VALUES('%s', '%s');", userDT, name, country);
                stmt2.execute(query2);
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    public void insertIntoPlaylist(String userDT, String music, String musicDT) {
        String query = String.format("SELECT Name, Artist, Genre FROM %s WHERE Name='%s';", musicDT, music);

        try {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        try {
            while(rs.next()) {
                String name = rs.getString("Name");
                String artist = rs.getString("Artist");
                String genre = rs.getString("Genre");
                String query2 = String.format("INSERT INTO %s VALUES('%s', '%s', '%s');", userDT, name, artist, genre);
                stmt2.execute(query2);
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    public void insertIntoPlaylistFromFollowing(String userDT, String followingDT) {
        String query = String.format("SELECT Name, Artist, Genre FROM %s;", followingDT);

        try {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        try {
            while(rs.next()) {
                String name = rs.getString("Name");
                String artist = rs.getString("Artist");
                String genre = rs.getString("Genre");
                String query2 = String.format("INSERT INTO %s VALUES('%s', '%s', '%s');", userDT, name, artist, genre);
                stmt2.execute(query2);
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    // Get metodları

    public ArrayList<String> getArtistNames() throws SQLException {
        ArrayList<String> artisNames = new ArrayList<>();
        String query = "SELECT Name FROM artists;";

        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        while (rs.next()) {
            artisNames.add(rs.getString("Name"));
        }
        return artisNames;
    }

    public ArrayList<String> getMusicNames(String musicDT) throws SQLException {
        ArrayList<String> musicNames = new ArrayList<>();
        String query = String.format("SELECT Name FROM %s;", musicDT);

        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        while (rs.next()) {
            musicNames.add(rs.getString("Name"));
        }
        return musicNames;
    }

    public String getMusicGenre(String name) {
        String query = String.format("SELECT Name FROM popmusics WHERE Name='%s';", name);
        String query2 = String.format("SELECT Name FROM jazzmusics WHERE Name='%s';", name);
        String query3 = String.format("SELECT Name FROM klasikmusics WHERE Name='%s';", name);

        try {
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                return "Pop";
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        try {
            rs = stmt2.executeQuery(query2);
            if (rs.next()) {
                return "Jazz";
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        try {
            rs = stmt3.executeQuery(query3);
            if (rs.next()) {
                return "Klasik";
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
        return "";
    }

    public String[][] getUserList() throws SQLException {
        ArrayList<ArrayList<String>> userList = new ArrayList<>();
        String query = "SELECT * FROM users";

        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        while (rs.next()) {
            String userName = rs.getString("UserName");
            String email = rs.getString("Email");
            String subscription = rs.getString("Subscription");
            String country = rs.getString("Country");
            ArrayList<String> temp = new ArrayList<>();

            temp.add(userName);
            temp.add(email);
            temp.add(subscription);
            temp.add(country);

            userList.add(temp);
        }
        String[][] finalList = new String[userList.size()][];
        for (int i = 0; i < userList.size(); i++) {
            ArrayList<String> row = userList.get(i);
            finalList[i] = row.toArray(new String[row.size()]);
        }
        return finalList;
    }

    public ArrayList<String> getUserNames() {
        ArrayList<String> names = new ArrayList<>();
        String query = "SELECT UserName FROM users";
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        try {
            while(rs.next()) {
                names.add(rs.getString("UserName"));
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
        return names;
    }

    public String[][] getMusicList(String musicDT) throws SQLException {
        ArrayList<ArrayList<String>> musicList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s;", musicDT);

        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        while(rs.next()) {
            String musicName = rs.getString("Name");
            String artist = rs.getString("Artist");
            String date = rs.getString("Date");
            String album = rs.getString("Album");
            String genre = rs.getString("Genre");

            ArrayList<String> temp = new ArrayList<>();
            temp.add(musicName);
            temp.add(artist);
            temp.add(date);
            temp.add(album);
            temp.add(genre);

            musicList.add(temp);
        }

        String[][] finalList = new String[musicList.size()][];
        for(int i = 0; i < musicList.size(); i++) {
            ArrayList<String> row = musicList.get(i);
            finalList[i] = row.toArray(new String[row.size()]);
        }
        return finalList;
    }

    public String[][] getUserPlaylist(String userDT) throws SQLException {
        ArrayList<ArrayList<String>> playlist = new ArrayList<>();
        String query = String.format("SELECT * FROM %s;", userDT);

        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        try {
            while (rs.next()) {
                String musicName = rs.getString("Name");
                String artist = rs.getString("Artist");
                String genre = rs.getString("Genre");

                ArrayList<String> temp = new ArrayList<>();
                temp.add(musicName);
                temp.add(artist);
                temp.add(genre);

                playlist.add(temp);
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        String[][] finalList = new String[playlist.size()][];
        for(int i = 0; i < playlist.size(); i++) {
            ArrayList<String> row = playlist.get(i);
            finalList[i] = row.toArray(new String[row.size()]);
        }
        return finalList;
    }

    public ArrayList<String> getPremiumUsers(String currentUser, String userFollowingDT) throws SQLException {
        ArrayList<String> finalList = new ArrayList<>();

        String query = "SELECT UserName FROM users WHERE Subscription='Premium';";

        rs = stmt.executeQuery(query);
        while (rs.next()) {
            String query2 = String.format("SELECT UserName FROM %s WHERE UserName='%s';", userFollowingDT, rs.getString("UserName"));
            rs2 = stmt2.executeQuery(query2);

            if (!rs.getString("UserName").equals(currentUser) && !rs2.next()) {
                finalList.add(rs.getString("UserName"));
            }
        }
        return finalList;
    }

    public String[][] getPremiumUserTable(String currentUser, String userFollowingDT) throws SQLException {
        ArrayList<ArrayList<String>> premiumUsers = new ArrayList<>();
        String query = "SELECT * FROM users WHERE Subscription='Premium';";

        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        while(rs.next()) {
            String query2 = String.format("SELECT UserName FROM %s WHERE UserName='%s';", userFollowingDT, rs.getString("UserName"));
            rs2 = stmt2.executeQuery(query2);

            String userName = rs.getString("UserName");
            String subscription = rs.getString("Subscription");
            String country = rs.getString("Country");

            if (!userName.equals(currentUser) && !rs2.next()) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(userName);
                temp.add(subscription);
                temp.add(country);

                premiumUsers.add(temp);
            }
        }

        String[][] finalList = new String[premiumUsers.size()][];
        for(int i = 0; i < premiumUsers.size(); i++) {
            ArrayList<String> row = premiumUsers.get(i);
            finalList[i] = row.toArray(new String[row.size()]);
        }
        return finalList;
    }

    public String[][] getFollowingTable(String userDT) throws SQLException {
        ArrayList<ArrayList<String>> followingList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s;", userDT);

        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        while(rs.next()) {
            String name = rs.getString("UserName");
            String country = rs.getString("Country");

            ArrayList<String> temp = new ArrayList<>();
            temp.add(name);
            temp.add(country);

            followingList.add(temp);
        }

        String[][] finalList = new String[followingList.size()][];
        for(int i = 0; i < followingList.size(); i++) {
            ArrayList<String> row = followingList.get(i);
            finalList[i] = row.toArray(new String[row.size()]);
        }
        return finalList;
    }

    public String[][] getTop10Table(String musicDT) {
        ArrayList<ArrayList<String>> finalTop10 = new ArrayList<>();
        String query = String.format("SELECT Listenings FROM %s", musicDT);
        ArrayList<Integer> listeningNumbers = new ArrayList<>();

        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                listeningNumbers.add(rs.getInt("Listenings"));
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        Collections.sort(listeningNumbers);
        Collections.reverse(listeningNumbers);
        int[] top10Listenings;

        if (listeningNumbers.size() > 10) {
            top10Listenings = new int[10];
            for (int i = 0; i < 10; i++) {
                top10Listenings[i] = listeningNumbers.get(i);
            }
        }

        else {
            top10Listenings = new int[listeningNumbers.size()];
            for (int i = 0; i < top10Listenings.length; i++) {
                top10Listenings[i] = listeningNumbers.get(i);
            }
        }

        for (int listening : top10Listenings) {
            String query2 = String.format("SELECT * FROM %s WHERE Listenings=%d", musicDT, listening);
            try {
                rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    if (rs2.getInt("Listenings") != 0) {
                        ArrayList<String> temp = new ArrayList<>();

                        String name = rs2.getString("Name");
                        String artist = rs2.getString("Artist");
                        String date = rs2.getString("Date");
                        String album = rs2.getString("Album");
                        String listenings = String.valueOf(rs2.getInt("Listenings"));

                        boolean add = true;
                        for (ArrayList<String> check : finalTop10) {
                            if (check.contains(name)) {
                                add = false;
                            }
                        }

                        if (add) {
                            temp.add(name);
                            temp.add(artist);
                            temp.add(date);
                            temp.add(album);
                            temp.add(listenings);

                            finalTop10.add(temp);
                        }
                    }
                }
            } catch (SQLException exception) {
                showErrorMessage(exception);
            }
        }

        String[][] finalList = new String[finalTop10.size()][];
        for(int i = 0; i < finalTop10.size(); i++) {
            ArrayList<String> row = finalTop10.get(i);
            finalList[i] = row.toArray(new String[row.size()]);
        }
        return finalList;
    }

    // Silme metodu

    public void delete(String query) {
        try {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.execute("SET SQL_SAFE_UPDATES=0;");
            stmt.execute(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    // Güncelleme metodları

    public void update(String query) {
        try {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.execute("SET SQL_SAFE_UPDATES=0;");
            stmt.execute(query);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }

    public void updateListenings(String musicDT, String musicName) {
        String query1 = String.format("SELECT Listenings FROM %s WHERE Name='%s';", musicDT, musicName);

        try {
            rs = stmt.executeQuery(query1);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        int currListenings = -1;

        try {
            while (rs.next()) {
                currListenings = rs.getInt("Listenings");
            }
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }

        String query2 = String.format("UPDATE %s SET Listenings=%d WHERE Name='%s';", musicDT, currListenings + 1, musicName);

        try {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.execute("SET SQL_SAFE_UPDATES=0;");
            stmt.execute(query2);
        } catch (SQLException exception) {
            showErrorMessage(exception);
        }
    }
}

