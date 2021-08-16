import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.io.*;;

class Front
{
	public static void main(String args[])
	{
		FirstWindow f1 = new FirstWindow();
	}
}

class FirstWindow
{
	public FirstWindow()
	{
		JFrame f = new JFrame("Menu");

		JButton b1obj = new JButton("Packer");   //Submit button
		b1obj.setBounds(100,200,140,40);
		// --------------------
		JButton b2obj = new JButton("Unpacker");   //Submit button
		b2obj.setBounds(400,200,140,40);

		
		JLabel lobj = new JLabel("Enter Your Choice");
		lobj.setBounds(100,100,500,200);


		f.add(lobj);   //we Add all components in window
		f.add(b1obj);
		f.add(b2obj);
	

		f.setSize(600,500);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //use to terminte operation

		b1obj.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent eobj)
			{
				f.setVisible(false);
				SecondWindow o = new SecondWindow();
			}
		});
		b2obj.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent eobj)
			{
				f.setVisible(false);
				ThirdWindow o = new ThirdWindow();
			}
		});
	}
}

class SecondWindow
{
	public SecondWindow()
	{
		JFrame f = new JFrame("Packer");

		JButton bobj = new JButton("Submit");   //Submit button
		bobj.setBounds(100,200,140,40);
		// --------------------
		JLabel lobj1 = new JLabel("Enter Folder name");   //empty textfield(Box)
		lobj1.setBounds(10,10,100,100);

		JTextField tf1 = new JTextField();
		// (x cordinate, y cordinate, widtyh, height)
		tf1.setBounds(100,50,130,30);
		// ------------------------------------
		JLabel lobj2 = new JLabel("Enter File name");
		lobj2.setBounds(10,110,100,100);

		JTextField tf2 = new JTextField();
		// (x cordinate, y cordinate, widtyh, height)
		tf2.setBounds(100,120,130,30);

		JButton prev = new JButton("Preveous");   //Submit button
		prev.setBounds(400,200,140,40);

		f.add(lobj1);   //we Add all components in window
		f.add(bobj);
		f.add(tf1);
		f.add(lobj2);
		f.add(tf2);
		f.add(prev);

		f.setSize(600,500);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //use to terminte operation

		bobj.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent eobj)
			{
				Packer pobj = new Packer(tf1.getText(), tf2.getText());
				f.setVisible(false);

				FirstWindow o = new FirstWindow();
			}
		});

		prev.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent eobj)
			{
				f.setVisible(false);

				FirstWindow o = new FirstWindow();
			}
		});
		
	}
}



class Packer
{
	public FileOutputStream outstream = null;

	public Packer(String FolderName,String FileName)
	{
		try
		{
			System.out.println("Inside Packer");

			File outfile = new File(FileName); 				//File create
			outstream = new FileOutputStream(FileName);		//for Write in to File
			//Set the current working directory
			System.setProperty("use.dir",FolderName);
			
			TravelDirectory(FolderName);			
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
	}

	public void TravelDirectory(String path)
	{
		int Counter = 0;
		File directoryPath = new File(path);

		File arr[] = directoryPath.listFiles();

		for(File filename : arr)
		{
			//System.out.println(filename.getAbsolutePath());
			if(filename.getName().endsWith(".txt"))
			{
				PackFile(filename.getAbsolutePath());
				Counter++;
			}			
		}
		System.out.println();
		System.out.println("Sucessfully Packed File :: " + Counter);
	}
	
	public void PackFile(String FilePath)
	{
		//System.out.println("File name received "+ FilePath);
		byte Header[] = new byte[100];
		byte Buffer[] = new byte[1024];
		int length = 0;

		FileInputStream istream = null;

		File fobj = new File(FilePath);

		String temp = FilePath+" "+fobj.length();
		
		// Create header of 100 bytes
		for(int i = temp.length(); i < 100; i++)
		{
			temp = temp + " ";
		}		

		Header = temp.getBytes();   //String to byte_array
		
		try
		{
			outstream.write(Header,0,Header.length);    //  use to write Header in to file
			// open the file for reading
			istream = new FileInputStream(FilePath);     //use to read File	
			while((length = istream.read(Buffer)) > 0)
			{
				outstream.write(Buffer,0,length);
			}

			istream.close();
		}
		catch(Exception obj)
		{}
		// System.out.println("Header : "+temp.length());
	}
}

class ThirdWindow
{
	public ThirdWindow()
	{
		JFrame f = new JFrame("UnPacker");

		JButton bobj = new JButton("Submit");   //Submit button
		bobj.setBounds(100,200,140,40);
		// --------------------
		JLabel lobj = new JLabel("Enter File name");   //empty textfield(Box)
		lobj.setBounds(10,10,100,100);

		JTextField tf = new JTextField();
		// (x cordinate, y cordinate, widtyh, height)
		tf.setBounds(100,50,130,30);
		// -----------------------------------
		JButton prev = new JButton("Preveous");   //Submit button
		prev.setBounds(400,200,140,40);

		f.add(lobj);   //we Add all components in window
		f.add(bobj);
		f.add(tf);
		f.add(prev);
		f.setSize(600,500);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //use to terminte operation

		bobj.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent eobj)
			{
				UnPacker pobj = new UnPacker(tf.getText());
				f.setVisible(false);
				FirstWindow o = new FirstWindow();
			}
		});

		prev.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent eobj)
			{
				f.setVisible(false);

				FirstWindow o = new FirstWindow();
			}
		});
	}
}

class UnPacker
{
	public FileOutputStream outstream = null;


	public UnPacker(String src)
	{
		System.out.println("Inside UnPacker");
		unpackFile(src);
	}
		
	public void unpackFile(String FilePath)
	{

		try
		{
			int Counter = 0;
			FileInputStream instream = new FileInputStream(FilePath);
			byte Header[] = new byte[100];
			int length = 0;

			while((length = instream.read(Header,0,100)) > 0)
			{
				String str = new String(Header);
				String ext = str.substring(str.lastIndexOf("/"));
				ext = ext.substring(1);

					
				String words[] = ext.split("\\s");   //just Like Tokenisation in c as strtok
				String name = words[0];
				int size = Integer.parseInt(words[1]);
				byte arr[] = new byte[size]; 
				instream.read(arr,0,size); 
				FileOutputStream fout = new FileOutputStream(name); 
				fout.write(arr,0,size);
				Counter++;
			}
			instream.close();

			System.out.println("Sucessfully number of UnPacked File :: " + Counter);
		
		}
		catch(Exception e)
		{
		
		}

				
	}
}
