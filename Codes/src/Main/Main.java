package Main;

import Entidades.Entidade;
import Manutencao.Jogo;
import Monstros.Monstro;
import Herois.Humano;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var j = new Jogo();
        var leitor = new Scanner(System.in);
        var random = new Random();

        j.iniciarJogo();

        //loop do jogo
        boolean jogando = true;

        while (jogando) {
            System.out.println("Começando a rodada " + j.getRodadas());
            //checarRodada() -> adicionar possibilidade de eventos especiais
            //if rodadas = 5 -> loja
            //if rodadas = 10 -> chefe

            //===== CONTROLE DOS MONSTROS EM CAMPO:
            j.monstrosEmCampo.clear();
            j.entidadesEmCombate.clear();
            j.esperar(1);

            //===== SELECIONANDO MONSTROS DA RODADA:
            j.monstrosEmCampo = j.selecionarMonstro(j.getRodadas());

            j.exibirStatusHumanos(j.humanos);
            j.exibirStatusMonstros(j.monstrosEmCampo);

            //sistema para determinar quem age primeiro com base na velocidade dos personagens
            j.entidadesEmCombate.addAll(j.monstrosEmCampo);
            j.entidadesEmCombate.addAll(j.humanos);
            j.entidadesEmCombate.sort((p1, p2) -> p2.getVelocidade() - p1.getVelocidade());

            // loop da rodada
            boolean emRodada = true;

            // Da pra fazer uma rotulação:
            loopDeCombate:
            while (emRodada) {
                for (Entidade e : j.entidadesEmCombate) {
                    e.agir(leitor, j.entidadesEmCombate);
                    j.esperar(1);
                    if (j.verificarFim(j.entidadesEmCombate)){
                        emRodada = false;
                        if (j.fimJogo) {
                            j.fimJogo();
                            break loopDeCombate;
                        }
                        break; //sai do loop mais interno que ele está (for)
                    }
                }
            }
            //próxima rodada
            j.passarRodada();

        }
    }
}