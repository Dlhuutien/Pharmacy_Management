package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.DoiMatKhauDAO;

public class DoiMatKhau_UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnCenter, pnNorth;
	private JTextField txtTaiKhoan;
	private JPasswordField txtMK, txtMKMoi, txtXacNhanMK;
	private JButton btnDangNhap, btnThoat, btnDoiMK;
	private DoiMatKhauDAO doiMatKhauDAO;

	public DoiMatKhau_UI() throws Exception {
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2005);
		try {
			doiMatKhauDAO = (DoiMatKhauDAO) registry.lookup("DoiMatKhauDAO");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Không thể kết nối đến máy chủ RMI");
		}
		createGui();
	}

	public void createGui() {
		setTitle("Đổi mật khẩu");
		setSize(500, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		doiMK();
	}

	public void doiMK() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());
		Color mauNen = new Color(102, 205, 170);

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Đổi mật khẩu");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(Color.RED);
		pnNorth.add(lblTitle);
		pnNorth.setBackground(mauNen);

		pnCenter = new JPanel();
		pnContent.add(pnCenter, BorderLayout.CENTER);
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();
		Box b6 = Box.createHorizontalBox();
		b1.setBorder(BorderFactory.createTitledBorder("Tài khoản"));
		b2.setBorder(BorderFactory.createTitledBorder("Mật khẩu cũ"));
		b3.setBorder(BorderFactory.createTitledBorder("Mật khẩu mới"));
		b4.setBorder(BorderFactory.createTitledBorder("Xác nhận mật khẩu mới"));
		txtTaiKhoan = new JTextField(15);
		txtMK = new JPasswordField(15);
		txtMKMoi = new JPasswordField(15);
		txtXacNhanMK = new JPasswordField(15);
		b1.add(txtTaiKhoan);
		b2.add(txtMK);
		b3.add(txtMKMoi);
		b4.add(txtXacNhanMK);
		txtTaiKhoan.setColumns(30);

		btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setBackground(mauNen);
		btnDangNhap.setForeground(Color.WHITE);
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(mauNen);
		btnThoat.setForeground(Color.WHITE);
		btnDoiMK = new JButton("Đổi mật khẩu");
		btnDoiMK.setBackground(mauNen);
		btnDoiMK.setForeground(Color.WHITE);
		b5.add(btnDoiMK);
		b5.add(Box.createHorizontalStrut(10));
		b5.add(btnThoat);

		JLabel mota = new JLabel("Nếu bạn muốn đăng nhập hãy chọn:  ");
		b6.add(mota);
		b6.add(btnDangNhap);

		b.add(b1);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(b2);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(b3);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(b4);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b5);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b6);
		pnCenter.add(b);

		this.add(pnContent);
		btnDangNhap.addActionListener(this);
		btnDoiMK.addActionListener(this);
		btnThoat.addActionListener(this);

	}

	public static void main(String[] args) throws Exception {
		new DoiMatKhau_UI().setVisible(true);
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		Object o = e.getSource();
//		if (o.equals(btnDangNhap)) {
//			try {
//				new DangNhap_UI().setVisible(true);
////				new DoiMatKhau_UI().dispose();
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		} else if (o.equals(btnDoiMK)) {
//		    char[] pwd = txtMKMoi.getPassword();
//		    char[] xacNhanPWD = txtXacNhanMK.getPassword();
//		    if (pwd.length == 0 || txtMK.getPassword().length == 0 || xacNhanPWD.length == 0) {
//		        JOptionPane.showMessageDialog(this, "Bạn chưa nhập đủ thông tin");
//		    } else {
//		        String pwdd = new String(pwd);
//		        String xacNhanPWDD = new String(xacNhanPWD);
//		        if (pwdd.equalsIgnoreCase(xacNhanPWDD)) {
//		            try {
//		                doiMatKhauDAO.thucHienDoiMK(txtTaiKhoan.getText(), pwdd);
////		            	doiMatKhauDAO.thucHienDoiMK(txtTaiKhoan.getText(), pwdd);
//		            } catch (RemoteException e1) {
//		                e1.printStackTrace();
//		            }
//		        } else {
//		            JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận mật khẩu không trùng khớp");
//		            txtMKMoi.requestFocus();
//		            txtXacNhanMK.setText("");
//		        }
//		    }
//		}else if (o.equals(btnThoat)) {
//			dispose();
//		}
//	}
//
//}
	  @Override
	    public void actionPerformed(ActionEvent e) {
	        Object o = e.getSource();
	        if (o.equals(btnDangNhap)) {
	            try {
	                new DangNhap_UI().setVisible(true);
	            } catch (Exception e1) {
	                e1.printStackTrace();
	            }
	        } else if (o.equals(btnDoiMK)) {
	            char[] pwd = txtMKMoi.getPassword();
	            char[] xacNhanPWD = txtXacNhanMK.getPassword();
	            if (pwd.length == 0 || txtMK.getPassword().length == 0 || xacNhanPWD.length == 0) {
	                JOptionPane.showMessageDialog(this, "Bạn chưa nhập đủ thông tin");
	            } else {
	                String pwdd = new String(pwd);
	                String xacNhanPWDD = new String(xacNhanPWD);
	                if (pwdd.equalsIgnoreCase(xacNhanPWDD)) {
	                    try {
	                        boolean result = doiMatKhauDAO.thucHienDoiMK(txtTaiKhoan.getText(), pwdd);
	                        if (result) {
	                            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
	                        } else {
	                            JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc mật khẩu không đúng");
	                            txtMK.setText("");
	                            txtMK.requestFocus();
	                        }
	                    } catch (RemoteException e1) {
	                        e1.printStackTrace();
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận mật khẩu không trùng khớp");
	                    txtMKMoi.requestFocus();
	                    txtXacNhanMK.setText("");
	                }
	            }
	        } else if (o.equals(btnThoat)) {
	            dispose();
	        }
	    }
	}