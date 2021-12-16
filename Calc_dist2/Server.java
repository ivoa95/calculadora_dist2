
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Servidor {

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(5050);
            servidor.executar();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private final int porta;

    public Servidor(int porta) throws IOException {
        this.porta = porta;
    }

    public void executar() throws IOException {
        ServerSocket servidor = new ServerSocket(porta);
        System.out.println("Ouvindo a porta " + porta);

        Socket server1 = null;
        while ((server1 = servidor.accept()) != null) { // aguarda um cliente se conectar
            System.out.println("Cliente conectado: " + server1.getInetAddress().getHostAddress());

            DataOutputStream saida1 = new DataOutputStream(server1.getOutputStream());
            DataInputStream entrada1 = new DataInputStream(server1.getInputStream());

            // ler operação escolhida pelo cliente
            //int opr = entrada.readInt(); // o cliente enviou com writeInt, então leio com readInt
            String operador = entrada1.readUTF();
            double numero1 = entrada1.readDouble();
            double numero2 = entrada1.readDouble();


            //int num1 = (int) (Math.random() * 10 + 1);
            //int num2 = (int) (Math.random() * 10 + 1);
            String operacao = null;
            double resultado = 0;
            switch (operador) {
                case "+":
                    operacao = "+";
                    resultado = numero1 + numero2;
                    break;
                case "-":
                    operacao = "-";
                    resultado = numero1 - numero2;
                    break;
                case "*":
                    operacao = "*";
                    resultado = numero1 * numero2;
                    break;
                case "/":
                    operacao = "/";
                    resultado = numero1 / (double) numero2;
                    break;
            }

            // enviar mensagem para o cliente
            //String mensagem = numero1 + operador + numero2;
            saida1.writeUTF(resultado+"");
            saida1.flush();


        }

    }
}