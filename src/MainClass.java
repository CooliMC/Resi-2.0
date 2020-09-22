import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

import javax.swing.JOptionPane;

public class MainClass
{
	public static AtomicIntegerArray Speicher;
	public static AtomicBoolean AllowLoop = new AtomicBoolean(true);
	public static AtomicInteger Steps = new AtomicInteger(0);
	public static AtomicInteger NowLine = new AtomicInteger(0);
	
	/*public static void main (String[] args)
	{
		String b = "10 cload 10\n11 end\n\n\n";
		
		String[][] a = sortieren(b, 10);
		
		
	}*/
	
	public static void ausfuehren(String[][] line)
	{
		for(int i = 0; i < line.length; i++)
		{
			NowLine.set(i);
			
			while(!AllowLoop.get())
			{
				//Auf ein neues Herz warte :D ^^
			}
			AllowLoop.set(false);
			Steps.incrementAndGet();
			
			switch(line[i][1])
			{
				case "load":
				{
					Speicher.set(0, Speicher.get(Integer.parseInt(line[i][2])));
					break;
				}
				
				case "cload":
				{
					Speicher.set(0, Integer.parseInt(line[i][2]));
					break;
				}
				
				case "iload":
				{
					Speicher.set(0, Speicher.get(Speicher.get(0)));
					break;
				}
				
				case "store":
				{
					Speicher.set(Integer.parseInt(line[i][2]), Speicher.get(0));
					break;
				}
				
				case "istore":
				{
					Speicher.set(Speicher.get(Integer.parseInt(line[i][2])), Speicher.get(0));
					break;
				}
				
				case "add":
				{
					Speicher.set(0, Speicher.get(0) + Speicher.get(Integer.parseInt(line[i][2])));
					break;
				}
				
				case "cadd":
				{
					Speicher.set(0, Speicher.get(0) + Integer.parseInt(line[i][2]));
					break;
				}
				
				case "iadd":
				{
					Speicher.set(0, Speicher.get(0) + Speicher.get(Speicher.get(0)));
					break;
				}
				
				case "sub":
				{
					Speicher.set(0, Speicher.get(0) - Speicher.get(Integer.parseInt(line[i][2])));
					if(Speicher.get(0) < 0) Speicher.set(0, 0);
					break;
				}
				
				case "csub":
				{
					Speicher.set(0, Speicher.get(0) - Integer.parseInt(line[i][2]));
					if(Speicher.get(0) < 0) Speicher.set(0, 0);
					break;
				}
				
				case "isub":
				{
					Speicher.set(0, Speicher.get(0) - Speicher.get(Speicher.get(0)));
					if(Speicher.get(0) < 0) Speicher.set(0, 0);
					break;
				}
				
				case "mult":
				{
					Speicher.set(0, Speicher.get(0) * Speicher.get(Integer.parseInt(line[i][2])));
					break;
				}
				
				case "cmult":
				{
					Speicher.set(0, Speicher.get(0) * Integer.parseInt(line[i][2]));
					break;
				}
				
				case "imult":
				{
					Speicher.set(0, Speicher.get(0) * Speicher.get(Speicher.get(0)));
					break;
				}
				
				case "div":
				{
					if(Speicher.get(Integer.parseInt(line[i][2])) != 0)
					{
						Speicher.set(0, Speicher.get(0) / Speicher.get(Integer.parseInt(line[i][2])));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Fehler im Programm (" + line[i][1] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
						i = line.length;
						break;
					}
					break;
				}
				
				case "cdiv":
				{
					if(Integer.parseInt(line[i][2]) != 0)
					{
						Speicher.set(0, Speicher.get(0) / Integer.parseInt(line[i][2]));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Fehler im Programm (" + line[i][1] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
						i = line.length;
						break;
					}
					break;
				}
				
				case "idiv":
				{
					if(Speicher.get(Speicher.get(0)) != 0)
					{
						Speicher.set(0, Speicher.get(0) / Speicher.get(Speicher.get(0)));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Fehler im Programm (" + line[i][1] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
						i = line.length;
						break;
					}
					break;
				}
				
				case "goto":
				{
					int localint = 0;
					for(int j = 0; j < line.length; j++)
					{
						if(line[i][2].equals(line[j][0]))
						{
							i = j-1;
							localint = 1;
							break;
						}
					
					}
					if(localint == 0)
					{
						JOptionPane.showMessageDialog(null, "Fehler im Programm (" + line[i][1] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
						i = line.length;
						break;
					}
					break;
				}
				
				case "jzero":
				{
					if(Speicher.get(0) == 0)
					{
						int localint = 0;
						for(int j = 0; j < line.length; j++)
						{
							if(line[i][2].equals(line[j][0]))
							{
								i = j-1;
								localint = 1;
								break;
							}
						}
						if(localint == 0)
						{
							JOptionPane.showMessageDialog(null, "Fehler im Programm (" + line[i][1] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
							i = line.length;
							break;
						}
					}
					break;
				}
				
				case "end":
				{
					i = line.length;
					JOptionPane.showMessageDialog(null, "Programm erfolgreich ausgeführt", "Information", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			
				default:
				{
					JOptionPane.showMessageDialog(null, "Fehler im Programm (" + line[i][1] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
					i = line.length;
					break;
				}
			}
		}
		
		for(int i = 0; i < Speicher.length(); i++)
		{
			System.out.println("Speicherzelle " + i + ":" + Speicher.get(i));
		}
	}
	
	
	public static String[][] sortieren(String code, int memorysize)
	{
		if(code.length() > 0)
		{
			Speicher = new AtomicIntegerArray(memorysize);
			int lines = 1;
			
			for(int i = 0; i < code.length(); i++)
			{
				if(code.charAt(i) == '\n' || code.charAt(i) == '\r' || ("" + code.charAt(i)).equals(String.format("%n")))
				{
					if(i < (code.length()-1))
					{
						if( !(code.charAt(i+1) == '\n' || code.charAt(i+1) == '\r' || ("" + code.charAt(i+1)).equals(String.format("%n"))))
						{
							lines++;
						}
					}
				}
			}
			
			String[] templines = new String[lines];
			int templine = 0;
			int lastlooptempadd = 0;
			
			for(int i = 0; i < code.length(); i ++)
			{
				if(code.charAt(i) == '\n'  || code.charAt(i) == '\r' || ("" + code.charAt(i)).equals(String.format("%n")))
				{
					if(lastlooptempadd == 0)
					{
						templine++;
						lastlooptempadd++;
					}
				}
				else
				{
					if((i < code.length()) && (templine < lines))	//UND HIER NEU
					{
						lastlooptempadd = 0;
						if(templines[templine] == null) templines[templine] = "";
						templines[templine] = templines[templine] + code.charAt(i);
					}
				}
			}
			
			
			String[][] line = new String[lines][3];
			
			for(int i = 0; i < lines; i++)
			{
				int k = 0;
				
				for(int j = 0; j < templines[i].length(); j++)
				{
					while(templines[i].charAt(j) == ' ' && line[i][k] == null)
					{
						j++;
					}
					
					if(k == 2 && line[i][k] != "" && line[i][k] != null && (templines[i].charAt(j) == '\n' || templines[i].charAt(j) == '\r' || ("" + templines[i].charAt(j)).equals(String.format("%n"))))
					{
						j = templines[i].length();
					}
					else if (templines[i].charAt(j) == ' ')
					{
						k++;
						j++;
					}
					
					if(j < templines[i].length())
					{
						if(line[i][k] == null) line[i][k] = "";
						line[i][k] = line[i][k] + templines[i].charAt(j);
					}
				}
			}
			
			return line;
		}
		else
		{
			return new String[0][0];
		}
	}
	
	public static Boolean FormaleSpracheKorrekt(String[][] input, int stelle, int memorysize)
	{
		switch(stelle)
		{
			case 0:
			{
				int minint = -1;
		
				for(int i = 0; i < input.length; i++)
				{	
					try
					{
						int tempint = Integer.parseInt(input[i][stelle]);
						if(tempint > minint)
						{
							minint = tempint;
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Zeilennummer kleiner als vorhergehende oder negativ (" + input[i][stelle] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null, "Ungültige Zeilennummer (" + input[i][stelle] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
				
				if(minint > -1)
				{
					return true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Fehler im Programm (input)", "Fehler", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			
			case 1:
			{
				for(int i = 0; i < input.length; i++)
				{
					String tempstring = input[i][stelle];
					if(i != input.length-1)
					{	
						if(tempstring.equals("load") || tempstring.equals("cload") || tempstring.equals("iload") || tempstring.equals( "store") ||
							tempstring.equals("istore") || tempstring.equals("add") || tempstring.equals("cadd") || tempstring.equals("iadd") ||
							tempstring.equals("sub") || tempstring.equals("csub") || tempstring.equals("isub") || tempstring.equals("mult") ||
							tempstring.equals("cmult") || tempstring.equals("imult") || tempstring.equals("div") || tempstring.equals("cdiv") ||
							tempstring.equals("idiv") || tempstring.equals("goto") || tempstring.equals("jzero") || tempstring.equals("end"))
						{
							if(tempstring.equals("end"))
							{
								if(input[i][stelle+1] != null)
								{
									JOptionPane.showMessageDialog(null, "Ungültige Variable beim End Befehl (" + input[i][stelle+1] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
									return false;
								}
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Ungültige Befehl (" + input[i][stelle] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
					else
					{
						if(!tempstring.equals("end") || input[i][stelle+1] != null )
						{
							JOptionPane.showMessageDialog(null, "Kein End Befehl in der letzten Zeile oder Zeichen hinter letztem End (Bitte alles nach 'End' löschen)", "Fehler", JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
				}
				
				if(input.length > 0)
				{
					return true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Fehler im Programm (input)", "Fehler", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			
			case 2:
			{
				for(int i = 0; i < input.length; i++)
				{	
					try
					{
						int tempint = Integer.parseInt(input[i][stelle]);
						
						if(tempint < 0 &&
							tempint > memorysize)
						{
							JOptionPane.showMessageDialog(null, "Speicherzelle nicht vorhanden (" + input[i][stelle] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
					catch (Exception e)
					{
						if(i != input.length-1)
						{
							JOptionPane.showMessageDialog(null, "Ungültige Speicherzelle (" + input[i][stelle] + ")", "Fehler", JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
				}
				
				if(input.length > 0)
				{
					return true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Fehler im Programm (input)", "Fehler", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			
			default:
			{
				JOptionPane.showMessageDialog(null, "Fehler im Programm (Stelle)", "Fehler", JOptionPane.ERROR_MESSAGE);
				return false;
			}
				
		}
	}
}
