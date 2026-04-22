package LojaCliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClienteThread extends Thread {
    private int id;
    private JavaServer[] lojas;
    private List<Veiculo> garagem = new ArrayList<>();
    private Random rand = new Random();

    public ClienteThread(int id, JavaServer[] lojas) {
        this.id = id;
        this.lojas = lojas;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int lojaSorteada = rand.nextInt(3);
                Veiculo v = lojas[lojaSorteada].venderParaCliente();
                garagem.add(v);
                Thread.sleep(rand.nextInt(3000) + 2000);
            }
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}