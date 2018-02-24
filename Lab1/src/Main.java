
public class Main {

	public static void main(String[] args) {
		
		// Instanciando objetos		
		CartaLacaio lac1 = new CartaLacaio(1, "Frodo Bolseiro", 2, 1, 1);
		CartaLacaio lac2 = new CartaLacaio(2, "Aragorn", 5, 7, 6);
		CartaLacaio lac3 = new CartaLacaio(3, "Legolas", 8, 4, 6);
		CartaMagia mag1 = new CartaMagia(4, "You shall not pass", 4, true, 7);
		CartaMagia mag2 = new CartaMagia(5, "Telecinese", 3, false, 2);

		// Impressao objetos
		System.out.println("Primeiro lacaio:\n"+lac1);
		System.out.println("Segundo lacaio:\n"+lac2);
		System.out.println("Terceiro lacaio:\n"+lac3);
		System.out.println("Primeira magia:\n"+mag1);
		System.out.println("Segunda magia:\n"+mag2);
	}
}