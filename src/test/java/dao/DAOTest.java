/**
 * TestDAO.java
 *
 * 	Version:
 *	$Id:$
 *
 *	Revisions:
 *	$Log:$
 */

/**
 *
 *	@author	jimiford
 */
package dao;

import org.junit.Test;




/**
 * @author jimiford
 *
 */
public class DAOTest {

	public static final String TEST_FOLDER = "test_database/";
	
	@Test
	public void testProductSerialization() {
		/*
		try {
			Product one = new Product("Tofu", TYPE.MISC, 789L);
			DAOManager.<Product>freeze(one, TEST_FOLDER + one.getName());
			assertTrue("Deserialized product should be equal to Tofu.", 
					DAOManager.<Product>thaw(TEST_FOLDER + 
							   one.getName()).equals(one));
		} catch (Exception e) {
			fail("Exception was caught.\n" + e.getMessage());
		}*/
	}
	
	@Test
	public void testUserSerialization() {
		/*try {
			
			User one = new User("Bobby", pass1, Type.MANAGER);
			DAOManager.<User>freeze(one, TEST_FOLDER + one.getName());
			assertEquals("Deserialized product should be equal to Tofu.", 
					DAOManager.<User>thaw(TEST_FOLDER + one.getName()), one);
		} catch (Exception e) {
			fail("Exception was caught.\n" + e.getMessage());
		}*/
	}
	
	@Test
	public void testHashMapSerialization() {
		/*try {
			this.map = new HashMap<String, User>();
			User one = new User("Bobby", pass1, Type.MANAGER);
			User two = new User("bbd3982", pass1, Type.RESTOCKER);
			User three = new User("Bobby", pass2, Type.MANAGER);
			map.put(one.getName(), one);
			map.put(two.getName(), two);
			map.put(three.getName(), three);
			DAOManager.<HashMap<String, User>>freeze(map, TEST_FOLDER 
					        + mapfile);
			HashMap<String, User> map2 = 
					DAOManager.<HashMap<String, User>>thaw(TEST_FOLDER 
							+ mapfile);
			assertEquals("Size of map should be 2.", 2, map2.size());
		} catch (Exception e) {
			fail("Exception was caught.\n" + e.getMessage());
		}*/
	}
	
}
