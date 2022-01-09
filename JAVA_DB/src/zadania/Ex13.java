package zadania;

import java.sql.*;

public class Ex13 {
    Statement stmt;

    Ex13() throws SQLException, ClassNotFoundException {
        Connection con = null;
        Class.forName("org.apache.derby.jdbc.ClientDriver");    //client
        con = DriverManager.getConnection("jdbc:derby://localhost/ksidb");    //connecting?
                                                            // 10.15.2.0:1527 wrong ip for sure - check
        stmt = con.createStatement();

        String sel = "SELECT a.name, p.tytul, p.rok, p.cena" +
                     "FROM AUTOR a INNER JOIN POZYCJE p ON a.AUTID = p.AUTID" +
                     "WHERE p.rok >= 2000 AND p.cena > 30";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sel);
            while (rs.next())  {
                String name = rs.getString("name");
                String title = rs.getString("tytul");
                String year = rs.getString("rok");
                String price = rs.getString("cena");

                System.out.println("Author: " + name);
                System.out.println("Title: " + title);
                System.out.println("Year: " + year);
                System.out.println("Price: " + price);
            }

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sel);
            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            for (int i = 1; i <= cc; i++)
                System.out.print(rsmd.getColumnLabel(i) + "     ");

            System.out.println("\n------------------------------ przewijanie do góry");

            // ... ?

            System.out.println("\n----------------------------- pozycjonowanie abs.");
            int[] poz =  { 3, 7, 9  };
            for (int p = 0; p < poz.length; p++)  {
                System.out.print("[ " + poz[p] + " ] ");
                // ... ?
                for (int i = 1; i <= cc; i++) System.out.print(rs.getString(i) + ", ");
                System.out.println("");
            }

            stmt.close();
            con.close();
        } catch (SQLException exc)  {
            System.out.println(exc.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new Ex13();
    }
}
