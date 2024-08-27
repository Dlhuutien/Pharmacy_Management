package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import dao.*;
import entities.*;

public class QuanLyThuoc_GUI extends JPanel implements ActionListener, MouseListener, DocumentListener, ItemListener {
	private static final long serialVersionUID = 1L;
	private JTextField txtChucNang;
	private JTextField txtTenTP;
	private JTextField txtMaTP;
	private DefaultTableModel modelThuoc;
	private JTable tableThuoc;
	private JTextField txtMaNCC;
	private JTextField txtTenNCC;
	private JTextField txtEmail;
	private JTextField txtSoDienThoai;
	private JTextField txtDiaChi;
	private JTextField txtSoLuongNhap;
	private JTextField txtDonGiaBan;
	private JTextField txtDonGiaNhap;
	private JTextField txtDangBaoChe;
	private JDateChooser txtNgaySanXuat;
	private JTextField txtNoiSanXuat;
	private JComboBox<String> txtNhaCungCap;
	private JTextField txtKeDon;
	private JComboBox<String> txtTenTH;
	private JTextField txtMaTH;
	private JDateChooser txtNgayHetHan;
	private JCheckBox chkKeDon;
	private JTextField txtThanhPhan;
	private JButton btnTaiHinh;
	private JButton btnNhapThuoc;
	private JButton btnXoaThuoc;
	private JComboBox cbSapXep;
	private JButton btnTangDan;
	private JButton btnGiamDan;
	private JTextField txtTimKiemTheoMa;
	private JComboBox cbLocThuoc;
	private JButton btnSua;
	private ThuocDAO thuoc_dao;
	private ChiTietThuocDAO ct_dao;
	private ThanhPhanDAO tp_dao;
	private NhaCungCapDAO ncc_dao;
	private String imagePath = "Anh/default.png";
	private ImageIcon selectedImageIcon;
	private JLabel imgLabel;
	private JButton btnThemNhaCC;
	private JButton btnXemChiTiet;
	private JButton btnSuaNCC;
	private int ThuocCounter;
	private int TPCounter;
	private int DTCounter;
	private JButton btnRefresh;

	Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2005);
	public QuanLyThuoc_GUI() throws Exception {
		try {
			ct_dao = (ChiTietThuocDAO) registry.lookup("ChiTietThuocDAO");
			tp_dao = (ThanhPhanDAO) registry.lookup("ThanhPhanDAO");
			ncc_dao = (NhaCungCapDAO) registry.lookup("NhaCungCapDAO");
//			thuoc_dao = (ThuocDAO) registry.lookup("ThuocDAO");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Không thể kết nối đến máy chủ RMI");
		}
//		ct_dao = new ChiTietThuoc_DAO();
//		tp_dao = new ThanhPhan_DAO();
//		ncc_dao = new NhaCungCap_DAO();
		setLayout(new BorderLayout());
		Box b = Box.createVerticalBox();
		b.add(QuanLyThuoc());
		b.add(QuanLyNhaCungCap());
		b.add(ChucNang());
		b.add(Box.createVerticalStrut(10));
		add(b, BorderLayout.NORTH);
		String[] colHeader = { "Mã thuốc", "Tên thuốc", "Kê đơn", "Nhà cung cấp", "Nơi sản xuất", "Ngày sản xuất",
				"Ngày hết hạn", "Dạng bào chế", "Thành phần", "Đơn giá nhập", "Đơn giá bán", "Số lượng tồn" };
		modelThuoc = new DefaultTableModel(colHeader, 0);
		tableThuoc = new JTable(modelThuoc);
		add(new JScrollPane(tableThuoc));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tableThuoc.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tableThuoc.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tableThuoc.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		tableThuoc.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tableThuoc.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
		tableThuoc.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
		tableThuoc.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);

		add(Capnhat(), BorderLayout.SOUTH);
		docDuLieuDataBaseVaoTable();
		addSK();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelThuoc);
		tableThuoc.setRowSorter(sorter);
		Comparator<String> stringComparator = Comparator.naturalOrder();
		Comparator<Integer> integerComparator = Comparator.naturalOrder();
		Comparator<Date> dateComparator = Comparator.comparing(Date::getTime);
		sorter.setComparator(0, stringComparator); // Mã thuốc
		sorter.setComparator(1, stringComparator); // Tên thuốc
		sorter.setComparator(11, integerComparator); // Số lượng tồn
		btnTangDan.addActionListener(e -> {
			int choice = cbSapXep.getSelectedIndex();
			if (choice == 0) {
				sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
			}
			if (choice == 1) {
				sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
			}
			if (choice == 2) {
				sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(11, SortOrder.ASCENDING)));
			}
		});

		btnGiamDan.addActionListener(e -> {
			int choice = cbSapXep.getSelectedIndex();
			if (choice == 0) {
				sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
			}
			if (choice == 1) {
				sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.DESCENDING)));
			}
			if (choice == 2) {
				sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(11, SortOrder.DESCENDING)));
			}
		});
	}

	public JPanel Capnhat() {
		JPanel panel = new JPanel();

		btnNhapThuoc = new JButton("Nhập thuốc");
		btnXoaThuoc = new JButton("Xóa thuốc");
		btnSua = new JButton("Sửa thông tin thuốc");
		btnTaiHinh = new JButton("Tải hình");
		btnRefresh = new JButton("Refresh");
		btnTaiHinh.addActionListener(this);
		panel.add(btnTaiHinh);
		panel.add(btnNhapThuoc);
		panel.add(btnXoaThuoc);
		panel.add(btnSua);
		panel.add(btnRefresh);

		return panel;
	}

	public JPanel ChucNang() {
		JPanel panel = new JPanel(new BorderLayout());

		JLabel lbSapXep = new JLabel("Sắp xếp theo: ");
		String[] kieu = { "Mã thuốc", "Tên thuốc", "Số lượng tồn" };
		cbSapXep = new JComboBox<>(kieu);
		JLabel lbLoc = new JLabel("Lọc thuốc theo: ");
		String[] loai = { "Tất cả", "Kê đơn", "Không kê đơn", "Hết hạn" };
		cbLocThuoc = new JComboBox<>(loai);
		cbLocThuoc.addItemListener(this);
		btnTangDan = new JButton("Tăng dần");
		btnGiamDan = new JButton("Giảm dần");
		txtTimKiemTheoMa = new JTextField(20);
		JLabel iconLabel = new JLabel(createResizedIcon("icons/search.png", 20, 20));

		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalStrut(20));
		b.add(lbSapXep);
		b.add(cbSapXep);
		b.add(Box.createHorizontalStrut(10));
		b.add(btnTangDan);
		b.add(Box.createHorizontalStrut(10));
		b.add(btnGiamDan);
		b.add(Box.createHorizontalStrut(50));
		b.add(txtTimKiemTheoMa);
		b.add(iconLabel);
		b.add(Box.createHorizontalStrut(50));
		b.add(lbLoc);
		b.add(cbLocThuoc);
		b.add(Box.createHorizontalStrut(20));
		panel.add(b);

		return panel;
	}

	public JPanel QuanLyThuoc() throws RemoteException, NotBoundException {
		thuoc_dao = (ThuocDAO) registry.lookup("ThuocDAO");
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Thông tin thuốc"));
		imgLabel = new JLabel(createResizedIcon("Anh/default.png", 140, 140));
		imgLabel.setBorder(BorderFactory.createTitledBorder("Ảnh sản phẩm"));
		JLabel lbMaTH = new JLabel("Mã thuốc: ");
		JLabel lbTenTH = new JLabel("Tên thuốc: ");
		JLabel lbNhaCungCap = new JLabel("Nhà cung cấp: ");
		JLabel lbNoiSanXuat = new JLabel("Nơi sản xuất: ");
		JLabel lbNgaySanXuat = new JLabel("Ngày sản xuất: ");
		JLabel lbNgayHetHan = new JLabel("Ngày hết hạn: ");
		JLabel lbDangBaoChe = new JLabel("Dạng bào chế: ");
		JLabel lbThanhPhan = new JLabel("Thành phần:      ");
		JLabel lbDonGiaNhap = new JLabel("Đơn giá nhập: ");
		JLabel lbDonGiaBan = new JLabel("Đơn giá bán: ");
		JLabel lbSoLuongNhap = new JLabel("Số lượng nhập: ");

		txtMaTH = new JTextField(20);
		txtMaTH.setEditable(false);
		txtTenTH = new JComboBox<String>();
		txtTenTH.setEditable(true);
		List<String> danhSachTenThuoc = thuoc_dao.getalltenthuoc();
		for (String tenThuoc : danhSachTenThuoc) {
			txtTenTH.addItem(tenThuoc);
		}
		chkKeDon = new JCheckBox("Thuốc kê đơn");
		txtNhaCungCap = new JComboBox<String>();
		txtNhaCungCap.setEditable(true);
		List<String> danhSachTenNhaCC = ncc_dao.getallma();
		for (String ten : danhSachTenNhaCC) {
			txtNhaCungCap.addItem(ten);
		}
		txtNoiSanXuat = new JTextField(20);
		txtNgaySanXuat = new JDateChooser();
		txtNgayHetHan = new JDateChooser();
		txtDangBaoChe = new JTextField(100);
		txtThanhPhan = new JTextField(50);
		txtDonGiaNhap = new JTextField(20);
		txtDonGiaBan = new JTextField(20);
		txtSoLuongNhap = new JTextField(20);

		Box b = Box.createHorizontalBox();
		Box br = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		b1.add(lbMaTH);
		b1.add(txtMaTH);

		Box b2 = Box.createHorizontalBox();
		b2.add(lbNhaCungCap);
		b2.add(txtNhaCungCap);

		Box b3 = Box.createHorizontalBox();
		b3.add(lbNgaySanXuat);
		b3.add(txtNgaySanXuat);

		Box b4 = Box.createHorizontalBox();
		b4.add(lbDangBaoChe);
		b4.add(txtDangBaoChe);

		Box b5 = Box.createHorizontalBox();
		b5.add(lbDonGiaNhap);
		b5.add(txtDonGiaNhap);
		b5.add(Box.createHorizontalStrut(20));
		b5.add(lbDonGiaBan);
		b5.add(txtDonGiaBan);

		br.add(b1);
		br.add(b2);
		br.add(b3);
		br.add(b4);
		br.add(b5);

		Box br0 = Box.createVerticalBox();
		Box br1 = Box.createHorizontalBox();
		br1.add(lbTenTH);
		br1.add(txtTenTH);
		br1.add(Box.createHorizontalStrut(20));
		br1.add(chkKeDon);

		Box br2 = Box.createHorizontalBox();
		br2.add(lbNoiSanXuat);
		br2.add(txtNoiSanXuat);

		Box br3 = Box.createHorizontalBox();
		br3.add(lbNgayHetHan);
		br3.add(txtNgayHetHan);

		Box br4 = Box.createHorizontalBox();
		br4.add(lbThanhPhan);
		br4.add(txtThanhPhan);

		Box br5 = Box.createHorizontalBox();
		br5.add(lbSoLuongNhap);
		br5.add(txtSoLuongNhap);

		br0.add(br1);
		br0.add(br2);
		br0.add(br3);
		br0.add(br4);
		br0.add(br5);

		b.add(Box.createHorizontalStrut(20));
		b.add(imgLabel);
		b.add(Box.createHorizontalStrut(20));
		b.add(br);
		b.add(Box.createHorizontalStrut(20));
		b.add(br0);

		lbMaTH.setPreferredSize(lbNgaySanXuat.getPreferredSize());
		lbNhaCungCap.setPreferredSize(lbNgaySanXuat.getPreferredSize());
		lbNgaySanXuat.setPreferredSize(lbNgaySanXuat.getPreferredSize());
		lbDangBaoChe.setPreferredSize(lbNgaySanXuat.getPreferredSize());
		lbDonGiaNhap.setPreferredSize(lbNgaySanXuat.getPreferredSize());
		lbDonGiaBan.setPreferredSize(lbNgaySanXuat.getPreferredSize());

		lbTenTH.setPreferredSize(lbSoLuongNhap.getPreferredSize());
		lbNoiSanXuat.setPreferredSize(lbSoLuongNhap.getPreferredSize());
		lbNgayHetHan.setPreferredSize(lbSoLuongNhap.getPreferredSize());
//		lbThanhPhan.setPreferredSize(lbSoLuongNhap.getPreferredSize());

		panel.add(b);
		return panel;
	}

	public ImageIcon createResizedIcon(String iconPath, int width, int height) {
		ImageIcon originalIcon = new ImageIcon(iconPath);
		Image image = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	public JPanel QuanLyThanhPhan() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel lbMaTP = new JLabel("Mã thành phần: ");
		JLabel lbTenTP = new JLabel("Tên thành phần: ");
		JLabel lbChucNang = new JLabel("Mô tả chức năng: ");

		txtMaTP = new JTextField(20);
		txtTenTP = new JTextField(100);
		txtChucNang = new JTextField(200);

		Box b0, b, b1, b2, b3, b4;
		b0 = Box.createVerticalBox();
		b = Box.createVerticalBox();
		b.setBorder(BorderFactory.createTitledBorder("Thành phần: "));
		b1 = Box.createHorizontalBox();
		b1.add(lbMaTP);
		b1.add(txtMaTP);

		b2 = Box.createHorizontalBox();
		b2.add(lbTenTP);
		b2.add(txtTenTP);

		b3 = Box.createHorizontalBox();
		b3.add(lbChucNang);
		b3.add(txtChucNang);

		b.add(b1);
		b.add(b2);
		b.add(b3);

		b0.add(b);
//		add(b0, BorderLayout.NORTH);

		lbMaTP.setPreferredSize(lbChucNang.getPreferredSize());
		lbTenTP.setPreferredSize(lbChucNang.getPreferredSize());
		panel.add(b0);

		return panel;
	}

	public JPanel QuanLyNhaCungCap() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel lbMaNCC = new JLabel("Mã nhà cung cấp: ");
		JLabel lbTenNCC = new JLabel("Tên nhà cung cấp: ");
		JLabel lbEmail = new JLabel("Email: ");
		JLabel lbSoDienThoai = new JLabel("Số điện thoại: ");
		JLabel lbDiaChi = new JLabel("Địa chỉ: ");

		txtMaNCC = new JTextField(20);
		txtTenNCC = new JTextField(20);
		txtEmail = new JTextField(100);
		txtSoDienThoai = new JTextField(20);
		txtDiaChi = new JTextField(100);

		Box b0, b, b1, b2, b3, b4, b5, b6;
		b0 = Box.createHorizontalBox();
		b = Box.createVerticalBox();
		b.setBorder(BorderFactory.createTitledBorder("Nhà cung cấp: "));
		b1 = Box.createHorizontalBox();
		b1.add(lbMaNCC);
		b1.add(txtMaNCC);

		b2 = Box.createHorizontalBox();
		b2.add(lbTenNCC);
		b2.add(txtTenNCC);

		b3 = Box.createHorizontalBox();
		b3.add(lbEmail);
		b3.add(txtEmail);

		b4 = Box.createHorizontalBox();
		b4.add(lbSoDienThoai);
		b4.add(txtSoDienThoai);

		b5 = Box.createHorizontalBox();
		b5.add(lbDiaChi);
		b5.add(txtDiaChi);

		b.add(b1);
		b.add(b2);
		b.add(b3);
		b.add(b4);
		b.add(b5);

		b0.add(b);
		b0.add(Box.createHorizontalStrut(10));

		Box b7 = Box.createVerticalBox();
		btnThemNhaCC = new JButton("Thêm nhà CC");
		btnXemChiTiet = new JButton("  Xem chi tiết  ");
		btnSuaNCC = new JButton("        Sửa         ");
		b7.add(btnThemNhaCC);
		b7.add(Box.createVerticalStrut(10));
		b7.add(btnXemChiTiet);
		b7.add(Box.createVerticalStrut(10));
		b7.add(btnSuaNCC);

		b0.add(b7);
		b0.add(Box.createHorizontalStrut(10));

		lbMaNCC.setPreferredSize(lbTenNCC.getPreferredSize());
		lbEmail.setPreferredSize(lbTenNCC.getPreferredSize());
		lbSoDienThoai.setPreferredSize(lbTenNCC.getPreferredSize());
		lbDiaChi.setPreferredSize(lbTenNCC.getPreferredSize());
		panel.add(b0);

		return panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tableThuoc.getSelectedRow();
		if (row >= 0) {
			// Lấy thông tin từ hàng đã chọn và hiển thị lên các component
			txtMaTH.setText(modelThuoc.getValueAt(row, 0).toString());
			txtTenTH.setSelectedItem(modelThuoc.getValueAt(row, 1).toString());
			chkKeDon.setSelected(modelThuoc.getValueAt(row, 2).toString().equals("Có") ? true : false);
			txtNhaCungCap.setSelectedItem(modelThuoc.getValueAt(row, 3).toString());
			txtNoiSanXuat.setText(modelThuoc.getValueAt(row, 4).toString());
			String ngaySX = modelThuoc.getValueAt(row, 5).toString();
			LocalDate ngaysx = LocalDate.parse(ngaySX, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			txtNgaySanXuat.setDate(java.sql.Date.valueOf(ngaysx));
			String ngayHH = modelThuoc.getValueAt(row, 6).toString();
			LocalDate ngayhh = LocalDate.parse(ngayHH, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			txtNgayHetHan.setDate(java.sql.Date.valueOf(ngayhh));
			txtDangBaoChe.setText(modelThuoc.getValueAt(row, 7).toString());
			txtThanhPhan.setText(modelThuoc.getValueAt(row, 8).toString());
			txtDonGiaNhap.setText(modelThuoc.getValueAt(row, 9).toString());
			txtDonGiaBan.setText(modelThuoc.getValueAt(row, 10).toString());
			txtSoLuongNhap.setText(modelThuoc.getValueAt(row, 11).toString());
			try {
				if (thuoc_dao.getanhthuoc(modelThuoc.getValueAt(row, 0).toString()) != null) {
					selectedImageIcon = new ImageIcon(thuoc_dao.getanhthuoc(modelThuoc.getValueAt(row, 0).toString()));
					Image image = selectedImageIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
					imgLabel.setIcon(new ImageIcon(image));
					revalidate();
					repaint();
				} else {
					selectedImageIcon = new ImageIcon("Anh/default.png");
					Image image = selectedImageIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
					imgLabel.setIcon(new ImageIcon(image));
					revalidate();
					repaint();
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnRefresh)) {
			modelThuoc.setRowCount(0);
			try {
				docDuLieuDataBaseVaoTable();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (o.equals(btnTaiHinh)) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int result = fileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				imagePath = fileChooser.getSelectedFile().getAbsolutePath();
				selectedImageIcon = new ImageIcon(imagePath);
				Image image = selectedImageIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);

				// Hiển thị hình ảnh trên imgLabel
				imgLabel.setIcon(new ImageIcon(image));

				// Yêu cầu vẽ lại giao diện để hiển thị hình ảnh mới
				revalidate();
				repaint();
			}
		}
		if (o.equals(btnXemChiTiet)) {
			try {
				new QuanLyNhaCungCap_GUI(this).setVisible(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (o.equals(btnThemNhaCC)) {
			if (checkValid()) {
				try {
					ncc_dao.create(taoNhaCC());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txtNhaCungCap.removeAllItems();
				List<String> danhSachTenNhaCC = null;
				try {
					danhSachTenNhaCC = ncc_dao.getallma();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (String ten : danhSachTenNhaCC) {
					txtNhaCungCap.addItem(ten);
				}
				JOptionPane.showMessageDialog(this, "Thêm thành công 1 nhà cung cấp");
			}
		}
		if (o.equals(btnSuaNCC)) {
			if (checkValid()) {
				try {
					suaThongTinNCC();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					new QuanLyNhaCungCap_GUI(this).dispose();
				} catch (Exception e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				try {
					new QuanLyNhaCungCap_GUI(this).setVisible(true);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				txtNhaCungCap.removeAllItems();
				List<String> danhSachTenNhaCC = null;
				try {
					danhSachTenNhaCC = ncc_dao.getallma();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (String ten : danhSachTenNhaCC) {
					txtNhaCungCap.addItem(ten);
				}
				JOptionPane.showMessageDialog(this, "Sửa thành công 1 nhà cung cấp");
			}

		}
		if (o.equals(btnNhapThuoc)) {

			if (checkValidInput()) {
				Thuoc th = null;
				try {
					th = taoThuoc();
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				try {
					thuoc_dao.addThuoc(taoThuoc());
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				for (String tp : txtThanhPhan.getText().replaceAll("\\s", "").split(",")) {
					try {
						if (tp_dao.exists(tp)) {
							String tenTP = tp;
							String cn = null;
							ct_dao.addChiTietThuoc(new ChiTietThuoc(generateDTID(), th,
									new ThanhPhan(tp_dao.getMaTPByTenTP(tp), tenTP, cn)));
						} else {
							String maTP = generateThanhPhanID();
							String tenTP = tp;
							String cn = null;
							tp_dao.create(new ThanhPhan(maTP, tenTP, cn));
							ct_dao.addChiTietThuoc(new ChiTietThuoc(generateDTID(), th, new ThanhPhan(maTP, tenTP, cn)));
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(this, "Thêm thành công thuốc");
				modelThuoc.setRowCount(0);
				try {
					docDuLieuDataBaseVaoTable();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if (o.equals(btnXoaThuoc)) {
			int row = tableThuoc.getSelectedRow();
			String ma = modelThuoc.getValueAt(row, 0).toString();
			int yes = JOptionPane.YES_OPTION;
			if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dòng này") == yes) {
				try {
					ct_dao.deleteChiTietThuocByMaThuoc(ma);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					thuoc_dao.deleteThuoc(ma);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				modelThuoc.removeRow(row);
				JOptionPane.showMessageDialog(this, "Xóa thành công!");
			}
		}
		if(o.equals(btnSua)) {
			int selectedRow = tableThuoc.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa.", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(checkValidInput()) {
				String maThuoc = txtMaTH.getText();
				String tenThuoc = txtTenTH.getSelectedItem().toString();
				String hinhThuoc = imagePath; // assuming imagePath is a class variable containing the image path
				boolean keDon = chkKeDon.isSelected();
				NhaCungCap nhaCungCap = new NhaCungCap(getSubstringInParentheses(txtNhaCungCap.getSelectedItem().toString()));
				String noiSanXuat = txtNoiSanXuat.getText();
				LocalDate ngaySanXuat = txtNgaySanXuat.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate ngayHetHan = txtNgayHetHan.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String dangBaoChe = txtDangBaoChe.getText();
				double donGiaNhap = Double.parseDouble(txtDonGiaNhap.getText());
				double donGiaBan = Double.parseDouble(txtDonGiaBan.getText());
				int soLuongTon = Integer.parseInt(txtSoLuongNhap.getText());

				// Create and return a new Thuoc instance
				Thuoc th = new Thuoc(maThuoc, tenThuoc, hinhThuoc, keDon, nhaCungCap, noiSanXuat, ngaySanXuat, ngayHetHan,
						dangBaoChe, donGiaNhap, donGiaBan, soLuongTon);
				
				boolean success = false;
				try {
					success = thuoc_dao.updateThuoc(th);
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				if (success) {
					// Update the table model
					modelThuoc.setRowCount(0);
					try {
						docDuLieuDataBaseVaoTable();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					JOptionPane.showMessageDialog(this, "Thuốc đã được cập nhật thành công.", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Cập nhật thuốc không thành công.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			String selectedValue = (String) cbLocThuoc.getSelectedItem();

			if ("Hết hạn".equals(selectedValue)) {
				modelThuoc.setRowCount(0);
				try {
					loadExpiredMedicines();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if ("Kê đơn".equals(selectedValue)) {
				// Handle logic for "Kê đơn" selection
				modelThuoc.setRowCount(0);
				try {
					loadMedicinesKeDon();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if ("Không kê đơn".equalsIgnoreCase(selectedValue)) {
				modelThuoc.setRowCount(0);
				try {
					loadMedicinesKoKeDon();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				modelThuoc.setRowCount(0);
				try {
					docDuLieuDataBaseVaoTable();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private void loadExpiredMedicines() throws RemoteException {
		ArrayList<Thuoc> list = thuoc_dao.getallthuocExpired();
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (Thuoc thuoc : list) {
			String str = "";
			List<ChiTietThuoc> listTP = ct_dao.getalltptheomathuoc(thuoc.getMaThuoc());
			if (listTP.size() == 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
			} else if (listTP.size() > 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
				for (int i = 1; i < listTP.size(); i++) {
					str += ", " + tp_dao.gettptheoma(listTP.get(i).getThanhphan().getMaTP());
				}
				modelThuoc.addRow(new Object[] { thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						thuoc.isKeDon() ? "Có" : "Không", ncc_dao.gettheoma(thuoc.getNhaCungCap().getMaNCC()),
						thuoc.getNoiSanXuat(), thuoc.getNgaySanXuat().format(dateFormatter),
						thuoc.getNgayHetHan().format(dateFormatter), thuoc.getDangBaoChe(), str,
						currencyFormatter.format(thuoc.getDonGiaNhap()), currencyFormatter.format(thuoc.getDonGiaBan()),
						thuoc.getSoLuongTon() });
			} else {
				System.out.println("EERRROR!");
			}
		}
	}

	private void loadMedicinesKeDon() throws RemoteException {
		ArrayList<Thuoc> list = thuoc_dao.getThuocKeDonList();
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (Thuoc thuoc : list) {
			String str = "";
			List<ChiTietThuoc> listTP = ct_dao.getalltptheomathuoc(thuoc.getMaThuoc());
			if (listTP.size() == 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
			} else if (listTP.size() > 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
				for (int i = 1; i < listTP.size(); i++) {
					str += ", " + tp_dao.gettptheoma(listTP.get(i).getThanhphan().getMaTP());
				}
				modelThuoc.addRow(new Object[] { thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						thuoc.isKeDon() ? "Có" : "Không", ncc_dao.gettheoma(thuoc.getNhaCungCap().getMaNCC()),
						thuoc.getNoiSanXuat(), thuoc.getNgaySanXuat().format(dateFormatter),
						thuoc.getNgayHetHan().format(dateFormatter), thuoc.getDangBaoChe(), str,
						currencyFormatter.format(thuoc.getDonGiaNhap()), currencyFormatter.format(thuoc.getDonGiaBan()),
						thuoc.getSoLuongTon() });
			} else {
				System.out.println("EERRROR!");
			}
		}
	}

	private void loadMedicinesKoKeDon() throws RemoteException {
		ArrayList<Thuoc> list = thuoc_dao.getThuocKoKeDonList();
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (Thuoc thuoc : list) {
			String str = "";
			List<ChiTietThuoc> listTP = ct_dao.getalltptheomathuoc(thuoc.getMaThuoc());
			if (listTP.size() == 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
			} else if (listTP.size() > 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
				for (int i = 1; i < listTP.size(); i++) {
					str += ", " + tp_dao.gettptheoma(listTP.get(i).getThanhphan().getMaTP());
				}
				modelThuoc.addRow(new Object[] { thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						thuoc.isKeDon() ? "Có" : "Không", ncc_dao.gettheoma(thuoc.getNhaCungCap().getMaNCC()),
						thuoc.getNoiSanXuat(), thuoc.getNgaySanXuat().format(dateFormatter),
						thuoc.getNgayHetHan().format(dateFormatter), thuoc.getDangBaoChe(), str,
						currencyFormatter.format(thuoc.getDonGiaNhap()), currencyFormatter.format(thuoc.getDonGiaBan()),
						thuoc.getSoLuongTon() });
			} else {
				System.out.println("EERRROR!");
			}
		}
	}

	private String generateDTID() throws RemoteException {
		// Lấy danh sách tất cả các mã chi tiết thuốc
		ArrayList<String> allIDs = ct_dao.getAllChiTietThuocIDs();

		// Kiểm tra xem mã mới đã tồn tại trong danh sách hay chưa
		String newID;
		do {
			DTCounter++;
			newID = "DT" + String.format("%04d", DTCounter);
		} while (allIDs.contains(newID));

		return newID;
	}

	private void suaThongTinNCC() throws RemoteException {
		if (txtMaNCC.getText().length() != 0) {
			String maNCC = txtMaNCC.getText();
			String tenNCC = txtTenNCC.getText();
			String email = txtEmail.getText();
			String soDienThoai = txtSoDienThoai.getText();
			String diaChi = txtDiaChi.getText();

			NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, email, soDienThoai, diaChi);

			// Gọi hàm cập nhật thông tin nhà cung cấp trong lớp DAO
			boolean capNhatThanhCong = ncc_dao.capNhat(ncc);
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa");
		}
	}

	public void updateNCCInfo(String maNCC, String tenNCC, String email, String soDienThoai, String diaChi) {
		// Update text fields with the received information
		txtMaNCC.setText(maNCC);
		txtTenNCC.setText(tenNCC);
		txtEmail.setText(email);
		txtSoDienThoai.setText(soDienThoai);
		txtDiaChi.setText(diaChi);
	}

	public void passNCCInfo(String maNCC, String tenNCC, String email, String soDienThoai, String diaChi) {
		// Update text fields with the received information
		txtMaNCC.setText(maNCC);
		txtTenNCC.setText(tenNCC);
		txtEmail.setText(email);
		txtSoDienThoai.setText(soDienThoai);
		txtDiaChi.setText(diaChi);
	}

	public void docDuLieuDataBaseVaoTable() throws RemoteException{
		List<Thuoc> list = thuoc_dao.getallthuoc();
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (Thuoc thuoc : list) {
			String str = "";
			List<ChiTietThuoc> listTP = ct_dao.getalltptheomathuoc(thuoc.getMaThuoc());
			if (listTP.size() == 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
			} else if (listTP.size() > 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
				for (int i = 1; i < listTP.size(); i++) {
					str += ", " + tp_dao.gettptheoma(listTP.get(i).getThanhphan().getMaTP());
				}
				modelThuoc.addRow(new Object[] { thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						thuoc.isKeDon() ? "Có" : "Không", ncc_dao.gettheoma(thuoc.getNhaCungCap().getMaNCC()),
						thuoc.getNoiSanXuat(), thuoc.getNgaySanXuat().format(dateFormatter),
						thuoc.getNgayHetHan().format(dateFormatter), thuoc.getDangBaoChe(), str,
						currencyFormatter.format(thuoc.getDonGiaNhap()), currencyFormatter.format(thuoc.getDonGiaBan()),
						thuoc.getSoLuongTon() });
			} else {
				System.out.println("EERRROR!");
			}
		}
	}

	public void docDuLieuDataBaseVaoTablebyName(String ten) throws RemoteException {
		List<Thuoc> list = thuoc_dao.getThuocByTenThuoc(ten);
		NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (Thuoc thuoc : list) {
			String str = "";
			List<ChiTietThuoc> listTP = ct_dao.getalltptheomathuoc(thuoc.getMaThuoc());
			if (listTP.size() == 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
			} else if (listTP.size() > 0) {
				str += tp_dao.gettptheoma(listTP.get(0).getThanhphan().getMaTP());
				for (int i = 1; i < listTP.size(); i++) {
					str += ", " + tp_dao.gettptheoma(listTP.get(i).getThanhphan().getMaTP());
				}
				modelThuoc.addRow(new Object[] { thuoc.getMaThuoc(), thuoc.getTenThuoc(),
						thuoc.isKeDon() ? "Có" : "Không", ncc_dao.gettheoma(thuoc.getNhaCungCap().getMaNCC()),
						thuoc.getNoiSanXuat(), thuoc.getNgaySanXuat().format(dateFormatter),
						thuoc.getNgayHetHan().format(dateFormatter), thuoc.getDangBaoChe(), str,
						currencyFormatter.format(thuoc.getDonGiaNhap()), currencyFormatter.format(thuoc.getDonGiaBan()),
						thuoc.getSoLuongTon() });
			} else {
				System.out.println("EERRROR!");
			}
		}
	}

	public NhaCungCap taoNhaCC() {
		String ma = txtMaNCC.getText();
		String ten = txtTenNCC.getText();
		String email = txtEmail.getText();
		String sdt = txtSoDienThoai.getText();
		String diaChi = txtDiaChi.getText();
		return new NhaCungCap(ma, ten, email, sdt, diaChi);
	}

	private boolean checkValidInput() {
		// Kiểm tra cú pháp và tính hợp lệ của các trường dữ liệu
		if (txtTenTH.getSelectedItem().toString().isEmpty() || txtNoiSanXuat.getText().isEmpty()
				|| txtDangBaoChe.getText().isEmpty() || txtThanhPhan.getText().isEmpty()
				|| txtDonGiaNhap.getText().isEmpty() || txtDonGiaBan.getText().isEmpty()
				|| txtSoLuongNhap.getText().isEmpty() || txtNhaCungCap.getSelectedItem().toString().isEmpty()
				|| txtNgaySanXuat.getDate() == null || txtNgayHetHan.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
			return false;
		}

		// Kiểm tra thành phần chỉ được nhập số, chữ thường, chữ in hoa và dấu ','
		if (!txtThanhPhan.getText().matches("[0-9a-zA-Z,\\s]+")) {
			JOptionPane.showMessageDialog(this,
					"Thành phần chỉ được nhập số, chữ thường, chữ in hoa, dấu ',' và dấu cách");
			return false;
		}

		// Kiểm tra tên thuốc chỉ được chứa chữ cái, số và dấu cách
		if (!txtTenTH.getSelectedItem().toString().matches("[a-zA-Z0-9\\s]+")) {
			JOptionPane.showMessageDialog(this, "Tên thuốc chỉ được chứa chữ cái, số và dấu cách");
			return false;
		}

		// Kiểm tra nhà cung cấp chỉ được chứa chữ cái, số và dấu cách
		if (!txtNhaCungCap.getSelectedItem().toString().matches("[a-zA-Z0-9\\s\\(\\)]+")) {
			JOptionPane.showMessageDialog(this, "Nhà cung cấp chỉ được chứa chữ cái, số và dấu cách");
			return false;
		}

		// Kiểm tra nơi sản xuất chỉ được chứa chữ cái
		if (!txtNoiSanXuat.getText().matches("[a-zA-Z\\s]+")) {
			JOptionPane.showMessageDialog(this, "Nơi sản xuất chỉ được chứa chữ cái và dấu cách");
			return false;
		}

		// Kiểm tra số lượng nhập, đơn giá nhập và đơn giá bán phải là số dương
		try {
			double soLuongNhap = Double.parseDouble(txtSoLuongNhap.getText());
			double donGiaNhap = Double.parseDouble(txtDonGiaNhap.getText());
			double donGiaBan = Double.parseDouble(txtDonGiaBan.getText());

			if (soLuongNhap <= 0 || donGiaNhap <= 0 || donGiaBan <= 0) {
				JOptionPane.showMessageDialog(this, "Số lượng, đơn giá nhập và đơn giá bán phải là số dương");
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Số lượng, đơn giá nhập và đơn giá bán phải là số");
			return false;
		}

		// Kiểm tra ngày sản xuất phải trước ngày hiện tại
		LocalDate ngaySanXuat = txtNgaySanXuat.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ngayHienTai = LocalDate.now();
		if (ngaySanXuat.isAfter(ngayHienTai)) {
			JOptionPane.showMessageDialog(this, "Ngày sản xuất phải trước ngày hiện tại");
			return false;
		}

		// Kiểm tra ngày hết hạn phải sau ngày sản xuất
		LocalDate ngayHetHan = txtNgayHetHan.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (ngayHetHan.isBefore(ngaySanXuat)) {
			JOptionPane.showMessageDialog(this, "Ngày hết hạn phải sau ngày sản xuất");
			return false;
		}

		// Nếu tất cả điều kiện đều hợp lệ, trả về true
		return true;
	}

	public void addSK() {
		tableThuoc.addMouseListener(this);
		btnXemChiTiet.addActionListener(this);
		btnThemNhaCC.addActionListener(this);
		btnSuaNCC.addActionListener(this);
		btnNhapThuoc.addActionListener(this);
		btnXoaThuoc.addActionListener(this);
		btnSua.addActionListener(this);
		btnTaiHinh.addActionListener(this);
		btnTangDan.addActionListener(this);
		btnGiamDan.addActionListener(this);
		txtTimKiemTheoMa.getDocument().addDocumentListener(this);
	}

	private String generateThuocID() throws RemoteException {
		// Get the current maximum Thuoc ID from the database
		int maxThuocID = thuoc_dao.getMaxThuocID();

		// Increment the counter based on the current maximum Thuoc ID
		ThuocCounter = maxThuocID + 1;

		// Create the Thuoc ID by concatenating "TH" with the sequential number
		return "TH" + String.format("%04d", ThuocCounter);
	}

	public Thuoc taoThuoc() throws RemoteException {
		// Retrieve data from GUI components
		String maThuoc = generateThuocID();
		String tenThuoc = txtTenTH.getSelectedItem().toString();
		String hinhThuoc = imagePath; // assuming imagePath is a class variable containing the image path
		boolean keDon = chkKeDon.isSelected();
		NhaCungCap nhaCungCap = new NhaCungCap(getSubstringInParentheses(txtNhaCungCap.getSelectedItem().toString()));
		String noiSanXuat = txtNoiSanXuat.getText();
		LocalDate ngaySanXuat = txtNgaySanXuat.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ngayHetHan = txtNgayHetHan.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String dangBaoChe = txtDangBaoChe.getText();
		double donGiaNhap = Double.parseDouble(txtDonGiaNhap.getText());
		double donGiaBan = Double.parseDouble(txtDonGiaBan.getText());
		int soLuongTon = Integer.parseInt(txtSoLuongNhap.getText());

		// Create and return a new Thuoc instance
		return new Thuoc(maThuoc, tenThuoc, hinhThuoc, keDon, nhaCungCap, noiSanXuat, ngaySanXuat, ngayHetHan,
				dangBaoChe, donGiaNhap, donGiaBan, soLuongTon);
	}

	private String generateThanhPhanID() throws RemoteException {
		// Increment the counter for each new employee
		TPCounter = tp_dao.countThanhPhan() + 1;

		// Create the employee ID by concatenating "NV" with the sequential number
		return "TP" + String.format("%04d", TPCounter);
	}

	public static String getSubstringInParentheses(String input) {
		String patternString = "\\(([^)]+)\\)";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			// Group 1 contains the substring inside the parentheses
			return matcher.group(1);
		} else {
			// Return an empty string if no match is found
			return "";
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		delaySearch();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		delaySearch();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		delaySearch();
	}

	private void delaySearch() {
		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Perform search or any other action here
				modelThuoc.setRowCount(0);
				try {
					docDuLieuDataBaseVaoTablebyName(txtTimKiemTheoMa.getText());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		timer.setRepeats(false); // Execute the action only once
		timer.start();
	}

	public boolean checkValid() {
		String maNCC = txtMaNCC.getText();
		String tenNCC = txtTenNCC.getText();
		String email = txtEmail.getText();
		String soDienThoai = txtSoDienThoai.getText();
		String diaChi = txtDiaChi.getText();

		if (maNCC.isEmpty() || tenNCC.isEmpty() || email.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
			return false;
		}

		if (!isValidTenNCC(tenNCC)) {
			JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được chứa ký tự đặc biệt và số");
			return false;
		}

		if (!isValidEmail(email)) {
			JOptionPane.showMessageDialog(this, "Email không hợp lệ");
			return false;
		}

		if (!isValidSoDienThoai(soDienThoai)) {
			JOptionPane.showMessageDialog(this, "Số điện thoại phải có đúng 10 chữ số");
			return false;
		}

		// Additional validation for other fields if needed

		return true;
	}

	private boolean isValidTenNCC(String tenNCC) {
		// Your validation logic for Tên nhà cung cấp (Tên không chứa ký tự đặc biệt và
		// số)
		// Modify the regex pattern based on your specific requirements
		return tenNCC.matches("^[a-zA-Z0-9\\s]+$");
	}

	private boolean isValidEmail(String email) {
		// Your validation logic for Email (Check against a regex pattern for email
		// format)
		// Modify the regex pattern based on your specific requirements
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private boolean isValidSoDienThoai(String soDienThoai) {
		// Your validation logic for Số điện thoại (Check if it has exactly 10 digits)
		return soDienThoai.matches("\\d{10}");
	}
}
