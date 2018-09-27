import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import classes.coordenada.*;
import classes.pilha.*;
import classes.fila.*;
import classes.*;

public class Programa
{
	public static void main(String[] args)
	{
		try
		{
			//leitura do arquivo
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Digite o nome do arquivo a ser lido e sua extensao .txt: ");
			String localArquivo = teclado.readLine();
			BufferedReader arq = new BufferedReader(new FileReader(localArquivo););
			int linhas = Integer.parseInt(arq.readLine().trim());
			int colunas = Integer.parseInt(arq.readLine().trim());
			System.out.println("Linhas: " + linhas + "\nColunas: " + colunas + "\n");
			char[][] matriz = new char[linhas][colunas];
			for (int l = 0; l < linhas; l++)
			{
				String linha = arq.readLine();
				for (int c = 0; c < colunas; c++)
				{
					char valoratual = linha.charAt(c);
					System.out.print(valoratual + " ");
					matriz[l][c] = valoratual;
				}
				System.out.println();
			}
			arq.close();
			Labirinto lab = new Labirinto(matriz, linhas, colunas);
			lab.procurarSaida();

			System.out.println("Labirinto resolvido, saída em: " + lab.getSaida());
			System.out.print("Caminho: ");
			Pilha<Coordenada> inverso = lab.getInversoDeCaminho();
			while(!inverso.isVazia())
			{
				System.out.print(inverso.getUmItem() + " ");
				inverso.jogueForaUmItem();
			}
		}
		catch (Exception erro)
		{
			System.err.println(erro.getMessage());
		}
	}

	/*protected static void printaMatriz(char[][] m, int maxl, int maxc)
	{
		if (maxl * maxc < 1000) //só printa matriz se a quantidade de caracteres for menor que 1000, se não o programa não roda
		for(int l = 0; l < maxl; l++)
		{
			for(int c = 0; c < maxc; c++)
				System.out.print(m[l][c] + " ");
			System.out.println();
		}
	}*/
}