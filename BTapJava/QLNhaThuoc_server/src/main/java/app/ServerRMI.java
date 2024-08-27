package app;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import jakarta.persistence.EntityManager;
import dao.*;
import services.*;

public class ServerRMI {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException{
		Registry registry = LocateRegistry.createRegistry(2005);
		
		EntityManagerFactoryUtil entityManagerFactory = new EntityManagerFactoryUtil();
		EntityManager entityManager = entityManagerFactory.getEntityManager();
		
		DangNhapDAO dangNhapDAO = new DangNhap_Service(entityManager);
		registry.bind("DangNhapDAO", dangNhapDAO);
		
		DoiMatKhauDAO doiMatKhauDAO = new DoiMatKhau_Service(entityManager);
		registry.bind("DoiMatKhauDAO", doiMatKhauDAO);
		
		QuanLyDAO quanLyDAO = new QuanLy_Service(entityManager);
		registry.bind("QuanLyDAO", quanLyDAO);
		
		KhachHangDAO khachHangDAO = new KhachHang_Service(entityManager);
		registry.bind("KhachHangDAO", khachHangDAO);
		
		NhanVienDAO nhanVienDAO = new NhanVien_Service(entityManager);
		registry.bind("NhanVienDAO", nhanVienDAO);
		
		TaiKhoanNhanVienDAO taiKhoanNhanVienDAO = new TaiKhoanNhanVien_Service(entityManager);
		registry.bind("TaiKhoanNhanVienDAO", taiKhoanNhanVienDAO);
		
		NhaCungCapDAO cungCapDAO = new NhaCungCap_Service(entityManager);
		registry.bind("NhaCungCapDAO", cungCapDAO);
		
		ThanhPhanDAO thanhPhanDAO = new ThanhPhan_Service(entityManager);
		registry.bind("ThanhPhanDAO", thanhPhanDAO);
		
		ChiTietThuocDAO chiTietThuocDAO = new ChiTietThuoc_Service(entityManager);
		registry.bind("ChiTietThuocDAO", chiTietThuocDAO);
		
		ThuocDAO thuocDAO = new Thuoc_Service(entityManager);
		registry.bind("ThuocDAO", thuocDAO);
		
		HoaDonDAO hoaDonDAO = new HoaDon_Service(entityManager);
		registry.bind("HoaDonDAO", hoaDonDAO);
		
		ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDon_Service(entityManager);
		registry.bind("ChiTietHoaDonDAO", chiTietHoaDonDAO);
		
		System.out.println("RMI Server ready");
	}
}
