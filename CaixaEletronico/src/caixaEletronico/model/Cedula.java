package caixaEletronico.model;

public class Cedula {
    
    private int valor;
    private int quantidade;
    
    /**
     * Construtor vazio
     */
    public Cedula() {
    }
    
    /**
     * Construtor com parametros
     * @param valor Valor da cédula
     * @param quantidade Quantidade de cédulas do valor especificado
     */
    public Cedula(int valor, int quantidade) {
        this.valor = valor;
        this.quantidade = quantidade;
    }
    
    //GETTERS AND SETTERS
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    
}
