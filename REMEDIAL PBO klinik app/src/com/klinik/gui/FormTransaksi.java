package com.klinik.gui;

import com.klinik.dao.*;
import com.klinik.exception.StokTidakCukupException;
import com.klinik.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FormTransaksi extends JFrame {

    private PasienDAO pasienDAO = new PasienDAO();
    private DokterDAO dokterDAO = new DokterDAO();
    private ObatDAO obatDAO = new ObatDAO();
    private PemeriksaanDAO pemeriksaanDAO = new PemeriksaanDAO();

    private JComboBox<Pasien> cmbPasien;
    private JComboBox<Dokter> cmbDokter;
    private JTextField txtKeluhan, txtDiagnosa, txtBiayaPeriksa;
    private JComboBox<Obat> cmbObat;
    private JTextField txtJumlahObat;
    private JTable tabelResep;
    private DefaultTableModel modelResep;
    private Pemeriksaan pemeriksaanBaru;

    public FormTransaksi() {
        setTitle("Transaksi Pemeriksaan Pasien");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(buatPanelForm(), BorderLayout.NORTH);
        add(buatPanelResep(), BorderLayout.CENTER);
        add(buatPanelSimpan(), BorderLayout.SOUTH);

        muatComboBox();
        pemeriksaanBaru = null;
    }

    private JPanel buatPanelForm() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Data Pemeriksaan"));

        cmbPasien = new JComboBox<>();
        cmbDokter = new JComboBox<>();
        txtKeluhan = new JTextField();
        txtDiagnosa = new JTextField();
        txtBiayaPeriksa = new JTextField("25000");

        panel.add(new JLabel("Pasien:"));
         panel.add(cmbPasien);
        panel.add(new JLabel("Dokter:"));
        panel.add(cmbDokter);

        panel.add(new JLabel("Keluhan:"));
        panel.add(txtKeluhan);
        panel.add(new JLabel("Diagnosa:"));
         panel.add(txtDiagnosa);

        panel.add(new JLabel("Biaya Periksa:"));
        panel.add(txtBiayaPeriksa);
        panel.add(new JLabel());
        panel.add(new JLabel());

        return panel;
    }

    private JPanel buatPanelResep() {
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cmbObat = new JComboBox<>();
        txtJumlahObat = new JTextField(5);
        JButton btnTambahResep = new JButton("Tambah Obat ke Resep");
        btnTambahResep.addActionListener(e -> tambahResep());

        panelInput.add(new JLabel("Obat:"));
        panelInput.add(cmbObat);
        panelInput.add(new JLabel("Jumlah:"));
        panelInput.add(txtJumlahObat);
        panelInput.add(btnTambahResep);

        modelResep = new DefaultTableModel(new Object[]{"Nama Obat", "Jumlah", "Harga Satuan", "Subtotal"}, 0);
        tabelResep = new JTable(modelResep);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Resep Obat"));
        panel.add(panelInput, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabelResep), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buatPanelSimpan() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSimpan = new JButton("Simpan Transaksi");
        btnSimpan.addActionListener(e -> simpanTransaksi());
        panel.add(btnSimpan);
        return panel;
    }

    private void muatComboBox() {
        try {
            List<Pasien> daftarPasien = pasienDAO.tampilkanSemua();
            for (Pasien p : daftarPasien) cmbPasien.addItem(p);

            List<Dokter> daftarDokter = dokterDAO.tampilkanSemua();
            for (Dokter d : daftarDokter) cmbDokter.addItem(d);

            List<Obat> daftarObat = obatDAO.tampilkanSemua();
            for (Obat o : daftarObat) cmbObat.addItem(o);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    // dipakai buat nampung resep sementara sebelum disimpan ke DB
    private java.util.List<DetailResep> resepSementara = new java.util.ArrayList<>();

    private void tambahResep() {
        Obat obatDipilih = (Obat) cmbObat.getSelectedItem();
        if (obatDipilih == null) {
            JOptionPane.showMessageDialog(this, "Data obat kosong, silakan tambah data obat dulu");
            return;
        }
        int jumlah;
        try {
            jumlah = Integer.parseInt(txtJumlahObat.getText().trim());
            if (jumlah <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah obat harus angka lebih dari 0");
            return;
        }

        if (jumlah > obatDipilih.getStok()) {
            JOptionPane.showMessageDialog(this, "Stok obat tidak mencukupi! Sisa stok: " + obatDipilih.getStok());
            return;
        }

        DetailResep resep = new DetailResep(0, 0, obatDipilih, jumlah);
        resepSementara.add(resep);
        modelResep.addRow(new Object[]{obatDipilih.getNamaObat(), jumlah, obatDipilih.getHarga(), resep.getSubtotal()});
        txtJumlahObat.setText("");
    }

    private void simpanTransaksi() {
        Pasien pasienDipilih = (Pasien) cmbPasien.getSelectedItem();
        Dokter dokterDipilih = (Dokter) cmbDokter.getSelectedItem();

        if (pasienDipilih == null || dokterDipilih == null) {
            JOptionPane.showMessageDialog(this, "Data pasien/dokter belum tersedia");
            return;
        }
        if (txtDiagnosa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Diagnosa wajib diisi");
            return;
        }

        double biaya;
        try {
            biaya = Double.parseDouble(txtBiayaPeriksa.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Biaya periksa harus berupa angka");
            return;
        }

        Pemeriksaan pemeriksaan = new Pemeriksaan(0, pasienDipilih, dokterDipilih, LocalDate.now(),
                txtKeluhan.getText(), txtDiagnosa.getText(), biaya);
        for (DetailResep r : resepSementara) {
            pemeriksaan.tambahResep(r);
        }

        try {
            pemeriksaanDAO.simpanTransaksi(pemeriksaan);
            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!\nTotal biaya: Rp" + pemeriksaan.getTotalBiaya());
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi: " + e.getMessage());
        } catch (StokTidakCukupException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
