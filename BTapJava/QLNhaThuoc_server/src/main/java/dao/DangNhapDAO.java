package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DangNhapDAO extends Remote{
	 boolean dNhap(String username, String password, boolean isAdmin) throws RemoteException;
}
