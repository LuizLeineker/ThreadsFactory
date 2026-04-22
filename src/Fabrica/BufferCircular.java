package Fabrica;
import java.util.concurrent.Semaphore;

public class BufferCircular {
    private Vehicle[] buffer = new Vehicle[40];  //buffer com capacidade de 40
    private int in = 0, out = 0;

    private Semaphore cheio = new Semaphore(0);
    private Semaphore vazio = new Semaphore(40);
    private Semaphore mutex = new Semaphore(1);

    public int inserir(Vehicle v) throws InterruptedException {
        vazio.acquire();
        mutex.acquire();

        int posicao = in;
        buffer[in] = v;

        in = (in + 1) % 40;

        mutex.release();
        cheio.release();

        return posicao;
    }

    public Vehicle remover() throws InterruptedException {
        cheio.acquire();
        mutex.acquire();

        Vehicle v = buffer[out];
        out = (out + 1) % 40;

        mutex.release();
        vazio.release();

        return v;
    }
}