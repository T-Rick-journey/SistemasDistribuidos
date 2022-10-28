package ClassificadosVeiculos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class Classificados extends UnicastRemoteObject implements InterfaceServidor{
    
    public List<Veiculo> listaVeiculos;
    public List<Veiculo> pesquisaResultado;
    public static final long serialVersionUID = 1L;
    
    protected Classificados() throws RemoteException {
        super ();
       this.listaVeiculos = new LinkedList<Veiculo>();
    }
    
    public List search2Ano (int anoVeiculo) throws RemoteException{
        if (!(listaVeiculos.isEmpty())){
            pesquisaResultado = new LinkedList<Veiculo>();
            for (int i=0; i<listaVeiculos.size();i++) {
                if (listaVeiculos.get(i).ano == anoVeiculo){
                    pesquisaResultado.add(listaVeiculos.get(i));
                }
            }
            return pesquisaResultado;
        }
        return pesquisaResultado;
    }
    
    public void add (Veiculo v) throws RemoteException{
        listaVeiculos.add(v);
        System.out.println("veiculo adicionado!!!");
    }
    
}
