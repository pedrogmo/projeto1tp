import java.lang.reflect.*;

public class Fila<Classe> implements Cloneable
{
	private Object[] vet;
	private int qtd = 0;

	public Fila(int capacidade) throws Exception   //Não se deve colocar "<>" em cabeçalhos de métodos
	{
		if(capacidade <= 0)
			throw new Exception("Tamanho inválido");

		this.vet = new Object[capacidade];
	}

	private Classe meuCloneDeClasse(Classe classe)
	{
		Classe ret = null;
		try
		{
			Class<?> x = classe.getClass();
			Class<?>[] tipoDoParametroFormal = null;
			Method metodo = x.getMethod("clone",tipoDoParametroFormal);
			Object[] parametrosReais = null;
			ret = (Classe)metodo.invoke(parametrosReais);
		}
		catch(Exception erro)
		{}

		return ret;
	}

	public void guarde(Classe obj) throws Exception
	{
		if(this.isCheia())
			throw new Exception("Vetor cheio");

		if(obj == null)
			throw new Exception("Parâmetro é null");

		if(obj instanceof Cloneable)
			this.vet[this.qtd] = meuCloneDeClasse(obj);
		else
			this.vet[this.qtd] = obj;

		this.qtd++;
	}

	public void jogueForaUmItem() throws Exception
	{
		if(this.isVazia())
			throw new Exception("Vetor vazio");

		for(int i = 0; i < this.qtd - 1; i++)
			this.vet[i] = this.vet[i + 1];

		this.qtd--;
	}

	public boolean isCheia()
	{
		if(this.qtd != this.vet.length)
			return false;

		return true;
	}

	public boolean isVazia()
	{
		if(this.qtd != 0)
			return false;

		return true;
	}

	public String toString()
	{
		if(this.isVazia())
			return "Vazia";

		String text = "";

		for(int i = 0; i < this.qtd; i++)
			text += this.vet[i] + " ";

		return text;
	}

	public boolean equals(Object outro)
	{
		if(outro == null)
			return false;

		if(this == outro)
			return true;

		if(this.getClass() != outro.getClass())
			return false;

		Fila<Classe> out = (Fila<Classe>) outro;

		if(this.qtd != out.qtd)
			return false;

		for(int i = 0; i < this.qtd; i++)
			if(!this.vet[i].equals(out.vet[i]))
				return false;

		return true;
	}

	public int hashCode()
	{
		int ret = 666;

		ret = ret * 2 + new Integer(this.qtd).hashCode();

		for(int i = 0; i < this.qtd; i++)
			ret = ret * 3 + this.vet[i].hashCode();

		return ret;
	}

	public Fila (Fila modelo) throws Exception
	{
		if(modelo == null)
			throw new Exception("Modelo ausente");

		this.qtd = modelo.qtd;

		for(int i = 0; i < this.vet.length; i++)
			this.vet[i] = modelo.vet[i];
	}

	public Object clone()
	{
		Fila<Classe> obj = null;

		try
		{
			obj = new Fila<Classe>(this);
		}
		catch(Exception erro)
		{
		}

		return obj;
	}
}