package Manutencao;

import Entidades.Entidade;
import Herois.Curador;
import Herois.Humano;
import Herois.Guerreiro;
import Herois.Tanque;
import Monstros.*;
import Main.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static Manutencao.Bestiario.sortearMonstro;

public class Jogo{
    //Objetos do jogo
    public ArrayList<Humano> humanos = new ArrayList<>();
    public ArrayList<Monstro> monstrosEmCampo = new ArrayList<>();
    public ArrayList<Entidade> entidadesEmCombate = new ArrayList<>();

    //Controle de rodadas e fim de jogo
    public boolean fimJogo;
    private int rodadas = 1;

    Random rand = new Random();
    Scanner leitor = new Scanner(System.in);


    //==========  MÉTODOS DE CONTROLE DE RODADAS  ==========
    public int getRodadas() {return rodadas;}

    public void setRodadas(int rodadas) {this.rodadas = rodadas;}

    public void passarRodada() {
        System.out.println("Fim da rodada " +  rodadas++);
        esperar(1);
        System.out.println("Começando a rodada " + rodadas);
    }


    //==========  MÉTODOS DE CONTROLE DE JOGO  ==========
    //1.
    public void esperar(long milissegundos){
        try {
            var tempo = milissegundos * 1000;
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrompido.");
        }
    }
    //2.
    public String escolherNome(){
        System.out.println("Digite o nome do seu herói: ");
        return leitor.nextLine();
    }
    //3.
    public int escolherClasse(){
        System.out.println("Escolha a classe do seu herói:\t[1]Guerreiro\t[2]Tanque\t[3]Curador");
        if (leitor.hasNextInt()){
            int escolha = leitor.nextInt();
            leitor.nextLine();
            if (escolha >= 1 && escolha <= 3){
                return escolha;
            }
            System.out.print("Número inválido! Digite novamente.");
            return escolherClasse();
        }
        System.out.println("Input inválido! Digite novamente!");
        leitor.nextLine();
        return escolherClasse();
    }
    //4.
    public Humano criarHeroi(String nome, int classe){
        return switch (classe) {
            case 1 -> new Guerreiro(nome);
            case 2 -> new Tanque(nome);
            case 3 -> new Curador(nome);
            default -> {
                System.out.println("Classe inválida! Não foi possível criar o herói.");
                yield null; // Retorna nulo se a classe for inválida
            }
        };
    }
    //5.
    public ArrayList<Monstro> selecionarMonstro(int rodada){
        var monstrosDaRodada = new ArrayList<Monstro>();

        switch (rodada) {
            case 1, 2 -> {
                for (int i = 0 ; i < 3 ; i++){
                    Monstro m = Math.random() < 0.6 ? sortearMonstro("COMUM") : sortearMonstro("RARO");
                    monstrosDaRodada.add(m);
                }
                return monstrosDaRodada;
            }
            case 3 -> {
                for (int i = 0 ; i < 3 ; i++){
                    Monstro m = Math.random() < 0.3 ? sortearMonstro("COMUM") : sortearMonstro("RARO");
                    monstrosDaRodada.add(m);
                }
                return monstrosDaRodada;
            }
            case 4 -> {
                for (int i = 0 ; i < 2 ; i++){
                    Monstro m = Math.random() < 0.25 ? sortearMonstro("COMUM") : sortearMonstro("RARO");
                    monstrosDaRodada.add(m);
                }
                monstrosDaRodada.add(sortearMonstro("EPICO"));
                return monstrosDaRodada;
            }

            case 5 -> {
                //invasão na loja??

            }
            case 6 -> {

            }
            case 7 -> {

            }
            case 8 -> {

            }
            case 9 -> {

            }
            case 10 -> {

            }

            default -> System.out.printf("\nErro ao inicializar monstros na rodada %d", rodada);
        }
        System.out.println("Erro no método selecionarMonstro.");
        return null;
    }
    //6.
    public boolean verificarFim(List<Entidade> entidades) {
        int humanosVivos = 0;
        int monstrosVivos = 0;

        for (Entidade e : entidades) {
            if (e instanceof Humano && ((Humano) e).getVida() > 0) {
                humanosVivos++;
            }
            else if (e instanceof Monstro &&  ((Monstro) e).getVida() > 0) {
                monstrosVivos++;
            }
        }
        if (humanosVivos == 0){
            System.out.println("Todos os humanos morreram!!");
            esperar(1);
            if (rodadas < 5)
                System.out.println("Boa sorte na próxima vez!");
            if (rodadas > 5 && rodadas < 10)
                System.out.println("Foi uma boa tentativa!");
            if (rodadas == 10)
                System.out.println("He's the final boss for a reason, tho...\nFoi uma ótima tentativa!");

            this.fimJogo = true;
            return true;
        }
        if (monstrosVivos == 0){
            System.out.println("Todos os monstros morreram!!");
            esperar(1);
            System.out.println("Muito bem!!");
            esperar(1);
            this.fimJogo = false;
            return true;
        }
        return false;

    }
    //==========  MÉTODOS DE INÍCIO E FIM DE JOGO  ==========
    public void iniciarJogo(){
        System.out.println("Iniciando o jogo...");
        esperar(1); // Espera 1 segundo
        System.out.println("Se prepare!");
        setRodadas(1);
        esperar(1);

        for (int i = 0; i < 3; i++) {
            System.out.println("Criando herói " + (i + 1) + "...");
            esperar(1); // Espera 1 segundo para cada herói

            String nome = escolherNome();
            int classe = escolherClasse();
            Humano humano = criarHeroi(nome, classe);
            humanos.add(humano);
        }
    }
    public void exibirStatusHumanos(List<Humano> humanos){
        System.out.println("=====.=====");
        System.out.println("Status dos heróis");
        esperar(1);
        for (Humano h : humanos){
            h.mostrarStatusSimplificado();
            esperar(1);
        }
    }
    public void exibirStatusMonstros(List<Monstro> monstros){
        System.out.println("=====.=====");
        System.out.println("Status dos monstros");
        esperar(1);
        for (Monstro m : monstros){
            m.mostrarStatus();
            esperar(1);
        }
        System.out.println("=====.=====");
    }
    public void fimJogo(){
        System.out.println("Obrigado por jogar!!");
        setRodadas(1);
        humanos.clear();
    }
}



