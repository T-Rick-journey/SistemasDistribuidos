package RemoteCalculatorUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class Server {

    public static void main(String[] args) throws IOException {
        //Declarara a porta e cria o buffer de entrada e saida
        int porta = 6789;
        byte[] dadosReceber = new byte[1024];
        byte[] dadosEnviar = new byte[2048];
        
        //Atribui soquete
        DatagramSocket socket = new DatagramSocket(porta);
        System.out.println("Servidor iniciado!");
        
        while (true) {
            //Cria o datagrama que armazenara a resposta
            DatagramPacket request = new DatagramPacket(dadosReceber, 
                    dadosReceber.length);

            //Aguarda o recebimento do datagrama
            System.out.println("Aguardando datagrama...");
            socket.receive(request);

            //pega a requisição no datagrama e tranforma em string
            System.out.println("Datagrama UDP recebido:");
            String sentenca = new String(request.getData());
            System.out.println(sentenca);
            
            //Dados do destinatatio pra envio da resposta
            InetAddress enderecoIP = request.getAddress();
            int portaDestino = request.getPort();
           
            //Tratamento da mensagem recebida
            //Identifica os numeros e o operador na mensagem recebida
            StringTokenizer st = new StringTokenizer(sentenca); 
            double numero1 = Double.parseDouble(st.nextToken());
            String operador = st.nextToken();
            double numero2 = Double.parseDouble(st.nextToken());
            String resultado;
            
            // Realiza o calculo
            if ("+".equals(operador)){
                   resultado = Double.toString(numero1 + numero2);
            } else { if ("-".equals(operador)) {
                resultado = Double.toString(numero1 - numero2);
            } else{ if ("*".equals(operador)){
                resultado = Double.toString(numero1 * numero2);
            } else{ resultado = Double.toString(numero1 / numero2);
            }}}
            
            //* pacote a ser enviado
            System.out.println(resultado);
            String mensagemResposta = (sentenca + " = " +resultado);
            System.out.println(mensagemResposta);
            dadosEnviar = mensagemResposta.getBytes();
            
            //* ----> enviar pacote ao destinatatio
            DatagramPacket pacoteEnviar = new DatagramPacket(dadosEnviar, 
                    dadosEnviar.length, enderecoIP, portaDestino);
            socket.send(pacoteEnviar);
        }
    }
}
