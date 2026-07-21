package com.penjualan.gui;

import com.penjualan.util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {

    public MainForm() {
        setTitle("Aplikasi Penjualan");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbl = new JLabel("Selamat datang, " + Session.user.getNama() + " (" + Session.user.getRole() + ")", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(lbl);

        JMenu menuMaster = new JMenu("Master Data");
        JMenu menuTransaksi = new JMenu("Transaksi");
        JMenu menuAkun = new JMenu("Akun");

        tambahMenu(menuMaster, "Data User", e -> new UserForm().setVisible(true));
        tambahMenu(menuMaster, "Data Barang", e -> new BarangForm().setVisible(true));
        tambahMenu(menuMaster, "Data Pelanggan", e -> new PelangganForm().setVisible(true));
        tambahMenu(menuTransaksi, "Transaksi Penjualan", e -> new TransaksiForm().setVisible(true));
        tambahMenu(menuTransaksi, "Laporan Penjualan", e -> new LaporanForm().setVisible(true));
        tambahMenu(menuAkun, "Logout", e -> { Session.user = null; dispose(); new LoginForm().setVisible(true); });

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuMaster);
        menuBar.add(menuTransaksi);
        menuBar.add(menuAkun);
        setJMenuBar(menuBar);
    }

    private void tambahMenu(JMenu menu, String label, ActionListener aksi) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(aksi);
        menu.add(item);
    }
}
