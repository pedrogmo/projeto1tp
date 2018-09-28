package classes.labirinto;
import classes.coordenada.*;
import classes.pilha.*;
import classes.fila.*;

/**
* A classe Labirinto, apresenta métodos para a resolução de um labirinto.
* O labirinto trabalha a partir da leitura de um arquivo texto onde # representa obstaculos, E entrada, S saída e os espaços em branco são possiveis caminhos.
* @author Pedro Gomes Moreira e Gustavo Henrique de Meira.
* @since 2018.
*/
public class Labirinto implements Cloneable
{
	/**
     * Matriz de char que representa o labirinto.
    */
	protected char[][] matriz;
    /**
     * Pilha de Coordenadas que armazena o caminho feito para resolver o labirinto.
    */
	protected Pilha<Coordenada> caminho;
    /**
     * Pilha de Filas de Coordenadas que armazena as possibilidades de locomoção dentro do labirinto em cada uma das filas.
    */
	protected Pilha<Fila<Coordenada>> possibilidades;
    /**
     * Coordenada atual representa a posição atual no labirinto.
    */
	protected Coordenada atual;
    /**
     * Fila de coordenadas que guarda possibilidades de locomoção a partir de um determinado espaço.
    */
	protected Fila<Coordenada> fila;
    /**
     * achouSaida controla se foi possível encontrar a saída.
    */
	protected boolean achouSaida;
    /**
     * passouRegressivo verifica se passou pelo modo regressivo.
    */
	protected boolean passouRegressivo;
    /**
     * colunas é o número de colunas do labirinto.
    */
	protected int colunas;
    /**
     * linhas é o número de linhas do labirinto.
    */
	protected int linhas;

    /**
     * Constrói uma instância para Labirinto.
     * Para tanto, são passados parâmetros da matriz a ser usada, linha e coluna.
     * @param matriz a matriz a ser utilizada.
     * @param linha a quantidade de linhas da matriz.
     * @param coluna a quantidade de colunas da matriz.
     * @throws Exception se matriz for null ou se linha/coluna for(em) null.
     */
	public Labirinto(char[][] m,int l,int c) throws Exception
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
	
	/**
     * Através do uso de procurarEntrada(), progressivo(),regressivo(),procurarAdjacentes(),haParaOndeIr() verifica o caminho a ser feito até a saída.
     * @throws Exception se possibilidades estiver vazia.
    */
	public void procurarSaida() throws Exception
	{
		procurarEntrada();
		while(!this.achouSaida)
		{
			if (!this.passouRegressivo)
				procurarAdjacentes();
			else
				this.passouRegressivo = false;

			if (haParaOndeIr())
				progressivo();
			else
				regressivo();
		}
	}
	
	/**
     * Percorre o Labirinto a fim de encontrar a entrada.
     * @throws Exception se atual for null, ou seja não foi encontrada a entrada.
    */
	protected void procurarEntrada() throws Exception
	{
		for (int l = 0; l < this.linhas; l++)
			for (int c = 0; c < this.colunas; c++)
				if (c == 0 || l == 0 || c == this.colunas - 1 || l == this.linhas - 1)
				{
					if (this.matriz[l][c] == 'E')
						this.atual = new Coordenada(l,c);
				}
		if (this.atual == null)
				throw new Exception("Caractere 'E' do início do labirinto não econtrado.");
	}

	/**
     * Caminha pelo labirinto em uma direção constante, se há para onde ir
	 * @throws Exception se algum atributo da classe também lançar alguma exceção
    */
	protected void progressivo() throws Exception
	{
		this.atual = fila.getUmItem();
		this.fila.jogueForaUmItem();
		int lPasso = atual.getLinha();
		int cPasso = atual.getColuna();
		if (this.matriz[lPasso][cPasso] == 'S') //achou saida
			this.achouSaida = true;
		else //nao e a saida, e so um espaco em branco. Tem que dar passo
		{
			this.matriz[lPasso][cPasso] = '*';
			this.caminho.guarde(this.atual);
			this.possibilidades.guarde(this.fila);
		}
	}

	/**
     * Manuseia as possibilidades de caminho para progredir, e recua passos enquanto não há possibilidades anteriores de movimento.
     * @throws Exception se a pilha de possibilidades estiver vazia.
    */
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

	/**
     * Ordena Pilha de coordenadas de forma que o caminho é do primeiro ao último passo.
	 * @throws Exception se o labirinto ainda não tiver sido concluído.
    */
	public Pilha<Coordenada> getInversoDeCaminho() throws Exception
	{
		if (!this.achouSaida)
			throw new Exception("Labirinto ainda não foi finalizado");
		Pilha<Coordenada> ret = new Pilha<Coordenada>(this.colunas * this.linhas);
		while (!this.caminho.isVazia())
		{
			ret.guarde(this.caminho.getUmItem());
			this.caminho.jogueForaUmItem();
		}
		return ret;
	}

	/**
     * Verifica se a fila não está vazia.
    */
	protected boolean haParaOndeIr()
	{
		return !this.fila.isVazia();
	}

	/**
     * Retorna a coordenada da saida
     * @throws Exception se ao fim do processo não foi encontrada a saída.
    */
	public Coordenada getSaida() throws Exception
	{
		if (!this.achouSaida)
			throw new Exception("Saída não encontrada");
		return this.atual;
	}

	/**
     * Guarda em fila os possíveis próximos passos a partir da coordenada atual.
	 * @throws Exception se algum método possivelmente lançar exceção.
    */
	protected void procurarAdjacentes() throws Exception
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

	/**
     * Gera uma string com texto que representa o labirinto.
     * Retorna o labirinto até momento.
     * @return uma string do labirinto.
    */
	public String toString()
	{
		String ret = "";
		for (int l = 0; l < this.linhas; l++)
		{
			for (int c = 0; c < this.colunas; c++)
				ret += this.matriz[l][c] + " ";
			ret += "\r\n";
		}
		return ret;
	}

	/**
     * Verifica se o Labirinto atual é igual ao objeto passado por parâmetro.
     * Compara as classes do objeto, verifica a quantidade de linhas e colunas dos dois e os caracteres em cada posição.
     * @return true se os Labirintos forem iguais, false caso contrário.	
    */
	public boolean equals(Object obj)
	{
		if (this == obj)
		    return true;
		if (obj == null)
		    return false;
		if (this.getClass()!=obj.getClass())
		    return false;

		Labirinto labOutro = (Labirinto) obj;
		
		if (this.linhas != labOutro.linhas)
		   	return false;	
		if (this.colunas != labOutro.colunas)
			return false;
		if (!this.caminho.equals(labOutro.caminho))
			return false;
		if (!this.possibilidades.equals(labOutro.possibilidades))
			return false;
		if (!this.fila.equals(labOutro.fila))
			return false;
		if (!this.atual.equals(labOutro.atual))
			return false;		

		for(int l = 0; l < this.linhas; l++)
			for(int c  = 0; c < this.colunas; c++)
				if(this.matriz[l][c] != labOutro.matriz[l][c])
					return false;

		return true;
	}

	/**
	 * Gera o código de espalhamento (código de hash) da instância atual de Labirinto, que chama o método.
	 * @return o código de espalhamento do Labirinto que chama o método.    
    */
	public int hashCode()
	{
		int ret = 1;

		ret = ret * 2 + new Integer(this.linhas).hashCode();
		ret = ret * 2 + new Integer(this.colunas).hashCode();
		for(int l = 0; l < this.linhas; l++)
			for(int c  = 0; c < this.colunas; c++)
					ret = ret * 2 + new Character(matriz[l][c]).hashCode();
		ret = ret * 2 + this.fila.hashCode();
		ret = ret * 2 + this.possibilidades.hashCode();
		ret = ret * 2 + this.atual.hashCode();
		ret = ret * 2 + this.caminho.hashCode();
		ret = ret * 2 + new Boolean(this.achouSaida).hashCode();
		ret = ret * 2 + new Boolean(this.passouRegressivo).hashCode();

		return ret;
	}

	/**
	 * Constrói uma cópia da instância de Labirinto.
	 * Para tanto, é passada como parâmetro o Labirinto de modelo a ser copiado.
	 * @param modelo modelo de Labirinto.
	 * @throws Exception se o modelo passado for nulo.
	*/
	public Labirinto(Labirinto modelo) throws Exception
	{
		if (modelo == null)
			throw new Exception ("Modelo ausente");

		this.colunas = new Integer(modelo.colunas);
	    this.linhas = new Integer(modelo.linhas);
		this.matriz = new char[this.linhas][this.colunas];
		for(int l = 0; l < this.linhas; l++)
			for(int c  = 0; c < this.colunas; c++)
				this.matriz[l][c] = new Character(modelo.matriz[l][c]);
		this.caminho = modelo.caminho.clone();
		this.possibilidades = modelo.possibilidades.clone();
		this.atual = modelo.atual;
		this.fila = modelo.fila.clone();
		this.achouSaida = new Boolean(modelo.achouSaida);
		this.passouRegressivo = new Boolean(modelo.passouRegressivo);
	}

	/**
	* Clona e retorna a instância atual de Labirinto. 
	* Instancia um novo Labirinto com o construtor de cópia, passando como parâmetro o Labirinto atual.
	* @return o clone do labirinto que chama o método.
	*/
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