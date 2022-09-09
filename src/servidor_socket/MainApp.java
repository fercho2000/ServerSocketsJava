package servidor_socket;

public class MainApp {
    public static void main(String[] args) {


        ServidorSocket server = new ServidorSocket();
        server.start(4444);
        System.out.println("El Servidor se encuentra disponible ....");
    }
}
