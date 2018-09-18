
import javax.net.ssl.ExtendedSSLSession;

public class Coordenada
{
  private int linha, coluna;

  public Coordenada(int l, int c) throws Exception
  {
	  if(l<0 && c<0)
		  throw new Exception("Coordenadas inválidas");

	  else
	  {
		  if(l<0)
		  	 throw new Exception("Linha inválida");
		  if(c<0)
	         throw new Exception("Coluna inválida");
	  }

	  this.linha = l;
	  this.coluna = c;
  }

  public int getLinha()
  {
	  return this.linha;
  }

  public int getColuna()
  {
	  return this.coluna;
  }

  public String toString()
  {
	  return "(" + this.linha + "," + this.coluna + ")";
  }

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

  public int hashCode()
  {
	  int ret = 1;

	  ret = ret * 2 + new Integer(this.linha ).hashCode();
	  ret = ret * 2 + new Integer(this.coluna).hashCode();

	  return ret;
  }

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