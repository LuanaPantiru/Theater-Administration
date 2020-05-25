package com.sala_spectacole.persistence;

import com.sala_spectacole.connection.DatabaseConnection;
import com.sala_spectacole.domain.Spectacol;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class SpectacolRepository {
    private static SpectacolRepository instance;
    private static final String INSERT_STATEMENT = "INSERT INTO spectacole (nume, numeSala, data, pretNominal) VALUES (?,?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM spectacole WHERE nume =?";
    private static final String SALA_DATE = "SELECT * FROM spectacole WHERE numeSala = ? AND data = ?";
    private static final String UPDATE_SALA = "UPDATE spectacole SET numeSala =? WHERE numeSala = ?";
    private static final String SALA_SPECTACOLE = "SELECT nume FROM spectacole WHERE numeSala = ?";
    private static final String DELETE_SPECTACOL = "DELETE FROM spectacole WHERE nume = ?";

    private SpectacolRepository() {
    }

    public static SpectacolRepository getInstance() {
        if (instance == null) {
            instance = new SpectacolRepository();
        }
        return instance;
    }

    public void saveSpectacol(Spectacol spectacol) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            statement.setString(1, spectacol.getNumeSpectacol());
            statement.setString(2, spectacol.getSala());
            String date = spectacol.getZi() + "-" + spectacol.getLuna() + "-" + spectacol.getAn();
            statement.setString(3, date);
            statement.setDouble(4, spectacol.getPretNominal());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Un nou spectacol inserata cu succes!");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul inserarii noului spectacol " + e.getMessage());
        }
    }

    public Spectacol findSpectacol(String numeSpectacol) {
        Spectacol spectacol = new Spectacol();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            statement.setString(1, numeSpectacol);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.println("Spectacolul a fost gasit");
                spectacol.setNumeSpectacol(result.getString("nume"));
                spectacol.setNumeSala(result.getString("numeSala"));
                String data = result.getString("data");
                String[] ziLunaAn = data.split("-");
                spectacol.setZi(Integer.valueOf(ziLunaAn[0]));
                spectacol.setLuna(Integer.valueOf(ziLunaAn[1]));
                spectacol.setAn(Integer.valueOf(ziLunaAn[2]));
                spectacol.setPretNominal(result.getDouble("pretNominal"));
            }

        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul cautarii spectacolului " + e.getMessage());
        }
        return spectacol;
    }

    public List<String> salaSpectacole(String numeSala) {
        List<String> spectacole = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(SALA_SPECTACOLE)) {
            statement.setString(1, numeSala);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                spectacole.add(result.getString("nume"));
            }

        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul cautarii spectacolului " + e.getMessage());
        }
        return spectacole;
    }

    public boolean verifSalaData(String numeSala, Integer zi, Integer luna, Integer an) {
        String date = zi + "-" + luna + "-" + an;
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(SALA_DATE)) {
            statement.setString(1, numeSala);
            statement.setString(2, date);
            ResultSet result = statement.executeQuery();
            if (result.next()) return true;
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul verificarii salii si a datei: " + e.getMessage());
        }
        return false;
    }

    public void deleteSpectacol(String numeSpectacol) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_SPECTACOL)) {
            statement.setString(1, numeSpectacol);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("S-a anulat spectacolul!");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul stergerii: " + e.getMessage());
        }
    }

    public void updateSala(String numeSalaNou, String numeSalaVechi) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_SALA)) {
            statement.setString(1, numeSalaNou);
            statement.setString(2, numeSalaVechi);
            int rowsUpdate = statement.executeUpdate();
            if (rowsUpdate > 0) {
                System.out.println("S-a modificat numele persoanei");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul inregistrarii " + e.getMessage());
        }
    }

}
