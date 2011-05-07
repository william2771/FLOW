package flow.structure;

import java.util.ArrayList;


@SuppressWarnings("serial")
public class FlowList<E> extends ArrayList<E>
{

	

	public FlowList()
	{
			super();
			
	}

	public FlowList(Collection<E> c)
	    {
		super();

		this.addAll(c);

	    }


	
	public void push(E item)
	{
		this.add(0, item);
	}
	
	public E peek()
	{
		return this.get(0);
	}
	
	public E pop()
	{
		E item = this.get(0);
		this.remove(0);
		
		return item;
		
	}
	
	public void enqueue(E item)
	{
	
		this.add(item);
		
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
	
	public int getlength(){
	    return this.size();
	}
	
	public void join(FlowList<E> other)
	{
		this.addAll(other);
	}

	

}