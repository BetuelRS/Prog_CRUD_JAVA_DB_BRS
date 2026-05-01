/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Loja;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import java.sql.SQLException;

/**
 *
 * @author Aluno1
 */

public class Loja extends javax.swing.JFrame {
    
    private LigaDB ligaDB;
    private LojaCRUD crud;
    private DefaultTableModel cartModel;
    private double totalCart = 0.0;
    private Map<Integer, Integer> produtoLinhaMap = new HashMap<>();
    private List<Integer> clienteIds = new ArrayList<>();
    private int ultimoCodVenda = 0;

    
        /**
     * Creates new form Loja
     */
    public Loja() {
        initComponents();
    Style.aplicarTema();                     // Tema moderno

    ligaDB = new LigaDB();
    crud = new LojaCRUD(ligaDB);
    configurarTabelaCarrinho();
    carregarClientes();
    carregarProdutos();

    // Estilizar botões principais
    Style.estilizarBotao(btnEmitirVenda);
    Style.estilizarBotao(btnEmitirFatura);
    Style.estilizarBotao(btnLimparCarrinho);
    Style.estilizarBotao(btnRemoverItem);
    // Se tiver outros botões (Sair, Voltar) também pode estilizar
    Style.estilizarBotao(jButton1);
    Style.estilizarBotao(jButton2);

    // Estilizar tabela
    Style.estilizarTabela(tableCart);

    // Permitir seleção múltipla
    tableCart.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    // Listeners (já existentes)
    btnRemoverItem.addActionListener(e -> removerItensSelecionados());
    btnLimparCarrinho.addActionListener(e -> limparCarrinho());

    // Tornar fundo do frame e do grid ligeiramente cinza
    getContentPane().setBackground(Style.COR_FUNDO);
    productGridPanel.setBackground(Style.COR_FUNDO);
     // se o JScrollPane tiver nome, ajuste
    jScrollPane2.getViewport().setBackground(Style.COR_FUNDO);
    // Aumentar ligeiramente o cartPanel
    bottomPanel.setPreferredSize(new java.awt.Dimension(350, 120));
        }
    
    private void removerItensSelecionados() {
    int[] selectedRows = tableCart.getSelectedRows();
    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(this, "Selecione pelo menos um item para remover.");
        return;
    }
    // Remover da última para a primeira (para não deslocar índices)
    for (int i = selectedRows.length - 1; i >= 0; i--) {
        cartModel.removeRow(selectedRows[i]);
    }
    // Reconstruir o mapa produto -> linha
    rebuildProdutoLinhaMap();
    atualizarTotal();
}
    private void limparCarrinho() {
    cartModel.setRowCount(0);
    produtoLinhaMap.clear();
    atualizarTotal();
}
    
    // ── CONFIGURAÇÃO DA TABELA DO CARRINHO ──────────────────────────
    private void configurarTabelaCarrinho() {
    String[] colunas = {"Produto", "Preço Unit.", "Qtd", "Subtotal", "CodProd"};
    cartModel = new DefaultTableModel(colunas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tableCart.setModel(cartModel);
    // Esconder a coluna do código do produto
    tableCart.getColumnModel().getColumn(4).setMinWidth(0);
    tableCart.getColumnModel().getColumn(4).setMaxWidth(0);
    tableCart.getColumnModel().getColumn(4).setWidth(0);
    // Larguras das outras colunas
    tableCart.getColumnModel().getColumn(0).setPreferredWidth(150);
    tableCart.getColumnModel().getColumn(1).setPreferredWidth(80);
    tableCart.getColumnModel().getColumn(2).setPreferredWidth(50);
    tableCart.getColumnModel().getColumn(3).setPreferredWidth(80);
}
     // ── CARREGAR CLIENTES ───────────────────────────────────────────
        private void carregarClientes() {
    List<String> nifNome = crud.carregarNifNomeClientes();
    clienteIds = crud.carregarIdsClientes(); // IDs continuam na mesma ordem
    for (String texto : nifNome) {
        comboClientes.addItem(texto);
    }
}
      // ── CARREGAR PRODUTOS (COM IMAGENS) ─────────────────────────────
        private void carregarProdutos() {
    List<LojaCRUD.Produto> produtos = crud.carregarProdutos();
    for (LojaCRUD.Produto p : produtos) {
        ImageIcon icon = carregarImagem(p.imagem);
        JPanel card = criarPainelProduto(p.codigo, p.nome, p.preco, icon);
        productGridPanel.add(card);
    }
    productGridPanel.revalidate();
    productGridPanel.repaint();
}
        private JPanel criarPainelProduto(int codigo, String nome, double preco, ImageIcon icon) {
    JButton btnAdd = new JButton("Adicionar");
    Style.estilizarBotao(btnAdd);
    btnAdd.addActionListener(e -> adicionarAoCarrinho(codigo, nome, preco));
    return Style.criarCardProduto(nome, String.format("%.2f €", preco), icon, btnAdd);
}

        private void emitirVenda() {
        if (comboClientes.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }
        if (cartModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "O carrinho está vazio.");
            return;
        }
        int idCliente = clienteIds.get(comboClientes.getSelectedIndex());
        try {
            ultimoCodVenda = crud.inserirVenda(idCliente, totalCart, produtoLinhaMap, cartModel);
            JOptionPane.showMessageDialog(this, "Venda realizada com sucesso! (Código: " + ultimoCodVenda + ")");
            limparCarrinho();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao emitir venda: " + e.getMessage());
        }
    }

    private void emitirFatura() {
        if (ultimoCodVenda == 0) {
            JOptionPane.showMessageDialog(this, "Nenhuma venda realizada nesta sessão.");
            return;
        }
        crud.gerarFatura(ultimoCodVenda);
    }

    // Método auxiliar para carregar imagem a partir do caminho guardado na BD
    private ImageIcon carregarImagem(String nomeGuardadoNaBD) {
    try {
        // Remove eventual caminho extra guardado (ex: "imagem/CLEDI.png" → "CLEDI.png")
        String nomeFicheiro = new java.io.File(nomeGuardadoNaBD).getName(); // só o nome do ficheiro
        java.net.URL url = getClass().getResource("/imagens/" + nomeFicheiro);
        if (url == null) {
            throw new Exception("Imagem não encontrada no classpath: " + nomeFicheiro);
        }
        ImageIcon original = new ImageIcon(url);
        Image scaled = original.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    } catch (Exception e) {
        // Placeholder 100% autónomo – não precisa de nenhum ficheiro!
        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(100, 100, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g = bi.createGraphics();
        g.setColor(java.awt.Color.LIGHT_GRAY);
        g.fillRect(0, 0, 100, 100);
        g.setColor(java.awt.Color.BLACK);
        g.drawString("Sem imagem", 15, 55);
        g.dispose();
        return new ImageIcon(bi);
    }
}
    private void adicionarAoCarrinho(int codProduto, String nome, double preco) {
        if (produtoLinhaMap.containsKey(codProduto)) {
            // Produto já existe → incrementar quantidade e atualizar subtotal
            int linha = produtoLinhaMap.get(codProduto);
            int qtd = (int) cartModel.getValueAt(linha, 2) + 1;
            double subtotal = qtd * preco;
            cartModel.setValueAt(qtd, linha, 2);
            cartModel.setValueAt(String.format("%.2f", subtotal), linha, 3);
        } else {
            // Novo produto → adicionar linha
            Object[] row = {nome, String.format("%.2f", preco), 1, String.format("%.2f", preco), codProduto};
            cartModel.addRow(row);
            int linha = cartModel.getRowCount() - 1;
            produtoLinhaMap.put(codProduto, linha);
        }
        atualizarTotal();
    }
    
    private void rebuildProdutoLinhaMap() {
    produtoLinhaMap.clear();
    for (int i = 0; i < cartModel.getRowCount(); i++) {
        int cod = (int) cartModel.getValueAt(i, 4); // coluna do código
        produtoLinhaMap.put(cod, i);
    }
}
    // ── ATUALIZAR TOTAL DO CARRINHO ─────────────────────────────────
    private void atualizarTotal() {
        totalCart = 0.0;
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String subtotalStr = (String) cartModel.getValueAt(i, 3);
            totalCart += Double.parseDouble(subtotalStr.replace(",", "."));
        }
        lblTotal.setText(String.format("%.2f €", totalCart));
    }
    // ── EMITIR VENDA ────────────────────────────────────────────────
    private void gerarFatura(int codVenda) {
    String numeroFatura = "";
    String nomePdf = "";
    Document document = null;

    try (Connection conn = ligaDB.getConnect()) {
        conn.setAutoCommit(false);

        // 1. Obter dados da venda e do cliente
        String sqlVenda = "SELECT v.dataVenda, v.total, c.nome_cliente, c.nif, c.morada, c.cod_postal, c.localidade, c.email " +
                         "FROM venda v JOIN cliente c ON v.codCliente = c.cod_cliente WHERE v.codVenda = ?";
        String dataVenda = "";
        double total = 0;
        String nomeCliente = "";
        int nif = 0;
        String morada = "";
        int codPostalNum = 0;
        String localidade = "";
        String email = "";

        try (PreparedStatement ps = conn.prepareStatement(sqlVenda)) {
            ps.setInt(1, codVenda);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dataVenda = rs.getString("dataVenda");
                total = rs.getDouble("total");
                nomeCliente = rs.getString("nome_cliente");
                nif = rs.getInt("nif");
                morada = rs.getString("morada");
                codPostalNum = rs.getInt("cod_postal");
                localidade = rs.getString("localidade");
                email = rs.getString("email");
            } else {
                throw new Exception("Venda não encontrada.");
            }
        }

        // 2. Gerar número da fatura (ex: FT 2025/0001)
        String sqlNum = "SELECT MAX(codFatura) FROM fatura";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlNum)) {
            int ultimaFatura = 0;
            if (rs.next()) {
                ultimaFatura = rs.getInt(1);
            }
            int nextId = ultimaFatura + 1;
            String year = new SimpleDateFormat("yyyy").format(new Date());
            numeroFatura = "FT " + year + "/" + String.format("%04d", nextId);
        }

        // 3. Inserir registo na tabela fatura (inicialmente com nomePdf provisório)
        String sqlInsert = "INSERT INTO fatura (codVenda, numeroFatura, dataFatura, total, nomePdf) VALUES (?, ?, NOW(), ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setInt(1, codVenda);
            ps.setString(2, numeroFatura);
            ps.setDouble(3, total);
            ps.setString(4, "temp.pdf"); // placeholder
            ps.executeUpdate();
        }

        // 4. Definir o caminho do PDF (pasta "faturas" na raiz do projeto)
        Path faturasDir = Paths.get("faturas");
        if (!Files.exists(faturasDir)) {
            Files.createDirectory(faturasDir);
        }
        nomePdf = "faturas/" + numeroFatura.replace("/", "_") + ".pdf";

        // 5. Atualizar o nomePdf na BD
        try (PreparedStatement ps = conn.prepareStatement("UPDATE fatura SET nomePdf = ? WHERE numeroFatura = ?")) {
            ps.setString(1, nomePdf);
            ps.setString(2, numeroFatura);
            ps.executeUpdate();
        }

        conn.commit();

        // 6. Criar o PDF com iText
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(nomePdf));
        document.open();

        // Estilos básicos
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Font fontCabecalho = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        // Cabeçalho
        document.add(new Paragraph("FATURA", fontTitulo));
        document.add(new Paragraph("Nº: " + numeroFatura, fontNormal));
        document.add(new Paragraph("Data: " + dataVenda, fontNormal));
        document.add(Chunk.NEWLINE);

        // Dados do cliente
        document.add(new Paragraph("Cliente: " + nomeCliente, fontNormal));
        document.add(new Paragraph("NIF: " + nif, fontNormal));
        document.add(new Paragraph("Morada: " + morada + ", " + codPostalNum + " " + localidade, fontNormal));
        document.add(new Paragraph("Email: " + email, fontNormal));
        document.add(Chunk.NEWLINE);

        // Tabela de itens
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Cabeçalhos da tabela
        table.addCell(new Phrase("Produto", fontCabecalho));
        table.addCell(new Phrase("Qtd", fontCabecalho));
        table.addCell(new Phrase("Preço Unit.", fontCabecalho));
        table.addCell(new Phrase("Subtotal", fontCabecalho));

        // Obter itens da venda (com nome do produto)
        String sqlItens = "SELECT iv.quantidade, iv.precoUnitario, iv.subtotal, p.nome " +
                          "FROM item_venda iv JOIN produto p ON iv.codProduto = p.codProduto " +
                          "WHERE iv.codVenda = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlItens)) {
            ps.setInt(1, codVenda);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nomeProduto = rs.getString("nome");
                int qtd = rs.getInt("quantidade");
                double preco = rs.getDouble("precoUnitario");
                double subtotal = rs.getDouble("subtotal");

                table.addCell(new Phrase(nomeProduto, fontNormal));
                table.addCell(new Phrase(String.valueOf(qtd), fontNormal));
                table.addCell(new Phrase(String.format("%.2f €", preco), fontNormal));
                table.addCell(new Phrase(String.format("%.2f €", subtotal), fontNormal));
            }
        }
        document.add(table);

        // Total
        Paragraph totalParagraph = new Paragraph("Total: " + String.format("%.2f €", total), fontTitulo);
        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalParagraph);

        document.close();

        // 7. Abrir o PDF automaticamente
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(new java.io.File(nomePdf));
        }

        JOptionPane.showMessageDialog(this, "Fatura " + numeroFatura + " gerada com sucesso!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao gerar fatura: " + e.getMessage());
        e.printStackTrace();
        if (document != null && document.isOpen()) {
            document.close();
        }
    }
}

    // ── MÉTODO MAIN (opcional, para testes) ─────────────────────────
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> new Loja().setVisible(true));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cartPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        cliente = new javax.swing.JLabel();
        comboClientes = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCart = new javax.swing.JTable();
        bottomPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnEmitirVenda = new javax.swing.JButton();
        btnEmitirFatura = new javax.swing.JButton();
        btnLimparCarrinho = new javax.swing.JButton();
        btnRemoverItem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        productGridPanel = new javax.swing.JPanel();
        footer = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Loja");
        setAutoRequestFocus(false);
        setPreferredSize(new java.awt.Dimension(1200, 700));

        cartPanel.setPreferredSize(new java.awt.Dimension(350, 350));
        cartPanel.setLayout(new java.awt.BorderLayout());

        topPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        topPanel.setName(""); // NOI18N

        cliente.setText("Cliente: ");
        topPanel.add(cliente);

        topPanel.add(comboClientes);

        cartPanel.add(topPanel, java.awt.BorderLayout.NORTH);

        tableCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableCart);

        cartPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Total: ");
        bottomPanel.add(jLabel1);

        lblTotal.setText("0,00");
        bottomPanel.add(lblTotal);

        btnEmitirVenda.setText("Emitir Venda");
        btnEmitirVenda.addActionListener(this::btnEmitirVendaActionPerformed);
        bottomPanel.add(btnEmitirVenda);

        btnEmitirFatura.setText("Emitir Fatura");
        btnEmitirFatura.addActionListener(this::btnEmitirFaturaActionPerformed);
        bottomPanel.add(btnEmitirFatura);

        btnLimparCarrinho.setText("Limpar carrinho");
        bottomPanel.add(btnLimparCarrinho);

        btnRemoverItem.setText("Remover selecionado");
        bottomPanel.add(btnRemoverItem);

        cartPanel.add(bottomPanel, java.awt.BorderLayout.SOUTH);

        getContentPane().add(cartPanel, java.awt.BorderLayout.WEST);

        productGridPanel.setLayout(new java.awt.GridLayout(0, 4, 10, 10));
        jScrollPane2.setViewportView(productGridPanel);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButton1.setText("Sair");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        footer.add(jButton1);

        jButton2.setText("Voltar");
        jButton2.addActionListener(this::jButton2ActionPerformed);
        footer.add(jButton2);

        getContentPane().add(footer, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEmitirVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmitirVendaActionPerformed
        emitirVenda();
    }//GEN-LAST:event_btnEmitirVendaActionPerformed

    private void btnEmitirFaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmitirFaturaActionPerformed
        emitirFatura();
    }//GEN-LAST:event_btnEmitirFaturaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     dispose();
     new PRP.Pagina_Principal().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton btnEmitirFatura;
    private javax.swing.JButton btnEmitirVenda;
    private javax.swing.JButton btnLimparCarrinho;
    private javax.swing.JButton btnRemoverItem;
    private javax.swing.JPanel cartPanel;
    private javax.swing.JLabel cliente;
    private javax.swing.JComboBox<String> comboClientes;
    private javax.swing.JPanel footer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel productGridPanel;
    private javax.swing.JTable tableCart;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
