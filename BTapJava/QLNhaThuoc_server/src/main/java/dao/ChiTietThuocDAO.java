package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entities.*;

public interface ChiTietThuocDAO extends Remote{
	public ArrayList<ChiTietThuoc> getalltptheomathuoc(String mathuoc) throws RemoteException;
	public ArrayList<String> getAllChiTietThuocIDs() throws RemoteException;
	public boolean addChiTietThuoc(ChiTietThuoc chiTietThuoc) throws RemoteException;
	public int countChiTietThuoc() throws RemoteException;
	public boolean deleteChiTietThuocByMaThuoc(String maThuoc) throws RemoteException;

}
