
package RemoteCalculatorUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) throws IOException {
        // get destino
        int porta = 6789;
        InetAddress ip = InetAddress.getLocalHost();
        
        // Criar mensagem
        String mensagem = "30 / 5";
        byte[] datagrama = mensagem.getBytes();;
        DatagramPacket request = new DatagramPacket(datagrama,
                mensagem.length(), ip, porta);
        
        // Atribuir Soquete
        DatagramSocket socket = new DatagramSocket();
        
        //Enviar mensagem
        System.out.println("Enviando: " + mensagem);
         socket.send(request);
         
        //Cria datagrama de resposta
        byte[] resposta = new byte[2048];
        DatagramPacket datagramaResposta = new DatagramPacket(resposta, resposta.length);
        
        //receber mensagem com a resposta
        socket.receive(datagramaResposta);
        
        //Exibe resposta
        System.out.println("Mensagem recebida: ");
        System.out.println(new String (datagramaResposta.getData()));
        
        //finaliza conexão
        socket.close();
    }   
    
    
}
