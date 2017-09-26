package mx.unotv.noticias.prerender.utils;

//import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
//import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class EscribeHTML {

	private static Logger LOG = Logger.getLogger(EscribeHTML.class);
	
	/**
	 * Metodoq que escribe un HTML Local
	 * */
	public static boolean writeHTML(String rutaHTML, String nombreArchivo, String HTML) 
	{		
		LOG.debug("Inicia writeHTML..");		
		LOG.debug("rutaHTML: "+rutaHTML);
		LOG.debug("nombreArchivo: "+nombreArchivo);
		boolean success = false;
		try {			
			FileWriter fichero = null;
	        PrintWriter pw = null;
	        success = createFolders(rutaHTML);	       
	        if(success) {
		        try {
					fichero = new FileWriter(rutaHTML + nombreArchivo);				
					pw = new PrintWriter(fichero);							
					pw.println(CodificaCaracteres.cambiaCaracteres(HTML));
					pw.close();
					success = true;
				} catch(Exception e){			
					LOG.error("Error al crear html " + rutaHTML + ": ", e);
					success = false;
				}finally{
					try{                    			              
						if(null!= fichero)
							fichero.close();
					}catch (Exception e2){
						success = false;
						LOG.error("Error al cerrar el file: ", e2);
					}
				}
	        }
		} catch(Exception e) {
			success = false;
			LOG.error("Fallo al crear el componente: ", e);
		}		
		return success;
	}
	
	/**
	 * Metodo que crea los folder en el servidor
	 * 
	 * */
	private static boolean createFolders(String carpetaContenido) 
	{
		
		LOG.debug("Inicia createFolders");
		LOG.debug("carpetaContenido: " + carpetaContenido);
		
		boolean success = false;
		
		try {						
			File carpetas = new File(carpetaContenido) ;			
			//LOG.debug("carpetas.exists(): "+carpetas.exists());			 
			if(!carpetas.exists()) {
				success = carpetas.mkdirs();				
				LOG.debug("success mkdirs: "+success);
			} else 
				success = true;							
		} catch (Exception e) {
			success = false;
			LOG.error("Ocurrio error al crear las carpetas: ", e);
		} 
		return success;
	}
	
	/**
	 * Metodo que tranfiere al webserver lso componentes
	 * */
	public static void transfiereWebServer(String rutaShell, String pathLocal, String pathRemote) 
	{
		String comando = rutaShell + " " + pathLocal+ " " + pathRemote;
		LOG.debug("comando: "+comando);		
		try {								
			Runtime r = Runtime.getRuntime();			
			r.exec(comando).waitFor();
			r.exec(comando);			
		} catch(Exception e) {
			LOG.error("Ocurrio un error al ejecutar el Shell " + comando + ": ", e);
		}		
	}
	
	/**
	 * Metodo que tranfiere al webserver lso componentes
	 * */
	public static void transfiereWebServer12(String rutaShell, String pathLocal, String pathRemote) 
	{
		String comando = rutaShell + " " + pathLocal+ " " + pathRemote;
		LOG.debug("comando: "+comando);		
		try {								
			Runtime r = Runtime.getRuntime();			
			r.exec(comando).waitFor();
			r.exec(comando);			
		} catch(Exception e) {
			LOG.error("Ocurrio un error al ejecutar el Shell " + comando + ": ", e);
		}		
	}
	
}
