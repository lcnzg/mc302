package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import base.cartas.HabilidadesLacaio;
import base.cartas.Lacaio;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;

public class Leitor {
	private FileReader arquivo;
	private Scanner entrada;

	public Leitor() throws FileNotFoundException {
		arquivo = new FileReader("arquivo.txt");
		entrada = new Scanner(arquivo);
	}

	public List<ILaMaSerializable> leObjetos() {

		entrada = entrada.useDelimiter("\n");
		List<ILaMaSerializable> lista = new ArrayList<ILaMaSerializable>();

		while (entrada.hasNext()) {
			String tag = entrada.next();

			if (tag.equals("obj Lacaio")) {
				Lacaio obj = new Lacaio(UUID.fromString(entrada.next().substring(3)), entrada.next().substring(5),
						Integer.valueOf(entrada.next().substring(14)), Integer.valueOf(entrada.next().substring(7)),
						Integer.valueOf(entrada.next().substring(11)), Integer.valueOf(entrada.next().substring(12)),
						HabilidadesLacaio.valueOf(entrada.next().substring(11)));
				entrada.next();
				lista.add(obj);
			}

			if (tag.equals("obj Buff")) {
				Buff obj = new Buff(UUID.fromString(entrada.next().substring(3)), entrada.next().substring(5),
						Integer.valueOf(entrada.next().substring(14)), Integer.valueOf(entrada.next().substring(18)),
						Integer.valueOf(entrada.next().substring(16)));
				entrada.next();
				lista.add(obj);
			}

			if (tag.equals("obj Dano")) {
				Dano obj = new Dano(UUID.fromString(entrada.next().substring(3)), entrada.next().substring(5),
						Integer.valueOf(entrada.next().substring(14)), Integer.valueOf(entrada.next().substring(5)));
				entrada.next();
				lista.add(obj);
			}

			if (tag.equals("obj DanoArea")) {
				DanoArea obj = new DanoArea(UUID.fromString(entrada.next().substring(3)), entrada.next().substring(5),
						Integer.valueOf(entrada.next().substring(14)), Integer.valueOf(entrada.next().substring(5)));
				entrada.next();
				lista.add(obj);
			}
		}

		entrada.close();
		return lista;
	}
}
