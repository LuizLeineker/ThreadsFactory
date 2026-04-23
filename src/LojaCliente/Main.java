package LojaCliente;

import Fabrica.Veiculo;

public class Main {
    public static void main(String[] args) {

        JavaServer[] lojas = {
            new JavaServer(1),
            new JavaServer(2),
            new JavaServer(3)
        };

        // Clientes
        for (int i = 1; i <= 20; i++) {
            new Cliente(i, lojas).start();
        }

        // Thread que recebe da fábrica
        new Thread(() -> {
            try {
                java.net.Socket socket = new java.net.Socket("localhost", 5000);
                java.io.ObjectInputStream in = new java.io.ObjectInputStream(socket.getInputStream());

                java.util.Random rand = new java.util.Random();

                System.out.println("Conectado à fábrica!");

                while (true) {
                    Veiculo v = (Veiculo) in.readObject();

                    lojas[rand.nextInt(3)].receberDaFabrica(v);

                    Thread.sleep(50); // controle leve
                }

            } catch (Exception e) {
                System.out.println("Erro na conexão com a fábrica.");
            }
        }).start();
    }
}