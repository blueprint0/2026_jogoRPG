package Monstros;

import Entidades.Entidade;
import Manutencao.Jogo;

import java.util.List;
import java.util.Scanner;

public class Gnomo extends Monstro{

    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Gnomo(){
        super("Gnomo", "COMUM", 80, 10, 5, 5, 10, 10);
        //nome, tipo, vida, ataque, defesa, cura,  velocidade
    }


    @Override
    public void agir(Scanner scanner, List<Entidade> entidadesEmCombate) {
        if(!this.estaVivo())
            return;
        this.introduzir();

        this.diminuirCooldown();
    }

    @Override
    public int getCooldownMaximo() {
        return 2;
    }

    public void receberDano(int dano){
        if (Math.random() < 0.2){
            System.out.println("O Gnomo é baixinho e desviou do ataque!");
            return;
        }

        String m = String.format("\u001B[31m%s sofreu %d de dano!!\u001B[0m", this.getNome(), dano);
        System.out.println(m);

        if(this.vida - dano <= 0){
            this.vida = 0;
            var mensagem = String.format("☠️ %s morreu!!", this.getNome());
            System.out.println(mensagem);
        }
        else{
            this.vida -= dano;
        }
    }

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate) {

        this.resetarCooldown();
    }

}
