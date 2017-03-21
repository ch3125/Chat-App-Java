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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import JavaChat.server.ServerClient;
import JavaChat.server.UniqueIdentifier;
import java.security.SecureRandom;
import java.util.UUID;

/**
 *
 * @author Home
 */
public class Server  {
    private List<ServerClient>  clients = new ArrayList<ServerClient>();
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
        }
        run=new Thread("Server"){
    @Override
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
            @Override
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
            @Override
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
                        process(packet);
                      clients.add(new ServerClient("Yan",packet.getAddress(),packet.getPort(),50));
                      System.out.println(clients.get(0).address.toString()+":"+clients.get(0).port);

                     
                        
                        
                }
            }
        };
        receive.start();
    }
    private void process(DatagramPacket packet){
        String string=new String(packet.getData());
        if(string.startsWith("/c/")){
           // UUID id=UUID.randomUUID();
           
            //id.toString();
            int id=UniqueIdentifier.getIdentifier();
            System.out.println("Identifier : "+id);
            clients.add(new ServerClient(string.substring(3,string.length()),packet.getAddress(),packet.getPort(),id));
            System.out.println(string.substring(3,string.length()));
        }else{
            System.out.println(string);
        }
    }
    
    
}
