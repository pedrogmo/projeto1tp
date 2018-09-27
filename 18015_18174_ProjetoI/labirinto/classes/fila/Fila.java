package classes.fila;
import java.lang.reflect.*;

/**
* A classe Fila representa uma fila, que tem como base um vetor gen�rico interno e um inteiro indicador de quantidade de itens.
* Inst�ncias da classe podem guardar, jogar fora e retornar itens armazenados, de uma classe qualquer, representada por "X".
* Al�m disso, h� m�todos boolean que indicam se a fila est� vazia e cheia.
* @author Pedro Gomes Moreira e Gustavo Henrique de Meira.
* @since 2018.
*/
public class Fila<X> implements Cloneable
{
	/**
	* Vetor gen�rico que guarda objetos da classe X especificada na instancia��o.
	*/
	protected Object[] vetor;

	/**
	* Inteiro que indica quantidade de itens armazenados, para manuten��o do vetor.
	*/
	protected int qtd;

	/**
	* O m�todo meuCloneDeX() � respons�vel por clonar um objeto do vetor para armazen�-lo e retorn�-lo, evitando conflitos de edere�os de mem�ria.
	* O functionamento se baseia em guardar a classe dos par�metros para o m�todo "clone" da classe X ser invocado.
	* @param objeto x a ser clonado.
	* @return o clone do objeto x.
	*/
	protected X meuCloneDeX(X x)
	{
		//fazer: return (X)x.clone();
		X ret = null;
		try{
			Class<?> classe = x.getClass();
			Class<?>[] tiposDoParametroFormal;
			tiposDoParametroFormal = null;
			Method metodo = classe.getMethod("clone", tiposDoParametroFormal);
			Object[] parametrosReais = null;
			ret = (X)metodo.invoke(x, parametrosReais);
		}
		catch(InvocationTargetException erro){}
		catch(NoSuchMethodException erro2){}
		catch(IllegalAccessException erro3){}
		return ret;
	}

	/**
	* Constr�i uma inst�ncia da classe Fila.
	* Para tanto, � passado como par�metro o inteiro com a capacidade do vetor interno.
	* @param a capacidade desejada para o vetor interno.
	* @throws Exception se a capacidade especificada for negativa.
	*/
	public Fila (int capacidade) throws Exception
	{
		if (capacidade < 0)
			throw new Exception("Capacidade inv?lida");
		this.vetor = new Object[capacidade];
		this.qtd = 0;
	}

	/**
	* Constr�i uma c�pia da inst�ncia de Fila especificada.
	* Para tanto, � passada como par�metro a Fila de modelo a ser copiada.
	* @param o modelo de Fila.
	* @throws Exception se o modelo passado for nulo.
	*/
	public Fila (Fila modelo) throws Exception
	{
		if (modelo == null)
			throw new Exception("Modelo inexistente");
		this.qtd = modelo.qtd;
		this.vetor = new Object[modelo.vetor.length];
		for (int i = 0; i < modelo.qtd; i++)
			this.vetor[i] = modelo.vetor[i];
	}

	/**
	* Guarda um objeto da classe X na �ltima posi��o do vetor, aumentando a vari�vel de quantidade de itens armazenados.
	* @param o objeto da classe X a ser guardado.
    	* @throws Exception se o item a ser guardado for nulo e se a Fila estiver cheia.
	*/
	public void guarde(X h) throws Exception
	{
		if (h == null)
			throw new Exception("Paar�ametro inv�lido!");
		if (this.isCheia())
			throw new Exception("Fila cheia!");
		if (h instanceof Cloneable)
			this.vetor[qtd] = this.meuCloneDeX(h);
		else
			this.vetor[qtd] = h;
		this.qtd++;
	}

	/**
	* Joga fora o item da primeira posi��o do vetor, reduzindo a quantidade de itens.
	* Faz tudo que est� na frente da primeira posi��o ir para tr�s, com um loop.
	* @throws Exception se a fila est� vazia, n�o haveria itens para serem jogados fora.
	*/
	public void jogueForaUmItem() throws Exception
	{
		if (this.isVazia())
			throw new Exception("Nada a ser jogado fora");
		this.qtd--;
		//this.vetor[qtd] = null;
		for(int i = 0; i < this.qtd; i++)
			this.vetor[i] = this.vetor[i+1];
	}

	/**
	* Obtem um valor da classe X armazenado na primeira posi��o do vetor inteiro.
	* @return valor da posi��o 0 do vetor.
	* @throws Exception se a fila est� vazia.
	*/
	public X getUmItem() throws Exception
	{
		if (this.isVazia())
			throw new Exception("Nada a recuperar");
		if (this.vetor[0] instanceof Cloneable)
			return this.meuCloneDeX((X)this.vetor[0]);
		else
			return (X)this.vetor[0];
	}

	/**
	* Verifica se a fila est� vazia.
	* @return true se a fila est� vazia (quantidade de itens � 0), false caso contr�rio.
	*/
	public boolean isVazia()
	{
		return this.qtd == 0;
	}

	/**
	* Verifica se a fila est� cheia.
	* @return true se a fila est� cheia (quantidade de itens � igual ao tamanho), false caso contr�rio.
	*/
	public boolean isCheia()
	{
		return this.qtd == this.vetor.length;
	}

	/**
    	* Gera uma string com texto que representa a inst�ncia da fila.
    	* Retorna a quantidade de itens guardados e o primeiro item da fila.
    	* @return uma string com o conte�do da fila.
    	*/
	public String toString()
	{
		if (this.isVazia())
			return "Fila vazia";
		return "Fila com " + this.qtd + " itens, sendo o primeiro: '" + this.vetor[0].toString() + "'.";
	}

	/**
	* Verifica se a fila atual � igual ao objeto passado por par�metro.
	* Compara as classes do objeto, verifica a quantidade de itens dos dois e o vetor de dados.
	* @return true se as filas forem iguais, false caso contr�rio.
	*/
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return  false;
		Fila<X> f = (Fila<X>)obj;
		if (this.qtd != f.qtd)
			return false;
		for (int i = 0; i < this.qtd; i++)
			if (this.vetor[i] != f.vetor[i])
				return false;

		return true;
	}

	/**
	* Gera o c�digo de espalhamento (c�digo de hash) da inst�ncia atual de Fila, que chama o m�todo.
	* @return o c�digo de espalhamento da fila que chama o m�todo.
	*/
	public int hashCode()
	{
		int ret = 1;
		ret = ret * 2 + new Integer(this.qtd).hashCode();
		for (int i = 0; i < this.qtd; i++)
			ret = ret * 2 + this.vetor[i].hashCode();
		return ret;
	}

	/**
	* Clona e retorna a inst�ncia atual de fila.
	* Instancia uma nova fila com o construtor de c�pia, passando como par�metro a fila atual.
	* @return o clone da fila que chama o m�todo.
	*/
	public Object clone()
	{
		Fila<X> ret = null;
		try{ret = new Fila<X>(this);}
		catch(Exception erro){}
		return ret;
	}
}