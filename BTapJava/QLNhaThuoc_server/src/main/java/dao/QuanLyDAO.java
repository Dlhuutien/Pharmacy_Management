package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.QuanLy;

public interface QuanLyDAO extends Remote{
	QuanLy getQuanLyTheoMaQL(String id) throws RemoteException;
}
