package base.exception;

@SuppressWarnings("serial")
public class BaralhoVazioException extends IllegalArgumentException{

	public BaralhoVazioException(String mensagem){
		super(mensagem);		
	}	
}