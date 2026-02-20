package Manutencao;

public class Arena {
    //Dinheiro
    private static int dinheiroNaArena = 0;
    private static int dinheiroCompartilhado = 0;

    //========== GETTERS ==========
    public static int getDinheiroCompartilhado() {
        return dinheiroCompartilhado;
    }
    public static int getDinheiroNaArena() {
        return dinheiroNaArena;
    }

    //========== SETTERS ==========
    public static void setDinheiroCompartilhado(int dinheiroCompartilhado) {
        Arena.dinheiroCompartilhado = dinheiroCompartilhado;
    }
    public static void setDinheiroNaArena(int dinheiroNaArena) {
        Arena.dinheiroNaArena = dinheiroNaArena;
    }

    //========== MÉTODOS DE DINHEIRO ==========

    //Quando um Monstro morre
    public void depositarDinheiro(int dinheiro){
        setDinheiroNaArena(getDinheiroNaArena() + dinheiro);
    }
    //Ao fim de toda rodada
    public void pegarDinheiroArena(){
        setDinheiroCompartilhado(getDinheiroNaArena());
    }
    //Ao fim de toda rodada
    public void resetarDinheiroArena(){
        setDinheiroNaArena(0);
    }
    //Ao fim do jogo
    public void resetarArena(){
        setDinheiroNaArena(0);
        setDinheiroCompartilhado(0);
    }

    //========== MÉTODOS DE RODADAS ESPECIAIS ==========

}
