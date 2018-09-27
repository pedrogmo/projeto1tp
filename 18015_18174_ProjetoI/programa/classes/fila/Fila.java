package classes.fila;
import java.lang.reflect.*;

/**
* A classe Fila representa uma fila, que tem como base um vetor genérico interno e um inteiro indicador de quantidade de itens.
* Instâncias da classe podem guardar, jogar fora e retornar itens armazenados, de uma classe qualquer, representada por "X".
* Além disso, há métodos boolean que indicam se a fila está vazia e cheia.
* @author Pedro Gomes Moreira e Gustavo Henrique de Meira.
* @since 2018.
*/
public class Fila<X> implements Cloneable
{
	/**
	* Vetor genérico que guarda objetos da classe X especificada na instanciação.
	*/
	protected Object[] vetor;

	/**
	* Inteiro que indica quantidade de itens armazenados, para manutenção do vetor.
	*/
	protected int qtd;

	/**
	* O método meuCloneDeX() é responsável por clonar um objeto do vetor para armazená-lo e retorná-lo, evitando conflitos de edereços de memória.
	* O functionamento se baseia em guardar a classe dos parâmetros para o método "clone" da classe X ser invocado.
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
	* Constrói uma instância da classe Fila.
	* Para tanto, é passado como parâmetro o inteiro com a capacidade do vetor interno.
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
	* Constrói uma cópia da instância de Fila especificada.
	* Para tanto, é passada como parâmetro a Fila de modelo a ser copiada.
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
	* Guarda um objeto da classe X na última posição do vetor, aumentando a variável de quantidade de itens armazenados.
	* @param o objeto da classe X a ser guardado.
    	* @throws Exception se o item a ser guardado for nulo e se a Fila estiver cheia.
	*/
	public void guarde(X h) throws Exception
	{
		if (h == null)
			throw new Exception("Parametro invalido!");
		if (this.isCheia())
			throw new Exception("Fila cheia!");
		if (h instanceof Cloneable)
			this.vetor[qtd] = this.meuCloneDeX(h);
		else
			this.vetor[qtd] = h;
		this.qtd++;
	}

	/**
	* Joga fora o item da primeira posição do vetor, reduzindo a quantidade de itens.
	* Faz tudo que está na frente da primeira posição ir para trás, com um loop.
	* @throws Exception se a fila está vazia, não haveria itens para serem jogados fora.
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
	* Obtem um valor da classe X armazenado na primeira posição do vetor inteiro.
	* @return valor da posição 0 do vetor.
	* @throws Exception se a fila está vazia.
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
	* Verifica se a fila está vazia.
	* @return true se a fila está vazia (quantidade de itens é 0), false caso contrário.
	*/
	public boolean isVazia()
	{
		return this.qtd == 0;
	}

	/**
	* Verifica se a fila está cheia.
	* @return true se a fila está cheia (quantidade de itens é igual ao tamanho), false caso contrário.
	*/
	public boolean isCheia()
	{
		return this.qtd == this.vetor.length;
	}

	/**
    	* Gera uma string com texto que representa a instância da fila.
    	* Retorna a quantidade de itens guardados e o primeiro item da fila.
    	* @return uma string com o conteúdo da fila.
    	*/
	public String toString()
	{
		if (this.isVazia())
			return "Fila vazia";
		return "Fila com " + this.qtd + " itens, sendo o primeiro: '" + this.vetor[0].toString() + "'.";
	}

	/**
	* Verifica se a fila atual é igual ao objeto passado por parâmetro.
	* Compara as classes do objeto, verifica a quantidade de itens dos dois e o vetor de dados.
	* @return true se as filas forem iguais, false caso contrário.
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
	* Gera o código de espalhamento (código de hash) da instância atual de Fila, que chama o método.
	* @return o código de espalhamento da fila que chama o método.
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
	* Clona e retorna a instância atual de fila.
	* Instancia uma nova fila com o construtor de cópia, passando como parâmetro a fila atual.
	* @return o clone da fila que chama o método.
	*/
	public Object clone()
	{
		Fila<X> ret = null;
		try{ret = new Fila<X>(this);}
		catch(Exception erro){}
		return ret;
	}
}