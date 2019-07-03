package caixaEletronico.controller;

import caixaEletronico.model.Extrato;
import caixaEletronico.model.dao.ExtratoDAO;
import caixaEletronico.view.ExtratoView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class ExtratoController {
    
    private ExtratoView view;
    
    
    
    /**
     * Busca os registros de extrato no banco de dados e preenche a tabela do Frame ExtratoView com eles.
     * @param view Frame contendo a tabela a ser preenchida.
     */
    public void carregarTabela(ExtratoView view) {
        
        this.view = view;
        
        ArrayList<Extrato> extrato = ExtratoDAO.selectAll();
        DefaultTableModel model = (DefaultTableModel) this.view.getTblExtrato().getModel();
        
        for( Extrato e : extrato ){
            
            String data = e.getDataFormatada();
            String hora = e.getHoraFormatada();
            String operacao = e.getOperacao();
            String valorQtd = e.getValor();
            
            model.addRow( new Object[]{data+" "+hora, operacao, valorQtd} );
        }
        
        this.view.getTblExtrato().setModel( model );
    }
    
    /**
     * Insere um registro/atividade na tabela Extrato no banco de dados com data e hora atuais.
     * @param operacao Operação que foi realizada.
     * @param valorQtd Valor ou unidades de notas movimentadas.
     */
    void inserir(String operacao, String valorQtd) {
        
        String data = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
        
        ExtratoDAO.insertRegistro(data, hora, operacao, valorQtd);
        
    }
    
}
