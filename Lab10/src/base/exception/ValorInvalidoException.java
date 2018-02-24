package base.exception;

@SuppressWarnings("serial")
public class ValorInvalidoException extends Exception{
	
	public ValorInvalidoException(String mensagem){
		super(mensagem);
	}	
}