-----------------------------------------
-- Author: Ankush Varshneya (100853074)
-- Assignment#3 Part#4
-- This PL/SQL script uses 2 cursors to 
-- prints student/course in table format.
-- Using Method 3.
-----------------------------------------
DECLARE
  SNum Course.c#%type;
  CURSOR studentCursor IS
    SELECT Student.s#, Student.name, Student.birthdate, Student.address
    FROM Student;
  CURSOR courseCursor(SNum Grade.s#%type) IS
    SELECT Course.c#, Course.name, Course.location, Course.days, Course.times
    FROM Course, Grade
    WHERE Course.c# = Grade.c# AND Grade.s# = SNum;
BEGIN
      dbms_output.put_line('+------+------------+-----------+---------------------------+');
      dbms_output.put_line('|  s#  |    Name    | BirthDate |          Address          |');
      dbms_output.put_line('+======+============+===========+===========================+');
  FOR studentTuple IN studentCursor LOOP
      dbms_output.put_line('|'||NVL(LPAD(studentTuple.s#, 6), LPAD(' ',6))||
                           '|'||NVL(LPAD(studentTuple.name, 12), LPAD(' ',12))||
                           '|'||NVL(LPAD(studentTuple.birthdate,11), LPAD(' ',11))||
                           '|'||NVL(LPAD(studentTuple.address, 27), LPAD(' ',27))||'|');
      dbms_output.put_line('+------+------------+-----------+---------------------------+');
      dbms_output.put_line('|   +------+------------+------------+-----+------------+   |');
      dbms_output.put_line('|   |  c#  |    Name    |  Location  | Day |    Time    |   |');
      dbms_output.put_line('|   +======+============+============+==================+   |');    
    FOR courseTuple IN courseCursor(studentTuple.s#) LOOP
      dbms_output.put_line('|   |'||NVL(LPAD(courseTuple.c#, 6), LPAD(' ',6))||
                               '|'||NVL(LPAD(courseTuple.name, 12), LPAD(' ',12))||
                               '|'||NVL(LPAD(courseTuple.location,12), LPAD(' ',12))||
                               '|'||NVL(LPAD(courseTuple.days, 5), LPAD(' ',5))||
                               '|'||NVL(LPAD(courseTuple.times, 12), LPAD(' ',12))||'|   |');
      dbms_output.put_line('|   +------+------------+------------+-----+------------+   |');  
    END LOOP;
      dbms_output.put_line('+------+------------+-----------+---------------------------+');
  END LOOP;
END;
/