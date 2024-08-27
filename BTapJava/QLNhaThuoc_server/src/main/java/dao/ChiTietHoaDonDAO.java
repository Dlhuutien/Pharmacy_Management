package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

import entities.ChiTietHoaDon;

public interface ChiTietHoaDonDAO extends Remote{
	public List<Object[]> getThongKeSanPhamByDateRange(LocalDate fromDate, LocalDate toDate) throws RemoteException;
	public boolean themChiTietHoaDon(ChiTietHoaDon hd) throws RemoteException;
	public double getThanhTienByMaThuocAndMaHoaDon(String maThuoc, String maHoaDon) throws RemoteException;
	public boolean deleteChiTietHoaDonByMaThuoc(String maThuoc) throws RemoteException;

}
