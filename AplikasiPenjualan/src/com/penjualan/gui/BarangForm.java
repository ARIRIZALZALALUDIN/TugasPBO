package com.penjualan.gui;

import com.penjualan.dao.BarangDAO;
import com.penjualan.model.Barang;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BarangForm extends MasterForm<Barang> {
    private final JTextField txtId = new JTextField(), txtKode = new JTextField(), txtNama = new JTextField(),
            txtKategori = new JTextField(), txtHarga = new JTextField(), txtStok = new JTextField();
    private final BarangDAO dao = new BarangDAO();

    public BarangForm() { super("Data Barang", 650, 450); init(); }

    @Override protected String[] kolom() { return new String[]{"ID", "Kode", "Nama", "Kategori", "Harga", "Stok"}; }

    @Override protected JPanel panelForm() {
        JPanel p = new JPanel(new GridLayout(6, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Form Barang"));
        txtId.setEditable(false);
        p.add(new JLabel("ID")); p.add(txtId);
        p.add(new JLabel("Kode")); p.add(txtKode);
        p.add(new JLabel("Nama Barang")); p.add(txtNama);
        p.add(new JLabel("Kategori")); p.add(txtKategori);
        p.add(new JLabel("Harga")); p.add(txtHarga);
        p.add(new JLabel("Stok")); p.add(txtStok);
        return p;
    }

    @Override protected List<Barang> cari(String keyword) { return dao.cari(keyword); }

    @Override protected Object[] keBaris(Barang b) {
        return new Object[]{b.getId(), b.getKode(), b.getNama(), b.getKategori(), b.getHarga(), b.getStok()};
    }

    @Override protected void isiForm(int row) {
        txtId.setText(model.getValueAt(row, 0).toString());
        txtKode.setText(model.getValueAt(row, 1).toString());
        txtNama.setText(model.getValueAt(row, 2).toString());
        txtKategori.setText(model.getValueAt(row, 3).toString());
        txtHarga.setText(model.getValueAt(row, 4).toString());
        txtStok.setText(model.getValueAt(row, 5).toString());
    }

    @Override protected void simpan() throws Exception {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        dao.simpan(new Barang(id, txtKode.getText(), txtNama.getText(), txtKategori.getText(),
                Double.parseDouble(txtHarga.getText()), Integer.parseInt(txtStok.getText())));
    }

    @Override protected void hapus() throws Exception {
        if (!txtId.getText().isEmpty()) dao.hapus(Integer.parseInt(txtId.getText()));
    }

    @Override protected void bersihkan() {
        txtId.setText(""); txtKode.setText(""); txtNama.setText("");
        txtKategori.setText(""); txtHarga.setText(""); txtStok.setText("");
        table.clearSelection();
    }
}
