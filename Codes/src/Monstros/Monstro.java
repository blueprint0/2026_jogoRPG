package Monstros;

import Entidades.Entidade;
import Herois.Humano;
import Manutencao.Jogo;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class Monstro extends Entidade {
    protected String tipo;
    protected int dinheiroDrop;

    Jogo j = new Jogo();
    Random rand = new Random();

    //==========  CONSTRUTOR  ==========
    public Monstro (String nome, String tipo, int vida, int ataque, int defesa, int cura, int velocidade, int dinheiroDrop){
        super(nome, vida, ataque, defesa, cura, velocidade);
        //Apenas de Monstros, que variam para cada instância:
        this.tipo = tipo;
        this.dinheiroDrop = dinheiroDrop;
    }

    //==========  GETTERS  ==========
    public String getTipo(){return this.tipo;}
    public int getDinheiroDrop(){return this.dinheiroDrop;}

    //==========  SETTERS  ==========
    public void setTipo(String tipo){this.tipo = tipo;}
    public void setDinheiroDrop(int dinheiro){this.dinheiroDrop = dinheiro;}

    //==========  MÉTODOS DE STATUS  ==========
    public void mostrarStatus(){
        String status = String.format("%s: Vida = %d", nome, vida);
        System.out.println(status);
    }

    //========== MÉTODOS DE CONTROLE ==========
    public void introduzir(){
        var mensagem = String.format("\n🔴 Turno de: %s | Vida = %d", this.getNome(), this.getVida());
        System.out.println(mensagem);
        j.esperar(1);
    }

    //==========  MÉTODOS DE DANO  ==========
    public void atacar(Humano humano){
        var m = String.format("%s atacou %s!!", this.getNome(), humano.getNome());
        System.out.println(m);
        int dano = causarDano(humano);
        humano.receberDano(dano);

    }
    public int causarDano(Humano humano){
        int danoBruto = this.getAtaque() + rand.nextInt(16);
        return Math.max(0, danoBruto - humano.getDefesa());
    }
    public void receberDano(int dano){
        String m = String.format("\u001B[31m%s sofreu %d de dano!!\u001B[0m", this.getNome(), dano);
        System.out.println(m);

        if(this.vida - dano <= 0){
            this.vida = 0;
            var m2 = String.format("☠️ %s morreu!!", this.getNome());
            System.out.println(m2);
        }
        else{
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
        int curaReal = cura + rand.nextInt(16);
        return curaReal;
    }
    //curar aliado (não implementado)

    //==========  MÉTODOS DE COMBATE  ==========
    public abstract void agir(Scanner leitor, List<Entidade> entidadesEmCombate);
    //Cada monstro vai ter uma IA própria

    //==========  MÉTODO VAZIO (HABILIDADE ESPECIAL)  ==========
    public abstract void habilidadeEspecial(List<Entidade> e);
    //Todo monstro terá sua própria habilidade especial
}