package Fabrica;

import java.util.concurrent.Semaphore;

public class Factory {

    public static void main(String[] args) {

        BufferCircular buffer = new BufferCircular();

        Semaphore estoquePecas = new Semaphore(500); // 500 unidades
        Semaphore esteiraPecas = new Semaphore(5);   //esteira de paças

        Station[] estacoes = new Station[4];

        // 4 estacao de producao
        for (int i = 0; i < 4; i++) {
            estacoes[i] = new Station(i, buffer, esteiraPecas, estoquePecas);
            estacoes[i].iniciar();
        }

        System.out.println("Comecando a Producao da Fabrica...");
    }
}