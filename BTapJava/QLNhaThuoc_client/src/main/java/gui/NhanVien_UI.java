package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dao.*;
import entities.*;

public class NhanVien_UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenu menuTaoHoaDon;
	private JMenu menuXemHoaDon;
	private JMenu menuQuanLyKhachHang;
	private JMenu menuQuanLyNhanVien;
	private JMenu menuQuanLyThuoc;
	private JMenu menuThongKe;
	private JLabel txtMaQL;
	private JLabel txtTenQL;
	private JButton btnTaoHoaDon;
	private JButton btnXemHoaDon;
	private JButton btnQuanLyNhanVien;
	private JButton btnDoanhThu;
	private JButton btnXemThongTin;
	private JButton btnDangXuat;
	private JButton btnQuanLyThuoc;
	private QuanLyNhanVien_GUI qlnv;
	private TaiKhoanNhanVienDAO tk_dao;
	private NhanVienDAO nv_dao;
	private TaiKhoanQuanLy currentuser;
	private XemThongTinNV_GUI info;
	private QuanLyThuoc_GUI qlt;
	private QuanLyBanHang_GUI thd;
	private QuanLyKhachHang_GUI qlkh;
	private ThongKe_GUI tk;
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JButton btnQuanLyKH;
	public NhanVien_UI(TaiKhoanNhanVien curUser) throws Exception {
		try {
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2005);
			tk_dao = (TaiKhoanNhanVienDAO) registry.lookup("TaiKhoanNhanVienDAO");
			nv_dao = (NhanVienDAO) registry.lookup("NhanVienDAO");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Không thể kết nối đến máy chủ RMI");
		}
//		tk_dao = new TaiKhoanNhanVien_DAO();
//		nv_dao = new NhanVien_DAO();
		setTitle("GIAO DIỆN CHO NHÂN VIÊN");
	    this.setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	    
//	    try {
//			ConnectDB.getInstance().connect();
//
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		info = new XemThongTinNV_GUI(curUser);
//		qlt = new QuanLyThuoc_GUI();
		qlkh = new QuanLyKhachHang_GUI();
		thd = new QuanLyBanHang_GUI(curUser);
		tk = new ThongKe_GUI();

		cardPanel.add(info, "XemThongTinUI");
		cardPanel.add(thd, "TaoHoaDon");
		cardPanel.add(qlkh, "QuanLyKhachHangUI");
		cardPanel.add(tk, "ThongKeUI");

		add(cardPanel, BorderLayout.CENTER);
//		tk_dao = new TaiKhoanNhanVien_DAO();
//		nv_dao = new NhanVien_DAO();
	    
	    createTit(curUser);
	    createMenu();
		btnXemThongTin.addActionListener(this);
		btnTaoHoaDon.addActionListener(this);
		btnDoanhThu.addActionListener(this);
		btnQuanLyKH.addActionListener(this);
		btnDangXuat.addActionListener(this);
	}
	public void createTit(TaiKhoanNhanVien currentuser) {
	    JPanel bTieuDe = new JPanel();
	    bTieuDe.setLayout(new BoxLayout(bTieuDe, BoxLayout.X_AXIS));
	    bTieuDe.setBackground(Color.WHITE);

	    JLabel lbLogo = new JLabel(createResizedIcon("icons/logo.png", 100, 80));
	    JLabel lbTieuDe = new JLabel("Quản lý bán thuốc An Nhiên");
	    lbTieuDe.setFont(new Font("Arial", Font.BOLD, 30));
	    lbTieuDe.setForeground(Color.RED);
	    
	    JLabel lbMaQL = new JLabel("Mã Nhân viên: ");txtMaQL = new JLabel(currentuser.getTaiKhoan());
//	    JLabel lbTen = new JLabel("Tên Nhân viên: ");txtTenQL = new JLabel(nv_dao.getNhanVienTheoMaNV(currentuser.getTaiKhoan())
//	    		.getTenNV());

	    Box bIF = Box.createVerticalBox();
	    Box b1, b2;
	    b1 = Box.createHorizontalBox();
	    b2 = Box.createHorizontalBox();
	    b1.add(lbMaQL);b1.add(txtMaQL);
//	    b2.add(lbTen);b2.add(txtTenQL);
	    bIF.add(b1);
	    bIF.add(b2);

	    bTieuDe.add(Box.createHorizontalStrut(44)); // Để tạo khoảng cách giữa logo và tiêu đề
	    bTieuDe.add(lbLogo);
	    bTieuDe.add(Box.createHorizontalGlue()); // Để căn phải
	    bTieuDe.add(lbTieuDe);
	    bTieuDe.add(Box.createHorizontalGlue()); // Để căn giữa
	    bTieuDe.add(bIF);
	    bTieuDe.add(Box.createHorizontalStrut(10)); // Để tạo khoảng cách giữa tiêu đề và thông tin

//	    lbMaQL.setPreferredSize(lbTen.getPreferredSize());
	    
	    add(bTieuDe, BorderLayout.NORTH);
	}


	public void createMenu() {
		JPanel pMenu = new JPanel();
		pMenu.setLayout(new GridLayout(8,1,0,3));
		pMenu.setBorder(BorderFactory.createTitledBorder("Chọn chức năng"));
		pMenu.setBackground(Color.WHITE);
		btnTaoHoaDon = createButton("Tạo Hóa Đơn", "icons/bill.png");
		btnQuanLyKH = createButton("Quản lý khách hàng", "icons/customer.png");
		btnDoanhThu = createButton("Thống Kê", "icons/analysis.png");
		btnXemThongTin = createButton("Thông tin", "icons/admin.png");
		btnDangXuat = createButton("Đăng xuất", "icons/logout.png");
		pMenu.add(btnTaoHoaDon);
		pMenu.add(btnQuanLyKH);
		pMenu.add(btnDoanhThu);
		pMenu.add(btnXemThongTin);
		pMenu.add(Box.createHorizontalBox());
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
		if (o.equals(btnDangXuat)) {
			this.dispose();;
		}
		if (o.equals(btnDoanhThu)) {
			cardLayout.show(cardPanel, "ThongKeUI");
		}
		if (o.equals(btnQuanLyKH)) {
			cardLayout.show(cardPanel, "QuanLyKhachHangUI");
		}
		if(o.equals(btnDangXuat)) {
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
