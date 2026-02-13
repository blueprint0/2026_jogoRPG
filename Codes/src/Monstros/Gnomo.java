package Monstros;

import Entidades.Entidade;
import Manutencao.Jogo;

import java.util.List;
import java.util.Scanner;

public class Gnomo extends Monstro{

    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Gnomo(){
        super("Gnomo", "COMUM", 80, 10, 5, 5, 10, 2, 10);
        //nome, tipo, vida, ataque, defesa, cura,  velocidade
    }

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate) {

        this.diminuirCooldown();
    }

    @Override
    public void agir(Scanner scanner, List<Entidade> entidadesEmCombate) {
        if(!this.estaVivo())
            return;
        var m = String.format("Vez do %s | Vida = %d", this.getNome(), this.getVida());
        System.out.println(m);
        j.esperar(1);

    }

    @Override
    public int getCooldownMaximo() {
        return 2;
    }

    public void receberDano(int dano){
        if (Math.random() < 0.2) {
            System.out.printf("\n%s É baixinho e desviou do ataque!!", this.getNome());
        }else {
            if(this.vida - dano <= 0){
                this.vida = 0;
            }
            else{
                this.vida -= dano;
            }
        }
    }

}
