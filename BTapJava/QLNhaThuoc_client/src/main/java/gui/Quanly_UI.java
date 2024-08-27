package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Closeable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.DangNhapDAO;
import dao.NhanVienDAO;
import dao.QuanLyDAO;
import dao.TaiKhoanNhanVienDAO;

public class Quanly_UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel txtMaQL;
	private JLabel txtTenQL;
	private JButton btnTaoHoaDon;
	private JButton btnXemHoaDon;
	private JButton btnQuanLyNhanVien;
	private JButton btnDoanhThu;
	private JButton btnXemThongTin;
	private JButton btnDangXuat;
	private JButton btnQuanLyThuoc;
	
	private NhanVienDAO nv_dao;
	private TaiKhoanNhanVienDAO tk_dao;
	private QuanLyDAO ql_dao;
	private XemThongTin_GUI info;
	private entities.QuanLy ql;
	private QuanLyNhanVien_GUI qlnv;
	private QuanLyThuoc_GUI qlt;
	private QuanLyKhachHang_GUI qlkh;
//	private QuanLyBanHang_GUI thd;
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JButton btnQuanLyKH;
	private entities.TaiKhoanQuanLy curr;
	private ThongKe_GUI tk;
	private QuanLyHoaDon_GUI qlhd;
	private JButton btnQuanLyHoaDon;
		
	public Quanly_UI(entities.TaiKhoanQuanLy currentuser) throws Exception {
		try {
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2005);
			ql_dao = (QuanLyDAO) registry.lookup("QuanLyDAO");
			nv_dao = (NhanVienDAO) registry.lookup("NhanVienDAO");
			tk_dao = (TaiKhoanNhanVienDAO) registry.lookup("TaiKhoanNhanVienDAO");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Không thể kết nối đến máy chủ RMI");
		}
		
		setTitle("GIAO DIỆN CHO QUẢN LÝ");
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
//		setResizable(false);
		curr = currentuser;
//		try {
//			ConnectDB.getInstance().connect();
//
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		qlnv = new QuanLyNhanVien_GUI();
		info = new XemThongTin_GUI(currentuser);
		qlt = new QuanLyThuoc_GUI();
		qlkh = new QuanLyKhachHang_GUI();
		tk = new ThongKe_GUI();
		qlhd = new QuanLyHoaDon_GUI();

		cardPanel.add(info, "XemThongTinUI");
		cardPanel.add(qlnv, "QuanLyNhanVienUI");
		cardPanel.add(qlt, "QuanLyThuocUI");
		cardPanel.add(qlkh, "QuanLyKhachHangUI");
		cardPanel.add(tk, "ThongKeUI");
		cardPanel.add(qlhd, "QuanLyHoaDon");

		add(cardPanel, BorderLayout.CENTER);
//		ql_dao = new QuanLy_DAO();
//
		createTit(currentuser);
		createMenu();
		btnQuanLyNhanVien.addActionListener(this);
		btnXemThongTin.addActionListener(this);
		btnQuanLyThuoc.addActionListener(this);
		btnQuanLyKH.addActionListener(this);
		btnDoanhThu.addActionListener(this);
		btnQuanLyHoaDon.addActionListener(this);
		btnDangXuat.addActionListener(this);
	}

	public void createTit(entities.TaiKhoanQuanLy currentuser) throws RemoteException {
		JPanel bTieuDe = new JPanel();
		bTieuDe.setLayout(new BoxLayout(bTieuDe, BoxLayout.X_AXIS));
		bTieuDe.setBackground(Color.WHITE);

		JLabel lbLogo = new JLabel(createResizedIcon("icons/logo.png", 100, 80));
		JLabel lbTieuDe = new JLabel("Quản lý bán thuốc An Nhiên");
		lbTieuDe.setFont(new Font("Arial", Font.BOLD, 30));
		lbTieuDe.setForeground(Color.RED);

		ql = ql_dao.getQuanLyTheoMaQL(currentuser.getTaiKhoan());
		System.out.println(ql);
		JLabel lbMaQL = new JLabel("Mã quản lý: ");
		txtMaQL = new JLabel(ql.getMaQL());
		JLabel lbTen = new JLabel("Tên quản lý: ");
		txtTenQL = new JLabel(ql.getTenQL());

		Box bIF = Box.createVerticalBox();
		Box b1, b2;
		b1 = Box.createHorizontalBox();
		b2 = Box.createHorizontalBox();
		b1.add(lbMaQL);
		b1.add(txtMaQL);
		b2.add(lbTen);
		b2.add(txtTenQL);
		bIF.add(b1);
		bIF.add(b2);

		bTieuDe.add(Box.createHorizontalStrut(44)); // Để tạo khoảng cách giữa logo và tiêu đề
		bTieuDe.add(lbLogo);
		bTieuDe.add(Box.createHorizontalGlue()); // Để căn phải
		bTieuDe.add(lbTieuDe);
		bTieuDe.add(Box.createHorizontalGlue()); // Để căn giữa
		bTieuDe.add(bIF);
		bTieuDe.add(Box.createHorizontalStrut(10)); // Để tạo khoảng cách giữa tiêu đề và thông tin

		lbMaQL.setPreferredSize(lbTen.getPreferredSize());

		add(bTieuDe, BorderLayout.NORTH);
	}

	public void createMenu() {
		JPanel pMenu = new JPanel();
		pMenu.setLayout(new GridLayout(9, 1, 0, 3));
		pMenu.setBorder(BorderFactory.createTitledBorder("Chọn chức năng"));
		pMenu.setBackground(Color.WHITE);
		btnQuanLyKH = createButton("Quản lý khách hàng", "icons/customer.png");
		btnQuanLyNhanVien = createButton("Quản Lý Nhân Viên", "icons/Reception.png");
		btnQuanLyThuoc = createButton("Quản Lý Thuốc", "icons/update.png");
		btnDoanhThu = createButton("Thống Kê", "icons/analysis.png");
		btnXemThongTin = createButton("Thông tin", "icons/admin.png");
		btnDangXuat = createButton("Đăng xuất", "icons/logout.png");
		btnQuanLyHoaDon = createButton("Quản lý hóa đơn", "icons/bills.png");
		pMenu.add(btnQuanLyKH);
		pMenu.add(btnQuanLyNhanVien);
		pMenu.add(btnQuanLyThuoc);
		pMenu.add(btnQuanLyHoaDon);
		pMenu.add(btnDoanhThu);
		pMenu.add(btnXemThongTin);
		pMenu.add(Box.createHorizontalBox());
		pMenu.add(Box.createHorizontalBox());
		pMenu.add(btnDangXuat);
		add(pMenu, BorderLayout.WEST);
	}

	public ImageIcon createResizedIcon(String iconPath, int width, int height) {
		ImageIcon originalIcon = new ImageIcon(iconPath);
		Image image = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	private JButton createButton(String text, String iconPath) {
		JButton button = new JButton(text);
		button.setHorizontalAlignment(SwingConstants.LEFT);
//        button.setOpaque(false); // Vô hiệu hóa màu nền của nút
//        button.setContentAreaFilled(false); // Vô hiệu hóa vùng chứa nội dung nền của nút
//        button.setBorderPainted(false); // Vô hiệu hóa viền của nút
		button.setFocusPainted(false);
		ImageIcon buttonIcon = new ImageIcon(iconPath);
		Image image = buttonIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);
		button.setIcon(icon);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnQuanLyNhanVien)) {
			cardLayout.show(cardPanel, "QuanLyNhanVienUI");
		}
		if (o.equals(btnXemThongTin)) {
			cardLayout.show(cardPanel, "XemThongTinUI");
		}
		if (o.equals(btnQuanLyThuoc)) {
			cardLayout.show(cardPanel, "QuanLyThuocUI");
		}
		if (o.equals(btnTaoHoaDon)) {
			cardLayout.show(cardPanel, "TaoHoaDon");
		}
		if (o.equals(btnQuanLyKH)) {
			cardLayout.show(cardPanel, "QuanLyKhachHangUI");
		}
		if (o.equals(btnDangXuat)) {
			this.dispose();
			;
		}
		if (o.equals(btnDoanhThu)) {
			cardLayout.show(cardPanel, "ThongKeUI");
		}
		if (o.equals(btnQuanLyHoaDon)) {
			cardLayout.show(cardPanel, "QuanLyHoaDon");
		}
		if (o.equals(btnDangXuat)) {
			this.setVisible(false);
			try {
				new DangNhap_UI().setVisible(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
