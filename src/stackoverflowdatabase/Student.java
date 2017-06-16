package stackoverflowdatabase;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.je.DatabaseEntry;

/**
 * Created by User on 5/20/2017.
 */
public class Student {
    private String studentName;
    private int studentID;
    private String departmentName;
    private double studentCGPA;

    public Student(){

    }
    public Student(String studentName , int studentID , String departmentName , double studentCGPA){
        this.studentName = studentName;
        this.studentID = studentID;
        this.departmentName =departmentName;
        this.studentCGPA = studentCGPA;
    }


    public void setStudentName(String studentName){
        this.studentName = studentName;
    }
    public void setStudentID(int studentID){
        this.studentID = studentID;
    }
    public void setDepartmentName(String departmentName){
        this.departmentName = departmentName;
    }
    public void setStudentCGPA(double studentCGPA){
        this.studentCGPA = studentCGPA;
    }


    public String getStudentName(){
        return studentName;
    }
    public int getStudentID(){ return studentID;}
    public String getDepartmentName(){ return departmentName;}
    public double getStudentCGPA(){ return studentCGPA;}



    public DatabaseEntry objectToEntry() {

        TupleOutput output = new TupleOutput();
        DatabaseEntry entry = new DatabaseEntry();

        // write to tuple
        output.writeString(getStudentName());
        output.writeInt(getStudentID());
        output.writeString(getDepartmentName());
        output.writeDouble(getStudentCGPA());


        TupleBinding.outputToEntry(output, entry);

        return entry;
    }



    // convert entry to object
    public Student entryToObject(DatabaseEntry entry) {

        TupleInput input = TupleBinding.entryToInput(entry);
        Student tempStudent = new Student();

        // set name, id , department and cgpa
        tempStudent.setStudentName(input.readString());
        tempStudent.setStudentID(input.readInt());
        tempStudent.setDepartmentName(input.readString());
        tempStudent.setStudentCGPA(input.readDouble());

        return tempStudent;
    }

}
