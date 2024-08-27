package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entities.NhanVien;

public interface NhanVienDAO extends Remote{
	public int getMaxEmployeeID() throws RemoteException;
	public ArrayList<NhanVien> getalltbNhanVien() throws RemoteException;
	public boolean create(NhanVien nv) throws RemoteException;
	public boolean xoa(String ma) throws RemoteException;
	public boolean update(NhanVien nv) throws RemoteException;
	public int countNhanVien() throws RemoteException;
	public NhanVien getNhanVienTheoMaNV(String id) throws RemoteException;
}
