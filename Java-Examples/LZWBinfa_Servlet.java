package drob.lzwbinfa.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Binfa
 */
@WebServlet("/Binfa")
public class Binfa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Binfa() {
        super();
        // TODO Auto-generated constructor stub
        
        fa = gyoker;
    }
    
    /* ****************************************************** */
    
    public void egyBitFeldolg(char b)
	{
		if(b == '0')
		{
			
			if(fa.egyesGyermek() == null)
			{
				Csomopont uj = new Csomopont('0');
				fa.ujNullasGyermek(uj);
				fa = gyoker;
			}
			else
			{
				fa = fa.nullasGyermek();
			}
		}
		else
		{
			if(fa.egyesGyermek() == null)
			{
				Csomopont uj = new Csomopont('1');
				fa.ujEgyesGyermek(uj);
				fa = gyoker;
			}
			else
			{
				fa = fa.egyesGyermek();
			}
		}
	}
	
	public void kiir()
	{
		melyseg = 0;
		kiir(gyoker, new java.io.PrintWriter(System.out));
	}
	
	public void kiir(java.io.PrintWriter os)
	{
		melyseg = 0;
		kiir(gyoker, os);
	}
	
	class Csomopont
	{
		public Csomopont(char betu)
		{
			this.betu = betu;
			balNulla = null;
			jobbEgy = null;
		};
		
		public Csomopont nullasGyermek()
		{
			return balNulla;
		}
		
		public Csomopont egyesGyermek()
		{
			return jobbEgy;
		}
		
		public void ujNullasGyermek(Csomopont gy)
		{
			balNulla = gy;
		}
		
		public void ujEgyesGyermek(Csomopont gy)
		{
			jobbEgy = gy;
		}
		
		public char getBetu()
		{
			return betu;
		}
		private char betu;
		
		private Csomopont balNulla = null;
		private Csomopont jobbEgy = null;
		
	};
	
	private Csomopont fa = null;
	
	private int melyseg, atlagosszeg, atlagdb;
	private double szorasosszeg;
	
	public void kiir(Csomopont elem, java.io.PrintWriter os)
	{
		if(elem != null)
		{
			++melyseg;
			kiir(elem.egyesGyermek(), os);
			for (int i = 0; i < melyseg; i++)
			{
				os.print("---");
			}
			os.print(elem.getBetu());
			os.print("(");
			os.print(melyseg - 1);
			os.println(")");
			kiir(elem.nullasGyermek(), os);
			--melyseg;
		}
	}
	
	protected Csomopont gyoker = new Csomopont('/');
	int maxMelyseg;
	double atlag, szoras;
	
	public int getMelyseg()
	{
		melyseg = maxMelyseg = 0;
		rmelyseg(gyoker);
		return maxMelyseg - 1;
	}
	
	public void rmelyseg(Csomopont elem)
	{
		if(elem != null)
		{
			++melyseg;
			if(melyseg > maxMelyseg)
			{
				maxMelyseg = melyseg;
			}
			rmelyseg(elem.egyesGyermek());
			rmelyseg(elem.nullasGyermek());
			--melyseg;
		}
	}
	
	public double getAtlag()
	{
		melyseg = atlagosszeg = atlagdb = 0;
		ratlag(gyoker);
		atlag = ((double) atlagosszeg) / atlagdb;
		return atlag;
	}
	
	public double getSzoras()
	{
		atlag = getAtlag();
		szorasosszeg = 0.0;
		melyseg = atlagdb = 0;
		
		rszoras(gyoker);
		
		if(atlagdb - 1 > 0)
		{
			szoras = Math.sqrt(szorasosszeg / (atlagdb - 1));
		}
		else
		{
			szoras = Math.sqrt(szorasosszeg);
		}		
		
		return szoras;
	}
	
	public void ratlag(Csomopont elem)
	{
		if(elem != null)
		{
			++melyseg;
			ratlag(elem.egyesGyermek());
			ratlag(elem.nullasGyermek());
			--melyseg;
			if(elem.egyesGyermek() == null && elem.nullasGyermek() == null)
			{
				++atlagdb;
				atlagosszeg += melyseg;
			}
		}
	}
	
	public void rszoras(Csomopont elem)
	{
		if(elem != null)
		{
			++melyseg;
			rszoras(elem.egyesGyermek());
			rszoras(elem.nullasGyermek());
			--melyseg;
			if(elem.egyesGyermek() == null && elem.nullasGyermek() == null)
			{
				++atlagdb;
				szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
			}
		}
	}
    
    
    /* ****************************************************** */
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String parameter = request.getParameter("param");
		
		byte[] b = parameter.getBytes();
		
		Binfa binFa = new Binfa();
		
		java.io.PrintWriter kiFile = new java.io.PrintWriter( new java.io.BufferedWriter( new java.io.FileWriter("output.txt")));
		
		boolean kommentben = false;
		
		for(int h = 0; h < b.length; ++h)
		{
			if(b[h] == 0x3e)
			{
				kommentben = true;
				continue;
			}
			
			if(b[h] == 0x0a)
			{
				kommentben = false;
				continue;
			}
			
			if(kommentben)
			{
				continue;
			}
			
			if(b[h] == 0x4e)
			{
				continue;
			}
			
			for (int i = 0; i < 8; i++)
			{
				if((b[h] & 0x80) != 0)
				{
					binFa.egyBitFeldolg('1');
				}
				else
				{
					binFa.egyBitFeldolg('0');
				}
				b[h] <<= 1;
			}
		}
		
		binFa.kiir(kiFile);
		
		kiFile.println("depth = " + binFa.getMelyseg());
		kiFile.println("mean = " + binFa.getAtlag());
		kiFile.println("var = " + binFa.getSzoras());
		
		kiFile.close();
		
		File file = new File("output.txt");
		FileInputStream fis = new FileInputStream(file);
		
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		
		
		out.println("<head><title>LZWBinfa</title></head>");
		out.println("<body>");
		out.println("<h2>Dékány Róbert LZWBinfa Servlet</h2>");
		try(BufferedReader br = new BufferedReader(new InputStreamReader(fis))) { for(String line; (line = br.readLine()) != null; ) { out.println("<p>" + line + "</p>"); } }
		
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
