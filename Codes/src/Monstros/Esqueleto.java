package Monstros;

import Entidades.Entidade;
import Herois.Humano;
import Manutencao.Jogo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Esqueleto extends Monstro{

    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Esqueleto(){
        super("Esqueleto", "COMUM", 80, 17, 0, 5, 15, 0);
    }

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> alvosPossiveis) {
        //Ignora armadura
        System.out.println("Esqueleto usou a habilidade especial!");
        j.esperar(1);
        //Ordena por maior defesa
        alvosPossiveis = alvosPossiveis.reversed();
        for (Entidade e : alvosPossiveis){
            if(e instanceof Humano && e.estaVivo()){
                String m = String.format("O Esqueleto atacou %s ignorando sua armadura!!", e.getNome());
                System.out.println(m);
                int dano = 17 + rand.nextInt(16);
                //Usando o método 'receberDano()' para ignorar a armadura
                ((Humano) e).receberDano(dano);
                j.esperar(1);
                break;
            }
        }
        this.resetarCooldown();
    }

    @Override
    public void agir(Scanner scanner, List<Entidade> entidadesEmCombate) {
        if (!this.estaVivo())
            return;
        this.introduzir();

        //Esqueleto ataca quem tem menor defesa
        List<Entidade> alvosPossiveis = new ArrayList<>(entidadesEmCombate);
        alvosPossiveis.sort(Comparator.comparingInt(Entidade::getDefesa));

        for (Entidade e : alvosPossiveis){
            if (e instanceof Humano && e.estaVivo()){
                if (this.getCooldownAtual() == 0)
                    this.habilidadeEspecial(alvosPossiveis);
                else {
                    this.atacar((Humano) e);
                    this.diminuirCooldown();
                }
                break;
            }
        }
        j.esperar(1);
    }

    @Override
    public int getCooldownMaximo() {
        return 2;
    }
}
