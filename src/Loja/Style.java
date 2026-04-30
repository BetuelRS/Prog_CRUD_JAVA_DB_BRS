package Loja;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Style {

    // Paleta de cores moderna
    public static final Color COR_FUNDO = new Color(245, 247, 250);
    public static final Color COR_PRIMARIA = new Color(33, 66, 99);
    public static final Color COR_BOTAO = new Color(44, 88, 132);
    public static final Color COR_BOTAO_HOVER = new Color(66, 120, 170);
    public static final Color COR_TEXTO = new Color(50, 50, 50);
    public static final Color COR_CARD_FUNDO = Color.WHITE;
    public static final Color COR_CARD_BORDA = new Color(210, 210, 210);
    public static final Color COR_CARD_HOVER_BORDA = COR_PRIMARIA;

    // Fontes
    public static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONTE_PRECO = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONTE_BOTAO = new Font("Segoe UI", Font.PLAIN, 12);

    /**
     * Aplica look and feel moderno e cores base.
     */
    public static void aplicarTema() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // mantém o default se falhar
        }
        // Pode forçar cores de alguns componentes via UIManager.put(...)
    }

    /**
     * Estiliza um botão com cor, padding e efeito hover.
     */
    public static void estilizarBotao(JButton botao) {
        botao.setFont(FONTE_BOTAO);
        botao.setBackground(COR_BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(COR_BOTAO_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(COR_BOTAO);
            }
        });
    }

    /**
     * Cria um card de produto moderno.
     *
     * @param nome   Nome do produto (pode ser HTML para quebra de linha)
     * @param preco  String formatada do preço (ex: "439.90 €")
     * @param icon   Imagem do produto
     * @param btnAdd Botão "Adicionar" já estilizado e com ActionListener
     * @return JPanel configurado
     */
    public static JPanel criarCardProduto(String nome, String preco, ImageIcon icon, JButton btnAdd) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COR_CARD_FUNDO);

        // Borda composta: linha cinza + margem interna
        Border borderNormal = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_CARD_BORDA, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
        Border borderHover = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_CARD_HOVER_BORDA, 2),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)   // ajusta para manter o mesmo tamanho
        );
        card.setBorder(borderNormal);

        card.setPreferredSize(new Dimension(180, 260));
        card.setMaximumSize(new Dimension(200, 280));

        // Componentes
        JLabel lblImagem = new JLabel(icon);
        lblImagem.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nome com quebra automática (HTML)
        JLabel lblNome = new JLabel("<html><div style='text-align:center;'>" + nome + "</div></html>");
        lblNome.setFont(FONTE_NORMAL);
        lblNome.setForeground(COR_TEXTO);
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPreco = new JLabel(preco);
        lblPreco.setFont(FONTE_PRECO);
        lblPreco.setForeground(COR_PRIMARIA);
        lblPreco.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão já estilizado
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdd.setForeground(COR_PRIMARIA);

        // Montagem com espaçamentos
        card.add(Box.createVerticalStrut(5));
        card.add(lblImagem);
        card.add(Box.createVerticalStrut(10));
        card.add(lblNome);
        card.add(Box.createVerticalStrut(8));
        card.add(lblPreco);
        card.add(Box.createVerticalGlue());
        card.add(btnAdd);
        card.add(Box.createVerticalStrut(5));

        // Efeito hover no card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(borderHover);
                card.setBackground(new Color(240, 245, 255)); // leve azulado
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(borderNormal);
                card.setBackground(COR_CARD_FUNDO);
            }
        });

        return card;
    }

    /**
     * Aplica estilos básicos à tabela do carrinho.
     */
    public static void estilizarTabela(JTable table) {
        table.setFont(FONTE_NORMAL);
        table.getTableHeader().setFont(FONTE_NORMAL);
        table.setRowHeight(26);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 2));
    }
}