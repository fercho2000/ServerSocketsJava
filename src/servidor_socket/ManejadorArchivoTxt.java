package servidor_socket;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// a. Monte el compilador de Java. Cree un archivo llamado datos.txt usando la utilidad manejo de archivos en Java.
public class ManejadorArchivoTxt {

    private static final String NOMBRE_DEL_ARCHIVO = "\\src\\archivos\\datos.txt";

    public ManejadorArchivoTxt() {

    }

    public void crearArchivo() {
        try {
            if (validarSiExisteArchivo()) {
                // eliminarArchivo();
                System.out.println("Se elimino archivo anterior..");
            }
            Files.createFile(Paths.get((obtenerRutaArchivo())));
            System.out.println("Archivo creado...");

        } catch (Exception e) {
            System.out.println("Ocurrio un error creando el archivo...");
        }
    }

    boolean validarSiExisteArchivo() {
        try {
            return Files.exists(Paths.get((obtenerRutaArchivo())));
        } catch (Exception e) {
            return false;
        }
    }

    public void eliminarArchivo() throws IOException {
        File targetFile = new File(String.valueOf(obtenerRutaArchivo()));
        targetFile.delete();
    }

    String obtenerRutaArchivo() {
        return Paths.get("").toAbsolutePath() + NOMBRE_DEL_ARCHIVO;
    }
}
