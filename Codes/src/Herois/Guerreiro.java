package Herois;

import Entidades.Entidade;
import Monstros.Monstro;
import Manutencao.Jogo;

import java.util.List;

public class Guerreiro extends Humano{
    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Guerreiro (String nome){            //variáveis que mudam independente de serem Guerreiro
        super(nome, "Guerreiro", 120, 20, 10, 0, 25, 2); //variáveis fixas para os Guerreiros
    }

    //========== HABILIDADE ESPECIAL  ==========
    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate){
        String texto = String.format("🔥\u001B[31m%s usou o ataque giratório!!\u001B[0m", nome);
        System.out.println(texto);
        j.esperar(1);

        for (Entidade e : entidadesEmCombate) {
            if (e instanceof Monstro && ((Monstro) e).estaVivo()){
                this.atacar((Monstro) e);
            }
        }
        this.resetarCooldown();
    }

    //========== COOLDOWN ==========
    @Override
    public int getCooldownMaximo() {
        return 2;
    }
}
