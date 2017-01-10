package ru.coutvv.hibapp.util;

import org.hibernate.Session;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestSessionUtil {

	@Test
	public void testSession() {
		
		try(Session session = SessionUtil.getSession();) {
			assertNotNull(session);
		}
	}
}
