package Loja;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class FaturaPDF {

    // Constantes de estilo
    private static final BaseColor COR_EMPRESA = new BaseColor(33, 66, 99);   // azul escuro
    private static final BaseColor COR_TABELA_CABECALHO = new BaseColor(44, 88, 132);
    private static final BaseColor COR_TABELA_LINHA_PAR = new BaseColor(230, 240, 255);

    private final LigaDB ligaDB;

    public FaturaPDF(LigaDB ligaDB) {
        this.ligaDB = ligaDB;
    }

    public void gerarFatura(int codVenda) {
        String numeroFatura = "";
        String nomePdf = "";
        Document document = null;

        try (Connection conn = ligaDB.getConnect()) {
            conn.setAutoCommit(false);

            // 1. Dados da venda e cliente
            String sqlVenda = "SELECT v.dataVenda, v.total, c.nome_cliente, c.nif, c.morada, c.cod_postal, c.localidade, c.email "
                    + "FROM venda v JOIN cliente c ON v.codCliente = c.cod_cliente WHERE v.codVenda = ?";
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

            // 2. Número da fatura
            String sqlNum = "SELECT MAX(codFatura) FROM fatura";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlNum)) {
                int ultimaFatura = rs.next() ? rs.getInt(1) : 0;
                int nextId = ultimaFatura + 1;
                String year = new SimpleDateFormat("yyyy").format(new Date());
                numeroFatura = "FT " + year + "/" + String.format("%04d", nextId);
            }

            // 3. Inserir na BD (placeholder)
            String sqlInsert = "INSERT INTO fatura (codVenda, numeroFatura, dataFatura, total, nomePdf) VALUES (?, ?, NOW(), ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setInt(1, codVenda);
                ps.setString(2, numeroFatura);
                ps.setDouble(3, total);
                ps.setString(4, "temp.pdf");
                ps.executeUpdate();
            }

            // 4. Diretório e nome ficheiro
            Path faturasDir = Paths.get("faturas");
            if (!Files.exists(faturasDir)) Files.createDirectory(faturasDir);
            nomePdf = "faturas/" + numeroFatura.replace("/", "_") + ".pdf";

            // 5. Atualizar nomePdf
            try (PreparedStatement ps = conn.prepareStatement("UPDATE fatura SET nomePdf = ? WHERE numeroFatura = ?")) {
                ps.setString(1, nomePdf);
                ps.setString(2, numeroFatura);
                ps.executeUpdate();
            }

            conn.commit();

            // 6. Gerar PDF formatado
            document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomePdf));
            document.open();

            // Cabeçalho da empresa
            Paragraph header = new Paragraph();
            header.setAlignment(Element.ALIGN_CENTER);
            header.add(new Chunk("TECHCOMPONENTES, LDA\n", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, COR_EMPRESA)));
            header.add(new Chunk("Rua das Tecnologias, 123 – 4000-123 Porto\n"
                    + "NIF: 500 123 456 | Tel: 222 333 444\n"
                    + "email: geral@techcomponentes.pt\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY)));
            document.add(header);

            // Linha separadora
            LineSeparator linha = new LineSeparator(1.5f, 100, COR_EMPRESA, Element.ALIGN_CENTER, 0);
            document.add(linha);

            // Título "FATURA"
            Paragraph titulo = new Paragraph("FATURA",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, COR_EMPRESA));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingBefore(10f);
            titulo.setSpacingAfter(5f);
            document.add(titulo);

            // Número e Data lado a lado
            PdfPTable infoFat = new PdfPTable(2);
            infoFat.setWidthPercentage(100);
            infoFat.setSpacingBefore(5f);
            infoFat.setSpacingAfter(10f);
            infoFat.addCell(celulaInfo("Nº Fatura: " + numeroFatura, Element.ALIGN_LEFT));
            infoFat.addCell(celulaInfo("Data: " + dataVenda, Element.ALIGN_RIGHT));
            document.add(infoFat);

            // Título "DADOS DO CLIENTE"
            Paragraph clienteTitulo = new Paragraph("DADOS DO CLIENTE",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, COR_EMPRESA));
            clienteTitulo.setSpacingAfter(5f);
            document.add(clienteTitulo);

            document.add(new Paragraph(nomeCliente, FontFactory.getFont(FontFactory.HELVETICA, 11)));
            document.add(new Paragraph("NIF: " + nif, FontFactory.getFont(FontFactory.HELVETICA, 11)));
            document.add(new Paragraph("Morada: " + morada + ", " + codPostalNum + " " + localidade, 
                    FontFactory.getFont(FontFactory.HELVETICA, 11)));
            document.add(new Paragraph("Email: " + email, FontFactory.getFont(FontFactory.HELVETICA, 11)));
            document.add(Chunk.NEWLINE);

            // Tabela de itens
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{45, 10, 20, 25});

            // Cabeçalhos da tabela com cor
            Font fontCabecalho = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
            adicionarCelulaCabecalho(table, "Produto", fontCabecalho);
            adicionarCelulaCabecalho(table, "Qtd", fontCabecalho);
            adicionarCelulaCabecalho(table, "Preço Unit.", fontCabecalho);
            adicionarCelulaCabecalho(table, "Subtotal", fontCabecalho);

            // Dados dos itens (alternando cores)
            String sqlItens = "SELECT iv.quantidade, iv.precoUnitario, iv.subtotal, p.nome "
                    + "FROM item_venda iv JOIN produto p ON iv.codProduto = p.codProduto WHERE iv.codVenda = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlItens)) {
                ps.setInt(1, codVenda);
                ResultSet rs = ps.executeQuery();
                int linhaAtual = 0;
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    int qtd = rs.getInt("quantidade");
                    double preco = rs.getDouble("precoUnitario");
                    double subtotal = rs.getDouble("subtotal");

                    BaseColor bg = (linhaAtual % 2 == 0) ? COR_TABELA_LINHA_PAR : BaseColor.WHITE;
                    Font fontLinha = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

                    PdfPCell c1 = new PdfPCell(new Phrase(nome, fontLinha));
                    c1.setBackgroundColor(bg);
                    c1.setPadding(5);
                    PdfPCell c2 = new PdfPCell(new Phrase(String.valueOf(qtd), fontLinha));
                    c2.setBackgroundColor(bg);
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c2.setPadding(5);
                    PdfPCell c3 = new PdfPCell(new Phrase(String.format("%.2f €", preco), fontLinha));
                    c3.setBackgroundColor(bg);
                    c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    c3.setPadding(5);
                    PdfPCell c4 = new PdfPCell(new Phrase(String.format("%.2f €", subtotal), fontLinha));
                    c4.setBackgroundColor(bg);
                    c4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    c4.setPadding(5);

                    table.addCell(c1);
                    table.addCell(c2);
                    table.addCell(c3);
                    table.addCell(c4);
                    linhaAtual++;
                }
            }
            document.add(table);

            // Total e observações
            Paragraph totalP = new Paragraph("TOTAL: " + String.format("%.2f €", total),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, COR_EMPRESA));
            totalP.setAlignment(Element.ALIGN_RIGHT);
            totalP.setSpacingBefore(10f);
            document.add(totalP);

            // Linha final
            document.add(new Paragraph("\n", FontFactory.getFont(FontFactory.HELVETICA, 6)));
            LineSeparator linhaFim = new LineSeparator(0.8f, 100, BaseColor.LIGHT_GRAY, Element.ALIGN_CENTER, 0);
            document.add(linhaFim);
            document.add(new Paragraph("Obrigado pela sua preferência.\nTechComponentes, Lda – Soluções em Hardware",
                    FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.GRAY)));

            document.close();

            // Abrir PDF
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new java.io.File(nomePdf));
            }

            JOptionPane.showMessageDialog(null, "Fatura " + numeroFatura + " gerada com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar fatura: " + e.getMessage());
            e.printStackTrace();
            if (document != null && document.isOpen()) document.close();
        }
    }

    // Métodos auxiliares de formatação
    private PdfPCell celulaInfo(String texto, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(texto,
                FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.DARK_GRAY)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(align);
        return cell;
    }

    private void adicionarCelulaCabecalho(PdfPTable table, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBackgroundColor(COR_TABELA_CABECALHO);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6);
        table.addCell(cell);
    }
}