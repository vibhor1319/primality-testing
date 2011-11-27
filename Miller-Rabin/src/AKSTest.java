import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Vincent
 *
 */
public class AKSTest 
{

	/**
	 * Test method for {@link AKS#AKS(java.math.BigInteger)}.
	 */
	@Test
	public void testAKS() 
	{
		AKS a = new AKS(new BigInteger("10"));
		assertNotNull(a);
	}

	/**
	 * Test method for {@link AKS#isPrime()}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testIsPrime() 
	{
		assertFalse(new AKS(BigInteger.valueOf(8)).isPrime());
		assertFalse(new AKS(BigInteger.valueOf(10)).isPrime());
		assertTrue(new AKS(BigInteger.valueOf(13)).isPrime());
		assertFalse(new AKS(BigInteger.valueOf(32)).isPrime());
		assertFalse(new AKS(BigInteger.valueOf(44)).isPrime());
		assertTrue(new AKS(BigInteger.valueOf(61)).isPrime());
		assertTrue(new AKS(BigInteger.valueOf(89)).isPrime());
		assertFalse(new AKS(BigInteger.valueOf(90)).isPrime());
		assertFalse(new AKS(BigInteger.valueOf(94)).isPrime());

		BigInteger totest = new BigInteger("1519380").pow(32768).add(BigInteger.ONE);
		assertTrue(new AKS(totest).isPrime());
	}

	/**
	 * Test method for {@link AKS#totient(java.math.BigInteger)}.
	 */
	@Test
	public void testTotient() 
	{
		AKS a = new AKS(BigInteger.ZERO);
		
		// http://en.wikipedia.org/wiki/Totient
		assertEquals( 0, a.totient(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(4)) );
		assertEquals( 0, a.totient(BigInteger.valueOf(13)).compareTo(BigInteger.valueOf(12)) );
		assertEquals( 0, a.totient(BigInteger.valueOf(32)).compareTo(BigInteger.valueOf(16)) );
		assertEquals( 0, a.totient(BigInteger.valueOf(44)).compareTo(BigInteger.valueOf(20)) );
		assertEquals( 0, a.totient(BigInteger.valueOf(61)).compareTo(BigInteger.valueOf(60)) );
		assertEquals( 0, a.totient(BigInteger.valueOf(89)).compareTo(BigInteger.valueOf(88)) );
		assertEquals( 0, a.totient(BigInteger.valueOf(90)).compareTo(BigInteger.valueOf(24)) );
		assertEquals( 0, a.totient(BigInteger.valueOf(94)).compareTo(BigInteger.valueOf(46)) );
	}

	/**
	 * Test method for {@link AKS#multiplicativeOrder(java.math.BigInteger)}.
	 */
	@Test
	public void testMultiplicativeOrder() 
	{
		// http://en.wikipedia.org/wiki/Multiplicative_order
		// multiplicative order of 4 mod 7 is 3 because 4^3 mod 7 = 1
		AKS a = new AKS(BigInteger.valueOf(4));
		assertEquals( 0, a.multiplicativeOrder(BigInteger.valueOf(7)).compareTo(BigInteger.valueOf(3)) );
				
		// http://rosettacode.org/wiki/Multiplicative_order
		// multiplicative order of 37 mod 1000 is 100 because 37^100 mod 1000 = 1
		AKS b = new AKS(BigInteger.valueOf(37));
		assertEquals( 0, b.multiplicativeOrder(BigInteger.valueOf(1000)).compareTo(BigInteger.valueOf(100)) );		
	}

}
