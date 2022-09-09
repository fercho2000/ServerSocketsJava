package servidor_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

//b. Elabore un programa socket server que recoja las peticiones de un programa cliente.
public class ServidorSocket {


    private ServerSocket serverSocket;


    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true)
                new EchoClientHandler(serverSocket.accept()).start();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }

    }

    public void stop() {
        try {

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class EchoClientHandler extends Thread {
        private static final int POSICION_NUMERO_OPERACION = 0;
        private static final int POSICION_NUMERO_CUENTA = 1;
        private static final int POSICION_VALOR_CUENTA = 2;
        private static final String OPERACION_REGISTRO = "1";
        private static final String OPERACION_CONSULTA = "2";
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private ManejadorArchivoTxt manejadorArchivoTxt;
        private RegistroDatosArchivo registroDatosArchivo;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
            manejadorArchivoTxt = new ManejadorArchivoTxt();
            manejadorArchivoTxt.crearArchivo();
        }

        public void run() {
            try {
                registroDatosArchivo = new RegistroDatosArchivo();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                String numerocuenta = null;
                String valorCuenta = null;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    System.out.println("entra este valor " + inputLine);
                    String[] datosCliente = inputLine.split(",");
                    String operacion = datosCliente[POSICION_NUMERO_OPERACION];
                    numerocuenta = datosCliente[POSICION_NUMERO_CUENTA];



                    if (Objects.equals(operacion, OPERACION_REGISTRO)) {
                        valorCuenta = datosCliente[POSICION_VALOR_CUENTA];
                        boolean ingresoRegistro = registroDatosArchivo.guardar(manejadorArchivoTxt.obtenerRutaArchivo(), inputLine);

                        System.out.println(" el numero de la cuenta es: " + numerocuenta + " y el valor es : " + valorCuenta);

                        out.println(ingresoRegistro ? "Registro grabado OK" : "Registro grabado NO-OK");
                    } else if (Objects.equals(operacion, OPERACION_CONSULTA)) {
                        out.println("Consultando Cuenta número : " + numerocuenta);
                    }

                }

                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                System.out.println("exception .. ");
            }
        }
    }

    public static void main(String[] args) {


        ServidorSocket server = new ServidorSocket();
        server.start(4444);
        System.out.println("El Servidor se encuentra disponible ....");
    }

}