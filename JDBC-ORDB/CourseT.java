/**
 * Author: Ankush Varshneya (100853074)
 * Assignment#5 Part#4 - course_t Object
 * CREATE TYPE course_t AS OBJECT (
 *  c#                VARCHAR2(4),
 *  name              VARCHAR2(10),
 *  preReqCourses     course_nt,
 *  studentsEnrolled  student_nt
 * );
 */
 
import java.sql.SQLException;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class CourseT implements SQLData
{
  public static final String  _SQL_NAME = "COURSE_T";
  public static final int     _SQL_TYPECODE = OracleTypes.STRUCT;
  
  private String              _cnum;
  private String              _name;
  private java.sql.Array      _preReqCourses;
  private java.sql.Array      _studentsEnrolled;

  public CourseT() {
  }

  public CourseT( String          cnum,
                  String          name,
                  java.sql.Array  preReqCourses,
                  java.sql.Array  studentsEnrolled)
  throws SQLException {
    setCnum(cnum);
    setName(name);
    setPreReqCourses(preReqCourses);
    setStudentsEnrolled(studentsEnrolled);
  }
  
  public void readSQL(SQLInput stream, String type)
  throws SQLException { 
    setCnum(stream.readString());
    setName(stream.readString());
    setPreReqCourses(stream.readArray());
    setStudentsEnrolled(stream.readArray());
  }

  public void writeSQL(SQLOutput stream)
  throws SQLException {
    stream.writeString(getCnum());
    stream.writeString(getName());
    stream.writeArray(getPreReqCourses());
    stream.writeArray(getStudentsEnrolled());
  }

  public String getSQLTypeName() throws SQLException
  { return _SQL_NAME; }

  public String getCnum()
  { return _cnum; }

  public void setCnum(String cnum)
  { _cnum = cnum; }


  public String getName()
  { return _name; }

  public void setName(String name)
  { _name = name; }


  public java.sql.Array getPreReqCourses()
  { return _preReqCourses; }

  public void setPreReqCourses(java.sql.Array preReqCourses)
  { _preReqCourses = preReqCourses; }

  
  public java.sql.Array getStudentsEnrolled()
  { return _studentsEnrolled; }

  public void setStudentsEnrolled(java.sql.Array studentsEnrolled)
  { _studentsEnrolled = studentsEnrolled; }
  
}