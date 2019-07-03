package caixaEletronico.model;

public class CotaMinima {
    
    private int valor;

    /**
     * Contrutor padrao.
     */
    public CotaMinima() {
    }
    
    /**
     * Contrutor com valor a ser setado na cota mínima.
     * @param valor 
     */
    public CotaMinima(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
