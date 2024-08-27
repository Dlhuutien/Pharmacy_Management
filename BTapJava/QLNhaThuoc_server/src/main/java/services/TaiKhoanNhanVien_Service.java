package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import dao.TaiKhoanNhanVienDAO;
import entities.TaiKhoanNhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class TaiKhoanNhanVien_Service extends UnicastRemoteObject implements TaiKhoanNhanVienDAO{
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public TaiKhoanNhanVien_Service(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@Override
	public boolean create(TaiKhoanNhanVien tk) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(tk);
			trans.commit();
			return true;
		} catch (Exception e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean xoa(String ma) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			TaiKhoanNhanVien taiKhoanNhanVien = entityManager.find(TaiKhoanNhanVien.class, ma);
			entityManager.remove(taiKhoanNhanVien);
			trans.commit();
			return true;
		} catch (Exception e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
		}
		return false;	}

	@Override
	public ArrayList<TaiKhoanNhanVien> getAllTaiKhoan() throws RemoteException {
		 ArrayList<TaiKhoanNhanVien> dsnv = new ArrayList<>();
		    try {
		        Query query = entityManager.createQuery("SELECT tk FROM TaiKhoanNhanVien tk");
		        @SuppressWarnings("unchecked")
				List<TaiKhoanNhanVien> results = query.getResultList();
		        for (TaiKhoanNhanVien tk : results) {
		            dsnv.add(tk);
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    return dsnv;
	}

	@Override
	public TaiKhoanNhanVien timTaiKhoanTheoTaiKhoan(String taiKhoan) throws RemoteException {
		  TaiKhoanNhanVien tk = null;
		    try {
		        Query query = entityManager.createQuery("SELECT tk FROM TaiKhoanNhanVien tk WHERE tk.taiKhoan = :taiKhoan");
		        query.setParameter("taiKhoan", taiKhoan);
		        tk = (TaiKhoanNhanVien) query.getSingleResult();
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    return tk;
	}

}
