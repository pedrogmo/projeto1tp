import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import classes.coordenada.Coordenada;
import classes.pilha.Pilha;
import classes.fila.Fila;
import classes.Labirinto;

public class Programa
{
	public static void main(String[] args)
	{
		try
		{
			//leitura do arquivo
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Digite o nome do arquivo a ser lido: ");
			String localArquivo = teclado.readLine();
			BufferedReader arq = new BufferedReader(new FileReader(localArquivo + ".txt"));
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
			
			Coordenada saida = lab.getSaida();
			System.out.println("Labirinto resolvido, saída em: " + saida.toString());
			System.out.print("Caminho: ");
			Pilha<Coordenada> inverso = lab.getInversoDeCaminho();
			while(!inverso.isVazia())
			{
				System.out.print(inverso.getUmItem() + " ");
				inverso.jogueForaUmItem();
			}
			PrintStream resultado = new PrintStream(localArquivo + ".res.txt");
			resultado.printnln(lab.toString());
		}
		catch (Exception erro)
		{
			System.err.println(erro.getMessage());
		}
	}
}