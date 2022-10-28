package ClassificadosVeiculos;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfaceServidor extends Remote {
    public List search2Ano (int anoVeiculo) throws RemoteException;
    public void add (Veiculo v) throws RemoteException;
}

