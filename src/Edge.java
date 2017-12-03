
public class Edge  {
	
	private Vertex inVertex;
	
	private Vertex outVertex;
	
	private int capacity;
	
	private int flow;
	
	private EdgeType edgeType;
	
	public Edge(Vertex inVertex, Vertex outVertex, int capacity, EdgeType edgeType) throws Exception
	{
		if(inVertex == null || outVertex == null)
		{
			throw new Exception("InVertex or OutVertex is null");
		}
		
		if(capacity < 0)
		{
			throw new Exception("capacity should be more than or equal to 0");
		}
		
		this.inVertex = inVertex;
		this.outVertex = outVertex;
		this.capacity = capacity;
		this.flow = 0;
		this.edgeType = edgeType;
	}

	public Vertex getInVertex() {
		return inVertex;
	}

	public Vertex getOutVertex() {
		return outVertex;
	}

	public int getRemainingCapacity() {
		return capacity - this.flow;
	}

	public int getFlow() {
		return this.flow;
	}

	public void setFlow(int flow) throws Exception {
		
		if(flow < 0 || flow > this.capacity)
		{
			throw new Exception("flow should be more than or equal to 0 and should not be more than capacity");
		}
		
		this.flow = flow;
	}

	public EdgeType getEdgeType() {
		return edgeType;
	}
	
	public boolean equals(Object obj) 
	{
		if(obj == null)
		{
			return false;
		}
		
		Edge edge = ( Edge )obj;
		
		if(this.inVertex.equals(edge.inVertex) && this.outVertex.equals(edge.outVertex))
		{
			return true;
		}
		
		return false;
	}
}
