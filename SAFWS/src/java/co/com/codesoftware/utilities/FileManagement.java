package co.com.codesoftware.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import sun.misc.BASE64Encoder;

public class FileManagement {

	// public static String encodeToString(String rutaImg, String extension){
	// String imageString = null;
	// ByteArrayOutputStream bos = new ByteArrayOutputStream();
	// try {
	// BufferedImage img = ImageIO.read(new File(rutaImg));
	// ImageIO.write(img, extension, bos);
	// byte[] imageBytes = bos.toByteArray();
	//
	// BASE64Encoder encoder = new BASE64Encoder();
	//
	// imageString = encoder.encode(imageBytes);
	// bos.close();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return imageString;
	// }
	
	public static String encodeToString(String rutaImg, String extension){
		String imageString = null;
		InputStream inputStream = null;
		ByteArrayOutputStream baos = null;
		try {
			if(FileManagement.tipoSistemaOperativo().equalsIgnoreCase("WINDOWS")){
				rutaImg = "C:" + rutaImg;
			}
			File file = new File(rutaImg);
			if(!file.exists()){
				return "La ruta de la imagen a codificar no existe";
			}
			inputStream = new FileInputStream(file);
			//inputStream = new FileInputStream("C:\\IMAGENES\\pantallaPrincipal\\1-1_producto.jpeg");
			byte[] buffer = new byte[1024];
	        baos = new ByteArrayOutputStream();

	        int bytesRead;
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            baos.write(buffer, 0, bytesRead);
	        }
	        BASE64Encoder enc = new BASE64Encoder();
	        imageString = enc.encode(baos.toByteArray());			
		} catch (Exception e) {
			System.out.println("Error CodificaBase64.codificacionDocumento " + e);
			return null;
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return imageString;
	}
	
	private static String tipoSistemaOperativo(){
		ResourceBundle rb = ResourceBundle.getBundle("co.com.codesoftware.properties.baseProperties");
		return rb.getString("TIPO_SO");
	}

}
