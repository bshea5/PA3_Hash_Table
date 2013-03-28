import java.util.LinkedList;
import java.util.Iterator;


public class hashTable {
	
	private int SIZE = 11; //prime
	protected LinkedList<Entry>[] a;
	
	public hashTable(int s){
		this.SIZE = s;
		//hash table 'array of linked lists'
		this.a = (LinkedList<Entry>[])new LinkedList[SIZE];  // LinkedList is the raw type
	}
	
	public int getSize()
	{
		return SIZE;
	}
	public void setSize(int s)
	{
		this.SIZE = s;
	}
	
	/*
	 * suggested hash function for strings from notes:
	 * 	h(str) = Math.abs(str.hashCod()) % SIZE
	 */
	//hash function, returns index for bucket location of given key
	public int hash(String primary, String secondary)
	{
		if(secondary == null)
			return Math.abs(primary.hashCode()) % SIZE;
		else
			return Math.abs(primary.hashCode() + secondary.hashCode()) % SIZE;
	}
	
	//search for given hashed key (p,s) to find bucket(LinkedList)
	//traverse bucket for (p,s), return boolean
	public boolean search(String p, String s){
		
		//first find bucket
		int bucketIndex = hash(p,s);

		if(bucketIndex == SIZE || a[bucketIndex] == null)
		{	
			//System.out.println("No bucket ; ;");
			return false;
		}
		
		//found bucket, search through Linked List with iterator
		Iterator it = a[bucketIndex].listIterator(0);
		while(it.hasNext())
		{
			Entry entry = (Entry)it.next();

			if(entry.getPrimary().equals(p))
			{
				if(entry.getSecondary() == null && s == null)
					return true; //entry exists!
				else if( (entry.getSecondary() == null && s != null) || 
						(entry.getSecondary() != null && s == null) )
					return false;
				else if(entry.getSecondary().equals(s))
					return true; //entry exists!
			}
		}
		return false; //entry doesn't exist.
			
	}
	
	//search for hashed key
	//if not found, insert Entry into hash table
	public void insert(String p, String s, Entry e){
		
		//if(search(p,s) == true)
			//System.out.println("duplicate key error @ " + hash(p,s));
		//else
		//{
			int bucketIndex = hash(p,s);
			if(a[bucketIndex] == null)
				a[bucketIndex] = new LinkedList();
			a[bucketIndex].add(e);
		//}
		
	}
	
	//use hashed key (p,s) to retrieve Entry
	public Entry getEntry(String p, String s){
		
		//first find bucket
		int bucketIndex = hash(p,s);

		if(bucketIndex == SIZE || a[bucketIndex] == null)
		{	
			//System.out.println("No bucket ; ;");
			return null;
		}
		
		//found bucket, search through Linked List with iterator
		Iterator it = a[bucketIndex].listIterator();
		while(it.hasNext())
		{
			Entry entry = (Entry)it.next();
			if(entry.getPrimary().equals(p))
			{
				//System.out.println("1st if");
				if((entry.getSecondary() == null && s == null) || entry.getSecondary().equals(s));
					return entry; //entry exists!
			}
		}
		System.out.println("Entry doesn't exist.");
		return null; //entry doesn't exist.
			
	}
	
	//print the entries held in the hash table
	public void printEachBucketContents()
	{
		//Test for contents of each bucket
		for(int i=0; i<getSize(); i++)
		{
			if(a[i] != null)
			{
				Iterator it = a[i].listIterator();
				System.out.println("-----------Bucket [" + i + "] Entries-------------------------");
				System.out.println("SIZE of Bucket:\t " + a[i].size());
				while(it.hasNext())
				{
					Entry e = (Entry)it.next();
					System.out.print("\t" + e.getPrimary() + " / " + e.getSecondary() + "\t");
					System.out.println("Pages: " + e.getPages());
				}
			}
			else
			{
				System.out.println("-----------Bucket [" + i + "] Entries-------------------------");
				System.out.println("SIZE of Bucket:\t 0");
			}
		}
	}
}
