package com.sala_spectacole.persistence;

import com.sala_spectacole.connection.DatabaseConnection;
import com.sala_spectacole.domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocRezervatRepository {
    private static LocRezervatRepository instance;
    private static final String INSERT_STATEMENT = "INSERT INTO rezervate (numeSpectacol,cnpPersoana, categorie, loc, rand, pret) VALUES (?,?,?,?,?,?)";
    private static final String FIND_LOC = "SELECT * FROM rezervate WHERE numeSpectacol = ? AND categorie = ? AND loc = ? AND rand = ?";
    private static final String DELETE_LOC = "DELETE FROM rezervate WHERE cnpPersoana = ? AND numeSpectacol = ?";
    private static final String DELETE_SPECTACOL = "DELETE FROM rezervate WHERE numeSpectacol = ?";
    private static final String LOCURI_SPECTACOL = "SELECT * FROM rezervate WHERE numeSpectacol = ?";
    private static final String LOC_SPECTACOL = "SELECT * FROM rezervate WHERE cnpPersoana = ? AND numeSpectacol = ?";

    private LocRezervatRepository() {
    }

    public static LocRezervatRepository getInstance() {
        if (instance == null) {
            instance = new LocRezervatRepository();
        }
        return instance;
    }

    public void saveLocRezervat(LocRezervat loc) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            statement.setString(1, loc.getNumeSpectacol());
            statement.setString(2, loc.getCnpPersoana());
            statement.setString(3, loc.getLoc().getClass().getSimpleName());
            statement.setInt(4, loc.getLoc().getNrLoc());
            statement.setString(5, loc.getLoc().getNrRand());
            statement.setDouble(6, loc.getPret());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("S-a rezervat locul!");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul rezervarii " + e.getMessage());
        }
    }

    public LocRezervat findLoc(LocRezervat loc) {
        LocRezervat locGasit = new LocRezervat();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(FIND_LOC)) {
            statement.setString(1, loc.getNumeSpectacol());
            statement.setString(2, loc.getClass().getSimpleName());
            statement.setInt(3, loc.getLoc().getNrLoc());
            statement.setString(4, loc.getLoc().getNrRand());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.println("A fost gasita persoana!");
                locGasit.setCnpPersoana(result.getString("cnp"));
                locGasit.setNumeSpectacol(result.getString("numeSpectacol"));
                locGasit.setPret(result.getDouble("pret"));
                String categorie = result.getString("categorie");
                LocCategoria1 locCategorie;
                switch (categorie) {
                    case "LocCategoria2": {
                        locCategorie = new LocCategoria2(result.getInt("loc"), result.getString("rand"));
                    }
                    break;
                    case "LocLoja": {
                        locCategorie = new LocLoja(result.getInt("loc"), result.getString("rand"));
                    }
                    break;
                    case "LocBalcon": {
                        locCategorie = new LocBalcon(result.getInt("loc"), result.getString("rand"));
                    }
                    break;
                    default:
                        locCategorie = new LocCategoria1(result.getInt("loc"), result.getString("rand"));
                }
                locGasit.setLoc(locCategorie);
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul inregistrarii " + e.getMessage());
        }
        return locGasit;
    }

    public List<LocRezervat> locuriRezervateSpectacol(String numeSpectacol) {
        List<LocRezervat> locuriRezervate = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(LOCURI_SPECTACOL)) {
            statement.setString(1, numeSpectacol);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String categorie = result.getString("categorie");
                LocCategoria1 loc;
                switch (categorie) {
                    case "LocCategoria2": {
                        loc = new LocCategoria2(result.getInt("loc"), result.getString("rand"));
                    }
                    break;
                    case "LocLoja": {
                        loc = new LocLoja(result.getInt("loc"), result.getString("rand"));
                    }
                    break;
                    case "LocBalcon": {
                        loc = new LocBalcon(result.getInt("loc"), result.getString("rand"));
                    }
                    break;
                    default:
                        loc = new LocCategoria1(result.getInt("loc"), result.getString("rand"));
                }
                LocRezervat locRezervat = new LocRezervat(loc, result.getString("cnpPersoana"), result.getString("numeSpectacol"), result.getDouble("pret"));
                locuriRezervate.add(locRezervat);
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul rezervarii " + e.getMessage());
        }
        return locuriRezervate;
    }

    public void deleteLocuri(String cnp, String numeSpectacol) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_LOC)) {
            statement.setString(1, cnp);
            statement.setString(2, numeSpectacol);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("S-au anulat cu succes biletele.");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul anularii biletelor " + e.getMessage());
        }
    }

    public void deleteSpectacol(String numeSpectacol) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_SPECTACOL)) {
            statement.setString(1, numeSpectacol);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("S-au anulat cu succes biletele.");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul anularii spectacolului " + e.getMessage());
        }
    }

    public List<LocRezervat> locSpectacol(String cnp, String numeSpectacol) {
        List<LocRezervat> locuri = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(LOC_SPECTACOL)) {
            statement.setString(1, cnp);
            statement.setString(2, numeSpectacol);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String categorie = result.getString("categorie");
                LocCategoria1 loc;
                switch (categorie) {
                    case "LocCategoria2": {
                        loc = new LocCategoria2(result.getInt("loc"), result.getString("rand"));
                    }
                    break;
                    case "LocLoja": {
                        loc = new LocLoja(result.getInt("loc"), result.getString("rand"));
                    }
                    break;
                    case "LocBalcon": {
                        loc = new LocBalcon(result.getInt("loc"), result.getString("rand"));
                    }
                    break;
                    default:
                        loc = new LocCategoria1(result.getInt("loc"), result.getString("rand"));
                }
                LocRezervat locRezervat = new LocRezervat(loc, result.getString("cnpPersoana"), result.getString("numeSpectacol"), result.getDouble("pret"));
                locuri.add(locRezervat);
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul rezervarii " + e.getMessage());
        }
        return locuri;
    }

}
