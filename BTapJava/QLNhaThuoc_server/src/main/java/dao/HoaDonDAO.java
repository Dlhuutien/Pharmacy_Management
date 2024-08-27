package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.HoaDon;

public interface HoaDonDAO extends Remote{
	public ArrayList<HoaDon> getAllHoaDon() throws RemoteException;
	public ArrayList<HoaDon> getHoaDonByMaHDLike(String maHD) throws RemoteException;
	public ArrayList<HoaDon> getHoaDonBySoDienThoai(String soDienThoai) throws RemoteException;
	public ArrayList<HoaDon> getAllHoaDonByDateRange(LocalDate fromDate, LocalDate toDate) throws RemoteException;

	public int getProductCodeCountByDateRange(LocalDate fromDate, LocalDate toDate) throws RemoteException;
	public int getProductNotCountByDateRange(LocalDate fromDate, LocalDate toDate) throws RemoteException;
	public int getInvoiceCountByDateRange(LocalDate fromDate, LocalDate toDate) throws RemoteException;
	public List<Object[]> getTongTienDaBanByNhanVienAndDateRange(LocalDate fromDate, LocalDate toDate) throws RemoteException;
	public double getTotalByDateRange(LocalDate fromDate, LocalDate toDate) throws RemoteException;
	
	public int demSoLuongHoaDon() throws RemoteException;
	public boolean themHoaDon(HoaDon hoaDon) throws RemoteException;
	public double getTongTienByMaHD(String maHD) throws RemoteException;
	public boolean updateTongTien(String maHD, double additionalAmount) throws RemoteException;
	public HoaDon getHoaDonByMaHD(String maHD) throws RemoteException;
}

