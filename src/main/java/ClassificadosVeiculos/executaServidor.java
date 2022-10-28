
package ClassificadosVeiculos;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class executaServidor {

    public static void main(String[] args) throws RemoteException {

        Classificados servidor = new Classificados();
        LocateRegistry.createRegistry(1099);
        try {
            Naming.bind("rmi://localhost/ServidorRMI", servidor);
            System.out.println("Servidor conectado!OK!");
        } catch (MalformedURLException ex) {
            Logger.getLogger(executaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(executaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
