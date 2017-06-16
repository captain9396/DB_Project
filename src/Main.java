/**
 * Created by Syed Mostofa Monsur on 20/05/2017.
*/
import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.*;
import stackoverflowdatabase.Student;
import stackoverflowdatabase.Votes;

import java.io.File;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        try {
            EnvironmentConfig environmentConfig = new EnvironmentConfig();
            environmentConfig.setAllowCreate(true);

            Environment newEnvironment = new Environment(
                    new File("G:\\10101010101010\\L3T1\\MY COURSE\\DATABASE SESSIONAL\\project") , environmentConfig);

            DatabaseConfig databaseConfig = new DatabaseConfig();
            databaseConfig.setAllowCreate(true);

            Database testDatabase = newEnvironment.openDatabase(null , "test" , databaseConfig);

            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry value = new DatabaseEntry();

            ArrayList<Votes> voteRecords = Votes.parseVotes("G:\\10101010101010\\L3T1\\MY COURSE\\" +
                    "DATABASE SESSIONAL\\XML DATASET\\DataScience\\Votes.xml");
//            System.out.println("Here : " + voteRecords.size());
//            System.out.println(voteRecords.get(0).getVoteId());
//            System.out.println(voteRecords.get(0).getPostId());
//            System.out.println(voteRecords.get(0).getVoteTypeId());
//            System.out.println(voteRecords.get(0).getCreationDate());



            for(int i = 0 ; i < voteRecords.size() ; i++){
                IntegerBinding.intToEntry(i , key);
                testDatabase.put(null, key , voteRecords.get(i).objectToEntry());
            }

            IntegerBinding.intToEntry(40000,key);


            if(testDatabase.get(null , key , value , null) == OperationStatus.SUCCESS){
                Votes newVotes = new Votes();
                newVotes.entryToObject(value);

                System.out.println(newVotes.getVoteId());
                System.out.println(newVotes.getPostId());
                System.out.println(newVotes.getVoteTypeId());
                System.out.println(newVotes.getCreationDate());
            }else{
                System.out.println("Record not found");
            }






            testDatabase.close();
            //newEnvironment.removeDatabase(null , "test");
            newEnvironment.close();

        } catch (DatabaseException dbe) {
            System.out.println("Error :" + dbe.getMessage());
        }
    }

}

