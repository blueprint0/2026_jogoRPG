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
        super("Zumbi", "COMUM", 50, 12, 8, 0, 10, 2, 5);
    }

    //========== HABILIDADE ESPECIAL ==========
    @Override
    public void habilidadeEspecial(List<Entidade> entidadesEmCombate) {
        System.out.println("Zumbi usou a habilidade especial!");
        j.esperar(1);

        System.out.printf("O Zumbi comeu os restos de carne das suas vítimas e recuperou %d de vida!\n", this.danoAcumulado);
        danoAcumulado = 0;
        this.resetarCooldown();
    }

    //========== MÉTODOS DE COMBATE ==========
    public void atacar(Humano humano){
        int dano = causarDano(humano);
        humano.receberDano(dano);
        String mensagem = String.format("\u001B[31m%s mordeu %s e causou %d de dano!\u001B[0m", this.getNome(), humano.getNome(), dano);
        System.out.println(mensagem);
        j.esperar(1);

        //Mecânica única de Zumbi
        this.danoAcumulado += dano;
    }

    @Override
    public void agir(Scanner scanner, List<Entidade> entidadesEmCombate) {
        if (!this.estaVivo())
            return;
        var m = String.format("Vez do %s | Vida = %d", this.getNome(), this.getVida());
        System.out.println(m);
        j.esperar(1);


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
        return 0;
    }

}
