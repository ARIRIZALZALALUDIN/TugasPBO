package com.penjualan.gui;

import com.penjualan.dao.PelangganDAO;
import com.penjualan.model.Pelanggan;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PelangganForm extends MasterForm<Pelanggan> {
    private final JTextField txtId = new JTextField(), txtKode = new JTextField(), txtNama = new JTextField(),
            txtAlamat = new JTextField(), txtTelp = new JTextField();
    private final PelangganDAO dao = new PelangganDAO();

    public PelangganForm() { super("Data Pelanggan", 650, 450); init(); }

    @Override protected String[] kolom() { return new String[]{"ID", "Kode", "Nama", "Alamat", "No Telp"}; }

    @Override protected JPanel panelForm() {
        JPanel p = new JPanel(new GridLayout(5, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Form Pelanggan"));
        txtId.setEditable(false);
        p.add(new JLabel("ID")); p.add(txtId);
        p.add(new JLabel("Kode")); p.add(txtKode);
        p.add(new JLabel("Nama")); p.add(txtNama);
        p.add(new JLabel("Alamat")); p.add(txtAlamat);
        p.add(new JLabel("No Telp")); p.add(txtTelp);
        return p;
    }

    @Override protected List<Pelanggan> cari(String keyword) { return dao.cari(keyword); }

    @Override protected Object[] keBaris(Pelanggan pl) {
        return new Object[]{pl.getId(), pl.getKode(), pl.getNama(), pl.getAlamat(), pl.getNoTelp()};
    }

    @Override protected void isiForm(int row) {
        txtId.setText(model.getValueAt(row, 0).toString());
        txtKode.setText(model.getValueAt(row, 1).toString());
        txtNama.setText(model.getValueAt(row, 2).toString());
        txtAlamat.setText(model.getValueAt(row, 3).toString());
        txtTelp.setText(model.getValueAt(row, 4).toString());
    }

    @Override protected void simpan() throws Exception {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        dao.simpan(new Pelanggan(id, txtKode.getText(), txtNama.getText(), txtAlamat.getText(), txtTelp.getText()));
    }

    @Override protected void hapus() throws Exception {
        if (!txtId.getText().isEmpty()) dao.hapus(Integer.parseInt(txtId.getText()));
    }

    @Override protected void bersihkan() {
        txtId.setText(""); txtKode.setText(""); txtNama.setText("");
        txtAlamat.setText(""); txtTelp.setText("");
        table.clearSelection();
    }
}
