package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.TaiKhoanNhanVienDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class QuanLyTaiKhoanNhanVien_GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtTimKiem;
	private JTable table;
	private DefaultTableModel tableModel;
	private TaiKhoanNhanVienDAO qltknv_dao;

	public QuanLyTaiKhoanNhanVien_GUI() throws Exception {
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2005);
		try {
			qltknv_dao = (TaiKhoanNhanVienDAO) registry.lookup("TaiKhoanNhanVienDAO");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Không thể kết nối đến máy chủ RMI");
		}
		setTitle("Xem tài khoản");
		setSize(500, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		// Thanh tìm kiếm
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lbTimKiem = new JLabel("Tìm kiếm theo tài khoản:");
		txtTimKiem = new JTextField(20);
		JButton btnTimKiem = new JButton("Tìm");
		JButton btnDoiMatKhau = new JButton("Đổi mật khẩu");
		
		btnDoiMatKhau.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new DoiMatKhau_UI().setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		
		btnTimKiem.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Xử lý tìm kiếm dựa trên nội dung nhập vào
		        String taiKhoanCanTim = txtTimKiem.getText().trim();

		        // Kiểm tra xem chuỗi tìm kiếm có rỗng không
		        if (taiKhoanCanTim.isEmpty()) {
		            JOptionPane.showMessageDialog(QuanLyTaiKhoanNhanVien_GUI.this, "Vui lòng nhập mã tài khoản cần tìm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Thực hiện tìm kiếm trong bảng
		        int row = -1;
		        for (int i = 0; i < table.getRowCount(); i++) {
		            String taiKhoan = tableModel.getValueAt(i, 0).toString();
		            if (taiKhoan.equalsIgnoreCase(taiKhoanCanTim)) {
		                row = i;
		                break;
		            }
		        }

		        // Kiểm tra xem tìm thấy hay không
		        if (row != -1) {
		            // Di chuyển đến dòng tìm thấy
		            table.setRowSelectionInterval(row, row);
		            table.scrollRectToVisible(table.getCellRect(row, 0, true));
		        } else {
		            JOptionPane.showMessageDialog(QuanLyTaiKhoanNhanVien_GUI.this, "Không tìm thấy mã tài khoản: " + taiKhoanCanTim, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		        }
		    }
		});

		searchPanel.add(lbTimKiem);
		searchPanel.add(txtTimKiem);

		add(searchPanel, BorderLayout.NORTH);

		// Bảng tài khoản và mật khẩu
		String[] columnNames = { "Tài khoản", "Mật khẩu" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		// Thêm một số dữ liệu mẫu để hiển thị trong bảng
		themDuLieu();

		// Có thể thêm các chức năng khác ở đây
		JPanel bot = new JPanel();
		bot.add(btnTimKiem);
		bot.add(btnDoiMatKhau);
		add(bot, BorderLayout.SOUTH);
	}

	private void themDuLieu() throws RemoteException {
		ArrayList<entities.TaiKhoanNhanVien> list = qltknv_dao.getAllTaiKhoan();

		for (entities.TaiKhoanNhanVien tk : list) {
			// Lấy thông tin từ đối tượng TaiKhoanNhanVien
			String taiKhoan = tk.getTaiKhoan();
			String matKhau = tk.getMatKhau();
			String maNV = tk.getNhanVien().getMaNV();
			// Thêm dòng mới vào bảng và điền dữ liệu
			tableModel.addRow(new Object[] { taiKhoan, matKhau, maNV });
		}
	}
}
