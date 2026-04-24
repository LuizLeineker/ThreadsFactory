package Loja;

import Fabrica.Veiculo;
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
        System.out.println(
            "[RECEBIMENTO LOJA] ID:" + v.getId() +
            " COR:" + v.getCor() +
            " TIPO:" + v.getTipo() +
            " ESTACAO:" + v.getIdEstacao() +
            " FUNC:" + v.getIdFuncionario() +
            " LOJA:" + id +
            " POS_LOJA:" + v.posLoja
    );
        mutex.release();
        itens.release();
    }

    public Veiculo venderParaCliente() throws InterruptedException {
        itens.acquire();
        mutex.acquire();
        Veiculo v = esteira[cabeca];
        cabeca = (cabeca + 1) % 20;
        System.out.println(
            "[VENDA CLIENTE] " +
            "LOJA:" + id +
            " ID:" + v.getId() +
            " COR:" + v.getCor() +
            " TIPO:" + v.getTipo()
        );
        mutex.release();
        vagas.release();
        return v;
    }
}