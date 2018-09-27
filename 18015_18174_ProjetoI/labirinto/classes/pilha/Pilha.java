package classes.pilha;
import java.lang.reflect.*;

/**
A classe Pilha permite empilhar e manusear objetos em um conceito first in, first out.

Nela pode-se guardar, jogar fora e obter objetos
@author Pedro Gomes Moreira e Gustavo Henrique de Meira.
@since 2018.
*/
public class Pilha<X> implements Cloneable
{
	/**
	@vetor � um vetor do tipo object que armazena os valores a serem manuseados
	*/
    	protected Object[] vetor;
    	/**
    	@qtd armazena a quantidade de itens no vetor
    	*/
    	protected int qtd = 0;

    	/**
    	Constroi uma nova inst�ncia da classe Pilha.
	Para tanto, deve ser fornecido um inteiro que ser� utilizado
	como capacidade da inst�ncia rec�m criada.
	@param capacidade o n�mero inteiro a ser utilizado como capacidade.
    	@throws Exception se a capacidade for negativa.
    	*/
    	public Pilha (int capacidade) throws Exception
    	{
        	if (capacidade<0)
            		throw new Exception ("Capacidade inv�lida");
        	this.vetor = new Object [capacidade];
    	}

    	/**
    	Cria-se um clone do objeto a ser manuseado
    	@param � um objeto do tipo X cujo m�todo clone ser� acessado
    	@return  o m�todo j� invocado
    	*/
	protected X meuCloneDeX(X x)
	{
		//fazer: return (X)x.clone();
		X ret = null;
		try{
			Class<?> classe = x.getClass(); //classe String � guardada dentro da vari�vel
			Class<?>[] tiposDoParametroFormal; //par�metro formal � declarado na hora de implementar um m�todo
			tiposDoParametroFormal = null; //vetor nulo, porque clone n�o tem par�metros
			Method metodo = classe.getMethod("clone", tiposDoParametroFormal);
			Object[] parametrosReais = null;
			ret = (X)metodo.invoke(x, parametrosReais); //erro aqui, invoke tem dois par�metros
		}
		catch(InvocationTargetException erro){}
		catch(NoSuchMethodException erro2){}
		catch(IllegalAccessException erro3){}
		return ret;
	}
	/**
	Guarda o objeto na �ltima posi��o do vetor
	@param � o objeto a ser guardado
	@throws Exception se o objeto for null ou se a fila estiver cheia
	*/
    	public void guarde (X s) throws Exception
    	{
		if (s==null)
			throw new Exception ("Informacao ausente");

		if (this.isCheia())
				throw new Exception ("Pilha cheia");
        	if (s instanceof Cloneable)
	        	this.vetor[this.qtd] = this.meuCloneDeX(s);  // vai dar pau; tem que contornar
	    	else
	        	this.vetor[this.qtd] = s;
        	this.qtd++;
	}

    	/**
    	Obt�m o �ltimo item da pilha
    	@throws Exception se a pilha estiver vazia
    	*/
    	public X getUmItem () throws Exception
    	{
        	if (this.isVazia())
        	    	throw new Exception ("Nada a recuperar");
	        if (this.vetor[this.qtd-1] instanceof Cloneable)
	            return this.meuCloneDeX((X)this.vetor[this.qtd-1]);
        	return (X)this.vetor[this.qtd-1];
    	}

    	/**
    	Descarta do vetor o �ltimo item
    	@throws Exception se a pilha estiver vazia
    	*/
    	public void jogueForaUmItem () throws Exception
    	{
		if (this.isVazia())
		{
			Exception problema;
	    	problema = new Exception ("Pilha vazia");
	    	throw problema;
		}
        	this.qtd--;
        	this.vetor[this.qtd]=null;
    	}

    	/**
    	Boolean que retorna se a pilha est� cheia
    	@return se a quantidade de itens � igual ao tamanho do vetor
    	*/
    	public boolean isCheia ()
    	{
        	return this.qtd==this.vetor.length;
    	}

    	/**
    	Boolean que retorna se a pilha est� vazia
    	@return se a quantidade de itens � igual a 0
    	*/
    	public boolean isVazia ()
    	{
        	return this.qtd==0;
    	}

	/**
	Transforma a pilha em string para ser exibida informando o n�mero de elementos e qual � o �ltimo
	@return retorna a Pilha em string informando o n�mero de elementos e qual � o �ltimo
	*/
    	public String toString ()
    	{
		if (this.qtd==0)
		    return "Vazia";

		return this.qtd+" elementos, sendo o ultimo "+this.vetor[this.qtd-1];
	}
	/**
	Compara o objeto a pilha e retorna se s�o iguais ou n�o
	@param � o objeto a comparar com a pilha
	@return true se for igual ou false se forem diferentes
	*/
	public boolean equals (Object obj)//compara this e obj
	{
		if (this==obj)
		    return true;

		if (obj==null)
		    return false;

		if (this.getClass()!=obj.getClass())
		    return false;

		Pilha<X> pil = (Pilha<X>)obj;

		if (this.qtd!=pil.qtd)
		    return false;

		for (int i=0; i<this.qtd; i++)
		    if (!this.vetor[i].equals(pil.vetor[i]))
		        return false;

		return true;
	}

    	/**
    	Retorna o c�digo de espalhamento(hash)
    	@return o c�digo de hash
    	*/
	public int hashCode ()
	{
		int ret=666; // so nao pode ser zero

		ret = ret*2 + new Integer(this.qtd).hashCode();

        for (int i=0; i<this.qtd; i++)
          //if (this.vetor[i]!=null)
				ret = ret*2 + this.vetor[i].hashCode();

		return ret;
	}
	/**
	Cria um construtor de c�pia de Pilha
	@param modelo � um objeto do tipo pilha que receber� os dados da
	@throws Exception se o modelo for null
	*/
	public Pilha (Pilha modelo) throws Exception //construtor de copia
	{
		if (modelo==null)
			throw new Exception ("Modelo ausente");

		this.qtd = modelo.qtd;

		this.vetor = new Object[modelo.vetor.length];

		for (int i=0; i<=modelo.qtd; i++)
		    this.vetor[i] = modelo.vetor[i];
	}

    	/**
    	Cria e retorna uma c�pia da inst�ncia chamante do m�todo (this).
    	Utiliza o construtor de c�pia para criar uma inst�ncia id�ntica ao
    	this para ent�o retorn�-la.
    	para ser utilizada como modelo para a constru��o da nova inst�ncia
    	*/
	public Object clone ()
	{
		Pilha<X> ret=null;

		try
		{
			ret = new Pilha<X> (this);
		}
		catch (Exception erro)
		{}

		return ret;
	}
}