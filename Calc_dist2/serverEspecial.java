
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.math.*;

class ServidorEspecial {

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(5051);
            servidor.executar();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private final int porta;

    public ServidorEspecial(int porta) throws IOException {
        this.porta = porta;
    }

    public void executar() throws IOException {
        ServerSocket servidor = new ServerSocket(porta);
        System.out.println("Ouvindo a porta " + porta);

        Socket server2 = null;
        while ((server2 = servidor.accept()) != null) { // aguarda um cliente se conectar
            System.out.println("Cliente conectado: " + server2.getInetAddress().getHostAddress());

            DataOutputStream saida2 = new DataOutputStream(server2.getOutputStream());
            DataInputStream entrada2 = new DataInputStream(server2.getInputStream());

            // ler operação escolhida pelo cliente
            //int opr = entrada.readInt(); // o cliente enviou com writeInt, então leio com readInt
            String operador = entrada2.readUTF();
            double numero1 = entrada2.readDouble();
            double numero2 = entrada2.readDouble();


            //int num1 = (int) (Math.random() * 10 + 1);
            //int num2 = (int) (Math.random() * 10 + 1);
            String operacao = null;
            double resultado = 0;
            switch (operador) {
                case "%":
                    operacao = "%";
                    resultado = (numero1 * numero2)/100;
                    break;
                case "r":
                    operacao = "r";
                    resultado = Math.sqrt(numero1);;
                    break;
                case "++":
                    operacao = "++";
                    resultado = numero1 + numero2 + numero1;
                    break;
                case "^":
                    operacao = "^";
                    resultado = Math.pow (numero1, numero2);
                    break;
            }

            // enviar mensagem para o cliente
           // String mensagem = numero1 + operador + numero2;
            saida2.writeUTF(resultado+"");
            saida2.flush();


        }

    }
}