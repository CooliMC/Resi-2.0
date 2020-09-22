import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;


import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;


public class GUI {

	private JTextField Speicherzellen_1;
	private JFrame Resi2;
	DefaultListModel Model;
	DefaultListModel model;
	JTextArea Quelltextfeld;
	public AtomicBoolean Running = new AtomicBoolean(false);
	public AtomicBoolean Paused = new AtomicBoolean(false);
	public AtomicBoolean Stopped = new AtomicBoolean(false);
	public AtomicInteger HerzZahl = new AtomicInteger(1);
	private File file;
	private JTextField Schritte;

	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					new GUI();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	
	public GUI() {
		initialize();
		this.Resi2.setVisible(true);
	}

	
	private void initialize() {
		Resi2 = new JFrame();
		Resi2.setResizable(false);
		Resi2.setTitle("Resi 2.0                                                                                                                                                                                                                      By Marc Neund\u00F6rfer");
		Resi2.getContentPane().setBackground(Color.LIGHT_GRAY);
		Resi2.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/res/bin.gif")));
		Resi2.setBounds(100, 100, 1000, 600);
		Resi2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Resi2.getContentPane().setLayout(null);
		
		JLabel QuellcodeHeader = new JLabel("Quellcode:");
		QuellcodeHeader.setBounds(10, 11, 200, 30);
		QuellcodeHeader.setFont(new Font("SansSerif", Font.BOLD, 30));
		Resi2.getContentPane().add(QuellcodeHeader);
		
		Speicherzellen_1 = new JTextField();
		Speicherzellen_1.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent arg0)
			{
				try
				{
					int tempint =Integer.parseInt(Speicherzellen_1.getText());
					if(tempint <= 0)
					{
						JOptionPane.showMessageDialog(null, "Nur gültige Zahlen Eingeben", "Fehler", JOptionPane.ERROR_MESSAGE);
						Speicherzellen_1.setText("10");
					}
					else if(tempint > 100000)
					{
						JOptionPane.showMessageDialog(null, "Nur gültige Zahlen Eingeben", "Fehler", JOptionPane.ERROR_MESSAGE);
						Speicherzellen_1.setText("10");
					}
					else
					{
						MainClass.Speicher = new AtomicIntegerArray(tempint);
						repaintSpeicher();
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Nur gültige Zahlen Eingeben", "Fehler", JOptionPane.ERROR_MESSAGE);
					Speicherzellen_1.setText("10");
				}
			}
		});
		Speicherzellen_1.setText("10");
		MainClass.Speicher = new AtomicIntegerArray(10);
		Speicherzellen_1.setBounds(750, 93, 220, 30);
		Speicherzellen_1.setFont(new Font("SansSerif", Font.BOLD, 30));
		Resi2.getContentPane().add(Speicherzellen_1);
		Speicherzellen_1.setColumns(10);
		
		JScrollPane Quelltextfeldrahmen = new JScrollPane();
		Quelltextfeldrahmen.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Quelltextfeldrahmen.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Quelltextfeldrahmen.setBounds(10, 52, 500, 499);
		Resi2.getContentPane().add(Quelltextfeldrahmen);
		
		Quelltextfeld = new JTextArea();
		Quelltextfeld.setFont(new Font("SansSerif", Font.BOLD, 30));
		Quelltextfeldrahmen.setViewportView(Quelltextfeld);
		
		JButton Neu = new JButton("Neu");
		Neu.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Quelltextfeld.getHighlighter().removeAllHighlights();
				Quelltextfeld.setText("");
				Speicherzellen_1.setText("10");
				MainClass.Speicher = new AtomicIntegerArray(10);
				MainClass.Steps.set(0);
				if(Running.get() && !Stopped.get())
				{
					Stopped.set(true);
				}
				repaintSpeicher();
			}
		});
		Neu.setBounds(520, 11, 105, 30);
		Resi2.getContentPane().add(Neu);
		
		JButton Laden = new JButton("Laden");
		Laden.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				Quelltextfeld.getHighlighter().removeAllHighlights();
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Resi-Datei","rmp","txt","resi");
		        JFileChooser chooser = new JFileChooser("c:/");
		        chooser.addChoosableFileFilter(filter);
		        chooser.setAcceptAllFileFilterUsed(false);
		        chooser.setFileFilter(filter);
		        int rueckgabeWert = chooser.showOpenDialog(null);
		        
		        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		            file = chooser.getSelectedFile();
		            try {
						Quelltextfeld.setText(new Scanner(file).useDelimiter("\\Z").next());
					}
		            catch (FileNotFoundException e) {	JOptionPane.showMessageDialog(null, "Fehler beim laden der Datei", "Fehler", JOptionPane.ERROR_MESSAGE);	}
		        }
		        
		        if(Running.get() && !Stopped.get())
				{
					Stopped.set(true);
				}
		        MainClass.Speicher = new AtomicIntegerArray(10);
		        MainClass.Steps.set(0);
		        repaintSpeicher();
			}
		});
		Laden.setBounds(635, 11, 105, 30);
		Resi2.getContentPane().add(Laden);
		
		JButton Speichern = new JButton("Speichern");
		Speichern.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(Running.get() && !Stopped.get())
				{
					Stopped.set(true);
					JOptionPane.showMessageDialog(null, "Programm gestoppt. Bis zur Beendigung warten und erneut versuchen", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					Quelltextfeld.getHighlighter().removeAllHighlights();
					
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Resi-Datei","rmp","txt","resi");
					JFileChooser chooser = new JFileChooser("c:/");
					chooser.setAcceptAllFileFilterUsed(false);
					chooser.setFileFilter(filter);
					chooser.addChoosableFileFilter(filter);
					int rueckgabeWert = chooser.showSaveDialog(null);
		        
					if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
					{
						file = chooser.getSelectedFile();
		            
						try {
							PrintWriter out = new PrintWriter(file);
							out.println(Quelltextfeld.getText());
							out.close();
						}
						catch (FileNotFoundException e1)
						{	
							JOptionPane.showMessageDialog(null, "Fehler beim Speichern der Datei", "Fehler", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		Speichern.setBounds(750, 11, 105, 30);
		Resi2.getContentPane().add(Speichern);
		
		JTextField Herz = new JTextField();
		Herz.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent arg0)
			{
				try
				{
					int tempint =Integer.parseInt(Herz.getText());
					if(tempint <= 0)
					{
						JOptionPane.showMessageDialog(null, "Nur gültige Zahlen Eingeben", "Fehler", JOptionPane.ERROR_MESSAGE);
						Herz.setText("1");
					}
					HerzZahl.set(tempint);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Nur gültige Zahlen Eingeben", "Fehler", JOptionPane.ERROR_MESSAGE);
					Herz.setText("1");
					HerzZahl.set(1);
				}
			}
		});
		Herz.setText("1");
		Herz.setFont(new Font("SansSerif", Font.BOLD, 30));
		Herz.setBounds(655, 520, 256, 31);
		Resi2.getContentPane().add(Herz);
		Herz.setColumns(10);
		
		JButton Pruefen = new JButton("Pr\u00FCfen");
		Pruefen.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				String[][] tempstring = MainClass.sortieren(Quelltextfeld.getText(), Integer.parseInt(Speicherzellen_1.getText()));
				
				if(MainClass.FormaleSpracheKorrekt(tempstring, 0, Integer.parseInt(Speicherzellen_1.getText())) &&
					MainClass.FormaleSpracheKorrekt(tempstring, 1, Integer.parseInt(Speicherzellen_1.getText())) &&
					MainClass.FormaleSpracheKorrekt(tempstring, 2, Integer.parseInt(Speicherzellen_1.getText())))
				{
					JOptionPane.showMessageDialog(null, "Prüfung Erfolgreich! Keine Fehler!", "Prüfung", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		Pruefen.setBounds(865, 11, 105, 30);
		Resi2.getContentPane().add(Pruefen);
		
		JButton Step = new JButton("Step");
		Step.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(Running.get())
				{
					if(!Stopped.get())
					{
						MainClass.AllowLoop.set(true);
						repaintSpeicher();
					}
				}
				else
				{
					Running.set(true);
					Paused.set(true);
					
					Thread tempthread = new Gesteuert(Quelltextfeld.getText(), Integer.parseInt(Speicherzellen_1.getText()));
					tempthread.start();
					
					new LoopSteuerung(tempthread).start();
					new GuiRefresher().start();
					new Highlighting().start();
				}
			}
		});
		Step.setBounds(635, 52, 105, 30);
		Resi2.getContentPane().add(Step);
		
		JButton Starten = new JButton("Starten");
		Starten.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(!Running.get())
				{
					Running.set(true);
					
					Thread tempthread = new Gesteuert(Quelltextfeld.getText(), Integer.parseInt(Speicherzellen_1.getText()));
					tempthread.start();
					
					new LoopSteuerung(tempthread).start();
					new GuiRefresher().start();
					new Highlighting().start();
				}
				else
				{
					if(!Stopped.get() && !Paused.get())
					{
						JOptionPane.showMessageDialog(null, "Programm läuft bereits!", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						if(!Stopped.get())
						{
							Paused.set(false);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Programm wurde gestoppt! Auf Beendung warten!", "Fehler", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		Starten.setBounds(520, 52, 105, 30);
		Resi2.getContentPane().add(Starten);
		
		JButton Pause = new JButton("Pause");
		Pause.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(Running.get())
				{
					if(!Stopped.get())
					{
						if(Paused.get())
						{
							Paused.set(false);
						}
						else
						{
							Paused.set(true);
						}
					}
				}
			}
		});
		Pause.setBounds(750, 52, 105, 30);
		Resi2.getContentPane().add(Pause);
		
		JButton AufBeginn = new JButton("Auf Beginn");
		AufBeginn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(Running.get() && !Stopped.get())
				{
					Stopped.set(true);
				}
				MainClass.Speicher = new AtomicIntegerArray(Integer.parseInt(Speicherzellen_1.getText()));
				MainClass.Steps.set(0);
				repaintSpeicher();
				Quelltextfeld.getHighlighter().removeAllHighlights();
			}
		});
		AufBeginn.setBounds(865, 52, 105, 30);
		Resi2.getContentPane().add(AufBeginn);
		
		JScrollPane Speicherzellen = new JScrollPane();
		Speicherzellen.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Speicherzellen.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Speicherzellen.setBounds(520, 134, 450, 375);
		Resi2.getContentPane().add(Speicherzellen);
		
		model = new DefaultListModel();
		JList Speicherzellenwert = new JList(model);
		Speicherzellenwert.setFont(new Font("SansSerif", Font.BOLD, 25));
		Speicherzellenwert.setBackground(UIManager.getColor("CheckBox.light"));
		model.addElement("Wert:");
		for(int i = 0; i < Integer.parseInt(Speicherzellen_1.getText()); i++)
		{
			model.addElement(MainClass.Speicher.get(i));
		}
		Speicherzellen.setViewportView(Speicherzellenwert);
		
		Model = new DefaultListModel();
		JList Speicherzellenname = new JList(Model);
		Speicherzellenname.setFont(new Font("SansSerif", Font.BOLD, 25));
		Speicherzellenname.setBackground(UIManager.getColor("CheckBox.background"));
		Model.addElement("Speicherzelle:");
		for(int i = 0; i < Integer.parseInt(Speicherzellen_1.getText()); i++)
		{
			Model.addElement("Zelle " + i);
		}
		Speicherzellen.setRowHeaderView(Speicherzellenname);
		
		JLabel TaktrateHeader = new JLabel("Taktrate:");
		TaktrateHeader.setFont(new Font("SansSerif", Font.BOLD, 30));
		TaktrateHeader.setBounds(520, 520, 150, 31);
		Resi2.getContentPane().add(TaktrateHeader);
		
		JLabel HerzHeader = new JLabel("Hz");
		HerzHeader.setFont(new Font("SansSerif", Font.BOLD, 30));
		HerzHeader.setBounds(921, 520, 49, 31);
		Resi2.getContentPane().add(HerzHeader);
		
		JLabel SpeicherzellenHeader = new JLabel("Speicherzellen:");
		SpeicherzellenHeader.setFont(new Font("SansSerif", Font.BOLD, 30));
		SpeicherzellenHeader.setBounds(520, 93, 220, 31);
		Resi2.getContentPane().add(SpeicherzellenHeader);
		
		Schritte = new JTextField();
		Schritte.setText("0");
		Schritte.setEditable(false);
		Schritte.setBounds(360, 11, 150, 33);
		Schritte.setFont(new Font("SansSerif", Font.BOLD, 30));
		Resi2.getContentPane().add(Schritte);
		Schritte.setColumns(10);
		
		JLabel SchritteHeader = new JLabel("Schritte:");
		SchritteHeader.setBounds(228, 11, 122, 30);
		SchritteHeader.setFont(new Font("SansSerif", Font.BOLD, 30));
		Resi2.getContentPane().add(SchritteHeader);
	}
	
	public class Gesteuert extends Thread
	{
		String text;
		int memory;
		
		public Gesteuert(String t, int me)
		{
			text = t;
			memory = me;
		}
		
		public void run()
		{
			String[][] a = MainClass.sortieren(text, memory);
			MainClass.Steps.set(0);

			if(MainClass.FormaleSpracheKorrekt(a, 0, memory) &&
					MainClass.FormaleSpracheKorrekt(a, 1, memory) &&
					MainClass.FormaleSpracheKorrekt(a, 2, memory))
			{
				MainClass.ausfuehren(a);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Fehler beim automatischen Durchlauf", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
			
			Quelltextfeld.getHighlighter().removeAllHighlights();
			Running.set(false);
		}
	}
	
	public class LoopSteuerung extends Thread
	{
		Thread Tothread;
		
		public LoopSteuerung(Thread thread)
		{
			Tothread = thread;
		}
		
		public void run()
		{
			long time = System.nanoTime();
			
			while(Running.get())
			{
				if(!Paused.get())
				{
					if(Stopped.get())
					{
						Tothread.stop();
						Running.set(false);
						Paused.set(false);
						Stopped.set(false);
						Quelltextfeld.getHighlighter().removeAllHighlights();
					}
					else
					{
						if(System.nanoTime() >= (time + (1000000000/HerzZahl.get())))
						{
							MainClass.AllowLoop.set(true);
							time = System.nanoTime();
						}
					}
				}
				
				if(Stopped.get())
				{
					Tothread.stop();
					Running.set(false);
					Paused.set(false);
					Stopped.set(false);
					Quelltextfeld.getHighlighter().removeAllHighlights();
				}
			}
		}
	}
	
	public class GuiRefresher extends Thread
	{
		public void run()
		{
			long time = System.currentTimeMillis();
			
			while(Running.get())
			{
				if(System.currentTimeMillis() >= (time +50))
				{
					try{	model.removeAllElements();	} catch (Exception e) {}
					try{	model.addElement("Wert:");	} catch (Exception e) {}
					
					try{
						for(int i = 0; i < Integer.parseInt(Speicherzellen_1.getText()); i++)
						{
							try{	model.addElement(MainClass.Speicher.get(i));	} catch (Exception e) {}
						}
					} catch (Exception e) {}
				
				
					if(MainClass.Steps.get() >= 1000)
					{
						Schritte.setText((MainClass.Steps.get()/1000) + "k");
					}
					else
					{
						Schritte.setText("" + MainClass.Steps.get());
					}
					
					time = System.currentTimeMillis();
				}
			}
			
			while(System.currentTimeMillis() <= (time + 1000))
			{
				if(MainClass.Steps.get() >= 1000)
				{
					Schritte.setText("" + (MainClass.Steps.get()/1000));
				}
				else
				{
					Schritte.setText("" + MainClass.Steps.get());
				}
				
				if(System.currentTimeMillis() <= (time +600))
				{
					try{	model.removeAllElements();	} catch (Exception e) {}
					try{	model.addElement("Wert:");	} catch (Exception e) {}
					try{
						for(int i = 0; i < Integer.parseInt(Speicherzellen_1.getText()); i++)
						{
							try{	model.addElement(MainClass.Speicher.get(i));	} catch (Exception e) {}
						}
					} catch (Exception e) {}
				}
			}
		}
		//public void run() {}
	}
	
	public class Highlighting extends Thread
	{
		public void run()
		{
			String code = Quelltextfeld.getText();
			int tempint = MainClass.NowLine.get();
			Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
			int normalagain = 0;
			int lines = 1;
			
			for(int i = 0; i < code.length(); i++)
			{
				if(code.charAt(i) == '\n' || code.charAt(i) == '\r' || ("" + code.charAt(i)).equals(String.format("%n")) || (("" + code.charAt(i)).equals(System.getProperty("line.separator"))))
				{
					if(normalagain == 0)
					{
						lines++;
						normalagain++;
					}
				}
				else
				{
					normalagain = 0;
				}
			}
			
			int[][] marking = new int[lines][2];
			int tempcountvariable = 0;
			normalagain = 0;
			
			for(int i = 0; i < code.length(); i++)
			{
				if(code.charAt(i) == '\n' || code.charAt(i) == '\r' || ("" + code.charAt(i)).equals(String.format("%n")) || (("" + code.charAt(i)).equals(System.getProperty("line.separator"))))
				{
					if(tempcountvariable < lines && normalagain == 0)
					{
						if(tempcountvariable == 0)
						{
							marking[0][0] = 0;
							marking[0][1] = i;
							tempcountvariable++;
							normalagain++;
						}
						else
						{
							marking[tempcountvariable][0] = marking[tempcountvariable-1][1] + 1;
							marking[tempcountvariable][1] = i;
							tempcountvariable++;
							normalagain++;
						}
					}
				}
				else
				{
					normalagain = 0;
				}
			}
			
			System.out.println(lines);
			while(Running.get())
			{
				if(tempint != MainClass.NowLine.get())
				{
					tempint = MainClass.NowLine.get();
				
					Quelltextfeld.getHighlighter().removeAllHighlights();
					Quelltextfeld.getHighlighter().removeAllHighlights();
					try {
						Quelltextfeld.getHighlighter().addHighlight(marking[tempint-1][0], marking[tempint-1][1], painter);
					} catch (Exception e) { }
				}
			}
		}
	}
	
	public void repaintSpeicher()
	{
		try{	model.removeAllElements();	} catch (Exception e) {}
		try{	Model.removeAllElements();	} catch (Exception e) {}
		
		try{	model.addElement("Wert:");	} catch (Exception e) {}
		try{	Model.addElement("Speicherzelle:");	} catch (Exception e) {}
		
		for(int i = 0; i < Integer.parseInt(Speicherzellen_1.getText()); i++)
		{
			try{	model.addElement(MainClass.Speicher.get(i));	} catch (Exception e) {}
		}
		
		for(int i = 0; i < Integer.parseInt(Speicherzellen_1.getText()); i++)
		{
			try{	Model.addElement("Zelle " + i);	} catch (Exception e) {}
		}
		
		if(MainClass.Steps.get() >= 1000)
		{
			Schritte.setText("" + (MainClass.Steps.get()/1000));
		}
		else
		{
			Schritte.setText("" + MainClass.Steps.get());
		}
	}
}
