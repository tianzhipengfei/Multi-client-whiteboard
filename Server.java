import java.awt.Color;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import java.net.InetAddress;

/**
 * Creates an instance of the RemoteMath class and
 * publishes it in the rmiregistry
 * 
 */
public class Server {

	public static void main(String[] args)  {
		
		try {
			String ip = "localhost";
			String port = "10999";
			String username = "Super user";
			if(args.length==3) {
				ip = args[0];
				port = args[1];
				username = args[2];
			}
			try {
				if (ip.equals("localhost") || ip.equals("127.0.0.1")) {
					String hostname = InetAddress.getLocalHost().getHostAddress();
					System.out.println(hostname);
				} else {
					System.out.println(ip);
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			String url = "rmi://" + ip + ":" + port + "/board";
			//Export the remote math object to the Java RMI runtime so that it
			//can receive incoming remote calls.
			//Because RemoteMath extends UnicastRemoteObject, this
			//is done automatically when the object is initialized.
			//
		    //RemoteMath obj = new RemoteMath();
			//IRemoteMath stub = (IRemoteMath) UnicastRemoteObject.exportObject(obj, 0);
			// 
			IRemoteBoard remoteBoard = new RemoteBoard();
            
            //Publish the remote object's stub in the registry under the name "Compute"
			LocateRegistry.createRegistry(Integer.parseInt(port));
            //Registry registry = LocateRegistry.getRegistry();
            Naming.bind(url, remoteBoard);
            
            //The server will continue running as long as there are remote objects exported into
            //the RMI runtime, to re	move remote objects from the
            //RMI runtime so that they can no longer accept RMI calls you can use:
           // UnicastRemoteObject.unexportObject(remoteMath, false);
            MyPaint paint = new MyPaint();
            paint.initFrame(true,remoteBoard,username);
            int usernum = 0;
            int messagenum = 0;
            remoteBoard.getUserList().add(username);
            while(true) {
            	//System.out.println(remoteMath.getCandidate().size());
            	if(remoteBoard.getCandidate().size() > 0) {
            		String user = remoteBoard.getCandidate().get(0);
            		int value=JOptionPane.showConfirmDialog(null, "Do you want "+user+" to join?", "Tips", 0);
                	if(value==0){remoteBoard.approve(user);}
                	if(value==1){remoteBoard.reject(user);}
            	}
            	
            	if(remoteBoard.getUserList().size() != usernum) {
            		usernum = remoteBoard.getUserList().size();
            		paint.updateUserList();
            	}
            	if(remoteBoard.getAllMessage().size() != messagenum) {
            		messagenum = remoteBoard.getAllMessage().size();
            		paint.updateMessageList();
            	}
            	long startTime = System.nanoTime();
            	while(true) {
            		if((System.nanoTime() - startTime) > 500) {
            			break;
            		}
            	}
            	paint.updateShape();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}