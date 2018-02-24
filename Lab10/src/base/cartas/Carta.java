package base.cartas;

import java.util.UUID;

public abstract class Carta {

	private UUID id;
	private String nome;
	private int custoMana;

	public Carta(String nome, int custoMana) {

		this.nome = nome;
		this.custoMana = custoMana;
		this.id = UUID.randomUUID();
	}

	public Carta(UUID id, String nome, int custoMana) {

		this(nome, custoMana);
		this.id = id;
	}

	public int getCustoMana() {
		return custoMana;
	}

	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}

	public UUID getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {

		return "\n" +getNome() + " (ID: " + getId() + ")\n" + "Custo de Mana: " + getCustoMana() + "\n";
	}

	// Metodo abstrato
	public abstract void usar(Carta alvo);

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Carta))
			return false;
		Carta c = (Carta) obj;
		if (nome == null || c.nome == null)
			return false;

		return id == c.id && nome.equals(c.nome) && custoMana == c.custoMana;
	}

	@Override
	public int hashCode() {

		int hash = custoMana * 3;
		hash += id.hashCode() * 7;
		hash += nome.hashCode() * 9;

		return hash;
	}
}