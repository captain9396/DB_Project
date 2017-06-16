package stackoverflowdatabase;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.je.DatabaseEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 5/23/2017.
 */
public class Votes {
    private String voteId;
    private String postId;
    private String voteTypeId;
    private String creationDate;



    public Votes(String voteId , String postId , String voteTypeId , String creationDate){
        this.voteId = voteId;
        this.postId = postId;
        this.voteTypeId = voteTypeId;
        this.creationDate = creationDate;
    }

    public Votes() {}

    public void setVoteId(String voteId){ this.voteId = voteId;}
    public void setPostId(String postId){ this.postId = postId;}
    public void setVoteTypeId(String voteTypeId){ this.voteTypeId = voteTypeId;}
    public void setCreationDate(String creationDate){ this.creationDate = creationDate;}

    public String getVoteId(){return voteId;}
    public String getPostId(){ return postId;}
    public String getVoteTypeId(){return voteTypeId;}
    public String getCreationDate(){return creationDate;}




    public DatabaseEntry objectToEntry(){
        TupleOutput tupleOutput = new TupleOutput();
        DatabaseEntry databaseEntry = new DatabaseEntry();

        tupleOutput.writeString(voteId);
        tupleOutput.writeString(postId);
        tupleOutput.writeString(voteTypeId);
        tupleOutput.writeString(creationDate);

        TupleBinding.outputToEntry(tupleOutput , databaseEntry);
        return databaseEntry;
    }




    public void entryToObject(DatabaseEntry databaseEntry){
        TupleInput tupleInput = TupleBinding.entryToInput(databaseEntry);
        this.voteId =tupleInput.readString();
        this.postId = tupleInput.readString();
        this.voteTypeId = tupleInput.readString();
        this.creationDate = tupleInput.readString();
    }


    public static ArrayList<Votes> parseVotes(String fileDirectory){
        ArrayList<Votes> voteRecords = new ArrayList<>();
        try {
            File fXmlFile = new File( fileDirectory );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document newDocument = dBuilder.parse(fXmlFile);
            newDocument.getDocumentElement().normalize();

            NodeList nodeList = newDocument.getElementsByTagName("row");

            for(int i = 0 ; i < nodeList.getLength(); i++){
                Node nodeItem = nodeList.item( i );
                if ( nodeItem.getNodeType() == Node.ELEMENT_NODE) {
                    Element nodeElement = (Element) nodeItem;
                    Votes newVotes = new Votes(
                      nodeElement.getAttribute("Id"),nodeElement.getAttribute("PostId"),
                      nodeElement.getAttribute("VoteTypeId"),nodeElement.getAttribute("CreationDate")
                    );
                    voteRecords.add( newVotes );
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return voteRecords;
    }


}
