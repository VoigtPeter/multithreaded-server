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
	
	public static void usePort(int port) {
		if(PORT_TABLE.contains(port) == false) {
			PORT_TABLE.add(port);
		}
	}
	
	public static void removePortFromUsed(int port) {
		if(PORT_TABLE.contains(port) == true) {
			PORT_TABLE.remove(port);
		}
	}
	
	public static void addPortToBlacklist(int port) {
		if(BLACKLIST.contains(port) == false) {
			BLACKLIST.add(port);
		}
	}
	
	public static void removePortFromBlacklist(int port) {
		if(BLACKLIST.contains(port) == true) {
			BLACKLIST.remove(port);
		}
	}
	
	public static void addPortToWhitelist(int port) {
		if(WHITELIST.contains(port) == false) {
			WHITELIST.add(port);
		}
	}
	
	public static void removePortFromWhitelist(int port) {
		if(WHITELIST.contains(port) == true) {
			WHITELIST.remove(port);
		}
	}
	
	public static void setPortRange(int from, int to) {
		PORT_RANGE[0] = from;
		PORT_RANGE[1] = to;
	}
	
	public static void reservePort(int port) {
		if(RESERVED.contains(port) == false) {
			RESERVED.add(port);
		}
	}
	
	public static void freePort(int port) {
		if(RESERVED.contains(port) == true) {
			RESERVED.remove(port);
		}
	}
	
	public static void freeAllPorts() {
		RESERVED.clear();
	}
	
	public static int getDynamicPort() {
		if(USE_WHITELIST == true) {
			for(int i=0; i<WHITELIST.size(); i++) {
				if(PORT_TABLE.contains(i) == false && RESERVED.contains(i) == false) {
					return WHITELIST.get(i);
				}
			}
			System.out.println("No free port available!");
			return 0;
		} else {
			for(int i=PORT_RANGE[0]; i<PORT_RANGE[1]; i++) {
				
			}
			System.out.println("No free port available!");
			return 0;
		}
	}
	
	public static boolean isPortFree(int port) {
		if(PORT_TABLE.contains(port) == false) {
			return true;
		}
		return false;
	}
	
	public static boolean isPortBlacklisted(int port) {
		if(BLACKLIST.contains(port) == false) {
			return true;
		}
		return false;
	}
	
	public static boolean isPortWhitelisted(int port) {
		if(WHITELIST.contains(port) == false) {
			return true;
		}
		return false;
	}
	
	public static boolean isPortReserved(int port) {
		if(RESERVED.contains(port) == false) {
			return true;
		}
		return false;
	}

}
