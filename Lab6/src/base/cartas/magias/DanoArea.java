package base.cartas.magias;

import java.util.List;
import java.util.UUID;

import base.Carta;
import base.cartas.Lacaio;

public class DanoArea extends Dano {

	public DanoArea(UUID id, String nome, int custoMana, int dano) {
		super(id, nome, custoMana, dano);

	}

	public DanoArea(String nome, int custoMana, int dano) {
		super(nome, custoMana, dano);

	}

	@Override
	public String toString() {
		return super.toString();
	}

	public void usar(List<Carta> alvos) {

		for (int i = 0; i < alvos.size(); i++) {

			((Lacaio) alvos.get(i)).setVidaAtual(((Lacaio) alvos.get(i)).getVidaAtual() - getDano());

			if (((Lacaio) alvos.get(i)).getVidaAtual() < 0) {
				((Lacaio) alvos.get(i)).setVidaAtual(0);
			}
		}

	}

}
