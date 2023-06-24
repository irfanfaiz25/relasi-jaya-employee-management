/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fungsi;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import koneksi.db_karyawan;
import java.sql.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author tentangmac
 */
public class metod {
        db_karyawan db = new db_karyawan();
        Connection con = koneksi.db_karyawan.konek();
        ResultSet rs = null;
        Statement st = null;
        
        public void fillDataJTable(JTable jt) {
            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Id","nama","Bulan","masuk","ambil_gaji","total_gaji"
            },0);
        try {
           
            String sql = "SELECT * FROM gaji_kry";
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                String id = rs.getString("Id");
                String nama = rs.getString("nama");
                String bulan = rs.getString("Bulan");
                String masuk = rs.getString("masuk");
                String ambil_gaji = rs.getString("ambil_gaji");
                String total_gaji = rs.getString("total_gaji");
                
                model.addRow(new Object[]{
                    id, nama, bulan, masuk, ambil_gaji, total_gaji
                });
                jt.setModel(model);
            }
        } catch (Exception e) {

        }
    }
        
        public void openFile(String file){
            try {
                File path = new File(file);
                Desktop.getDesktop().open(path);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        public void exportExcel(JTable jt) {
            try {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showSaveDialog(jt);
                File saveFile = jFileChooser.getSelectedFile();
                
                if (saveFile != null) {
                    saveFile = new File(saveFile.toString()+".xlsx");
                    Workbook wb = new XSSFWorkbook();
                    Sheet sheet = wb.createSheet("Data Gaji");
                    Row rowCol = sheet.createRow(0);
                    
                    for (int i=0;i<jt.getColumnCount();i++) {
                        Cell cell = rowCol.createCell(i);
                        cell.setCellValue(jt.getColumnName(i));
                    }
                    
                    for (int j=0;j<jt.getRowCount();j++) {
                        Row row = sheet.createRow(j);
                        for (int k=0;k<jt.getColumnCount();k++) {
                            Cell cell = row.createCell(k);
                            if (jt.getValueAt(j, k) != null){
                                cell.setCellValue(jt.getValueAt(j, k).toString());
                            }
                        }
                    }
                    FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                    wb.write(out);
                    wb.close();
                    out.close();
                    openFile(saveFile.toString());
                } else {
                    JOptionPane.showMessageDialog(null,"error");
                }
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException io) {
                System.out.println(io);

            }
        }
}
