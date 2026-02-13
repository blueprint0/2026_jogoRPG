package Monstros;

import Entidades.Entidade;
import Manutencao.Jogo;

import java.util.List;
import java.util.Scanner;

public class Goblin extends Monstro{

    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Goblin (){
        super("Goblin", "COMUM", 80, 10, 5, 5, 20, 3, 30);
    }

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate) {
        //Rouba moedas
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
        return 0;
    }
}
