package main;

import java.sql.SQLException;

public interface adminInterface {
    void addArtist(int id, String name, String country) throws SQLException;
    void addMusic(int id, String name, String date, String artist, String album, String genre, int listenings) throws SQLException;
    void deleteArtist(String name);
    void deleteMusic(String musicDT, String name);
    void updateArtist(String artistNameToUpdate, int updatedID, String updatedName, String updatedCountry);
    void updateMusic(String musicNameToUpdate, String updatedMusicName, String updatedAlbum, int updatedListenings);
}
