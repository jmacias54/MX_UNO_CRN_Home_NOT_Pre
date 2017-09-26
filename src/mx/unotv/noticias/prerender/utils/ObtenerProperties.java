package mx.unotv.noticias.prerender.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import mx.unotv.noticias.prerender.dto.ParametrosDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;


public class ObtenerProperties {
	
	//LOG
	private final Logger LOG = Logger.getLogger(this.getClass().getName());
	@Value("${ambiente}")
	private String ambiente = "";
	
	@Value("${${ambiente}.ruta.properties}")
	private String rutaProperties = "";
	
	@Value("${${ambiente}.ruta.propertiesgeneral}")
	private String rutaPropertiesGeneral = "";

	/**
	 * 
	 * */
	public ParametrosDTO obtenerPropiedades() 
	{
		ParametrosDTO parametrosDTO = new ParametrosDTO();		 
		try {	    	
			
			LOG.debug("Ruta Prop 1: "+rutaProperties);
			LOG.debug("Ruta Prop 2: "+rutaPropertiesGeneral);
			
			Properties propertiesGeneral = new Properties();
			propertiesGeneral.load(new FileInputStream(new File(rutaPropertiesGeneral)));
			
			Properties props = new Properties();
			props.load(new FileInputStream(new File(rutaProperties)));
									
			parametrosDTO.setPathFiles(propertiesGeneral.getProperty("path_files_web02Uno")+props.getProperty("pathFiles"));			
			parametrosDTO.setPathFilesEspeciales(propertiesGeneral.getProperty("path_files_web02Uno")+"/");
			parametrosDTO.setWs_url_base(propertiesGeneral.getProperty("dominio_wsb"));
			parametrosDTO.setDominio_wsd(propertiesGeneral.getProperty("dominio_wsd"));
			parametrosDTO.setBasePaginas(propertiesGeneral.getProperty("dominio_portal")+props.getProperty("basePaginas"));
			parametrosDTO.setBasePaginasEspeciales(propertiesGeneral.getProperty("dominio_portal")+props.getProperty("basePaginasEspeciales"));


			parametrosDTO.setBaseRecursosEstaticos(props.getProperty("baseRecursosEstaticos") == null?"": props.getProperty("baseRecursosEstaticos"));
			parametrosDTO.setNameHTML(props.getProperty("nameHTML") == null?"": props.getProperty("nameHTML"));			
			parametrosDTO.setBaseTheme(props.getProperty("baseTheme") == null?"": props.getProperty("baseTheme"));
			parametrosDTO.setBaseURL(props.getProperty("baseURL") == null?"": props.getProperty("baseURL"));			
			parametrosDTO.setBasePagesPortal(props.getProperty("basePagesPortal") == null?"": props.getProperty("basePagesPortal"));
			
			parametrosDTO.setPaginas(props.getProperty("paginas") == null?"": props.getProperty("paginas"));
			parametrosDTO.setPaginasEspeciales(props.getProperty("paginasEspeciales") == null?"": props.getProperty("paginasEspeciales"));
			
			parametrosDTO.setRutaArchivoMot(props.getProperty("rutaArchivoMot") == null?"": props.getProperty("rutaArchivoMot"));
			parametrosDTO.setRutaEstaticoMot(props.getProperty("rutaEstaticoMot") == null?"": props.getProperty("rutaEstaticoMot"));
			parametrosDTO.setNombreAplicacion(props.getProperty("nombreAplicacion") == null?"": props.getProperty("nombreAplicacion"));
			parametrosDTO.setLineEscribir(props.getProperty("lineEscribir") == null?"": props.getProperty("lineEscribir"));			
			parametrosDTO.setCatalogoParametros(props.getProperty("catalogoParametros") == null?"": props.getProperty("catalogoParametros"));
			
			parametrosDTO.setWs_metodo_getParameter(props.getProperty("ws_metodo_getParameter") == null?"": props.getProperty("ws_metodo_getParameter"));
			parametrosDTO.setWs_metodo_notaByMagazine(props.getProperty("ws_metodo_notaByMagazine") == null?"": props.getProperty("ws_metodo_notaByMagazine"));
			
			parametrosDTO.setMagazineHtml(props.getProperty("magazineHtml"));
			parametrosDTO.setMagazineHtmlItem(props.getProperty("magazineHtmlItem"));
			parametrosDTO.setMagazine_ids(props.getProperty("magazine_ids"));
			parametrosDTO.setTipoNotaConfig(props.getProperty("tipoNotaConfig"));
			
		} catch (Exception ex) {
			parametrosDTO = new ParametrosDTO();
			LOG.error("No se encontro el Archivo de propiedades: ", ex);			
		}
		return parametrosDTO;
    }
}
