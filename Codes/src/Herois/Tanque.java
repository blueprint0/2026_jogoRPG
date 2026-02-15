package Herois;

import Entidades.Entidade;
import Monstros.Monstro;

import java.util.List;

public class Tanque extends Humano{
    //========== CONSTRUTOR ==========
    public Tanque(String nome){
        super(nome, "Tanque", 200, 20, 15, 0, 10);
    }

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate){
        //Taunt nos monstros
        if (!this.estaVivo())
            return;

    }

    @Override
    public int getCooldownMaximo() {
        return 2;
    }
}
