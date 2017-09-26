package mx.unotv.noticias.prerender.bo.exception;

public class GeneraHomeBOException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public GeneraHomeBOException(String mensaje) {
        super(mensaje);
    }

	public GeneraHomeBOException(Throwable exception) {
        super(exception);
    }
	
    public GeneraHomeBOException(String mensaje, Throwable exception) {
        super(mensaje, exception);
    }
}
