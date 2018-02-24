package base.cartas.magias;

import java.io.IOException;
import java.util.UUID;

import base.cartas.Carta;
import base.cartas.Lacaio;
import io.Escritor;
import io.ILaMaSerializable;

public class Dano extends Magia implements ILaMaSerializable{

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
		return super.toString() + "Dano: " + getDano();
	}

	public void usar(Carta alvo) {

		((Lacaio) alvo).setVidaAtual(((Lacaio) alvo).getVidaAtual() - getDano());

		if (((Lacaio) alvo).getVidaAtual() < 0) {
			((Lacaio) alvo).setVidaAtual(0);
		}
	}
	
	@Override
	public void escreveAtributos(Escritor fw) throws IOException {
		fw.escreveDelimObj("Dano");
		fw.escreveAtributo("ID", getId().toString());
		fw.escreveAtributo("Nome", getNome());
		fw.escreveAtributo("Custo de Mana", String.valueOf(getCustoMana()));
		fw.escreveAtributo("Dano", String.valueOf(getDano()));
		fw.escreveDelimObj("Dano");
		fw.finalizar();
	}
}