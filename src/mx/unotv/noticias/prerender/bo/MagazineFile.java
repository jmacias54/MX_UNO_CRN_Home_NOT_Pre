package mx.unotv.noticias.prerender.bo;

import java.util.List;

import mx.unotv.noticias.prerender.bo.exception.MagazineFileException;
import mx.unotv.noticias.prerender.dto.NotaDTO;
import mx.unotv.noticias.prerender.dto.ParametrosDTO;
import mx.unotv.noticias.prerender.utils.CodificaCaracteres;

import org.apache.log4j.Logger;

public class MagazineFile {
	
	//LOG
	private Logger LOG = Logger.getLogger(this.getClass().getName());
	

	/**
	 * Metodo que genera el HTML de un Magazine
	 * @param List<NotaDTO>
	 * @param ParametrosDTO
	 * @throws MagazineFileException
	 * */
	public String getHTMLMagazine(List<NotaDTO> lista, ParametrosDTO parametrosDTO) throws MagazineFileException
	{			
			LOG.debug("Inicia getHTMLMagazine en MagazineFile");			
			String html = parametrosDTO.getMagazineHtml();
			String htmlItemPatron = parametrosDTO.getMagazineHtmlItem();
			String htmlItems = "";

			try 
				{
				
				if(lista != null && !lista.isEmpty())
				{
					
					for(int i=0; i<lista.size(); i++){
						
						NotaDTO dto = lista.get(i);
						
						//copia del htmlItem que se modifica con cada nota durante la iteracion
						String htmlItemTemp = htmlItemPatron;
												
						htmlItemTemp = htmlItemTemp.replace("$ID_PARENT$", CodificaCaracteres.cambiaCaracteres(dto.getFcIdCategoria()));						
						htmlItemTemp = htmlItemTemp.replace("$NOMBRE_PARENT$", CodificaCaracteres.cambiaCaracteres(dto.getFcCategoriaDescripcion()));						
						htmlItemTemp = htmlItemTemp.replace("$TITULO_NOTA$", CodificaCaracteres.cambiaCaracteres(dto.getFcTitulo()));																														
						htmlItemTemp = htmlItemTemp.replace("$URL_IMAGEN_PRINCIPAL_NOTA$", dto.getFcImagenPrincipal());						
						htmlItemTemp = htmlItemTemp.replace("$PIE_IMAGEN$", CodificaCaracteres.cambiaCaracteres(dto.getFcImagenPie()));
						
						//Tratamiento de url de la nota
						if(dto.getFcMagazineUrlNota() == null || dto.getFcMagazineUrlNota().equals("")){
							htmlItemTemp = htmlItemTemp.replace("$URL_NOTA$", "/portal/unotv");
						}
						else{
							htmlItemTemp = htmlItemTemp.replace("$URL_NOTA$", dto.getFcMagazineUrlNota());
						}
						
						//[INI] Tratamiento tipo nota
						String[] listTiposNotaConfig = parametrosDTO.getTipoNotaConfig().replaceAll("\\[", "").split("\\]");
						for(int j=0; j<listTiposNotaConfig.length; j++){
							String[] listTipoNotaConfig = listTiposNotaConfig[j].split("\\|");
							
							if(listTipoNotaConfig.length > 1){
								String tipoNotaId = listTipoNotaConfig[0]; // ej: galeria
								String tipoNotaValor = listTipoNotaConfig[1]; // <i class="fa fa fa-camera-retro"></i>
								
								if(dto.getFcIdTipoNota().equals(tipoNotaId)){
									htmlItemTemp = htmlItemTemp.replace("$TIPO_NOTA$", tipoNotaValor);
								}
							}
						}
						
						//En caso de que no se haya encontrado valor para el tipo de nota
						htmlItemTemp = htmlItemTemp.replace("$TIPO_NOTA$", "");
						//[FIN] Tratamiento tipo nota
						
						
						
						
						
						htmlItems += htmlItemTemp.replace("/portal/unotv", "");						
					}

					html = html.replace("$ALL_ITEMS$", htmlItems);					
				}
				
				else
				{					
					html = html.replace("$ALL_ITEMS$", htmlItems);
					LOG.debug("getMagazine.lista: vacio");
				}			
				return html;
			} catch (Exception e) {
				LOG.error("Exception en getHTMLMagazine: ",e);
				throw new MagazineFileException(e.getMessage());
			}						
		}

}//FIN CLASE
