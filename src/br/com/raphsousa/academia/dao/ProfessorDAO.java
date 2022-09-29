package br.com.raphsousa.academia.dao;

import br.com.raphsousa.academia.Base;
import br.com.raphsousa.academia.modelo.bd.AcademiaBD;
import br.com.raphsousa.academia.modelo.entidades.Professor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    private static final String INSERIR_SQL = 
            "insert into professor(nome) values"
            + "('%s')";
    private static final String ALTERAR_SQL = "Update professor "
            + "set nome='%s' where id=%d";
    private static final String REMOVER_SQL = "delete from professor "
            + "where id=%d";
    private static final String SELECIONAR_TUDO_SQL = "Select * from professor";
    private static final String SELECIONAR_POR_ID =
            "Select * from professor where id=%d";
    
    
    public static void inserir(Professor professor) {
        String sql = String.format(INSERIR_SQL,
                professor.getNome());
        if (AcademiaBD.execute(sql, true)) {
            Base.mensagem(professor.getNome() + " inserido com sucesso");
        }
    }

    public static void alterar(Professor professor) {
        String sql = String.format(ALTERAR_SQL,
                professor.getNome(),
                professor.getId());
        if (AcademiaBD.execute(sql, true)) {
            Base.mensagem("Professor " + professor.getId() +
                    " alterado com sucesso");
        }
    }

    public static void remover(Professor professor) {
        String sql = String.format(REMOVER_SQL,
                professor.getId());
        if (AcademiaBD.execute(sql, true)) {
            Base.mensagem("Professor " + professor.getId() + 
                    " removido com sucesso");
        }
    }

    private static List<Professor> selecionar(String sql) {
        List<Professor> lista = new ArrayList<>();
        Connection con = AcademiaBD.conectar();
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                lista.add(new Professor(id, nome));
            }
            AcademiaBD.desconectar(con);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }
        return lista;
    }


    public static List<Professor> selecionarTodos() {
        return selecionar(SELECIONAR_TUDO_SQL);
    }

    public static List<Professor> selecionarPorId(int id) {
        return selecionar(String.format(
                SELECIONAR_POR_ID, id));
    }
    
}
