package com.sala_spectacole.persistence;

import com.sala_spectacole.connection.DatabaseConnection;
import com.sala_spectacole.domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaRepository implements Pret {
    private static SalaRepository instance;
    private static final String INSERT_STATEMENT = "INSERT INTO sali (nume, locuriCategoria1, randuriCategoria1, " +
            "locuriCategoria2, randuriCategoria2, locuriLoja, randuriLoja, locuriBalcon, randuriBalcon ) " +
            "VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM sali WHERE nume = ?";
    private static final String SELECT_SALI = "SELECT nume FROM sali";
    private static final String DELETE_SALA = "DELETE FROM sali WHERE nume = ?";
    private static final String UPDATE_SALA = "UPDATE sali SET nume = ? WHERE nume = ?";


    private SalaRepository() {
    }

    public static SalaRepository getInstance() {

        if (instance == null) {
            instance = new SalaRepository();
        }
        return instance;
    }

    public void saveSala(Sala sala) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            statement.setString(1, sala.getNume());
            statement.setInt(2, sala.getLocuriCategoria1());
            statement.setInt(3, sala.getRanduriCategoria1());
            statement.setInt(4, sala.getLocuriCategoria2());
            statement.setInt(5, sala.getRanduriCategoria2());
            statement.setInt(6, sala.getLocuriLoja());
            statement.setInt(7, sala.getRanduriLoja());
            statement.setInt(8, sala.getLocuriBalcon());
            statement.setInt(9, sala.getRanduriBalcon());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("O noua sala inserata cu succes!");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul inserarii noii sali " + e.getMessage());
        }
    }

    public Sala selectSala(String numeSala) {
        Sala sala = new Sala();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            statement.setString(1, numeSala);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.println("Sala a fost gasita!");
                sala.setNume(result.getString("nume"));
                sala.setLocuriCategoria1(result.getInt("locuriCategoria1"));
                sala.setRanduriCategoria1(result.getInt("randuriCategoria1"));
                sala.setLocuriCategoria2(result.getInt("locuriCategoria2"));
                sala.setRanduriCategoria2(result.getInt("randuriCategoria2"));
                sala.setLocuriLoja(result.getInt("locuriLoja"));
                sala.setRanduriLoja(result.getInt("randuriLoja"));
                sala.setLocuriBalcon(result.getInt("locuriBalcon"));
                sala.setRanduriBalcon(result.getInt("randuriBalcon"));
            }


        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul selectarii salii " + e.getMessage());
        }
        return sala;
    }

    public void add(Map<String, List<LocCategoria1>> asezare, String categorie, Integer nrLocuri, Integer nrRanduri) {
        List<LocCategoria1> locuri = new ArrayList<>();
        for (int i = 0; i < nrRanduri; i++)
            for (int j = 0; j < nrLocuri / nrRanduri; j++) {
                LocCategoria1 loc;
                switch (categorie) {
                    case "LocCategoria2": {
                        loc = new LocCategoria2(j + 1, "R" + (i + 1));
                    }
                    break;
                    case "LocLoja": {
                        loc = new LocLoja(j + 1, "R" + (i + 1));
                    }
                    break;
                    case "LocBalcon": {
                        loc = new LocBalcon(j + 1, "R" + (i + 1));
                    }
                    break;
                    default:
                        loc = new LocCategoria1(j + 1, "R" + (i + 1));
                }
                locuri.add(loc);
            }
        asezare.put(categorie, locuri);
    }

    public Map<String, List<LocCategoria1>> formareSala(Sala sala) {
        Map<String, List<LocCategoria1>> asezare = new HashMap<>();
        Integer locuriCategoria1 = sala.getLocuriCategoria1();
        Integer randuriCategoria1 = sala.getRanduriCategoria1();
        Integer locuriCategoria2 = sala.getLocuriCategoria2();
        Integer randuriCategoria2 = sala.getRanduriCategoria2();
        Integer locuriLoja = sala.getLocuriLoja();
        Integer randuriLoja = sala.getRanduriLoja();
        Integer locuriBalcon = sala.getLocuriBalcon();
        Integer randuriBalcon = sala.getRanduriBalcon();
        add(asezare, "LocCategoria1", locuriCategoria1, randuriCategoria1);
        add(asezare, "LocCategoria2", locuriCategoria2, randuriCategoria2);
        add(asezare, "LocLoja", locuriLoja, randuriLoja);
        add(asezare, "LocBalcon", locuriBalcon, randuriBalcon);
        return asezare;
    }

    public List<String> sali() {
        List<String> sali = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_SALI)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                sali.add(result.getString("nume"));
            }

        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul selectarii salilor " + e.getMessage());
        }
        return sali;
    }

    public void deleteSala(String numeSala) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_SALA)) {
            statement.setString(1, numeSala);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("S-a anulat spectacolul!");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul stergerii: " + e.getMessage());
        }
    }

    public void updateSala(String numeNou, String numeVechi) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_SALA)) {
            statement.setString(1, numeNou);
            statement.setString(2, numeVechi);
            int rowsUpdate = statement.executeUpdate();
            if (rowsUpdate > 0) {
                System.out.println("S-a modificat numele persoanei");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul inregistrarii " + e.getMessage());
        }
    }


    @Override
    public void setarePret(double pret, Map<String, List<LocCategoria1>> asezare) {
        for (String categorie : asezare.keySet()) {
            List<LocCategoria1> locuri = asezare.get(categorie);
            for (LocCategoria1 loc : locuri)
                loc.setPret(pret);
        }
    }
}
