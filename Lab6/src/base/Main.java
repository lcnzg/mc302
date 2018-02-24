package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import base.cartas.Lacaio;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;

public class Main {

	public static void main(String[] args) {

		/*
		 * ArrayList<Carta> cartas = new ArrayList<Carta>();
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.add(new Carta("Lala", 5)); }
		 * 
		 * long s = System.nanoTime();
		 * 
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.get(i); }
		 * 
		 * System.out.println("A operacao demorou " + (System.nanoTime() - s) +
		 * " ns");
		 */

		/*
		 * LinkedList<Carta> cartas = new LinkedList<Carta>();
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.add(new Carta("Lala", 5)); }
		 * 
		 * long s = System.nanoTime();
		 * 
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.get(i); }
		 * 
		 * System.out.println("A operacao demorou " + (System.nanoTime() - s) +
		 * " ns");
		 */

		/*
		 * ArrayList<Carta> cartas = new ArrayList<Carta>();
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.add(new Carta("Lala", 5)); }
		 * 
		 * long s = System.nanoTime();
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.contains(cartas.get(i)); }
		 * 
		 * System.out.println("A operacao demorou " + (System.nanoTime() - s) +
		 * " ns");
		 */

		/*
		 * LinkedList<Carta> cartas = new LinkedList<Carta>();
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.add(new Carta("Lala", 5)); }
		 * 
		 * long s = System.nanoTime();
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.contains(cartas.get(i)); }
		 * 
		 * System.out.println("A operacao demorou " + (System.nanoTime() - s) +
		 * " ns");
		 */

		/*
		 * HashSet<Carta> cartas = new HashSet<Carta>();
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.add(new Carta("Lala", 5)); }
		 * 
		 * long s = System.nanoTime();
		 * 
		 * for (Carta carta : cartas) { cartas.contains(carta); }
		 * 
		 * System.out.println("A operacao demorou " + (System.nanoTime() - s) +
		 * " ns");
		 */

		/*
		 * TreeSet<Carta> cartas = new
		 * TreeSet<Carta>(Comparator.comparing(Carta::getId));
		 * 
		 * for (int i = 0; i < 10000; i++) { cartas.add(new Carta("Lala", 5)); }
		 * 
		 * long s = System.nanoTime();
		 * 
		 * for (Carta carta : cartas) { cartas.contains(carta); }
		 * 
		 * System.out.println("A operacao demorou " + (System.nanoTime() - s) +
		 * " ns");
		 */

		Collection<Carta> cartas = new ArrayList();

		Lacaio lac1 = new Lacaio("Frodo Bolseiro", 2, 1, 1, 1);
		Lacaio lac2 = new Lacaio("Aragorn", 5, 7, 6, 6);
		Lacaio lac3 = new Lacaio("Legolas", 8, 4, 6, 6);
		Lacaio lac4 = new Lacaio("Eurico Sampaio", 9, 4, 8, 8);
		Lacaio lac5 = new Lacaio("Feliciana Garrido", 11, 9, 3, 3);
		Lacaio lac6 = new Lacaio("Miguelina Alencar", 13, 5, 7, 7);
		Lacaio lac7 = new Lacaio("Pascoal Parracho", 14, 6, 6, 6);
		DanoArea dan1 = new DanoArea("Laser", 10, 3);
		DanoArea dan2 = new DanoArea("Bomba", 4, 5);
		DanoArea dan3 = new DanoArea("Metralhadora", 6, 7);
		Dano dan4 = new Dano("Flecha", 4, 1);
		Dano dan5 = new Dano("Pistola", 8, 5);
		Dano dan6 = new Dano("Faca", 10, 3);

		Collection<Carta> lista = Arrays.asList(lac1, lac2, lac3, lac4, lac5, lac6, lac7, dan1, dan2, dan3, dan4, dan5,
				dan6);

		/*
		 * final Comparator<Carta> comp = (c1, c2) -> Integer.compare(((Lacaio)
		 * c1).getAtaque(), ((Lacaio) c2).getAtaque());
		 * 
		 * Carta fluxo = lista.stream().filter(f-> f instanceof
		 * Lacaio).max(comp).get();
		 * 
		 * System.out.println(fluxo);
		 */

		/*
		 * Integer soma = lista.stream().filter(f-> f instanceof
		 * Lacaio).mapToInt(c->((Lacaio) c).getAtaque()).sum();
		 * 
		 * System.out.println(soma);
		 */

		Collection<Carta> fluxo = lista.stream().filter(f -> f instanceof Lacaio)
				.sorted((c1, c2) -> ((Lacaio) c1).getVidaAtual() - ((Lacaio) c2).getVidaAtual())
				.collect(Collectors.toList());

		System.out.println(fluxo);

	}

}
