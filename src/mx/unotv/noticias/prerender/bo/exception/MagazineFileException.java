package mx.unotv.noticias.prerender.bo.exception;

public class MagazineFileException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public MagazineFileException(String mensaje) {
        super(mensaje);
    }

	public MagazineFileException(Throwable exception) {
        super(exception);
    }
	
    public MagazineFileException(String mensaje, Throwable exception) {
        super(mensaje, exception);
    }	
	
}
