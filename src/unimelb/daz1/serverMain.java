package unimelb.daz1;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by davidzd on 15/9/20.
 */
public class serverMain {

	public static void main(String[] args) {
		Arg arg = new Arg();
		CmdLineParser parser = new CmdLineParser(arg);
		try {
			parser.parseArgument(args);
		} catch(CmdLineException e){
			System.err.println(e.getMessage());
			parser.printUsage(System.err);
			return;
		}
		ServerSocket serverSocket = null;
		// TODO Auto-generated method stub
		Server server = new Server(arg.port);
		server.start();
	}

}
