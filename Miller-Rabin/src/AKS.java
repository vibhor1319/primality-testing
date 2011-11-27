import java.math.BigInteger;

/**
 * 
 */

/**
 * @author Vincent
 *
 */
public class AKS 
{

	BigInteger n;
	boolean n_isprime;
	BigInteger factor;
	
	public AKS(BigInteger n)
	{
		this.n = n;
		
	}
	
	public boolean isPrime() 
	{
		// If ( n = a^b for a in natural numbers and b > 1), output COMPOSITE
		BigInteger a = new BigInteger("2");
		BigInteger aSquared = a.pow(2);;
		
		while (aSquared.compareTo(n) < 0)
		{
			BigInteger result;
			aSquared = a.pow(2);

			int power = 2;
			int comparison;
			
			do
			{
				result = a.pow(power);
				power++;
				comparison = n.compareTo(result);
			}
			while( comparison <= 0 && power < Integer.MAX_VALUE );
			
			if( comparison == 0 )
			{
				factor = a;
				n_isprime = false;
				return n_isprime;
			}
		}
		
		// Find the smallest r such that o_r(n) > log^2 n
		// o_r(n) is the multiplicative order of n modulo r
		// the multiplicative order of n modulo r is the 
		// smallest positive integer k with	n^k = 1 (mod r).
		double log = this.log();
		double logSquared = log*log;
		int k = 1;
		BigInteger r = new BigInteger("2");
		do
		{
			r.add(BigInteger.ONE);
			k = multiplicativeOrder(r);
		}
		while( k < logSquared );
		
		// If 1 < (a,n) < n for some a <= r, output COMPOSITE
		for( BigInteger i = BigInteger.valueOf(2); i.compareTo(r) <= 0; i.add(BigInteger.ONE) )
		{
			BigInteger gcd = n.gcd(a);
			if ( gcd.compareTo(BigInteger.ONE) > 0 && gcd.compareTo(n) < 0 )
			{
				factor = a;
				n_isprime = false;
				return false;
			}
		}
		
		// If n <= r, output PRIME
		if( n.compareTo(r) <= 0 )
		{
			n_isprime = true;
			return true;
		}

		
		// For i = 1 to sqrt(totient)log(n) do
		// if (X+i)^n <> X^n + i (mod X^r - 1,n), output composite;

		// sqrt(totient)log(n)
		int limit = (int) (Math.sqrt(totient(r).doubleValue()) * this.log());
		// X^r - 1
		Poly modPoly = new Poly(BigInteger.ONE, r.intValue()).minus(new Poly(BigInteger.ONE,0));
		// X^n (mod X^r - 1, n)
		Poly partialOutcome = new Poly(BigInteger.ONE, 1).modPow(n, modPoly, n);
		for( int i = 1; i <= limit; i++ )
		{
			Poly polyI = new Poly(new BigInteger(Integer.toString(i)),0);
			// X^n + i (mod X^r -1, n)
			Poly outcome = partialOutcome.plus(polyI);
			Poly p = new Poly(BigInteger.ONE,1).plus(polyI).modPow(n, modPoly, n);
			if( !outcome.equals(p) )
			{
				factor = new BigInteger(Integer.toString(i));
				n_isprime = false;
				return n_isprime;
			}
		}
		
		n_isprime = true;
	    return n_isprime;
	}

	
	/***
	 * Calculate the totient of a BigInteger r
	 * Try every integer less than r and see if it divides r
	 * If not, increase the totient by one
	 * 
	 * @param r BigInteger to calculate the totient of
	 * @return phi(r)--number of integers less than r that are coprime
	 */
	BigInteger totient(BigInteger r)
	{
		BigInteger result = BigInteger.ZERO;
		
		for(BigInteger i = BigInteger.ZERO; i.compareTo(r) < 0; i = i.add(BigInteger.ONE))
		{
			if( r.gcd(i).compareTo(BigInteger.ONE) == 0 )
				result = result.add(BigInteger.ONE);
		}
		
		return result;
	}
	
	
	// TODO test this method
	int multiplicativeOrder(BigInteger r)
	{
		int k = 0;
		int result;
		
		do
		{
			k++;
			result = n.modPow(new BigInteger(Long.toString(k)),r).intValue();
		}
		while( result != 1 );
		
		return k;
	}
	
	
	// log base 2
	double log()
	{
		// from http://world.std.com/~reinhold/BigNumCalcSource/BigNumCalc.java
		BigInteger b;
		
	    int temp = n.bitLength() - 1000;
	    if (temp > 0) 
	    {
	    	b=n.shiftRight(temp); 
	        return (Math.log(b.doubleValue()) + temp*Math.log(2));
	    }
	    else 
	    	return (Math.log(n.doubleValue()));
	}
	
	
	public BigInteger getFactor()
	{
		return factor;
	}
	
}
