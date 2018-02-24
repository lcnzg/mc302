package base;

import java.util.ArrayList;

import base.cartas.Carta;
import util.Util;

public class Mesa {

	private ArrayList<Carta> maoP;
	private ArrayList<Carta> maoS;
	private ArrayList<Carta> lacaiosP;
	private ArrayList<Carta> lacaiosS;
	private int poderHeroiP;
	private int poderHeroiS;
	private int manaP;
	private int manaS;

	public Mesa() {
		maoP = new ArrayList<Carta>();
		maoS = new ArrayList<Carta>();
		lacaiosP = new ArrayList<Carta>();
		lacaiosS = new ArrayList<Carta>();

		poderHeroiP = Util.PODER_HEROI;
		poderHeroiS = Util.PODER_HEROI;

		manaP = 1;
		manaS = 1;
	}

	public ArrayList<Carta> getMaoP() {
		return maoP;
	}

	public void setMaoP(ArrayList<Carta> maoP) {
		this.maoP = maoP;
	}

	public ArrayList<Carta> getMaoS() {
		return maoS;
	}

	public void setMaoS(ArrayList<Carta> maoS) {
		this.maoS = maoS;
	}

	public ArrayList<Carta> getLacaiosP() {
		return lacaiosP;
	}

	public void setLacaiosP(ArrayList<Carta> lacaiosP) {
		this.lacaiosP = lacaiosP;
	}

	public ArrayList<Carta> getLacaiosS() {
		return lacaiosS;
	}

	public void setLacaiosS(ArrayList<Carta> lacaiosS) {
		this.lacaiosS = lacaiosS;
	}

	public int getPoderHeroiP() {
		return poderHeroiP;
	}

	public void setPoderHeroiP(int poderHeroiP) {
		this.poderHeroiP = poderHeroiP;
	}

	public int getPoderHeroiS() {
		return poderHeroiS;
	}

	public void setPoderHeroiS(int poderHeroiS) {
		this.poderHeroiS = poderHeroiS;
	}

	public int getManaP() {
		return manaP;
	}

	public void setManaP(int manaP) {
		this.manaP = manaP;
	}

	public int getManaS() {
		return manaS;
	}

	public void setManaS(int manaS) {
		this.manaS = manaS;
	}

	public void decPoderHeroi(int valor, char jogador) {

		if (jogador == 'P') {
			poderHeroiP -= valor;
		}

		if (jogador == 'S') {
			poderHeroiS -= valor;
		}
	}

	public void decMana(int valor, char jogador) {

		if (jogador == 'P') {
			manaP -= valor;
		}

		if (jogador == 'S') {
			manaS -= valor;
		}
	}

	// Carta arbitraria (sempre 0)
	public Carta sacarCarta(char jogador) {
		Carta card;

		if (jogador == 'P') {
			card = maoP.get(0);
			maoP.remove(0);
		}

		else {
			card = maoS.get(0);
			maoS.remove(0);
		}

		return card;
	}
}