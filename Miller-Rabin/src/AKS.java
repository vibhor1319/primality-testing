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
		// smallest positive integer k with	n^k ≡ 1 (mod r).
		double log = this.log();
		double logSquared = log*log;
		int k = 1;
		BigInteger r = BigInteger.ONE;
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
		
		// For a = 1 to  do
		// if (X+a)n≠ Xn+a (mod Xr − 1,n), output composite;

		n_isprime = true;
	    return n_isprime;
	}


	int multiplicativeOrder(BigInteger r)
	{
		int k = 0;
		int result;
		
		do
		{
			k++;
			result = n.pow(k).mod(r).intValue();
		}
		while( result != 1 );
		
		return k;
	}
	
}
