package LojaCliente;

import java.util.concurrent.Semaphore;

public class JavaServer {
    private int id;
    private Veiculo[] esteira = new Veiculo[20];
    private int cabeca = 0, cauda = 0;
    private Semaphore mutex = new Semaphore(1);
    private Semaphore itens = new Semaphore(0);
    private Semaphore vagas = new Semaphore(20);

    public JavaServer(int id) { this.id = id; }

    public void receberDaFabrica(Veiculo v) throws InterruptedException {
        vagas.acquire();
        mutex.acquire();
        v.idLoja = this.id;
        v.posLoja = cauda;
        esteira[cauda] = v;
        cauda = (cauda + 1) % 20;
        System.out.println("[LOJA " + id + "] Recebeu carro " + v.id);
        mutex.release();
        itens.release();
    }

    public Veiculo venderParaCliente() throws InterruptedException {
        itens.acquire();
        mutex.acquire();
        Veiculo v = esteira[cabeca];
        cabeca = (cabeca + 1) % 20;
        System.out.println("[LOJA " + id + "] Vendeu carro " + v.id);
        mutex.release();
        vagas.release();
        return v;
    }
}