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
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
       setTitle("Cherno Chat Client");
		client = new Client(name, address, port);
		boolean connect = client.openConnection(address);
		if (!connect) {
			System.err.println("Connection failed!");
			console("Connection failed!"); 
		} 
		createWindow(); 
		console("Attempting a connection to " + address + ":" + port + ", user: " + name);
		String connection = "/c/" + name + "/e/";
		client.send(connection.getBytes());
		
		running = true;
		run = new Thread(this, "Running");
		run.start();
       
    }
    /* recieving data from server
    
    */
    private void createWindow() { 
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 550);
		setLocationRelativeTo(null);
 
		
 
		//mntmExit = new JMenuItem("Exit");
		//mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
 
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 28, 815, 30, 7 }; // SUM = 880
		gbl_contentPane.rowHeights = new int[] { 25, 485, 40 }; // SUM = 550
		contentPane.setLayout(gbl_contentPane);
 
		txtHistory = new JTextArea();
		txtHistory.setEditable(false);
		JScrollPane scroll = new JScrollPane(txtHistory);
		caret = (DefaultCaret) txtHistory.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.insets = new Insets(0, 0, 5, 5);
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridx = 0;
		scrollConstraints.gridy = 0;
		scrollConstraints.gridwidth = 3;
		scrollConstraints.gridheight = 2;
		scrollConstraints.weightx = 1;
		scrollConstraints.weighty = 1;
		scrollConstraints.insets = new Insets(0, 5, 0, 0);
		contentPane.add(scroll, scrollConstraints);
 
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send(txtMessage.getText(), true);
				} 
			} 
		}); 
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		gbc_txtMessage.weightx = 1;
		gbc_txtMessage.weighty = 0;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);
                txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMessageKeyPressed(evt);
            }
        });
 
		btnSend = new JButton("Send");
		  btnSend.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		gbc_btnSend.weightx = 0;
		gbc_btnSend.weighty = 0;
		contentPane.add(btnSend, gbc_btnSend);
 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				String disconnect = "/d/" + client.getID() + "/e/";
				send(disconnect, false);
				running = false;
				client.close();
			} 
		}); 
 
		setVisible(true);
 
		txtMessage.requestFocusInWindow();
	} 
 
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

        getContentPane().add(jScrollPane1, new java.awt.GridBagConstraints());
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
        System.out.println("ClientWindow.java--- btnSendActionPerformed");
        String message=txtMessage.getText();
       send(message,true);
    }//GEN-LAST:event_btnSendActionPerformed

    private void txtMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyPressed
        // TODO add your handling code here:
        System.out.println("ClientWindow.java--- txtMessageKeyPressed");
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            send(txtMessage.getText(),true);
        }
    }//GEN-LAST:event_txtMessageKeyPressed

  
    
    private void send(String message, boolean text){
    if(message.equals(""))return;
    if(text){
    message=client.getName()+": "+message;
     message="/m/"+message+"/e/";}
      txtMessage.setText("");
      System.out.println("ClientWindow.java--- send(message)");
     client.send(message.getBytes());
       
}
    
    
    public void console(String message){
         System.out.println("ClientWindow.java--- console");
        txtHistory.append(message+"\n\r");
      txtHistory.setCaretPosition(txtHistory.getDocument().getLength());
    }
    public void listen(){
        listen=new Thread("Listen"){
        @Override
        public void run(){
            while(running){
                 System.out.println("ClientWindow.java--- listen");
            String message=client.receive();
            if(message.startsWith("/c/")){
             client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
             console("Successfully connected to server! ID: "+client.getID());
          }else if(message.startsWith("/m/"))
          {
              String text= message.substring(3);
             text= text.split("/e/")[0];
              System.out.println("text: "+text);
              console(text);
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
private JPanel contentPane;
    @Override
    public void run() {
        listen();
    }
 
}
