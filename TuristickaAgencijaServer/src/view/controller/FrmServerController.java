/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.controller;

import domain.Putnik;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import server.Server;
import view.FrmServer;
import view.components.KonektovaniKorisniciTableModel;

/**
 *
 * @author nikol
 */
public class FrmServerController {

    public static Map<Socket, Putnik> getUlogovaniKorisnici() {
        return ulogovaniKorisnici;
    }

    public static void setUlogovaniKorisnici(Map<Socket, Putnik> aUlogovaniKorisnici) {
        ulogovaniKorisnici = aUlogovaniKorisnici;
    }

    private final FrmServer frmServer;
    private static Map<Socket, Putnik> ulogovaniKorisnici;
    private int brojKlijenata;
    private Server server;
    private static FrmServerController instance;

    public static FrmServerController getInstance() {
        if (instance == null) {
            instance = new FrmServerController();
        }
        return instance;
    }

    private FrmServerController() {
        this.frmServer = new FrmServer();
        this.brojKlijenata = 0;
        this.ulogovaniKorisnici = new HashMap<>();
        prikazivanjeForme();
        dodajFunkcije();
    }

    private void prikazivanjeForme() {
        frmServer.getBtnZaustaviServer().setEnabled(false);
        frmServer.getLblBrojKonektovanihKlijenata().setText(Integer.toString(brojKlijenata));
        frmServer.getLblStatusServera().setText("NEAKTIVAN");
        frmServer.getLblStatusServera().setForeground(Color.red);
        frmServer.getTableKonektovaniKorisnici().setModel(KonektovaniKorisniciTableModel.getInstance());
        frmServer.pack();
        frmServer.setLocationRelativeTo(null);
        frmServer.setVisible(true);
    }

    public void pokreniServer() {
        if (server == null) {
            try {
                server = new Server();
                server.start();
                frmServer.getBtnPokreniServer().setEnabled(false);
                frmServer.getBtnZaustaviServer().setEnabled(true);
                frmServer.getLblStatusServera().setText("AKTIVAN");
                frmServer.getLblStatusServera().setForeground(Color.green);
                this.ulogovaniKorisnici = new HashMap<>();

//                System.out.println("Server je pokrenut");
                JOptionPane.showMessageDialog(frmServer, "Server je uspesno pokrenut", "Potvrda", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frmServer, "Pokretanje servera neuspesno", "Greska", JOptionPane.ERROR_MESSAGE);
                zaustaviServer();
            }
        }
    }

    public void zaustaviServer() {
        if (server != null) {
            try {
                server.interrupt();
                server.getServerSocket().close();

                frmServer.getBtnPokreniServer().setEnabled(true);
                frmServer.getBtnZaustaviServer().setEnabled(false);
                frmServer.getLblStatusServera().setText("NEAKTIVAN");
                frmServer.getLblStatusServera().setForeground(Color.red);
//                this.brojKlijenata = 0;
                frmServer.getLblBrojKonektovanihKlijenata().setText(Integer.toString(this.brojKlijenata));
                for (Map.Entry<Socket, Putnik> entry : ulogovaniKorisnici.entrySet()) {
                    entry.getKey().close();
                }
                server = null;
                JOptionPane.showMessageDialog(FrmServerController.getInstance().getFrm(), "Server je zaustavljen", "Potvrda", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
//                Logger.getLogger(FrmServerController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(FrmServerController.getInstance().getFrm(), "Zaustavljanje servera je neuspesno", "Greska", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    public void dodajKorisnika(Socket socket, Putnik putnik) {
        this.brojKlijenata++;
        frmServer.getLblBrojKonektovanihKlijenata().setText(Integer.toString(brojKlijenata));
        ulogovaniKorisnici.put(socket, putnik);
        KonektovaniKorisniciTableModel.getInstance().dodajKorisnikaUTabelu(putnik);
    }

    public void izbaciKorisnika(Socket socket) {
        try {
            socket.close();
        } catch (IOException ex) {
//            Logger.getLogger(FrmServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.brojKlijenata--;
        frmServer.getLblBrojKonektovanihKlijenata().setText(Integer.toString(brojKlijenata));
        KonektovaniKorisniciTableModel.getInstance().izbrisiKorisnikaIzTabele(ulogovaniKorisnici.get(socket));
        ulogovaniKorisnici.remove(socket);
    }

    public int getBrojKlijenata() {
        return brojKlijenata;
    }

    public void setBrojKlijenata(int brojKlijenata) {
        this.brojKlijenata = brojKlijenata;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public FrmServer getFrm() {
        return this.frmServer;
    }

    private void dodajFunkcije() {
        frmServer.getBtnPokreniServer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pokreniServer();
            }
        });

        frmServer.getBtnZaustaviServer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zaustaviServer();
            }
        });
    }

}
