package com.penjualan.gui;

import com.penjualan.dao.UserDAO;
import com.penjualan.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserForm extends MasterForm<User> {
    private final JTextField txtId = new JTextField(), txtUser = new JTextField(), txtNama = new JTextField();
    private final JPasswordField txtPass = new JPasswordField();
    private final JComboBox<String> cbRole = new JComboBox<>(new String[]{"admin", "kasir"});
    private final UserDAO dao = new UserDAO();

    public UserForm() { super("Data User", 600, 420); init(); }

    @Override protected boolean pakaiPencarian() { return false; } // tidak diminta di ketentuan tugas

    @Override protected String[] kolom() { return new String[]{"ID", "Username", "Password", "Nama", "Role"}; }

    @Override protected JPanel panelForm() {
        JPanel p = new JPanel(new GridLayout(5, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Form User"));
        txtId.setEditable(false);
        p.add(new JLabel("ID")); p.add(txtId);
        p.add(new JLabel("Username")); p.add(txtUser);
        p.add(new JLabel("Password")); p.add(txtPass);
        p.add(new JLabel("Nama Lengkap")); p.add(txtNama);
        p.add(new JLabel("Role")); p.add(cbRole);
        return p;
    }

    @Override protected List<User> cari(String keyword) { return dao.getAll(); }

    @Override protected Object[] keBaris(User u) {
        return new Object[]{u.getId(), u.getUsername(), u.getPassword(), u.getNama(), u.getRole()};
    }

    @Override protected void isiForm(int row) {
        txtId.setText(model.getValueAt(row, 0).toString());
        txtUser.setText(model.getValueAt(row, 1).toString());
        txtPass.setText(model.getValueAt(row, 2).toString());
        txtNama.setText(model.getValueAt(row, 3).toString());
        cbRole.setSelectedItem(model.getValueAt(row, 4));
    }

    @Override protected void simpan() throws Exception {
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        dao.simpan(new User(id, txtUser.getText(), new String(txtPass.getPassword()), txtNama.getText(),
                (String) cbRole.getSelectedItem()));
    }

    @Override protected void hapus() throws Exception {
        if (!txtId.getText().isEmpty()) dao.hapus(Integer.parseInt(txtId.getText()));
    }

    @Override protected void bersihkan() {
        txtId.setText(""); txtUser.setText(""); txtPass.setText(""); txtNama.setText("");
        table.clearSelection();
    }
}
