package Herois;

import Entidades.Entidade;
import Manutencao.Arena;
import Monstros.Monstro;
import Manutencao.Jogo;

import java.util.*;

public abstract class Humano extends Entidade {
    protected String classe;
    //Variáveis com valor de inicialização fixo (fora do construtor)
    //protected int nivel = 0;
    //protected int xp = 0;
    //protected Enum estados (congelado, queimando, envenenado)

    //Escudo extra do Paladino
    protected int escudoExtra = 0;

    Random rand = new Random();
    Jogo j = new Jogo();

    //==========  CONSTRUTOR  ==========
    public Humano (String nome, String classe, int vida, int ataque, int defesa, int cura, int velocidade){
        super(nome, vida, ataque, defesa, cura, velocidade);
        //Apenas de Humanos, que variam para cada instância:
        this.classe = classe;
    }

    //==========  GETTERS  ==========
    public String getClasse(){return this.classe;}
    public int getEscudoExtra(){return this.escudoExtra;}

    //==========  SETTERS  ==========
    public void setClasse(String classe){this.classe = classe;}
    public void setEscudoExtra(int escudoExtra){this.escudoExtra = escudoExtra;}

    //==========  MÉTODOS DE STATUS  ==========
    public void mostrarStatus(){
        String status = String.format("Status: %s - %s | Vida: %d | Ataque: %d | Defesa: %d | Cura: %d | Velocidade: %d",
                nome, classe, vida, ataque, defesa, cura, velocidade);
        System.out.println(status);
    }
    public void mostrarStatusSimplificado(){
        String status = String.format("%s: Vida = %d | CD = %d", this.getNome(), this.getVida(), this.getCooldownAtual());
        System.out.println(status);
    }
    public void mostrarDinheiro(){
        String status = String.format("\u001B[33mDinheiro: %d\u001B[0m", Arena.getDinheiroCompartilhado());
        System.out.println(status);

    }
    //==========  MÉTODOS DE COMBATE  ==========
    @Override
    public void agir(Scanner leitor, List<Entidade> entidadesEmCombate){
        if (!this.estaVivo())
            return;

        //Variável de segurança
        boolean inputValido = false;

        while (!inputValido){
            try {
                var m = String.format("\n🔵 Turno de: %s | Vida = %d| CD = %d",this.getNome(), this.getVida(), this.getCooldownAtual());
                System.out.println(m);
                j.esperar(1);
                System.out.println("Escolha sua ação:\t[1]Atacar\t[2]Se curar\t[3]Curar aliado\t[4]Habilidade especial");
                if (leitor.hasNextInt()){
                    int acao = leitor.nextInt();
                    int numero = 0;
                    switch (acao){
                        case 1:
                            inputValido = true;
                            ArrayList<Monstro> alvosDisponiveis = new ArrayList<>();
                            System.out.println("Escolha um monstro para atacar!");
                            j.esperar(1);
                            for (Entidade e : entidadesEmCombate) {
                                if (e instanceof Monstro) {
                                    alvosDisponiveis.add((Monstro) e);
                                    numero++;
                                    String mensagem = String.format("[%d] %s: Vida = %d", numero, ((Monstro) e).getNome(), ((Monstro) e).getVida());
                                    System.out.println(mensagem);
                                    j.esperar(1);
                                }
                            }
                            int escolhaAtaque = leitor.nextInt();
                            leitor.nextLine();
                            var alvoAtaque = alvosDisponiveis.get(escolhaAtaque - 1);
                            if (alvoAtaque.getVida() <= 0) {
                                System.out.println("Alvo já está morto!");
                                break;
                            }
                            this.atacar(alvoAtaque);
                            this.diminuirCooldown();
                            break;
                        case 2:
                            inputValido = true;
                            this.seCurar();
                            this.diminuirCooldown();
                            break;
                        case 3:
                            inputValido = true;
                            ArrayList<Humano> aliadosDisponiveis = new ArrayList<>();
                            System.out.println("Escolha um aliado para curar!");
                            for  (Entidade e : entidadesEmCombate) {
                                if (e instanceof Humano) {
                                    aliadosDisponiveis.add((Humano) e);
                                    numero++;
                                    System.out.printf("[%d] %s: Vida = %d\n", numero, ((Humano) e).getNome(), ((Humano) e).getVida());
                                    j.esperar(1);
                                }
                            }
                            int escolhaCura = leitor.nextInt();
                            leitor.nextLine();
                            this.curarAliado(aliadosDisponiveis.get(escolhaCura - 1));
                            this.diminuirCooldown();
                            break;
                        case 4:
                            inputValido = true;
                            if (this.getCooldownAtual() == 0)
                                this.habilidadeEspecial(entidadesEmCombate);
                            else
                                System.out.println("A habilidade ainda está em cooldown!");
                            break;
                        default:
                            System.out.println("Ação inválida! Digite novamente");
                    }
                } else {
                    System.out.println("Input inválido!! Digite novamente");
                    leitor.nextLine();
                }

            } catch (InputMismatchException e) {
                System.out.println("Input inválido! Digite novamente");
                leitor.next(); //descarta o input incorreto
            }
        }
    }

    //==========  MÉTODOS DE DANO  ==========
    public void atacar(Monstro monstro){
        var m = String.format("%s atacou %s!", this.getNome(), monstro.getNome());
        System.out.println(m);
        int dano = causarDano(monstro);
        monstro.receberDano(dano);
    }
    public int causarDano(Monstro monstro){
        int danoBruto = this.getAtaque() + rand.nextInt(16);
        return Math.max(0, danoBruto - monstro.getDefesa());
    }
    public void receberDano(int dano){
        //Escudo extra do Paladino
        if (this.getEscudoExtra() > 0){
            int danoEscudo = dano;
            dano = Math.max(0, dano - this.getEscudoExtra());
            if (dano > 0)
                System.out.println("O escudo absorveu parte do dano!");
            else
                System.out.println("O escudo absorveu todo o dano!!");
            this.setEscudoExtra(Math.max(0, this.getEscudoExtra() - danoEscudo));
        }

        String mensagem = String.format("\u001B[31m%s sofreu %d de dano!!\u001B[0m", this.getNome(), dano);
        System.out.println(mensagem);
        if (this.vida - dano <= 0) {
            this.vida = 0;
            var m = String.format("☠️ %s morreu!!", this.getNome());
            System.out.println(m);
        }
        else  {
            this.vida -= dano;
        }
    }

    //==========  MÉTODOS DE CURA  ==========
    public void seCurar(){
        if (this.getVidaMaxima() == this.getVida()){
            System.out.println("Você já está com a vida máxima!");
            return;
        }

        int cura = calcularCura();
        vida += cura;
        String mensagem = String.format("\u001B[32m%s se curou %d de vida!\u001B[0m", nome, cura);
        System.out.println(mensagem);
    }
    public int calcularCura(){
        return this.cura + rand.nextInt(16);
    }
    public void curarAliado(Humano h){
        if (h.getVidaMaxima() == h.getVida()){
            System.out.println(h.getNome() + " já está com a vida máxima!");
            return;
        }

        int cura = calcularCura();
        h.setVida(h.getVida() + cura);
        String mensagem = String.format("\u001B[32m%s curou %d pontos de vida de %s!!\u001B[0m", this.getNome(), cura, h.getNome());
        System.out.println(mensagem);
    }

    //==========  MÉTODO VAZIO (HABILIDADE ESPECIAL)  ==========
    public abstract void habilidadeEspecial(List<Entidade> entidadesEmCombate);
}

