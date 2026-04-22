package LojaCliente;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        JavaServer[] lojas = { new JavaServer(1), new JavaServer(2), new JavaServer(3) };

        for (int i = 1; i <= 20; i++) {
            new ClienteThread(i, lojas).start();
        }

        new Thread(() -> {
            try {
                int idSeq = 1;
                String[] cores = {"Red", "Green", "Blue"};
                String[] tipos = {"SUV", "SEDAN"};
                Random r = new Random();
                while (true) {
                    Veiculo v = new Veiculo(idSeq++, cores[idSeq % 3], tipos[idSeq % 2],
                            r.nextInt(4)+1, r.nextInt(5)+1, r.nextInt(40));
                    lojas[r.nextInt(3)].receberDaFabrica(v);
                    Thread.sleep(1000);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }
}