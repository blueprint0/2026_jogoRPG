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
        super("Esqueleto", "COMUM", 80, 17, 0, 5, 15, 2, 0);
    }

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> alvosPossiveis) {
        //Ignora armadura
        System.out.println("Esqueleto usou a habilidade especial!");
        j.esperar(1);
        //Ordena por maior defesa
        alvosPossiveis.reversed();
        for (Entidade e : alvosPossiveis){
            if(e instanceof Humano && e.estaVivo()){
                int dano = 17 + rand.nextInt(16);
                //Usando o método 'receberDano()' para ignorar a armadura
                ((Humano) e).receberDano(dano);
                String m = String.format("O Esqueleto atacou %s ignorando sua armadura! Causou %d de ano!!", ((Humano) e).getNome(),  dano);
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
        var m = String.format("Vez do %s | Vida = %d", this.getNome(), this.getVida());
        System.out.println(m);
        j.esperar(1);

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
        return 0;
    }
}
