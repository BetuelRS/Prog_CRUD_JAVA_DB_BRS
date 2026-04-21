/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Aluno1
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;


public class crud_db {
    
    LigaDB conectar = new LigaDB();
    // Métodos para CRUD de leituras
    public void insertLeitura(int cod_sensor, float valor, Date data_hora){
        String sql = "insert into leituras values(null, ?, ?, ?)";
        try{
            Connection conn = conectar.getConnect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cod_sensor);
            stmt.setFloat(2, valor);
            stmt.setTimestamp(3, new java.sql.Timestamp(data_hora.getTime()));
            
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            System.getLogger(crud_db.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    // Métodos para CRUD de sensores
    public void insertSensor(String nome, String tipo, int unidade){
        String sql = "insert into sensor values(null, ?, ?, ?)";
        try { 
            Connection conn = conectar.getConnect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, tipo);
            stmt.setInt(3, unidade);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(crud_db.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    public void deleteSensor(int cod_sensor){
        String sql = "delete from sensor where cod_sensor = ?";
        try { 
            Connection conn = conectar.getConnect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cod_sensor);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(crud_db.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    public void updateSensor(int cod_sensor, String nome, String tipo, int unidade){
        String sql = "update sensor set nome = ?, tipo = ?, unidade = ? where cod_sensor = ?";
        try { 
            Connection conn = conectar.getConnect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, tipo);
            stmt.setInt(3, unidade);
            stmt.setInt(4, cod_sensor);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(crud_db.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    public ArrayList<Object[]> getAllSensor() throws SQLException {
    ArrayList<Object[]> dados = new ArrayList<>();

    String sql = "SELECT * FROM sensor";

    try {
        Connection conn = conectar.getConnect();
        PreparedStatement ps = conn.prepareStatement(sql);
        try(ResultSet result = ps.executeQuery()){

        while (result.next()) {
            Object[] row = new Object[]{
                result.getString("nome"),
                result.getString("tipo"),
                result.getInt("Unidade")
            };
            dados.add(row);
            }
        }
    } catch (SQLException ex) {
        System.getLogger(crud_db.class.getName())
              .log(System.Logger.Level.ERROR, (String) null, ex);
    }

    return dados;
}
    // Método para testar a ligação à base de dados
    public boolean TestarLigacao(){
            try {
                Connection conn = conectar.getConnect();
                System.out.println("Ligação OK: "+ conn.getCatalog());
                return true;
            }catch( SQLException ex){
                System.out.println("Falha na ligação: ");
                ex.printStackTrace();
                return false;
            }
        }
    
        // Método para CRUD clientes
    public void insertCliente(String nome_cliente, String categoria_cliente, Date data_nasc, int nif, String email, String genero, int cartao_identificacao, int numero_telefone, String redes_sociais, String morada, int zona_postal, int cod_postal, String nacionalidade, String pais, Date data_registo, String imagem, String OBS){
        String sql = "insert into cliente values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = conectar.getConnect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome_cliente);
            stmt.setString(2, categoria_cliente);
            stmt.setDate(3, (java.sql.Date) data_nasc);
            stmt.setInt(4, nif);
            stmt.setString(5, email);
            stmt.setString(6, genero);
            stmt.setInt(7, cartao_identificacao);
            stmt.setInt(8, numero_telefone);
            stmt.setString(9, redes_sociais);
            stmt.setString(10, morada);
            stmt.setInt(11, zona_postal);
            stmt.setInt(12, cod_postal);
            stmt.setString(13, nacionalidade);
            stmt.setString(14, pais);
            stmt.setDate(15, (java.sql.Date) data_registo);
            stmt.setString(16, imagem);
            stmt.setString(17, OBS);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(crud_db.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    public void deleteCliente(int cod_cliente){
        String sql = "delete from cliente where cod_cliente = ?";
        try { 
            Connection conn = conectar.getConnect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cod_cliente);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(crud_db.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

//Método para atuallizar o cliente


    
    public void updateCliente(
        int cod_cliente,
        String nome_cliente,
        String categoria_cliente,
        Date data_nasc,
        int nif, 
        String email, 
        String genero, 
        int cartao_identificacao, 
        int numero_telefone, 
        String redes_sociais, 
        String morada, 
        int zona_postal, 
        int cod_postal, 
        String nacionalidade, 
        String pais, 
        Date data_registo, 
        String imagem, String OBS){
        String sql = "update cliente set nome_cliente = ?, categoria_cliente = ?, data_nasc = ?, nif = ?, email = ?, genero = ?, cartao_identificacao = ?, numero_telefone = ?, redes_sociais = ?, morada = ?, zona_postal = ?, cod_postal = ?, nacionalidade = ?, pais = ?, data_registo = ?, imagem = ?, OBS = ? where cod_cliente = ?";
        try { 
            Connection conn = conectar.getConnect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome_cliente);
            stmt.setString(2, categoria_cliente);
            stmt.setDate(3, (java.sql.Date) data_nasc);
            stmt.setInt(4, nif);
            stmt.setString(5, email);
            stmt.setString(6, genero);
            stmt.setInt(7, cartao_identificacao);
            stmt.setInt(8, numero_telefone);
            stmt.setString(9, redes_sociais);
            stmt.setString(10, morada);
            stmt.setInt(11, zona_postal);
            stmt.setInt(12, cod_postal);
            stmt.setString(13, nacionalidade);
            stmt.setString(14, pais);
            stmt.setDate(15, (java.sql.Date) data_registo);
            stmt.setString(16, imagem);
            stmt.setString(17, OBS);
            stmt.setInt(18, cod_cliente);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(crud_db.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public ArrayList<Object[]> getAllCliente() throws SQLException {
    ArrayList<Object[]> dados = new ArrayList<>();
    String sql = "SELECT * FROM cliente";
    Connection conn = conectar.getConnect();
    PreparedStatement stmt = conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {
        Object[] row = {
            rs.getInt("cod_cliente"),
            rs.getString("nome_cliente"),
            rs.getString("categoria_cliente"),
            rs.getDate("data_nasc"),
            rs.getInt("nif"),
            rs.getString("email"),
            rs.getString("genero"),
            rs.getInt("cartao_identificacao"),
            rs.getInt("numero_telefone"),
            rs.getString("redes_sociais"),
            rs.getString("morada"),
            rs.getInt("zona_postal"),
            rs.getInt("cod_postal"),
            rs.getString("nacionalidade"),
            rs.getString("pais"),
            rs.getDate("data_registo"),
            rs.getString("imagem"),
            rs.getString("OBS")
        };
        dados.add(row);
    }
    return dados;
}
    
    public Map<String, Object> buscarClientePorCodigo(int cod_cliente) {
    String sql = "SELECT * FROM cliente WHERE cod_cliente = ?";
    Map<String, Object> cliente = new HashMap<>();
    try (Connection conn = conectar.getConnect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, cod_cliente);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            cliente.put("cod_cliente", rs.getInt("cod_cliente"));
            cliente.put("nome_cliente", rs.getString("nome_cliente"));
            cliente.put("categoria_cliente", rs.getString("categoria_cliente"));
            cliente.put("data_nasc", rs.getDate("data_nasc"));
            cliente.put("nif", rs.getInt("nif"));
            cliente.put("email", rs.getString("email"));
            cliente.put("genero", rs.getString("genero"));
            cliente.put("cartao_identificacao", rs.getInt("cartao_identificacao"));
            cliente.put("numero_telefone", rs.getInt("numero_telefone"));
            cliente.put("redes_sociais", rs.getString("redes_sociais"));
            cliente.put("morada", rs.getString("morada"));
            cliente.put("zona_postal", rs.getInt("zona_postal"));
            cliente.put("cod_postal", rs.getInt("cod_postal"));
            cliente.put("nacionalidade", rs.getString("nacionalidade"));
            cliente.put("pais", rs.getString("pais"));
            cliente.put("data_registo", rs.getDate("data_registo"));
            cliente.put("imagem", rs.getString("imagem"));
            cliente.put("OBS", rs.getString("OBS"));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return cliente;
}

//função para contar quantidade de clientes por genero (Masculino, Feminino, Outro) e mostrar numa tabela
    public Object[] contarClientesPorGenero() {
    String sql = "SELECT " +
                 "SUM(CASE WHEN genero IN ('M', 'Masculino') THEN 1 ELSE 0 END) AS Masculino, " +
                 "SUM(CASE WHEN genero IN ('F', 'Feminino') THEN 1 ELSE 0 END) AS Feminino, " +
                 "SUM(CASE WHEN genero NOT IN ('M', 'Masculino', 'F', 'Feminino') OR genero IS NULL THEN 1 ELSE 0 END) AS Outros " +
                 "FROM cliente";
    Object[] resultado = new Object[]{0, 0, 0}; // [Masculino, Feminino, Outros]
    try (Connection conn = conectar.getConnect();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            resultado[0] = rs.getInt("Masculino");
            resultado[1] = rs.getInt("Feminino");
            resultado[2] = rs.getInt("Outros");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return resultado;
}
    
    // Retorna uma lista de arrays: cada array contém [código_zona, quantidade]
public List<Object[]> contarClientesPorZona() {
    String sql = "SELECT zona_postal, COUNT(*) AS quantidade " +
                 "FROM cliente " +
                 "WHERE zona_postal IS NOT NULL " +
                 "GROUP BY zona_postal " +
                 "ORDER BY zona_postal";
    List<Object[]> lista = new ArrayList<>();
    try (Connection conn = conectar.getConnect();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            int zona = rs.getInt("zona_postal");
            int qtd = rs.getInt("quantidade");
            lista.add(new Object[]{zona, qtd});
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return lista;
}

public List<Object[]> contarClientesPorCategoria() {
    String sql = "SELECT categoria_cliente, COUNT(*) AS quantidade " +
                 "FROM cliente " +
                 "WHERE categoria_cliente IS NOT NULL " +
                 "GROUP BY categoria_cliente " +
                 "ORDER BY categoria_cliente";
    List<Object[]> lista = new ArrayList<>();
    try (Connection conn = conectar.getConnect();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            String categoria = rs.getString("categoria_cliente");
            int quantidade = rs.getInt("quantidade");
            lista.add(new Object[]{categoria, quantidade});
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return lista;
}
    
}