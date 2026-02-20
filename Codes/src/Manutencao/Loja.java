package Manutencao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Loja {

    Random r = new Random();

    //Itens à venda na loja
    private List<Itens> itensVendendo = new ArrayList<>();
    //Estoque
    private List<Supplier<Itens>> estoque = new ArrayList<>();

    public List<Itens> inicializarLoja() {
        while(itensVendendo.size() < 3){
            itensVendendo.add(estoque.get(r.nextInt(estoque.size())).get());
        }
        return itensVendendo;
    }
}
