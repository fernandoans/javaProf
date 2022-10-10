import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Conexao {

    private Connection con;

    public static void main(String [] args) throws SQLException {
        new Conexao().executar();
    }

    public void executar() throws SQLException {
        try {
            iniciarConexao();
            criarTabela();
            inserirRegistro("Py", "Python", nota());
            inserirRegistro("Jv", "Java", nota());
            inserirRegistro("C+", "Linguagem C++", nota());
            inserirRegistro("C#", "Linguagem C#", nota());
            inserirRegistro("Ju", "Julia", nota());
            inserirRegistro("R", "Linguagem R", nota());
            verRegistros();
            alterarRegistro("Jv", nota());
            verRegistros();
            excluirRegistro("Jv");
            verRegistros();
            eliminarTabela();
        } finally {
            fecharConexao();
        }
    }

    public void iniciarConexao() throws SQLException {
        System.out.println("Conectando a base de dados...");
        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/demo?useSSL=false",
            "root",
            "root"
        );
        System.out.println("Conexão Válida: " + con.isValid(5));
    }

    public void fecharConexao() throws SQLException {
        System.out.println("Fechando a conexão com a base de dados...");
        con.close();
        System.out.println("Conexão Válida: " + con.isValid(5));
    }

    public void criarTabela() throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement("""
        CREATE TABLE linguagem(
            codigo CHAR(2) NOT NULL,
            nome VARCHAR(20) NOT NULL,
            classificacao int4,
            PRIMARY KEY(codigo)
        )
        """)) {
            int linhas = pstm.executeUpdate();
            System.out.println("Tabela Criada: " + linhas);
        }
    }

    public void inserirRegistro(String codigo, String nome, int classificacao) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement("""
        INSERT INTO linguagem(codigo, nome, classificacao)
        VALUES (?, ?, ?)
        """)) {
            pstm.setString(1, codigo);
            pstm.setString(2, nome);
            pstm.setInt(3, classificacao);
            int linhas = pstm.executeUpdate();
            System.out.println("Qtd Linha Inserida: " + linhas);
        }
    }

    public void alterarRegistro(String codigo, int classificacao) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement("""
        UPDATE linguagem SET classificacao = ?
        WHERE codigo = ?
        """)) {
            pstm.setInt(1, classificacao);
            pstm.setString(2, codigo);
            int linhas = pstm.executeUpdate();
            System.out.println("Qtd Linha Alterada: " + linhas);
        }
    }

    public void excluirRegistro(String codigo) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement("""
        DELETE FROM linguagem 
        WHERE codigo = ?
        """)) {
            pstm.setString(1, codigo);
            int linhas = pstm.executeUpdate();
            System.out.println("Qtd Linha Excluída: " + linhas);
        }
    }

    public void verRegistros() throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement("""
        SELECT codigo, nome, classificacao
        FROM linguagem
        ORDER BY classificacao
        """)) {
            try (ResultSet res = pstm.executeQuery()) {
                while (res.next()) {
                    String codigo = res.getString(1);
                    String nome = res.getString(2);
                    int classificacao = res.getInt(3);
                    System.out.println("\t> " + codigo + " - " + nome + " (" + classificacao + ")");
                }
            }
        }
    }

    public void eliminarTabela() throws SQLException {
       try (PreparedStatement pstm = con.prepareStatement("""
       DROP TABLE linguagem 
       """)) {
           int linhas = pstm.executeUpdate();
           System.out.println("Tabela Excluída: " + linhas);
       }
    }

   private int nota() {
        return (int)(Math.random()*10)+1;
    }

}