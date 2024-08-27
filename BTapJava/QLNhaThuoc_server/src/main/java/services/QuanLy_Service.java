package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dao.QuanLyDAO;
import entities.QuanLy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class QuanLy_Service extends UnicastRemoteObject implements QuanLyDAO{
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public QuanLy_Service(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}
	
	@Override
	public QuanLy getQuanLyTheoMaQL(String id) throws RemoteException {
		 QuanLy ql = null;
	        try {
	            Query query = entityManager.createQuery("SELECT q FROM QuanLy q WHERE q.maQL = :id");
	            query.setParameter("id", id);
	            ql = (QuanLy) query.getSingleResult();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return ql;
	    }

}
