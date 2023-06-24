/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fungsi;
import java.sql.*;
/**
 *
 * @author tentangmac
 */


public class hitungGaji {

    public Statement st = null;
    public ResultSet rs = null;
    
    Connection con = koneksi.db_karyawan.konek();
    
    public int gajiStaff, gajiKasir, gajiAdmin, gajiAss, gajiMan, jmlMasuk;
    
    
    public int staff (int jmlMasuk) throws SQLException  {
        
        String cb = "SELECT * FROM gaji_bagian WHERE bagian = 'staff'";
        st = con.createStatement();
        rs = st.executeQuery(cb);
        
        while (rs.next()) {
        int gaji = rs.getInt("gaji");
       
        this.gajiStaff = gaji * jmlMasuk;
        }
        return this.gajiStaff;
    }
    public int kasir (int jmlMasuk) throws SQLException  {
        
        String cb = "SELECT * FROM gaji_bagian WHERE bagian = 'kasir'";
        st = con.createStatement();
        rs = st.executeQuery(cb);
        
        while (rs.next()) {
        int gaji = rs.getInt("gaji");
        
        this.gajiKasir = gaji * jmlMasuk;
        }
        return this.gajiKasir;
    }
    public int admin (int jmlMasuk) throws SQLException  {
        
        String cb = "SELECT * FROM gaji_bagian WHERE bagian = 'admin'";
        st = con.createStatement();
        rs = st.executeQuery(cb);
        
        while (rs.next()) {
        int gaji = rs.getInt("gaji");
       
        this.gajiAdmin = gaji * jmlMasuk;
        }
        return this.gajiAdmin;
    }
    public int ass (int jmlMasuk) throws SQLException  {
        
        String cb = "SELECT * FROM gaji_bagian WHERE bagian = 'asisten manager'";
        st = con.createStatement();
        rs = st.executeQuery(cb);
        
        while (rs.next()) {
        int gaji = rs.getInt("gaji");
        
        this.gajiAss = gaji * jmlMasuk;
        }
        return this.gajiAss;
    }
    public int manager (int jmlMasuk) throws SQLException  {
        
        String cb = "SELECT * FROM gaji_bagian WHERE bagian = 'manager'";
        st = con.createStatement();
        rs = st.executeQuery(cb);
        
        while (rs.next()) {
        int gaji = rs.getInt("gaji");
        
        this.gajiMan = gaji * jmlMasuk;
        }
        return this.gajiMan;
    }
    
}
