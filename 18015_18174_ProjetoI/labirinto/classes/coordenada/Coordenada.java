package classes.coordenada;

/**
* A classe Coordenada representa uma simples coordenada com inteiros linha e coluna, que indicam a posição em uma matriz.
* Instâncias da classe não podem trocar os valores originalmente definidos como parâmetros do método construtor.
* Há getters que retornam linha e coluna.
* @author Pedro Gomes Moreira e Gustavo Henrique de Meira.
* @since 2018.
*/
public class Coordenada
{
	/**
	* Variáveis de linha e coluna da coordenada representam a posição na matriz.
	*/
	protected int linha, coluna;

   /**
	* Constrói uma nova instância da classe Coordenada.
	* Para tanto, são exigidos os parâmetros inteiros linha e coluna.
	* @param l a linha da coordenada.
	* @param c a coluna da coordenada.
	* @throws Exception se um dos valores passados for negativo.
	*/
	public Coordenada(int l, int c) throws Exception
	{
		if(l<0 || c<0)
			throw new Exception("Coordenada inválida");
	  	this.linha = l;
	  	this.coluna = c;
	}

	/**
	* Obtém a linha da coordenada.
	* @return a linha da coordenada.
	*/
	public int getLinha()
	{
		return this.linha;
	}

	/**
	* Obtém a coluna da coordenada.
	* @return a coluna da coordenada.
	*/
	public int getColuna()
	{
		return this.coluna;
	}

	/**
	* Gera uma string de texto com linha e coluna da coordenada dentro de parênteses.
	* @return string com a coordenada, no formato (linha,coluna).
	*/
	public String toString()
	{
		return "(" + this.linha + "," + this.coluna + ")";
	}

	/**
	* Verifica a igualdade entre o objeto de Coordenada e um outro objeto passado por parâmetro.
	* Verifica se o objeto passado é uma coordenada com mesma linha e coluna que a coordenada this,
	* retornando true se os valores de ambas forem iguais, e false caso contrário
	* @param obj o objeto a ser comparado com a coordenada.
	* @return true se os objetos forem iguais e false caso contrário.
	*/
	public boolean equals (Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;

	  	if (this.getClass() != obj.getClass())
		  	return false;

	  	Coordenada cor = (Coordenada)obj;

	  	if (this.linha != cor.linha)
		 	 return false;

	  	if (this.coluna != cor.coluna)
			  return false;

	  	return true;
	}

	/**
	* Calcula e retorna o código de espalhamento (ou código de hash) da instância de Coordenada que chama o método.
	* @return o código de espalhamento da coordenada que chama o método.
	*/
	public int hashCode()
	{
	 	 int ret = 1;

	 	 ret = ret * 2 + new Integer(this.linha ).hashCode();
	 	 ret = ret * 2 + new Integer(this.coluna).hashCode();

	 	 return ret;
	}

	/**
	* Compara duas coordenadas a fim de verificar se a atual é maior, menor ou igual à coordenada passada por parâmetro.
	* Retorna 1 se a linha atual for maior que a da outra, ou se ambas forem iguais mas a coluna maior.
	* Retorna -1 se a linha atual for menor que a da outra, ou se ambas forem iguais mas a coluna menor.
	* Retorna 0 se a linha e coluna das duas coordenadas forem iguais.
	* @param outra a coordenada a ser comparada com a instância que chama o método.
	* @return o inteiro que indica se this é maior, menor ou igual à outra.
	*/
	public int compareTo(Coordenada outra)
	{
		int ret = 0;
		if (this.linha > outra.linha)
			ret = 1;
		else
			if(this.linha < outra.linha)
				ret = -1;
			else
				if (this.coluna > outra.coluna)
					ret = 1;
				else
					if (this.coluna < outra.coluna)
						ret = -1;
					else
						ret = 0;
		return ret;
	}
}