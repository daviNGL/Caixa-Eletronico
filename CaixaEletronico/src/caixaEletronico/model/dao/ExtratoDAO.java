package caixaEletronico.model.dao;

import caixaEletronico.conexao.ConexaoFactory;
import caixaEletronico.model.Extrato;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class ExtratoDAO {
    
    private static final String SELECT_ALL = "SELECT * FROM extrato";
    private static final String INSERT_REGISTRO = "INSERT INTO extrato VALUES (DEFAULT, ?, ?, ?, ?)";
    
    public static ArrayList<Extrato> selectAll() {
        
        ArrayList<Extrato> lista = new ArrayList<>();
        
        try( Connection con = ConexaoFactory.getConexao();
                PreparedStatement pstm = con.prepareStatement(SELECT_ALL);
                ResultSet rs = pstm.executeQuery(); ){
            
            while(rs.next()){
                
                Date data = rs.getDate(2);
                Time hora = rs.getTime(3);
                String operacao = rs.getString(4);
                String valorQtd = rs.getString(5);
                
                lista.add( new Extrato(data, hora, operacao, valorQtd) );
            }
            
        
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return(lista);
    }

    public static boolean insertRegistro(String data, String hora, String operacao, String valorQtd) {
        
        try( Connection con = ConexaoFactory.getConexao();
                PreparedStatement pstm = con.prepareStatement(INSERT_REGISTRO); ){
            
            pstm.setString(1, data);
            pstm.setString(2, hora);
            pstm.setString(3, operacao);
            pstm.setString(4, valorQtd);
            
            if( pstm.executeUpdate() != 0 ) return(true);
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return(false);
    }
    
}
