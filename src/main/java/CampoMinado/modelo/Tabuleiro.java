package CampoMinado.modelo;

import CampoMinado.excecao.ExplosaException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;
    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void alternarMarcacao(int linha, int coluna){
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao()); //terminal
    }

    /*public void abrir(int linha, int coluna){
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.abrir()); //terminal
    }*/ //Aula 242
    public void abrir(int linha, int coluna){
        try{
            campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.abrir());
        } catch (Exception ex){
            //FIXME Ajustar implentação metodo abrir
            campos.forEach(c -> c.setAberto(true));
            throw ex;
        }
    }



    private void gerarCampos() {
        for( int l = 0; l<linhas ; l++){
            for(int col = 0; l<colunas ; col++){
                campos.add(new Campo(l, col));
            }
        }
    }

    private void associarVizinhos() {
        for (Campo c1: campos){
            for(Campo c2: campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minad = camp -> camp.isMinado();

        do{
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minad).count();
        } while (minasArmadas < minas);
    }

    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(camps -> camps.objetivoAlvancado());
    }

    public void reiniciar(){
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }



}
