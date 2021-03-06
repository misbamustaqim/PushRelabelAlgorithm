import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PushRelabel {
	
	private Graph graph;
	private boolean isInitialized;
	private int maxFlow;
	private Queue<Vertex> relabelVertexQueue;
	private int relabelCount = 0;
	private int pushCount = 0;
	
	public PushRelabel()
	{
		this.graph = new Graph();
		this.isInitialized = false;
		this.relabelVertexQueue = new LinkedList<Vertex>();
	}
	
	public void addEdge(String firstVertex, String secondVertex, int capacity) throws Exception
	{
		if(this.isInitialized)
		{
			throw new Exception("Cannot add an edge after the class is initialized");
		}
		
		this.graph.addEdge(firstVertex, secondVertex, capacity);		
	}
	
	public int getMaxFlow() throws Exception
	{
		if(this.isInitialized)
		{
			return this.maxFlow;
		}
		
		this.initialize();
		
		this.calculateMaxFlow();
		
		return this.maxFlow;
	}
	
	private void calculateMaxFlow() throws Exception
	{		
		while (relabelVertexQueue.size() > 0) {
			
			Vertex vertex = this.relabelVertexQueue.poll();

			if (vertex.getExcessFlow() > 0) {
				for (Edge edge : vertex.getOutEdges()) {

					if (edge.getRemainingCapacity() > 0 && edge.getOutVertex().getHeight() >= vertex.getHeight()) {

						this.relabel(vertex);
						this.tryToPushForExcessFlow(vertex);
						
						break;
					}
				}
			}
		}
		
		System.out.println("RelabelCount: " + this.relabelCount);
		System.out.println("PushCount: " + this.pushCount);

		this.maxFlow = this.graph.getVertex("t").getExcessFlow();
	}

	private void tryToPushForExcessFlow(Vertex vertexWithOverflow) throws Exception {
		
		boolean isPushSuccessful = false;
		
		for (Edge edge : vertexWithOverflow.getOutEdges()) {
			
			Vertex secondVertex = edge.getOutVertex();
			
			if(vertexWithOverflow.getExcessFlow() > 0 
					&& edge.getRemainingCapacity() > 0
					&& vertexWithOverflow.getHeight() == secondVertex.getHeight() + 1)
			{
				this.push(edge);
				
				isPushSuccessful = true;
				
				Vertex innerSecondVertex = edge.getOutVertex();
				
				tryToPushForExcessFlow(innerSecondVertex);
			}
			
			if(isPushSuccessful == false && vertexWithOverflow.getExcessFlow() > 0 && !vertexWithOverflow.getVertexName().equals("t"))
			{
				this.relabelVertexQueue.add(vertexWithOverflow);
			}
		}
	}
	
	private void initialize() throws Exception
	{
		if(this.isInitialized)
		{
			return;
		}
		
		Vertex source = this.graph.getVertex("s");
		
		// set source height to |V|
		source.initializeSourceHeight(this.graph.getVertexCount());
		
		// initialize preflow
		for (Edge edge : source.getOutEdges()) {
			
			int edgeCapacity = edge.getRemainingCapacity();
			
			Edge reverseEdge = null;
			
			for (Edge rEdge : edge.getOutVertex().getOutEdges()) {
				if(rEdge.getOutVertex().equals(source) && rEdge.getEdgeType() != edge.getEdgeType())
				{
					reverseEdge = rEdge;
					break;
				}
			}
			
			edge.setFlow(edgeCapacity);
			reverseEdge.setFlow(0);
			
			edge.getOutVertex().setExcessFlow(edgeCapacity);
			
			this.relabelVertexQueue.add(edge.getOutVertex());
			
			int sourceExcessFlow = source.getExcessFlow();
			
			source.setExcessFlow(sourceExcessFlow - edgeCapacity);
		}
		
		this.isInitialized = true;
	}
	
	private void push(Edge edge) throws Exception
	{
		Vertex firstVertex = edge.getInVertex();
		Vertex secondVertex = edge.getOutVertex();
		
		assert(firstVertex.getExcessFlow() > 0);
		assert(firstVertex.getHeight() == secondVertex.getHeight() + 1);
		
		int deltaFlow = Math.min(firstVertex.getExcessFlow(), edge.getRemainingCapacity());
		
		//System.out.println("Pushing " + deltaFlow + " from " + firstVertex.getVertexName() + " to " + secondVertex.getVertexName());
				
		Edge reverseEdge = null;
		
		for (Edge rEdge : secondVertex.getOutEdges()) {
			if(rEdge.getOutVertex().equals(firstVertex) && rEdge.getEdgeType() != edge.getEdgeType())
			{
				reverseEdge = rEdge;
				break;
			}
		}
		
		edge.setFlow(edge.getFlow() + deltaFlow);
		reverseEdge.setFlow(reverseEdge.getFlow() - deltaFlow);
		
		firstVertex.setExcessFlow(firstVertex.getExcessFlow() - deltaFlow);
		secondVertex.setExcessFlow(secondVertex.getExcessFlow() + deltaFlow);
		
		this.pushCount++;
	}
	
	private void relabel(Vertex vertex) throws Exception
	{
		assert(vertex.getExcessFlow() > 0);
		
		int minHeight = Integer.MAX_VALUE;
		
		for (Edge edge : vertex.getOutEdges()) {
			
			if(edge.getRemainingCapacity() > 0)
			{
				Vertex secondVertex = edge.getOutVertex();
				
				assert(vertex.getHeight() <= secondVertex.getHeight());    // (vertex.getHeight() <= secondVertex.getHeight() = true do not relabel
				
				if(minHeight > secondVertex.getHeight())
				{
					minHeight = secondVertex.getHeight();
				}
			}
		}
		
		//System.out.println("Relabling " + vertex.getVertexName() + " old: " + vertex.getHeight() + " new: " + (minHeight + 1));
		
		vertex.setHeight(minHeight + 1);
		
		this.relabelCount++;
	}
}
