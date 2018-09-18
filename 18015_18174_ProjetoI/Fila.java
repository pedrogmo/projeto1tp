import java.lang.reflect.*;

public class Fila<X> implements Cloneable
//versão com loop
{
	private Object[] vetor;
	private int qtd;

	private X meuCloneDeX(X x)
	{
		//fazer: return (X)x.clone();
		X ret = null;
		try{
			Class<?> classe = x.getClass(); //classe String é guardada dentro da variável
			Class<?>[] tiposDoParametroFormal; //parâmetro formal é declarado na hora de implementar um método
			tiposDoParametroFormal = null; //vetor nulo, porque clone não tem parâmetros
			Method metodo = classe.getMethod("clone", tiposDoParametroFormal);
			Object[] parametrosReais = null;
			ret = (X)metodo.invoke(parametrosReais);
		}
		catch(InvocationTargetException erro){}
		catch(NoSuchMethodException erro2){}
		catch(IllegalAccessException erro3){}
		return ret;
	}

	public Fila (int capacidade) throws Exception
	{
		if (capacidade < 0)
			throw new Exception("Capacidade inválida");
		this.vetor = new Object[capacidade];
		this.qtd = 0;
	}

	public Fila (Fila modelo) throws Exception
	{
		if (modelo == null)
			throw new Exception("Modelo ineXistente");
		this.qtd = modelo.qtd;
		this.vetor = new Object[modelo.vetor.length];
		for (int i = 0; i < modelo.qtd; i++)
			this.vetor[i] = modelo.vetor[i];
	}

	public void guarde(X h) throws Exception
	{
		if (h == null)
			throw new Exception("Horário inválido!");
		if (this.isCheia())
			throw new Exception("Fila cheia!");
		if (h instanceof Cloneable)
			this.vetor[qtd] = this.meuCloneDeX(h);
		else
			this.vetor[qtd] = h;
		this.qtd++;
	}

	public void jogueForaUmItem() throws Exception
	{
		if (this.isVazia())
			throw new Exception("Nada a ser jogado fora");
		this.qtd--;
		//this.vetor[qtd] = null;
		for(int i = 0; i < this.qtd; i++)
			this.vetor[i] = this.vetor[i+1];
			//"passinho"
			//o que está além do qtd é considerado lixo
	}

	public X getUmItem() throws Exception
	{
		if (this.isVazia())
			throw new Exception("Nada a recuperar");
		if (this.vetor[0] instanceof Cloneable)
			return this.meuCloneDeX((X)this.vetor[0]);
		else
			return (X)this.vetor[0];
	}

	public boolean isVazia()
	{
		return this.qtd == 0;
	}

	public boolean isCheia()
	{
		return this.qtd == this.vetor.length;
	}

	public String toString()
	{
		if (this.isVazia())
			return "Fila vazia";
		return "Fila com " + this.qtd + " itens, sendo o primeiro: '" + this.vetor[0].toString() + "'.";
	}

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

	public int hashCode()
	{
		int ret = 1;
		ret = ret * 2 + new Integer(this.qtd).hashCode();
		for (int i = 0; i < this.qtd; i++)
			ret = ret * 2 + this.vetor[i].hashCode();
		return ret;
	}

	public Object clone()
	{
		Fila<X> ret = null;
		try{ret = new Fila<X>(this);}
		catch(Exception erro){}
		return ret;
	}
}