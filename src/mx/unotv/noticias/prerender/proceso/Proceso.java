package mx.unotv.noticias.prerender.proceso;

import mx.unotv.noticias.prerender.bo.GeneraHomeBO;
import mx.unotv.noticias.prerender.dto.ParametrosDTO;
import mx.unotv.noticias.prerender.utils.EscribeArchivoMonitoreo;
import mx.unotv.noticias.prerender.utils.ObtenerProperties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Fernando Aviles
 *
 */
public class Proceso implements ApplicationContextAware {

	//LOG
	private final Logger LOG = Logger.getLogger(this.getClass().getName()); 
		
	@Autowired
	private GeneraHomeBO generaHomeBO;
	@Autowired
	private ObtenerProperties obtenerProperties;

	
			
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		LOG.info("***Entrando al contexto****") ;
	}

	/**
	 * Metodo para generar los prerender
	 * */
	public void generaHTML() 
	{
		try {	
			ParametrosDTO parametrosDTO = obtenerProperties.obtenerPropiedades();
			
			LOG.info("========= INICIA PROCESO PRE-RENDER ========") ;						
			generaHomeBO.generaHome(parametrosDTO);
			
			LOG.debug("parametrosDTO.getLineEscribir(): "+parametrosDTO.getLineEscribir().trim());
			//EscribeArchivoMonitoreo.escribeArchivoMon(Integer.valueOf(parametrosDTO.getLineEscribir().trim()), parametrosDTO);
			LOG.info("========= TERMINA PROCESO PRE-RENDER ========") ;
			
		} catch (Exception e) {
			LOG.error("Exception", e);
		}		
	}

}//FIN CLASE
