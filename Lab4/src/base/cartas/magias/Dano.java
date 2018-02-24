package base.cartas.magias;

import java.util.UUID;

import base.Carta;
import base.cartas.Lacaio;

public class Dano extends Magia {

	private int dano;

	public Dano(UUID id, String nome, int custoMana, int dano) {
		super(id, nome, custoMana);

		this.dano = dano;
	}

	public Dano(String nome, int custoMana, int dano) {
		super(nome, custoMana);

		this.dano = dano;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	@Override
	public String toString() {
		return super.toString() + "Dano: " + getDano() + "\n";
	}

	public void usar(Carta alvo) {

		((Lacaio) alvo).setVidaAtual(((Lacaio) alvo).getVidaAtual() - getDano());

		if (((Lacaio) alvo).getVidaAtual() < 0) {
			((Lacaio) alvo).setVidaAtual(0);
		}
	}

}
