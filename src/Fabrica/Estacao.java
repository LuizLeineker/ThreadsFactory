package Fabrica;

import java.util.concurrent.Semaphore;

public class Estacao {
    private int id;
    private Funcionario[] funcionarios = new Funcionario[5];
    private Semaphore[] ferramentas = new Semaphore[5];

    public Estacao(int id, BufferCircular buffer, Semaphore esteiraPecas, Semaphore estoquePecas){

        this.id = id;

        for (int i = 0; i < 5; i++){
            ferramentas[i] = new Semaphore(1);
        }

        for (int i = 0; i < 5; i++){
            Semaphore esquerda = ferramentas[i];
            Semaphore direita = ferramentas[(i + 1) % 5];

            funcionarios[i] = new Funcionario(i, id, esquerda, direita, buffer, esteiraPecas, estoquePecas);
        }
    }

    public void iniciar(){
        for (Funcionario f : funcionarios){
            f.start();
        }
    }
}