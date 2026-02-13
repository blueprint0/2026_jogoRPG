package Entidades;

import java.util.List;
import java.util.Scanner;

public abstract class Entidade {
    protected int vida, ataque, defesa, cura, velocidade, cooldownAtual;

    //========== CONSTRUTOR ==========
    public Entidade(int vida, int ataque, int defesa, int cura, int velocidade, int cooldownAtual) {
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.cura = cura;
        this.velocidade = velocidade;
        this.cooldownAtual = cooldownAtual;
    }

    //==========  GETTERS  ==========
    public int getVida(){ return this.vida; }
    public int getAtaque(){ return this.ataque; }
    public int getDefesa(){ return this.defesa; }
    public int getCura(){ return this.cura; }
    public int getVelocidade(){ return this.velocidade; }
    public int getCooldownAtual(){ return this.cooldownAtual; }

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
