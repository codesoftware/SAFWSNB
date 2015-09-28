package co.com.codesoftware.logic.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class CodificaBase64 {

    private String documento;
    /**
     * Funcion con la cual codifico un documento en base 64 
     * @param urlDocumento
     * @return
     */
    public boolean codificacionDocumento(String urlDocumento) {
        boolean rta = false;
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            urlDocumento = urlDocumento.replaceAll("/", "\\");
            System.out.println(urlDocumento);
            inputStream = new FileInputStream(urlDocumento);
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            BASE64Encoder enc = new BASE64Encoder();
            documento = enc.encode(baos.toByteArray());
            rta = true;
        } catch (Exception e) {
            System.out.println("Error CodificaBase64.codificacionDocumento " + e);
            rta = false;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return rta;
    }

    /**
     * Funcion con la cual decodifico el pdf que tengo en base 64
     * 
     * @param rutaDestino
     * @param nombreImg
     * @return
     */
    public boolean decodificaBase64(String rutaDestino, String nombreImg) {
        StringBuilder path = new StringBuilder();
        path.append(rutaDestino);
        path.append("\\");
        path.append(nombreImg);
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodedBytes = decoder.decodeBuffer(this.documento);
            File file = new File(path.toString());
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(decodedBytes);
            fop.flush();
            fop.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
