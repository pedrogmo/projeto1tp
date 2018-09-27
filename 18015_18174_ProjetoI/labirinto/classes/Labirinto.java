package classes;
import classes.coordenada.*;
import classes.pilha.*;
import classes.fila.*;

public class Labirinto implements Cloneable
{
	protected char[][] matriz;
	protected Pilha<Coordenada> caminho;
	protected Pilha<Fila<Coordenada>> possibilidades;
	protected Coordenada atual;
	protected Fila<Coordenada> fila;
	protected boolean achouSaida;
	protected boolean passouRegressivo;
	protected int colunas;
	protected int linhas;

	public Labirinto(char[][] m, l, c) throws Exception
	{
		if (m == null)
			throw new Exception("Matriz nula");
		if (l <= 0 || c <= 0)
			throw new Exception("Colunas e/ou Linhas inválida(s)");
		this.linhas = l;
		this.colunas = c;
		this.matriz = m;
		this.caminho = new Pilha<Coordenada>(this.colunas * this.linhas);
		this.possibilidades = new Pilha<Fila<Coordenada>>(this.colunas * this.linhas);
		this.fila = new Fila<Coordenada>(3);
		this.atual = null;
		this.achouSaida = false;
		this.passouRegressivo = false;
	}

	protected void progressivo()
	{
		atual = fila.getUmItem();
		fila.jogueForaUmItem();
		int lPasso = atual.getLinha();
		int cPasso = atual.getColuna();
		if (matriz[lPasso][cPasso] == 'S') //achou saida
			this.achouSaida = true;
		else //nao e a saida, e so um espaco em branco. Tem que dar passo
		{
			this.matriz[lPasso][cPasso] = '*';
			this.caminho.guarde(this.atual);
			this.possibilidades.guarde(this.fila);
		}
	}

	protected void regressivo() throws Exception
	{
		this.passouRegressivo = true;
		boolean sairRegressivo = false;
		do
		{
			this.atual = this.caminho.getUmItem(); //desempilha um item de caminho
			this.caminho.jogueForaUmItem();
			this.matriz[this.atual.getLinha()][this.atual.getColuna()] = ' ';
			this.fila = this.possibilidades.getUmItem(); //desempilha fila de possibilidades
			this.possibilidades.jogueForaUmItem();
			if (haParaOndeIr())
				sairRegressivo = true;//afirma que saiu
			if (this.possibilidades.isVazia())
				throw new Exception("Não foi possível resolver o labirinto");
		}
		while (!sairRegressivo);
	}

	public void procurarSaida() throws Exception
	{
		while(!this.achouSaida)
		{
			if (!this.passouRegressivo())
				procurarAdjacentes();
			else
				this.passouRegressivo = false;

			if (haParaOndeIr())
				progressivo();
			else
				regressivo();
		}
	}
	public Pilha<Coordenada> getInversoDeCaminho()
	{
		Pilha<Coordenada> ret = new Pilha<Coordenada>(this.colunas * this.linhas);
		while (!this.caminho.isVazia())
		{
			ret.guarde(this.caminho.getUmItem());
			this.caminho.jogueForaUmItem();
		}
		return ret;
	}
	protected boolean haParaOndeIr()
	{
		return !this.fila.isVazia();
	}

	public Coordenada getSaida()
	{
		if (!this.achouSaida)
			throw new Exception("Saída não encontrada");
		return atual;
	}

	protected void procurarEntrada() throws Exception
	{
		for (int l = 0; l < this.linhas; l++)
			for (int c = 0; c < this.colunas; c++)
				if (c == 0 || l == 0 || c == this.colunas - 1 || l == this.linhas - 1)
				{
					char valoratual = this.matriz[l][c];
					if (valoratual == 'E')
						atual = new Coordenada(l,c);
				}
		if (atual == null)
				throw new Exception("Caractere 'E' do início do labirinto não econtrado.");
	}

	public void procurarAdjacentes()
	{
		this.fila = new Fila<Coordenada>(3);
		int cAtual = this.atual.getColuna();
		int lAtual = this.atual.getLinha();
		if (cAtual + 1 < colunas)         //para direita
			if (this.matriz[lAtual][cAtual + 1] == ' ' || this.matriz[lAtual][cAtual + 1] == 'S')
				this.fila.guarde(new Coordenada(lAtual, cAtual + 1));
		if (lAtual + 1 < linhas)          //para baixo
			if (this.matriz[lAtual + 1][cAtual] == ' ' || this.matriz[lAtual + 1][cAtual] == 'S')
				this.fila.guarde(new Coordenada(lAtual + 1, cAtual));
		if (lAtual - 1 >= 0)              //para cima
			if (this.matriz[lAtual - 1][cAtual] == ' ' || this.matriz[lAtual - 1][cAtual] == 'S')
				this.fila.guarde(new Coordenada(lAtual - 1, cAtual));
		if (cAtual - 1 >= 0)         	  //para esquerda
			if (this.matriz[lAtual][cAtual - 1] == ' ' || this.matriz[lAtual][cAtual - 1] == 'S')
				this.fila.guarde(new Coordenada(lAtual, cAtual - 1));
	}

	public String toString()
	{
		// \r e \n
	}

	public boolean equals(Object obj)
	{

	}

	public int hashCode()
	{

	}

	public Labirinto(Labirinto modelo) throws Exception
	{
		if (modelo == null)
		throw new Exception ("Modelo ausente");

		this.matriz = new Object(modelo.matriz.length, modelo.matriz[0].length);
		this.caminho = new Object (modelo.colunas * modelo.linhas);
		this.possibilidades = new Object (modelo.possibilidades.length);
		this.atual = modelo.atual;
		this.fila = new Object (modelo.fila.length);
		this.achouSaida = modelo.achouSaida;
		this.passouRegressivo = modelo.passouRegressivo;
		this.colunas = modelo.colunas;
	    this.linhas = modelo.linhas;

	    for (int i=0; i<=modelo.fila.length; i++)
		    this.fila[i] = modelo.fila[i];



	}

	public Labirinto clone()
	{
		Labirinto ret = null;
		try
		{
			ret = new Labirinto(this);

	    }
	    catch(Exception erro)
	    {}
	    return ret;


	}
}