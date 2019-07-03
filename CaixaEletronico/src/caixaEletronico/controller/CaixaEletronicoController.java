package caixaEletronico.controller;

import caixaEletronico.model.dao.CedulaDAO;
import caixaEletronico.model.dao.CotaMinimaDAO;

public class CaixaEletronicoController {
    
    private final ExtratoController controladorExtrato = new ExtratoController();
    
    /**
     * Faz uma consulta nas cédulas guardadas no banco e gera um relátorio com a quantidade de cada valor.
     * @return Retorna uma String contendo o relatório das cédulas.
     */
    public String pegaRelatorioCedulas() {
        
        int notas2 = CedulaDAO.selectQuantidade(2);
        int notas5 = CedulaDAO.selectQuantidade(5);
        int notas10 = CedulaDAO.selectQuantidade(10);
        int notas20 = CedulaDAO.selectQuantidade(20);
        int notas50 = CedulaDAO.selectQuantidade(50);
        int notas100 = CedulaDAO.selectQuantidade(100);
        
        if( notas2 == -1 || notas5 == -1 || notas10 == -1 || notas20 == -1 || notas50 == -1 || notas100 == -1 )
            return("Ocorreu algum erro ao efetuar a consulta. Favor, tente novamente.");
        
        return("Cédula               Quantidade\n\n"
             + "-------------------------------------------\n"
             + "R$2.00                 "+notas2+" unidades.\n"
             + "-------------------------------------------\n"
             + "R$5.00                 "+notas5+" unidades.\n"
             + "-------------------------------------------\n"
             + "R$10.00               "+notas10+" unidades.\n"
             + "-------------------------------------------\n"
             + "R$20.00               "+notas20+" unidades.\n"
             + "-------------------------------------------\n"
             + "R$50.00               "+notas50+" unidades.\n"
             + "-------------------------------------------\n"
             + "R$100.00             "+notas100+" unidades.\n"
             + "-------------------------------------------\n");
    }
    
    /**
     * faz uma consulta nas cédulas guardadas no banco e calcula o valor total disponível.
     * @return Retorna uma String contendo o valor total disponível.
     */
    public String pegaValorTotalDisponivel() {
        
        int total = CedulaDAO.selectValorTotal();
        
        if( total == -1 ) return("Ocorreu algum erro no processo e não foi possível calcular o total.\n"
                + "Favor, tente novamente mais tarde.\n\n");
        
        return("Valor total disponível: R$"+total+".00\n\n");
    }
    
    /**
     * Busca no banco o valor atual da cota mínima.
     * @return Retorna o valor atual da cota mínima.
     */
    public String  pegaValorCotaMinima() {
        
        int valor = CotaMinimaDAO.selectValor();
        
        if( valor == -1 ) return("Nenhuma cota mínima inserida no momento, pressione 'Sim' para cadastrar uma agora.");
        
        return("Valor atual da cota mínima: R$"+valor+".00.");
    }
    
    /**
     * Altera o valor da cota mínima para o valor passado no parâmetro da função.
     * @param valor Valor a ser setado na cota mínima.
     * @return  Retorna uma String informando se alterou ou não.
     */
    public String alterarValorCotaMinima(int valor) {
        
        if(CotaMinimaDAO.updateValor(valor)) 
            return("Valor da cota mínima foi alterado para R$"+valor+".00 com sucesso!");
        
        return("Não foi possível alterar o valor da cota mínima. Favor, tente novamente mais tarde.");
    }
    
    /**
     * Simula a operação de saque em um caixa eletronico, calculando a menor quantidade possível de notas para tal ação
     * e decrementando elas da base de dados.
     * @param valor Valor a ser sacado.
     * @return Uma String informando as notas utilizadas no saque ou uma mensagem informando erro.
     */
    public String sacar(int valor) {
        
        //VERIFICA SE O CAIXA ESTA NO VALOR DA COTA MINIMA
        if( CedulaDAO.selectValorTotal() < CotaMinimaDAO.selectValor() )
            return("Saque negado!\n\nO saldo do caixa está abaixo da cota mínima.\nFavor, comunicar um operador.");
        
        //VERIFICA SE O VALOR A SER SACADO É MAIOR QUE O TOTAL DO CAIXA
        if( CedulaDAO.selectValorTotal() < valor )
            return("Saque não realizado por falta de cédulas.\nFavor, chame um operador.");
        
        int tot = valor;
        int notas100 = CedulaDAO.selectQuantidade(100);
        int notas50 = CedulaDAO.selectQuantidade(50);
        int notas20 = CedulaDAO.selectQuantidade(20);
        int notas10 = CedulaDAO.selectQuantidade(10);
        int notas5 = CedulaDAO.selectQuantidade(5);
        int notas2 = CedulaDAO.selectQuantidade(2);
        
        int n100 = valor/100 <= notas100 ? valor/100 : notas100;
        valor -= n100*100;
        int n50 = valor/50 <= notas50 ? valor/50 : notas50;
        valor -= n50*50;
        int n20 = valor/20 <= notas20 ? valor/20 : notas20;
        valor -= n20*20;
        int n10 = valor/10 <= notas10 ? valor/10 : notas10;
        valor -= n10*10;
        int n5 = valor/5 <= notas5 ? valor/5 : notas5;
        valor -= n5*5;
        int n2 = valor/2 <= notas2 ? valor/2 : notas2;
        valor -= n2*2;
        
        //VERIFICA SE FOI POSSIVEL SEPARAR AS NOTAS PARA O SAQUE
        if( n2+n5+n10+n20+n50+n100 > 30 )
            return("Saque negado!\n\n O saque demanda uma quantidade muito alta de notas, tente com outro valor.");
        
        if( valor == 1 )
            return("Saque negado!\n\n Não é possível sacar esse valor, aproximar o valor para cima ou para baixo.");
        
        if( valor != 0 )
            return("Saque negado!\n\n Não foi possível completar o saque, tente novamente mais tarde.");
        
        //NAO OCORREU NENHUM PROBLEMA, ENTAO:
        
        //ATUALIZA AS CEDULAS NO BANCO
        CedulaDAO.removerCedulas(100, n100);
        CedulaDAO.removerCedulas(50, n50);
        CedulaDAO.removerCedulas(20, n20);
        CedulaDAO.removerCedulas(10, n10);
        CedulaDAO.removerCedulas(5, n5);
        CedulaDAO.removerCedulas(2, n2);
        
        //PREPARA UMA RESPOSTA
        String resp = "Saque autorizado! Retire as cédulas: \n\n";
        
        if(n2 > 0) resp = resp.concat("Notas de R$2.00: " + n2 + " unidades.\n");
        if(n5 > 0) resp = resp.concat("Notas de R$5.00: " + n5 + " unidades.\n");
        if(n10 > 0) resp = resp.concat("Notas de R$10.00: " + n10 + " unidades.\n");
        if(n20 > 0) resp = resp.concat("Notas de R$20.00: " + n20 + " unidades.\n");
        if(n50 > 0) resp = resp.concat("Notas de R$50.00: " + n50 + " unidades.\n");
        if(n100 > 0) resp = resp.concat("Notas de R$100.00: " + n100 + " unidades.\n");
        
        //ADICIONA O REGISTRO DO SAQUE NO EXTRATO
        controladorExtrato.inserir("Saque.", "R$"+tot+".00");
        
        return(resp);
    }
    
    
    /**
     * Efetua a reposição das cédulas na base de dados.
     * @param valor Valor da nota a ser feita a reposição.
     * @param qtd Quantidade/unidades a serem repostas.
     * @return Uma String informando sucesso ou falha.
     */
    public String reposicaoDeCedulas(int valor, int qtd) {
        
        if(CedulaDAO.adicionarCedulas(valor, qtd)){
            controladorExtrato.inserir("Reposição de cédulas de R$"+valor, qtd+" unidades.");
            return(qtd + " cédulas de R$"+valor+".00 foram adicionadas com sucesso!");
        }
        
        return("Não foi possível completar a reposição, tente novamente mais tarde.");
    }
    
    
    
}
