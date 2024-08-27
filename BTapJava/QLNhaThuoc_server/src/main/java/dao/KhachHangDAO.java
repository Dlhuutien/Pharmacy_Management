package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entities.KhachHang;

public interface KhachHangDAO extends Remote{
	public int getMaxCustomerID() throws RemoteException;
	public ArrayList<KhachHang> getalltbKhachHang() throws RemoteException;
	public boolean create(KhachHang kh) throws RemoteException;
	public boolean xoa(String ma) throws RemoteException;
	public boolean update(KhachHang kh) throws RemoteException;
	public int countKhachHang() throws RemoteException;
	public KhachHang getKhachHangTheoMaKH(String id) throws RemoteException;
	public KhachHang getKhachHangBySoDienThoai(String soDienThoai) throws RemoteException;
	public void capNhatDiemHang(String soDienThoai, int diemMoi, String hangMoi) throws RemoteException;
}
