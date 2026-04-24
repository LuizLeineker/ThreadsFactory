package Loja;

import Fabrica.Veiculo;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        JavaServer estoqueLoja = new JavaServer(1); // ID da Loja

        // THREAD 1: Conecta na Fábrica para puxar veículos
        new Thread(() -> {
            try (Socket socketFabrica = new Socket("localhost", 5000);
                 ObjectInputStream in = new ObjectInputStream(socketFabrica.getInputStream())) {

                System.out.println("[LOJA] Conectada à Fábrica na porta 5000");
                while (true) {
                    Veiculo v = (Veiculo) in.readObject();
                    estoqueLoja.receberDaFabrica(v);
                }
            } catch (Exception e) {
                System.err.println("[LOJA] Erro de conexão com a Fábrica: " + e.getMessage());
            }
        }).start();

        // THREAD 2: Abre servidor para os Clientes
        try (ServerSocket servidorLoja = new ServerSocket(6000)) {
            System.out.println("[LOJA] Servidor da Loja pronto na porta 6000...");

            while (true) {
                Socket socketCliente = servidorLoja.accept();
                new Thread(() -> {
                    try (ObjectOutputStream out = new ObjectOutputStream(socketCliente.getOutputStream())) {
                        Veiculo v = estoqueLoja.venderParaCliente();
                        out.writeObject(v);
                        out.flush();
                    } catch (Exception e) {
                        System.err.println("[LOJA] Erro ao enviar veículo ao cliente.");
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}