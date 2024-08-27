package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import dao.QuanLyDAO;
import entities.QuanLy;

public class XemThongTin_GUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField lbtMa;
    private JTextField lbtTen;
    private JTextField lbtGioiTinh;
    private JTextField lbtSoDienThoai;
    private JTextField lbtNgaySinh;
    private JTextField lbtLuong;
    private JTextField txtMa;
    private JTextField lbtTK;
    private JPasswordField lbtMK; // Changed to JPasswordField
    private JButton btnDoiMatKhau;
    private QuanLyDAO ql_dao;

    public XemThongTin_GUI(entities.TaiKhoanQuanLy cur) throws Exception {
		try {
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2005);
			ql_dao = (QuanLyDAO) registry.lookup("QuanLyDAO");
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new Exception("Không thể kết nối đến máy chủ RMI");
		}
		
        QuanLy ql = ql_dao.getQuanLyTheoMaQL(cur.getTaiKhoan());
        setLayout(new BorderLayout());
        Box tong = Box.createVerticalBox();
        Box p1 = Box.createVerticalBox();
        p1.setBorder(BorderFactory.createTitledBorder("Thông tin cá nhân"));
        JLabel lbMa = new JLabel("Mã quản lý: ");
        JLabel lbTen = new JLabel("Tên quản lý: ");
        JLabel lbGioiTinh = new JLabel("Giới tính: ");
        JLabel lbSoDienThoai = new JLabel("Số điện thoại: ");
        JLabel lbNgaySinh = new JLabel("Ngày sinh: ");
        JLabel lbLuong = new JLabel("Lương: ");

        lbtMa = new JTextField(20);
        lbtMa.setText(ql.getMaQL());
        lbtTen = new JTextField(20);
        lbtTen.setText(ql.getTenQL());
        lbtGioiTinh = new JTextField(20);
        lbtGioiTinh.setText(ql.isGioiTinh() ? "Nam" : "Nữ");
        lbtSoDienThoai = new JTextField(20);
        lbtSoDienThoai.setText(ql.getSoDienThoai());
        lbtNgaySinh = new JTextField(20);
//        lbtNgaySinh.setText(ql.getNgaySinh().toString());
        lbtNgaySinh.setText(ql.getNgaySinh() != null ? ql.getNgaySinh().toString() : "");

        lbtLuong = new JTextField(20);
        NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
        lbtLuong.setText(currencyFormatter.format(ql.getLuongCoBan()));

        Box b1, b2, b3, b4, b5, b6;
        b1 = Box.createHorizontalBox();
        b1.add(lbMa);
        b1.add(lbtMa);

        b2 = Box.createHorizontalBox();
        b2.add(lbTen);
        b2.add(lbtTen);

        b3 = Box.createHorizontalBox();
        b3.add(lbGioiTinh);
        b3.add(lbtGioiTinh);

        b4 = Box.createHorizontalBox();
        b4.add(lbSoDienThoai);
        b4.add(lbtSoDienThoai);

        b5 = Box.createHorizontalBox();
        b5.add(lbNgaySinh);
        b5.add(lbtNgaySinh);

        b6 = Box.createHorizontalBox();
        b6.add(lbLuong);
        b6.add(lbtLuong);

        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        p1.add(b4);
        p1.add(b5);
        p1.add(b6);
        JPanel anh = new JPanel();
        JLabel lbHinhDaiDien = new JLabel(createResizedIcon("icons/users.png", 300, 300));
        anh.add(lbHinhDaiDien);
        tong.add(anh);
        tong.add(p1);

        Box b11, b22, p2;
        JLabel lbTK = new JLabel("Tài khoản: ");
        JLabel lbMK = new JLabel("Mật khẩu: ");

        lbtTK = new JTextField(20);
        lbtTK.setText(cur.getTaiKhoan());
        lbtMK = new JPasswordField(20); // Changed to JPasswordField
        lbtMK.setText(cur.getMatKhau()); // Set text for JPasswordField
        btnDoiMatKhau = new JButton("Đổi mật khẩu");

        b11 = Box.createHorizontalBox();
        b11.add(lbTK);
        b11.add(lbtTK);

        b22 = Box.createHorizontalBox();
        b22.add(lbMK);
        b22.add(lbtMK);

        JPanel b33 = new JPanel();
        b33.add(btnDoiMatKhau);

        p2 = Box.createVerticalBox();
        p2.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));
        p2.add(Box.createVerticalGlue());
        p2.add(b11);
        p2.add(b22);
        p2.add(b33);

        tong.add(p2);

        lbMK.setPreferredSize(lbSoDienThoai.getPreferredSize());
        lbTK.setPreferredSize(lbSoDienThoai.getPreferredSize());
        lbMa.setPreferredSize(lbSoDienThoai.getPreferredSize());
        lbTen.setPreferredSize(lbSoDienThoai.getPreferredSize());
        lbGioiTinh.setPreferredSize(lbSoDienThoai.getPreferredSize());
        lbNgaySinh.setPreferredSize(lbSoDienThoai.getPreferredSize());
        lbLuong.setPreferredSize(lbSoDienThoai.getPreferredSize());
        add(tong);
        
        btnDoiMatKhau.addActionListener(this);
    }

    public ImageIcon createResizedIcon(String iconPath, int width, int height) {
        Image originalIcon = new ImageIcon(iconPath).getImage();
        Image resizedIcon = originalIcon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedIcon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o.equals(btnDoiMatKhau)) {
        	try {
				new DoiMatKhau_UI().setVisible(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
}
