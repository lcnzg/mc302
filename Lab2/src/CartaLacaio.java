
public class CartaLacaio {

	private int ID;
	private String nome;
	private int ataque;
	private int vidaAtual;
	private int vidaMaxima;
	private int custoMana;
	
	// Metodo Construtor padrao
	public CartaLacaio(int ID, String nome, int ataque, int vida, int mana) {
		
		this.ID = ID;
		this.nome = nome;
		this.ataque = ataque;
		this.vidaAtual = vida;
		this.vidaMaxima = vida;
		this.custoMana = mana;
	}
	
	// Construtor reduzido
	public CartaLacaio(int ID, String nome, int mana) {
		
		this.ID = ID;
		this.nome = nome;
		this.custoMana = mana;
	}
	
	// Construtor copia
	public CartaLacaio(CartaLacaio lacaio) {
		
		this.ID = lacaio.ID;
		this.nome = lacaio.nome;
		this.ataque = lacaio.ataque;
		this.vidaAtual = lacaio.vidaAtual;
		this.vidaMaxima = lacaio.vidaMaxima;
		this.custoMana = lacaio.custoMana;
	}
	

	// Metodos gets e sets
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getVidaAtual() {
		return vidaAtual;
	}

	public void setVidaAtual(int vidaAtual) {
		this.vidaAtual = vidaAtual;
	}

	public int getVidaMaxima() {
		return vidaMaxima;
	}

	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
	}

	public int getCustoMana() {
		return custoMana;
	}

	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}
	
	// Metodo que buffa o nome
	private static void alteraNomeFortalecido(CartaLacaio carta){
		
		carta.setNome(carta.getNome() + " Buffed");		
	}
	
	// Metodos buffar
	
	public void buffar(int a){
		
		if (a>0){
			this.ataque += a;
			this.vidaAtual += a;
			
			alteraNomeFortalecido(this);
		}		
	}
	
	public void buffar(int a, int v){
		
		if (a>0){
			this.ataque += a;		
		}
		
		if (v>0){			
			this.vidaAtual += v;	
		}
				
		if (v>0 || a>0){
		alteraNomeFortalecido(this);
		}
	}
	

@Override
	public String toString() {
		
		String out = getNome()+" (ID: "+getID()+")\n";
		out = out + "Ataque = "+getAtaque()+"\n";
		out = out + "Vida Atual = "+getVidaAtual()+"\n";
		out = out + "Vida Maxima = "+getVidaMaxima()+"\n";
		out = out + "Custo de Mana = "+getCustoMana()+"\n";
		
		return out;
				
	}
}
