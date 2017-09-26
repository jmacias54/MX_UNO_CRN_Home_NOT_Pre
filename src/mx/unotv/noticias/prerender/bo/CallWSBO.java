package mx.unotv.noticias.prerender.bo;

import mx.unotv.noticias.prerender.bo.exception.CallWSBOException;
import mx.unotv.noticias.prerender.dto.ParametrosDTO;
import mx.unotv.noticias.prerender.dto.ResponseNotaDTO;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class CallWSBO{

	
	//LOG
	private static Logger LOG = Logger.getLogger(CallWSBO.class);
	
	private RestTemplate restTemplate;	
	private HttpHeaders headers =  new HttpHeaders();

	
	/*
	 * Constructor
	 * */
	public CallWSBO()
	{		
		try {
				
				restTemplate = new RestTemplate();
				ClientHttpRequestFactory factory = restTemplate.getRequestFactory();

		        if ( factory instanceof SimpleClientHttpRequestFactory)
		        {
		            ((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 15 * 1000 );
		            ((SimpleClientHttpRequestFactory) factory).setReadTimeout( 15 * 1000 );
		            LOG.info("Inicializando rest template");
		        }
		        else if ( factory instanceof HttpComponentsClientHttpRequestFactory)
		        {
		            ((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 15 * 1000);
		            ((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 15 * 1000);
		            LOG.info("Inicializando rest template");
		        }
		        restTemplate.setRequestFactory( factory );
		        headers.setContentType(MediaType.APPLICATION_JSON);
			
		} catch (Exception e) {
			LOG.error("Exception en Constructor EnviaPushIonicCallWSBO",e);
		}		
		
	}
	
	
	/**
	 * Metodo que obtiene el valor de la tabla de Parametros de UnoTV
	 * @param parIdParametro, id del parametros
	 * @return String, valor del parametros
	 * */
	public String _callObtieneParametro(String parIdParametro, ParametrosDTO parametrosDTO) throws CallWSBOException
	{
		
		LOG.debug("Inicia _callObtieneParametro");	
		String stRutaWS ="";
		try {
			stRutaWS = parametrosDTO.getWs_url_base()+parametrosDTO.getWs_metodo_getParameter();							
			return restTemplate.postForObject(stRutaWS,parIdParametro, String.class);			
		} catch (Exception e) {	
			LOG.error("Exception _callObtieneParametro URL:"+stRutaWS);		
			LOG.error("Exception en _callObtieneParametro:",e);		
			throw new CallWSBOException(e.getMessage());
		}	
	}
	
	
	/**
	 * Metodo que obtiene las notas de un magazine
	 * @param magazineId, id del magazine
	 * @return ResponseNotaDTO, notas para el magazine
	 * */
	public ResponseNotaDTO _notaByMagazine(String magazineId, ParametrosDTO parametrosDTO) throws CallWSBOException
	{		
		LOG.debug("Inicia _notaByMagazine");	
		String stRutaWS ="";
		String magazineNumRegistros ="5";
		try {
			stRutaWS = parametrosDTO.getDominio_wsd()+parametrosDTO.getWs_metodo_notaByMagazine();
			HttpEntity<String> entity = new HttpEntity<String>("",headers);
			return restTemplate.postForObject(stRutaWS.replace("{idMagazine}", magazineId).replace("{numRegistros}", magazineNumRegistros), entity, ResponseNotaDTO.class);
		
		} catch (Exception e) {	
			LOG.error("Exception _notaByMagazine URL:"+stRutaWS);		
			LOG.error("Exception en _notaByMagazine:",e);		
			throw new CallWSBOException(e.getMessage());
		}	
	}
	
	
	
	
	


	
}//FIN CLASE
