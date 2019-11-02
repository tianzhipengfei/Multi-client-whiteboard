import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * RMI Remote interface - must be shared between client and server.
 * All methods must throw RemoteException.
 * All parameters and return types must be either primitives or Serializable.
 *  
 * Any object that is a remote object must implement this interface.
 * Only those methods specified in a "remote interface" are available remotely.
 */
public interface IRemoteBoard extends Remote {
	
	public boolean join(String username) throws RemoteException;
	
	public boolean approved(String username) throws RemoteException;
	
	public void approve(String username) throws RemoteException;
	
	public ArrayList<String> getCandidate() throws RemoteException;
	
	public boolean inRejectList(String username) throws RemoteException;
	
	public void reject(String username) throws RemoteException;
	
	public boolean inUserList(String username) throws RemoteException;
	
	public ArrayList<String> getUserList() throws RemoteException;
	
	public int kickUser(String username) throws RemoteException;
	
	public void sendMessage(String message) throws RemoteException;
	
	public ArrayList<String> getAllMessage() throws RemoteException;
	
	public ArrayList<MyShape> getAllShape() throws RemoteException;
	
	public HashMap<String,MyShape> getCurrentShape() throws RemoteException;
	
	public void newShape(String username,MyShape shape) throws RemoteException;
	
	public void updateShape(String username,int x1,int y1,int x2,int y2) throws RemoteException;
	
	public void addShape(String username) throws RemoteException;
	
	public void addPoint(String username,int x2,int y2) throws RemoteException;
	
	
	public int getMessageSize() throws RemoteException;
}