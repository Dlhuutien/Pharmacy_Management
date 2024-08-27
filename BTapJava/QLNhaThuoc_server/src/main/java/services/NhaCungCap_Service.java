package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import dao.NhaCungCapDAO;
import entities.NhaCungCap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class NhaCungCap_Service extends UnicastRemoteObject implements NhaCungCapDAO{
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public NhaCungCap_Service(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}


	@Override
	public String gettheoma(String ma) throws RemoteException {
	    String tenNCC = null;
	    try {
	        Query query = entityManager.createQuery("SELECT ncc.tenNCC FROM NhaCungCap ncc WHERE ncc.maNCC = :ma");
	        query.setParameter("ma", ma);
	        tenNCC = (String) query.getSingleResult();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return tenNCC;
	}
	
	@Override
	public ArrayList<String> getallten() throws RemoteException {
	    ArrayList<String> dst = new ArrayList<>();
	    try {
	        Query query = entityManager.createQuery("SELECT ncc.tenNCC FROM NhaCungCap ncc");
	        @SuppressWarnings("unchecked")
	        List<String> results = query.getResultList();
	        dst.addAll(results);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return dst;
	}
	
	@Override
	public ArrayList<String> getallma() throws RemoteException {
	    ArrayList<String> dst = new ArrayList<>();
	    try {
	        Query query = entityManager.createQuery("SELECT CONCAT(ncc.tenNCC, ' (', ncc.maNCC, ')') FROM NhaCungCap ncc");
	        @SuppressWarnings("unchecked")
	        List<String> results = query.getResultList();
	        dst.addAll(results);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return dst;
	}

	@Override
	public ArrayList<NhaCungCap> getall() throws RemoteException {
	    ArrayList<NhaCungCap> dst = new ArrayList<>();
	    try {
	        Query query = entityManager.createQuery("SELECT ncc FROM NhaCungCap ncc");
	        @SuppressWarnings("unchecked")
	        List<NhaCungCap> results = query.getResultList();
	        dst.addAll(results);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return dst;
	}


	@Override
	public boolean create(NhaCungCap ncc) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(ncc);
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
			NhaCungCap nhaCungCap = entityManager.find(NhaCungCap.class, ma);
			entityManager.remove(nhaCungCap);
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
	public boolean capNhat(NhaCungCap ncc) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(ncc);
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
}
