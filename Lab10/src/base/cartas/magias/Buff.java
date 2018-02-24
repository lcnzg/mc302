package base.cartas.magias;

import java.io.IOException;
import java.util.UUID;

import base.cartas.Carta;
import base.cartas.Lacaio;
import io.Escritor;
import io.ILaMaSerializable;

public class Buff extends Magia implements ILaMaSerializable{

	private int aumentoEmAtaque;
	private int aumentoEmVida;

	public Buff(UUID id, String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
		super(id, nome, custoMana);
		this.aumentoEmAtaque = aumentoEmAtaque;
		this.aumentoEmVida = aumentoEmVida;
	}

	public Buff(String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
		super(nome, custoMana);
		this.aumentoEmAtaque = aumentoEmAtaque;
		this.aumentoEmVida = aumentoEmVida;
	}

	public int getAumentoEmAtaque() {
		return aumentoEmAtaque;
	}

	public void setAumentoEmAtaque(int aumentoEmAtaque) {
		this.aumentoEmAtaque = aumentoEmAtaque;
	}

	public int getAumentoEmVida() {
		return aumentoEmVida;
	}

	public void setAumentoEmVida(int aumentoEmVida) {
		this.aumentoEmVida = aumentoEmVida;
	}

	@Override
	public String toString() {
		return super.toString() + "Aumento em Ataque: " + getAumentoEmAtaque() + "\n" + "Aumento em Vida: "
				+ getAumentoEmVida();
	}

	public void usar(Carta alvo) {

		((Lacaio) alvo).setVidaAtual(((Lacaio) alvo).getVidaAtual() + getAumentoEmVida());
		((Lacaio) alvo).setVidaMaxima(((Lacaio) alvo).getVidaMaxima() + getAumentoEmVida());
		((Lacaio) alvo).setAtaque(((Lacaio) alvo).getAtaque() + getAumentoEmAtaque());
	}
	
	@Override
	public void escreveAtributos(Escritor fw) throws IOException {
		fw.escreveDelimObj("Buff");
		fw.escreveAtributo("ID", getId().toString());
		fw.escreveAtributo("Nome", getNome());
		fw.escreveAtributo("Custo de Mana", String.valueOf(getCustoMana()));
		fw.escreveAtributo("Aumento em Ataque", String.valueOf(getAumentoEmAtaque()));
		fw.escreveAtributo("Aumento em Vida", String.valueOf(getAumentoEmVida()));
		fw.escreveDelimObj("Buff");
		fw.finalizar();
	}
}