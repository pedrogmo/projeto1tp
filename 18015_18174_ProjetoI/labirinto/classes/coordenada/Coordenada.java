package classes.coordenada;

/**
* A classe Coordenada representa uma simples coordenada com inteiros linha e coluna, que indicam a posi��o em uma matriz.
* Inst�ncias da classe n�o podem trocar os valores originalmente definidos como par�metros do m�todo construtor.
* H� getters que retornam linha e coluna.
* @author Pedro Gomes Moreira e Gustavo Henrique de Meira.
* @since 2018.
*/
public class Coordenada
{
	/**
	* Vari�veis de linha e coluna da coordenada representam a posi��o na matriz.
	*/
	protected int linha, coluna;

   /**
	* Constr�i uma nova inst�ncia da classe Coordenada.
	* Para tanto, s�o exigidos os par�metros inteiros linha e coluna.
	* @param l a linha da coordenada.
	* @param c a coluna da coordenada.
	* @throws Exception se um dos valores passados for negativo.
	*/
	public Coordenada(int l, int c) throws Exception
	{
		if(l<0 || c<0)
			throw new Exception("Coordenada inv�lida");
	  	this.linha = l;
	  	this.coluna = c;
	}

	/**
	* Obt�m a linha da coordenada.
	* @return a linha da coordenada.
	*/
	public int getLinha()
	{
		return this.linha;
	}

	/**
	* Obt�m a coluna da coordenada.
	* @return a coluna da coordenada.
	*/
	public int getColuna()
	{
		return this.coluna;
	}

	/**
	* Gera uma string de texto com linha e coluna da coordenada dentro de par�nteses.
	* @return string com a coordenada, no formato (linha,coluna).
	*/
	public String toString()
	{
		return "(" + this.linha + "," + this.coluna + ")";
	}

	/**
	* Verifica a igualdade entre o objeto de Coordenada e um outro objeto passado por par�metro.
	* Verifica se o objeto passado � uma coordenada com mesma linha e coluna que a coordenada this,
	* retornando true se os valores de ambas forem iguais, e false caso contr�rio
	* @param obj o objeto a ser comparado com a coordenada.
	* @return true se os objetos forem iguais e false caso contr�rio.
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
	* Calcula e retorna o c�digo de espalhamento (ou c�digo de hash) da inst�ncia de Coordenada que chama o m�todo.
	* @return o c�digo de espalhamento da coordenada que chama o m�todo.
	*/
	public int hashCode()
	{
	 	 int ret = 1;

	 	 ret = ret * 2 + new Integer(this.linha ).hashCode();
	 	 ret = ret * 2 + new Integer(this.coluna).hashCode();

	 	 return ret;
	}

	/**
	* Compara duas coordenadas a fim de verificar se a atual � maior, menor ou igual � coordenada passada por par�metro.
	* Retorna 1 se a linha atual for maior que a da outra, ou se ambas forem iguais mas a coluna maior.
	* Retorna -1 se a linha atual for menor que a da outra, ou se ambas forem iguais mas a coluna menor.
	* Retorna 0 se a linha e coluna das duas coordenadas forem iguais.
	* @param outra a coordenada a ser comparada com a inst�ncia que chama o m�todo.
	* @return o inteiro que indica se this � maior, menor ou igual � outra.
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