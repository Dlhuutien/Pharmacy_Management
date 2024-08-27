package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.ChiTietHoaDonDAO;
import entities.ChiTietHoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class ChiTietHoaDon_Service extends UnicastRemoteObject implements ChiTietHoaDonDAO{
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public ChiTietHoaDon_Service(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
    public List<Object[]> getThongKeSanPhamByDateRange(LocalDate fromDate, LocalDate toDate) throws RemoteException {
        List<Object[]> resultList = new ArrayList<>();
        try {
            String jpql = "SELECT cthd.thuoc.tenThuoc, SUM(cthd.soLuong), cthd.thuoc.soLuongTon "
                        + "FROM ChiTietHoaDon cthd "
                        + "JOIN cthd.thuoc "
                        + "JOIN cthd.hoaDon hd "
                        + "WHERE hd.ngayMua BETWEEN :fromDate AND :toDate "
                        + "GROUP BY cthd.thuoc.tenThuoc, cthd.thuoc.soLuongTon";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            resultList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

	@Override
	public boolean themChiTietHoaDon(ChiTietHoaDon hd) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(hd);
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
	public double getThanhTienByMaThuocAndMaHoaDon(String maThuoc, String maHoaDon) throws RemoteException {
	    double thanhTien = 0;
	    try {
	        String jpql = "SELECT c FROM ChiTietHoaDon c WHERE c.thuoc.maThuoc = :maThuoc AND c.hoaDon.maHD = :maHD";
	        Query query = entityManager.createQuery(jpql);
	        query.setParameter("maThuoc", maThuoc);
	        query.setParameter("maHD", maHoaDon);
	        
	        ChiTietHoaDon cthd = (ChiTietHoaDon) query.getSingleResult();
	        thanhTien = cthd.getThanhTien();
	    } catch (NoResultException e) {
	        System.out.println("Không tìm thấy chi tiết hóa đơn.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return thanhTien;
	}

	@Override
    public boolean deleteChiTietHoaDonByMaThuoc(String maThuoc) throws RemoteException {
        try {
            entityManager.getTransaction().begin();
            String jpql = "DELETE FROM ChiTietHoaDon c WHERE c.thuoc.maThuoc = :maThuoc";
            int deletedCount = entityManager.createQuery(jpql)
                    .setParameter("maThuoc", maThuoc)
                    .executeUpdate();
            entityManager.getTransaction().commit();
            return deletedCount > 0;
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            ex.printStackTrace();
        }
        return false;
    }


}
