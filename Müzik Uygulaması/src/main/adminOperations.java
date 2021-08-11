package main;

import java.sql.SQLException;
import java.util.ArrayList;

public class adminOperations extends DBHelper implements adminInterface {
    @Override
    public void addArtist(int id, String name, String country) throws SQLException {
        String query = String.format("INSERT INTO artists VALUES(%d, '%s', '%s');", id, name, country);
        insertIntoArtists(query);
    }

    @Override
    public void addMusic(int id, String name, String date, String artist, String album, String genre, int listenings) throws SQLException {
        switch (genre) {
            case "Pop":
                String query = String.format("INSERT INTO popmusics VALUES(%d, '%s', '%s', '%s', '%s', '%s', %d);", id, name, date, artist, album, genre, listenings);
                insertIntoMusics(query);
                break;
            case "Jazz":
                String query2 = String.format("INSERT INTO jazzmusics VALUES(%d, '%s', '%s', '%s', '%s', '%s', %d);", id, name, date, artist, album, genre, listenings);
                insertIntoMusics(query2);
                break;
            case "Klasik":
                String query3 = String.format("INSERT INTO klasikmusics VALUES(%d, '%s', '%s', '%s', '%s', '%s', %d);", id, name, date, artist, album, genre, listenings);
                insertIntoMusics(query3);
                break;
            default:
                System.out.println("Unknown Genre");
                break;
        }
    }

    @Override
    public void deleteArtist(String name) {
        String query = String.format("DELETE FROM artists WHERE Name='%s'", name);
        delete(query);
    }

    @Override
    public void deleteMusic(String musicDT, String name) {
        String query = String.format("DELETE FROM %s WHERE Name='%s'",musicDT, name);
        delete(query);

        ArrayList<String> userNames = getUserNames();
        for (String userName: userNames) {
            String userPopDT = userName + "poplist";
            String userJazzDT = userName + "jazzlist";
            String userKlasikDT = userName + "klasiklist";

            String queryPop = String.format("DELETE FROM %s WHERE Name='%s';",userPopDT, name);
            String queryJazz = String.format("DELETE FROM %s WHERE Name='%s';",userJazzDT, name);
            String queryKlasik = String.format("DELETE FROM %s WHERE Name='%s';",userKlasikDT, name);

            delete(queryPop);
            delete(queryJazz);
            delete(queryKlasik);
        }
    }

    @Override
    public void updateArtist(String artistNameToUpdate, int updatedID, String updatedName, String updatedCountry) {
        String query = String.format("UPDATE artists SET ID=%d, Name='%s', Country='%s' WHERE Name='%s';", updatedID, updatedName, updatedCountry, artistNameToUpdate);
        update(query);

        ArrayList<String> userNames = getUserNames();

        for (String name: userNames) {
            String userPop = name + "poplist";
            String userJazz = name + "jazzlist";
            String userKlasik = name + "klasiklist";

            String userPopQuery = String.format("UPDATE %s SET Artist='%s' WHERE Artist='%s';", userPop, updatedName, artistNameToUpdate);
            String userJazzQuery = String.format("UPDATE %s SET Artist='%s' WHERE Artist='%s';", userJazz, updatedName, artistNameToUpdate);
            String userKlasikQuery = String.format("UPDATE %s SET Artist='%s' WHERE Artist='%s';", userKlasik, updatedName, artistNameToUpdate);

            update(userPopQuery);
            update(userJazzQuery);
            update(userKlasikQuery);
        }

        String popQuery = String.format("UPDATE popmusics SET Artist='%s' WHERE Artist='%s';", updatedName, artistNameToUpdate);
        String jazzQuery = String.format("UPDATE jazzmusics SET Artist='%s' WHERE Artist='%s';", updatedName, artistNameToUpdate);
        String klasikQuery = String.format("UPDATE klasikmusics SET Artist='%s' WHERE Artist='%s';", updatedName, artistNameToUpdate);

        update(popQuery);
        update(jazzQuery);
        update(klasikQuery);
    }

    @Override
    public void updateMusic(String musicNameToUpdate, String updatedMusicName, String updatedAlbum, int updatedListenings) {
        String musicGenre = getMusicGenre(musicNameToUpdate);
        ArrayList<String> userNames = getUserNames();

        switch (musicGenre) {
            case "Pop":
                String query = String.format("UPDATE popmusics SET Name='%s', Album='%s', Listenings=%d WHERE Name='%s';", updatedMusicName, updatedAlbum, updatedListenings, musicNameToUpdate);
                for (String name: userNames) {
                    String userDT = name + "poplist";
                    String userQuery = String.format("UPDATE %s SET Name='%s' WHERE Name='%s';", userDT, updatedMusicName, musicNameToUpdate);
                    update(userQuery);
                }
                update(query);
                break;
            case "Jazz":
                String query2 = String.format("UPDATE jazzmusics SET Name='%s', Album='%s', Listenings=%d WHERE Name='%s';", updatedMusicName, updatedAlbum, updatedListenings, musicNameToUpdate);
                for (String name: userNames) {
                    String userDT = name + "jazzlist";
                    String userQuery = String.format("UPDATE %s SET Name='%s' WHERE Name='%s';", userDT, updatedMusicName, musicNameToUpdate);
                    update(userQuery);
                }
                update(query2);
                break;
            case "Klasik":
                String query3 = String.format("UPDATE klasikmusics SET Name='%s', Album='%s', Listenings=%d WHERE Name='%s';", updatedMusicName, updatedAlbum, updatedListenings, musicNameToUpdate);
                for (String name: userNames) {
                    String userDT = name + "klasiklist";
                    String userQuery = String.format("UPDATE %s SET Name='%s' WHERE Name='%s';", userDT, updatedMusicName, musicNameToUpdate);
                    update(userQuery);
                }
                update(query3);
                break;
            default:
                System.out.println("Unknown");
                break;
        }
    }
}
