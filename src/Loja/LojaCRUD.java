package Loja;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LojaCRUD {

    private final LigaDB ligaDB;
    private final FaturaPDF faturaPDF;


    public LojaCRUD(LigaDB ligaDB) {
    this.ligaDB = ligaDB;
    this.faturaPDF = new FaturaPDF(ligaDB);
}

    // ── Clientes ─────────────────────────────────────────────
    public List<String> carregarNomesClientes() {
        List<String> nomes = new ArrayList<>();
        String sql = "SELECT nome_cliente FROM cliente ORDER BY nome_cliente";
        try (Connection conn = ligaDB.getConnect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                nomes.add(rs.getString("nome_cliente"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar clientes: " + e.getMessage());
        }
        return nomes;
    }

    public List<Integer> carregarIdsClientes() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT cod_cliente FROM cliente ORDER BY nome_cliente";
        try (Connection conn = ligaDB.getConnect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ids.add(rs.getInt("cod_cliente"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar IDs clientes: " + e.getMessage());
        }
        return ids;
    }

    // ── Produtos ────────────────────────────────────────────
    public List<Produto> carregarProdutos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT codProduto, nome, preco, imagem FROM produto";
        try (Connection conn = ligaDB.getConnect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Produto(
                        rs.getInt("codProduto"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getString("imagem")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar produtos: " + e.getMessage());
        }
        return lista;
    }
    

    // Classe interna para representar um produto
    public static class Produto {
        public int codigo;
        public String nome;
        public double preco;
        public String imagem;

        public Produto(int codigo, String nome, double preco, String imagem) {
            this.codigo = codigo;
            this.nome = nome;
            this.preco = preco;
            this.imagem = imagem;
        }
    }

    // ── Venda ───────────────────────────────────────────────
    public int inserirVenda(int idCliente, double total, Map<Integer, Integer> produtoLinhaMap,
                             DefaultTableModel cartModel) throws SQLException {
        String sqlVenda = "INSERT INTO venda (codCliente, dataVenda, total) VALUES (?, NOW(), ?)";
        String sqlItem = "INSERT INTO item_venda (codVenda, codProduto, quantidade, precoUnitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ligaDB.getConnect()) {
            conn.setAutoCommit(false);

            // Inserir venda
            int codVenda;
            try (PreparedStatement psVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {
                psVenda.setInt(1, idCliente);
                psVenda.setBigDecimal(2, BigDecimal.valueOf(total));
                psVenda.executeUpdate();
                ResultSet rs = psVenda.getGeneratedKeys();
                if (rs.next()) {
                    codVenda = rs.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter código da venda.");
                }
            }

            // Inserir itens
            try (PreparedStatement psItem = conn.prepareStatement(sqlItem)) {
                for (Map.Entry<Integer, Integer> entry : produtoLinhaMap.entrySet()) {
                    int codProduto = entry.getKey();
                    int linha = entry.getValue();
                    int qtd = (int) cartModel.getValueAt(linha, 2);
                    double precoUnit = Double.parseDouble(((String) cartModel.getValueAt(linha, 1)).replace(",", "."));
                    double subtotal = qtd * precoUnit;

                    psItem.setInt(1, codVenda);
                    psItem.setInt(2, codProduto);
                    psItem.setInt(3, qtd);
                    psItem.setBigDecimal(4, BigDecimal.valueOf(precoUnit));
                    psItem.setBigDecimal(5, BigDecimal.valueOf(subtotal));
                    psItem.executeUpdate();
                }
            }
            conn.commit();
            return codVenda;
        }
    }


    // ── Fatura ──────────────────────────────────────────────
    public void gerarFatura(int codVenda) {
    faturaPDF.gerarFatura(codVenda);
}
}