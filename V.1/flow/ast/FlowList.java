import java.util.ArrayList;

public class FlowList<E>
{

	private ArrayList<E> myList;

	public FlowList()
	{
			myList = new ArrayList<E>();
			
	}
	
	public void push(E item)
	{
		myList.add(0, item);
	}
	
	public E peek()
	{
		return myList.get(0);
	}
	
	public E pop()
	{
		E item = myList.get(0);
		myList.remove(0);
		
		return item;
		
	}
	
	public void enqueue(E item)
	{
	
		myList.add(item);
		
	}
	
	public E dequeue()
	{
		return this.pop();
	}
	
	public void append(E item)
	{
		this.enqueue(item);
	}
	
	public void prepend(E item)
	{
		this.push(item);
	}
	
	public void remove(int index)
	{
	
		myList.remove(index);
	
	}
	
	public void join(FlowList<E> other)
	{
		myList.addAll(other.getList());
	}
	
	public ArrayList<E> getList()
	{
	
		return myList;
		
	}
	
	public E get(int index)
	{
		return myList.get(index);
	}

}