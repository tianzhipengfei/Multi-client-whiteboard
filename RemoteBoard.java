import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Server side implementation of the remote interface.
 * Must extend UnicastRemoteObject, to allow the JVM to create a 
 * remote proxy/stub.
 *
 */
public class RemoteBoard extends UnicastRemoteObject implements IRemoteBoard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3239346823506932895L;
	private ArrayList<MyShape> shape = new ArrayList<MyShape>();
	private HashMap<String,MyShape> currentShape = new HashMap<String,MyShape>();
	private ArrayList<String> users = new ArrayList<String>();
	private ArrayList<String> usercandidate = new ArrayList<String>();
	private ArrayList<String> rejectedUser = new ArrayList<String>();
	private ArrayList<String> message = new ArrayList<String>();
	private final Object lock = new Object();
	private final Object messagelock = new Object();
	private final Object shapelock = new Object();
	private final Object editlock = new Object();
	//private static final long serialVersionUID = 1L;
	
	protected RemoteBoard() throws RemoteException {
	}
	
	public boolean join(String username) throws RemoteException{
		synchronized (lock) {
			if(users.contains(username)) {
				return false;
			}
			if(inRejectList(username)) {
				return false;
			}
			usercandidate.add(username);
			return true;
		}
	}
	
	public boolean approved(String username) throws RemoteException{
		synchronized (lock) {
			if(users.contains(username)) {
				return true;
			}
			return false;
		}
	}
	
	public void approve(String username) throws RemoteException{
		synchronized (lock) {
			users.add(username);
			usercandidate.remove(username);
		}
	}
	
	public ArrayList<String> getCandidate() throws RemoteException{
		synchronized (lock) {
			return usercandidate;
		}
	}
	
	public boolean inRejectList(String username) throws RemoteException{
		synchronized (lock) {
			return rejectedUser.contains(username);
		}
	}
	
	public void reject(String username) throws RemoteException{
		synchronized (lock) {
			usercandidate.remove(username);
			rejectedUser.add(username);
		}
	}
	
	public boolean inUserList(String username) throws RemoteException{
		synchronized(lock) {
			return users.contains(username);
		}
	}
	
	public ArrayList<String> getUserList() throws RemoteException{
		synchronized(lock) {
			return users;
		}
	}
	
	public int kickUser(String username) throws RemoteException{
		synchronized(lock) {
			if(users.contains(username)) {
				users.remove(username);
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	public void sendMessage(String message) throws RemoteException{
		synchronized(messagelock) {
			this.message.add(message);
		}
	}
	
	public ArrayList<String> getAllMessage() throws RemoteException{
		synchronized(messagelock) {
			return message;
		}
	}
	
	public ArrayList<MyShape> getAllShape() throws RemoteException{
		synchronized(shapelock) {
			return shape;
		}
	}
	
	public HashMap<String,MyShape> getCurrentShape() throws RemoteException{
		synchronized(editlock) {
			return currentShape;
		}
	}
	
	public void newShape(String username,MyShape shape) throws RemoteException{
		synchronized(editlock) {
			currentShape.put(username,shape);
		}
	}
	
	public void updateShape(String username,int x1,int y1,int x2,int y2) throws RemoteException{
		synchronized(editlock) {
			currentShape.get(username).upgradePoint(x1, y1, x2, y2);
		}
	}
	
	public void addShape(String username) throws RemoteException{
		MyShape tmp = currentShape.get(username);
		synchronized(editlock) {
			currentShape.remove(username);
		}
		shape.add(tmp);
	}
	
	public void addPoint(String username,int x2,int y2) throws RemoteException{
		MyShape tmp = currentShape.get(username);
		tmp.addPoint(x2, y2);
	}
	
	public int getMessageSize() throws RemoteException {
		return message.size();
	}
}