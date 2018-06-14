package multithreaded_server.util;

import java.util.ArrayList;
import java.util.List;

/**
 * PortHandler
 * 
 * @since 0.3.2
 * @version 0.3.2
 * @author Peter Voigt
 *
 */
public class PortHandler {
	
	private static List<Integer> PORT_TABLE = new ArrayList<Integer>();
	private static List<Integer> RESERVED = new ArrayList<Integer>();
	private static List<Integer> BLACKLIST = new ArrayList<Integer>();
	private static List<Integer> WHITELIST = new ArrayList<Integer>();
	private static int[] PORT_RANGE = {49200, 65500};
	private static boolean USE_WHITELIST = false;
	
	/**
	 * This method marks a port as "used"
	 * 
	 * @param port Port
	 */
	public static void usePort(int port) {
		if(PORT_TABLE.contains(port) == false) {
			PORT_TABLE.add(port);
		}
	}
	
	/**
	 * This method marks a port as "free"
	 * 
	 * @param port Port
	 */
	public static void removePortFromUsed(int port) {
		if(PORT_TABLE.contains(port) == true) {
			PORT_TABLE.remove(port);
		}
	}
	
	/**
	 * This method adds a port to the blacklist
	 * 
	 * @param port Port
	 */
	public static void addPortToBlacklist(int port) {
		if(BLACKLIST.contains(port) == false) {
			BLACKLIST.add(port);
		}
	}
	
	/**
	 * This method removes a port from the blacklist
	 * 
	 * @param port Port
	 */
	public static void removePortFromBlacklist(int port) {
		if(BLACKLIST.contains(port) == true) {
			BLACKLIST.remove(port);
		}
	}
	
	/**
	 * This method adds a port to the whitelist
	 * 
	 * @param port Port
	 */
	public static void addPortToWhitelist(int port) {
		if(WHITELIST.contains(port) == false) {
			WHITELIST.add(port);
		}
	}
	
	/**
	 * This method removes a port from the whitelist
	 * 
	 * @param port Port
	 */
	public static void removePortFromWhitelist(int port) {
		if(WHITELIST.contains(port) == true) {
			WHITELIST.remove(port);
		}
	}
	
	/**
	 * This method sets the port range for the dynamic port distribution
	 * 
	 * @param from Start of the port range
	 * @param to End of the port range
	 */
	public static void setPortRange(int from, int to) {
		PORT_RANGE[0] = from;
		PORT_RANGE[1] = to;
	}
	
	/**
	 * This method marks a port as "reserved" so the port handler won't return it when a program requests a port from the port range
	 * 
	 * @param port Port
	 */
	public static void reservePort(int port) {
		if(RESERVED.contains(port) == false) {
			RESERVED.add(port);
		}
	}
	
	/**
	 * This method marks a reserved port as "free"
	 * NOTE: Use for reserved ports only
	 * 
	 * @param port Port
	 */
	public static void freePort(int port) {
		if(RESERVED.contains(port) == true) {
			RESERVED.remove(port);
		}
	}
	
	/**
	 * This method marks every reserved port as "free"
	 * NOTE: Use for reserved ports only
	 */
	public static void freeAllPorts() {
		RESERVED.clear();
	}
	
	/**
	 * This method returns a free port within the set range
	 * If the whitelist is enabled it will only return free ports from the whitelist
	 * As long as the whitelist is enabled the blacklist is automatically disabled
	 * 
	 * @return A free Port
	 */
	public static int getDynamicPort() {
		if(USE_WHITELIST == true) {
			for(int i=0; i<WHITELIST.size(); i++) {
				if(PORT_TABLE.contains(WHITELIST.get(i)) == false && RESERVED.contains(WHITELIST.get(i)) == false) {
					return WHITELIST.get(i);
				}
			}
			System.out.println("No free port available!");
			return 0;
		} else {
			for(int i=PORT_RANGE[0]; i<PORT_RANGE[1]; i++) {
				if(PORT_TABLE.contains(i) == false && RESERVED.contains(i) == false && BLACKLIST.contains(i) == false) {
					return i;
				}
			}
			System.out.println("No free port available!");
			return 0;
		}
	}
	
	/**
	 * This method checks if a given port is free
	 * 
	 * @param port Port
	 * @return TRUE if the port is free; FALSE if the port is in use
	 */
	public static boolean isPortFree(int port) {
		if(PORT_TABLE.contains(port) == false && RESERVED.contains(port) == false) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks if a given port is blacklisted
	 * 
	 * @param port Port
	 * @return TRUE if the port is blacklisted; FALSE if the port is not blacklisted
	 */
	public static boolean isPortBlacklisted(int port) {
		if(BLACKLIST.contains(port) == false) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks if a given port is whitelisted
	 * 
	 * @param port Port
	 * @return TRUE if the port is whitelisted; FALSE if the port is not whitelisted
	 */
	public static boolean isPortWhitelisted(int port) {
		if(WHITELIST.contains(port) == false) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks if a given port is reserved
	 * 
	 * @param port Port
	 * @return TRUE if the port is reserved; FALSE if the port is not reserved
	 */
	public static boolean isPortReserved(int port) {
		if(RESERVED.contains(port) == false) {
			return true;
		}
		return false;
	}

}
