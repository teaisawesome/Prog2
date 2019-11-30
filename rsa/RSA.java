package rsa;
import java.math.*;
import java.util.*;
import java.io.*;


class KulcsPar
{
	BigInteger d, e, m;
	
	public KulcsPar()
	{
		int meretBitekben = 700 * (int) (Math.log((double)10) / Math.log((double)2));
		
		BigInteger p = new BigInteger(meretBitekben, 100, new Random());
		
		BigInteger q = new BigInteger(meretBitekben, 100, new Random());
		
		m = p.multiply(q);
		
		BigInteger z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		
		do
		{
			do
			{
				d = new java.math.BigInteger(meretBitekben, new Random());
			}
			while(d.equals(BigInteger.ONE));
				
		}
		while(!z.gcd(d).equals(BigInteger.ONE));
		
		e = d.modInverse(z);
	}
}
class Karakterek
{
	private String encrypted;
	private char karakter = ' ';
	private int gyakorisag = 0;
	
	public Karakterek(String str, char k)
	{
		encrypted = str;
		karakter = k;
	}
	
	public Karakterek(String str)
	{
		encrypted = str;
	}
	
	public void setEncrypted(String str)
	{
		encrypted = str;
	}
	
	public String getEncrypted()
	{
		return encrypted;
	}
	
	public void setKarakter(char k)
	{
		karakter = k;
	}
	
	public char getKarakter()
	{
		return karakter;
	}
	
	public void increment()
	{
		gyakorisag += 1;
	}
	
	public int getGyak()
	{
		return gyakorisag;
	}
}


public class RSA
{

	public static void main(String[] args) throws IOException
	{
		
		KulcsPar jSzereplo = new KulcsPar();
		
		String tisztaSzoveg = "Jurassic Park is a 1993 American science fiction adventure film directed by Steven Spielberg and produced by Kathleen Kennedy and Gerald R. Molen.";
		
		System.out.println("Eredeti szöveg: " + tisztaSzoveg);
		System.out.print("Visszafejtett szöveg: ");
		
		//byte[] buffer = tisztaSzoveg.getBytes();
		
		//BigInteger[] titkos = new BigInteger[buffer.length];
		
		/*for(int i = 0; i < titkos.length; ++i)
		{
			titkos[i] = new BigInteger(new byte[] {buffer[i]});
			titkos[i] = titkos[i].modPow(jSzereplo.e, jSzereplo.m);
		}
		
		for(int i = 0; i < titkos.length; ++i)
		{
			titkos[i] = titkos[i].modPow(jSzereplo.d, jSzereplo.m);
			buffer[i] = titkos[i].byteValue();
		}*/
		
		PrintWriter writer = new PrintWriter("output.txt");
		for(int idx = 0; idx < tisztaSzoveg.length(); ++idx)
		{
			Character tisztaszoveg = tisztaSzoveg.charAt(idx);
			tisztaszoveg =  Character.toLowerCase(tisztaszoveg);
			byte[] buffer = tisztaszoveg.toString().getBytes();
			BigInteger[] titkos = new BigInteger[buffer.length];
			byte[] output = new byte[buffer.length];
			
			for(int i = 0; i < titkos.length; ++i)
			{
				titkos[i] = new BigInteger(new byte[] {buffer[i]});
				titkos[i] = titkos[i].modPow(jSzereplo.e, jSzereplo.m);
				
				output[i] = titkos[i].byteValue();
				writer.print(titkos[i]);
			} 
			writer.println();
		}
		writer.close();
		
		
		
		BufferedReader inputStream = new BufferedReader(new FileReader("output.txt"));
		int lines = 0;
		String line[] = new String[100000];
		
		while((line[lines] = inputStream.readLine()) != null)
		{
			lines++;
		}
		
		inputStream.close();
		
		Karakterek kar[]  = new Karakterek[1000];
	
		boolean found;
		kar[0] = new Karakterek(line[0]);
		int count = 1;
		
		for(int i = 1; i < lines; i++)
		{
			found = false;
			for(int j = 0; j < count; j++)
			{
				if(kar[j].getEncrypted().equals(line[i]))
				{
					kar[j].increment();
					found = true;
					break;
				}
			}
			if(!found)
			{
				kar[count] = new Karakterek(line[i]);
				count++;
			}
		}
		
		for(int i = 0; i < count; i++)
		{
			for(int j = i + 1; j < count; j++)
			{
				if(kar[i].getGyak() < kar[j].getGyak())
				{
					Karakterek x = kar[i];
					kar[i] = kar[j];
					kar[j] = x;
				}
			}
		}
		
		BufferedReader f = new BufferedReader(new FileReader("gyakorisag.txt"));
		
		char[] karakter = new char[31];
		//char[] karakter = new char[69];
		int karCount = 0;
		String k;
		
		while((k = f.readLine()) != null)
		{
			karakter[karCount] = k.charAt(0);
			karCount++;
		}
		f.close();
		
		for(int i = 0; i < count; i++)
		{
			kar[i].setKarakter(karakter[i]);
		}
		
		for(int i = 0; i < lines; i++)
		{
			for(int j = 0; j < count; j++)
			{
				if(line[i].equals(kar[j].getEncrypted()))
				{
					System.out.print(kar[j].getKarakter());
				}
			}
		}
		System.out.println();
	}
}
