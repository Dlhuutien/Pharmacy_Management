package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DoiMatKhauDAO extends Remote{
	public boolean ktraTaiKhoan(String username, String password) throws RemoteException;
	public boolean thucHienDoiMK(String username, String newPass) throws RemoteException;
}
