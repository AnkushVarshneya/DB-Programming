/**
 * Author: Ankush Varshneya (100853074)
 * Assignment#5 Part#4 - grad_t Object
 * CREATE TYPE grad_t UNDER student_t (
 *  Phone             VARCHAR2(15)
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

public class GradT extends StudentT implements SQLData
{
  public static final String  _SQL_NAME = "GRAD_T";
  public static final int     _SQL_TYPECODE = OracleTypes.STRUCT;
  
  private String              _phone;

  public GradT() {}

  public GradT( String          phone,
                String          snum,
                String          name,
                String          gender,
                java.sql.Array  coursesEnrolled)
  throws SQLException {
    super(snum, name, gender, coursesEnrolled);
    setPhone(phone);
  }
  
  public void readSQL(SQLInput stream, String type)
  throws SQLException { 
    super.readSQL(stream, type);
    setPhone(stream.readString());
  }

  public void writeSQL(SQLOutput stream)
  throws SQLException {
    super.writeSQL(stream);
    stream.writeString(getPhone());
  }

  public String getSQLTypeName() throws SQLException
  { return _SQL_NAME; }

  public String getPhone()
  { return _phone; }

  public void setPhone(String phone)
  { _phone = phone; }

}