package com.penjualan.gui;

import com.penjualan.dao.TransaksiDAO;
import com.penjualan.model.Transaksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LaporanForm extends JFrame {

    public LaporanForm() {
        setTitle("Laporan Penjualan");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"No Transaksi", "Tanggal", "Pelanggan", "Kasir", "Barang", "Jumlah", "Harga", "Subtotal", "Total"}, 0);
        JTable table = new JTable(model);

        TransaksiDAO dao = new TransaksiDAO();
        for (Transaksi t : dao.laporan()) {
            model.addRow(new Object[]{t.getNoTransaksi(), t.getTanggal(), t.getNamaPelanggan(), t.getNamaKasir(),
                    t.getDetail().get(0).getNamaBarang(), t.getDetail().get(0).getJumlah(),
                    t.getDetail().get(0).getHarga(), t.getDetail().get(0).getSubtotal(), t.getTotalBayar()});
        }

        JLabel lblTotal = new JLabel(String.format("Grand Total: Rp %,.0f", dao.totalSemua()), SwingConstants.RIGHT);
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(lblTotal, BorderLayout.SOUTH);
    }
}
