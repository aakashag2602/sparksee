
import com.sparsity.sparksee.gdb.*;

import java.io.FileNotFoundException;

public class HelloSparksee {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException
    {
	//
        // Create a sample database
        //
        SparkseeConfig cfg = new SparkseeConfig();
        Sparksee sparksee = new Sparksee(cfg);
        Database db = sparksee.create("HelloSparksee.gdb", "HelloSparksee"); 
        //Database db = sparksee.open("HelloSparksee.gdb", true);
        Session sess = db.newSession(); 
        Graph g = sess.getGraph();
        Runtime instance = Runtime.getRuntime();

	// 
	// SCHEMA
	//
        int movieType, castType, peopleType, directsType;
        // Add a node type for the movies, with a unique identifier and two indexed attributes
        int start_mem = (int) instance.totalMemory();
        int start_mem1 = (int) instance.freeMemory();
        int start_mem2 = start_mem - start_mem1;
        System.out.println("Start Mem: "+start_mem+" Start Mem1: "+start_mem1);
        long start_time = System.nanoTime();
        movieType = g.newNodeType("MOVIE"); 
        long end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating node type: "+(end_time - start_time));
        movieType = g.findType("MOVIE");
        System.out.println("Inside: "+movieType);
        if(Type.InvalidType != movieType)
        	System.out.println(g.countNodes());
        start_time = System.nanoTime();
        int movieIdType = g.newAttribute(movieType, "ID", DataType.Long, AttributeKind.Unique);
        int movieTitleType = g.newAttribute(movieType, "TITLE", DataType.String, AttributeKind.Indexed);
        int movieYearType = g.newAttribute(movieType, "YEAR", DataType.Integer, AttributeKind.Indexed);
        end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating node attributes: "+(end_time - start_time));
        

	// Add a node type for the people, with a unique identifier and an indexed attribute
        start_time = System.nanoTime();
        peopleType = g.newNodeType("PEOPLE");
        end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating node type: "+(end_time - start_time));
        //peopleType = g.findType("PEOPLE");
        //if(Type.InvalidType != peopleType)
        //	System.out.println("Inside: "+peopleType);
        start_time = System.nanoTime();
        int peopleIdType = g.newAttribute(peopleType, "ID", DataType.Long, AttributeKind.Unique);
        int peopleNameType = g.newAttribute(peopleType, "NAME", DataType.String, AttributeKind.Indexed);
        end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating node attributes: "+(end_time - start_time));

        // Add an undirected edge type with an attribute for the cast of a movie
        castType = g.newEdgeType("CAST", false, false);
       // castType = g.findType("CAST");
       // if(Type.InvalidType != castType)
       // 	System.out.println(castType);
        int castCharacterType = g.newAttribute(castType, "CHARACTER", DataType.String, AttributeKind.Basic);

	// Add a directed edge type restricted to go from people to movie for the director of a movie
        directsType = g.newRestrictedEdgeType("DIRECTS", peopleType, movieType, false);
       // directsType = g.findType("DIRECTS");
       // if(Type.InvalidType != directsType)
       // 	System.out.println(directsType);


	// 
	// DATA
	//

	// Add some MOVIE nodes
	Value value = new Value();

	//g.newNode(arg0)
	start_time = System.nanoTime();
	long mLostInTranslation = g.newNode(movieType);
    //System.out.println("mLostInTransaction: "+mLostInTranslation);
	g.setAttribute(mLostInTranslation, movieIdType, value.setLong(1));
	g.setAttribute(mLostInTranslation, movieTitleType, value.setString("Lost in Translation"));
	g.setAttribute(mLostInTranslation, movieYearType, value.setInteger(2003));
	end_time = System.nanoTime();
    System.out.println("Time Elapsed for creating node type LIT: "+(end_time - start_time));

	start_time = System.nanoTime();
	long mVickyCB = g.newNode(movieType);
        //System.out.println("mLostInTransaction: "+mVickyCB);
	g.setAttribute(mVickyCB, movieIdType, value.setLong(2));
	g.setAttribute(mVickyCB, movieTitleType, value.setString("Vicky Cristina Barcelona"));
	g.setAttribute(mVickyCB, movieYearType, value.setInteger(2008));
	end_time = System.nanoTime();
    System.out.println("Time Elapsed for creating node type: VCB"+(end_time - start_time));

        start_time = System.nanoTime();
	long mManhattan = g.newNode(movieType);
        //System.out.println("mLostInTransaction: "+mManhattan);
	g.setAttribute(mManhattan, movieIdType, value.setLong(3));
	g.setAttribute(mManhattan, movieTitleType, value.setString("Manhattan"));
	g.setAttribute(mManhattan, movieYearType, value.setInteger(1979));
	end_time = System.nanoTime();
    System.out.println("Time Elapsed for creating node type: "+(end_time - start_time));
    

        for (int i=0;i<1024;i++){
            start_time = System.nanoTime();
            long mRay = g.newNode(movieType);
            g.setAttribute(mManhattan, movieIdType, value.setLong(i+4));
        	g.setAttribute(mManhattan, movieTitleType, value.setString("M"));
        	g.setAttribute(mManhattan, movieYearType, value.setInteger(1979));
           // System.out.println("MovieType Node Val: " + mRay);
	    end_time = System.nanoTime();
            //System.out.println("Time Elapsed for creating node type in loop: "+(end_time - start_time));
        }

        
	// Add some PEOPLE nodes
        start_time = System.nanoTime();
	long pScarlett = g.newNode(peopleType);
        //System.out.println("Cast: "+pScarlett);
	g.setAttribute(pScarlett, peopleIdType, value.setLong(1));
	g.setAttribute(pScarlett, peopleNameType, value.setString("Scarlett Johansson"));
	end_time = System.nanoTime();
    System.out.println("Time Elapsed for creating people: "+(end_time - start_time));

        start_time = System.nanoTime();
	long pBill = g.newNode(peopleType);
        //System.out.println("Cast: "+pBill);
	g.setAttribute(pBill, peopleIdType, value.setLong(2));
	g.setAttribute(pBill, peopleNameType, value.setString("Bill Murray"));
	end_time = System.nanoTime();
    System.out.println("Time Elapsed for creating people: "+(end_time - start_time));

	long pSofia = g.newNode(peopleType);
        System.out.println("Cast: "+pSofia);
	g.setAttribute(pSofia, peopleIdType, value.setLong(3));
	g.setAttribute(pSofia, peopleNameType, value.setString("Sofia Coppola"));

	long pWoody = g.newNode(peopleType);
        System.out.println("Cast: "+pWoody);
	g.setAttribute(pWoody, peopleIdType, value.setLong(4));
	g.setAttribute(pWoody, peopleNameType, value.setString("Woody Allen"));

	long pPenelope = g.newNode(peopleType);
        System.out.println("Cast: "+pPenelope);
	g.setAttribute(pPenelope, peopleIdType, value.setLong(5));
	g.setAttribute(pPenelope, peopleNameType, value.setString("Penelope Cruz"));

        start_time = System.nanoTime();
	long pDiane = g.newNode(peopleType);
        //System.out.println("Cast: "+pDiane);
	g.setAttribute(pDiane, peopleIdType, value.setLong(6));
	g.setAttribute(pDiane, peopleNameType, value.setString("Diane Keaton"));
	end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating people: "+(end_time - start_time));


	
	// Add some CAST edges
        start_time = System.nanoTime();
	long anEdge;
	anEdge = g.newEdge(castType, mLostInTranslation, pScarlett);
        //System.out.println("Edges: "+anEdge);
	g.setAttribute(anEdge, castCharacterType, value.setString("Charlotte"));
	end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating undirection Edge: "+(end_time - start_time));
	
        start_time = System.nanoTime();
	anEdge = g.newEdge(castType, mLostInTranslation, pBill);
        //System.out.println("Edges: "+anEdge);
	g.setAttribute(anEdge, castCharacterType, value.setString("Bob Harris"));
	end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating undirectional edge: "+(end_time - start_time));

	anEdge = g.newEdge(castType, mVickyCB, pScarlett);
        System.out.println("Edges: "+anEdge);
	g.setAttribute(anEdge, castCharacterType, value.setString("Cristina"));

	anEdge = g.newEdge(castType, mVickyCB, pPenelope);
        System.out.println("Edges: "+anEdge);
	g.setAttribute(anEdge, castCharacterType, value.setString("Maria Elena"));

	anEdge = g.newEdge(castType, mManhattan, pDiane);
        System.out.println("Edges: "+anEdge);
	g.setAttribute(anEdge, castCharacterType, value.setString("Mary"));

        start_time = System.nanoTime();
	anEdge = g.newEdge(castType, mManhattan, pWoody);
        //System.out.println("Edges: "+anEdge);
	g.setAttribute(anEdge, castCharacterType, value.setString("Isaac"));
	end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating undirectional edge: "+(end_time - start_time));
        
	// Add some DIRECTS edges
        start_time = System.nanoTime();
	anEdge = g.newEdge(directsType, pSofia, mLostInTranslation);
        //System.out.println("Edges: "+anEdge);
	end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating directional edge: "+(end_time - start_time));

	anEdge = g.newEdge(directsType, pWoody, mVickyCB);
        System.out.println("Edges: "+anEdge);

        start_time = System.nanoTime();
	anEdge = g.newEdge(directsType, pWoody, mManhattan);
        //System.out.println("Edges: "+anEdge);
	end_time = System.nanoTime();
        System.out.println("Time Elapsed for creating directional edge: "+(end_time - start_time));
        
	System.out.println(g.countNodes() + " Edge: "+g.countEdges());
	
	int end_mem = (int) instance.totalMemory();
    int end_mem1 = (int) instance.freeMemory();
    int end_mem2 = end_mem - end_mem1;
    int mb = 1024 * 1024;
    System.out.println("Total Memory for addtriplet api: " + (end_mem2 - start_mem2)/1024 + " used_mem:" + end_mem2 + " " +start_mem2 +" "+ end_mem1 +" "+ end_mem);
    

	// 
	// QUERIES
	//

	// Get the movies directed by Woody Allen
	//Objects directedByWoody = g.neighbors(pWoody, directsType, EdgesDirection.Outgoing);

	// Get the cast of the movies directed by Woody Allen
	//Objects castDirectedByWoody = g.neighbors(directedByWoody, castType, EdgesDirection.Any);
	
	// We don't need the directedByWoody collection anymore, so we should close it
	//directedByWoody.close();


	// Get the movies directed by Sofia Coppola
	//Objects directedBySofia = g.neighbors(pSofia, directsType, EdgesDirection.Outgoing);

	// Get the cast of the movies directed by Sofia Coppola
	//Objects castDirectedBySofia = g.neighbors(directedBySofia, castType, EdgesDirection.Any);
	
	// We don't need the directedBySofia collection anymore, so we should close it
	//directedBySofia.close();


	// We want to know the people that acted in movies directed by Woody AND in movies directed by Sofia.
	//Objects castFromBoth = Objects.combineIntersection(castDirectedByWoody, castDirectedBySofia);

	/*ObjectsIterator it = castDirectedBySofia.iterator();
    while (it.hasNext())
    {
        long peopleOid = it.next();
        g.getAttribute(peopleOid, peopleNameType, value);
        System.out.println("Sofia: Hello " + value.getString());
    }
	
    it = castDirectedByWoody.iterator();
    while (it.hasNext())
    {
        long peopleOid = it.next();
        g.getAttribute(peopleOid, peopleNameType, value);
        System.out.println("Woody: Hello " + value.getString());
    }
    
	// We don't need the other collections anymore
	castDirectedByWoody.close();
	castDirectedBySofia.close();

        // Say hello to the people found
        it = castFromBoth.iterator();
        while (it.hasNext())
        {
            long peopleOid = it.next();
            g.getAttribute(peopleOid, peopleNameType, value);
            System.out.println("Hello " + value.getString());
        }
        // The ObjectsIterator must be closed
        it.close();

        // The Objects must be closed
        castFromBoth.close();
	 */

	//
        // Close the database
	//
        sess.close();
        db.close();
        sparksee.close();
    }
}
