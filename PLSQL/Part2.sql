-----------------------------------------
-- Author: Ankush Varshneya (100853074)
-- Assignment#3 Part#2
-- This PL/SQL script creates 2 packages.
-----------------------------------------

--Student Package 
CREATE OR REPLACE PACKAGE Students AS
  FUNCTION  add_student(StuName Student.name%type) RETURN Student.s#%type;
  FUNCTION  add_student(StuName Student.name%type, StuBirthDate Student.birthdate%type) RETURN Student.s#%type;
  FUNCTION  add_student(StuName Student.name%type, StuBirthDate Student.birthdate%type, StuAddress Student.address%type) RETURN Student.s#%type;
  PROCEDURE delete_student(StuName Student.name%type);
  PROCEDURE change_name(SNum Student.s#%type, StuName_New Student.name%type);
  PROCEDURE change_address(SNum Student.s#%type, StuAddress_New Student.address%type);
  PROCEDURE enroll_course(SNum Student.s#%type, CNum Course.c#%type);
END Students;
/
CREATE OR REPLACE PACKAGE BODY Students AS

  FUNCTION add_student(StuName Student.name%type) RETURN Student.s#%type IS
  BEGIN
    RETURN add_student(StuName, NULL, NULL);
  END;
  
  FUNCTION add_student(StuName Student.name%type, StuBirthDate Student.birthdate%type) RETURN Student.s#%type IS
  BEGIN
    RETURN add_student(StuName, StuBirthDate, NULL);
  END;
  
  FUNCTION add_student(StuName Student.name%type, StuBirthDate Student.birthdate%type, StuAddress Student.address%type) RETURN Student.s#%type IS
  	NullNameException Exception;
  BEGIN
    IF (StuName IS NULL) THEN
      -- raise error if student name is null
      RAISE NullNameException;
    ELSE
      INSERT INTO Student (s#, name, birthdate, address) VALUES('s'||stuNumSeq.NEXTVAL, StuName, StuBirthDate, StuAddress);
    END IF;
    dbms_output.put_line('add_student operation sucessful!');    
    RETURN 's'||stuNumSeq.CURRVAL;
  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
      dbms_output.put_line('add_student operation unsucessful: Student is already added!');
      RETURN 's-1';
    WHEN NullNameException THEN
      dbms_output.put_line('add_student operation unsucessful: Student name cannot be null!');
      RETURN 's-1';
  END;
  
  PROCEDURE delete_student(StuName Student.name%type) IS
    NullNameException Exception;
    NotExistException Exception;
    StudentCount      integer;
  BEGIN
    -- Check if student exists and raise error accordingly
    SELECT COUNT(*) INTO StudentCount
    FROM Student
    WHERE Student.name = StuName;
    IF StudentCount < 1 THEN
      RAISE NotExistException;
    ELSIF (StuName IS NULL) THEN
      -- raise error if student name is null
      RAISE NullNameException;
    ELSE
      -- If it exists delete it
      DELETE FROM Student WHERE Student.name = StuName;
    END IF;
    dbms_output.put_line('delete_student operation sucessful!');
  EXCEPTION
    WHEN NotExistException THEN
      dbms_output.put_line('delete_student operation unsucessful: Student cannot be deleted as it does not exist!');
    WHEN NullNameException THEN
      dbms_output.put_line('delete_student operation unsucessful: Student name cannot be null!');
  END;
    
  PROCEDURE change_name(SNum Student.s#%type, StuName_New Student.name%type) IS
    NullNameException Exception;
  	NotExistException Exception;
    StudentCount      integer;
  BEGIN
    -- Check if student exists and raise error accordingly
    SELECT COUNT(*) INTO StudentCount
    FROM Student
    WHERE Student.s# = SNum;
    IF StudentCount < 1 THEN
      RAISE NotExistException;
    ELSIF (StuName_New IS NULL) THEN
      -- raise error if student name is null
      RAISE NullNameException;
    ELSE
      -- If it exists change its name
      UPDATE Student SET
        Student.name = StuName_New
      WHERE Student.s# = SNum;
    END IF;
    dbms_output.put_line('change_name operation sucessful!');
  EXCEPTION
    WHEN NotExistException THEN
      dbms_output.put_line('change_name operation unsucessful: Student cannot change name as it does not exist!');
    WHEN DUP_VAL_ON_INDEX THEN
      dbms_output.put_line('change_name operation unsucessful: Student cannot change name as that name is already added!');
    WHEN NullNameException THEN
      dbms_output.put_line('change_name operation unsucessful: Student name cannot be null!');
  END;
  
  PROCEDURE change_address(SNum Student.s#%type, StuAddress_New Student.address%type) IS
  	NotExistException Exception;
    StudentCount      integer;
  BEGIN
    -- Check if student exists and raise error accordingly
    SELECT COUNT(*) INTO StudentCount
    FROM Student
    WHERE Student.s# = SNum;
    IF StudentCount < 1 THEN
      RAISE NotExistException;
    ELSE
      -- If it exists change its address
      UPDATE Student SET
        Student.address = StuAddress_New
      WHERE Student.s# = SNum;
    END IF;
    dbms_output.put_line('change_address operation sucessful!');
  EXCEPTION
    WHEN NotExistException THEN
      dbms_output.put_line('change_address operation unsucessful: Student cannot change address as it does not exist!');
  END;
  
  PROCEDURE enroll_course(SNum Student.s#%type, CNum Course.c#%type) IS
  	SNotExistException    Exception;
  	CNotExistException    Exception;
    TimeConflictException Exception;
    StudentCount          integer;
    CourseCount           integer;
    TimeConflictCount     integer;
  BEGIN
    -- Check if student/course exists and raise error accordingly
    SELECT COUNT(*) INTO StudentCount
    FROM Student
    WHERE Student.s# = SNum;
    SELECT COUNT(*) INTO CourseCount
    FROM Course
    WHERE Course.c# = CNum;
    IF StudentCount < 1 THEN
      RAISE SNotExistException;
    ELSIF CourseCount < 1 THEN
      RAISE CNotExistException;
    ELSE
      -- check for course time conflicts and raise error accordingly
      SELECT COUNT(*) INTO TimeConflictCount
      FROM (
        SELECT * FROM Grade, Course C1
        WHERE EXISTS(
          SELECT * FROM Course C2
          WHERE C2.c# = CNum 
            AND Grade.s# = SNum
            AND Grade.c# = C1.c#
            AND C2.days = C1.days
            AND C2.times = C1.times
            AND C2.c# != C1.c#));
      IF TimeConflictCount > 0 THEN
        RAISE TimeConflictException;
      ELSE
        -- If no conflicts enroll student with NULL Mark
        INSERT INTO Grade (s#,c#) VALUES(SNum, CNum);
      END IF;
    END IF;
    dbms_output.put_line('enroll_course operation sucessful!');
  EXCEPTION
    WHEN SNotExistException THEN
      dbms_output.put_line('enroll_course operation unsucessful: Student cannot be enrolled as it does not exist!');
    WHEN CNotExistException THEN
      dbms_output.put_line('enroll_course operation unsucessful: Course cannot be enrolled to as it does not exist!'); 
    WHEN TimeConflictException THEN
      dbms_output.put_line('enroll_course operation unsucessful: Time conflicts with courses!');
    WHEN DUP_VAL_ON_INDEX THEN
      dbms_output.put_line('enroll_course operation unsucessful: Student is already enrolled!');
  END; 
END Students;
/
--Course Package
CREATE OR REPLACE PACKAGE Courses AS
  PROCEDURE add_course(CNum Course.c#%type, CName Course.name%type);
  PROCEDURE add_course(CNum Course.c#%type, CName Course.name%type, CLocation Course.location%type, CDays Course.days%type, CTimes Course.times%type);
  PROCEDURE delete_course(CName Course.name%type);
  PROCEDURE change_loc(CNum Course.c#%type, CLocation Course.location%type);
  PROCEDURE give_mark(SNum Student.s#%type, CNum Course.c#%type, CMark Grade.mark%type); 
END Courses;
/
CREATE OR REPLACE PACKAGE BODY Courses AS

  PROCEDURE add_course(CNum Course.c#%type, CName Course.name%type) IS
  BEGIN
    add_course(CNum, CName, NULL, NULL, NULL);
  END;

  PROCEDURE add_course(CNum Course.c#%type, CName Course.name%type, CLocation Course.location%type, CDays Course.days%type, CTimes Course.times%type) IS
    NullNameException           Exception;
    CheckViolatedTimesException Exception;
    CheckViolatedDaysException  Exception;
    TimeConflictException       Exception;
    TimeConflictCount           integer;
  BEGIN
    -- see if course conflicts with same location for
    -- same days and times and raise error accordingly
    SELECT COUNT(*) INTO TimeConflictCount
    FROM Course
    WHERE Course.location = CLocation
      AND Course.days = CDays
      AND Course.times = CTimes;
    IF TimeConflictCount > 0 THEN
      RAISE TimeConflictException;
    ELSIF CTimes NOT IN ('8:35-9:25', '10:35-11:25', '1:35-2:25', '3:35-4:25') THEN
      -- raise an error if times are invalid
      RAISE CheckViolatedTimesException;
    ELSIF CDays NOT IN ('MWF', 'TR') THEN
      -- raise an error if days are invalid
      RAISE CheckViolatedDaysException;
    ELSIF (CName IS NULL) THEN    
      -- raise error if course name is null
      RAISE NullNameException;
    ELSE
      -- If no conflicts add the course
      INSERT INTO Course (c#, name, location, days, times) VALUES (CNum, CName, CLocation, CDays, CTimes);
    END IF;
    dbms_output.put_line('add_course operation sucessful!');
  EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
      dbms_output.put_line('add_course operation unsucessful: Course is already added!');
    WHEN CheckViolatedTimesException THEN
      dbms_output.put_line('add_course operation unsucessful: Invalid Times, use 10:35-11:25 or 1:35-2:25 or 3:35-4:25 for times!');
    WHEN CheckViolatedDaysException THEN
      dbms_output.put_line('add_course operation unsucessful: Invalid Days, use MWF or TR for days!');
    WHEN TimeConflictException THEN
      dbms_output.put_line('add_course operation unsucessful: Location conflicts with another course!');
    WHEN NullNameException THEN
      dbms_output.put_line('add_course operation unsucessful: Course name cannot be null!');
  END;

  PROCEDURE delete_course(CName Course.name%type) IS  
    NullNameException Exception;
    NotExistException Exception;
    CourseCount       integer;
    EnrolledException Exception;
    EnrolledCount     integer;
  BEGIN
    -- Check if course exists and raise error accordingly
    SELECT COUNT(*) INTO CourseCount
    FROM Course
    WHERE Course.name = CName;
    -- Check to see if students are enrolled in it.
    SELECT COUNT(*) INTO EnrolledCount
    FROM Grade, Course
    WHERE Course.name = CName AND Course.c# = Grade.c#;
    IF CourseCount < 1 THEN
      RAISE NotExistException;
    ELSIF EnrolledCount > 0 THEN
      RAISE EnrolledException;
    ELSIF (CName IS NULL) THEN    
      -- raise error if course name is null
      RAISE NullNameException;
    ELSE
      -- If it exists delete it
      DELETE FROM Course WHERE Course.name = CName;
    END IF;
    dbms_output.put_line('delete_course operation sucessful!');
  EXCEPTION
    WHEN NotExistException THEN
      dbms_output.put_line('delete_course operation unsucessful: Course cannot be deleted as it does not exist!');
    WHEN EnrolledException THEN
      dbms_output.put_line('delete_course operation unsucessful: Course cannot be deleted as it has students enrolled!');
    WHEN NullNameException THEN
      dbms_output.put_line('delete_course operation unsucessful: Course name cannot be null!');
  END;

  PROCEDURE change_loc(CNum Course.c#%type, CLocation Course.location%type) IS
    NotExistException Exception;
    CourseCount       integer;
    TimeConflictException   Exception;
    TimeConflictCount       integer;
  BEGIN
    -- see if course exists
    SELECT COUNT(*) INTO CourseCount
    FROM Course
    WHERE Course.c# = CNum;
    -- see if course conflicts with new location for
    -- same days and times and raise error accordingly
    SELECT COUNT(*) INTO TimeConflictCount
    FROM Course C1, Course C2
    WHERE C2.c# = CNum
      And C1.location = CLocation
      AND C1.days = C2.days
      AND C1.times = C2.times
      AND C2.c# != C1.c#;
    IF CourseCount < 1 THEN
      RAISE NotExistException;
    ELSIF TimeConflictCount > 0 THEN
      RAISE TimeConflictException;
    ELSE
      -- If no conflicts update the location
      UPDATE Course SET
        Course.location = CLocation
      WHERE Course.c# = CNum;
    END IF;
    dbms_output.put_line('change_loc operation sucessful!');
  EXCEPTION
    WHEN NotExistException THEN
      dbms_output.put_line('change_loc operation unsucessful: Course location cannot be changed as it does not exist!');
    WHEN TimeConflictException THEN
      dbms_output.put_line('change_loc operation unsucessful: Location conflicts with another course!');
  END;

  PROCEDURE give_mark(SNum Student.s#%type, CNum Course.c#%type, CMark Grade.mark%type) IS
    EnrolledException Exception;
    EnrolledCount     integer;
  BEGIN
	-- See if student is enrolled before giving mark
	SELECT COUNT(*) INTO EnrolledCount
    FROM Grade
    WHERE Grade.s# = SNum AND Grade.c# = CNum;
    IF EnrolledCount < 1 THEN
      RAISE EnrolledException;
    ELSE
      -- If it exists give mark
      UPDATE Grade SET
        Grade.mark = CMark
      WHERE Grade.s# = SNum AND Grade.c# = CNum;
    END IF;
    dbms_output.put_line('give_mark operation sucessful!');
  EXCEPTION
    WHEN EnrolledException THEN
      dbms_output.put_line('give_mark operation unsucessful: Cannot give mark as student not enrolled in course!');
  END;
END Courses;
/