/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db.impl;

import domain.Grad;
import domain.Usputni;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbConnectionRepository;

/**
 *
 * @author nikol
 */
public class RepositoryDBUsputni implements DbConnectionRepository<Usputni> {

    @Override
    public List<Usputni> getAll(Usputni usputni) throws Exception {
        if (usputni.getPutovanje().getPutovanjeID() == 0) {
            throw new Exception("Nemnoguce je ucitati usputne gradove jer putovanje ne postoji");
        }
        List<Usputni> usputniGradovi = new ArrayList<>();
        try {
//            vec ima putovanje u sebi tako da ne moramo da ga ucitavamo iz baze
            String query = "SELECT u.redni_broj AS rednibroj,\n"
                    + "g.gradid AS gradid, g.naziv AS gradnaziv, g.drzava AS graddrzava\n"
                    + "FROM usputni u\n"
                    + "INNER JOIN grad g\n"
                    + "ON(u.gradid = g.gradid)\n"
                    + "WHERE u.putovanjeid = " + usputni.getPutovanje().getPutovanjeID() + " ORDER BY rednibroj ASC;";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Usputni u = new Usputni();
                Grad grad = new Grad();

                grad.setGradID(rs.getInt("gradid"));
                grad.setNaziv(rs.getString("gradnaziv"));
                grad.setDrzava(rs.getString("graddrzava"));

                u.setGrad(grad);
                u.setPutovanje(usputni.getPutovanje());
                u.setRedniBroj(rs.getInt("rednibroj"));

                usputniGradovi.add(u);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            throw new Exception("Sistem ne moze da ucita usputne gradove");
        }
        return usputniGradovi;
    }

    @Override
    public List<Usputni> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(Usputni usputni) throws Exception {
        try {
            String query = "INSERT INTO usputni(gradid, putovanjeid, redni_broj)\n"
                    + "VALUES (?,?,?);";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

//            ne generisu se kljucevi jer se primarni kljucevi unose
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, usputni.getGrad().getGradID());
            ps.setInt(2, usputni.getPutovanje().getPutovanjeID());
            ps.setInt(3, usputni.getRedniBroj());

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
//            e.printStackTrace();
            if(e.getMessage().startsWith("Duplicate entry")){
                throw new Exception("Putovanje ne moze imati dva ista usputna grada");
            }
            throw new Exception("Sistem ne moze da kreira usputnu destinaciju");
        }
    }

    @Override
    public void edit(Usputni param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Usputni usputni) throws Exception {
        try {
//                brisanje usputnih gradova iz liste
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            String query = "DELETE FROM usputni WHERE putovanjeid = " + usputni.getPutovanje().getPutovanjeID() + ";";
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new Exception("Sistem ne moze da obrise usputne gradove");
        }
    }

    @Override
    public Usputni getOne(Usputni param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
