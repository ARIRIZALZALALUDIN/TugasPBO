package com.penjualan.gui;

import com.penjualan.dao.BarangDAO;
import com.penjualan.dao.PelangganDAO;
import com.penjualan.dao.TransaksiDAO;
import com.penjualan.exception.AplikasiException;
import com.penjualan.model.Barang;
import com.penjualan.model.DetailTransaksi;
import com.penjualan.model.Pelanggan;
import com.penjualan.model.Transaksi;
import com.penjualan.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransaksiForm extends JFrame {

    private final JComboBox<Pelanggan> cbPelanggan = new JComboBox<>(new PelangganDAO().cari(null).toArray(new Pelanggan[0]));
    private final JComboBox<Barang> cbBarang = new JComboBox<>(new BarangDAO().cari(null).toArray(new Barang[0]));
    private final JTextField txtJumlah = new JTextField();
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Barang", "Jumlah", "Harga", "Subtotal"}, 0);
    private final JTable table = new JTable(model);
    private final JLabel lblTotal = new JLabel("Total: Rp 0", SwingConstants.RIGHT);
    private final TransaksiDAO dao = new TransaksiDAO();
    private Transaksi transaksi = new Transaksi();

    public TransaksiForm() {
        setTitle("Transaksi Penjualan");
        setSize(650, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Item Transaksi"));
        form.add(new JLabel("Pelanggan")); form.add(cbPelanggan);
        form.add(new JLabel("Barang")); form.add(cbBarang);
        form.add(new JLabel("Jumlah")); form.add(txtJumlah);

        JButton btnTambah = new JButton("Tambah ke Keranjang");
        JButton btnSimpan = new JButton("Simpan Transaksi");
        JPanel tombol = new JPanel();
        tombol.add(btnTambah); tombol.add(btnSimpan);

        JPanel utara = new JPanel(new BorderLayout());
        utara.add(form, BorderLayout.CENTER);
        utara.add(tombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(utara, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(lblTotal, BorderLayout.SOUTH);

        btnTambah.addActionListener(e -> {
            try {
                Barang b = (Barang) cbBarang.getSelectedItem();
                int jml = Integer.parseInt(txtJumlah.getText());
                if (jml <= 0 || jml > b.getStok()) {
                    JOptionPane.showMessageDialog(this, "Jumlah tidak valid / stok kurang (stok: " + b.getStok() + ")");
                    return;
                }
                DetailTransaksi d = new DetailTransaksi(b.getId(), b.getNama(), jml, b.getHarga());
                transaksi.tambah(d);
                model.addRow(new Object[]{d.getNamaBarang(), d.getJumlah(), d.getHarga(), d.getSubtotal()});
                lblTotal.setText(String.format("Total: Rp %,.0f", hitungTotal()));
                txtJumlah.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Jumlah harus angka!");
            }
        });

        btnSimpan.addActionListener(e -> {
            if (transaksi.getDetail().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Keranjang masih kosong!");
                return;
            }
            Pelanggan p = (Pelanggan) cbPelanggan.getSelectedItem();
            transaksi.setNoTransaksi(dao.nomorBaru());
            transaksi.setIdPelanggan(p.getId());
            transaksi.setIdUser(Session.user.getId());
            try {
                dao.simpan(transaksi);
                JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!");
                transaksi = new Transaksi();
                model.setRowCount(0);
                lblTotal.setText("Total: Rp 0");
            } catch (AplikasiException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private double hitungTotal() {
        double total = 0;
        for (DetailTransaksi d : transaksi.getDetail()) total += d.getSubtotal();
        return total;
    }
}
