package mx.unotv.noticias.prerender.utils;

import mx.unotv.noticias.prerender.dto.ParametrosDTO;

import org.apache.log4j.Logger;

public class PrerenderUtils {
	
	private static Logger LOG = Logger.getLogger(PrerenderUtils.class);
	
	/**/
	public static boolean validaErroresPagina(String HTML) {
		boolean success = false;
		try {
			if(HTML.indexOf("JSP Processing Error") > 0 || 
			   HTML.indexOf("failed to compile") > 0 || 
			   HTML.indexOf("lotsErrorContent") > 0 || 
			   HTML.indexOf("errorContent") > 0 || 
			   HTML.indexOf("login.button.login") > 0) 				
				success = false;
			else 
				success = true;			
		} catch(Exception e) {
			LOG.error("Errores en la pagina", e);
			success = false;
		}
		return success;
	}	
	
	/**/
	public static String reemplazaPlantilla(String HTML, ParametrosDTO parametrosDTO) {		
		
		try {
			String valorBase [] = HTML.split("<base");
			valorBase[0] = valorBase[1].substring(0, valorBase[1].indexOf("/>"));
			String tmp [] = valorBase[0].split("href=\"");
			String base = tmp[1].substring(0, tmp[1].indexOf("\""));			
			LOG.debug("base: "+base);
			LOG.debug("getBaseURL: "+parametrosDTO.getBaseURL());
			HTML = HTML.replace(base, parametrosDTO.getBaseURL());
		} catch (Exception e) {
			LOG.debug("No tiene base URL");
		}				
		
							
		return HTML;
	}	
	
	/**/
	public static String reemplazaURLPages(String HTML,  ParametrosDTO parametrosDTO) {
		try {
			
			//Remplaza url tema
			HTML = HTML.replace(parametrosDTO.getBaseTheme(), parametrosDTO.getBaseRecursosEstaticos());
			
			//Remplaza url portal			
			HTML = HTML.replace(parametrosDTO.getBasePagesPortal(), "");
			
		} catch (Exception e) {
			LOG.error("Ocurrio error al modificar URL de las paginas");
		}
		return HTML;		
	}
	
	/**/
	public static String reemplazaURLCanonicaSeccion(String HTML,  String parSeccion) {
		try {
			
			String urlCanonica = "http://www.unotv.com/noticias/portal/"+parSeccion+"/";
						
			//Remplaza 
			HTML = HTML.replace("$URL_PAGE$", urlCanonica);
			
			
		} catch (Exception e) {
			LOG.error("Ocurrio error al modificar URL de las paginas");
		}
		return HTML;		
	}
	
	/**/
	public static String reemplazaURLCanonicaEspeciales(String HTML,  String parSeccion) {
		try {
			
			String urlCanonica = "http://www.unotv.com/"+parSeccion+"/";
						
			//Remplaza 
			HTML = HTML.replace("$URL_PAGE$", urlCanonica);
			
			
		} catch (Exception e) {
			LOG.error("Ocurrio error al modificar URL de las paginas");
		}
		return HTML;		
	}
		
	
}
