package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.ThanhPhan;

public interface ThanhPhanDAO extends Remote{
	public String gettptheoma(String ma) throws RemoteException;
	public boolean create(ThanhPhan tp) throws RemoteException;
	public int countThanhPhan() throws RemoteException;
	public boolean exists(String tenTP) throws RemoteException;
	public String getMaTPByTenTP(String tenTP) throws RemoteException;
}
