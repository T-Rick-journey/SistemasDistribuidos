package Chatroom3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.net.InetAddress;

public class Cliente extends JFrame implements ActionListener, KeyListener {
    //Interface Grafica
    public static final long serialVersionUID = 1L;
    public JTextArea JtextMensagens;
    public JTextField JtextMensagem;
    public JButton btEnviar;
    public JButton btSair;
    public JLabel lblHistorico;
    public JLabel lblMsg;
    public JPanel pnlContent;
    public JTextField JtxtIP;
    public JTextField JtextPorta;
    public JTextField JtextNome;
    
    //BackEnd tcp
    public Socket soquete;
    public OutputStream outputStream ;
    public Writer writer;
    public BufferedWriter bufferWriter;
    public InetAddress IP;
    public int porta = 12345;
    

    public Cliente() throws IOException{
        IP = InetAddress.getLocalHost();
        JLabel lblMessage = new JLabel("Verificar!");
        JtxtIP = new JTextField(IP.toString());
        JtxtIP.setEditable(false);
        JtextPorta = new JTextField(Integer.toString(porta));
        JtextPorta.setEditable(false);
        JtextNome = new JTextField("Cliente");
        Object[] texts = {lblMessage, IP, porta, JtextNome };
        JOptionPane.showMessageDialog(null, texts);
        pnlContent = new JPanel();
        JtextMensagens = new JTextArea(10,20);
        JtextMensagens.setEditable(false);
        JtextMensagens.setBackground(new Color(240,240,240));
        JtextMensagem = new JTextField(20);
        lblHistorico = new JLabel("Histórico");
        lblMsg = new JLabel("Mensagem");
        btEnviar = new JButton("Enviar");
        btEnviar.setToolTipText("Enviar Mensagem");
        btSair = new JButton("Sair");
        btSair.setToolTipText("Sair do Chat");
        btEnviar.addActionListener(this);
        btSair.addActionListener(this);
        btEnviar.addKeyListener(this);
        JtextMensagem.addKeyListener(this);
        JScrollPane scroll = new JScrollPane(JtextMensagens);
        JtextMensagens.setLineWrap(true);
        pnlContent.add(lblHistorico);
        pnlContent.add(scroll);
        pnlContent.add(lblMsg);
        pnlContent.add(JtextMensagem);
        pnlContent.add(btSair);
        pnlContent.add(btEnviar);
        pnlContent.setBackground(Color.LIGHT_GRAY);
        JtextMensagens.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE));
        JtextMensagem.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        setTitle(JtextNome.getText());
        setContentPane(pnlContent);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(300,350);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void conectar() throws IOException{
        soquete = new Socket(IP,porta);
        outputStream = soquete.getOutputStream();
        writer = new OutputStreamWriter(outputStream);
        bufferWriter = new BufferedWriter(writer);
        bufferWriter.write(JtextNome.getText()+"\r\n");
        bufferWriter.flush();
}
    public void enviarMensagem(String mensagem) throws IOException{
        if(mensagem.equals("#QUIT")){
          bufferWriter.write("Desconectado \r\n");
          JtextMensagens.append("Desconectado \r\n");
        }else{
            bufferWriter.write(mensagem+"\r\n");
            JtextMensagens.append(JtextNome.getText()+" disse: " 
                    +JtextMensagem.getText()+"\r\n");
        }
        bufferWriter.flush();
        JtextMensagem.setText("");
}
    public void escutar() throws IOException{
        InputStream inputStream = soquete.getInputStream();
        InputStreamReader inputRead = new InputStreamReader(inputStream);
        BufferedReader bufferRead = new BufferedReader(inputRead);
        String mensagem = "";

        while(!"#QUIT".equals(mensagem)){
            if(bufferRead.ready()){
                mensagem = bufferRead.readLine();
                if(mensagem.equals("#QUIT")){
                    JtextMensagens.append("Saindo do chat!!!\r\n");}
                else {
                    if(!("#USERS".equals(mensagem))){
                        JtextMensagens.append(mensagem+"\r\n");
                    }
                        
                } 
            }
        }
    }
    public void sair() throws IOException{
        enviarMensagem(JtextNome.getText()+" partiu dessa para melhor!");
        bufferWriter.close();
        writer.close();
        outputStream.close();
        soquete.close();
}
    public void actionPerformed(ActionEvent e) {
        try {
            if(e.getActionCommand().equals(btEnviar.getActionCommand()))
                enviarMensagem(JtextMensagem.getText());
            else
                if(e.getActionCommand().equals(btSair.getActionCommand()))
                    sair();
        } catch (IOException e1) {
          e1.printStackTrace();
    }
}
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
           try {
              enviarMensagem(JtextMensagem.getText());
           } catch (IOException e1) {
           e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
    
    public static void main(String []args) throws IOException{
        Cliente app = new Cliente();
        app.conectar();
        app.escutar();
    }
}
