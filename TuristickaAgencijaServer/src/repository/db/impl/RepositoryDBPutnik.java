/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db.impl;

import domain.Putnik;
import domain.util.Pol;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbConnectionRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author nikol
 */
public class RepositoryDBPutnik implements DbConnectionRepository<Putnik> {

//    ucitava putnike bez njihovih rezervacija
    @Override
    public List<Putnik> getAll(Putnik putnik) throws Exception {
        List<Putnik> putnici = new ArrayList<>();
        try {
            String query = "SELECT putnikid, ime, prezime, pol, email, broj_telefona, sifra FROM putnik WHERE LOWER(ime)='" + putnik.getIme().toLowerCase() + "' AND LOWER(prezime)='" + putnik.getPrezime().toLowerCase() + "';";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Putnik p = new Putnik();
                p.setPutnikID(rs.getInt("putnikid"));
                p.setIme(rs.getString("ime"));
                p.setPrezime(rs.getString("prezime"));
                p.setPol((rs.getString("pol").equals("muski") ? Pol.MUSKI : Pol.ZENSKI));
                p.setEmail(rs.getString("email"));
                p.setBrojTelefona(rs.getString("broj_telefona"));
                p.setSifra(rs.getString("sifra"));

                putnici.add(p);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            throw new Exception("Sistem ne moze da pronadje putnike");
        }
        return putnici;
    }

//    ucitava putnike bez njihovih rezervacija
    @Override
    public List<Putnik> getAll() throws Exception {
        List<Putnik> putnici = new ArrayList<>();
        try {
            String query = "SELECT putnikid, ime, prezime, pol, email, broj_telefona, sifra FROM putnik;";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Putnik putnik = new Putnik();
                putnik.setPutnikID(rs.getInt("putnikid"));
                putnik.setIme(rs.getString("ime"));
                putnik.setPrezime(rs.getString("prezime"));
                putnik.setPol((rs.getString("pol").equals("muski") ? Pol.MUSKI : Pol.ZENSKI));
                putnik.setEmail(rs.getString("email"));
                putnik.setBrojTelefona(rs.getString("broj_telefona"));
                putnik.setSifra(rs.getString("sifra"));

                putnici.add(putnik);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            throw new Exception("Sistem ne moze da ucita putnike");
        }
        return putnici;
    }

    @Override
    public void add(Putnik putnik) throws Exception {
        try {
            String query = "INSERT INTO putnik(ime, prezime, pol, email, broj_telefona, sifra) VALUES(?, ?, ?, ?, ?, ?);";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, putnik.getIme());
            ps.setString(2, putnik.getPrezime());
            ps.setString(3, putnik.getPol().toString().toLowerCase());
            ps.setString(4, putnik.getEmail());
            ps.setString(5, putnik.getBrojTelefona());
            ps.setString(6, Integer.toString(putnik.getSifra().hashCode()));

            ps.executeUpdate();
            ResultSet rsKeys = ps.getGeneratedKeys();
            if(rsKeys.next()){
                putnik.setPutnikID(rsKeys.getInt(1));
            }else{
                throw new Exception("id putnika neuspesno generisan");
            }
            ps.close();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            throw new Exception("Sistem ne moze da zapamti putnika");
        }
    }

    @Override
    public void edit(Putnik putnik) throws Exception {
        try {
//            id i sifra se ne menjaju
            String query = "UPDATE putnik SET ime='" + putnik.getIme() + "', prezime='" + putnik.getPrezime() + "', pol='" + putnik.getPol().toString().toLowerCase() + "', email='" + putnik.getEmail() + "', broj_telefona='" + putnik.getBrojTelefona() + "' WHERE putnikid=" + putnik.getPutnikID() + ";";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
            throw new Exception("Sistem ne moze da zapamti putnika");
        }
    }

    @Override
    public void delete(Putnik putnik) throws Exception {
        try {
            String query = "DELETE FROM putnik WHERE putnikid=" + putnik.getPutnikID() + ";";
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            throw new Exception("Sistem ne moze da obrise putnika");
        }
    }

    @Override
    public Putnik getOne(Putnik putnik) throws Exception {
        Putnik p = new Putnik();
        try {
            String query = "SELECT putnikid, ime, prezime, pol, email, broj_telefona, sifra FROM putnik WHERE putnikid=" + putnik.getPutnikID() + ";";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                p.setPutnikID(rs.getInt("putnikid"));
                p.setIme(rs.getString("ime"));
                p.setPrezime(rs.getString("prezime"));
                p.setPol((rs.getString("pol").equals("muski") ? Pol.MUSKI : Pol.ZENSKI));
                p.setEmail(rs.getString("email"));
                p.setBrojTelefona(rs.getString("broj_telefona"));
                p.setSifra(rs.getString("sifra"));
            }

            // logika za ucitavanje rezervacija sa spajanjem sa putnikom, terminom i putovanjem
            rs.close();
            statement.close();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            throw new Exception("Sistem ne moze da ucita putnika");
        }
        return p;
    }

}
