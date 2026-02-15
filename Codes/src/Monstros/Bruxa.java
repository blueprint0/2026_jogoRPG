package Monstros;

import Entidades.Entidade;
import Herois.Humano;
import Manutencao.Jogo;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Bruxa extends Monstro{
    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Bruxa(){
        super("Bruxa", "RARO", 100, 20, 5, 15, 15, 20);
    }

    //==========  GETTERS  ==========
    @Override
    public int getCooldownMaximo(){
        return 3;
    }

    //========== MÉTODOS DE COMBATE ==========
    @Override
    public void habilidadeEspecial(List<Entidade> alvosPossiveis) {
        System.out.println("A bruxa usou a habilidade especial!!");
        j.esperar(1);
        for (Entidade e : alvosPossiveis) {
            if (e instanceof Humano) {
                String mensagem = String.format("Uma maldição terrível foi posta sobre %s!!", ((Humano) e).getNome());
                System.out.println(mensagem);
                j.esperar(1);

                ((Humano) e).setVida(e.getVida() / 2);
                this.resetarCooldown();
                break;
            }
        }
    }

    @Override
    public void agir(Scanner scanner, List<Entidade> entidadesEmCombate) {
        if (!this.estaVivo())
            return;
        this.introduzir();

        int acoes = 2;

        //Criar uma cópia da lista original para evitar erro "ConcurrentModificationException"
        List<Entidade> alvosPossiveis = new ArrayList<>(entidadesEmCombate);

        //Ordenar com base na vida em ordem decrescente (por causa do reversed)
        alvosPossiveis.sort(Comparator.comparingInt(Entidade::getVida).reversed());

        for (Entidade e : alvosPossiveis){
            if (e instanceof Humano && e.getVida() > 0 && acoes > 0){
                if (this.getCooldownAtual() == 0) {
                    this.habilidadeEspecial(alvosPossiveis);
                    acoes--;
                } else {
                    this.atacar((Humano) e);
                    acoes--;
                    j.esperar(1);
                }
            }
        }
        this.diminuirCooldown();
    }
}
