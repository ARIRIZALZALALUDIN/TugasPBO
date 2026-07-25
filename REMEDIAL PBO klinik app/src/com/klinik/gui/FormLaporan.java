package com.klinik.gui;

import com.klinik.dao.PemeriksaanDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FormLaporan extends JFrame {

    private PemeriksaanDAO pemeriksaanDAO = new PemeriksaanDAO();
    private JTextField txtDari, txtSampai;
    private JTable tabel;
    private DefaultTableModel model;

    public FormLaporan() {
        setTitle("Laporan Pemeriksaan");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(buatPanelFilter(), BorderLayout.NORTH);
        add(buatPanelTabel(), BorderLayout.CENTER);
        add(buatPanelExport(), BorderLayout.SOUTH);
    }

    private JPanel buatPanelFilter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtDari = new JTextField(LocalDate.now().withDayOfMonth(1).toString(), 10);
        txtSampai = new JTextField(LocalDate.now().toString(), 10);
        JButton btnTampilkan = new JButton("Tampilkan");
        btnTampilkan.addActionListener(e -> tampilkanLaporan());

        panel.add(new JLabel("Dari tanggal (yyyy-mm-dd):"));
        panel.add(txtDari);
        panel.add(new JLabel("Sampai tanggal:"));
        panel.add(txtSampai);
        panel.add(btnTampilkan);
        return panel;
    }

    private JScrollPane buatPanelTabel() {
        model = new DefaultTableModel(new Object[]{"ID Periksa", "Pasien", "Dokter", "Tanggal", "Diagnosa", "Biaya Periksa"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabel = new JTable(model);
        return new JScrollPane(tabel);
    }

    private JPanel buatPanelExport() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnExport = new JButton("Export ke File (.txt)");
        btnExport.addActionListener(e -> exportKeFile());
        panel.add(btnExport);
        return panel;
    }

    private void tampilkanLaporan() {
        try {
            model.setRowCount(0);
            List<String[]> data = pemeriksaanDAO.laporanTransaksi(txtDari.getText().trim(), txtSampai.getText().trim());
            for (String[] baris : data) {
                model.addRow(baris);
            }
            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data pemeriksaan pada rentang tanggal tersebut");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil laporan: " + e.getMessage());
        }
    }

    private void exportKeFile() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diexport. Tampilkan laporan dulu.");
            return;
        }

        String namaFile = "laporan_pemeriksaan_" + LocalDate.now() + ".txt";
        try (FileWriter fw = new FileWriter(namaFile);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("LAPORAN PEMERIKSAAN KLINIK");
            pw.println("Periode: " + txtDari.getText() + " s/d " + txtSampai.getText());
            pw.println("=================================================");

            for (int i = 0; i < model.getRowCount(); i++) {
                pw.printf("%-4s | %-20s | %-20s | %-12s | %-20s | Rp%s%n",
                        model.getValueAt(i, 0), model.getValueAt(i, 1), model.getValueAt(i, 2),
                        model.getValueAt(i, 3), model.getValueAt(i, 4), model.getValueAt(i, 5));
            }

            JOptionPane.showMessageDialog(this, "Laporan berhasil diexport ke file: " + namaFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal export laporan: " + e.getMessage());
        }
    }
}
