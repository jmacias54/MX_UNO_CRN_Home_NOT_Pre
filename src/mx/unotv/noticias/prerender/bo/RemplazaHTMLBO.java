package mx.unotv.noticias.prerender.bo;

import mx.unotv.noticias.prerender.bo.exception.CallWSBOException;
import mx.unotv.noticias.prerender.bo.exception.RemplazaHTMLBOException;
import mx.unotv.noticias.prerender.dto.ParametrosDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class RemplazaHTMLBO {

	//LOG
	private static Logger LOG = Logger.getLogger(RemplazaHTMLBO.class);
	@Autowired
	private CallWSBO callWSBO;	
	
	/**
	 * Metodo que remplaza las versiones de para los script, styles y json
	 * @param HTML, HTML que se esta generando
	 * @ParametrosDTO, parametros del properties
	 * @throws RemplazaHTMLBOException
	 * @author Fernando Aviles
	 * */
	public String remplazaVersionesParametros(String HTML, ParametrosDTO parametrosDTO) throws RemplazaHTMLBOException
	{				
		LOG.debug("Inicia Remplaza Versiones");
		try {			
			String[] arrayParametros = parametrosDTO.getCatalogoParametros().split("\\|");			
			for (String strParametro : arrayParametros) {
				String[] arrayParametro = strParametro.split(",");
				LOG.debug("Id: "+arrayParametro[0]);
				LOG.debug("Remplazar: "+arrayParametro[1]);			
				String strParValor = ""; //callWSBO._callObtieneParametro(arrayParametro[0], parametrosDTO);											
				LOG.debug("Se remplaza: "+arrayParametro[1]+" por: "+strParValor);
				HTML = HTML.replace(arrayParametro[1], strParValor);								
			}						
			return HTML;
		} /*catch(CallWSBOException ex){
			LOG.error("CallWSBOException en remplazaVersiones: "+ex.getMessage());
			throw new RemplazaHTMLBOException(ex.getMessage());
		}*/ catch (Exception e) {
			LOG.error("Exception en remplazaVersiones: ",e);
			throw new RemplazaHTMLBOException(e.getMessage());
		}			
	}

	/**
	 * Metodo que rempplaza la base url
	 * @param String con el HTML
	 * @param ParametrosDTO, DTO con los parametros
	 * @return HTML que se esta tratando
	 * @throws RemplazaHTMLBOException
	 * */
	public String reemplazaPlantilla(String HTML, ParametrosDTO parametrosDTO) throws RemplazaHTMLBOException
	{		
		LOG.debug("Inicia reemplazaPlantilla..");
		try {
			String valorBase [] = HTML.split("<base");
			valorBase[0] = valorBase[1].substring(0, valorBase[1].indexOf("/>"));
			String tmp [] = valorBase[0].split("href=\"");
			String base = tmp[1].substring(0, tmp[1].indexOf("\""));			
			LOG.debug("base: "+base);
			LOG.debug("getBaseURL: "+parametrosDTO.getBaseURL());
			HTML = HTML.replace(base, parametrosDTO.getBaseURL());
			return HTML;
		} catch (Exception e) {
			LOG.error("No tiene base URL: ",e);
			throw new RemplazaHTMLBOException(e.getMessage());
		}						
	}	
	
	
	/**
	 * Metodo que remplaza las url de portal
	 * @param String con el HTML
	 * @param ParametrosDTO, DTO con los parametros
	 * @return HTML que se esta tratando
	 * @throws RemplazaHTMLBOException
	 * */
	public String reemplazaURLPages(String HTML,  ParametrosDTO parametrosDTO)throws RemplazaHTMLBOException 
	{
		try {
			
			//Remplaza url tema
			HTML = HTML.replace(parametrosDTO.getBaseTheme(), parametrosDTO.getBaseRecursosEstaticos());
			
			//Remplaza url portal			
			HTML = HTML.replace(parametrosDTO.getBasePagesPortal(), "");
			
			return HTML;		

		} catch (Exception e) {
			LOG.error("Exception en reemplazaURLPages",e);
			throw new RemplazaHTMLBOException(e.getMessage());
		}
	}
	
	/**
	 * Metodo que remplaza la url canonica de la secciones
	 * @param String con el HTML
	 * @param parSeccion, DTO con los parametros
	 * @return HTML que se esta tratando
	 * @throws RemplazaHTMLBOException
	 * */
	public String reemplazaURLCanonicaSeccion(String HTML,  String parSeccion) throws RemplazaHTMLBOException 
	{
		try {
			
			String urlCanonica = "http://www.unotv.com/noticias/portal/"+parSeccion+"/";					
			//Remplaza 
			HTML = HTML.replace("$URL_PAGE$", urlCanonica);
			return HTML;
			
		} catch (Exception e) {
			LOG.error("Exception en reemplazaURLCanonicaSeccion: ",e);
			throw new RemplazaHTMLBOException(e.getMessage());
		}
				
	}
	
	/**
	 * Metodo que remplaza la url canonica de las paginas especiales
	 * @param String con el HTML
	 * @param parSeccion, DTO con los parametros
	 * @return HTML que se esta tratando
	 * @throws RemplazaHTMLBOException
	 * */
	public String reemplazaURLCanonicaEspeciales(String HTML,  String parSeccion) throws RemplazaHTMLBOException 
	{
		try {
			
			String urlCanonica = "http://www.unotv.com/"+parSeccion+"/";					
			//Remplaza 
			HTML = HTML.replace("$URL_PAGE$", urlCanonica);
			return HTML;		
			
		} catch (Exception e) {
			LOG.error("Exception en reemplazaURLCanonicaEspeciales: ",e);
			throw new RemplazaHTMLBOException(e.getMessage());
		}
	}
	
		
}//FIN CLASE
