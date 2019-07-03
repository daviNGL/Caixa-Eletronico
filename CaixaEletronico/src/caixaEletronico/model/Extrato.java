package caixaEletronico.model;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Extrato {
    
    private int id;
    private Date data;
    private Time hora;
    private String operacao;
    private String  valor;
    
    /**
     * Contrutor padrão.
     */
    public Extrato() {
    }
    
    /**
     * Construtor com data, hora, operacao e valor.
     * @param data Data da operação.
     * @param hora Hora da operação.
     * @param operacao Operação realizada.
     * @param valor Valor movimentado.
     */
    public Extrato(Date data, Time hora, String operacao, String valor) {
        this.data = data;
        this.hora = hora;
        this.operacao = operacao;
        this.valor = valor;
    }
    
    /**
     * Construtor com id data, hora, operacao e valor.
     * @param data Data da operação.
     * @param hora Hora da operação.
     * @param operacao Operação realizada.
     * @param valor Valor movimentado.
     */
    public Extrato(int id, Date data, Time hora, String operacao, String valor) {
        this(data, hora, operacao, valor);
        this.valor = valor;
    }
    
    //GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }
    
    public String getDataFormatada() {
        return( new SimpleDateFormat("dd/MM/yyyy").format(this.data) );
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }
    
    public String getHoraFormatada() {
        return( new SimpleDateFormat("HH:mm:ss").format(this.hora) );
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
