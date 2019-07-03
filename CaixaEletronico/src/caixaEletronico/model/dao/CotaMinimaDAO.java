package caixaEletronico.model.dao;

import caixaEletronico.conexao.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CotaMinimaDAO {
    
    private static final String SELECT_VALOR = "SELECT valor FROM cota_minima LIMIT 1";
    private static final String UPDATE_VALOR = "UPDATE cota_minima SET valor=?";
    
    /**
     * Busca no banco o valor atual da cota mínima;
     * @return Retorna o valor atual ou -1 se ocorrer algum erro.
     */
    public static int selectValor() {
        
        try( Connection con = ConexaoFactory.getConexao(); 
                PreparedStatement pstm = con.prepareStatement(SELECT_VALOR);
                ResultSet rs = pstm.executeQuery()){
            
            if( rs.next() ) return( rs.getInt(1) );
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return(-1);
    }
    
    /**
     * Seta o valor da cota mínima no banco de dados.
     * @param valor Valor a ser setado no vanco.
     * @return True se alterar o valor, False se não alterar.
     */
    public static boolean updateValor(int valor) {
        
        try( Connection con = ConexaoFactory.getConexao();
                PreparedStatement pstm = con.prepareStatement(UPDATE_VALOR); ){
            
            pstm.setInt(1, valor);
            
            if( pstm.executeUpdate() != 0 ) return(true);
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return(false);
    }
    
}
