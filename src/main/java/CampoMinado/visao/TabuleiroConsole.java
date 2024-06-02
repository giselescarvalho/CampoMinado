package CampoMinado.visao;

import CampoMinado.excecao.ExplosaException;
import CampoMinado.excecao.SairException;
import CampoMinado.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {

    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;

        executarJogo();

    }

    private void executarJogo() {
        try {
            boolean continuar = true;
            while (continuar){
                loopDoJogo();
                System.out.println("outra partida? ** S/n ** ");
                String resposta = entrada.nextLine();

                if("n".equalsIgnoreCase(resposta)){
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                }

            }
        } catch (SairException ex){
            System.out.println("Tchau ;;;;;");
        } finally {
            entrada.close();
        }
    }

    private void loopDoJogo() {
        try {
            while (!tabuleiro.objetivoAlcancado()){
                System.out.println(tabuleiro.toString());

                String digitado = capturarValorDigitado("Digite (x, y) : ");


                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim()))
                        .iterator();

                digitado = capturarValorDigitado((" 1 - Abrir \nou 2 - (Des)marcar"));
                if("1".equals(digitado)){
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if("2".equals(digitado)){
                    tabuleiro.alternarMarcacao(xy.next(), xy.next());
                }
            }

            System.out.println("Você ganhou ");
        } catch (ExplosaException exp){
            System.out.println(tabuleiro);
            System.out.println("Você perdeu");
        }
    }

    private String capturarValorDigitado (String texto){
        System.out.println(texto);
        String digitado = entrada.nextLine();

        if("sair".equalsIgnoreCase(digitado)){
            throw new SairException();
        }

        return digitado;

    }
}
