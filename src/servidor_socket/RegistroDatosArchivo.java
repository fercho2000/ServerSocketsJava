package servidor_socket;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistroDatosArchivo {


    public boolean guardar(String filePath, String contentToBeAppended) {

        try (
                FileWriter fw = new FileWriter(filePath, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)
        ) {
            out.println(contentToBeAppended);
            return true;
        } catch (IOException e) {
            return false;
        }

    }
}

