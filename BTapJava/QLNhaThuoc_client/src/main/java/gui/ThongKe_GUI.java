package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

import dao.*;
import entities.*;

public class ThongKe_GUI extends JPanel implements ActionListener, MouseListener {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel modelSanPham;
    private JTable tableSanPham;
    private DefaultTableModel modelNhanVien;
    private JTable tableNhanVien;
    private JDateChooser tuNgay;
    private JDateChooser denNgay;
    private JButton btnThongKe;
    private JLabel lbThongKeSPBanDuoc;
    private JLabel lbThongKeHoaDon;
    private JLabel lbThongKeSPChuaBan;
    private JLabel lbThongKeDoanhThu;
    private HoaDonDAO qlhd_dao;
    private ChiTietHoaDonDAO qlcthd_dao;

    public ThongKe_GUI() throws Exception {
        setLayout(new BorderLayout());
        
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2005);
        try {
            qlhd_dao = (HoaDonDAO) registry.lookup("HoaDonDAO");
            qlcthd_dao =  (ChiTietHoaDonDAO) registry.lookup("ChiTietHoaDonDAO");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Không thể kết nối đến máy chủ RMI");
        }
        
        // Bang San Pham
        String[] columnNames = { "Số thứ tự", "Tên sản phẩm", "Số lượng đã bán", "Số lượng còn lại" };
        modelSanPham = new DefaultTableModel(columnNames, 0);
        tableSanPham = new JTable(modelSanPham);
        tableSanPham.getColumnModel().getColumn(0).setPreferredWidth(100); // Số thứ tự
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(250); // Tên sản phẩm
        tableSanPham.getColumnModel().getColumn(2).setPreferredWidth(200); // Số lượng đã bán
        tableSanPham.getColumnModel().getColumn(3).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(tableSanPham);

        // Bang Nhan Vien
        String[] columnNames1 = { "Số thứ tự", "Mã nhân viên", "Tên nhân viên", "Tổng tiền đã bán" };
        modelNhanVien = new DefaultTableModel(columnNames1, 0);
        tableNhanVien = new JTable(modelNhanVien);
        JScrollPane scrollPane1 = new JScrollPane(tableNhanVien);
        tableNhanVien.getColumnModel().getColumn(0).setPreferredWidth(100); // Số thứ tự
        tableNhanVien.getColumnModel().getColumn(1).setPreferredWidth(200); // Mã nhân viên
        tableNhanVien.getColumnModel().getColumn(2).setPreferredWidth(250); // Tên nhân viên
        tableNhanVien.getColumnModel().getColumn(3).setPreferredWidth(220);

        
        
        JLabel lb11 = new JLabel("Tổng số sản phẩm bán được");
        JLabel lb12 = new JLabel("Tổng tiền mà nhân viên đã bán được");

        Box btotal = Box.createHorizontalBox();
        Box left = Box.createVerticalBox();
        left.add(lb11);
        left.add(scrollPane);
        left.add(lb12);
        left.add(scrollPane1);
        left.setBorder(BorderFactory.createTitledBorder("Thống kê dạng bảng"));
        btotal.add(left);

        Box spacey0 = Box.createVerticalBox();
        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        Box p = Box.createHorizontalBox();
        JLabel lbTuNgay = new JLabel("Từ ngày: ");
        tuNgay = new JDateChooser();
        JLabel lbDenNgay = new JLabel("Đến ngày: ");
        denNgay = new JDateChooser();
        p.add(Box.createHorizontalStrut(40));
        p.add(lbTuNgay);
        p.add(tuNgay);
        p.add(Box.createHorizontalStrut(20));
        p.add(lbDenNgay);
        p.add(denNgay);
        btnThongKe = new JButton("Thống kê");
        p.add(Box.createHorizontalStrut(20));
        p.add(btnThongKe);
        p.add(Box.createHorizontalStrut(40));
        spacey0.add(Box.createVerticalStrut(20));
        spacey0.add(p);
        right.add(spacey0, BorderLayout.NORTH);
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2, 30, 30));
        JPanel b1, b2, b3, b4;

        b1 = new JPanel();
        b1.setLayout(new BorderLayout());

        JPanel bl1con = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bl1con.setBackground(Color.WHITE);

        JLabel bl1 = new JLabel("Thống kê sản phẩm bán được");
        bl1con.add(bl1);
        bl1.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, 20));
        
        lbThongKeSPBanDuoc = new JLabel("0 sản phẩm");
        Box c1 = Box.createHorizontalBox();
        c1.add(Box.createHorizontalGlue());
        c1.add(lbThongKeSPBanDuoc);
        c1.add(Box.createHorizontalGlue());
        lbThongKeSPBanDuoc.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, 30));
        b1.add(bl1con, BorderLayout.NORTH);
        b1.add(c1);
        b1.setBackground(Color.YELLOW);

        b2 = new JPanel();
        b2.setLayout(new BorderLayout());
        JPanel bl2con = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bl2con.setBackground(Color.white);
        JLabel bl2 = new JLabel("Thống kê hóa đơn đã xuất");
        bl2.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, 20));
        bl2con.add(bl2);
        Box c2 = Box.createHorizontalBox();
        lbThongKeHoaDon = new JLabel("0 Hóa đơn");
        lbThongKeHoaDon.setFont(new Font(null, Font.BOLD, 30));
        c2.add(Box.createHorizontalGlue());
        c2.add(lbThongKeHoaDon);
        c2.add(Box.createHorizontalGlue());
        b2.add(bl2con, BorderLayout.NORTH);
        b2.add(c2);
        b2.setBackground(Color.LIGHT_GRAY);

        b3 = new JPanel();
        b3.setLayout(new BorderLayout());
        JPanel bl3con = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bl3con.setBackground(Color.white);
        JLabel bl3 = new JLabel("Thống kê sản phẩm chưa bán");
        bl3.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, 20));
        bl3con.add(bl3);
        lbThongKeSPChuaBan = new JLabel("0 sản phẩm");
        Box c3 = Box.createHorizontalBox();
        c3.add(Box.createHorizontalGlue());
        c3.add(lbThongKeSPChuaBan);
        c3.add(Box.createHorizontalGlue());
        lbThongKeSPChuaBan.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, 30));
        b3.add(bl3con, BorderLayout.NORTH);
        b3.add(c3);
        b3.setBackground(Color.green);

        b4 = new JPanel();
        b4.setLayout(new BorderLayout());
        JPanel bl4con = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bl4con.setBackground(Color.white);
        JLabel bl4 = new JLabel("Thống kê theo doanh thu");
        bl4.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, 20));
        bl4con.add(bl4);
        lbThongKeDoanhThu = new JLabel("0 VND");
        Box c4 = Box.createHorizontalBox();
        c4.add(Box.createHorizontalGlue());
        c4.add(lbThongKeDoanhThu);
        c4.add(Box.createHorizontalGlue());
        lbThongKeDoanhThu.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, 30));
        b4.add(bl4con, BorderLayout.NORTH);
        b4.add(c4);
        b4.setBackground(Color.CYAN);
        Box spacey = Box.createVerticalBox();
        Box spacex = Box.createHorizontalBox();

        p1.setBorder(BorderFactory.createTitledBorder("Thống kê chi tiết"));
        spacey.add(Box.createVerticalStrut(40));
        spacey.add(p1);
        spacey.add(Box.createVerticalStrut(40));

        spacex.add(Box.createHorizontalStrut(40));
        spacex.add(spacey);
        spacex.add(Box.createHorizontalStrut(40));

        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        p1.add(b4);
        btotal.add(right);
        right.add(spacex);
        add(btotal);
        
        btnThongKe.addActionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

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
        Object o = e.getSource();
        if(o.equals(btnThongKe)) {
            LocalDate fromDate = null;
            LocalDate toDate = null;
            // Convert JDateChooser to LocalDate
            if (tuNgay != null && tuNgay.getDate() != null) {
                fromDate = tuNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            if (denNgay != null && denNgay.getDate() != null) {
                toDate = denNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            try {
                updateProductStatisticsTable();
                updateNhanVientable();
                NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
                lbThongKeSPBanDuoc.setText(String.valueOf(qlhd_dao.getProductCodeCountByDateRange(fromDate, toDate))+ " sản phẩm");
                lbThongKeSPChuaBan.setText(String.valueOf(qlhd_dao.getProductNotCountByDateRange(fromDate, toDate))+ " sản phẩm");
                lbThongKeHoaDon.setText(String.valueOf(qlhd_dao.getInvoiceCountByDateRange(fromDate, toDate))+ " hóa đơn");
                lbThongKeDoanhThu.setText(currencyFormatter.format(qlhd_dao.getTotalByDateRange(fromDate, toDate)));
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void createAndShowGUI() throws Exception {
        JFrame frame = new JFrame("ThongKe_GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);

        ThongKe_GUI thongKePanel = new ThongKe_GUI();
        frame.getContentPane().add(thongKePanel);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    public void updateProductStatisticsTable() throws RemoteException {
        LocalDate fromDate = null;
        LocalDate toDate = null;
        // Convert JDateChooser to LocalDate
        if (tuNgay != null && tuNgay.getDate() != null) {
            fromDate = tuNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        if (denNgay != null && denNgay.getDate() != null) {
            toDate = denNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        List<Object[]> productStatistics = qlcthd_dao.getThongKeSanPhamByDateRange(fromDate, toDate);
        // Update the table model
        modelSanPham.setRowCount(0);
        int stt = 1;
        for (Object[] row : productStatistics) {
            Object[] newRow = { stt++, row[0], row[1], row[2] };
            modelSanPham.addRow(newRow);
        }
    }
    public void updateNhanVientable() throws RemoteException {
        LocalDate fromDate = null;
        LocalDate toDate = null;
        // Convert JDateChooser to LocalDate
        if (tuNgay != null && tuNgay.getDate() != null) {
            fromDate = tuNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        if (denNgay != null && denNgay.getDate() != null) {
            toDate = denNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        List<Object[]> resultList = qlhd_dao.getTongTienDaBanByNhanVienAndDateRange(fromDate, toDate);

        // Làm sạch dữ liệu cũ trong bảng
        modelNhanVien.setRowCount(0);
        NumberFormat currencyFormatter = new DecimalFormat("#,##0 VND");
        // Đổ dữ liệu mới vào bảng
        int stt = 1;
        for (Object[] row : resultList) {
            Object[] newRow = { stt++, row[0], row[1], currencyFormatter.format(row[2]) };
            modelNhanVien.addRow(newRow);
        }
    }
}
