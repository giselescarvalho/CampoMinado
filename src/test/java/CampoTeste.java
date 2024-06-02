import CampoMinado.excecao.ExplosaException;
import CampoMinado.modelo.Campo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampoTeste {

    private Campo campo = new Campo(3,3);

    @BeforeEach
    void iniciarCampo(){

        campo = new Campo(3,3);
    }

    @Test
    void TesteVizinhoRealDistancia1(){
        Campo vizinhoEsquerda = new Campo(3,2);
        boolean resultadoEsquerda = campo.adicionarVizinho(vizinhoEsquerda);

        //Campo vizinhoDireita = new Campo(3,2);
        //boolean resultadoDireita= campo.adicionarVizinho(vizinhoDireita);

        assertTrue(resultadoEsquerda);
        //assertTrue(resultadoDireita);
    }

    @Test
    void TesteVizinhoRealDistancia1Direita(){
        Campo vizinhoDireita = new Campo(3,4);
        boolean resultadoDireita= campo.adicionarVizinho(vizinhoDireita);

        assertTrue(resultadoDireita);
    }

    @Test
    void TesteVizinhoRealDistancia1Cima(){
        Campo vizinho = new Campo(2,3);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void TesteVizinhoRealDistancia1Baixo(){
        Campo vizinho = new Campo(4,3);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void TesteVizinhoRealDistancia2(){
        Campo vizinho = new Campo(2,2);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void TesteNaoVizinhoRealDistancia(){
        Campo vizinho = new Campo(1,1);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertFalse(resultado);
    }

    @Test
    void TesteValorPadraoAtributoMarcado(){
        assertFalse(campo.isMarcado());
    }

    @Test
    void TesteAlternarMarcacao(){
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    void TesteAlternarMarcacaoDuasChamadas(){
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void TestAbrirNaoMinadoNaoMarcado(){
        assertTrue(campo.abrir()); //public
    }

    @Test
    void TestAbrirNaoMinadoMarcado(){
        campo.alternarMarcacao();
        assertFalse(campo.abrir()); //public
    }

    @Test
    void TestAbrirMinadoMarcado(){
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void TestAbrirMinadoNaoMarcado(){
        campo.minar();
        assertThrows(ExplosaException.class, () ->{
            campo.abrir();
        });

        assertFalse(campo.abrir());
    }

    @Test
    void TestAbrirComVizinhos1(){
        Campo campo11 = new Campo(1,1);
        Campo vizinhoDoVizinho1 = new Campo(2,2);

        vizinhoDoVizinho1.adicionarVizinho(campo11);
        campo11.adicionarVizinho(vizinhoDoVizinho1);

        campo.adicionarVizinho(vizinhoDoVizinho1);
        campo.abrir();

        assertTrue(campo.isAberto() && campo11.isAberto());
    }

    @Test
    void TestAbrirComVizinhos2(){

        Campo campo11 = new Campo(1,1);
        Campo campo12 = new Campo(1,2);
        campo12.minar();

        Campo vizinhoDoVizinho1 = new Campo(2,2);
        vizinhoDoVizinho1.adicionarVizinho(campo11);
        vizinhoDoVizinho1.adicionarVizinho(campo12);


        campo.adicionarVizinho(vizinhoDoVizinho1);
        campo.abrir();

        assertTrue(campo.isAberto() && !campo11.isAberto());
    }






}