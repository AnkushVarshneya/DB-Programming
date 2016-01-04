/**
 * Author: Ankush Varshneya (100853074)
 * Assignment#5 Part#4 - student_t Object
 * CREATE TYPE student_t AS OBJECT (
 *  s#                VARCHAR2(4),
 *  name              VARCHAR2(10),
 *  gender            CHAR(1),
 *  coursesEnrolled   course_nt
 * ) NOT FINAL;
 */
 
import java.sql.SQLException;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

abstract public class StudentT implements SQLData
{
  public static final String  _SQL_NAME = "STUDENT_T";
  public static final int     _SQL_TYPECODE = OracleTypes.STRUCT;
  
  private String              _snum;
  private String              _name;
  private String              _gender;
  private java.sql.Array      _coursesEnrolled;

  public StudentT() {}

  public StudentT(  String          snum,
                    String          name,
                    String          gender,
                    java.sql.Array  coursesEnrolled)
  throws SQLException {
    setSnum(snum);
    setName(name);
    setGender(gender);
    setCoursesEnrolled(coursesEnrolled);
  }
  
  public void readSQL(SQLInput stream, String type)
  throws SQLException { 
    setSnum(stream.readString());
    setName(stream.readString());
    setGender(stream.readString());
    setCoursesEnrolled(stream.readArray());
  }

  public void writeSQL(SQLOutput stream)
  throws SQLException {
    stream.writeString(getSnum());
    stream.writeString(getName());
    stream.writeString(getGender());
    stream.writeArray(getCoursesEnrolled());
  }

  public String getSQLTypeName() throws SQLException
  { return _SQL_NAME; }

  public String getSnum()
  { return _snum; }

  public void setSnum(String snum)
  { _snum = snum; }


  public String getName()
  { return _name; }

  public void setName(String name)
  { _name = name; }

  
  public String getGender()
  { return _gender; }

  public void setGender(String gender)
  { _gender = gender; }
  
  
  public java.sql.Array getCoursesEnrolled()
  { return _coursesEnrolled; }

  public void setCoursesEnrolled(java.sql.Array coursesEnrolled)
  { _coursesEnrolled = coursesEnrolled; }
  
}