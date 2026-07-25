package com.klinik.gui;

import com.klinik.dao.DokterDAO;
import com.klinik.model.Dokter;
import com.klinik.util.ValidasiInput;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FormDokter extends JFrame {

    private DokterDAO dokterDAO = new DokterDAO();

    private JTextField txtId, txtNama, txtSpesialisasi, txtTelp, txtCari;
    private JTable tabel;
    private DefaultTableModel model;

    public FormDokter() {
        setTitle("Data Master Dokter");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(buatPanelForm(), BorderLayout.NORTH);
        add(buatPanelTabel(), BorderLayout.CENTER);
        muatData();
    }

    private JPanel buatPanelForm() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Form Data Dokter"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNama = new JTextField();
        txtSpesialisasi = new JTextField();
        txtTelp = new JTextField();
        txtCari = new JTextField();

        panel.add(new JLabel("ID (auto):"));
        panel.add(txtId);
        panel.add(new JLabel("Nama:"));
        panel.add(txtNama);
        panel.add(new JLabel("Spesialisasi:"));
        panel.add(txtSpesialisasi);
        panel.add(new JLabel("No. Telp:"));
        panel.add(txtTelp);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnBersih = new JButton("Bersihkan Form");
        btnTambah.addActionListener(e -> tambahData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersih.addActionListener(e -> bersihkanForm());
        panelTombol.add(btnTambah);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnBersih);

        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCari.add(new JLabel("Cari:"));
        panelCari.add(txtCari);
        JButton btnCari = new JButton("Cari");
        btnCari.addActionListener(e -> cariData());
        panelCari.add(btnCari);

        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.add(panelTombol, BorderLayout.WEST);
        panelBawah.add(panelCari, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(panel, BorderLayout.CENTER);
        wrapper.add(panelBawah, BorderLayout.SOUTH);
        return wrapper;
    }

    private JScrollPane buatPanelTabel() {
        model = new DefaultTableModel(new Object[]{"ID", "Nama", "Spesialisasi", "No. Telp"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabel = new JTable(model);
        tabel.getSelectionModel().addListSelectionListener(e -> isiFormDariTabel());
        return new JScrollPane(tabel);
    }

    private void isiFormDariTabel() {
        int row = tabel.getSelectedRow();
        if (row < 0) return;
        txtId.setText(model.getValueAt(row, 0).toString());
        txtNama.setText(model.getValueAt(row, 1).toString());
        txtSpesialisasi.setText(model.getValueAt(row, 2).toString());
        txtTelp.setText(model.getValueAt(row, 3).toString());
    }

    private void muatData() {
        try {
            model.setRowCount(0);
            List<Dokter> daftar = dokterDAO.tampilkanSemua();
            for (Dokter d : daftar) {
                model.addRow(new Object[]{d.getId(), d.getNama(), d.getSpesialisasi(), d.getNoTelp()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
        }
    }

    private void tambahData() {
        if (!ValidasiInput.isNamaValid(txtNama.getText())) {
            JOptionPane.showMessageDialog(this, "Nama tidak valid");
            return;
        }
        try {
            Dokter d = new Dokter(0, ValidasiInput.toTitleCase(txtNama.getText()), txtSpesialisasi.getText(), txtTelp.getText());
            dokterDAO.tambah(d);
            JOptionPane.showMessageDialog(this, "Data dokter berhasil ditambahkan");
            muatData();
            bersihkanForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menambah data: " + e.getMessage());
        }
    }

    private void ubahData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu");
            return;
        }
        try {
            Dokter d = new Dokter(Integer.parseInt(txtId.getText()), ValidasiInput.toTitleCase(txtNama.getText()), txtSpesialisasi.getText(), txtTelp.getText());
            dokterDAO.ubah(d);
            JOptionPane.showMessageDialog(this, "Data dokter berhasil diubah");
            muatData();
            bersihkanForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
        }
    }

    private void hapusData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu");
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;
        try {
            dokterDAO.hapus(Integer.parseInt(txtId.getText()));
            JOptionPane.showMessageDialog(this, "Data dokter berhasil dihapus");
            muatData();
            bersihkanForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }

    private void cariData() {
        String kataKunci = txtCari.getText().trim();
        if (kataKunci.isEmpty()) {
            muatData();
            return;
        }
        try {
            model.setRowCount(0);
            List<Dokter> hasil = dokterDAO.cari(kataKunci);
            for (Dokter d : hasil) {
                model.addRow(new Object[]{d.getId(), d.getNama(), d.getSpesialisasi(), d.getNoTelp()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtNama.setText("");
        txtSpesialisasi.setText("");
        txtTelp.setText("");
        tabel.clearSelection();
    }
}
