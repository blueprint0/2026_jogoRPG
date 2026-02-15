package Herois;

import Entidades.Entidade;

import java.util.List;

public class Paladino extends Humano{

    //========== CONSTRUTOR ==========
    public Paladino(String nome){
        super(nome, "Paladino", 120, 25, 20, 0, 25);
    }

    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate) {
        System.out.println("🛡️\u001B[32mO Paladino concedeu um escudo aos seus aliados!\u001B[0m");
        for (Entidade e : entidadesEmCombate) {
            if (e instanceof Humano && e.estaVivo()) {
                ((Humano) e).setEscudoExtra(20);
            }
        }
        this.resetarCooldown();
        j.esperar(1);
    }

    @Override
    public int getCooldownMaximo() {
        return 3;
    }
}
