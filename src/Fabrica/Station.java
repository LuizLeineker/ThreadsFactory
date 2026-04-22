package Fabrica;

import java.util.concurrent.Semaphore;

public class Station {
    private int id;
    private Functionary[] funcionarios = new Functionary[5]; // 5 ferramentas para 5 funcionarios
    private Semaphore[] ferramentas = new Semaphore[5];

    public Station(int id, BufferCircular buffer, Semaphore esteiraPecas, Semaphore estoquePecas){

        this.id = id;

        for (int i = 0; i < 5; i++){
            ferramentas[i] = new Semaphore(1);
        }

        for (int i = 0; i < 5; i++){
            Semaphore esquerda = ferramentas[i];
            Semaphore direita = ferramentas[(i + 1) % 5];

            funcionarios[i] = new Functionary(i, id, esquerda, direita, buffer, esteiraPecas, estoquePecas);
        }
    }

    public void iniciar(){
        for (Functionary f : funcionarios){
            f.start();
        }
    }
}