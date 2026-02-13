package Monstros;

import Entidades.Entidade;
import Herois.Humano;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class Monstro extends Entidade {
    protected String nome, tipo;
    protected int dinheiroDrop;

    Random rand = new Random();

    //==========  CONSTRUTOR  ==========
    public Monstro (String nome, String tipo, int vida, int ataque, int defesa, int cura, int velocidade, int cooldownAtual, int dinheiroDrop){
        super(vida, ataque, defesa, cura, velocidade, cooldownAtual);
        //Apenas de Monstros:
        this.nome = nome;
        this.tipo = tipo;
        this.dinheiroDrop = dinheiroDrop;
    }

    //==========  GETTERS  ==========
    public String getNome(){return this.nome;}
    public String getTipo(){return this.tipo;}

    //==========  SETTERS  ==========
    public void setNome(String nome){this.nome = nome;}
    public void setVida(int vida){this.vida = vida;}
    public void setAtaque(int ataque){this.ataque = ataque;}
    public void setDefesa(int defesa){this.defesa = defesa;}
    public void setCura(int cura){this.cura = cura;}
    public void setVelocidade(int velocidade){this.velocidade = velocidade;}
    //==========  MÉTODOS DE STATUS  ==========
    public void mostrarStatus(){
        String status = String.format("%s: Vida = %d", nome, vida);
        System.out.println(status);
    }

    //==========  MÉTODOS DE DANO  ==========
    public void atacar(Humano humano){
        int dano = causarDano(humano);
        humano.receberDano(dano);
        String mensagem = String.format("\u001B[31m%s causou %d de dano em %s!!!\u001B[0m", nome, dano, humano.getNome());
        System.out.println(mensagem);
    }
    public int causarDano(Humano humano){
        int danoBruto = ataque + rand.nextInt(16);
        int danoReal = Math.max(0, danoBruto - humano.getDefesa());
        return danoReal;
    }
    public void receberDano(int dano){
        if(this.vida - dano <= 0){
            this.vida = 0;
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