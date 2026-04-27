
import java.awt.Color;
import javax.swing.JOptionPane;
import java.sql.Date;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Aluno1
 */
public class CL_add extends javax.swing.JFrame {
    public crud_db OP = new crud_db();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Pagina_Principal.class.getName());

    /**
     * Creates new form CL_add
     */
    public CL_add() {
        initComponents();
        CarregarDados();
    }

    private void CarregarDados(){
    if(OP.TestarLigacao()){
        online.setForeground(Color.green);
        online.setText("Ligação DB: Ativa");
    }else{
    online.setForeground(Color.red);
    online.setText("Ligação DB: Desativa");
    }
    }
    
    private boolean validarCampos() {
    // Limpa mensagens de erro (opcional: usar JLabel para exibir)
    String erro = "";

    // --- Nome (obrigatório, mínimo 3 caracteres) ---
    String nome = txtNome.getText().trim();
    if (nome.isEmpty()) {
        erro += "- Nome é obrigatório.\n";
    } else if (nome.length() < 3) {
        erro += "- Nome deve ter pelo menos 3 caracteres.\n";
    }

    // --- Categoria (opcional, mas se preenchido, sem números) ---
    String categoria = txtCategoria.getText().trim();
    if (!categoria.isEmpty() && !categoria.matches("[a-zA-ZÀ-ÿ\\s]+")) {
        erro += "- Categoria deve conter apenas letras.\n";
    }

    // --- Data de Nascimento (obrigatória, formato yyyy-MM-dd) ---
    String dataNascStr = txtDataNasc.getText().trim();
    if (dataNascStr.isEmpty()) {
        erro += "- Data de nascimento é obrigatória.\n";
    } else {
        try {
            java.sql.Date dataNasc = java.sql.Date.valueOf(dataNascStr);
            // Verifica se é uma data passada (não pode ser futura)
            if (dataNasc.after(new java.util.Date())) {
                erro += "- Data de nascimento não pode ser futura.\n";
            }
        } catch (IllegalArgumentException e) {
            erro += "- Data de nascimento inválida. Use o formato yyyy-mm-dd.\n";
        }
    }

    // --- NIF (obrigatório, 9 dígitos) ---
    String nifStr = txtNif.getText().trim();
    if (nifStr.isEmpty()) {
        erro += "- NIF é obrigatório.\n";
    } else {
        try {
            int nif = Integer.parseInt(nifStr);
            if (nifStr.length() != 9) {
                erro += "- NIF deve ter exatamente 9 dígitos.\n";
            }
        } catch (NumberFormatException e) {
            erro += "- NIF deve conter apenas números.\n";
        }
    }

    // --- Email (obrigatório e formato válido) ---
    String email = txtEmail.getText().trim();
    if (email.isEmpty()) {
        erro += "- Email é obrigatório.\n";
    } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        erro += "- Email inválido.\n";
    }

    // --- Gênero (obrigatório, M/F/Outro) ---
    String genero = txtGenero.getText().trim();
    if (genero.isEmpty()) {
        erro += "- Gênero é obrigatório.\n";
    } else if (!genero.matches("(?i)^(M|F|Masculino|Feminino|Outro)$")) {
        erro += "- Gênero inválido. Use M, F, Masculino, Feminino ou Outro.\n";
    }

    // --- Cartão de Identificação (obrigatório, numérico, 8-12 dígitos) ---
    String cartaoStr = txtCartaoId.getText().trim();
    if (cartaoStr.isEmpty()) {
        erro += "- Cartão de identificação é obrigatório.\n";
    } else {
        try {
            long cartao = Long.parseLong(cartaoStr);
            if (cartaoStr.length() < 8 || cartaoStr.length() > 12) {
                erro += "- Cartão de identificação deve ter entre 8 e 12 dígitos.\n";
            }
        } catch (NumberFormatException e) {
            erro += "- Cartão de identificação deve conter apenas números.\n";
        }
    }

    // --- Telefone (obrigatório, 9 dígitos, começa com 9) ---
    String telefoneStr = txtTelefone.getText().trim();
    if (telefoneStr.isEmpty()) {
        erro += "- Telefone é obrigatório.\n";
    } else {
        try {
            long telefone = Long.parseLong(telefoneStr);
            if (telefoneStr.length() != 9 || !telefoneStr.startsWith("9")) {
                erro += "- Telefone deve ter 9 dígitos e começar com 9.\n";
            }
        } catch (NumberFormatException e) {
            erro += "- Telefone deve conter apenas números.\n";
        }
    }

    // --- Redes Sociais (opcional, sem validação específica) ---
    // (pode deixar como está)

    // --- Morada (obrigatória, mínimo 5 caracteres) ---
    String morada = txtMorada.getText().trim();
    if (morada.isEmpty()) {
        erro += "- Morada é obrigatória.\n";
    } else if (morada.length() < 5) {
        erro += "- Morada deve ter pelo menos 5 caracteres.\n";
    }

    // --- Zona Postal (obrigatória, numérico, 4 dígitos) ---
    String zonaStr = txtZonaPostal.getText().trim();
    if (zonaStr.isEmpty()) {
        erro += "- Zona postal é obrigatória.\n";
    } else {
        try {
            int zona = Integer.parseInt(zonaStr);
            if (zonaStr.length() != 3) {
                erro += "- Zona postal deve ter 3 dígitos.\n";
            }
        } catch (NumberFormatException e) {
            erro += "- Zona postal deve conter apenas números.\n";
        }
    }

    // --- Código Postal (obrigatório, formato 0000-000) ---
    String codPostalStr = txtCodigoPostal.getText().trim();
    if (codPostalStr.isEmpty()) {
        erro += "- Código postal é obrigatório.\n";
    } else if (!codPostalStr.matches("\\d{4}-\\d{3}")) {
        erro += "- Código postal inválido. Use o formato 0000-000.\n";
    }

    // --- Nacionalidade (obrigatório, apenas letras) ---
    String nacionalidade = txtNacionalidade.getText().trim();
    if (nacionalidade.isEmpty()) {
        erro += "- Nacionalidade é obrigatória.\n";
    } else if (!nacionalidade.matches("[a-zA-ZÀ-ÿ\\s]+")) {
        erro += "- Nacionalidade deve conter apenas letras.\n";
    }

    // --- País (obrigatório, apenas letras) ---
    String pais = txtPais.getText().trim();
    if (pais.isEmpty()) {
        erro += "- País é obrigatório.\n";
    } else if (!pais.matches("[a-zA-ZÀ-ÿ\\s]+")) {
        erro += "- País deve conter apenas letras.\n";
    }

    // --- Data de Registo (obrigatória, formato yyyy-MM-dd, não futura) ---
    String dataRegistoStr = txtDataRegisto.getText().trim();
    if (dataRegistoStr.isEmpty()) {
        erro += "- Data de registo é obrigatória.\n";
    } else {
        try {
            java.sql.Date dataRegisto = java.sql.Date.valueOf(dataRegistoStr);
            if (dataRegisto.after(new java.util.Date())) {
                erro += "- Data de registo não pode ser futura.\n";
            }
        } catch (IllegalArgumentException e) {
            erro += "- Data de registo inválida. Use o formato yyyy-mm-dd.\n";
        }
    }

    // --- Imagem (opcional, mas se preenchido, validar extensão .jpg/.png) ---
    String imagem = txtimagem.getText().trim();
    if (!imagem.isEmpty() && !imagem.matches("(?i).*\\.(jpg|jpeg|png)$")) {
        erro += "- Imagem deve ser um arquivo .jpg, .jpeg ou .png.\n";
    }

    // --- OBS (opcional, sem validação) ---

    // Exibe erro se houver
    if (!erro.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Erros de validação:\n" + erro, "Dados inválidos", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    return true;
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Painel_Principal = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        formPanel = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblCategoria = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        lblDataNasc = new javax.swing.JLabel();
        lblNif = new javax.swing.JLabel();
        txtDataNasc = new javax.swing.JTextField();
        txtNif = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        lblGenero = new javax.swing.JLabel();
        lblcartaoId = new javax.swing.JLabel();
        lblTelefone = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtGenero = new javax.swing.JTextField();
        txtCartaoId = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        lblRedesSociais = new javax.swing.JLabel();
        txtRedesSociais = new javax.swing.JTextField();
        lblMorada = new javax.swing.JLabel();
        txtMorada = new javax.swing.JTextField();
        lblZonaPostal = new javax.swing.JLabel();
        lblCodigoPostal = new javax.swing.JLabel();
        txtZonaPostal = new javax.swing.JTextField();
        txtCodigoPostal = new javax.swing.JTextField();
        lblNacionalidade = new javax.swing.JLabel();
        lblPais = new javax.swing.JLabel();
        lblimagem = new javax.swing.JLabel();
        lblObs = new javax.swing.JLabel();
        txtNacionalidade = new javax.swing.JTextField();
        txtPais = new javax.swing.JTextField();
        txtimagem = new javax.swing.JTextField();
        txtObs = new javax.swing.JTextField();
        Voltar = new javax.swing.JButton();
        Enviar = new javax.swing.JButton();
        lblDataRegisto = new javax.swing.JLabel();
        txtDataRegisto = new javax.swing.JTextField();
        LimpaNome = new javax.swing.JButton();
        LimpaDat = new javax.swing.JButton();
        LimpaCat = new javax.swing.JButton();
        LimpaEmail = new javax.swing.JButton();
        LimpaNif = new javax.swing.JButton();
        LimparTelefone = new javax.swing.JButton();
        LimparRedesSOciais = new javax.swing.JButton();
        LimpaCartaoID = new javax.swing.JButton();
        LimpaGen = new javax.swing.JButton();
        LimparCodPostal = new javax.swing.JButton();
        LimparNascionalidade = new javax.swing.JButton();
        LimparMorada = new javax.swing.JButton();
        LimparZonaPostal = new javax.swing.JButton();
        LimparPais = new javax.swing.JButton();
        LimparOBS = new javax.swing.JButton();
        LimparImagem = new javax.swing.JButton();
        LimparDataRegisto = new javax.swing.JButton();
        lblLocalidade = new javax.swing.JLabel();
        txtLocalidade = new javax.swing.JTextField();
        LimparLocalidade = new javax.swing.JButton();
        footer = new javax.swing.JPanel();
        exit = new javax.swing.JButton();
        online = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(650, 730));
        setMinimumSize(new java.awt.Dimension(650, 730));
        setPreferredSize(new java.awt.Dimension(650, 730));

        Painel_Principal.setBackground(new java.awt.Color(0, 51, 102));
        Painel_Principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setBackground(new java.awt.Color(0, 153, 153));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Title.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        Title.setText("Adicionar Cliente");
        header.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 180, 40));

        Painel_Principal.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 620, 40));

        formPanel.setBackground(new java.awt.Color(0, 153, 153));
        formPanel.setMaximumSize(new java.awt.Dimension(600, 850));
        formPanel.setMinimumSize(new java.awt.Dimension(600, 850));
        formPanel.setPreferredSize(new java.awt.Dimension(600, 850));
        formPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNome.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblNome.setText("Nome");
        formPanel.add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 180, 25));
        formPanel.add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 260, 25));

        lblCategoria.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblCategoria.setText("Categoria");
        formPanel.add(lblCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 180, 25));

        txtCategoria.addActionListener(this::txtCategoriaActionPerformed);
        formPanel.add(txtCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 260, 25));

        lblDataNasc.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblDataNasc.setText("Data de Nascimento");
        formPanel.add(lblDataNasc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 180, 25));

        lblNif.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblNif.setText("NIF");
        formPanel.add(lblNif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 180, 25));
        formPanel.add(txtDataNasc, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 260, 25));
        formPanel.add(txtNif, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 260, 25));

        lblEmail.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblEmail.setText("Email");
        formPanel.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 180, 25));

        lblGenero.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblGenero.setText("Gênero");
        formPanel.add(lblGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 180, 25));

        lblcartaoId.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblcartaoId.setText("CartãoId");
        formPanel.add(lblcartaoId, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 180, 25));

        lblTelefone.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblTelefone.setText("Telefone");
        formPanel.add(lblTelefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 180, 25));
        formPanel.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 260, 25));
        formPanel.add(txtGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 260, 25));
        formPanel.add(txtCartaoId, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 260, 25));
        formPanel.add(txtTelefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 260, 25));

        lblRedesSociais.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblRedesSociais.setText("Redes Sociais");
        formPanel.add(lblRedesSociais, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 180, 25));
        formPanel.add(txtRedesSociais, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 260, 25));

        lblMorada.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblMorada.setText("Morada");
        formPanel.add(lblMorada, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 180, 25));
        formPanel.add(txtMorada, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 380, 260, 25));

        lblZonaPostal.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblZonaPostal.setText("Zona Postal");
        formPanel.add(lblZonaPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 180, 25));

        lblCodigoPostal.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblCodigoPostal.setText("Código Postal");
        formPanel.add(lblCodigoPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 180, 25));
        formPanel.add(txtZonaPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 260, 25));
        formPanel.add(txtCodigoPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 260, 25));

        lblNacionalidade.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblNacionalidade.setText("Nacionalidade");
        formPanel.add(lblNacionalidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 180, 25));

        lblPais.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblPais.setText("País");
        formPanel.add(lblPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 180, 25));

        lblimagem.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblimagem.setText("Imagem");
        formPanel.add(lblimagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 660, 180, 25));

        lblObs.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblObs.setText("OBS");
        formPanel.add(lblObs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 700, 180, 25));
        formPanel.add(txtNacionalidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 540, 260, 25));

        txtPais.addActionListener(this::txtPaisActionPerformed);
        formPanel.add(txtPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 580, 260, 25));
        formPanel.add(txtimagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 660, 260, 25));
        formPanel.add(txtObs, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 700, 260, 25));

        Voltar.setText("Voltar");
        Voltar.addActionListener(this::VoltarActionPerformed);
        formPanel.add(Voltar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 750, -1, -1));

        Enviar.setText("Enviar");
        Enviar.addActionListener(this::EnviarActionPerformed);
        formPanel.add(Enviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 750, -1, -1));

        lblDataRegisto.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblDataRegisto.setText("Data de Registo");
        formPanel.add(lblDataRegisto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, 180, 25));
        formPanel.add(txtDataRegisto, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 620, 260, 25));

        LimpaNome.setText("Limpar");
        LimpaNome.addActionListener(this::LimpaNomeActionPerformed);
        formPanel.add(LimpaNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, -1));

        LimpaDat.setText("Limpar");
        LimpaDat.addActionListener(this::LimpaDatActionPerformed);
        formPanel.add(LimpaDat, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, -1, -1));

        LimpaCat.setText("Limpar");
        LimpaCat.addActionListener(this::LimpaCatActionPerformed);
        formPanel.add(LimpaCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, -1, -1));

        LimpaEmail.setText("Limpar");
        LimpaEmail.addActionListener(this::LimpaEmailActionPerformed);
        formPanel.add(LimpaEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 180, -1, -1));

        LimpaNif.setText("Limpar");
        LimpaNif.addActionListener(this::LimpaNifActionPerformed);
        formPanel.add(LimpaNif, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, -1, -1));

        LimparTelefone.setText("Limpar");
        LimparTelefone.addActionListener(this::LimparTelefoneActionPerformed);
        formPanel.add(LimparTelefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, -1, -1));

        LimparRedesSOciais.setText("Limpar");
        LimparRedesSOciais.addActionListener(this::LimparRedesSOciaisActionPerformed);
        formPanel.add(LimparRedesSOciais, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, -1, -1));

        LimpaCartaoID.setText("Limpar");
        LimpaCartaoID.addActionListener(this::LimpaCartaoIDActionPerformed);
        formPanel.add(LimpaCartaoID, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 260, -1, -1));

        LimpaGen.setText("Limpar");
        LimpaGen.addActionListener(this::LimpaGenActionPerformed);
        formPanel.add(LimpaGen, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, -1, -1));

        LimparCodPostal.setText("Limpar");
        LimparCodPostal.addActionListener(this::LimparCodPostalActionPerformed);
        formPanel.add(LimparCodPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 460, -1, -1));

        LimparNascionalidade.setText("Limpar");
        LimparNascionalidade.addActionListener(this::LimparNascionalidadeActionPerformed);
        formPanel.add(LimparNascionalidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 540, -1, -1));

        LimparMorada.setText("Limpar");
        LimparMorada.addActionListener(this::LimparMoradaActionPerformed);
        formPanel.add(LimparMorada, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 380, -1, -1));

        LimparZonaPostal.setText("Limpar");
        LimparZonaPostal.addActionListener(this::LimparZonaPostalActionPerformed);
        formPanel.add(LimparZonaPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 420, -1, -1));

        LimparPais.setText("Limpar");
        LimparPais.addActionListener(this::LimparPaisActionPerformed);
        formPanel.add(LimparPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 580, -1, -1));

        LimparOBS.setText("Limpar");
        LimparOBS.addActionListener(this::LimparOBSActionPerformed);
        formPanel.add(LimparOBS, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 700, -1, -1));

        LimparImagem.setText("Limpar");
        LimparImagem.addActionListener(this::LimparImagemActionPerformed);
        formPanel.add(LimparImagem, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 660, -1, -1));

        LimparDataRegisto.setText("Limpar");
        LimparDataRegisto.addActionListener(this::LimparDataRegistoActionPerformed);
        formPanel.add(LimparDataRegisto, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 620, -1, -1));

        lblLocalidade.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        lblLocalidade.setText("Localidade");
        formPanel.add(lblLocalidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 180, 25));
        formPanel.add(txtLocalidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 500, 260, 25));

        LimparLocalidade.setText("Limpar");
        LimparLocalidade.addActionListener(this::LimparLocalidadeActionPerformed);
        formPanel.add(LimparLocalidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 500, -1, -1));

        scrollPane.setViewportView(formPanel);

        Painel_Principal.add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 600, 540));

        footer.setBackground(new java.awt.Color(0, 153, 153));
        footer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exit.setText("Sair");
        exit.addActionListener(this::exitActionPerformed);
        footer.add(exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 13, -1, 30));

        online.setText("Online");
        footer.add(online, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 0, 100, 50));

        jLabel1.setFont(new java.awt.Font("Cambria Math", 1, 18)); // NOI18N
        jLabel1.setText("BetuelRS");
        footer.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 2, 80, 50));

        jLabel2.setFont(new java.awt.Font("Cambria Math", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Conecção DB:");
        jLabel2.setAlignmentY(0.0F);
        footer.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, -1, 50));

        jLabel3.setFont(new java.awt.Font("Cambria Math", 1, 18)); // NOI18N
        jLabel3.setText("Exercícios Java V2");
        footer.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 2, -1, 50));

        Painel_Principal.add(footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, 610, 50));

        menu_principal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/home.png"))); // NOI18N
        menu_principal.setMnemonic('P');
        menu_principal.setText("Principal");
        menu_principal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_principalMouseClicked(evt);
            }
        });
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

        setJMenuBar(Barra_Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Painel_Principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Painel_Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarActionPerformed
        dispose();
        new Pagina_Principal().setVisible(true);
    }//GEN-LAST:event_VoltarActionPerformed

    private void EnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarActionPerformed
    if (!validarCampos()) {
    return; // não envia se houver erro
    }
        
    OP.insertCliente(
    txtNome.getText(), 
    txtCategoria.getText(), 
    java.sql.Date.valueOf(txtDataNasc.getText()),   // String -> Date
    Integer.parseInt(txtNif.getText()), 
    txtEmail.getText(), 
    txtGenero.getText(), 
    Integer.parseInt(txtCartaoId.getText()), 
    Integer.parseInt(txtTelefone.getText()), 
    txtRedesSociais.getText(), 
    txtMorada.getText(), 
    Integer.parseInt(txtZonaPostal.getText()), 
    Integer.parseInt(txtCodigoPostal.getText()),
    txtLocalidade.getText(),
    txtNacionalidade.getText(), 
    txtPais.getText(), 
    java.sql.Date.valueOf(txtDataRegisto.getText()), // String -> Date
    txtimagem.getText(), 
    txtObs.getText()
);
    }//GEN-LAST:event_EnviarActionPerformed

    private void menu_principalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_principalMouseClicked
        dispose();
        new Pagina_Principal().setVisible(true);
    }//GEN-LAST:event_menu_principalMouseClicked

    private void Gestao_Novo_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Gestao_Novo_ClienteActionPerformed
        dispose();
        new CL_add().setVisible(true);
    }//GEN-LAST:event_Gestao_Novo_ClienteActionPerformed

    private void Gestao_Listar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Gestao_Listar_ClienteActionPerformed
        dispose();
        new CL_listar().setVisible(true);
    }//GEN-LAST:event_Gestao_Listar_ClienteActionPerformed

    private void Gestao_Editar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Gestao_Editar_ClienteActionPerformed
        dispose();
        new CL_updt().setVisible(true);
    }//GEN-LAST:event_Gestao_Editar_ClienteActionPerformed

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

    private void menu_sairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_sairMouseClicked
        System.exit(0);
    }//GEN-LAST:event_menu_sairMouseClicked

    private void txtCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoriaActionPerformed

    private void txtPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaisActionPerformed

    private void LimpaNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpaNomeActionPerformed
    txtNome.setText("");
    }//GEN-LAST:event_LimpaNomeActionPerformed

    private void LimpaCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpaCatActionPerformed
        txtCategoria.setText("");
    }//GEN-LAST:event_LimpaCatActionPerformed

    private void LimpaDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpaDatActionPerformed
        txtDataNasc.setText("");
    }//GEN-LAST:event_LimpaDatActionPerformed

    private void LimpaNifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpaNifActionPerformed
        txtNif.setText("");
    }//GEN-LAST:event_LimpaNifActionPerformed

    private void LimpaEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpaEmailActionPerformed
        txtEmail.setText("");
    }//GEN-LAST:event_LimpaEmailActionPerformed

    private void LimpaGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpaGenActionPerformed
        txtGenero.setText("");
    }//GEN-LAST:event_LimpaGenActionPerformed

    private void LimpaCartaoIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpaCartaoIDActionPerformed
        txtCartaoId.setText("");
    }//GEN-LAST:event_LimpaCartaoIDActionPerformed

    private void LimparTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparTelefoneActionPerformed
        txtTelefone.setText("");
        
    }//GEN-LAST:event_LimparTelefoneActionPerformed

    private void LimparRedesSOciaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparRedesSOciaisActionPerformed
       txtRedesSociais.setText("");
    }//GEN-LAST:event_LimparRedesSOciaisActionPerformed

    private void LimparMoradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparMoradaActionPerformed
        txtMorada.setText("");
    }//GEN-LAST:event_LimparMoradaActionPerformed

    private void LimparZonaPostalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparZonaPostalActionPerformed
        txtZonaPostal.setText("");
    }//GEN-LAST:event_LimparZonaPostalActionPerformed

    private void LimparCodPostalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparCodPostalActionPerformed
        txtCodigoPostal.setText("");
    }//GEN-LAST:event_LimparCodPostalActionPerformed

    private void LimparLocalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparLocalidadeActionPerformed
        txtLocalidade.setText("");
    }//GEN-LAST:event_LimparLocalidadeActionPerformed

    private void LimparNascionalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparNascionalidadeActionPerformed
        txtNacionalidade.setText("");
    }//GEN-LAST:event_LimparNascionalidadeActionPerformed

    private void LimparPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparPaisActionPerformed
        txtPais.setText("");
    }//GEN-LAST:event_LimparPaisActionPerformed

    private void LimparDataRegistoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparDataRegistoActionPerformed
        txtDataRegisto.setText("");
    }//GEN-LAST:event_LimparDataRegistoActionPerformed

    private void LimparImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparImagemActionPerformed
        txtimagem.setText("");
    }//GEN-LAST:event_LimparImagemActionPerformed

    private void LimparOBSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimparOBSActionPerformed
        txtObs.setText("");
    }//GEN-LAST:event_LimparOBSActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new CL_add().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Barra_Menu;
    private javax.swing.JMenuItem EditarSensor;
    private javax.swing.JButton Enviar;
    private javax.swing.JMenuItem Gestao_Editar_Cliente;
    private javax.swing.JMenuItem Gestao_Listar_Cliente;
    private javax.swing.JMenuItem Gestao_Listar_Produto;
    private javax.swing.JMenuItem Gestao_Novo_Cliente;
    private javax.swing.JMenuItem Gestao_Novo_Produto;
    private javax.swing.JPopupMenu.Separator Gestao_Separador;
    private javax.swing.JButton LimpaCartaoID;
    private javax.swing.JButton LimpaCat;
    private javax.swing.JButton LimpaDat;
    private javax.swing.JButton LimpaEmail;
    private javax.swing.JButton LimpaGen;
    private javax.swing.JButton LimpaNif;
    private javax.swing.JButton LimpaNome;
    private javax.swing.JButton LimparCodPostal;
    private javax.swing.JButton LimparDataRegisto;
    private javax.swing.JButton LimparImagem;
    private javax.swing.JButton LimparLocalidade;
    private javax.swing.JButton LimparMorada;
    private javax.swing.JButton LimparNascionalidade;
    private javax.swing.JButton LimparOBS;
    private javax.swing.JButton LimparPais;
    private javax.swing.JButton LimparRedesSOciais;
    private javax.swing.JButton LimparTelefone;
    private javax.swing.JButton LimparZonaPostal;
    private javax.swing.JMenuItem ListarSensor;
    private javax.swing.JMenuItem NovoSensor;
    private javax.swing.JPanel Painel_Principal;
    private javax.swing.JLabel Title;
    private javax.swing.JButton Voltar;
    private javax.swing.JButton exit;
    private javax.swing.JPanel footer;
    private javax.swing.JPanel formPanel;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCodigoPostal;
    private javax.swing.JLabel lblDataNasc;
    private javax.swing.JLabel lblDataRegisto;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblLocalidade;
    private javax.swing.JLabel lblMorada;
    private javax.swing.JLabel lblNacionalidade;
    private javax.swing.JLabel lblNif;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblObs;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblRedesSociais;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblZonaPostal;
    private javax.swing.JLabel lblcartaoId;
    private javax.swing.JLabel lblimagem;
    private javax.swing.JMenu menu_gestao;
    private javax.swing.JMenu menu_principal;
    private javax.swing.JMenu menu_sair;
    private javax.swing.JMenu menu_sensor;
    private javax.swing.JMenu menu_sobre;
    private javax.swing.JLabel online;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextField txtCartaoId;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtDataNasc;
    private javax.swing.JTextField txtDataRegisto;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtGenero;
    private javax.swing.JTextField txtLocalidade;
    private javax.swing.JTextField txtMorada;
    private javax.swing.JTextField txtNacionalidade;
    private javax.swing.JTextField txtNif;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtObs;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtRedesSociais;
    private javax.swing.JTextField txtTelefone;
    private javax.swing.JTextField txtZonaPostal;
    private javax.swing.JTextField txtimagem;
    // End of variables declaration//GEN-END:variables
}
