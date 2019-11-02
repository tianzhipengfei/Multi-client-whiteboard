import java.awt.Color;
import java.io.EOFException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

/**
 * This class retrieves a reference to the remote object from the RMI registry. It
 * invokes the methods on the remote object as if it was a local object of the type of the 
 * remote interface.
 *
 */
public class Client {
	public static void main(String[] args) {
		String ip = "localhost";
		String port = "10999";
		String username = "jimmy";
		MyPaint paint;
		if(args.length == 3) {
			ip = args[0];
			port = args[1];
			username = args[2];
		}
		String url = "rmi://" + ip + ":" + port + "/board";
		try {
			//Connect to the rmiregistry that is running on localhost
			//Registry registry = LocateRegistry.getRegistry(hostName);
			//Retrieve the stub/proxy for the remote math object from the registry
			IRemoteBoard remoteBoard = (IRemoteBoard) Naming.lookup(url);
			if(!remoteBoard.join(username)) {
				System.out.println("dupliate user name or rejected before");System.exit(0);
			}
			paint = new MyPaint();
			try {
				while(!remoteBoard.approved(username)) {
					if(remoteBoard.inRejectList(username)) {
						System.out.println("rejectde by super user"); System.exit(0);
					}
				}
				int usernum = remoteBoard.getUserList().size();
				int messagenum = remoteBoard.getAllMessage().size();
				int shapenum = remoteBoard.getAllShape().size();
	            paint.initFrame(false,remoteBoard,username);
	            paint.updateUserList();
	            paint.updateMessageList();
				System.out.println("accpeted by super user");
				while(true) {
					if(!remoteBoard.inUserList(username)) {
						System.out.println(username +" exited");System.exit(0);
					}
					int tempNum = remoteBoard.getUserList().size();
					if(tempNum != usernum) {
	            		usernum = tempNum;
	            		paint.updateUserList();
	            	}
					int tempNum1 = remoteBoard.getMessageSize();
					if(tempNum1 != messagenum) {
						messagenum = tempNum1;
						paint.updateMessageList();
					}
					long startTime = System.nanoTime();
	            	while(true) {
	            		if((System.nanoTime() - startTime) > 1000) {
	            			break;
	            		}
	            	}
	            	paint.updateShape();
				}
			}
			catch(Exception e) {
				 JOptionPane.showMessageDialog(null, "Admin closed server.");
				e.printStackTrace();
				System.exit(0);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}