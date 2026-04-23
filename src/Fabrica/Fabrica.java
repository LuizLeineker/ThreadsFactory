package Fabrica;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Fabrica {
    public static void main(String[] args) throws Exception {

        BufferCircular buffer = new BufferCircular();

        Semaphore estoquePecas = new Semaphore(500); // 500 unidades
        Semaphore esteiraPecas = new Semaphore(5);   // 5 simultâneos

        Estacao[] estacoes = new Estacao[4];

        // Inicia as 4 estações
        for (int i = 0; i < 4; i++) {
            estacoes[i] = new Estacao(i, buffer, esteiraPecas, estoquePecas);
            estacoes[i].iniciar();
        }

        // ================= SOCKET SERVER =================
        ServerSocket servidor = new ServerSocket(5000);
        System.out.println("Fabrica pronta para conexões...");

        while (true) {
            Socket socket = servidor.accept();

            System.out.println("Loja conectada!");

            new Thread(() -> {
                try {
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                    while (true) {
                        Veiculo v = buffer.remover();

                        // LOG COMPLETO (igual exigência da prova)
                        System.out.println(
                            "[VENDA PARA LOJA] " +
                            "ID:" + v.getId() +
                            " COR:" + v.getCor() +
                            " TIPO:" + v.getTipo() +
                            " ESTACAO:" + v.getIdEstacao() +
                            " FUNCIONARIO:" + v.getIdFuncionario()
                        );

                        out.writeObject(v);
                        out.flush();

                        Thread.sleep(300);
                    }

                } catch (Exception e) {
                    System.out.println("Loja desconectada.");
                }
            }).start();
        }
    }
}