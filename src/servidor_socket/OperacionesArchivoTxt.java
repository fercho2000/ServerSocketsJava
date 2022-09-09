package servidor_socket;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class OperacionesArchivoTxt {

    private static final int POSICION_NUMERO_CUENTA = 0;
    private static final int POSICION_VALOR_CUENTA = 1;

    public boolean guardar(String rutaArchivo, String contentToBeAppended) {

        try (
                FileWriter fw = new FileWriter(rutaArchivo, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)
        ) {
            out.println(contentToBeAppended);
            return true;
        } catch (IOException e) {
            return false;
        }

    }


    public String consultarCuenta(String rutaArchivo, String numeroCuenta) throws IOException {
        final String MENSAJE_CUANDO_NO_EXISTE_CUENTA = "El Número de cuenta ingresado no existe...";
        final String MENSAJE_CUANDO_SI_EXISTE_CUENTA_EN_ARCHIVO = "El valor del número de cuenta solicitado es: %s";
        BufferedReader lecturaArchivo = Files.newBufferedReader(Paths.get(rutaArchivo), StandardCharsets.UTF_8);
        String lineaRegistro;
        String valorCuenta = null;

        while ((lineaRegistro = lecturaArchivo.readLine()) != null) {

            String[] datosCliente = lineaRegistro.split(",");
            if (Objects.equals(datosCliente[POSICION_NUMERO_CUENTA], numeroCuenta)) {
                valorCuenta = datosCliente[POSICION_VALOR_CUENTA];
            }

        }
        lecturaArchivo.close();

        return valorCuenta != null ? String.format(MENSAJE_CUANDO_SI_EXISTE_CUENTA_EN_ARCHIVO, valorCuenta)
                : MENSAJE_CUANDO_NO_EXISTE_CUENTA;
    }
}

