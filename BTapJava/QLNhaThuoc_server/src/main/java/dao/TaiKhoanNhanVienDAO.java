package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entities.TaiKhoanNhanVien;


public interface TaiKhoanNhanVienDAO extends Remote{
	public boolean create(TaiKhoanNhanVien tk) throws RemoteException;
	public boolean xoa(String ma) throws RemoteException;
	public ArrayList<TaiKhoanNhanVien> getAllTaiKhoan() throws RemoteException;
	public TaiKhoanNhanVien timTaiKhoanTheoTaiKhoan(String taiKhoan) throws RemoteException;
}
