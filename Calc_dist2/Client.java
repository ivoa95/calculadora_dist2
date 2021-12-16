
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.lang.String;

class Cliente {

    public static void main(String[] args) throws IOException {
        try {
            Cliente cliente = new Cliente("127.0.0.1");
            cliente.executar();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private final Scanner teclado = new Scanner(System.in);


    private final String host;


    public Cliente(String host) {
        this.host = host;

    }

    public void executar() throws IOException {
        Socket server1 = new Socket(host, 5050);
        Socket server2 = new Socket(host, 5051);

        System.out.println("O cliente se conectou ao servidor!");

        DataInputStream entrada1 = new DataInputStream(server1.getInputStream());
        DataOutputStream saida1 = new DataOutputStream(server1.getOutputStream());

        DataInputStream entrada2 = new DataInputStream(server2.getInputStream());
        DataOutputStream saida2 = new DataOutputStream(server2.getOutputStream());

        String operador = "";
        String operacao = "";
        double numero1 = 0;
        double numero2 = 0;
        String sair = "";



        while (!sair.equals("sair")){
            System.out.println("Escolha o primeiro valor: ou 'sair' para fechar: ");
            numero1 = Double.parseDouble(teclado.nextLine()); // ao ler entrada do TECLADO sempre use nextLine()
            System.out.println("Escolha a operação: ");
            operador = teclado.nextLine()+"";
            System.out.println("Escolha o segundo valor: ");
            numero2 = Double.parseDouble(teclado.nextLine()); // ao ler entrada



            if( operador.equals("+") || operador.equals("-") || operador.equals("*") || operador.equals("/") ){
                // enviar operação ao servidor basico
                saida1.writeUTF(operador);
                saida1.writeDouble(numero1);
                saida1.writeDouble(numero2);
                saida1.flush();

                String mensagem = entrada1.readUTF(); // o servidor enviou com writeUTF, então leio com readUTF
                System.out.println(mensagem);

            } else if(operador.equals("%") || operador.equals("r") || operador.equals("^") || operador.equals("++")){
                // enviar operação ao servidor especial
                if(operador.equals("r")){
                    saida2.writeUTF(operador);
                    saida2.writeDouble(numero1);
                }
                saida2.writeUTF(operador);
                saida2.writeDouble(numero1);
                saida2.writeDouble(numero2);
                saida2.flush();

                String mensagem = entrada2.readUTF(); // o servidor enviou com writeUTF, então leio com readUTF
                System.out.println(mensagem);
            }else{
                // fechar streams
                entrada1.close();
                entrada2.close();
                saida1.close();
                saida2.close();
                server1.close();
                server2.close();
            }
        }





    }
}