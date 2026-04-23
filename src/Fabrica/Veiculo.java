package Fabrica;

import java.io.Serializable;

public class Veiculo  implements Serializable {
    private static int contador = 0;

    private int id;
    private String rgb;
    private String tipo;
    public int idEstacao;
    public int idFuncionario;
    public int idLoja;
    public int posLoja;

    public Veiculo(int idEstacao, int idFuncionario) {
        synchronized (Veiculo.class) {
        this.id = ++contador;
    }

        String[] rgbs = {"255, 0, 0", "0, 255, 0", "0, 0, 255", "255, 165, 0", "10, 10, 10"};
        String[] tipos = {"SUV", "SEDAN"};

        this.rgb = rgbs[id % 5];
        this.tipo = tipos[id % 2];

        this.idEstacao = idEstacao;
        this.idFuncionario = idFuncionario;
    }

    public int getId() { return id; }
    public String getCor() { return rgb; }
    public String getTipo() { return tipo; }
    public int getIdEstacao() { return idEstacao; }
    public int getIdFuncionario() { return idFuncionario; }
}