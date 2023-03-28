/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author nikol
 */
public class Receiver{
        private Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }
    
    public Object receive() throws Exception{
        try {
            ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        } catch (Exception ex) {
//            ex.printStackTrace();
            throw new Exception("Error receiving object!\n"+ex.getMessage());
        }
    }
}
