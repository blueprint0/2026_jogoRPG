package Herois;

import Entidades.Entidade;
import Monstros.Monstro;

import java.util.List;

public class Curador extends Humano{
    //========== CONSTRUTOR ==========
    public Curador(String nome){
        super(nome, "Curador", 100, 15, 5, 25, 20);
    }

    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate) {
        //Cura todos os aliados em campo
        if (!this.estaVivo())
            return;

        System.out.printf("\u001B[32m%s curou todos os aliados em campo!!\u001B[0m\n", this.getNome());
        for (Entidade e : entidadesEmCombate) {
            if (e instanceof Humano && ((Humano) e).estaVivo())
                this.curarAliado((Humano) e);
        }
        this.resetarCooldown();
    }

    @Override
    public int getCooldownMaximo() {
        return 4;
    }
}
