package Monstros;

import Entidades.Entidade;
import Herois.Humano;
import Manutencao.Jogo;

import java.util.*;

public class Zumbi extends Monstro{
    //Mecânica úncica de Zumbi
    private int danoAcumulado;

    Jogo j = new Jogo();

    //========== CONSTRUTOR ==========
    public Zumbi(){
        super("Zumbi", "COMUM", 50, 25, 8, 0, 10, 5);
    }

    //========== GETTERS ==========
    public int getDanoAcumulado() {return this.danoAcumulado;}

    //========== SETTERS ==========
    public void setDanoAcumulado(int danoAcumulado) {this.danoAcumulado = danoAcumulado;}

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate) {
        System.out.println("Zumbi usou a habilidade especial!");
        j.esperar(1);
        var m = String.format("O Zumbi comeu os restos de carne das suas vítimas e recuperou %d de vida!\n", this.getDanoAcumulado());
        System.out.println(m);

        this.setVida(this.getVida() + this.getDanoAcumulado());

        this.setDanoAcumulado(0);
        this.resetarCooldown();
    }

    //========== MÉTODOS DE COMBATE ==========
    public void atacar(Humano humano){
        String mensagem = String.format("%s mordeu %s!", this.getNome(), humano.getNome());
        System.out.println(mensagem);

        int dano = causarDano(humano);
        humano.receberDano(dano);
        j.esperar(1);

        //Mecânica única de Zumbi
        this.setDanoAcumulado(this.getDanoAcumulado() + dano);
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
    public int getCooldownMaximo() {
        return 3;
    }

}
