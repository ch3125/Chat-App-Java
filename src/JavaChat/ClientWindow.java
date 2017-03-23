/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaChat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;
import javax.swing.JFrame;
import JavaChat.Client;

/**
 *
 * @author Home
 */
public class ClientWindow extends JFrame implements Runnable{

    /**
     * Creates new form Client
     */
     

    private DefaultCaret caret;
    private Client client;
    private Thread listen,run;
    private boolean running=false;
    public ClientWindow(String name,String address,int port) {
        client=new Client(name,address,port);
        
        boolean connect=client.openConnection(address);
        if(!connect){
            System.err.println("connection failed!");
            console("connection failed!");
        }
        
        setVisible(true);
        initComponents();
        myInitComponents();
        console("Attempting a connection to "+address+": "+port+" ,user: "+name);
        String connectiion="/c/"+name+" connected from "+address+":"+port;
        client.send(connectiion.getBytes());
         running=true;
        run=new Thread(this,"Running");
        run.start();
       
    }
    /* recieving data from server
    
    */
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        txtHistory = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        txtMessage = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        txtHistory.setEditable(false);
        txtHistory.setColumns(20);
        txtHistory.setRows(5);
        jScrollPane1.setViewportView(txtHistory);
        txtHistory.getAccessibleContext().setAccessibleName("");
        txtHistory.getAccessibleContext().setAccessibleParent(this);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        getContentPane().add(jScrollPane1, gridBagConstraints);
        jScrollPane1.getAccessibleContext().setAccessibleDescription("");

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        getContentPane().add(btnSend, new java.awt.GridBagConstraints());

        txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMessageKeyPressed(evt);
            }
        });
        getContentPane().add(txtMessage, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        String message=txtMessage.getText();
       send(message);
    }//GEN-LAST:event_btnSendActionPerformed

    private void txtMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            send(txtMessage.getText());
        }
    }//GEN-LAST:event_txtMessageKeyPressed

  
    
    private void send(String message){
    if(message.equals(""))return;
    message=client.getName()+" : "+message;
     console(message);
     message="/m/"+message;
     client.send(message.getBytes());
        txtMessage.setText("");
}
    private void myInitComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } 
        setLocationRelativeTo(null);
        setSize(880,550);
        setTitle("Chat Client");
        //for main grid layout
        GridBagLayout gbl=new GridBagLayout();
        gbl.columnWidths=new int[]{28,815,30,7};//sum-880
        gbl.rowHeights=new int[]{35,475,40};//sum-550
        gbl.columnWeights=new double[]{1,0,Double.MIN_VALUE};
        gbl.rowWeights=new double[]{1,0,Double.MIN_VALUE};
        getContentPane().setLayout(gbl);
        //for chat text area
         JScrollPane scroll=new JScrollPane(txtHistory);
        caret=(DefaultCaret)txtHistory.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        GridBagConstraints scrollConstraints=new GridBagConstraints();
        scrollConstraints.insets=new Insets(0,0,5,5);
        scrollConstraints.fill=GridBagConstraints.BOTH;
        scrollConstraints.gridx=0;
        scrollConstraints.gridy=0;
        scrollConstraints.gridwidth=3;
        scrollConstraints.gridheight=2;
        scrollConstraints.insets=new Insets(0,5,0,0);
        getContentPane().add(scroll,scrollConstraints);
        //for send button
        GridBagConstraints agbc=new GridBagConstraints();
        agbc.insets=new Insets(0,0,0,5);
        agbc.gridx=2;
        agbc.gridy=2;
        getContentPane().add(btnSend,agbc);
        //for text message
        GridBagConstraints bgbc=new GridBagConstraints();
        bgbc.insets=new Insets(0,0,0,5);
        bgbc.fill=GridBagConstraints.HORIZONTAL;
        bgbc.gridx=0;
        bgbc.gridy=2;
        bgbc.gridwidth=2;
        getContentPane().add(txtMessage,bgbc);
        txtMessage.requestFocusInWindow();
    }
    
    public void console(String message){
        txtHistory.append(message+"\n\r");
      txtHistory.setCaretPosition(txtHistory.getDocument().getLength());
    }
    public void listen(){
        listen=new Thread("Listen"){
        public void run(){
            while(running){
            String message=client.receive();
            if(message.startsWith("/c/")){
             client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
             console("Successfully connected to server! ID: "+client.getID());
          }
            }
        }
        };
        listen.start();
    }

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtHistory;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        listen();
    }
 
}
