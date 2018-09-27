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
		Vetor interno do tipo object que armazena os valores a serem manuseados.
		*/
    	protected Object[] vetor;
    	/**
    	A quantidade de itens no vetor.
    	*/
    	protected int qtd = 0;

    	/**
    	Constroi uma nova instância da classe Pilha.
		Para tanto, deve ser fornecido um inteiro que será utilizado
		como capacidade da instância recém criada.
		@param capacidade o número inteiro a ser utilizado como capacidade.
    	@throws Exception se a capacidade for negativa.
    	*/
    	public Pilha (int capacidade) throws Exception
    	{
        	if (capacidade<0)
            		throw new Exception ("Capacidade invalida");
        	this.vetor = new Object [capacidade];
    	}

    	/**
    	Cria-se um clone do objeto a ser manuseado
    	@param x um objeto do tipo X cujo método clone será acessado
    	@return  o método já invocado
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
		Guarda o objeto na última posição do vetor
		@param s o objeto a ser guardado
		@throws Exception se o objeto for null ou se a fila estiver cheia
		*/
    	public void guarde (X s) throws Exception
    	{
		if (s==null)
			throw new Exception ("Informacao ausente");

		if (this.isCheia())
				throw new Exception ("Pilha cheia");
        	if (s instanceof Cloneable)
	        	this.vetor[this.qtd] = this.meuCloneDeX(s);
	    	else
	        	this.vetor[this.qtd] = s;
        	this.qtd++;
		}

    	/**
    	Obtém o último item da pilha
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
    	Descarta do vetor o último item
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
    	Boolean que retorna se a pilha está cheia
    	@return se a quantidade de itens é igual ao tamanho do vetor
    	*/
    	public boolean isCheia ()
    	{
        	return this.qtd==this.vetor.length;
    	}

    	/**
    	Boolean que retorna se a pilha está vazia
    	@return se a quantidade de itens é igual a 0
    	*/
    	public boolean isVazia ()
    	{
        	return this.qtd==0;
    	}

		/**
		Transforma a pilha em string para ser exibida informando o número de elementos e qual é o último
		@return retorna a Pilha em string informando o número de elementos e qual é o último
		*/
    	public String toString ()
    	{
		if (this.qtd==0)
		    return "Vazia";

		return this.qtd+" elementos, sendo o ultimo "+this.vetor[this.qtd-1];
		}
		/**
		Compara o objeto a pilha e retorna se são iguais ou não
		@param obj o objeto a comparar com a pilha
		@return true se for igual ou false se forem diferentes
		*/
		public boolean equals (Object obj)
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
		Retorna o código de espalhamento(hash)
		@return o código de hash
		*/
		public int hashCode ()
		{
			int ret=1;

			ret = ret*2 + new Integer(this.qtd).hashCode();

			for (int i=0; i<this.qtd; i++)
			//if (this.vetor[i]!=null)
					ret = ret*2 + this.vetor[i].hashCode();

			return ret;
		}
		/**
		Cria um construtor de cópia de Pilha
		@param modelo objeto do tipo pilha que receber os dados da instância atual
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
		Cria e retorna uma cópia da instância chamante do método (this).
		Utiliza o construtor de cópia para criar uma instância idêntica ao this para então retorná-la.
		para ser utilizada como modelo para a construção da nova instância.
		@return o clone da pilha que chama o método.
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