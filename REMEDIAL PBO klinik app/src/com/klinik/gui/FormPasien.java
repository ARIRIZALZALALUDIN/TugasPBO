package com.klinik.gui;

import com.klinik.dao.PasienDAO;
import com.klinik.model.Pasien;
import com.klinik.util.ValidasiInput;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FormPasien extends JFrame {

    private PasienDAO pasienDAO = new PasienDAO();

    private JTextField txtId, txtNama, txtAlamat, txtTelp, txtTgl, txtCari;
    private JComboBox<String> cmbJk;
    private JTable tabel;
    private DefaultTableModel model;

    public FormPasien() {
        setTitle("Data Master Pasien");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(buatPanelForm(), BorderLayout.NORTH);
        add(buatPanelTabel(), BorderLayout.CENTER);

        muatData();
    }

    private JPanel buatPanelForm() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Form Data Pasien"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNama = new JTextField();
        txtAlamat = new JTextField();
        txtTelp = new JTextField();
        txtTgl = new JTextField("2000-01-01");
        cmbJk = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        txtCari = new JTextField();

        panel.add(new JLabel("ID (auto):"));
        panel.add(txtId);
        panel.add(new JLabel("Nama:"));
        panel.add(txtNama);

        panel.add(new JLabel("Alamat:"));
        panel.add(txtAlamat);
        panel.add(new JLabel("No. Telp:"));
        panel.add(txtTelp);

        panel.add(new JLabel("Tgl Lahir (yyyy-mm-dd):"));
        panel.add(txtTgl);
        panel.add(new JLabel("Jenis Kelamin:"));
        panel.add(cmbJk);

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
        model = new DefaultTableModel(new Object[]{"ID", "Nama", "Alamat", "No. Telp", "Tgl Lahir", "JK"}, 0) {
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
        txtAlamat.setText(model.getValueAt(row, 2).toString());
        txtTelp.setText(model.getValueAt(row, 3).toString());
        txtTgl.setText(model.getValueAt(row, 4).toString());
        cmbJk.setSelectedItem(model.getValueAt(row, 5).toString());
    }

    private void muatData() {
        try {
            model.setRowCount(0);
            List<Pasien> daftar = pasienDAO.tampilkanSemua();
            for (Pasien p : daftar) {
                model.addRow(new Object[]{p.getId(), p.getNama(), p.getAlamat(), p.getNoTelp(), p.getTanggalLahir(), p.getJenisKelamin()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
        }
    }

    private boolean validasiForm() {
        if (!ValidasiInput.isNamaValid(txtNama.getText())) {
            JOptionPane.showMessageDialog(this, "Nama tidak valid (hanya huruf & spasi)");
            return false;
        }
        if (!ValidasiInput.isNoTelpValid(txtTelp.getText())) {
            JOptionPane.showMessageDialog(this, "No. Telp tidak valid (10-13 digit angka)");
            return false;
        }
        return true;
    }

    private void tambahData() {
        if (!validasiForm()) return;
        try {
            Pasien p = new Pasien(
                    ValidasiInput.toTitleCase(txtNama.getText()),
                    txtAlamat.getText(),
                    txtTelp.getText(),
                    LocalDate.parse(txtTgl.getText()),
                    (String) cmbJk.getSelectedItem()
            );
            pasienDAO.tambah(p);
            JOptionPane.showMessageDialog(this, "Data pasien berhasil ditambahkan");
            muatData();
            bersihkanForm();
        } catch (Exception e) {
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
            Pasien p = new Pasien(
                    Integer.parseInt(txtId.getText()),
                    ValidasiInput.toTitleCase(txtNama.getText()),
                    txtAlamat.getText(),
                    txtTelp.getText(),
                    LocalDate.parse(txtTgl.getText()),
                    (String) cmbJk.getSelectedItem()
            );
            pasienDAO.ubah(p);
            JOptionPane.showMessageDialog(this, "Data pasien berhasil diubah");
            muatData();
            bersihkanForm();
        } catch (Exception e) {
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
            pasienDAO.hapus(Integer.parseInt(txtId.getText()));
            JOptionPane.showMessageDialog(this, "Data pasien berhasil dihapus");
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
            List<Pasien> hasil = pasienDAO.cari(kataKunci);
            for (Pasien p : hasil) {
                model.addRow(new Object[]{p.getId(), p.getNama(), p.getAlamat(), p.getNoTelp(), p.getTanggalLahir(), p.getJenisKelamin()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelp.setText("");
        txtTgl.setText("2000-01-01");
        cmbJk.setSelectedIndex(0);
        tabel.clearSelection();
    }
}
