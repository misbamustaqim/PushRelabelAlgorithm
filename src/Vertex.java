import java.util.HashSet;

public class Vertex {
	
	private String vertexName;
	private int height;
	private int excessFlow;
	
	private HashSet<Edge> outEdges; 
	
	public Vertex(String vertexName) throws Exception
	{
		if(vertexName == null || vertexName.isEmpty())
		{
			throw new Exception("vertexName is null or empty");
		}
		
		this.vertexName = vertexName;
		this.height = 0;
		this.excessFlow = 0;
		this.outEdges = new HashSet<Edge>();
	}
		
	public String getVertexName() {
		return vertexName;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void initializeSourceHeight(int height) throws Exception
	{
		if(height < 0 || !this.vertexName.equals("s"))
		{
			throw new Exception("height should not be negative and only source height can be initialized");
		}
		this.height = height;
	}
	
	public void setHeight(int height) throws Exception {
		
		assert(height < 0);
				
		if(this.vertexName.equals("s") || this.vertexName.equals("t"))
		{
			throw new Exception("Cannot change height of source or sink");
		}
		
		this.height = height;
	}
	
	public int getExcessFlow() {
		return excessFlow;
	}
	
	public void setExcessFlow(int excessFlow) throws Exception {
		
		if(!this.vertexName.equals("s") && excessFlow < 0)
		{
			throw new Exception("If the vertex is not source then excessFlow cannot be negative");
		}
		
		this.excessFlow = excessFlow;
	}

	public HashSet<Edge> getOutEdges() {
		return new HashSet<Edge>(this.outEdges);
	}
	
	public void addEdge(Edge edge) throws Exception
	{
		if(edge == null || this.outEdges.contains(edge) || !edge.getInVertex().equals(this))
		{
			throw new Exception("edge is null or already added or the invertex of edge doesnt match with this vertex");
		}
		
		this.outEdges.add(edge);
	}
	
	public boolean equals(Object obj) 
	{
		if(obj == null)
		{
			return false;
		}
		
		Vertex vertex = ( Vertex )obj;
		
		if(this.vertexName.equals(vertex.vertexName))
		{
			return true;
		}
		
		return false;
	}
}
