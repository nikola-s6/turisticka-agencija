/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db.impl;

import domain.Putnik;
import domain.Rezervacija;
import domain.Termin;
import domain.util.Pol;
import domain.util.StatusRezervacije;
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
public class RepositoryDBRezervacija implements DbConnectionRepository<Rezervacija> {

    @Override
    public List<Rezervacija> getAll(Rezervacija rezervacija) throws Exception {
        List<Rezervacija> rezervacije = new ArrayList<>();
        try {
            String query = "SELECT r.broj_rezervacije AS brojrezervacije, r.status AS STATUS,\n"
                    + "t.putovanjeid AS putovanjeid, t.terminid AS terminid, t.cena AS cena, t.datum_polaska AS datumpolaska, t.datum_povratka AS datumpovratka,\n"
                    + "p.putnikid AS putnikid\n"
                    + "FROM rezervacija r\n"
                    + "INNER JOIN termin t\n"
                    + "ON (r.terminid = t.terminid)\n"
                    + "INNER JOIN putnik p\n"
                    + "ON (r.putnikid = p.putnikid)\n"
                    + "WHERE (p.putnikid = " + rezervacija.getPutnik().getPutnikID() + " AND t.putovanjeid = " + rezervacija.getPutovanje().getPutovanjeID() + ");";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Rezervacija r = new Rezervacija();
                r.setPutovanje(rezervacija.getPutovanje());
                r.setPutnik(rezervacija.getPutnik());

                r.setBrojRezervacije(rs.getInt("brojrezervacije"));
                r.setStatus(rs.getString("status").equals("aktivan") ? StatusRezervacije.AKTIVAN : StatusRezervacije.NEAKTIVAN);

                Termin termin = new Termin();
                termin.setTerminID(rs.getInt("terminid"));
                termin.setCena(rs.getDouble("cena"));
                termin.setDatumPolaska(rs.getDate("datumpolaska"));
                termin.setDatumPovratka(rs.getDate("datumpovratka"));

                r.setTermin(termin);

                rezervacije.add(r);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            throw new Exception("Sistem ne moze da pronadje rezervacije");
        }
        return rezervacije;
    }

    @Override
    public List<Rezervacija> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(Rezervacija rezervacija) throws Exception {
        try {
            String query = "INSERT INTO rezervacija(putnikid, putovanjeid, terminid) VALUES(?,?,?);";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

//            ne generisu se kljucevi jer se primarni kljucevi unose
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, rezervacija.getPutnik().getPutnikID());
            ps.setInt(2, rezervacija.getPutovanje().getPutovanjeID());
            ps.setInt(3, rezervacija.getTermin().getTerminID());
//            ps.setString(4, rezervacija.getStatus().toString().toLowerCase());
//            default vrednost je neaktivan, moze biti aktivan samo nakon obrade rezervacije

            ps.executeUpdate();
            ResultSet rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                rezervacija.setBrojRezervacije(rsKeys.getInt(1));
            } else {
                throw new Exception("id putnika neuspesno generisan");
            }

            ps.close();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            if (e.getMessage().contains("Duplicate entry")) {
                throw new Exception("Putnik je vec rezervisao ovaj termin");
            }
//            e.printStackTrace();
            throw new Exception("Sistem ne moze da zapamti rezervaciju");
        }
    }

    @Override
    public void edit(Rezervacija rezervacija) throws Exception {
        try {
            String query = "UPDATE rezervacija\n"
                    + "SET `status` = '" + rezervacija.getStatus().toString().toLowerCase() + "'\n"
                    + "WHERE broj_rezervacije = " + rezervacija.getBrojRezervacije() + ";";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e) {
            if (rezervacija.getStatus().equals(StatusRezervacije.NEAKTIVAN)) {
                throw new Exception("Sistem ne moze da stornira rezervaciju");
            } else {
                throw new Exception("Sistem ne moze da obradi rezervaciju");
            }
        }
    }

    @Override
    public void delete(Rezervacija param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Rezervacija getOne(Rezervacija param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Rezervacija> getAll(Termin termin) throws Exception {
        if (termin.getTerminID() == 0) {
            throw new Error("nepotpuni podaci termina");
        }
        List<Rezervacija> rezervacije = new ArrayList<>();

        try {
            String query = "SELECT r.broj_rezervacije AS brojrezervacije, r.status AS STATUS,\n"
                    + "t.putovanjeid AS putovanjeid, t.terminid AS terminid, t.cena AS cena, t.datum_polaska AS datumpolaska, t.datum_povratka AS datumpovratka,\n"
                    + "p.putnikid AS putnikid, p.ime AS putnikime, p.prezime AS putnikprezime\n"
                    + "FROM rezervacija r\n"
                    + "INNER JOIN termin t\n"
                    + "ON (r.terminid = t.terminid)\n"
                    + "INNER JOIN putnik p\n"
                    + "ON (r.putnikid = p.putnikid)\n"
                    + "WHERE (r.terminid = " + termin.getTerminID() + ");";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Rezervacija r = new Rezervacija();

                Putnik putnik = new Putnik();
                putnik.setPutnikID(rs.getInt("putnikid"));
                putnik.setIme(rs.getString("putnikime"));
                putnik.setPrezime(rs.getString("putnikprezime"));
                r.setPutnik(putnik);

                r.setBrojRezervacije(rs.getInt("brojrezervacije"));
                r.setStatus(rs.getString("status").equals("aktivan") ? StatusRezervacije.AKTIVAN : StatusRezervacije.NEAKTIVAN);

                Termin t = new Termin();
                t.setTerminID(rs.getInt("terminid"));
                t.setCena(rs.getDouble("cena"));
                t.setDatumPolaska(rs.getDate("datumpolaska"));
                t.setDatumPovratka(rs.getDate("datumpovratka"));

                r.setTermin(termin);

                rezervacije.add(r);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            throw e;
        }
        return rezervacije;
    }

}
