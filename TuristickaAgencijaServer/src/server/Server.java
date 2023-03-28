/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import thread.ClientHandler;
import view.controller.FrmServerController;

/**
 *
 * @author nikol
 */
public class Server extends Thread {

    private ServerSocket serverSocket;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(9999);
    }

    @Override
    public void run() {
        try {

            while (!isInterrupted()) {
                System.out.println("Cekanje na konekciju");
                Socket socket = serverSocket.accept();
                handleClient(socket);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private void handleClient(Socket socket) {
        ClientHandler clientHandler = new ClientHandler(socket);
        clientHandler.start();
    }

}
