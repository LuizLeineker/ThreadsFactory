package Fabrica;

import java.util.concurrent.Semaphore;

public class Functionary extends Thread {
    private int id;
    private int idEstacao;

    private Semaphore esquerda;
    private Semaphore direita;
    private BufferCircular buffer;

    private Semaphore esteiraPecas;
    private Semaphore estoquePecas;

    public Functionary(int id, int idEstacao, Semaphore esquerda, Semaphore direita, BufferCircular buffer, Semaphore esteiraPecas, Semaphore estoquePecas){
        this.id = id;
        this.idEstacao = idEstacao;
        this.esquerda = esquerda;
        this.direita = direita;
        this.buffer = buffer;
        this.esteiraPecas = esteiraPecas;
        this.estoquePecas = estoquePecas;
    }

    @Override
    public void run() {
        try {
            while (true) {

                // Pegar peça (controle estoque 500 + 5 simultâneos)
                esteiraPecas.acquire();
                estoquePecas.acquire();

                // pega as duas peças para começar a produçaõ do carro
                // Evita deadlock (ordem alternada)
                if (id % 2 == 0) {
                    esquerda.acquire();
                    direita.acquire();
                } else {
                    direita.acquire();
                    esquerda.acquire();
                }

                // Produção
                Vehicle v = new Vehicle(idEstacao, id);
                int posicao = buffer.inserir(v);

                // LOG PRODUÇÃO
                System.out.println(
                        "[PRODUÇÃO VEICULO] ID:" + v.getId() + " RGB:" + v.getCor() + " TIPO:" + v.getTipo() +
                        " ESTACAO:" + v.getIdEstacao() + " FUNCIONARIO:" + v.getIdFuncionario() + " POSICAO BUFFER:" + posicao + "\n"
                );

                Thread.sleep(300);

                esquerda.release();
                direita.release();
                esteiraPecas.release();

                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}