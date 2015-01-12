import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Vertex implements Comparable<Vertex>
{
    public final String name;
    public ArrayList<Edge> adjacencies =new ArrayList<>();
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
    
    public void addEdge(Edge edge){
    	if (!adjacencies.contains(edge)) adjacencies.add(edge);
    	return;
    }
    
}

class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Edge)) {
			return false;
		}
		Edge other = (Edge) obj;
		if (target == null) {
			if (other.target != null) {
				return false;
			}
		} else if (!target.equals(other.target)) {
			return false;
		}
		return true;
	}
    
}

public class Dijkstra
{
    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args)
    {
//    Vertex v0 = new Vertex("Redvile");
//	Vertex v1 = new Vertex("Blueville");
//	Vertex v2 = new Vertex("Greenville");
//	Vertex v3 = new Vertex("Orangeville");
//	Vertex v4 = new Vertex("Purpleville");
//	
//	v0.addEdge(new Edge(v1, 5));
//	v0.addEdge(new Edge(v2, 10));
//	v0.addEdge(new Edge(v3, 8));
//
//	v1.addEdge(new  Edge(v0, 5));
//	v1.addEdge(new  Edge(v2, 3));
//	v1.addEdge(new  Edge(v4, 7));
//	
//	v2.addEdge ( new Edge(v0, 10));
//	v2.addEdge (  new Edge(v1, 3));
//
//	v3.addEdge (new Edge(v0, 8));
//	v3.addEdge(new  Edge(v4, 2));
//	
//	v4.addEdge( new Edge(v1, 7));
//	v4.addEdge( new Edge(v3, 2));
//
//
//	Vertex[] vertices = { v0, v1, v2, v3, v4 };
    	Graph graph = new Graph(100, 20);
    	
        computePaths(graph.vertices.get(0));
        for (Vertex v : graph.vertices)
	{
	    System.out.println("Distance to " + v + ": " + v.minDistance);
	    List<Vertex> path = getShortestPathTo(v);
	    System.out.println("Path: " + path);
	}
    }
}

class Graph {
	public ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	
	public Graph (int size, int precent) {
		// Add Veretices:
		for (int i=0;i<size;i++) 
			vertices.add(new Vertex("v"+i));
		// Add edges to verteics:
		for (Vertex v1:vertices) { // go over all the vertiecs just created
			for (Vertex v2:vertices){ //add a edge with a veritce to the above vertice
				Random Rand = new Random();
				int weight = Rand.nextInt(100);
				if (Rand.nextInt(100) <= precent){
					v1.addEdge(new Edge(v2, weight));
					v2.addEdge(new Edge(v1, weight));
				}//end if
			}
		}
	}
}