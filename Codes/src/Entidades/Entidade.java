package Entidades;

import java.util.List;
import java.util.Scanner;

public abstract class Entidade {
    //Variáveis comuns para Entidades mas com valor dinâmico
    protected String nome;
    protected int vida, ataque, defesa, cura, velocidade;

    //Variáveis comuns para Entidades mas com valor fixo
    protected int cooldownAtual = this.getCooldownMaximo();

    //========== CONSTRUTOR ==========
    public Entidade(String nome, int vida, int ataque, int defesa, int cura, int velocidade) {
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.cura = cura;
        this.velocidade = velocidade;
    }

    //==========  GETTERS  ==========
    public String getNome(){return this.nome;}
    public int getVida(){return this.vida;}
    public int getAtaque(){return this.ataque;}
    public int getDefesa(){return this.defesa;}
    public int getCura(){return this.cura;}
    public int getVelocidade(){return this.velocidade;}
    public int getCooldownAtual(){return this.cooldownAtual;}

    //========== SETTERS ==========
    public void setNome(String nome){this.nome = nome;}
    public void setVida(int vida){this.vida = vida;}
    public void setAtaque(int ataque){this.ataque = ataque;}
    public void setDefesa(int defesa){this.defesa = defesa;}
    public void setCura(int cura){this.cura = cura;}
    public void setVelocidade(int velocidade){this.velocidade = velocidade;}
    public void setCooldownAtual(int cooldownAtual){this.cooldownAtual = cooldownAtual;}

    //========== MÉTODOS ABSTRATOS ==========
    public abstract void agir(Scanner scanner, List<Entidade> entidadesEmCombate);

    public abstract int getCooldownMaximo();

    //========== MÉTODOS DE COOLDOWN ==========
    public void diminuirCooldown(){
        if (cooldownAtual > 0)
            cooldownAtual --;
    }
    public void resetarCooldown(){
        this.cooldownAtual = this.getCooldownMaximo();
    }

    //==========  MÉTODOS DE CONTROLE  ==========
    public boolean estaVivo(){
        return this.getVida() > 0;
    }
}
