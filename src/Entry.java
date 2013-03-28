import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;

public class Entry implements Comparable<Entry>
{
   private String primary, secondary;
   private LinkedList<Integer> pages = new LinkedList<Integer>(); //stores page numbers
   
   //constructor
   public Entry(String primary, String secondary)
   {
      this.primary = primary;
      this.secondary = secondary;
   }
      
   public int compareTo(Entry e)
   //compares 2 entries; returns 0 for match, 1 for greater than, -1 for less than
   //1st checks primary fields, than secondary
   //equal if both match or are null
   //if one secondary is null and the other isn't, the null field entry precedes
   //if both are non-null, comparison of secondary fields determines the order of the Entries
   {
	   // see discussion below

	   if(this.primary.compareToIgnoreCase(e.getPrimary()) > 0 )
	   {
		   //current string is greater than compared string
		   return 1;
	   }
	   else if(this.primary.compareToIgnoreCase(e.getPrimary()) < 0 )
	   {
		   return -1;
	   }
	   //primaries must equal, check secondaries
	   else if(this.secondary != null && e.secondary != null)
	   {
		   if(this.secondary.compareToIgnoreCase(e.getSecondary()) > 0)
			   return 1; //greater than
		   else if(this.secondary.compareToIgnoreCase(e.getSecondary()) < 0)
			   return -1; //less than
		   else
			   return 0; //must match than!
	   }
	   else if(this.secondary == null && e.secondary != null)
	   {
		   return -1;
	   }
	   else if(this.secondary != null && e.secondary == null)
	   {
		   return 1;
	   }
	   else //secondaries are both null, and primaries match
		   return 0;
    }
   
   //add a single pages number to the pages linkedlist
   public void addSinglePage(int p){
	   //pages.add(p);
	   
	   if(!pages.contains(p) && !pages.contains(-p))
	   {
		   boolean addToEnd = true;
		   ListIterator it = pages.listIterator();
		   int counter = -1;
		   while(it.hasNext())
		   {
			   int nextNum = (Integer)it.next();
			   counter++;
			   if(p <= Math.abs(nextNum))
			   {
				   pages.add(counter, p);//it.add(p);
				   System.out.print(p);
				   System.out.println(" less than: " + nextNum);
				   System.out.println(primary);
				   addToEnd = false;
				   break;
			   }
			   
		   }
		   if(addToEnd == true)
			   pages.add(p);
	   }
	   else
		   System.out.println("Page #" + p + " Exists!");
   }
   
   //adds a page range to the pages linkedlist
   public void addPageRange(int lower, int upper)
   {
	   //remove #'s withing page range that already exists in pages
	   for(int i = Math.abs(lower); i <= upper; i++)
	   {
		   if(pages.contains(i))
			   pages.remove(i);
	   }
	   //now, add in page range in proper order
	   ListIterator it = pages.listIterator();
	   boolean addToEnd = true;

	   while(it.hasNext())
	   {
		   int nextNum = (Integer)it.next();
		   if(Math.abs(lower) < nextNum)
		   {
			   it.add(lower);
			   it.add(upper);
			   addToEnd = false;
			   break;
		   }
	   }
	   if(addToEnd == true)
	   {
		   pages.add(lower);
		   pages.add(upper);
	   }
   }
   
   //print a proper list of pages 
   public String getPageList(){
	   //eg [1,-2,7,9] ---> 1, 2 - 7, 9.
	   String s = "";
	   for(int i=0; i<pages.size(); i++)
	   {
		   if(pages.get(i) < 0)
		   {
			   int x = Math.abs(pages.get(i));
			   s = s + (x + " - ");
		   }
		   else if(i == pages.size()-1)
			   s = s + (pages.get(i) + ".");
		   else
			   s = s + (pages.get(i) + ", ");
	   }
	   return s;
   }
   
    // appropriate get and set methods here ------------

	public String getPrimary() {
		return primary;
	}
	
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	
	public String getSecondary() {
		return secondary;
	}
	
	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}
	
	public LinkedList<Integer> getPages() {
		return pages;
	}

	public void setPages(LinkedList<Integer> pages) {
		this.pages = pages;
	}
	//--------------------------------------------------
}
