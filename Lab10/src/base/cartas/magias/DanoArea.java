package base.cartas.magias;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import base.cartas.Carta;
import base.cartas.Lacaio;
import io.Escritor;
import io.ILaMaSerializable;

public class DanoArea extends Dano implements ILaMaSerializable{

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
	
	@Override
	public void escreveAtributos(Escritor fw) throws IOException {
		fw.escreveDelimObj("DanoArea");
		fw.escreveAtributo("ID", getId().toString());
		fw.escreveAtributo("Nome", getNome());
		fw.escreveAtributo("Custo de Mana", String.valueOf(getCustoMana()));
		fw.escreveAtributo("Dano", String.valueOf(getDano()));
		fw.escreveDelimObj("DanoArea");
		fw.finalizar();
	}
}