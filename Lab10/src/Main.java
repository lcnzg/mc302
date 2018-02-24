import java.io.IOException;
import java.util.Random;


import base.service.impl.CartaServiceImpl;
import io.Escritor;
import io.ILaMaSerializable;
import io.Leitor;

public class Main {

	public static void main(String[] args) {
		
		ILaMaSerializable obj;
		obj = (ILaMaSerializable) (new CartaServiceImpl()).geraCartaAleatoria(new Random(), 6, 7, 8, null);
		
		try {
			//obj.escreveAtributos(new Escritor());
			
			System.out.println(new Leitor().leObjetos());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}