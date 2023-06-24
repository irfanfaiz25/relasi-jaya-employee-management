/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package SI;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import fungsi.hitungGaji;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author tentangmac
 */
public class formGajiKaryawan extends javax.swing.JFrame {
    
    /**
     * Creates new form formGajiKaryawan
     */
    fungsi.metod mtd = new fungsi.metod();
    NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in","ID"));
    public Statement st = null;
    public ResultSet rs = null;
    public PreparedStatement pst = null;
    public String order = "ORDER BY CASE WHEN Bulan = 'Januari' THEN 1 " +
                        "WHEN Bulan = 'Februari' THEN 2 " +
                        "WHEN Bulan = 'Maret' THEN 3 " +
                        "WHEN Bulan = 'April' THEN 4 " +
                        "WHEN Bulan = 'Mei' THEN 5 " +
                        "WHEN Bulan = 'Juni' THEN 6 " +
                        "WHEN Bulan = 'Juli' THEN 7 " +
                        "WHEN Bulan = 'Agustus' THEN 8 " +
                        "WHEN Bulan = 'September' THEN 9 " +
                        "WHEN Bulan = 'Oktober' THEN 10 " +
                        "WHEN Bulan = 'November' THEN 11 " +
                        "WHEN Bulan = 'Desember' THEN 12 " +
                        "ELSE NULL END";
    
    
    Connection con = koneksi.db_karyawan.konek();
    hitungGaji hitung = new hitungGaji();
    
    public formGajiKaryawan() {
        
        initComponents();
        
        mtd.fillDataJTable(tabelGaji);
        cari();
        tampilData();
        ambilNm();
        crData();
        tSize();
        
    }
    
    
    private void bersih() {
        txtId.setText("");
        txtNama.setText("");
        cmbBulan.setSelectedItem("Januari");
        cmbMasuk.setSelectedItem("30");
        txtAmbil.setText("Bulanan");
        txtGaji.setText("");
        
        simpan.setText("SIMPAN");
        txtId.setText("RJ");
        txtId.setEditable(true);
        txtNama.setEditable(true);
        tSize();
        
    }
    
    private void comboNama() {
        try {
            String ambilNama = "SELECT * FROM karyawan WHERE Id = '"+ txtId.getText() +"'";
            pst = con.prepareStatement(ambilNama);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                String addNama = rs.getString("nama");
                txtNama.setText(addNama);
                }
        
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
        tSize();
    }
    private void cari() {
        
        try {
            
            st = con.createStatement();
            
            
            if (crId.getSelectedItem().equals("Id")) {
                rs = st.executeQuery("SELECT * FROM gaji_kry WHERE Id LIKE '%"+ txtCari.getText() +"%' "+ order);
                cariBulan1.setEnabled(false);
                txtCari.setEnabled(true);
            } else if (crId.getSelectedItem().equals("nama")) {
                rs = st.executeQuery("SELECT * FROM gaji_kry WHERE nama LIKE '%"+ txtCari.getText() +"%' "+ order);
                cariBulan1.setEnabled(false);
                txtCari.setEnabled(true);
            } else if (crId.getSelectedItem().equals("Bulan")){
                rs = st.executeQuery("SELECT * FROM gaji_kry WHERE Bulan LIKE '%"+ cariBulan1.getSelectedItem().toString() +"%' ");
                cariBulan1.setEnabled(true);
                txtCari.setEnabled(false);
            } else {
                rs = st.executeQuery("SELECT * FROM gaji_kry WHERE Bulan LIKE '%"+ cariBulan1.getSelectedItem().toString()
                        +"%' AND Id LIKE '%"+ txtCari.getText() +"%' ");
                cariBulan1.setEnabled(true);
                txtCari.setEnabled(true);
            }
            
            DefaultTableModel mdl = new DefaultTableModel();
            
            mdl.addColumn("No");
            mdl.addColumn("Id");
            mdl.addColumn("Nama");
            mdl.addColumn("Bulan");
            mdl.addColumn("Masuk");
            mdl.addColumn("Terima Gaji");
            mdl.addColumn("Total Gaji");
            
            mdl.getDataVector().removeAllElements();
            mdl.fireTableDataChanged();
            mdl.setRowCount(0);
            
            
            int no = 1;
            while(rs.next()) {
                Object[] data = {
                    no++,
                    rs.getString("Id"),
                    rs.getString("nama"),
                    rs.getString("Bulan"),
                    rs.getString("masuk"),
                    rs.getString("ambil_gaji"),
                    rs.getString("total_gaji")
                };
                mdl.addRow(data);
                tabelGaji.setModel(mdl);
            }
            txtId.setText("RJ");
        } catch (Exception e) {
            
        }
        tSize();
    }
    
    private void ambilNm() {
        txtId.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
         @Override
         public void insertUpdate(DocumentEvent e) {
//             throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
               comboNama();
               statGaji();
               gajiBag();
         }

         @Override
         public void removeUpdate(DocumentEvent e) {
//             throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
               txtNama.setText("");
               txtGaji.setText("");
               txtAmbil.setText("");
                
         }

         @Override
         public void changedUpdate(DocumentEvent e) {
             throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         }
     });
    }
    private void crData() {
        txtCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                  cari();
                  
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                  cari();
                  
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }
    
    private void tampilData() {
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM gaji_kry "+ order);
            
            DefaultTableModel mdl = new DefaultTableModel();
            
            mdl.addColumn("No");
            mdl.addColumn("Id");
            mdl.addColumn("Nama");
            mdl.addColumn("Bulan");
            mdl.addColumn("Masuk");
            mdl.addColumn("Terima Gaji");
            mdl.addColumn("Total Gaji");
            
            mdl.getDataVector().removeAllElements();
            mdl.fireTableDataChanged();
            mdl.setRowCount(0);
            
            
            int no = 1;
            while(rs.next()) {
                Object[] data = {
                    no++,
                    rs.getString("Id"),
                    rs.getString("nama"),
                    rs.getString("Bulan"),
                    rs.getString("masuk"),
                    rs.getString("ambil_gaji"),
                    rs.getString("total_gaji")
                };
                mdl.addRow(data);
                tabelGaji.setModel(mdl);
                
            }
            
            txtId.setText("RJ");
            
        } catch (Exception e){
            
        }
        tSize();
    }
    private void tSize() {
        tabelGaji.getColumnModel().getColumn(0).setPreferredWidth(18);
        tabelGaji.getColumnModel().getColumn(1).setPreferredWidth(35);
        tabelGaji.getColumnModel().getColumn(2).setPreferredWidth(140);
        tabelGaji.getColumnModel().getColumn(3).setPreferredWidth(55);
        tabelGaji.getColumnModel().getColumn(4).setPreferredWidth(30);
        tabelGaji.getColumnModel().getColumn(5).setPreferredWidth(70);
        tabelGaji.getColumnModel().getColumn(6).setPreferredWidth(60);
    }
    private void gajiBag() {
        try {
            String cekB = "SELECT * FROM karyawan WHERE Id = '"+ txtId.getText() +"'";
            st = con.createStatement();
            rs = st.executeQuery(cekB);
            
            while (rs.next()) {
            String bag = rs.getString("bagian");
            hitung.jmlMasuk = Integer.parseInt((String)cmbMasuk.getSelectedItem());
            
            switch (bag) {
                case "Staff" :
                    hitung.gajiStaff = hitung.staff(hitung.jmlMasuk);
                    txtGaji.setText(String.valueOf(nf.format(hitung.gajiStaff)));
                break;
                case "Kasir" :
                    hitung.gajiKasir = hitung.kasir(hitung.jmlMasuk);
                    txtGaji.setText(String.valueOf(nf.format(hitung.gajiKasir)));
                break;
                case "Admin" :
                    hitung.gajiAdmin = hitung.admin(hitung.jmlMasuk);
                    txtGaji.setText(String.valueOf(nf.format(hitung.gajiAdmin)));
                break;
                case "Asisten Manager" :
                    hitung.gajiAss = hitung.ass(hitung.jmlMasuk);
                    txtGaji.setText(String.valueOf(nf.format(hitung.gajiAss)));
                break;
                case "Manager" :
                    hitung.gajiMan = hitung.manager(hitung.jmlMasuk);
                    txtGaji.setText(String.valueOf(nf.format(hitung.gajiMan)));
                break;    
                default:
            }
//            
            } 
        } catch (Exception e) {
            
        }
    }
    private void statGaji() {
        int hari = Integer.parseInt((String)(cmbMasuk.getSelectedItem()));
        if (hari >= 8 && hari <= 30) {
            txtAmbil.setText("bulanan");
        } else if (hari <= 7) {
              autoNumber();
        }
        
    }
    private void valGaji() {
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT LEFT (ambil_gaji,5) AS ambil, Id, Bulan FROM gaji_kry WHERE Id = '"+ txtId.getText() +"'");
            
            while (rs.next()) {
                String bln = rs.getString("Bulan");
                String amb = rs.getString("ambil");
                
                
//                if (bln.equals(cmbBulan.getSelectedItem().toString()) && amb.equals(txtAmbil.getText())){
//                    JOptionPane.showMessageDialog(null, "data gaji pada bulan "+ cmbBulan.getSelectedItem().toString() +" Sudah Terisi");
//                }
                if (bln.equals(cmbBulan.getSelectedItem().toString()) && amb.equals("bulan")) {
                    JOptionPane.showMessageDialog(null,"data gaji pada bulan "+ cmbBulan.getSelectedItem().toString() +" Sudah Terisi");
                    if (txtAmbil.getText().equals("minggu ke-1")) {
                        JOptionPane.showMessageDialog(null,"data gaji pada bulan "+ cmbBulan.getSelectedItem().toString() +" Sudah Terisi");
                        return;
                    }
                } else if (bln.equals(cmbBulan.getSelectedItem().toString()) && amb.equals("mingg")) {
                    if (txtAmbil.getText().equals("bulanan")) {
                        JOptionPane.showMessageDialog(null,"data gaji pada bulan "+ cmbBulan.getSelectedItem().toString() +" Sudah Terisi");
                        return;
                    }
                }
                return;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    
        /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        keliuar1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        simpan = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        kembali = new javax.swing.JButton();
        keliuar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        crId = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbMasuk = new javax.swing.JComboBox<>();
        cmbBulan = new javax.swing.JComboBox<>();
        txtGaji = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtAmbil = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        hapus1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelGaji = new javax.swing.JTable();
        cariBulan1 = new javax.swing.JComboBox<>();
        txtCari = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();

        keliuar1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        keliuar1.setText("KELUAR");
        keliuar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keliuar1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(981, 665));

        jPanel2.setBackground(new java.awt.Color(75, 86, 210));
        jPanel2.setPreferredSize(new java.awt.Dimension(989, 154));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Bahnschrift", 1, 48)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("DATA GAJI KARYAWAN");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/earn-money (1) copy.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("x");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/left.png"))); // NOI18N
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("back");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(15, 15, 15))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addContainerGap(320, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel19)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 39, Short.MAX_VALUE))
        );

        simpan.setBackground(new java.awt.Color(75, 86, 210));
        simpan.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        simpan.setForeground(new java.awt.Color(255, 255, 255));
        simpan.setText("SIMPAN");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        hapus.setBackground(new java.awt.Color(75, 86, 210));
        hapus.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        hapus.setForeground(new java.awt.Color(255, 255, 255));
        hapus.setText("HAPUS");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(75, 86, 210));
        jButton3.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("BATAL");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        kembali.setBackground(new java.awt.Color(75, 86, 210));
        kembali.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        kembali.setForeground(new java.awt.Color(255, 255, 255));
        kembali.setText("KEMBALI");
        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });

        keliuar.setBackground(new java.awt.Color(75, 86, 210));
        keliuar.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        keliuar.setForeground(new java.awt.Color(255, 255, 255));
        keliuar.setText("KELUAR");
        keliuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keliuarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(75, 86, 210));
        jLabel7.setText("Cari Data");

        crId.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        crId.setForeground(new java.awt.Color(75, 86, 210));
        crId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Id", "nama", "Bulan", "Id & bulan" }));
        crId.setBorder(null);
        crId.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                crIdItemStateChanged(evt);
            }
        });
        crId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                crIdMouseReleased(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 86, 210), 2));

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(75, 86, 210));
        jLabel8.setText("ID");

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(75, 86, 210));
        jLabel9.setText("Nama");

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(75, 86, 210));
        jLabel10.setText("Bulan");

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(75, 86, 210));
        jLabel11.setText("Total Gaji");

        jPanel6.setBackground(new java.awt.Color(75, 86, 210));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 86, 210), 2));

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("INPUT DATA GAJI");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtNama.setEditable(false);
        txtNama.setBackground(new java.awt.Color(255, 255, 255));
        txtNama.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        txtNama.setForeground(new java.awt.Color(75, 86, 210));
        txtNama.setBorder(null);
        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });
        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamaKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(75, 86, 210));
        jLabel13.setText("Masuk");

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(75, 86, 210));
        jLabel14.setText("Terima Gaji");

        cmbMasuk.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        cmbMasuk.setForeground(new java.awt.Color(75, 86, 210));
        cmbMasuk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30", "29", "28", "27", "26", "25", "24", "23", "22", "21", "20", "19", "18", "17", "16", "15", "14", "13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1" }));
        cmbMasuk.setBorder(null);
        cmbMasuk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMasukItemStateChanged(evt);
            }
        });

        cmbBulan.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        cmbBulan.setForeground(new java.awt.Color(75, 86, 210));
        cmbBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cmbBulan.setBorder(null);
        cmbBulan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBulanItemStateChanged(evt);
            }
        });
        cmbBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBulanActionPerformed(evt);
            }
        });

        txtGaji.setEditable(false);
        txtGaji.setBackground(new java.awt.Color(255, 255, 255));
        txtGaji.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        txtGaji.setForeground(new java.awt.Color(75, 86, 210));
        txtGaji.setBorder(null);
        txtGaji.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtGajiPropertyChange(evt);
            }
        });

        txtId.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        txtId.setForeground(new java.awt.Color(75, 86, 210));
        txtId.setBorder(null);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdKeyReleased(evt);
            }
        });

        txtAmbil.setEditable(false);
        txtAmbil.setBackground(new java.awt.Color(255, 255, 255));
        txtAmbil.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        txtAmbil.setForeground(new java.awt.Color(75, 86, 210));
        txtAmbil.setBorder(null);
        txtAmbil.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtAmbilPropertyChange(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(75, 86, 210));
        jLabel15.setText("Hari");

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(75, 86, 210));
        jLabel5.setText("Rp.");

        jSeparator1.setForeground(new java.awt.Color(75, 86, 210));

        jSeparator2.setForeground(new java.awt.Color(75, 86, 210));

        jSeparator3.setForeground(new java.awt.Color(75, 86, 210));

        jSeparator4.setForeground(new java.awt.Color(75, 86, 210));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator3)
                            .addComponent(jSeparator2)
                            .addComponent(txtNama, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbBulan, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtId)
                            .addComponent(txtAmbil, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator4)
                                    .addComponent(txtGaji, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))))))
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtAmbil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel5)
                    .addComponent(txtGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        hapus1.setBackground(new java.awt.Color(75, 86, 210));
        hapus1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        hapus1.setForeground(new java.awt.Color(255, 255, 255));
        hapus1.setText("CLEAR");
        hapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapus1ActionPerformed(evt);
            }
        });

        tabelGaji.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        tabelGaji.setForeground(new java.awt.Color(75, 86, 210));
        tabelGaji.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "ID", "Nama", "Bulan", "Masuk", "Terima Gaji", "Total Gaji"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelGaji.setGridColor(new java.awt.Color(255, 255, 255));
        tabelGaji.setRowHeight(30);
        tabelGaji.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelGajiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelGaji);

        cariBulan1.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        cariBulan1.setForeground(new java.awt.Color(75, 86, 210));
        cariBulan1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cariBulan1.setBorder(null);
        cariBulan1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cariBulan1ItemStateChanged(evt);
            }
        });
        cariBulan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariBulan1ActionPerformed(evt);
            }
        });

        txtCari.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        txtCari.setForeground(new java.awt.Color(75, 86, 210));
        txtCari.setBorder(null);
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        jSeparator5.setForeground(new java.awt.Color(75, 86, 210));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(279, 279, 279))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(hapus1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(keliuar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(hapus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel7)
                        .addGap(59, 59, 59)
                        .addComponent(crId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cariBulan1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCari, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(jSeparator5))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(crId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cariBulan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hapus1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(keliuar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
                
                try {   
                        st = con.createStatement();
                        rs = st.executeQuery("SELECT LEFT (ambil_gaji,5) AS ambil, Id, Bulan FROM gaji_kry WHERE Id = '"+ txtId.getText() +"'");

                        while (rs.next()) {
                            String bln = rs.getString("Bulan");
                            String amb = rs.getString("ambil");

                            if (bln.equals(cmbBulan.getSelectedItem().toString()) && amb.equals("bulan")) {
                                if (txtAmbil.getText().equals("minggu ke-1")) {
                                    JOptionPane.showMessageDialog(null,"Karyawan dengan Id '"+ txtId.getText() +"' Pada Bulan '"+ cmbBulan.getSelectedItem().toString() +"' Sudah Menerima Gaji Bulanan");
                                    return;
                                } else {
                                    JOptionPane.showMessageDialog(null,"Karyawan dengan Id '"+ txtId.getText() +"' Pada Bulan '"+ cmbBulan.getSelectedItem().toString() +"' Sudah Menerima Gaji");
                                    return;
                                } 
                            } else if (bln.equals(cmbBulan.getSelectedItem().toString()) && amb.equals("mingg")) {
                                if (txtAmbil.getText().equals("bulanan")) {
                                    JOptionPane.showMessageDialog(null,"Karyawan dengan Id '"+ txtId.getText() +"' Pada Bulan '"+ cmbBulan.getSelectedItem().toString() +"' Sudah Menerima Gaji Mingguan");
                                    return;
                                } else {
                                    statGaji();
                                }
                            }
                        }
                        
                        st = con.createStatement();
                        if (txtId.getText().equals("")||
                                txtNama.getText().equals("")||
                                txtGaji.getText().equals("")){
                                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong","Validasi Data",JOptionPane.INFORMATION_MESSAGE);
                                
                                return;
                            }
                        
                        if (simpan.getText() == "SIMPAN"){
                            String cekBulan = "SELECT * FROM gaji_kry WHERE Id = '"+ txtId.getText() +
                                    "' AND Bulan = '"+ cmbBulan.getSelectedItem().toString() +"' AND ambil_gaji = '"+ txtAmbil.getText() +"'";
                            rs = st.executeQuery(cekBulan);
                            
                            if (rs.next()){
                                JOptionPane.showMessageDialog(null,"Data Karyawan Pada Bulan Ini Sudah Ada");
                            } else {
                                String Sql = "INSERT INTO gaji_kry VALUES ('"+ txtId.getText() +
                                        "','"+ txtNama.getText() +
                                        "','"+ cmbBulan.getSelectedItem() +
                                        "','"+ cmbMasuk.getSelectedItem() +
                                        "','"+ txtAmbil.getText() +
                                        "','"+ txtGaji.getText() +"')";
                                st.executeUpdate(Sql);
                                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                                bersih();
                            }
                        } else {
                            String update = "UPDATE gaji_kry SET Bulan = '"+ cmbBulan.getSelectedItem()+
                                "', masuk = '"+ cmbMasuk.getSelectedItem() +
                                "', ambil_gaji = '"+ txtAmbil.getText() +
                                "', total_gaji = '"+ txtGaji.getText() +
                                "' WHERE Id = '"+ txtId.getText() + "'";
                                st.executeUpdate(update);
                                JOptionPane.showMessageDialog(null,"Data Berhasil Diubah");
                                bersih();
                                tampilData();
                        }
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                tampilData();
    }//GEN-LAST:event_simpanActionPerformed

    private void cmbBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBulanActionPerformed
    
    }//GEN-LAST:event_cmbBulanActionPerformed

    private void tabelGajiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelGajiMouseClicked
        String val3 = tabelGaji.getValueAt(tabelGaji.getSelectedRow(),3).toString();
        String val4 = tabelGaji.getValueAt(tabelGaji.getSelectedRow(),4).toString();
        String val5 = tabelGaji.getValueAt(tabelGaji.getSelectedRow(),5).toString();
        String val1 = tabelGaji.getValueAt(tabelGaji.getSelectedRow(),1).toString();

        txtId.setText(val1);
        txtNama.setText(tabelGaji.getValueAt(tabelGaji.getSelectedRow(),2).toString());
        cmbBulan.setSelectedItem(val3);
        cmbMasuk.setSelectedItem(val4);
        txtAmbil.setText(val5);
        txtGaji.setText(tabelGaji.getValueAt(tabelGaji.getSelectedRow(),6).toString());

        txtId.setEditable(false);
        txtNama.setEditable(false);
        simpan.setText("UBAH");
    }//GEN-LAST:event_tabelGajiMouseClicked

    private void keliuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keliuarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_keliuarActionPerformed

    private void hapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapus1ActionPerformed
                DefaultTableModel mdl = new DefaultTableModel();
                int jawab = JOptionPane.showConfirmDialog(null,"Semua Data Karyawan Akan Dihapus, Lanjutkan ?","Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (jawab == 0) {
                        try {
                                st = con.createStatement();
                                String sql2 = "DELETE FROM gaji_kry";
                                st.executeUpdate(sql2);
                                JOptionPane.showMessageDialog(null,"Semua Data Berhasil Dihapus");
                                ((DefaultTableModel)tabelGaji.getModel()).setNumRows(0);
                                bersih();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null,e);
                            }
        }
          tSize();
    }//GEN-LAST:event_hapus1ActionPerformed

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        home homi = new home();
        homi.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_kembaliActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        bersih();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        if (txtId.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Silahkan Pilih Data Yang Akan Dihapus !");
            } else {
                int jawab = JOptionPane.showConfirmDialog(null,"Data Dengan Id '"+ txtId.getText() +
                        "' Pada Bulan '"+ cmbBulan.getSelectedItem().toString() +"' Akan Dihapus, Lanjutkan ?","Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (jawab == 0) {
                        try {
                                st = con.createStatement();
                                String sql2 = "DELETE FROM gaji_kry WHERE Id = '"+ txtId.getText() + "' AND Bulan = '"+ cmbBulan.getSelectedItem().toString() +"' "
                                        + "AND ambil_gaji = '"+ txtAmbil.getText() +"'";
                                st.executeUpdate(sql2);
                                JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
                                tampilData();
                                bersih();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null,e);
                            }
            }
        }
          tSize();
    }//GEN-LAST:event_hapusActionPerformed

    private void txtNamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyReleased

    }//GEN-LAST:event_txtNamaKeyReleased

    private void cariBulan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariBulan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariBulan1ActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void txtIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdKeyReleased

    private void crIdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_crIdItemStateChanged
        // TODO add your handling code here:
        cari();
        
//        
    }//GEN-LAST:event_crIdItemStateChanged

    private void crIdMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crIdMouseReleased
        // TODO add your handling code here:
//        cari();
    }//GEN-LAST:event_crIdMouseReleased

    private void cariBulan1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cariBulan1ItemStateChanged
        // TODO add your handling code here:
        cari();
        crData();
    }//GEN-LAST:event_cariBulan1ItemStateChanged

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        //        cari();
        crData();
    }//GEN-LAST:event_txtCariKeyReleased

    private void keliuar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keliuar1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_keliuar1ActionPerformed

    private void txtGajiPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtGajiPropertyChange
        // TODO add your handling code here:
//        tFormat();
    }//GEN-LAST:event_txtGajiPropertyChange

    private void txtAmbilPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtAmbilPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmbilPropertyChange

    private void cmbMasukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMasukItemStateChanged
        // TODO add your handling code here:
        gajiBag();
        statGaji();
    }//GEN-LAST:event_cmbMasukItemStateChanged

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        home homi = new home();
        homi.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void cmbBulanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBulanItemStateChanged
        statGaji();
    }//GEN-LAST:event_cmbBulanItemStateChanged

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formGajiKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formGajiKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formGajiKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formGajiKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formGajiKaryawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cariBulan1;
    private javax.swing.JComboBox<String> cmbBulan;
    private javax.swing.JComboBox<String> cmbMasuk;
    private javax.swing.JComboBox<String> crId;
    private javax.swing.JButton hapus;
    private javax.swing.JButton hapus1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JButton keliuar;
    private javax.swing.JButton keliuar1;
    private javax.swing.JButton kembali;
    private javax.swing.JButton simpan;
    private javax.swing.JTable tabelGaji;
    private javax.swing.JTextField txtAmbil;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtGaji;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
private void autoNumber() {
    try {
        String sql = "SELECT MAX(RIGHT(ambil_gaji,1)) AS no_auto FROM gaji_kry WHERE Id = '"+ txtId.getText() +"' "
                + "AND Bulan = '"+ cmbBulan.getSelectedItem().toString() +"'";
        st = con.createStatement();
        rs = st.executeQuery(sql);
        
        if (rs.next()) {
            String no_auto;
            no_auto = Integer.toString(rs.getInt(1)+ 1);
                
                txtAmbil.setText("minggu ke-"+ no_auto);
        } 
    } catch (Exception e) {
            txtAmbil.setText("minggu ke-1");

    }
}
private void cekAmbil() {
    try {
        String sql = "SELECT MAX(RIGHT(ambil_gaji,1)) AS no_auto FROM gaji_kry WHERE Id = '"+ txtId.getText() +"' "
                + "AND Bulan = '"+ cmbBulan.getSelectedItem().toString() +"'";
        st = con.createStatement();
        rs = st.executeQuery(sql);
        
        if (rs.next()) {
            String no_auto;
            int cek;
            
            no_auto = Integer.toString(rs.getInt(1)+ 1);
            cek = Integer.parseInt((String)no_auto);
            if (cek >= 5) {
                cmbMasuk.setSelectedItem("30");
                JOptionPane.showMessageDialog(null,"Karyawan dengan Id '"+ txtId.getText() +"' Sudah Menerima Gaji Minggu ke-4 !");
            }
        } 
    } catch (Exception e) {

    }
}
}

