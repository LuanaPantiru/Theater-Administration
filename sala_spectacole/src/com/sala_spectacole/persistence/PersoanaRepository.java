package com.sala_spectacole.persistence;

import com.sala_spectacole.connection.DatabaseConnection;
import com.sala_spectacole.domain.Persoana;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersoanaRepository {
    private static PersoanaRepository instance;
    private static final String INSERT_STATEMENT = "INSERT INTO persoane (cnp, nume, prenume) VALUES (?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT cnp, nume, prenume FROM persoane WHERE cnp = ?";
    private static final String UPDATE_NUME = "UPDATE persoane SET nume =? WHERE cnp = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM persoane WHERE cnp = ?";

    private PersoanaRepository() {
    }

    public static PersoanaRepository getInstance() {
        if (instance == null) {
            instance = new PersoanaRepository();
        }
        return instance;
    }

    public void savePersona(Persoana pers) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            statement.setString(1, pers.getCnp());
            statement.setString(2, pers.getNume());
            statement.setString(3, pers.getPrenume());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("V-ati inregistrat cu succes!");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul inregistrarii " + e.getMessage());
        }
    }

    public Persoana selectPerson(String cnp) {
        Persoana pers = new Persoana();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            statement.setString(1, cnp);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                System.out.println("A fost gasita persoana!");
                pers.setCnp(result.getString("cnp"));
                pers.setNume(result.getString("nume"));
                pers.setPrenume(result.getString("prenume"));
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul inregistrarii " + e.getMessage());
        }
        return pers;
    }

    public void updateNume(String nume, String cnp) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_NUME)) {
            statement.setString(1, nume);
            statement.setString(2, cnp);
            int rowsUpdate = statement.executeUpdate();
            if (rowsUpdate > 0) {
                System.out.println("S-a modificat numele persoanei");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul inregistrarii " + e.getMessage());
        }
    }

    public void deletePersoana(String cnp) {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_STATEMENT)) {
            statement.setString(1, cnp);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("S-a anulat spectacolul!");
            }
        } catch (SQLException e) {
            System.out.println("S-a intamplat ceva in timpul stergerii: " + e.getMessage());
        }
    }
}
