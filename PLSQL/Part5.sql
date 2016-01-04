-----------------------------------------
-- Author: Ankush Varshneya (100853074)
-- Assignment#3 Part#5
-- This PL/SQL script uses 1 cursors to 
-- prints all tables in table format.
-- Uses Method 4.
-----------------------------------------
DECLARE
  -- make the coursor
  type scref is ref cursor;
  cursorv scref;
  -- declare needed tuples
  studentTuple  Student%rowtype;
  courseTuple   Course%rowtype;
  gradeTuple    Grade%rowtype;
  -- array of flags
  TYPE flagarray IS VARRAY(3) OF VARCHAR(10);
  flags flagarray := flagarray('Student', 'Course', 'Grade');
  
BEGIN
  FOR i IN flags.FIRST..flags.LAST LOOP
    CASE flags(i)
      WHEN 'Student' THEN
        OPEN cursorv FOR SELECT * FROM Student;
          dbms_output.put_line('+------+------------+-----------+---------------------------+');
          dbms_output.put_line('|  s#  |    Name    | BirthDate |          Address          |');
          dbms_output.put_line('+======+============+===========+===========================+');
        LOOP
          FETCH cursorv INTO studentTuple;
          EXIT WHEN cursorv%notfound;
          dbms_output.put_line('|'||NVL(LPAD(studentTuple.s#, 6), LPAD(' ',6))||
                               '|'||NVL(LPAD(studentTuple.name, 12), LPAD(' ',12))||
                               '|'||NVL(LPAD(studentTuple.birthdate,11), LPAD(' ',11))||
                               '|'||NVL(LPAD(studentTuple.address, 27), LPAD(' ',27))||'|');
          dbms_output.put_line('+------+------------+-----------+---------------------------+');
        END LOOP;
      WHEN 'Course' THEN
        OPEN cursorv FOR SELECT * FROM Course;
          dbms_output.put_line('+------+------------+------------+-----+------------+');
          dbms_output.put_line('|  c#  |    Name    |  Location  | Day |    Time    |');
          dbms_output.put_line('+======+============+============+==================+');
        LOOP
          FETCH cursorv INTO courseTuple;
          EXIT WHEN cursorv%notfound;
          dbms_output.put_line('|'||NVL(LPAD(courseTuple.c#, 6), LPAD(' ',6))||
                               '|'||NVL(LPAD(courseTuple.name, 12), LPAD(' ',12))||
                               '|'||NVL(LPAD(courseTuple.location,12), LPAD(' ',12))||
                               '|'||NVL(LPAD(courseTuple.days, 5), LPAD(' ',5))||
                               '|'||NVL(LPAD(courseTuple.times, 12), LPAD(' ',12))||'|');
          dbms_output.put_line('+------+------------+------------+-----+------------+');  
        END LOOP;
      WHEN 'Grade' THEN
        OPEN cursorv FOR SELECT * FROM Grade;
          dbms_output.put_line('+------+------+------+');
          dbms_output.put_line('|  c#  |  s#  | Mark |');
          dbms_output.put_line('+======+======+======+');
        LOOP
          FETCH cursorv INTO gradeTuple;
          EXIT WHEN cursorv%notfound;
          dbms_output.put_line('|'||NVL(LPAD(gradeTuple.c#, 6), LPAD(' ',6))||
                               '|'||NVL(LPAD(gradeTuple.s#, 6), LPAD(' ',6))||
                               '|'||NVL(LPAD(gradeTuple.mark, 6), LPAD(' ',6))||'|');
          dbms_output.put_line('+------+------+------+');  
        END LOOP;        
        ELSE
          dbms_output.put_line('invalid case!');
    END Case;
  END LOOP;
END;
/