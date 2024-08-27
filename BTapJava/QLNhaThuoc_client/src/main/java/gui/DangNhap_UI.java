package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.DangNhapDAO;
import entities.TaiKhoanQuanLy;
import entities.TaiKhoanNhanVien;

public class DangNhap_UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnCenter, pnNorth;
	private JLabel lblError;
	private JTextField txtTaiKhoan;
	private JPasswordField txtMK;
	private JButton btnDangNhap, btnThoat, btnDoiMatKhau;
	private JCheckBox chkQuanLy;
	
	DangNhapDAO dangNhapDAO;

	public DangNhap_UI()
			throws Exception, MalformedURLException, RemoteException, NotBoundException, ClassNotFoundException {
		super("Đăng nhập");
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2005);
		try {
			dangNhapDAO = (DangNhapDAO) registry.lookup("DangNhapDAO");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Không thể kết nối đến máy chủ RMI");
		}
		createGui();
	}

	public void createGui() {
		setTitle("Đăng nhập");
		setSize(420, 320);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		dangNhap();
	}

	public void dangNhap() {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());
		Color mauNen = new Color(102, 205, 170);

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Đăng nhập tài khoản");
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
		Box bError = Box.createHorizontalBox();
		b1.setBorder(BorderFactory.createTitledBorder("Tài khoản"));
		b2.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
		txtTaiKhoan = new JTextField(15);
		txtMK = new JPasswordField(15);
		b1.add(txtTaiKhoan);
		b2.add(txtMK);
		txtTaiKhoan.setColumns(30);
		lblError = new JLabel(" ");
		lblError.setForeground(Color.red);
		bError.add(lblError);
		Box bCheck = Box.createHorizontalBox();
		JLabel lbQuanLy = new JLabel("Quản lý: ");
		chkQuanLy = new JCheckBox();
		bCheck.add(Box.createHorizontalStrut(330));
		bCheck.add(lbQuanLy);
		bCheck.add(chkQuanLy);

		btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setBackground(mauNen);
		btnDangNhap.setForeground(Color.WHITE);
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(mauNen);
		btnThoat.setForeground(Color.WHITE);
		btnDoiMatKhau = new JButton("Đổi mật khẩu");
		btnDoiMatKhau.setBackground(mauNen);
		btnDoiMatKhau.setForeground(Color.WHITE);
		b3.add(btnDangNhap);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(btnThoat);

		JLabel mota = new JLabel("Nếu bạn muốn đổi mật khẩu thì vui lòng chọn:  ");
		b4.add(mota);
		b4.add(btnDoiMatKhau);

		b.add(b1);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(b2);
		b.add(Box.createRigidArea(new Dimension(0, 1)));
		b.add(bCheck);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(bError);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b4);
		pnCenter.add(b);

		this.add(pnContent);
		btnDangNhap.addActionListener(this);
		btnDoiMatKhau.addActionListener(this);
		btnThoat.addActionListener(this);
		chkQuanLy.addActionListener(this);
		
		txtTaiKhoan.setText("QL001");
		txtMK.setText("123");
		chkQuanLy.setSelected(true);
	}

	public static void main(String[] args) throws Exception {
		new DangNhap_UI().setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDangNhap)) {
			String username = txtTaiKhoan.getText();
			String password = new String(txtMK.getPassword());
			boolean isAdmin = chkQuanLy.isSelected();
			
			try {
				boolean isLoggedIn = dangNhapDAO.dNhap(username, password, isAdmin);
				if (isLoggedIn) {
					if (isAdmin) {
//						JOptionPane.showMessageDialog(this, "thanh cong ql");
						TaiKhoanQuanLy curUser = new TaiKhoanQuanLy(txtTaiKhoan.getText());
							new Quanly_UI(curUser).setVisible(true);
							new DangNhap_UI().setVisible(false);
					} else {
//						JOptionPane.showMessageDialog(this, "thanh cong nv");
						TaiKhoanNhanVien curUser = new TaiKhoanNhanVien(txtTaiKhoan.getText());
						new NhanVien_UI(curUser).setVisible(true);
						new DangNhap_UI().setVisible(false);
					}
					setVisible(false);
				} else {
					lblError.setText("Vui lòng kiểm tra: tên tài khoản phải bắt đầu bằng QT__ hoặc NV__");
					JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc mật khẩu không đúng");
					txtMK.setText("");
					txtMK.requestFocus();
				}
			} catch (RemoteException ex) {
				ex.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (o.equals(btnDoiMatKhau)) {
			try {
				 new DoiMatKhau_UI().setVisible(true);
//				 new DangNhap_UI().dispose();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (o.equals(btnThoat)) {
			dispose();
		}
	}

}
