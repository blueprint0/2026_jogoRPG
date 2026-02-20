package Monstros;

import Entidades.Entidade;
import Herois.Humano;
import Manutencao.Arena;
import Manutencao.Jogo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Gnomo extends Monstro{

    Arena arena = new Arena();
    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Gnomo(){
        super("Gnomo", "COMUM", 80, 10, 5, 5, 10);
        //nome, tipo, vida, ataque, defesa, cura,  velocidade
    }

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate) {

        this.resetarCooldown();
    }

    @Override
    public void agir(Scanner scanner, List<Entidade> entidadesEmCombate) {
        if (!this.estaVivo())
            return;
        this.introduzir();

        //Zumbi ataca humano aleatório
        List<Entidade> alvosPossiveis = new ArrayList<>(entidadesEmCombate);
        Collections.shuffle(alvosPossiveis);

        for (Entidade e : alvosPossiveis) {
            if (e instanceof Humano && e.estaVivo()){
                if (this.getCooldownAtual() == 0)
                    this.habilidadeEspecial(alvosPossiveis);
                else{
                    this.atacar((Humano) e);
                    this.diminuirCooldown();
                }
                break;
            }
        }
        j.esperar(1);
    }

    @Override
    public int getDropDinheiro() {
        return 10;
    }

    @Override
    public int getCooldownMaximo() {
        return 2;
    }

    public void receberDano(int dano){
        if (Math.random() < 0.2){
            System.out.println("O Gnomo é baixinho e desviou do ataque!");
            return;
        }

        String m = String.format("\u001B[31m%s sofreu %d de dano!!\u001B[0m", this.getNome(), dano);
        System.out.println(m);

        if(this.vida - dano <= 0){
            this.vida = 0;
            var mensagem = String.format("☠️ %s morreu!!", this.getNome());
            System.out.println(mensagem);
            this.droparDinheiro();
        }
        else{
            this.vida -= dano;
        }
    }


}
