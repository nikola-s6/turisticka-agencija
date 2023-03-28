/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db.impl;

import domain.Putnik;
import domain.Termin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbConnectionRepository;

/**
 *
 * @author nikol
 */
public class RepositoryDBTermin implements DbConnectionRepository<Termin> {

    @Override
    public List<Termin> getAll(Termin termin) throws Exception {
        if (termin.getPutovanje().getPutovanjeID() == 0) {
            throw new Exception("Nemoguce je ucitati termine jer putovanje ne postoji");
        }
        List<Termin> termini = new ArrayList<>();
        try {
            String query = "SELECT terminid, cena, datum_polaska, datum_povratka\n"
                    + "FROM termin\n"
                    + "WHERE putovanjeid = " + termin.getPutovanje().getPutovanjeID() + ";";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Termin t = new Termin();

                t.setTerminID(rs.getInt("terminid"));
                t.setCena(rs.getDouble("cena"));
                t.setDatumPolaska(rs.getDate("datum_polaska"));
                t.setDatumPovratka(rs.getDate("datum_povratka"));

                t.setPutovanje(termin.getPutovanje());

                termini.add(t);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            throw new Exception("Sistem ne moze da ucita termine");
        }
        return termini;
    }

    @Override
    public List<Termin> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(Termin termin) throws Exception {
        try {
            String query = "INSERT INTO termin(putovanjeid, cena, datum_polaska, datum_povratka)\n"
                    + "VALUES(?, ?, ?, ?);";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, termin.getPutovanje().getPutovanjeID());
            ps.setDouble(2, termin.getCena());
            ps.setDate(3, new java.sql.Date(termin.getDatumPolaska().getTime()));
            ps.setDate(4, new java.sql.Date(termin.getDatumPovratka().getTime()));

            ps.executeUpdate();
            ResultSet rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                termin.setTerminID(1);
            } else {
                throw new Exception("id termina neuspesno generisan");
            }
            ps.close();
        } catch (Exception e) {
//            e.printStackTrace();
            throw new Exception("Sistem ne moze da zapamti termin");
        }
    }

    @Override
    public void edit(Termin param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Termin termin) throws Exception {
        try {
//                brisanje termina iz liste
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            String query = "DELETE FROM termin WHERE putovanjeid = " + termin.getPutovanje().getPutovanjeID() + ";";
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new Exception("Sistem ne moze da obrise putovanje");
        }
    }

    @Override
    public Termin getOne(Termin param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
