package mx.unotv.noticias.prerender.bo;

import java.util.List;

import mx.unotv.noticias.prerender.bo.exception.CallWSBOException;
import mx.unotv.noticias.prerender.bo.exception.GeneraHomeBOException;
import mx.unotv.noticias.prerender.bo.exception.MagazineFileException;
import mx.unotv.noticias.prerender.bo.exception.RemplazaHTMLBOException;
import mx.unotv.noticias.prerender.dto.NotaDTO;
import mx.unotv.noticias.prerender.dto.ParametrosDTO;
import mx.unotv.noticias.prerender.dto.ResponseNotaDTO;
import mx.unotv.noticias.prerender.utils.EscribeHTML;
import mx.unotv.noticias.prerender.utils.PrerenderUtils;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

public class GeneraHomeBO{

	private static Logger LOG = Logger.getLogger(GeneraHomeBO.class);
	@Autowired
	private RemplazaHTMLBO remplazaHTMLBO;
	@Autowired
	private CallWSBO callWSBO;
	@Autowired
	private MagazineFile magazineFile;
	
	
	/**
	 * Metodo realiza el prerender de home de UnoTV
	 * @param ParametrosDTO, parametros para realizar el prerender
	 * @throws GeneraHomeBOException
	 * */
	public void generaHome(ParametrosDTO parametrosDTO) throws GeneraHomeBOException
	{
		LOG.debug("Inicia generaHome en GeneraHomeImpl");
		try {
			
			//genera
			generaMagazineHome(parametrosDTO);
			
			//Genera los Home de Secciones de Noticias
			generaHomesNoticias(parametrosDTO);
			
			//Genera homes's especiales
			generaHomesEspaciales(parametrosDTO);
						
		} catch (Exception e) {
			LOG.error("Exception en generaHome: "+e.getMessage());			
		}
	
	}
	
	
	/*
	 * Metodo que genera los homes de las secciones de noticias
	 * @param ParametrosDTO, DTO de parametros
	 * @throws GeneraHomeBOException
	 * */
	private void generaHomesNoticias(ParametrosDTO parametrosDTO) throws GeneraHomeBOException
	{
		LOG.info("=== Inicia generaHomesNoticia ===");
	
		Document doc = null;
		boolean success = false;
				
		String secciones=parametrosDTO.getPaginas();			
		LOG.debug("secciones a generar: "+secciones);			
		String[] arraySecciones = secciones.split("\\|");		
		
		for (String seccion : arraySecciones) {		
			
			LOG.debug("Seccion: "+seccion);
			try {	
								
					//Obtenemos la pagina de portal				
					LOG.info("Pagina a generar: "+parametrosDTO.getBasePaginas()+seccion);
					doc = Jsoup.connect(parametrosDTO.getBasePaginas() + seccion).timeout(120000).get();					
					success = PrerenderUtils.validaErroresPagina(doc.html());	
					LOG.debug("validaErroresPagina: "+success);
				
					//Si no tiene errores la pagina generamos su HTML
					if (success) {					
						//Generamos la pagina HTML					
						String HTML = doc.html();
											
						//Remplazamos valores en el HTML
						HTML = remplazaHTMLBO.reemplazaPlantilla(HTML, parametrosDTO);					
						HTML = remplazaHTMLBO.reemplazaURLPages(HTML, parametrosDTO);
						HTML = remplazaHTMLBO.reemplazaURLCanonicaSeccion(HTML, seccion);					
						HTML = remplazaHTMLBO.remplazaVersionesParametros(HTML, parametrosDTO);
												
						//Ruta donde se va escribir el html
						String rutaHTML = parametrosDTO.getPathFiles() + seccion + "/";					
						
						LOG.debug("Ruta del HTML: "+rutaHTML);										
						success = EscribeHTML.writeHTML(rutaHTML, "index.html", HTML);																			
						LOG.info("Generacion seccion "+seccion+" :"+success);
					
					}
					
			}catch (RemplazaHTMLBOException ex){
				LOG.error("Error al generar la pagina" + parametrosDTO.getBasePaginas()+seccion);
				LOG.error("RemplazaHTMLBOException: "+ex.getMessage());
			} catch(Exception e) {
				LOG.error("Error al generar la pagina" + parametrosDTO.getBasePaginas()+seccion +": ", e);
			}			
		}
		
	}
	
	
	/*
	 * Metodo que genera los homes de las secciones de Noticia 
	 * @param ParametrosDTO, DTO de parametros
	 * @throws GeneraHomeBOException
	 * */
	private void generaHomesEspaciales(ParametrosDTO parametrosDTO) throws GeneraHomeBOException
	{
		LOG.info("=== Inicia generaHomesEspaciales === ");
		Document doc = null;
		boolean success = false;		
		
		String secciones=parametrosDTO.getPaginasEspeciales();				
		LOG.debug("secciones a generar: "+secciones);						
		String[] arraySecciones = secciones.split("\\|");		
		
		
		for (String seccion : arraySecciones) {		
			
			LOG.debug("Seccion: "+seccion);
			try {				
					LOG.debug("Pagina portal a generar: "+parametrosDTO.getBasePaginasEspeciales()+seccion);
					doc = Jsoup.connect(parametrosDTO.getBasePaginasEspeciales() + seccion).timeout(120000).get();
					success = PrerenderUtils.validaErroresPagina(doc.html());	
					LOG.debug("validaErroresPagina: "+success);
			
					
					if(success) {	
						//Se va generar el html
						String HTML = "";					
						HTML = remplazaHTMLBO.reemplazaPlantilla(doc.html(), parametrosDTO);
						HTML = remplazaHTMLBO.reemplazaURLPages(HTML, parametrosDTO);
						HTML = remplazaHTMLBO.reemplazaURLCanonicaEspeciales(HTML, seccion);
						HTML = remplazaHTMLBO.remplazaVersionesParametros(HTML, parametrosDTO);
		
						//Ruta donde se va escribir el html
						String rutaHTML = parametrosDTO.getPathFilesEspeciales() + seccion + "/";					
						
						LOG.debug("La ruta del html es:"+rutaHTML);
						success = EscribeHTML.writeHTML(rutaHTML, "index.html", HTML);										
					}
				
				LOG.info("Generacion seccion "+seccion+" :"+success);
			
			}catch (RemplazaHTMLBOException ex){
				LOG.error("Error al generar la pagina" + parametrosDTO.getBasePaginasEspeciales()+seccion);
				LOG.error("RemplazaHTMLBOException: "+ex.getMessage());
			} catch(Exception e) {
				LOG.error("Error al generar la pagina" + parametrosDTO.getBasePaginasEspeciales()+seccion +": ", e);
			}				
		}		
	
	}
	
	
	/*
	 * Genera el magazine del home
	 * */
	private void generaMagazineHome(ParametrosDTO parametrosDTO)
	{
		LOG.debug("Inicia generaMagazineHome");
		LOG.debug("Magazine id: "+parametrosDTO.getMagazine_ids());
		boolean success = false;
		try {
			
			ResponseNotaDTO responseNotaDTO = callWSBO._notaByMagazine(parametrosDTO.getMagazine_ids(), parametrosDTO);			
			List<NotaDTO> listaNota = responseNotaDTO.getLista();			
						
			//
			String htmlMagazine = magazineFile.getHTMLMagazine(listaNota, parametrosDTO);						
			String rutaHTML = parametrosDTO.getPathFilesEspeciales()+"componentes_estaticos/magazine/noticia/";
			success = EscribeHTML.writeHTML(rutaHTML, "magazine-home.html", htmlMagazine);			
			
		} catch (CallWSBOException ce) {
			LOG.error("CallWSBOException generaMagazineHome[BO]: "+ce.getMessage());			
		} catch (MagazineFileException me) {
			LOG.error("CallWSBOException generaMagazineHome[BO]: "+me.getMessage());
		}
		LOG.info("Se genero magazine-home.html "+success);
	}

	
}// FIN Clase


