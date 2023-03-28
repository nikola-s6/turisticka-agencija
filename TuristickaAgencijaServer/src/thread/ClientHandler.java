/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thread;

import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import controller.Controller;
import domain.Grad;
import domain.Putnik;
import domain.Putovanje;
import domain.Rezervacija;
import domain.Termin;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.controller.FrmServerController;

/**
 *
 * @author nikol
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    private Putnik ulogovaniKlijent;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    @Override
    public void run() {
        System.out.println("klijent je konektovan");
        while (!socket.isClosed()) {
            try {
                Request request = (Request) receiver.receive();
                Response response = new Response();
                try {
                    switch (request.getOperation()) {
                        case LOGOVANJE:
                            Putnik p = (Putnik) request.getArgument();
                            response.setResult(Controller.getInstance().logovanje(p));
                            FrmServerController.getInstance().dodajKorisnika(socket, p);
                            break;
                        case ZAPAMTI_PUTNIKA:
                            Putnik putnik = (Putnik) request.getArgument();
                            Controller.getInstance().zapamtiPutnika(putnik);
                            break;
                        case PRONADJI_PUTNIKE:
                            Putnik putnikZaPretragu = (Putnik) request.getArgument();
                            response.setResult(Controller.getInstance().pronadjiPutnike(putnikZaPretragu));
                            break;
                        case UCITAJ_PUTNIKA:
                            Putnik putnikPojedinacnaPretraga = (Putnik) request.getArgument();
                            response.setResult(Controller.getInstance().ucitajPutnika(putnikPojedinacnaPretraga));
                            break;
                        case OBRISI_PUTNIKA:
                            Putnik putnikBrisanje = (Putnik) request.getArgument();
                            Controller.getInstance().obrisiPutnika(putnikBrisanje);
                            break;
                        case PRONADJI_PUTNOVANJA:
                            Grad grad = (Grad) request.getArgument();
                            response.setResult(Controller.getInstance().pronadjiPutovanja(grad));
                            break;
                        case UCITAJ_PUTOVANJE:
                            Putovanje putovanje = (Putovanje) request.getArgument();
                            response.setResult(Controller.getInstance().ucitajPutovanje(putovanje));
                            break;
                        case ZAPAMTI_PUTOVANJE:
                            Putovanje putovanjeDodavanje = (Putovanje) request.getArgument();
                            Controller.getInstance().zapamtiPutovanje(putovanjeDodavanje);
                            break;
                        case ZAPAMTI_REZERVACIJU:
                            Rezervacija r = (Rezervacija) request.getArgument();
                            response.setResult(Controller.getInstance().zapamtiRezervaciju(r));
                            break;
                        case PRONADJI_REZERVACIJE:
                            List<Object> parametri = (List<Object>) request.getArgument();
                            Putnik put = (Putnik) parametri.get(0);
                            Putovanje putova = (Putovanje) parametri.get(1);
                            response.setResult(Controller.getInstance().pronadjiRezervacije(put, putova));
                            break;
                        case UCITAJ_REZERVACIJE:
                            Termin terminRezervacije = (Termin) request.getArgument();
                            response.setResult(Controller.getInstance().ucitajRezervacije(terminRezervacije));
                            break;
                        case OBRADI_REZERVACIJU:
                            Rezervacija rez = (Rezervacija) request.getArgument();
                            Controller.getInstance().obradiRezervaciju(rez);
                            break;
                        case STORNIRAJ_REZERVACIJU:
                            Rezervacija rezervacija = (Rezervacija) request.getArgument();
                            Controller.getInstance().stornirajRezervaciju(rezervacija);
                            break;
                        case UCITAJ_PUTOVANJA_PUTNIKA:
                            Putnik putnik2 = (Putnik) request.getArgument();
                            response.setResult(Controller.getInstance().ucitajPutovanjaPutnika(putnik2));
                            break;
                        case UCITAJ_LISTU_GRADOVA:
                            response.setResult(Controller.getInstance().ucitajListuGradova());
                            break;
                        case UCITAJ_LISTU_PUTNIKA:
                            response.setResult(Controller.getInstance().ucitajListuPutnika());
                            break;
                        case UCITAJ_LISTU_PUTOVANJA:
                            response.setResult(Controller.getInstance().ucitajListuPutovanja());
                            break;
                        case UCITAJ_LISTU_TERMINA:
                            Putovanje putovanjeTermini = (Putovanje) request.getArgument();
                            response.setResult(Controller.getInstance().ucitajListuTermina(putovanjeTermini));
                            break;
                        default:
                            response.setException(new Exception("Pogresno poslat zahtev"));
                    }
                } catch (Exception e) {
//                    System.out.println(e.getMessage());
                    response.setException(e);
                }
                sender.send(response);
            } catch (Exception ex) {
                System.out.println("diskonektovan");
                FrmServerController.getInstance().izbaciKorisnika(socket);
            }
        }
    }

}
