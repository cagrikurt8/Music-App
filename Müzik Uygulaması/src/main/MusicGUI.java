package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class MusicGUI {
    private JPanel MainPanel;
    private JLabel welcomeLabel;
    private JLabel adminNameLabel;
    private JTextField adminNameField;
    private JLabel adminPasswordLabel;
    private JPasswordField adminPasswordField;
    private JButton adminLoginButton;
    private JTabbedPane loginTabs;
    private JPanel userPanel;
    private JLabel userNameLabel;
    private JTextField userNameField;
    private JLabel userPasswordLabel;
    private JPasswordField userPasswordField;
    private JButton userLoginButton;
    private JPanel adminPanel;
    private JPanel operationPanel;
    private JLabel operationHeader;
    private JTabbedPane adminOperationTabs;
    private JTabbedPane şarkıGüncellePane;
    private JTabbedPane sanatçıGüncellePane;
    private JTextField newMusicID;
    private JTextField newMusicName;
    private JTextField newMusicDate;
    private JTextField newMusicArtist;
    private JTextField newMusicAlbum;
    private JComboBox newMusicGenre;
    private JTextField newMusicListenings;
    private JButton yeniŞarkıEkleButton;
    private JTextField updatedMusicName;
    private JTextField updatedAlbumName;
    private JTextField updatedListenings;
    private JButton şarkıGüncelleButton;
    private JButton klasikSilButton;
    private JTextField şarkıcıID;
    private JTextField şarkıcıName;
    private JTextField şarkıcıCountry;
    private JButton sanatçıEkleButton;
    private JTextField updatedArtistID;
    private JTextField updatedArtistName;
    private JTextField updatedArtistCountry;
    private JButton sanatçıGüncelleButton;
    private JButton sanatçıSilButton;
    private JComboBox artistDeletionList;
    private JComboBox klasikDeletionList;
    private JTextField registerID;
    private JTextField registerEmail;
    private JComboBox registerSubscription;
    private JTextField registerCountry;
    private JButton registerButton;
    private JPasswordField registerPassword;
    private JTextField registerUsername;
    private JTable userTable;
    private JScrollPane userDataPanel;
    private JTabbedPane userOperationTabs;
    private JTable userPopTable;
    private JScrollPane userPopPane;
    private JButton addKlasikButton;
    private JComboBox klasikMusicList;
    private JScrollPane klasikMusicTablePane;
    private JTable followingTable;
    private JComboBox followingUserList;
    private JTable followingPopTable;
    private JButton addPoplistButton;
    private JTable klasikMusicTable;
    private JTabbedPane tabbedPane3;
    private JComboBox usersToFollowList;
    private JButton takipEtButton;
    private JTable usersToFollow;
    private JButton getirButton;
    private JButton şarkılarıGetirButton;
    private JButton takipEdilenleriGetirButton;
    private JTabbedPane tabbedPane1;
    private JTable followingJazzTable;
    private JTable followingKlasikTable;
    private JScrollPane followingPopPane;
    private JScrollPane followingJazzPane;
    private JScrollPane followingKlasikPane;
    private JTabbedPane tabbedPane2;
    private JScrollPane userJazzPane;
    private JTable userJazzTable;
    private JTable userKlasikTable;
    private JScrollPane userKlasikPane;
    private JButton addJazzlistButton;
    private JButton addKlasiklistButton;
    private JTabbedPane tabbedPane4;
    private JButton addJazzButon;
    private JTable jazzMusicTable;
    private JButton addPopButton;
    private JTable popMusicTable;
    private JComboBox jazzMusicList;
    private JComboBox popMusicList;
    private JTabbedPane tabbedPane5;
    private JComboBox jazzDeletionList;
    private JButton jazzSilButton;
    private JComboBox popDeletionList;
    private JButton popSilButton;
    private JComboBox musicNameToUpdate;
    private JComboBox artistsToUpdate;
    private JComboBox userPopMusicList;
    private JButton playPopMusic;
    private JComboBox userJazzMusicList;
    private JButton playJazzMusic;
    private JComboBox userKlasikMusicList;
    private JButton playKlasikMusic;
    private JTabbedPane tabbedPane6;
    private JTable popTop10Table;
    private JTable jazzTop10Table;
    private JTable klasikTop10Table;
    private JButton top10Güncelle;
    private JTextField newMusicCountry;
    private boolean isAdmin;
    private boolean ifUserExist;
    private DatabaseCheck check = new DatabaseCheck();
    private DBHelper helper = new DBHelper();
    private adminOperations admin = new adminOperations();

    public MusicGUI() {
        registerSubscription.addItem("Normal");
        registerSubscription.addItem("Premium");
        helper.createMusicsTable();
        helper.createArtistsTable();
        helper.createUsersTable();

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAdmin = adminNameField.getText().equals("admin") && adminPasswordField.getText().equals("admin");

                if (isAdmin) {
                    removeAllItems();
                    setAdminLists();

                    operationHeader.setText("Admin Paneli");

                    try {
                        setUserTable();
                    } catch (SQLException exception) {
                        helper.showErrorMessage(exception);
                    }

                    try {
                        setDeletionLists();
                    } catch (SQLException exception) {
                        helper.showErrorMessage(exception);
                    }

                    operationPanel.setVisible(true);
                    userOperationTabs.setVisible(false);
                    adminOperationTabs.setVisible(true);
                } else {
                    operationPanel.setVisible(false);
                    userOperationTabs.setVisible(false);
                    adminOperationTabs.setVisible(false);
                    JOptionPane.showMessageDialog(new JFrame(), "Admin bilgileri doğru değil!", "Inane warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        userLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAllItems();
                followingPopTable.setModel(new DefaultTableModel());
                followingJazzTable.setModel(new DefaultTableModel());
                followingKlasikTable.setModel(new DefaultTableModel());

                try {
                    ifUserExist = check.ifUserExist(userNameField.getText(), userPasswordField.getText());
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }

                if (ifUserExist) {
                    helper.createUserPlaylist(userNameField.getText());
                    try {
                        setPanelHeader();
                    } catch (SQLException exception) {
                        helper.showErrorMessage(exception);
                    }

                    try {
                        setMusicTable();
                        setUserPlaylist();
                        setSelectionLists();
                        setUsersToFollow();
                        setFollowingTable();
                        setTop10Tables();
                    } catch (SQLException exception) {
                        helper.showErrorMessage(exception);
                    }
                    operationPanel.setVisible(true);
                    userOperationTabs.setVisible(true);
                    adminOperationTabs.setVisible(false);
                } else {
                    operationPanel.setVisible(false);
                    userOperationTabs.setVisible(false);
                    adminOperationTabs.setVisible(false);
                    JOptionPane.showMessageDialog(new JFrame(), "Kullanıcı bulunamadı!", "Inane warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    helper.createUserAccount(Integer.parseInt(registerID.getText()), registerUsername.getText(), registerEmail.getText(), registerPassword.getText(), registerSubscription.getSelectedItem().toString(), registerCountry.getText());
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                    JOptionPane.showMessageDialog(new JFrame(), "Farklı ID girip kayıt işlemini tamamlayın!", "Inane warning", JOptionPane.WARNING_MESSAGE);
                }

                helper.createUserPlaylist(registerUsername.getText());
                helper.createFollowingList(registerUsername.getText());
            }
        });

        getirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setFollowingPlaylistTable();
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        });

        takipEtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userDT = getUserName() + "followinglist";
                String userToFollow = usersToFollowList.getSelectedItem().toString();
                try {
                    helper.insertIntoFollowingList(userDT, userToFollow);
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        });

        şarkılarıGetirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setUserPlaylist();

                    userPopMusicList.removeAllItems();
                    userJazzMusicList.removeAllItems();
                    userKlasikMusicList.removeAllItems();

                    for (String name : helper.getMusicNames(getUserName() + "poplist")) {
                        userPopMusicList.addItem(name);
                    }

                    for (String name : helper.getMusicNames(getUserName() + "jazzlist")) {
                        userJazzMusicList.addItem(name);
                    }

                    for (String name : helper.getMusicNames(getUserName() + "klasiklist")) {
                        userKlasikMusicList.addItem(name);
                    }
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        });

        takipEdilenleriGetirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    followingUserList.removeAllItems();
                    setFollowingTable();
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        });

        addPoplistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userDT = getUserName() + "poplist";
                String followingDT = followingUserList.getSelectedItem().toString() + "poplist";
                helper.insertIntoPlaylistFromFollowing(userDT, followingDT);
            }
        });

        addJazzlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userDT = getUserName() + "jazzlist";
                String followingDT = followingUserList.getSelectedItem().toString() + "jazzlist";
                helper.insertIntoPlaylistFromFollowing(userDT, followingDT);
            }
        });

        addKlasiklistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userDT = getUserName() + "klasiklist";
                String followingDT = followingUserList.getSelectedItem().toString() + "klasiklist";
                helper.insertIntoPlaylistFromFollowing(userDT, followingDT);
            }
        });

        addPopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userDT = getUserName() + "poplist";
                String music = popMusicList.getSelectedItem().toString();
                helper.insertIntoPlaylist(userDT, music, "popmusics");
                try {
                    userPopMusicList.removeAllItems();
                    for (String name : helper.getMusicNames(getUserName() + "poplist")) {
                        userPopMusicList.addItem(name);
                    }

                    popMusicList.removeAllItems();
                    for (String name: helper.getMusicNames("popmusics")) {
                        if (((DefaultComboBoxModel)userPopMusicList.getModel()).getIndexOf(name) == -1) {
                            popMusicList.addItem(name);
                        }
                    }
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        });

        addJazzButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userDT = getUserName() + "jazzlist";
                String music = jazzMusicList.getSelectedItem().toString();
                helper.insertIntoPlaylist(userDT, music, "jazzmusics");
                try {
                    userJazzMusicList.removeAllItems();
                    for (String name : helper.getMusicNames(getUserName() + "jazzlist")) {
                        userJazzMusicList.addItem(name);
                    }

                    jazzMusicList.removeAllItems();
                    for (String name: helper.getMusicNames("jazzmusics")) {
                        if (((DefaultComboBoxModel)userJazzMusicList.getModel()).getIndexOf(name) == -1) {
                            jazzMusicList.addItem(name);
                        }
                    }
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        });

        addKlasikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userDT = getUserName() + "klasiklist";
                String music = klasikMusicList.getSelectedItem().toString();
                helper.insertIntoPlaylist(userDT, music, "klasikmusics");
                try {
                    userKlasikMusicList.removeAllItems();
                    for (String name : helper.getMusicNames(getUserName() + "klasiklist")) {
                        userKlasikMusicList.addItem(name);
                    }

                    klasikMusicList.removeAllItems();
                    for (String name: helper.getMusicNames("klasikmusics")) {
                        if (((DefaultComboBoxModel)userKlasikMusicList.getModel()).getIndexOf(name) == -1) {
                            klasikMusicList.addItem(name);
                        }
                    }
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        });

        yeniŞarkıEkleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    admin.addMusic(Integer.parseInt(newMusicID.getText()), newMusicName.getText(), newMusicDate.getText(), newMusicArtist.getText(), newMusicAlbum.getText(), newMusicGenre.getSelectedItem().toString(), 0);
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(new JFrame(), "Farklı  müzik ID girip kayıt işlemini tamamlayın!", "Inane warning", JOptionPane.WARNING_MESSAGE);
                    helper.showErrorMessage(exception);
                }

                try {
                    admin.addArtist(Integer.parseInt(newMusicID.getText()), newMusicArtist.getText(), newMusicCountry.getText());
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(new JFrame(), "Farklı  sanatçı ID girip kayıt işlemini tamamlayın!", "Inane warning", JOptionPane.WARNING_MESSAGE);
                    helper.showErrorMessage(exception);
                }
            }
        });

        sanatçıEkleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    admin.addArtist(Integer.parseInt(şarkıcıID.getText()), şarkıcıName.getText(), şarkıcıCountry.getText());
                } catch (SQLException exception) {
                    JOptionPane.showMessageDialog(new JFrame(), "Farklı  sanatçı ID girip kayıt işlemini tamamlayın!", "Inane warning", JOptionPane.WARNING_MESSAGE);
                    helper.showErrorMessage(exception);
                }
            }
        });

        popSilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.deleteMusic("popmusics", popDeletionList.getSelectedItem().toString());
            }
        });

        jazzSilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.deleteMusic("jazzmusics", jazzDeletionList.getSelectedItem().toString());
            }
        });

        klasikSilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.deleteMusic("klasikmusics", klasikDeletionList.getSelectedItem().toString());
            }
        });

        sanatçıSilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.deleteArtist(artistDeletionList.getSelectedItem().toString());
            }
        });

        şarkıGüncelleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.updateMusic(musicNameToUpdate.getSelectedItem().toString(), updatedMusicName.getText(), updatedAlbumName.getText(), Integer.parseInt(updatedListenings.getText()));
            }
        });

        sanatçıGüncelleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.updateArtist(artistsToUpdate.getSelectedItem().toString(), Integer.parseInt(updatedArtistID.getText()), updatedArtistName.getText(), updatedArtistCountry.getText());
            }
        });

        playPopMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    helper.updateListenings("popmusics", userPopMusicList.getSelectedItem().toString());
            }
        });

        playJazzMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helper.updateListenings("jazzmusics", userJazzMusicList.getSelectedItem().toString());
            }
        });

        playKlasikMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helper.updateListenings("klasikmusics", userKlasikMusicList.getSelectedItem().toString());
            }
        });

        top10Güncelle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTop10Tables();
            }
        });
    }

    public void setPanelHeader() throws SQLException {
        if (check.isPremium(getUserName())) {
            operationHeader.setText("Hoşgeldiniz " + getUserName() + "(Premium - Ödendi)");
        } else {
            operationHeader.setText("Hoşgeldiniz " + getUserName());
        }
    }

    public void setAdminLists() {
        newMusicGenre.addItem("Pop");
        newMusicGenre.addItem("Jazz");
        newMusicGenre.addItem("Klasik");

        try {
            ArrayList<String> popMusics = helper.getMusicNames("popmusics");
            for (String name : popMusics) {
                musicNameToUpdate.addItem(name);
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        try {
            ArrayList<String> jazzMusics = helper.getMusicNames("jazzmusics");
            for (String name : jazzMusics) {
                musicNameToUpdate.addItem(name);
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        try {
            ArrayList<String> klasikMusics = helper.getMusicNames("klasikmusics");
            for (String name : klasikMusics) {
                musicNameToUpdate.addItem(name);
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        try {
            for (String name: helper.getArtistNames()) {
                artistsToUpdate.addItem(name);
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
    }

    public String getUserName() {
        return userNameField.getText();
    }

    public void setUserTable() throws SQLException {
        String[] columns = new String[] {"Kullanıcı Adı", "Email", "Abonelik", "Ülke"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        for (String[] row : helper.getUserList()) {
            model.addRow(row);
        }
        userTable.setModel(model);
    }

    public void setSelectionLists() throws SQLException {
        for (String name : helper.getMusicNames(getUserName() + "poplist")) {
            userPopMusicList.addItem(name);
        }

        for (String name : helper.getMusicNames(getUserName() + "jazzlist")) {
            userJazzMusicList.addItem(name);
        }

        for (String name : helper.getMusicNames(getUserName() + "klasiklist")) {
            userKlasikMusicList.addItem(name);
        }

        for (String name: helper.getMusicNames("popmusics")) {
            if (((DefaultComboBoxModel)userPopMusicList.getModel()).getIndexOf(name) == -1) {
                popMusicList.addItem(name);
            }
        }

        for (String name: helper.getMusicNames("jazzmusics")) {
            if (((DefaultComboBoxModel)userJazzMusicList.getModel()).getIndexOf(name) == -1) {
                jazzMusicList.addItem(name);
            }
        }

        for (String name: helper.getMusicNames("klasikmusics")) {
            if (((DefaultComboBoxModel)userKlasikMusicList.getModel()).getIndexOf(name) == -1) {
                klasikMusicList.addItem(name);
            }
        }

        for (String name : helper.getPremiumUsers(getUserName(), getUserName() + "followinglist")) {
            usersToFollowList.addItem(name);
        }
    }

    public void removeAllItems() {
        klasikMusicList.removeAllItems();
        jazzMusicList.removeAllItems();
        popMusicList.removeAllItems();
        usersToFollowList.removeAllItems();
        followingUserList.removeAllItems();
        artistDeletionList.removeAllItems();
        musicNameToUpdate.removeAllItems();
        popDeletionList.removeAllItems();
        artistDeletionList.removeAllItems();
        jazzDeletionList.removeAllItems();
        artistsToUpdate.removeAllItems();
        newMusicGenre.removeAllItems();
        userPopMusicList.removeAllItems();
        userJazzMusicList.removeAllItems();
        userKlasikMusicList.removeAllItems();
    }

    public void setDeletionLists() throws SQLException {
        for (String name: helper.getArtistNames()) {
            artistDeletionList.addItem(name);
        }

        for (String name: helper.getMusicNames("popmusics")) {
            popDeletionList.addItem(name);
        }

        for (String name: helper.getMusicNames("jazzmusics")) {
            jazzDeletionList.addItem(name);
        }

        for (String name: helper.getMusicNames("klasikmusics")) {
            klasikDeletionList.addItem(name);
        }
    }

    public void setMusicTable() throws SQLException {
        String[] columns = new String[] {"Şarkı Adı", "Şarkıcı", "Çıkış Tarihi", "Albüm", "Türü"};

        DefaultTableModel modelPop = new DefaultTableModel();
        DefaultTableModel modelJazz = new DefaultTableModel();
        DefaultTableModel modelKlasik = new DefaultTableModel();

        modelPop.setColumnIdentifiers(columns);
        modelJazz.setColumnIdentifiers(columns);
        modelKlasik.setColumnIdentifiers(columns);

        for (String[] row : helper.getMusicList("popmusics")) {
            modelPop.addRow(row);
        }

        for (String[] row : helper.getMusicList("jazzmusics")) {
            modelJazz.addRow(row);
        }

        for (String[] row : helper.getMusicList("klasikmusics")) {
            modelKlasik.addRow(row);
        }
        popMusicTable.setModel(modelPop);
        jazzMusicTable.setModel(modelJazz);
        klasikMusicTable.setModel(modelKlasik);
    }

    public void setUserPlaylist() throws SQLException {
        String userNamePOP = getUserName() + "poplist";
        String userNameJazz = getUserName() + "jazzlist";
        String userNameKlasik = getUserName() + "klasiklist";
        String[] columns = new String[] {"Şarkı Adı", "Şarkıcı", "Türü"};

        DefaultTableModel modelPOP = new DefaultTableModel();
        DefaultTableModel modelJAZZ = new DefaultTableModel();
        DefaultTableModel modelKLASIK = new DefaultTableModel();

        modelPOP.setColumnIdentifiers(columns);
        modelJAZZ.setColumnIdentifiers(columns);
        modelKLASIK.setColumnIdentifiers(columns);

        for(String[] row : helper.getUserPlaylist(userNamePOP)) {
            modelPOP.addRow(row);
        }

        for(String[] row : helper.getUserPlaylist(userNameJazz)) {
            modelJAZZ.addRow(row);
        }

        for(String[] row : helper.getUserPlaylist(userNameKlasik)) {
            modelKLASIK.addRow(row);
        }
        userPopTable.setModel(modelPOP);
        userJazzTable.setModel(modelJAZZ);
        userKlasikTable.setModel(modelKLASIK);
    }

    public void setUsersToFollow() throws SQLException {
        String[] columns = new String[]{"Kullanıcı Adı", "Abonelik", "Ülke"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        for (String[] row : helper.getPremiumUserTable(getUserName(), getUserName() + "followinglist")) {
            model.addRow(row);
        }
        usersToFollow.setModel(model);
    }

    public void setFollowingTable() throws SQLException {
            String userFollowingDT = getUserName().toLowerCase() + "followinglist";
            String[] columns = new String[]{"Kullanıcı Adı", "Ülke"};
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(columns);
            for (String[] row : helper.getFollowingTable(userFollowingDT)) {
                model.addRow(row);
                followingUserList.addItem(row[0]);
            }

            followingTable.setModel(model);
    }

    public void setFollowingPlaylistTable() throws SQLException {
        String userFollowingPOP = followingUserList.getSelectedItem().toString() + "poplist";
        String userFollowingJAZZ = followingUserList.getSelectedItem().toString() + "jazzlist";
        String userFollowingKLASIK = followingUserList.getSelectedItem().toString() + "klasiklist";

        String[] columns = new String[] {"Şarkı Adı", "Şarkıcı", "Türü"};

        DefaultTableModel modelPop = new DefaultTableModel();
        DefaultTableModel modelJazz = new DefaultTableModel();
        DefaultTableModel modelKlasik = new DefaultTableModel();

        modelPop.setColumnIdentifiers(columns);
        modelJazz.setColumnIdentifiers(columns);
        modelKlasik.setColumnIdentifiers(columns);

        for(String[] row : helper.getUserPlaylist(userFollowingPOP)) {
            modelPop.addRow(row);
        }

        for(String[] row : helper.getUserPlaylist(userFollowingJAZZ)) {
            modelJazz.addRow(row);
        }

        for(String[] row : helper.getUserPlaylist(userFollowingKLASIK)) {
            modelKlasik.addRow(row);
        }
        followingPopTable.setModel(modelPop);
        followingJazzTable.setModel(modelJazz);
        followingKlasikTable.setModel(modelKlasik);
    }

    public void setTop10Tables() {
        String[] columns = new String[] {"Şarkı Adı", "Şarkıcı", "Çıkış Tarihi", "Albüm", "Dinlenme Sayısı"};

        DefaultTableModel top10PopModel = new DefaultTableModel();
        DefaultTableModel top10JazzModel = new DefaultTableModel();
        DefaultTableModel top10KlasikModel = new DefaultTableModel();

        top10PopModel.setColumnIdentifiers(columns);
        top10JazzModel.setColumnIdentifiers(columns);
        top10KlasikModel.setColumnIdentifiers(columns);

        for(String[] row : helper.getTop10Table("popmusics")) {
            top10PopModel.addRow(row);
        }

        for(String[] row : helper.getTop10Table("jazzmusics")) {
            top10JazzModel.addRow(row);
        }

        for(String[] row : helper.getTop10Table("klasikmusics")) {
            top10KlasikModel.addRow(row);
        }

        popTop10Table.setModel(top10PopModel);
        jazzTop10Table.setModel(top10JazzModel);
        klasikTop10Table.setModel(top10KlasikModel);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JFrame jFrame = new JFrame("App");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        jFrame.setContentPane(new MusicGUI().MainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
