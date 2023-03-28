/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db.impl;

import domain.Grad;
import domain.Putnik;
import domain.Putovanje;
import domain.util.Ponuda;
import domain.util.Prevoz;
import domain.util.Smestaj;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbConnectionRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author nikol
 */
public class RepositoryDBPutovanje implements DbConnectionRepository<Putovanje> {

    @Override
    public List<Putovanje> getAll(Putovanje putovanje) throws Exception {
        List<Putovanje> putovanja = new ArrayList<>();
        try {
            String query = "SELECT p.putovanjeid AS putovanjeid, p.naziv AS naziv,\n"
                    + "kg.gradid AS krajnjiid, kg.naziv AS krajnjinaziv, kg.drzava AS krajnjidrzava,\n"
                    + "pg.gradid AS pocetniid, pg.naziv AS pocetninaziv, pg.drzava AS pocetnidrzava\n"
                    + "FROM putovanje p\n"
                    + "INNER JOIN grad pg\n"
                    + "ON (p.pocetni_grad = pg.gradid)\n"
                    + "INNER JOIN grad kg\n"
                    + "ON (p.krajnji_grad = kg.gradid)\n"
                    + "WHERE LOWER(kg.naziv) = '" + putovanje.getKrajnjiGrad().getNaziv().toLowerCase() + "';";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Putovanje p = new Putovanje();
                p.setPutovanjeID(rs.getInt("putovanjeid"));
                p.setNaziv(rs.getString("naziv"));
//                p.setKratakOpis(rs.getString("kratak_opis"));
//                switch (rs.getString("prevoz")) {
//                    case "autobus":
//                        p.setPrevoz(Prevoz.AUTOBUS);
//                        break;
//                    case "voz":
//                        p.setPrevoz(Prevoz.VOZ);
//                        break;
//                    case "brod":
//                        p.setPrevoz(Prevoz.BROD);
//                        break;
//                    case "avion":
//                        p.setPrevoz(Prevoz.AVION);
//                }
//                switch (rs.getString("smestaj")) {
//                    case "privatan":
//                        p.setSmestaj(Smestaj.PRIVATAN);
//                        break;
//                    case "hotel":
//                        p.setSmestaj(Smestaj.HOTEL);
//                        break;
//                    case "apartman":
//                        p.setSmestaj(Smestaj.APARTMAN);
//                        break;
//                }
//                switch (rs.getString("ponuda")) {
//                    case "pun_pansion":
//                        p.setPonuda(Ponuda.PUN_PANSION);
//                        break;
//                    case "polu_pansion":
//                        p.setPonuda(Ponuda.POLU_PANSION);
//                        break;
//                    case "bez_pansiona":
//                        p.setPonuda(Ponuda.BEZ_PANSIONA);
//                }
                Grad pocetni = new Grad();
                pocetni.setGradID(rs.getInt("pocetniid"));
                pocetni.setNaziv(rs.getString("pocetninaziv"));
                pocetni.setDrzava(rs.getString("pocetnidrzava"));

                Grad krajnji = new Grad();
                krajnji.setGradID(rs.getInt("krajnjiid"));
                krajnji.setNaziv(rs.getString("krajnjinaziv"));
                krajnji.setDrzava(rs.getString("krajnjidrzava"));

                p.setPocetniGrad(pocetni);
                p.setKrajnjiGrad(krajnji);

                putovanja.add(p);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            throw new Exception("Sistem ne moze da pronadje putovanja");
        }
        return putovanja;
    }

    @Override
    public List<Putovanje> getAll() throws Exception {
        List<Putovanje> putovanja = new ArrayList<>();
        try {
            String query = "SELECT p.putovanjeid AS putovanjeid, p.naziv AS naziv,\n"
                    + "kg.gradid AS krajnjiid, kg.naziv AS krajnjinaziv, kg.drzava AS krajnjidrzava,\n"
                    + "pg.gradid AS pocetniid, pg.naziv AS pocetninaziv, pg.drzava AS pocetnidrzava\n"
                    + "FROM putovanje p\n"
                    + "INNER JOIN grad pg\n"
                    + "ON (p.pocetni_grad = pg.gradid)\n"
                    + "INNER JOIN grad kg\n"
                    + "ON (p.krajnji_grad = kg.gradid);";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Putovanje p = new Putovanje();
                p.setPutovanjeID(rs.getInt("putovanjeid"));
                p.setNaziv(rs.getString("naziv"));

                Grad pocetni = new Grad();
                pocetni.setGradID(rs.getInt("pocetniid"));
                pocetni.setNaziv(rs.getString("pocetninaziv"));
                pocetni.setDrzava(rs.getString("pocetnidrzava"));

                Grad krajnji = new Grad();
                krajnji.setGradID(rs.getInt("krajnjiid"));
                krajnji.setNaziv(rs.getString("krajnjinaziv"));
                krajnji.setDrzava(rs.getString("krajnjidrzava"));

                p.setPocetniGrad(pocetni);
                p.setKrajnjiGrad(krajnji);

                putovanja.add(p);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Sistem ne moze da pronadje putovanja");
        }
        return putovanja;
    }

    @Override
    public void add(Putovanje putovanje) throws Exception {
        try {
            String query = "INSERT INTO putovanje(pocetni_grad, krajnji_grad, prevoz, smestaj, ponuda, kratak_opis, naziv)\n"
                    + "VALUES(?,?,?,?,?,?,?);";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, putovanje.getPocetniGrad().getGradID());
            ps.setInt(2, putovanje.getKrajnjiGrad().getGradID());
            ps.setString(3, putovanje.getPrevoz().toString().toLowerCase());
            ps.setString(4, putovanje.getSmestaj().toString().toLowerCase());
            ps.setString(5, putovanje.getPonuda().toString().toLowerCase());
            ps.setString(6, putovanje.getKratakOpis());
            ps.setString(7, putovanje.getNaziv());

            ps.executeUpdate();
            ResultSet rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                putovanje.setPutovanjeID(rsKeys.getInt(1));
            } else {
                throw new Exception("id putovanja neuspesno generisan");
            }
            ps.close();
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
            throw new Exception("Sistem ne moze da zapamti putovanje");
        }
    }

    @Override
    public void edit(Putovanje putovanje) throws Exception {
        try {
//            krajnji grad ne moze da se izmeni
            String query = "UPDATE putovanje SET pocetni_grad = " + putovanje.getPocetniGrad().getGradID()
                    + ", prevoz = '" + putovanje.getPrevoz().toString().toLowerCase()
                    + "', smestaj = '" + putovanje.getSmestaj().toString().toLowerCase()
                    + "', ponuda = '" + putovanje.getPonuda().toString().toLowerCase()
                    + "', kratak_opis = '" + putovanje.getKratakOpis()
                    + "', naziv = '" + putovanje.getNaziv() + "'"
                    + "WHERE putovanjeid = " + putovanje.getPutovanjeID() + ";";
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Sistem ne moze da zapamti putovanje");
        }
    }

    @Override
    public void delete(Putovanje putovanje) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Putovanje getOne(Putovanje putovanje) throws Exception {
        Putovanje p = new Putovanje();
        try {
            String query = "SELECT p.putovanjeid AS putovanjeid, p.naziv AS naziv, p.prevoz AS prevoz, p.smestaj AS smestaj, p.ponuda AS ponuda, p.kratak_opis AS kratakopis,\n"
                    + "pg.gradid AS pocetniid, pg.naziv AS pocetninaziv, pg.drzava AS pocetnidrzava,\n"
                    + "kg.gradid AS krajnjiid, kg.naziv AS krajnjinaziv, kg.drzava AS krajnjidrzava\n"
                    + "FROM putovanje p\n"
                    + "INNER JOIN grad pg\n"
                    + "ON (pg.gradid = p.pocetni_grad)\n"
                    + "INNER JOIN grad kg\n"
                    + "ON (kg.gradid = p.krajnji_grad)\n"
                    + "WHERE p.putovanjeid = " + putovanje.getPutovanjeID() + ";";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                p.setPutovanjeID(rs.getInt("putovanjeid"));
                p.setNaziv(rs.getString("naziv"));
                p.setKratakOpis(rs.getString("kratakopis"));
                switch (rs.getString("prevoz")) {
                    case "autobus":
                        p.setPrevoz(Prevoz.AUTOBUS);
                        break;
                    case "voz":
                        p.setPrevoz(Prevoz.VOZ);
                        break;
                    case "brod":
                        p.setPrevoz(Prevoz.BROD);
                        break;
                    case "avion":
                        p.setPrevoz(Prevoz.AVION);
                }
                switch (rs.getString("smestaj")) {
                    case "privatan":
                        p.setSmestaj(Smestaj.PRIVATAN);
                        break;
                    case "hotel":
                        p.setSmestaj(Smestaj.HOTEL);
                        break;
                    case "apartman":
                        p.setSmestaj(Smestaj.APARTMAN);
                        break;
                }
                switch (rs.getString("ponuda")) {
                    case "pun_pansion":
                        p.setPonuda(Ponuda.PUN_PANSION);
                        break;
                    case "polu_pansion":
                        p.setPonuda(Ponuda.POLU_PANSION);
                        break;
                    case "bez_pansiona":
                        p.setPonuda(Ponuda.BEZ_PANSIONA);
                }
                Grad pocetni = new Grad();
                pocetni.setGradID(rs.getInt("pocetniid"));
                pocetni.setNaziv(rs.getString("pocetninaziv"));
                pocetni.setDrzava(rs.getString("pocetnidrzava"));

                Grad krajnji = new Grad();
                krajnji.setGradID(rs.getInt("krajnjiid"));
                krajnji.setNaziv(rs.getString("krajnjinaziv"));
                krajnji.setDrzava(rs.getString("krajnjidrzava"));

                p.setPocetniGrad(pocetni);
                p.setKrajnjiGrad(krajnji);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            throw new Exception("Sistem ne moze da cita putovanje");
        }
        return p;
    }

    public List<Putovanje> getAll(Putnik putnik) throws Exception {
        if(putnik.getPutnikID() == 0){
            throw new Exception("Putnik nije u bazi");
        }
        List<Putovanje> putovanja = new ArrayList<>();
        try {
            String query = "SELECT p.naziv AS naziv, p.putovanjeid AS putovanjeid,\n"
                    + "pg.gradid AS pocetniid, pg.naziv AS pocetninaziv, pg.drzava AS pocetnidrzava,\n"
                    + "kg.gradid AS krajnjiid, kg.naziv AS krajnjinaziv, kg.drzava AS krajnjidrzava\n"
                    + "FROM putovanje p\n"
                    + "INNER JOIN grad pg\n"
                    + "ON(pg.gradid = p.pocetni_grad)\n"
                    + "INNER JOIN grad kg\n"
                    + "ON(kg.gradid = p.krajnji_grad)\n"
                    + "WHERE putovanjeid IN(SELECT putovanjeid\n"
                    + "FROM rezervacija\n"
                    + "WHERE putnikid = " + putnik.getPutnikID() + ");";
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Putovanje p = new Putovanje();
                p.setPutovanjeID(rs.getInt("putovanjeid"));
                p.setNaziv(rs.getString("naziv"));

                Grad pocetni = new Grad();
                pocetni.setGradID(rs.getInt("pocetniid"));
                pocetni.setNaziv(rs.getString("pocetninaziv"));
                pocetni.setDrzava(rs.getString("pocetnidrzava"));

                Grad krajnji = new Grad();
                krajnji.setGradID(rs.getInt("krajnjiid"));
                krajnji.setNaziv(rs.getString("krajnjinaziv"));
                krajnji.setDrzava(rs.getString("krajnjidrzava"));

                p.setPocetniGrad(pocetni);
                p.setKrajnjiGrad(krajnji);

                putovanja.add(p);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
//            e.printStackTrace();
//            Zapravo su to putovanja ali se ona koriste usvrhu nalazenja rezeravcija
            throw new Exception("Sistem ne moze da pronadje rezervacije po zadatoj vrednosti");
        }
        return putovanja;
    }
}
