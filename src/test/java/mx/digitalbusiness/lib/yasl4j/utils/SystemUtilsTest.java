package mx.digitalbusiness.lib.yasl4j.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SystemUtilsTest {

	@Test
	public void testGetOS_GivenLinuxOS_MustReturnGNULinux() {
		String expResult = "GNU/Linux";
		String result = SystemUtils.getOS("Linux");
		assertEquals(expResult, result);
	}
	
	@Test
	public void testGetOS_GivenUnixOS_MustReturnUnix() {
		String expResult = "Unix";
		String result = SystemUtils.getOS("UNIX");
		assertEquals(expResult, result);
	}
	
	@Test
	public void testGetOS_GivenWindowsOS_MustReturnMicrosoftWindows() {
		String expResult = "Microsoft Windows";
		String result = SystemUtils.getOS("Windows 8.1");
		assertEquals(expResult, result);
	}
	
	@Test
	public void testGetOS_GivenMacOS_MustReturnMacOS() {
		String expResult = "Mac OS";
		String result = SystemUtils.getOS("Machintosh");
		assertEquals(expResult, result);
	}
	
	@Test
	public void testGetOS_GivenUnknownOS_MustReturnUnknown() {
		String expResult = "Unknown";
		String result = SystemUtils.getOS("Solaris");
		assertEquals(expResult, result);
	}
	
	@Test
	public void testReadResourceFile() throws Exception {
		String[] expResult = new String[] {
				"Hello world!"
		};
		String[] result = SystemUtils.readResourceFile("test-res-file.txt");
		assertArrayEquals(expResult, result);
	}

}
