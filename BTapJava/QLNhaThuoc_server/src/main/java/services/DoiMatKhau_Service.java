package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dao.DoiMatKhauDAO;
import entities.TaiKhoanNhanVien;
import entities.TaiKhoanQuanLy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class DoiMatKhau_Service extends UnicastRemoteObject implements DoiMatKhauDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public DoiMatKhau_Service(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@Override
	public boolean ktraTaiKhoan(String username, String password) throws RemoteException {
		try {
			// Kiểm tra tài khoản nhân viên
			Query queryNV = entityManager.createQuery(
					"SELECT t FROM TaiKhoanNhanVien t WHERE t.taiKhoan = :username AND t.matKhau = :password");
			queryNV.setParameter("username", username);
			queryNV.setParameter("password", password);
			TaiKhoanNhanVien taiKhoanNV = (TaiKhoanNhanVien) queryNV.getSingleResult();
			if (taiKhoanNV != null) {
				return true;
			}

			// Kiểm tra tài khoản quản lý
			Query queryQL = entityManager.createQuery(
					"SELECT t FROM TaiKhoanQuanLy t WHERE t.taiKhoan = :username AND t.matKhau = :password");
			queryQL.setParameter("username", username);
			queryQL.setParameter("password", password);
			TaiKhoanQuanLy taiKhoanQL = (TaiKhoanQuanLy) queryQL.getSingleResult();
			return taiKhoanQL != null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return false;
	}

	@Override
	public boolean thucHienDoiMK(String username, String newPass) throws RemoteException {
		boolean success = false;
		try {
			entityManager.getTransaction().begin();

			// Cập nhật mật khẩu cho tài khoản nhân viên
			Query queryNV = entityManager
					.createQuery("UPDATE TaiKhoanNhanVien t SET t.matKhau = :newPass WHERE t.taiKhoan = :username");
			queryNV.setParameter("newPass", newPass);
			queryNV.setParameter("username", username);
			int updatedRowsNV = queryNV.executeUpdate();

			// Cập nhật mật khẩu cho tài khoản quản lý
			Query queryQL = entityManager
					.createQuery("UPDATE TaiKhoanQuanLy t SET t.matKhau = :newPass WHERE t.taiKhoan = :username");
			queryQL.setParameter("newPass", newPass);
			queryQL.setParameter("username", username);
			int updatedRowsQL = queryQL.executeUpdate();

			entityManager.getTransaction().commit();

			// Kiểm tra xem ít nhất một trong hai cập nhật đã thành công
			success = (updatedRowsNV > 0 || updatedRowsQL > 0);
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
		return success;
	}

}
