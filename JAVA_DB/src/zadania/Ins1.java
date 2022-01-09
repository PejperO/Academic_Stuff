package zadania;

import java.sql.*;

public class Ins1 {

    static public void main(String[] args) throws SQLException {
        new Ins1();
    }

    Statement stmt;

    Ins1() throws SQLException {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");    //client
            con = DriverManager.getConnection("jdbc:derby://localhost/ksidb");    //connecting?
                                                                // 10.15.2.0:1527 wrong ip for sure - check
            stmt = con.createStatement();
        } catch (Exception exc)  {
            System.out.println(exc);
            System.exit(1);
        }
        // nazwy wydawców do wpisywania do tabeli
        String[] wyd =  { "PWN", "PWE", "Czytelnik", "Amber", "HELION", "MIKOM" };

        // pierwszy numer wydawcy do wpisywania do tabeli: PWN ma numer 15, PWE ma 16, ...
        int beginKey = 15;

        String[] ins = new String[wyd.length]; // ? ... tablica instrukcji SQL do wpisywania rekordów do tabeli: INSERT ...

        int insCount = 0;   // ile rekordów wpisano
        try {
            for (int i = 0; i < ins.length; i++){ // wpisywanie rekordów
                stmt.executeUpdate(ins[i]);
                ((PreparedStatement)stmt).setString(1,wyd[i]);  //Precompilating
                //((PreparedStatement)stmt).setInt(2, beginKey+insCount); //should work?
                insCount++;
            }
            con.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }

    }
}