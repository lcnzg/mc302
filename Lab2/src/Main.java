
public class Main {

	public static void main(String[] args) {
				
		// Instanciando objetos		
		CartaLacaio lac1 = new CartaLacaio(1, "Frodo Bolseiro", 2, 1, 1);
		CartaLacaio lac2 = new CartaLacaio(2, "Aragorn", 5, 7, 6);
		CartaLacaio lac3 = new CartaLacaio(3, "Legolas", 8, 4, 6);
		CartaMagia mag1 = new CartaMagia(4, "You shall not pass", 4, true, 7);
		CartaMagia mag2 = new CartaMagia(5, "Telecinese", 3, false, 2);
		
		
		// 1. Instanciamento reduzido
		System.out.println("*Tarefa 1");
		CartaLacaio lac4 = new CartaLacaio(6, "Joao", 36);
		System.out.println("Lacaio 4:\n"+lac4);
			
	
		// 2. Lac3->Lac1
		System.out.println("*Tarefa 2");
		lac1.setAtaque(lac3.getAtaque());
		System.out.println("Lacaio 1:\n"+lac1);
	
			
		// 3. comparacao de toString
		 System.out.println("*Tarefa 3");
		 System.out.println(mag2);

	
		// 4. Lac5 por copia
		System.out.println("*Tarefa 4");
		CartaLacaio lac5 = new CartaLacaio(lac2);
		System.out.println("Lacaio 2:\n"+lac2);
		System.out.println("Lacaio 5:\n"+lac5);

		
		// 5. Public vs. Private
		System.out.println("*Tarefa 5");
		//System.out.println(mag1.nome);
		System.out.println(mag1.getNome());
		

		// 6. Buffando as cartas
		System.out.println("*Tarefa 6");
		lac1.buffar(-2);
		System.out.println("Lacaio 1:\n"+lac1);
		lac1.buffar(2);
		System.out.println("Lacaio 1:\n"+lac1);
		lac2.buffar(-1, 1);
		System.out.println("Lacaio 2:\n"+lac2);
		lac3.buffar(4, 5);
		System.out.println("Lacaio 3:\n"+lac3);	
		
	}

}
