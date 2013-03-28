import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayList;

public class indexer
{	
	public static void main(String[] args)
	{
		
		int SIZE = 101;
		//hash table 'array of linked lists'
		hashTable h = new hashTable(SIZE);
		
		ArrayList<Entry> a = new ArrayList(); //ArrayList for sorting Entries

		//get user argument for file
//		if(args.length > 0)
//		{
		
		try  //get page number, word / phrase
		{
			//Scanner sc = new Scanner (new FileReader(args[0]));

			//using local file in Eclipse for testing...
			Scanner sc = new Scanner(new FileReader("/Users/brandonshea/Desktop/eclipseFiles/PA3/src/test.txt"));
			
			int pageNumber = 0; int lowerPage = 0; int upperPage = 0;
		    while (sc.hasNextLine()) //sort data into Entries
		    {
		    	String currentLine; 
		    	String p , s;
		    	currentLine = sc.nextLine();
		    	//System.out.println("Currentline: " + currentLine);
		    	
		    	//st.replaceAll("\\s","") example for removing white space
		    	
		    	//page numbers ------------------------------------------------
		    	//page number if the 1st character isn't a tab or space
	    		//if a single digit, just add it to pages
	    		//if multi-page, first digit is negative; seperate into parts
		    	if(currentLine.charAt(0) != '\t' && currentLine.charAt(0) != ' ')
		    	{	
		    		if(currentLine.contains(" - ")) //multi-page
		    		{
		    			//split it
		    			String[] parts = currentLine.split(" - ");
		    			lowerPage = -(Integer.parseInt(parts[0]));
		    			upperPage = Integer.parseInt(parts[1]);
		    		}
		    		else //single page
		    		{
		    			pageNumber = Integer.parseInt(currentLine);
		    			lowerPage = 0; upperPage = 0; //reassign to 0, so that pageNumber is used instead
		    		}
		    	}
		    	//entries ----------------------------------------------------
		    	else  
		    	{
		    		currentLine = currentLine.trim();
		    		//split string into primary and secondary if there is a '#'
		    		//else just assign a primary and set secondary to null
		    		if(currentLine.contains("#"))
		    		{
		    			//split it
		    			String[] parts = currentLine.split("#");
		    			p = parts[0];
		    			s = parts[1];
		    		}
		    		//only primary exists, assign secondary as null
		    		else
		    		{
		    			p = currentLine;
		    			s = null;
		    		}
		    
		    		//See if primary and secondary exists as keys
		    		//if so, just add the corresponding page numbers
		    		//else, contruct a new entry with primary and secondary, assign page number
		    		//add new entry to hash table and ArrayList
		    		if(h.search(p, s) == true)
		    		{
		    			if(lowerPage != 0 && upperPage != 0)
		    			{
		    				h.getEntry(p, s).addPageRange(lowerPage, upperPage);
		    			}
		    			else
		    				h.getEntry(p, s).addSinglePage(pageNumber);
		    		}
		    		else //entry doesn't exist, make new Entry
		    		{
		    			Entry e = new Entry(p , s);
		    			
		    			//add pages
		    			if(lowerPage != 0 && upperPage != 0)
		    			{
		    				e.addPageRange(lowerPage, upperPage);
		    			}
		    			else
		    			{
		    				e.addSinglePage(pageNumber);
		    			}
		    			
		    			//add to hash table
		    			h.insert(p, s, e);
		    			
		    			//add to arrayList
		    			addToArrayList(a, e);
		    			
/*TEST*		    		System.out.print("Added " + e.getPrimary() + " / " + e.getSecondary());
		    			System.out.print("\tbucketIndex: " + h.hash(e.getPrimary(), e.getSecondary()) + "\n" );
		    			System.out.println("Pages: " + e.getPages());*/
		    		}
		    	}
		    }
			sc.close();

		}//-----------------------------
		catch(FileNotFoundException e) {
			System.out.print(e);
		}//-----------------------------
		
		//print contents of each bucket in terminal
		h.printEachBucketContents();
		
		//write index to text file
		writeToFile(a);
	}
	
	//Adds in the new Entry into the ArrayList; Alphabetical order
	// e is the Entry being added to the arrayList
	public static void addToArrayList(ArrayList<Entry> a , Entry e)
	{
		if(a.isEmpty())
		{
			a.add(e);
		} 
		for(int i=0; i<a.size(); i++)
		{
			if( e.compareTo(a.get(i)) == 0 ) //entry exists, don't add
				break;
			else if(e.compareTo(a.get(i)) < 0 || a.get(i) == null)
			{
				a.add(i, e);
				break;
			}
			else if(i == a.size()-1) //add to end of arrayList
			{
				a.add(e);
				break;
			}
			//System.out.println("Size: " + a.size());
			//System.out.println(i);
		}
	}
	
	public static void printArrayListTerminal(ArrayList<Entry> a)
	{
		for(int i=0; i<a.size(); i++)
		{
			Entry e = a.get(i);
			if(e.getSecondary() == null)
			{
				System.out.println(e.getPrimary() + "   " + e.getPages());
			}
			else
			{
				if(e.getPrimary() != a.get(i-1).getPrimary())
					System.out.println(e.getPrimary());
				System.out.println("\t" + e.getSecondary() + "   " + e.getPages());
			}
				
		}
	}
	
	//create and write ArrayList contents to text file
	public static void writeToFile(ArrayList<Entry> a)
	{
	    Writer writer = null;
	    
	    try {
	        File file = new File("/Users/brandonshea/Desktop/write.txt");
	        writer = new BufferedWriter(new FileWriter(file));

	    	for(int i=0; i<a.size(); i++)
	    	{
		        String text;
				Entry e = a.get(i);
				if(e.getSecondary() == null)
				{
					text = (e.getPrimary() + "   " + e.getPageList() + "\n");
				}
				else
				{
					if(!(e.getPrimary().equals(a.get(i-1).getPrimary())))
					{
						text = (e.getPrimary() + "\n");
						writer.write(text);
					}
					text = ("   " + e.getSecondary() + "   " + e.getPageList()+ "\n");
				}
		
		        writer.write(text);
		        
	    	}
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (writer != null) {
	                writer.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	}
}
