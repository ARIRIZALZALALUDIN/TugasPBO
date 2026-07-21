package com.penjualan.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Superclass form CRUD (INHERITANCE). Kerangka tabel+tombol+alur simpan/hapus/cari ditulis
// sekali di sini, lalu dipakai BarangForm/PelangganForm/UserForm secara berbeda (POLIMORFISME).
public abstract class MasterForm<T> extends JFrame {
    protected final DefaultTableModel model = new DefaultTableModel(kolom(), 0);
    protected final JTable table = new JTable(model);
    private final JTextField txtCari = new JTextField(10);

    public MasterForm(String judul, int lebar, int tinggi) {
        setTitle(judul);
        setSize(lebar, tinggi);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    // Dipanggil subclass di akhir constructor-nya (field form sudah pasti siap saat itu).
    protected void init() {
        JButton btnSimpan = new JButton("Simpan"), btnHapus = new JButton("Hapus"),
                btnBersih = new JButton("Bersihkan"), btnCari = new JButton("Cari");
        JPanel tombol = new JPanel();
        tombol.add(btnSimpan); tombol.add(btnHapus); tombol.add(btnBersih);
        if (pakaiPencarian()) {
            tombol.add(new JLabel(" | Cari:")); tombol.add(txtCari); tombol.add(btnCari);
            btnCari.addActionListener(e -> muatUlang(txtCari.getText()));
        }
        add(panelForm(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(tombol, BorderLayout.SOUTH);
        muatUlang(null);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (table.getSelectedRow() >= 0) isiForm(table.getSelectedRow());
        });
        btnSimpan.addActionListener(e -> {
            try { simpan(); muatUlang(null); bersihkan(); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage()); }
        });
        btnHapus.addActionListener(e -> {
            try { hapus(); muatUlang(null); bersihkan(); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage()); }
        });
        btnBersih.addActionListener(e -> bersihkan());
    }

    protected void muatUlang(String keyword) {
        model.setRowCount(0);
        for (T obj : cari(keyword)) model.addRow(keBaris(obj));
    }

    // ==== wajib di-override tiap form turunan ====
    protected abstract String[] kolom();
    protected abstract JPanel panelForm();
    protected abstract List<T> cari(String keyword);
    protected abstract Object[] keBaris(T obj);
    protected abstract void isiForm(int row);
    protected abstract void simpan() throws Exception;
    protected abstract void hapus() throws Exception;
    protected abstract void bersihkan();
    protected boolean pakaiPencarian() { return true; } // false utk form yg tak butuh pencarian
}
