
package ClassificadosVeiculos;

import java.io.Serializable;

public class Veiculo implements Serializable{
    
    String nomeCliente;
    String marcaVeiculo;
    double valorVenda;
    int ano;
    
    public Veiculo(String cliente, String modelo, double preco, int ano){
        this.nomeCliente = cliente;
        this.marcaVeiculo = modelo;
        this.valorVenda = preco;
        this.ano = ano;
    }
}
