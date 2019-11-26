package baserelacionalb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alba
 */
public class BaseRelacionalB {

    public static Connection conn = null;

    private Connection conexion() {
        final String driver = "jdbc:oracle:thin:";
        final String host = "localhost.localdomain";
        final String porto = "1521";
        final String sid = "orcl";
        final String usuario = "hr";
        final String password = "hr";
        String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;

//        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectRs() {
        String sql = "SELECT produtos.* FROM produtos";

        try (Connection conn = this.conexion();
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.print(rs.getString("codigo") + " - ");
                System.out.print(rs.getString("descricion") + " - ");
                System.out.println(rs.getInt("prezo"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actuRs(String codigo, int prezo) {
        String sql = "SELECT produtos.* FROM produtos";

        try (Connection conn = this.conexion();
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if (codigo.equals(rs.getString("codigo"))) {
                    rs.updateInt("prezo", prezo);
                    rs.updateRow();
                }
            }
            System.out.println("Actualizouse a base de datos");

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insireRs(String codigo, String descricion, int prezo) {
        String sql = "SELECT produtos.* FROM produtos";

        try (Connection conn = this.conexion();
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery(sql)) {

            rs.moveToInsertRow();
            rs.updateString("codigo", codigo);
            rs.updateString("descricion", descricion);
            rs.updateInt("prezo", prezo);
            rs.insertRow();

            System.out.println("Insertaronse os datos");

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borraRs(String codigo) {
        String sql = "SELECT produtos.* FROM produtos";

        try (Connection conn = this.conexion();
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if (codigo.equals(rs.getString("codigo"))) {
                    rs.deleteRow();
                }
            }

            System.out.println("Borraronse os datos");

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws SQLException {
        BaseRelacionalB obx = new BaseRelacionalB();
        obx.selectRs();
//        obx.actuRs("p2", 8);
//        obx.insireRs("p4", "corcho", 7);

//        obx.borraRs("p4");
        conn.close();
    }

}
