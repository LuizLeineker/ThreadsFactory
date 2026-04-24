package Fabrica;

import java.util.concurrent.Semaphore;

public class Funcionario extends Thread {
    private int id;
    private int idEstacao;

    private Semaphore esquerda;
    private Semaphore direita;
    private BufferCircular buffer;

    private Semaphore esteiraPecas;
    private Semaphore estoquePecas;

    public Funcionario(int id, int idEstacao, Semaphore esquerda, Semaphore direita, BufferCircular buffer, Semaphore esteiraPecas, Semaphore estoquePecas){
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


                esteiraPecas.acquire();
                estoquePecas.acquire();



                if (id % 2 == 0) {
                    esquerda.acquire();
                    direita.acquire();
                } else {
                    direita.acquire();
                    esquerda.acquire();
                }


                Veiculo v = new Veiculo(idEstacao, id);
                int posicao = buffer.inserir(v);


                System.out.println(
                        "[PRODUÇÃO VEICULO] ID:" + v.getId() + " RGB:" + v.getCor() + " TIPO:" + v.getTipo() +
                        " ESTACAO:" + v.getIdEstacao() + " FUNCIONARIO:" + v.getIdFuncionario() + " POSICAO BUFFER:" + posicao + "\n"
                );

                Thread.sleep(300);

                esquerda.release();
                direita.release();
                esteiraPecas.release();

                Thread.sleep(800);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}