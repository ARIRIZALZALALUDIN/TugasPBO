package com.klinik.gui;

import com.klinik.model.User;

import javax.swing.*;
import java.awt.*;

public class MenuUtama extends JFrame {

    private User userLogin;

    public MenuUtama(User user) {
        this.userLogin = user;

        setTitle("Sistem Klinik Sederhana - Menu Utama");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(buatMenuBar());

        JLabel lblSelamatDatang = new JLabel(
                "Selamat datang, " + userLogin.getNamaLengkap() + " (" + userLogin.getRole() + ")",
                SwingConstants.CENTER);
        lblSelamatDatang.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel panelTengah = new JPanel(new GridLayout(2, 2, 15, 15));
        panelTengah.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        panelTengah.add(buatTombolMenu("Data Pasien", e -> new FormPasien().setVisible(true)));
        panelTengah.add(buatTombolMenu("Data Dokter", e -> new FormDokter().setVisible(true)));
        panelTengah.add(buatTombolMenu("Data Obat", e -> new FormObat().setVisible(true)));
        panelTengah.add(buatTombolMenu("Transaksi Pemeriksaan", e -> new FormTransaksi().setVisible(true)));

        setLayout(new BorderLayout());
        add(lblSelamatDatang, BorderLayout.NORTH);
        add(panelTengah, BorderLayout.CENTER);
    }

    private JButton buatTombolMenu(String teks, java.awt.event.ActionListener aksi) {
        JButton btn = new JButton(teks);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.addActionListener(aksi);
        return btn;
    }

    private JMenuBar buatMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuMaster = new JMenu("Data Master");
        JMenuItem itemPasien = new JMenuItem("Pasien");
        JMenuItem itemDokter = new JMenuItem("Dokter");
        JMenuItem itemObat = new JMenuItem("Obat");
        itemPasien.addActionListener(e -> new FormPasien().setVisible(true));
        itemDokter.addActionListener(e -> new FormDokter().setVisible(true));
        itemObat.addActionListener(e -> new FormObat().setVisible(true));
        menuMaster.add(itemPasien);
        menuMaster.add(itemDokter);
        menuMaster.add(itemObat);

        JMenu menuTransaksi = new JMenu("Transaksi");
        JMenuItem itemPemeriksaan = new JMenuItem("Pemeriksaan Baru");
        itemPemeriksaan.addActionListener(e -> new FormTransaksi().setVisible(true));
        menuTransaksi.add(itemPemeriksaan);

        JMenu menuLaporan = new JMenu("Laporan");
        JMenuItem itemLaporan = new JMenuItem("Laporan Pemeriksaan");
        itemLaporan.addActionListener(e -> new FormLaporan().setVisible(true));
        menuLaporan.add(itemLaporan);

        JMenu menuAkun = new JMenu("Akun");
        JMenuItem itemLogout = new JMenuItem("Logout");
        itemLogout.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
        menuAkun.add(itemLogout);

        menuBar.add(menuMaster);
        menuBar.add(menuTransaksi);
        menuBar.add(menuLaporan);
        menuBar.add(menuAkun);
        return menuBar;
    }
}
