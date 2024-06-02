package CampoMinado.modelo;

import CampoMinado.excecao.ExplosaException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int linha;
    private final int coluna;

    private boolean aberto; //por default false
    private boolean minado;
    private boolean marcado;

    private List<Campo> vizinhos = new ArrayList<>();

    public Campo(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public boolean adicionarVizinho(Campo vizinho){
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;

        } else if (deltaGeral == 2 && diagonal){
            vizinhos.add(vizinho);
            return true;

        } else {
            return false;
        }

    }

    //public para passar nos testes
    public void alternarMarcacao(){
        if(!aberto){
            marcado = !marcado;
        }
    }

    public boolean abrir(){
        if(!aberto && !marcado){
            aberto = true;
            if(minado){
                //TODO Implementar nova versao no lugar de throw new ExplosaException();
            }

            if (vizinhancaSegura()){
                vizinhos.forEach(viz -> viz.abrir());
            }

            return true;
        } else {
            return false;
        }
    }

    boolean vizinhancaSegura(){
        return vizinhos.stream()
                .noneMatch(v -> v.minado);
    }

    public void minar(){
        minado = true;
    }


    public boolean isMarcado(){
        return marcado;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public boolean isAberto(){
        return aberto;
    }
    public boolean isMinado(){
        return minado;
    }

    public int getLinha(){
        return linha;
    }

    public int getColuna(){
        return coluna;
    }

    boolean objetivoAlvancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    long minasNaVizinhanca(){
        return  vizinhos.stream().filter(vizi -> vizi.minado).count();
    }

    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
    }


}
