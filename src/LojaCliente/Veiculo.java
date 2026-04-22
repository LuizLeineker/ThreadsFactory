package LojaCliente;

import java.io.Serializable;

public class Veiculo implements Serializable {
    public int id, idEstacao, idFuncionario, posFabrica, idLoja, posLoja;
    public String cor, tipo;

    public Veiculo(int id, String cor, String tipo, int idEstacao, int idFuncionario, int posFabrica) {
        this.id = id; this.cor = cor; this.tipo = tipo;
        this.idEstacao = idEstacao; this.idFuncionario = idFuncionario;
        this.posFabrica = posFabrica;
    }
}