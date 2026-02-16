package Monstros;

import Entidades.Entidade;
import Herois.Humano;
import Manutencao.Jogo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Vampiro extends Monstro{
    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Vampiro(){
        super("Vampiro", "EPICO", 120, 30, 10, 0, 35, 40);
    }

    //========== MÉTODOS DE COMBATE ==========
    public void atacar(Humano h){
        String mensagem = String.format("\u001B[31mO Vampiro sugou o sangue de %s e se regenerou!\u001B[0m", h.getNome());
        System.out.println(mensagem);
        j.esperar(1);
        int dano = causarDano(h);
        h.receberDano(dano);

        //Exclusivo de Vampiros
        this.setVida(this.getVida() + dano/2);
    }

    @Override
    public void agir(Scanner leitor, List<Entidade> entidadesEmCombate) {
        if (!this.estaVivo())
            return;
        this.introduzir();

        //Ataca quem tem mais vida
        List<Entidade> alvosDisponiveis = new ArrayList<>(entidadesEmCombate);
        alvosDisponiveis.sort(Comparator.comparingInt(Entidade::getVida).reversed());

        for (Entidade e : alvosDisponiveis) {
            if (e instanceof Humano && e.estaVivo()){
                if (this.getCooldownAtual() == 0){
                    this.habilidadeEspecial(alvosDisponiveis);
                    break;
                } else {
                    this.atacar((Humano) e);
                    this.diminuirCooldown();
                    break;
                }
            }
        }
    }

    @Override
    public int getCooldownMaximo() {
        return 2;
    }

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> alvosDisponiveis) {
        System.out.println("O vampiro saboreia o sangue dos inimigos para se fortalecer...");
        j.esperar(1);
        if (this.getDefesa() < 30)
            this.setDefesa(this.getDefesa() + 10);
        else
            this.setAtaque(this.getAtaque() + 10);
        this.resetarCooldown();
    }
}
