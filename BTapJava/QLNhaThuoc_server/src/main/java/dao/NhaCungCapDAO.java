package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entities.*;

public interface NhaCungCapDAO extends Remote{
	public String gettheoma(String ma) throws RemoteException;
	public ArrayList<String> getallten() throws RemoteException;
	public ArrayList<String> getallma() throws RemoteException;
	public ArrayList<NhaCungCap> getall() throws RemoteException;
	public boolean create(NhaCungCap ncc) throws RemoteException;
	public boolean xoa(String ma) throws RemoteException;
	public boolean capNhat(NhaCungCap ncc) throws RemoteException;
}
