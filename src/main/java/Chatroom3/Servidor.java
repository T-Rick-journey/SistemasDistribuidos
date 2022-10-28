package Chatroom3;

import java.io.*;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class Servidor extends Thread{
    public static ArrayList<BufferedWriter>clientes;
    public static ServerSocket servidor;
    public String nome;
    public Socket conexao;
    public InputStream inputStream;
    public InputStreamReader inputStramReader;
    public BufferedReader bufferReader;
    public static InetAddress IP;
    public static int porta = 12345;
    
    public Servidor(Socket conexao) throws UnknownHostException{
        
        this.conexao = conexao;
        try {
            inputStream  = conexao.getInputStream();
            inputStramReader = new InputStreamReader(inputStream);
            bufferReader = new BufferedReader(inputStramReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        try{
        String mensagem;
        OutputStream outputStream =  this.conexao.getOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        clientes.add(bufferWriter);
        nome = mensagem = bufferReader.readLine();

        while(!"#QUIT".equals(mensagem) && mensagem != null){
                mensagem = bufferReader.readLine();
                if ("#USERS".equals(mensagem)){
                    EnviaListaUsuarios(bufferWriter);
                } else {
                sendToAll(bufferWriter, mensagem);
                System.out.println(mensagem);
                }
        }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void sendToAll(BufferedWriter buffWrite, String mensagem) throws  IOException{
        BufferedWriter bufferedWriter;

        for(BufferedWriter bw : clientes){
            bufferedWriter = (BufferedWriter)bw;
            if(!(buffWrite == bufferedWriter)){
                bw.write(nome + " disse: " + mensagem+"\r\n");
                bw.flush();
            }
        }
    }
    public void EnviaListaUsuarios(BufferedWriter buffWrite)throws IOException{
        BufferedWriter bufferedWriter;
        for(BufferedWriter bw : clientes){
            bufferedWriter = (BufferedWriter)bw;
            if(buffWrite == bufferedWriter){
                bw.write(clientes.toArray()+"\r\n");
                bw.flush();
                
            }
        }
        
    }
    public static void main(String []args) {
        try{
            IP = InetAddress.getLocalHost();
            
            servidor = new ServerSocket(porta);
            clientes = new ArrayList<BufferedWriter>();
            JOptionPane.showMessageDialog(null,"Servidor ativo na porta: "+
            Integer.toString(porta));

            while(true){
                System.out.println("Aguardando conexão...");
                Socket con = servidor.accept();
                System.out.println("Cliente conectado...");
                Thread t = new Servidor(con);
                t.start();} 
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
} 