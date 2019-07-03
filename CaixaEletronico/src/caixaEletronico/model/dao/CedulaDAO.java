package caixaEletronico.model.dao;

import caixaEletronico.conexao.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CedulaDAO {
    
    private static final String SELECT_QTD = "SELECT quantidade FROM cedula WHERE valor=?";
    private static final String SELECT_VALOR_TOTAL = "SELECT SUM(valor*quantidade) FROM cedula";
    private static final String DECREASE_CEDULA = "UPDATE cedula SET quantidade=quantidade-? WHERE valor=?";
    private static final String INCREASE_CEDULA = "UPDATE cedula SET quantidade=quantidade+? WHERE valor=?";
    
    /**
     * Busca a quantidade de determinada cédula no banco de dados.
     * @param valor Valor da cédula para buscar sua quantidade.
     * @return A quantidade ou -1 se algum erro ocorrer.
     */
    public static int selectQuantidade(int valor) {
        
        //INICIALIZA O RESULT SET 
        ResultSet rs = null;
        
        //ABRE A CONEXAO E PRERA O STATEMENT
        try( Connection con = ConexaoFactory.getConexao(); 
                PreparedStatement pstm = con.prepareStatement(SELECT_QTD);){
            
            pstm.setInt(1, valor);
            rs = pstm.executeQuery();
            
            //VERIFICA SE TEM RESULTADO NO RESULT SET
            if( rs.next() ) return( rs.getInt("quantidade") );
            return(-1);
            
        }catch(SQLException ex){
            ex.printStackTrace();
            return(-1);
            
        }finally{ try {
            rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
}
        
    }

    
    /**
     * Calcula a soma da quantidade de todas as cédulas do banco.
     * @return Retorna a soma ou -1  se ocorrer algum erro.
     */
    public static int selectValorTotal() {
        
        
        try( Connection con = ConexaoFactory.getConexao();
                PreparedStatement pstm = con.prepareStatement(SELECT_VALOR_TOTAL);
                ResultSet rs = pstm.executeQuery(); ){
            
            if( rs.next() ) return( rs.getInt(1) );
            return(-1);
        
        }catch(SQLException ex){
            ex.printStackTrace();
            return(-1);
        }
        
    }

    public static void removerCedulas(int cedula, int qtd) {
        
        try( Connection con = ConexaoFactory.getConexao();
                PreparedStatement pstm = con.prepareStatement(DECREASE_CEDULA)){
    
            pstm.setInt(1, qtd);
            pstm.setInt(2, cedula);
            
            pstm.executeUpdate();
    
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public static boolean adicionarCedulas(int cedula, int qtd) {
        
        try( Connection con = ConexaoFactory.getConexao();
                PreparedStatement pstm = con.prepareStatement(INCREASE_CEDULA)){
    
            pstm.setInt(1, qtd);
            pstm.setInt(2, cedula);
            
            if(pstm.executeUpdate() != 0) return(true);
    
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return(false);
    }
    
    
}
