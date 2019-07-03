package caixaEletronico.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    
    private static final String URL = "jdbc:mysql://localhost:3306/caixa_eletronico?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";
    
    /**
     * Abre e retorna uma conexão com a base de dados "caixa_eletronico"
     * @return Retorna a conexão com o bd "caixa_eletronico"
     */
    public static Connection getConexao(){
        
        try {
            return( DriverManager.getConnection(URL, USUARIO, SENHA) );
        } catch (SQLException ex) {
            ex.printStackTrace();
            return(null);
        }
    }
}
