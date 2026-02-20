package Monstros;

import Entidades.Entidade;
import Manutencao.Jogo;

import java.util.List;
import java.util.Scanner;

public class Goblin extends Monstro{

    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Goblin (){
        super("Goblin", "COMUM", 80, 10, 5, 5, 20);
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
        this.introduzir();
    }

    @Override
    public int getDropDinheiro() {
        return 30;
    }

    @Override
    public int getCooldownMaximo() {
        return 0;
    }
}
