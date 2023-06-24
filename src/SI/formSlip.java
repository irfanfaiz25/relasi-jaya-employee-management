/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SI;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author T O M
 */
public class formSlip extends javax.swing.JFrame {
    Connection con = koneksi.db_karyawan.konek();
    Statement st = null;
    ResultSet rs =  null;
    /** Creates new form formSlip */
    public formSlip() {
        initComponents();
        cari();
        crData();
    }
    
    private void cari() {
        try {
            String ck = cmbAmbil.getSelectedItem().toString();
            if (ck.equals("Bulanan")) {
                cmbMinggu.setEnabled(false);
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM gaji_kry WHERE Id LIKE '%"+ txtId.getText() +"%' AND Bulan LIKE '%"+ cmbBulan.getSelectedItem().toString() +"%'");
            } else {
                cmbMinggu.setEnabled(true);
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM gaji_kry WHERE Id LIKE '%"+ txtId.getText() +"%' AND Bulan LIKE '%"+ cmbBulan.getSelectedItem().toString() +"%' AND ambil_gaji = '"+ cmbMinggu.getSelectedItem().toString() +"'");
            }
            
            DefaultTableModel mdl = new DefaultTableModel();
            
//            mdl.addColumn("No");
            mdl.addColumn("Id");
            mdl.addColumn("Nama");
            mdl.addColumn("Bulan");
            mdl.addColumn("Masuk");
            mdl.addColumn("Terima Gaji");
            mdl.addColumn("Total Gaji");
            
            mdl.getDataVector().removeAllElements();
            mdl.fireTableDataChanged();
            mdl.setRowCount(0);
            
//            int no = 1;
            while (rs.next()) {
                Object[] data = {
//                    no++,
                    rs.getString("Id"),
                    rs.getString("nama"),
                    rs.getString("Bulan"),
                    rs.getString("masuk"),
                    rs.getString("ambil_gaji"),
                    rs.getString("total_gaji"),
                };
                mdl.addRow(data);
                tabelGaji.setModel(mdl);
            }
            txtId.setText("RJ");
        } catch (Exception e) {
            
        }
        tSize();
    }
    private void crData() {
        txtId.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               cari();
            }
           

            @Override
            public void removeUpdate(DocumentEvent de) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                cari();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    private void tSize() {
//        tabelGaji.getColumnModel().getColumn(0).setPreferredWidth(18);
        tabelGaji.getColumnModel().getColumn(0).setPreferredWidth(35);
        tabelGaji.getColumnModel().getColumn(1).setPreferredWidth(140);
        tabelGaji.getColumnModel().getColumn(2).setPreferredWidth(55);
        tabelGaji.getColumnModel().getColumn(3).setPreferredWidth(30);
        tabelGaji.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabelGaji.getColumnModel().getColumn(5).setPreferredWidth(60);
    }
    private void cek() {
        try {
            String valId = tabelGaji.getModel().getValueAt(0, 1).toString();
            String valBln = tabelGaji.getModel().getValueAt(0, 3).toString();
            String valAmbil = tabelGaji.getModel().getValueAt(0, 5).toString();
            String cek = "SELECT * FROM history_gaji WHERE Id = '"+ valId +"' AND Bulan = '"+ valBln +"' AND ambil_gaji = '"+ valAmbil +"'";
            rs = st.executeQuery(cek);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null,"Slip Gaji dengan ID '"+ tabelGaji.getModel().getValueAt(0, 0).toString() +"' Sudah Tercetak !");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbBulan = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelGaji = new javax.swing.JTable();
        cetak = new javax.swing.JButton();
        cmbMinggu = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbAmbil = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(981, 665));

        jPanel2.setBackground(new java.awt.Color(75, 86, 210));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/document.png"))); // NOI18N

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Bahnschrift", 1, 42)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("SLIP GAJI");

        jLabel9.setFont(new java.awt.Font("Cascadia Code", 0, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Relasi Jaya Management");

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("x");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/left.png"))); // NOI18N
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("back");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(16, 16, 16))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(626, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel14))
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(75, 86, 210));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 86, 210), 2));

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CARI DATA");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel4)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtId.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        txtId.setForeground(new java.awt.Color(75, 86, 210));
        txtId.setBorder(null);

        jSeparator2.setForeground(new java.awt.Color(75, 86, 210));

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(75, 86, 210));
        jLabel2.setText("ID");

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(75, 86, 210));
        jLabel5.setText("Bulan");

        cmbBulan.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        cmbBulan.setForeground(new java.awt.Color(75, 86, 210));
        cmbBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
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

        tabelGaji.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        tabelGaji.setForeground(new java.awt.Color(75, 86, 210));
        tabelGaji.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Bulan", "Masuk", "Terima Gaji", "Total Gaji"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelGaji.setRowHeight(30);
        tabelGaji.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelGajiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelGaji);

        cetak.setBackground(new java.awt.Color(75, 86, 210));
        cetak.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        cetak.setForeground(new java.awt.Color(255, 255, 255));
        cetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/printer.png"))); // NOI18N
        cetak.setText(" CETAK SLIP");
        cetak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cetakMouseClicked(evt);
            }
        });
        cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakActionPerformed(evt);
            }
        });

        cmbMinggu.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        cmbMinggu.setForeground(new java.awt.Color(75, 86, 210));
        cmbMinggu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "minggu ke-1", "minggu ke-2", "minggu ke-3", "minggu ke-4" }));
        cmbMinggu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMingguItemStateChanged(evt);
            }
        });
        cmbMinggu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMingguActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(75, 86, 210));
        jLabel6.setText("Minggu");

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(75, 86, 210));
        jLabel11.setText("Terima Gaji");

        cmbAmbil.setFont(new java.awt.Font("Bahnschrift", 0, 11)); // NOI18N
        cmbAmbil.setForeground(new java.awt.Color(75, 86, 210));
        cmbAmbil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bulanan", "Mingguan" }));
        cmbAmbil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAmbilItemStateChanged(evt);
            }
        });
        cmbAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAmbilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbAmbil, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel5))
                                    .addGap(76, 76, 76)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(52, 52, 52)
                                    .addComponent(cmbMinggu, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(30, 30, 30)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cmbAmbil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmbMinggu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cmbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        System.exit(0);
                
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        home homi = new home();
        homi.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void cmbBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBulanActionPerformed

    }//GEN-LAST:event_cmbBulanActionPerformed

    private void tabelGajiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelGajiMouseClicked
       
    }//GEN-LAST:event_tabelGajiMouseClicked

    private void cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakActionPerformed
        
        try {
            String delete = ("DELETE FROM slip_kry");
            st.executeUpdate(delete);
            String valId = tabelGaji.getValueAt(tabelGaji.getSelectedRow(), 0).toString();
            String valBln = tabelGaji.getValueAt(tabelGaji.getSelectedRow(), 2).toString();
            String valAmbil = tabelGaji.getValueAt(tabelGaji.getSelectedRow(), 4).toString();
            String cek2 = ("SELECT * FROM history_gaji WHERE Id = '"+ valId +"' AND Bulan = '"+valBln+"' AND ambil_gaji = '"+valAmbil+"'");
            rs = st.executeQuery(cek2);
            
            DefaultTableModel mdl = (DefaultTableModel) tabelGaji.getModel();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null,"Slip Gaji dengan ID '"+ tabelGaji.getModel().getValueAt(0, 0).toString() +"' Pada Bulan atau Minggu Tersebut Sudah Tercetak !");
                return;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        DefaultTableModel mdl = (DefaultTableModel) tabelGaji.getModel();

        if (mdl.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null,"Terlalu Banyak Data, Silahkan Pilih 1 !");
           
        } else {
            try {
            String valId = tabelGaji.getValueAt(tabelGaji.getSelectedRow(), 0).toString();
            String valNama = tabelGaji.getValueAt(tabelGaji.getSelectedRow(), 1).toString();
            String valBln = tabelGaji.getValueAt(tabelGaji.getSelectedRow(), 2).toString();
            String valMasuk = tabelGaji.getValueAt(tabelGaji.getSelectedRow(), 3).toString();
            String valAmbil = tabelGaji.getValueAt(tabelGaji.getSelectedRow(), 4).toString();
            String valTotal = tabelGaji.getValueAt(tabelGaji.getSelectedRow(), 5).toString();
                    
                    String sql = "INSERT INTO slip_kry VALUES ('"+ valId +
                            "','"+ valNama +"','"+ valBln +"','"+ valMasuk +"','"+ valAmbil +"','"+ valTotal +"')";
                    st.executeUpdate(sql);
                    
                    String sql2 = "INSERT IGNORE INTO history_gaji (Id,nama,Bulan,masuk,ambil_gaji,total_gaji) VALUES ('"+ valId +
                            "','"+ valNama +"','"+ valBln +"','"+ valMasuk +"','"+ valAmbil +"','"+ valTotal +"')";
                    st.executeUpdate(sql2);
                  
                    String file ="src/report/slipGaji.jasper";
                    JasperPrint jp = JasperFillManager.fillReport(file, null, koneksi.db_karyawan.konek());
                    JasperViewer jv = new JasperViewer(jp,false);
                    jv.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
        }    
    }//GEN-LAST:event_cetakActionPerformed

    private void cmbBulanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBulanItemStateChanged
        cari();
    }//GEN-LAST:event_cmbBulanItemStateChanged

    private void cetakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cetakMouseClicked
        
    }//GEN-LAST:event_cetakMouseClicked

    private void cmbMingguItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMingguItemStateChanged
        cari();
    }//GEN-LAST:event_cmbMingguItemStateChanged

    private void cmbMingguActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMingguActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMingguActionPerformed

    private void cmbAmbilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAmbilItemStateChanged
        cari();
    }//GEN-LAST:event_cmbAmbilItemStateChanged

    private void cmbAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAmbilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAmbilActionPerformed

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
            java.util.logging.Logger.getLogger(formSlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formSlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formSlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formSlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formSlip().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cetak;
    private javax.swing.JComboBox<String> cmbAmbil;
    private javax.swing.JComboBox<String> cmbBulan;
    private javax.swing.JComboBox<String> cmbMinggu;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tabelGaji;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables

}
