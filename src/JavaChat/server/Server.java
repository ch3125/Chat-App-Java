/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Home
 */
public class Server {
    private int port;
    private DatagramSocket socket;
    private boolean running=false;
    private Thread run,manage,send,receive;
    public Server(int port) {
        this.port = port;
        try {
            
            socket=new DatagramSocket(port);
        } catch (SocketException ex) {
            ex.printStackTrace();
            return;
        }
        run=new Thread("Server"){
    
    public void run(){
        running=true;
        System.out.println("server started on port "+port);
        manageClients();
        receive();
        
    }
        };
        run.start();
    }
    //it manages the clients
    private void manageClients(){
        manage=new Thread("Manage"){
            public void run(){
                while(running){
                    //managing
                }
            }
        };
        manage.start();
    }
    
    private void receive(){
        receive=new Thread("Receive"){
            public void run(){
                while(running){
                    //receive
                        byte[] data=new byte[1024];
                        DatagramPacket packet=new DatagramPacket(data,data.length);
                        try {
                        socket.receive(packet);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        String string=new String(packet.getData());
                        System.out.println(string);
                }
            }
        };
        receive.start();
    }
    
    
}
