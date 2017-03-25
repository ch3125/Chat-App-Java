/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaChat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Home
 */
public class Client {
      private DatagramSocket socket;
      private InetAddress ip;
      private Thread send;
      private String name,address;
      private int port;
      private int ID=-1;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
      
      
       public Client(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
       
      public boolean openConnection(String address){
      
          try {
              ip=InetAddress.getByName(address);
                  socket=new DatagramSocket();
                  System.out.println("Client.java--- openConnection");
             
          } catch (UnknownHostException ex) {
              Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
              return false;
          }catch (SocketException ex) {
                  Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                  return false;
              }
         return true; 
    }

   
       public String receive(){
          
              byte[] data=new byte[1024];
              DatagramPacket packet=new DatagramPacket(data,data.length);
              System.out.println("Client.java--- receive");
              try {
              socket.receive(packet);
          } catch (IOException ex) {
              Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
              ex.printStackTrace();
          }
          String message=new String(packet.getData());
          
          return message;
    }
       
       public void close(){
           new Thread(){
               @Override
               public void run(){
           
           synchronized(socket){
           socket.close();
       }
       }
           }.start();
                   }
    /*
    sending data to serever
    */
    public void send(final byte[] data){
        send=new Thread("Send"){
            @Override
            public void run(){
                try {
                    System.out.println("Client.java--- send");
                    DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
                    socket.send(packet);
                } catch (IOException ex) {
                    Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        send.start();
        
    }

    
}
