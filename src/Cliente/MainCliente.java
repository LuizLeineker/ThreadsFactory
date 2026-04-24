package Cliente;

import Fabrica.Veiculo;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MainCliente {
    public static void main(String[] args) {
        // Dispara 20 threads de clientes
        for (int i = 1; i <= 20; i++) {
            final int idCliente = i;
            new Thread(() -> {
                try {
                    while (true) {
                        // Tenta conectar na Loja (porta 6000)
                        try (Socket socket = new Socket("localhost", 6000);
                             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                            Veiculo v = (Veiculo) in.readObject();
                            System.out.println("[CLIENTE " + idCliente + "] Comprou: ID " + v.getId() + " - " + v.getTipo());

                            // Espera entre 4 e 9 segundos para a próxima compra
                            Thread.sleep((long) (Math.random() * 5000 + 4000));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("[CLIENTE " + idCliente + "] Loja indisponível, tentando novamente...");
                    try { Thread.sleep(2000); } catch (InterruptedException ex) {}
                }
            }).start();
        }
    }
}