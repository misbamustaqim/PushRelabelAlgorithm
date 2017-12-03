import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
	
	private Map<String, Vertex> verticesByName;
	private Set<Edge> edges;
	
	public Graph()
	{
		this.verticesByName = new HashMap<String, Vertex>();
		this.edges = new HashSet<Edge>();
	}
	
	public void addEdge(String firstVertexName, String secondVertexName, int capacity) throws Exception
	{
		Vertex firstVertex;
		Vertex secondVertex;
		
		// try to get or add the first and second vertices
		if(!this.verticesByName.containsKey(firstVertexName))
		{
			firstVertex = new Vertex(firstVertexName);
			this.verticesByName.put(firstVertexName, firstVertex);
		}
		else
		{
			firstVertex = this.verticesByName.get(firstVertexName);
		}
		
		if(!this.verticesByName.containsKey(secondVertexName))
		{
			secondVertex = new Vertex(secondVertexName);
			this.verticesByName.put(secondVertexName, secondVertex);
		}
		else
		{
			secondVertex = this.verticesByName.get(secondVertexName);
		}
		
		// add Graph edge between first and second vertex and Residual edge between second and first vertex
		Edge graphEdge = new Edge(firstVertex, secondVertex, capacity, EdgeType.Graph);
		
		firstVertex.addEdge(graphEdge);
		
		// We should set the residual edge flow to max when initializing
		Edge residualEdge = new Edge(secondVertex, firstVertex, capacity, EdgeType.Resudual);
		residualEdge.setFlow(capacity);
		
		secondVertex.addEdge(residualEdge);
		
		this.edges.add(graphEdge);
		this.edges.add(residualEdge);
	}
	
	public ArrayList<Vertex> getNeighbors(Vertex rootvertex) throws Exception
	{
		assert(rootvertex != null);
		
		ArrayList<Vertex> neighbors = new ArrayList<>();
		
		for (Edge edge : rootvertex.getOutEdges()) {
			neighbors.add(edge.getOutVertex());
		}
		
		return neighbors;
	}
	
	public Vertex getVertex(String vertexName) throws Exception
	{
		if(!this.verticesByName.containsKey(vertexName))
		{
			throw new Exception("vertex " + vertexName + " was not found in graph");
		}
		
		return this.verticesByName.get(vertexName);
	}
	
	public int getVertexCount()
	{
		return this.verticesByName.size();
	}
	
	public HashSet<Vertex> getAllVertices()
	{
		return new HashSet<>(this.verticesByName.values());
	}
	
	public HashSet<Edge> getAllEdges()
	{
		return new HashSet<>(this.edges);
	}
}
