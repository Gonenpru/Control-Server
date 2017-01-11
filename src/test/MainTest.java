package test;

import org.junit.Before;
import org.junit.Test;

import main.ControlServer;

public class MainTest {

	private ControlServer main;
	
	@Before
	public void setUp(){
		main = new ControlServer();
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void test_main(){
		main.main(null);
	}
}
