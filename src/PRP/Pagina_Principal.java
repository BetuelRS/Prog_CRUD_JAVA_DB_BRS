package PRP;


import java.awt.Color;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.UIManager;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Aluno1
 */
public class Pagina_Principal extends javax.swing.JFrame {
    
    public crud_db OP = new crud_db();
    public crud_db Crud = new crud_db();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Pagina_Principal.class.getName());

    
    /**
     * Creates new form Pagina_Principal
     */
    
    
    public Pagina_Principal() {
        initComponents();
        
        CarregarDados();
        loadTable_Sensores();
        loadTable_Clientes();
        loadTable_Zonas();
        loadTable_Categorias();
        int totalClientes = OP.contarClientes();
    TotCLientes.setText(String.valueOf(totalClientes));
    carregarAnalytics();
    }
    
//Setar a label TotCLientes com o total de clientes usando a função contarClientes
    
    String totaaaaaaL;
    
    
    private void CarregarDados(){
    if(OP.TestarLigacao()){
        online.setForeground(Color.green);
        online.setText("Ligação DB: Ativa");
    }else{
    online.setForeground(Color.red);
    online.setText("Ligação DB: Desativa");
    }
    }
    
   private void carregarAnalytics() {
    try (Connection conn = new LigaDB().getConnect()) {
        // 1. Produto mais vendido
        String sqlProduto = "SELECT p.nome, SUM(iv.quantidade) AS total_qtd " +
                            "FROM item_venda iv JOIN produto p ON iv.codProduto = p.codProduto " +
                            "GROUP BY p.nome ORDER BY total_qtd DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sqlProduto);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String nome = rs.getString("nome");
                int qtd = rs.getInt("total_qtd");
                lblProdutoMaisVendido.setText(" " + nome + " (" + qtd + " un.)");
            } else {
                lblProdutoMaisVendido.setText("Produto mais vendido: (sem dados)");
            }
        }

        // 2. Cliente com maior volume de compras
        String sqlCliente = "SELECT c.nome_cliente, SUM(v.total) AS total_gasto " +
                            "FROM venda v JOIN cliente c ON v.codCliente = c.cod_cliente " +
                            "GROUP BY c.nome_cliente ORDER BY total_gasto DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sqlCliente);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String nome = rs.getString("nome_cliente");
                double total = rs.getDouble("total_gasto");
                lblClienteMaiorVolume.setText("" + nome + " (" +
                                              String.format("%.2f €", total) + ")");
            } else {
                lblClienteMaiorVolume.setText("Cliente com maior volume: (sem dados)");
            }
        }

        // 3. Média de valor por venda
        String sqlMedia = "SELECT AVG(total) AS media FROM venda";
        try (PreparedStatement stmt = conn.prepareStatement(sqlMedia);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                double media = rs.getDouble("media");
                if (rs.wasNull()) {
                    lblMediaVenda.setText("Média de valor por venda: (sem dados)");
                } else {
                    lblMediaVenda.setText("" + String.format("%.2f €", media));
                }
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar análises: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
            
    public void loadTable_Sensores(){
        DefaultTableModel model_tab_sensor = (DefaultTableModel) Table_sensores.getModel();
        model_tab_sensor.setNumRows(0);
        try {
            for(Object[] line: OP.getAllSensor()){
                model_tab_sensor.addRow(line);
            }
        } catch (SQLException ex) {
            System.getLogger(Pagina_Principal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void loadTable_Zonas() {
    // Supondo que você tenha uma JTable chamada "Table_Zonas" com duas colunas: "Zona Postal" e "Quantidade"
    DefaultTableModel model = (DefaultTableModel) Table_Zonas.getModel();
    model.setNumRows(0); // limpa a tabela

    List<Object[]> dados = OP.contarClientesPorZona();
    for (Object[] linha : dados) {
        model.addRow(linha);
    }
}
    
    //Tabela de contagem do genero pela função contarClientesPorGenero
    public void loadTable_Clientes() {
    DefaultTableModel model_tab_cliente = (DefaultTableModel) Table_CL.getModel();
    model_tab_cliente.setNumRows(0); // limpa a tabela
    
    Object[] linha = OP.contarClientesPorGenero(); // retorna [masculino, feminino, outros]
    model_tab_cliente.addRow(linha);
}

    public void loadTable_Categorias() {
    // Supondo que você tenha uma JTable chamada "Table_Categorias" com duas colunas: "Categoria" e "Quantidade"
    DefaultTableModel model = (DefaultTableModel) Table_Categorias.getModel();
    model.setNumRows(0); // limpa a tabela

    List<Object[]> dados = OP.contarClientesPorCategoria();
    for (Object[] linha : dados) {
        model.addRow(linha);
    }
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        Painel_Principal = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        exit = new javax.swing.JButton();
        online = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_sensores = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_CL = new javax.swing.JTable();
        TotCLientes = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_Zonas = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        Table_Categorias = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        analyticsPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblProdutoMaisVendido = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblClienteMaiorVolume = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblMediaVenda = new javax.swing.JLabel();
        Barra_Menu = new javax.swing.JMenuBar();
        menu_principal = new javax.swing.JMenu();
        menu_gestao = new javax.swing.JMenu();
        Gestao_Novo_Cliente = new javax.swing.JMenuItem();
        Gestao_Listar_Cliente = new javax.swing.JMenuItem();
        Gestao_Editar_Cliente = new javax.swing.JMenuItem();
        Gestao_Separador = new javax.swing.JPopupMenu.Separator();
        Gestao_Novo_Produto = new javax.swing.JMenuItem();
        Gestao_Listar_Produto = new javax.swing.JMenuItem();
        menu_sensor = new javax.swing.JMenu();
        NovoSensor = new javax.swing.JMenuItem();
        ListarSensor = new javax.swing.JMenuItem();
        EditarSensor = new javax.swing.JMenuItem();
        menu_sobre = new javax.swing.JMenu();
        menu_sair = new javax.swing.JMenu();
        Loja = new javax.swing.JMenu();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(860, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Painel_Principal.setBackground(new java.awt.Color(0, 51, 102));
        Painel_Principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exit.setText("Sair");
        exit.addActionListener(this::exitActionPerformed);
        jPanel1.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, -1));

        online.setText("Online");
        jPanel1.add(online, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        Painel_Principal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 820, 40));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Table_sensores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome", "Tipo", "Unidade"
            }
        ));
        jScrollPane1.setViewportView(Table_sensores);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 230, 160));

        jLabel1.setText("Sensores");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));

        Painel_Principal.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 250, 410));

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Table_CL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Masculino", "Feminino", "Outro"
            }
        ));
        jScrollPane2.setViewportView(Table_CL);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 230, 50));

        TotCLientes.setText("Clientes:");
        jPanel3.add(TotCLientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, -1, -1));

        Table_Zonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Zona Postal", "Localidade", "Quantidade"
            }
        ));
        jScrollPane3.setViewportView(Table_Zonas);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 230, 160));

        Table_Categorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Categoria", "Quantidade"
            }
        ));
        jScrollPane4.setViewportView(Table_Categorias);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 230, 100));

        jLabel3.setText("Clientes:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        Painel_Principal.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 250, 410));

        analyticsPanel.setBackground(new java.awt.Color(0, 153, 153));

        jLabel2.setText("Produto mais vendido :");
        analyticsPanel.add(jLabel2);

        lblProdutoMaisVendido.setText("\tProduto mais vendido: ...");
        lblProdutoMaisVendido.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblProdutoMaisVendido.setVerifyInputWhenFocusTarget(false);
        lblProdutoMaisVendido.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        analyticsPanel.add(lblProdutoMaisVendido);

        jLabel4.setText("Cliente com maior volume: ");
        analyticsPanel.add(jLabel4);

        lblClienteMaiorVolume.setText("Cliente com maior volume: ...");
        analyticsPanel.add(lblClienteMaiorVolume);

        jLabel6.setText("Média de valor por venda: ");
        jLabel6.setAlignmentX(2.0F);
        jLabel6.setAlignmentY(2.0F);
        analyticsPanel.add(jLabel6);

        lblMediaVenda.setText("Média de valor por venda: ...");
        analyticsPanel.add(lblMediaVenda);

        Painel_Principal.add(analyticsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 280, 410));

        getContentPane().add(Painel_Principal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 510));

        menu_principal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/home.png"))); // NOI18N
        menu_principal.setMnemonic('P');
        menu_principal.setText("Principal");
        Barra_Menu.add(menu_principal);

        menu_gestao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/gestao.png"))); // NOI18N
        menu_gestao.setMnemonic('G');
        menu_gestao.setText("Gestão");

        Gestao_Novo_Cliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Gestao_Novo_Cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/NC.png"))); // NOI18N
        Gestao_Novo_Cliente.setMnemonic('C');
        Gestao_Novo_Cliente.setText("Novo Cliente");
        Gestao_Novo_Cliente.addActionListener(this::Gestao_Novo_ClienteActionPerformed);
        menu_gestao.add(Gestao_Novo_Cliente);

        Gestao_Listar_Cliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Gestao_Listar_Cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/LC.png"))); // NOI18N
        Gestao_Listar_Cliente.setMnemonic('L');
        Gestao_Listar_Cliente.setText("Listar Cliente");
        Gestao_Listar_Cliente.addActionListener(this::Gestao_Listar_ClienteActionPerformed);
        menu_gestao.add(Gestao_Listar_Cliente);

        Gestao_Editar_Cliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Gestao_Editar_Cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/CLEDI.png"))); // NOI18N
        Gestao_Editar_Cliente.setText("Editar Cliente");
        Gestao_Editar_Cliente.addActionListener(this::Gestao_Editar_ClienteActionPerformed);
        menu_gestao.add(Gestao_Editar_Cliente);
        menu_gestao.add(Gestao_Separador);

        Gestao_Novo_Produto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Gestao_Novo_Produto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/NP.png"))); // NOI18N
        Gestao_Novo_Produto.setMnemonic('N');
        Gestao_Novo_Produto.setText("Novo Produto");
        menu_gestao.add(Gestao_Novo_Produto);

        Gestao_Listar_Produto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Gestao_Listar_Produto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/LP.png"))); // NOI18N
        Gestao_Listar_Produto.setMnemonic('R');
        Gestao_Listar_Produto.setText("Listar Produto");
        menu_gestao.add(Gestao_Listar_Produto);

        Barra_Menu.add(menu_gestao);

        menu_sensor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Sensor.png"))); // NOI18N
        menu_sensor.setMnemonic('S');
        menu_sensor.setText("Sensor");

        NovoSensor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        NovoSensor.setText("Novo Sensor");
        NovoSensor.addActionListener(this::NovoSensorActionPerformed);
        menu_sensor.add(NovoSensor);

        ListarSensor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        ListarSensor.setText("Listar Sensor");
        ListarSensor.addActionListener(this::ListarSensorActionPerformed);
        menu_sensor.add(ListarSensor);

        EditarSensor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        EditarSensor.setText("Editar Sensor");
        EditarSensor.addActionListener(this::EditarSensorActionPerformed);
        menu_sensor.add(EditarSensor);

        Barra_Menu.add(menu_sensor);

        menu_sobre.setIcon(new javax.swing.ImageIcon("C:\\Users\\betue\\Downloads\\round.png")); // NOI18N
        menu_sobre.setMnemonic('O');
        menu_sobre.setText("Sobre");
        Barra_Menu.add(menu_sobre);

        menu_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/sair.png"))); // NOI18N
        menu_sair.setMnemonic('A');
        menu_sair.setText("Sair");
        menu_sair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_sairMouseClicked(evt);
            }
        });
        Barra_Menu.add(menu_sair);

        Loja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Loja.png"))); // NOI18N
        Loja.setMnemonic('L');
        Loja.setText("Loja");
        Loja.setToolTipText("");
        Loja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LojaMouseClicked(evt);
            }
        });
        Barra_Menu.add(Loja);

        setJMenuBar(Barra_Menu);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void menu_sairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_sairMouseClicked
        System.exit(0);
    }//GEN-LAST:event_menu_sairMouseClicked

    private void Gestao_Novo_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Gestao_Novo_ClienteActionPerformed
        dispose();
        new CL_add().setVisible(true);
    }//GEN-LAST:event_Gestao_Novo_ClienteActionPerformed

    private void NovoSensorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NovoSensorActionPerformed
        dispose();
        new SN_add().setVisible(true);
    }//GEN-LAST:event_NovoSensorActionPerformed

    private void ListarSensorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListarSensorActionPerformed
        
    }//GEN-LAST:event_ListarSensorActionPerformed

    private void EditarSensorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarSensorActionPerformed
        dispose();
        new SN_updt().setVisible(true);
    }//GEN-LAST:event_EditarSensorActionPerformed

    private void Gestao_Listar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Gestao_Listar_ClienteActionPerformed
        dispose();
        new CL_listar().setVisible(true);
    }//GEN-LAST:event_Gestao_Listar_ClienteActionPerformed

    private void Gestao_Editar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Gestao_Editar_ClienteActionPerformed
        dispose();
        new CL_updt().setVisible(true);
    }//GEN-LAST:event_Gestao_Editar_ClienteActionPerformed

    private void LojaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LojaMouseClicked
        dispose();
        new Loja.Loja().setVisible(true);
    }//GEN-LAST:event_LojaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Pagina_Principal().setVisible(true));
        

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Barra_Menu;
    private javax.swing.JMenuItem EditarSensor;
    private javax.swing.JMenuItem Gestao_Editar_Cliente;
    private javax.swing.JMenuItem Gestao_Listar_Cliente;
    private javax.swing.JMenuItem Gestao_Listar_Produto;
    private javax.swing.JMenuItem Gestao_Novo_Cliente;
    private javax.swing.JMenuItem Gestao_Novo_Produto;
    private javax.swing.JPopupMenu.Separator Gestao_Separador;
    private javax.swing.JMenuItem ListarSensor;
    private javax.swing.JMenu Loja;
    private javax.swing.JMenuItem NovoSensor;
    private javax.swing.JPanel Painel_Principal;
    private javax.swing.JTable Table_CL;
    private javax.swing.JTable Table_Categorias;
    private javax.swing.JTable Table_Zonas;
    private javax.swing.JTable Table_sensores;
    private javax.swing.JLabel TotCLientes;
    private javax.swing.JPanel analyticsPanel;
    private javax.swing.JButton exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblClienteMaiorVolume;
    private javax.swing.JLabel lblMediaVenda;
    private javax.swing.JLabel lblProdutoMaisVendido;
    private javax.swing.JMenu menu_gestao;
    private javax.swing.JMenu menu_principal;
    private javax.swing.JMenu menu_sair;
    private javax.swing.JMenu menu_sensor;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JLabel online;
    // End of variables declaration//GEN-END:variables
}
