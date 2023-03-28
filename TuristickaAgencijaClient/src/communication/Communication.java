/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import domain.Grad;
import domain.Putnik;
import domain.Putovanje;
import domain.Rezervacija;
import domain.Termin;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikol
 */
public class Communication {

    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private static Communication instance;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Communication() throws IOException {
        this.socket = new Socket("localhost", 9999);
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket);
    }

    public static Communication getInstance() throws IOException {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public Putnik logovanje(String email, String sifra) throws Exception {
        Putnik putnik = new Putnik();
        putnik.setEmail(email);
        putnik.setSifra(sifra);

        Request request = new Request(Operation.LOGOVANJE, putnik);

        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (Putnik) response.getResult();
    }

    public List<Putnik> ucitajListuPutnika() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.UCITAJ_LISTU_PUTNIKA);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Putnik>) response.getResult();
    }

    public List<Putnik> pronadjiPutnike(Putnik putnik) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.PRONADJI_PUTNIKE);
        request.setArgument(putnik);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Putnik>) response.getResult();
    }

    public void obrisiPutnika(Putnik putnik) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OBRISI_PUTNIKA);
        request.setArgument(putnik);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public Putnik ucitajPutnika(Putnik putnik) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.UCITAJ_PUTNIKA);
        request.setArgument(putnik);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return putnik;
    }

    public void zapamtiPutnika(Putnik putnik) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.ZAPAMTI_PUTNIKA);
        request.setArgument(putnik);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public List<Grad> ucitajListuGradova() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.UCITAJ_LISTU_GRADOVA);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Grad>) response.getResult();
    }

    public void zapamtiPutovanje(Putovanje putovanje) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.ZAPAMTI_PUTOVANJE);
        request.setArgument(putovanje);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public List<Putovanje> ucitajListuPutovanja() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.UCITAJ_LISTU_PUTOVANJA);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Putovanje>) response.getResult();
    }

    public List<Putovanje> pronadjiPutovanja(Grad grad) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.PRONADJI_PUTNOVANJA);
        request.setArgument(grad);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Putovanje>) response.getResult();
    }

    public Putovanje ucitajPutovanje(Putovanje putovanje) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.UCITAJ_PUTOVANJE);
        request.setArgument(putovanje);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (Putovanje) response.getResult();
    }

    public List<Rezervacija> ucitajRezervacije(Termin termin) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.UCITAJ_REZERVACIJE);
        request.setArgument(termin);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Rezervacija>) response.getResult();
    }

    public Rezervacija zapamtiRezervaciju(Rezervacija rezervacija) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.ZAPAMTI_REZERVACIJU);
        request.setArgument(rezervacija);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (Rezervacija) response.getResult();
    }

    public List<Putovanje> pronadjiRezervacijePutnika(Putnik putnik) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.UCITAJ_PUTOVANJA_PUTNIKA);
        request.setArgument(putnik);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Putovanje>) response.getResult();
    }

    public List<Rezervacija> pronadjiRezervacije(Putnik putnik, Putovanje putovanje) throws Exception {
        List<Object> params = new ArrayList<>();
        params.add(putnik);
        params.add(putovanje);

        Request request = new Request();
        request.setOperation(Operation.PRONADJI_REZERVACIJE);
        request.setArgument(params);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Rezervacija>) response.getResult();
    }

    public void obradiRezervaciju(Rezervacija rezervacija) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OBRADI_REZERVACIJU);
        request.setArgument(rezervacija);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public void stornirajRezervaciju(Rezervacija rezervacija) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.STORNIRAJ_REZERVACIJU);
        request.setArgument(rezervacija);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public void logout() throws IOException {
        this.socket.close();
        this.instance = null;
    }
}
