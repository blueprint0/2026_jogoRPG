package Manutencao;

import Monstros.*;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.Random;

public class Bestiario {

    //Usando Supplier: primeiro, definimos uma List que guarda objetos que implementam a interface Supplier
    public static List<Supplier<Monstro>> monstrosComuns= new ArrayList<>();
    public static List<Supplier<Monstro>> monstrosRaros = new ArrayList<>();
    public static List<Supplier<Monstro>> monstrosEpicos = new ArrayList<>();
    //public static List<Supplier<Monstro>> chefes = new ArrayList<>();

    //Static block para encapsular instruções (popular a lista)
    static {
        //Lambda (função anônima)
        //monstrosComuns.add(() -> new Esqueleto());
        //Method reference
        monstrosComuns.add(Esqueleto::new);
        monstrosComuns.add(Gnomo::new);
        monstrosComuns.add(Zumbi::new);
        monstrosComuns.add(Goblin::new);
        monstrosRaros.add(Bruxa::new);
        //monstrosEpicos.add(Golem::new);
        //monstrosEpicos.add(Xama::new);
        monstrosEpicos.add(Vampiro::new);

    }
    //Ao usar o método get, ele executa a instrução e entrega o resultado, mas
    // mantém a instrução armazenada, permitindo reuso
    public static Monstro sortearMonstro(String raridade){
        var r = new Random();
        int indice;
        if (raridade.equals("COMUM")){
            indice = r.nextInt(monstrosComuns.size());
            return monstrosComuns.get(indice).get();
        }
        if (raridade.equals("RARO")){
            indice = r.nextInt(monstrosRaros.size());
            return monstrosRaros.get(indice).get();
        }
        if (raridade.equals("EPICO")){
            indice = r.nextInt(monstrosEpicos.size());
            return monstrosEpicos.get(indice).get();
        }
        System.out.println("Erro ao inicializar monstro.");
        return null;
    }
}
