package eecs2030.lab2;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;
import org.junit.Rule;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DominoTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(1);
	
	
	@Test
	public void test01_field() {
		try {
			List<Field> fields = Arrays.asList(Domino.class.getDeclaredFields());
			if (!fields.stream().map(Field::getName).anyMatch("MIN_VALUE"::equals)) {
				fail("the field MIN_VALUE is missing");
			}
			for (Field f : fields) {
				String name = f.getName();
				if (name.equals("MIN_VALUE")) {
					int mod = f.getModifiers();
					if (!(Modifier.isPublic(mod) &&
							Modifier.isFinal(mod) && 
							Modifier.isStatic(mod))) {
						fail("field MIN_VALUE is not public or not final or not static");
					}
					assertEquals("field MIN_VALUE has the wrong value",
							0, f.getInt(null));
				}
			}
		}
		catch (Exception x) {
			fail("exception occurred trying to get the fields of this class");
		}	
	}
	
	@Test
	public void test02_field() {
		try {
			List<Field> fields = Arrays.asList(Domino.class.getDeclaredFields());
			if (!fields.stream().map(Field::getName).anyMatch("MAX_VALUE"::equals)) {
				fail("the field MAX_VALUE is missing");
			}
			for (Field f : fields) {
				String name = f.getName();
				if (name.equals("MAX_VALUE")) {
					int mod = f.getModifiers();
					if (!(Modifier.isPublic(mod) &&
							Modifier.isFinal(mod) && 
							Modifier.isStatic(mod))) {
						fail("field MAX_VALUE is not public or not final or not static");
					}
					assertEquals("field MAX_VALUE has the wrong value",
							6, f.getInt(null));
				}
			}
		}
		catch (Exception x) {
			fail("exception occurred trying to get the fields of this class");
		}	
	}
	
	@Test
	public void test03_fields() {
		try {
			List<Field> fields = Arrays.asList(Domino.class.getDeclaredFields());
			
			int nNotStatic = 0;
			for (Field f : fields) {
				int mod = f.getModifiers();
				if (Modifier.isStatic(mod)) {
					;
				}
				else {
					nNotStatic++;
				}
			}
			assertTrue("there should be at least 2 non-static fields", nNotStatic >= 2);
		}
		catch (Exception x) {
			fail("exception occurred trying to get the fields of this class");
		}	
	}

	
	@Test
	public void test04_noArgCtor() {
		Domino d = new Domino();
		assertEquals(0, d.getValue1());
		assertEquals(0, d.getValue2());
	}
	
	@Test
	public void test05_ctor() {
		for (int i = 0; i <= 6; i++) {
			for (int j = 0; j <= 6; j++) {
				Domino d = new Domino(j, i);
				new Domino(i, j);
				int got1 = d.getValue1();
				int got2 = d.getValue2();
				if (got1 == i) {
					assertEquals(j, got2);
				}
				else {
					assertEquals(j, got1);
					assertEquals(i, got2);
				}
			}
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test06_ctorThrows() {
		new Domino(-1, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test07_ctorThrows() {
		new Domino(3, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test08_ctorThrows() {
		new Domino(7, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test09_ctorThrows() {
		new Domino(5, 7);
	}
	
	@Test
	public void test10_copyCtor() {
		for (int i = 0; i <= 6; i++) {
			for (int j = 0; j <= 6; j++) {
				Domino orig = new Domino(j, i);
				Domino d = new Domino(orig);
				new Domino(i, j);
				int got1 = d.getValue1();
				int got2 = d.getValue2();
				if (got1 == i) {
					assertEquals(j, got2);
				}
				else {
					assertEquals(j, got1);
					assertEquals(i, got2);
				}
			}
		}
	}
	
	@Test
	public void test11_isValueOK() {
		for (int i = 0; i <= 6; i++) {
			assertTrue(Domino.isValueOK(i));
		}
	}
	
	@Test
	public void test12_isValueOK() {
		for (int i = -5; i <= 15; i++) {
			if (i < 0 || i > 6) {
				assertFalse(Domino.isValueOK(i));
			}
		}
	}
	
	@Test
	public void test13_getSmallerValue() {
		for (int v1 = 0; v1 <= 6; v1++) {
			for (int v2 = v1; v2 <= 6; v2++) {
				Domino d = new Domino(v1, v2);
				assertEquals("getSmallerValue returned the larger value", 
						v1, d.getSmallerValue());
				
				d = new Domino(v2, v1);
				assertEquals("getSmallerValue returned the larger value", 
						v1, d.getSmallerValue());
			}
		}
	}
	
	@Test
	public void test14_getLargerValue() {
		for (int v1 = 0; v1 <= 6; v1++) {
			for (int v2 = v1; v2 <= 6; v2++) {
				Domino d = new Domino(v1, v2);
				assertEquals("getLargerValue returned the smaller value", 
						v2, d.getLargerValue());
				
				d = new Domino(v2, v1);
				assertEquals("getLargerValue returned the smaller value", 
						v2, d.getLargerValue());
			}
		}
	}
	
	
	
	@Test
	public void test15_allDominoes() {
		List<Domino> t = Domino.allDominoes();
		assertEquals("wrong number of dominoes",
				28, t.size());
		
		Set<String> exp = new HashSet<>();
		for (int v1 = 0; v1 <= 6; v1++) {
			for (int v2 = v1; v2 <= 6; v2++) {
				exp.add(v1 + "," + v2);
			}
		}
		
		for (Domino d : t) {
			int v1 = Math.min(d.getValue1(), d.getValue2());
			int v2 = Math.max(d.getValue1(), d.getValue2());
			String s = v1 + "," + v2;
			if (exp.contains(s)) {
				exp.remove(s);
			}
			else {
				fail(s + " was unexpected in allDominoes");
			}
		}
	}
	
	@Test
	public void test16_matches() {
		for (int v1 = 0; v1 <= 6; v1++) {
			for (int v2 = 0; v2 <= 6; v2++) {
				Domino d = new Domino(v1, v2);
				for (int u1 = 0; u1 <= 6; u1++) {
					Domino x = new Domino(u1, v2);
					assertTrue(d.matches(x));
					assertTrue(x.matches(d));
				}
				for (int u2 = 0; u2 <= 6; u2++) {
					Domino x = new Domino(v1, u2);
					assertTrue(d.matches(x));
					assertTrue(x.matches(d));
				}
			}
		}
	}
	
	@Test
	public void test17_matches() {
		for (int v1 = 0; v1 <= 6; v1++) {
			for (int v2 = 0; v2 <= 6; v2++) {
				Domino d = new Domino(v1, v2);
				for (int u1 = 0; u1 <= 6; u1++) {
					for (int u2 = 0; u2 <= 6; u2++) {
						if (u1 != v1 && u1 != v2 && u2 != v1 && u2 != v2) {
							Domino x = new Domino(u1, u2);
							assertFalse(d.matches(x));
							assertFalse(x.matches(d));
						}
					}
				}
			}
		}
	}
	
	
	@Test
	public void test18_equals() {
		Domino d = new Domino(2, 5);
		assertFalse("d.equals(null) returned true",
				d.equals(null));
	}
	
	@Test
	public void test19_equals() {
		Domino d = new Domino(2, 5);
		assertTrue("d.equals(d) returned false",
				d.equals(d));
	}
	
	@Test
	public void test20_equals() {
		for (int v1 = 0; v1 <= 6; v1++) {
			for (int v2 = 0; v2 <= 6; v2++) {
				Domino d1 = new Domino(v1, v2);
				Domino d2 = new Domino(v1, v2);
				Domino d3 = new Domino(v2, v1);
				assertTrue(d1.equals(d2));
				assertTrue(d2.equals(d1));
				assertTrue(d1.equals(d3));
				assertTrue(d3.equals(d1));
			}
		}
	}
	
	@Test
	public void test21_equals() {
		for (int v1 = 0; v1 <= 6; v1++) {
			for (int v2 = 0; v2 <= 6; v2++) {
				Domino d = new Domino(v1, v2);
				for (int u1 = 0; u1 <= 6; u1++) {
					for (int u2 = 0; u2 <= 6; u2++) {
						if (u1 != v1 && u1 != v2) {
							Domino x = new Domino(u1, u2);
							Domino y = new Domino(u2, u1);
							assertFalse(d.equals(x));
							assertFalse(x.equals(d));
							assertFalse(d.equals(y));
							assertFalse(y.equals(d));
						}
					}
				}
			}
		}
	}
	
	@Test
	public void test22_hashCode() {
		for (int v1 = 0; v1 <= 6; v1++) {
			for (int v2 = v1; v2 <= 6; v2++) {
				Domino d = new Domino(v1, v2);
				int exp = v1 + 11 * v2;
				assertEquals(exp, d.hashCode());
				d = new Domino(v2, v1);
				assertEquals(exp, d.hashCode());
			}
		}
	}
	
	@Test
	public void test23_toString() {
		for (int v1 = 0; v1 <= 6; v1++) {
			for (int v2 = v1; v2 <= 6; v2++) {
				Domino d = new Domino(v1, v2);
				String exp = "[" + v1 + " : " + v2 + "]";
				assertEquals(exp, d.toString());
				d = new Domino(v2, v1);
				assertEquals(exp, d.toString());
			}
		}
	}
}
