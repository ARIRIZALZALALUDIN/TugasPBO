package com.klinik.gui;

import com.klinik.dao.ObatDAO;
import com.klinik.model.Obat;
import com.klinik.util.ValidasiInput;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FormObat extends JFrame {

    private ObatDAO obatDAO = new ObatDAO();

    private JTextField txtId, txtNama, txtSatuan, txtHarga, txtStok, txtCari;
    private JTable tabel;
    private DefaultTableModel model;

    public FormObat() {
        setTitle("Data Master Obat");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(buatPanelForm(), BorderLayout.NORTH);
        add(buatPanelTabel(), BorderLayout.CENTER);
        muatData();
    }

    private JPanel buatPanelForm() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Form Data Obat"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNama = new JTextField();
        txtSatuan = new JTextField();
        txtHarga = new JTextField();
        txtStok = new JTextField();
        txtCari = new JTextField();

        panel.add(new JLabel("ID (auto):"));
        panel.add(txtId);
        panel.add(new JLabel("Nama Obat:"));
        panel.add(txtNama);
        panel.add(new JLabel("Satuan:"));
        panel.add(txtSatuan);
        panel.add(new JLabel("Harga:"));
        panel.add(txtHarga);
        panel.add(new JLabel("Stok:"));
        panel.add(txtStok);
        panel.add(new JLabel());
        panel.add(new JLabel());

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
        model = new DefaultTableModel(new Object[]{"ID", "Nama Obat", "Satuan", "Harga", "Stok"}, 0) {
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
        txtSatuan.setText(model.getValueAt(row, 2).toString());
        txtHarga.setText(model.getValueAt(row, 3).toString());
        txtStok.setText(model.getValueAt(row, 4).toString());
    }

    private void muatData() {
        try {
            model.setRowCount(0);
            List<Obat> daftar = obatDAO.tampilkanSemua();
            for (Obat o : daftar) {
                model.addRow(new Object[]{o.getId(), o.getNamaObat(), o.getSatuan(), o.getHarga(), o.getStok()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
        }
    }

    private boolean validasiForm() {
        if (ValidasiInput.isKosong(txtNama.getText())) {
            JOptionPane.showMessageDialog(this, "Nama obat wajib diisi");
            return false;
        }
        if (!ValidasiInput.isAngkaValid(txtStok.getText())) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka");
            return false;
        }
        try {
            Double.parseDouble(txtHarga.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka");
            return false;
        }
        return true;
    }

    private void tambahData() {
        if (!validasiForm()) return;
        try {
            Obat o = new Obat(0, txtNama.getText(), txtSatuan.getText(),
                    Double.parseDouble(txtHarga.getText()), Integer.parseInt(txtStok.getText()));
            obatDAO.tambah(o);
            JOptionPane.showMessageDialog(this, "Data obat berhasil ditambahkan");
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
        if (!validasiForm()) return;
        try {
            Obat o = new Obat(Integer.parseInt(txtId.getText()), txtNama.getText(), txtSatuan.getText(),
                    Double.parseDouble(txtHarga.getText()), Integer.parseInt(txtStok.getText()));
            obatDAO.ubah(o);
            JOptionPane.showMessageDialog(this, "Data obat berhasil diubah");
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
            obatDAO.hapus(Integer.parseInt(txtId.getText()));
            JOptionPane.showMessageDialog(this, "Data obat berhasil dihapus");
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
            List<Obat> hasil = obatDAO.cari(kataKunci);
            for (Obat o : hasil) {
                model.addRow(new Object[]{o.getId(), o.getNamaObat(), o.getSatuan(), o.getHarga(), o.getStok()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtNama.setText("");
        txtSatuan.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        tabel.clearSelection();
    }
}
