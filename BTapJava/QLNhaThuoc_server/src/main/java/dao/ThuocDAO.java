package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entities.Thuoc;

public interface ThuocDAO extends Remote{
	public boolean updateThuoc(Thuoc thuoc) throws RemoteException;
	public ArrayList<Thuoc> getThuocKeDonList() throws RemoteException;
	public int getMaxThuocID() throws RemoteException;
	public ArrayList<Thuoc> getThuocKoKeDonList() throws RemoteException;
	public ArrayList<Thuoc> getallthuocExpired() throws RemoteException;
	public ArrayList<Thuoc> getThuocByTenThuoc(String tenThuoc) throws RemoteException;
	public Thuoc getThuocByMa(String ma) throws RemoteException;
	public boolean deleteThuoc(String maThuoc) throws RemoteException;
	public boolean addThuoc(Thuoc thuoc) throws RemoteException;
	public int countThuoc() throws RemoteException;
	public boolean updateSoLuongTon(String maThuoc, int newSoLuongTon) throws RemoteException;
	public int getSoLuongTonByMaThuoc(String maThuoc) throws RemoteException;
	public ArrayList<Thuoc> getallthuoc() throws RemoteException;
	public String getanhthuoc(String ma) throws RemoteException;
	public ArrayList<String> getalltenthuoc() throws RemoteException;
}
