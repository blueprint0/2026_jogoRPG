package Herois;

import Entidades.Entidade;
import Monstros.Monstro;
import Manutencao.Jogo;

import java.util.*;

public abstract class Humano extends Entidade {
    protected String nome, classe;
    protected int dinheiro;

    Random rand = new Random();
    Jogo j = new Jogo();

    //==========  CONSTRUTOR  ==========
    public Humano (String nome, String classe, int vida, int ataque, int defesa, int cura, int velocidade, int cooldownAtual){
        super(vida, ataque, defesa, cura, velocidade, cooldownAtual);
        //Apenas de Humanos:
        this.nome = nome;
        this.classe = classe;
        this.dinheiro = 0;
    }

    //==========  GETTERS  ==========
    public String getNome(){return this.nome;}
    public String getClasse(){return this.classe;}
    public int getDinheiro(){return this.dinheiro;}

    //==========  SETTERS  ==========
    public void setNome(String nome){this.nome = nome;}
    public void setClasse(String classe){this.classe = classe;}
    public void setVida(int vida){this.vida = vida;}
    public void setAtaque(int ataque){this.ataque = ataque;}
    public void setDefesa(int defesa){this.defesa = defesa;}
    public void setCura(int cura){this.cura = cura;}
    public void setVelocidade(int velocidade){this.velocidade = velocidade;}
    public void setDinheiro(int dinheiro){this.dinheiro = dinheiro;}

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
        String status = String.format("\u001B[33mDinheiro: %d\u001B[0m", dinheiro);
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
                System.out.printf("Vez de: %s | Vida = %d| CD = %d\n",this.getNome(), this.getVida(), this.getCooldownAtual());
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
        int dano = causarDano(monstro);
        monstro.receberDano(dano);
        String mensagem = String.format("\u001B[31m%s causou %d de dano em %s!!!\u001B[0m", nome, dano, monstro.getNome());
        System.out.println(mensagem);
    }
    public int causarDano(Monstro monstro){
        int danoBruto = ataque + rand.nextInt(16);
        return Math.max(0, danoBruto - monstro.getDefesa());
    }
    public void receberDano(int dano){
        if (this.vida - dano <= 0) {
            this.vida = 0;
        }
        else  {
            this.vida -= dano;
        }
    }

    //==========  MÉTODOS DE CURA  ==========
    public void seCurar(){
        int cura = calcularCura();
        vida += cura;
        String mensagem = String.format("\u001B[32m%s se curou %d de vida!\u001B[0m", nome, cura);
        System.out.println(mensagem);
    }
    public int calcularCura(){
        return this.cura + rand.nextInt(16);
    }
    public void curarAliado(Humano h){
        int cura = calcularCura();
        h.setVida(h.getVida() + cura);
        String mensagem = String.format("\u001B[32m%s curou %d pontos de vida de %s!!\u001B[0m", this.getNome(), cura, h.getNome());
        System.out.println(mensagem);
    }

    //==========  MÉTODO VAZIO (HABILIDADE ESPECIAL)  ==========
    public abstract void habilidadeEspecial(List<Entidade> entidadesEmCombate);
}

