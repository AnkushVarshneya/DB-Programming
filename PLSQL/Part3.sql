-----------------------------------------
-- Author: Ankush Varshneya (100853074)
-- Assignment#3 Part#3
-- This PL/SQL script checks part 2
-- and populates the database.
-----------------------------------------
DECLARE
  res0   Student.s#%type;
  res1   Student.s#%type;
  res2   Student.s#%type;
  res3   Student.s#%type;
  res4   Student.s#%type;
  res5   Student.s#%type;
  res6   Student.s#%type;
  res7   Student.s#%type;
  res8   Student.s#%type;
  res9   Student.s#%type;
  resNew Student.s#%type;
  res    Student.s#%type;
BEGIN
  -- delete any previous values
  DELETE FROM Grade;
  DELETE FROM Student;
  DELETE FROM Course;

  -- Create 10 Students
  res0 := students.add_student('Ted');
  res1 := students.add_student('Jake', TO_DATE('01/01/1990', 'DD/MM/YYYY'));
  res2 := students.add_student('Lola', TO_DATE('02/02/1991', 'DD/MM/YYYY'), '123 Fakestreet2.');
  res3 := students.add_student('Emma', TO_DATE('03/03/1992', 'DD/MM/YYYY'), '123 Fakestreet3.');
  res4 := students.add_student('Mary', TO_DATE('04/04/1993', 'DD/MM/YYYY'), '123 Fakestreet4.');
  res5 := students.add_student('Pate', TO_DATE('05/05/1994', 'DD/MM/YYYY'), '123 Fakestreet5.');
  res6 := students.add_student('Joes', TO_DATE('06/06/1993', 'DD/MM/YYYY'), '123 Fakestreet6.');
  res7 := students.add_student('Brit', TO_DATE('07/07/1992', 'DD/MM/YYYY'), '123 Fakestreet7.');
  res8 := students.add_student('Kely', TO_DATE('08/08/1991', 'DD/MM/YYYY'), '123 Fakestreet8.');
  res9 := students.add_student('Tayl', TO_DATE('09/09/1990', 'DD/MM/YYYY'), '123 Fakestreet9.');
  
  -- Create 10 Courses
  courses.add_course('c0', 'math');
  courses.add_course('c1', 'chem', 'b1', 'MWF', '8:35-9:25');
  courses.add_course('c2', 'comp', 'b1', 'MWF', '10:35-11:25');
  courses.add_course('c3', 'phys', 'b1', 'MWF', '1:35-2:25');
  courses.add_course('c4', 'biol', 'b1', 'MWF', '3:35-4:25');
  courses.add_course('c5', 'soci', 'b1', 'TR', '8:35-9:25');
  courses.add_course('c6', 'engl', 'b1', 'TR', '10:35-11:25');
  courses.add_course('c7', 'stat', 'b1', 'TR', '1:35-2:25');
  courses.add_course('c8', 'econ', 'b1', 'TR', '3:35-4:25');
  courses.add_course('c9', 'busi', 'b2', 'MWF', '8:35-9:25');

  -- enrole students
  students.enroll_course(res0, 'c0');
  students.enroll_course(res1, 'c1');
  students.enroll_course(res2, 'c2');
  students.enroll_course(res3, 'c3');
  students.enroll_course(res4, 'c4');
  students.enroll_course(res5, 'c5');
  students.enroll_course(res6, 'c6');
  students.enroll_course(res7, 'c7');
  students.enroll_course(res8, 'c8');
  
  -- corner cases of students

  -- add a student with NULL name- you wil get error as student name can not be null
  res := students.add_student(NULL);
  -- add another ted - you wil get error as he already exists
  res := students.add_student('Ted');
  -- delete non-existing student - you will get error as he does not exist
  students.delete_student('NotStudent');
  -- delete ted - you wil not get error
  students.delete_student('Ted');
  -- re-add deleted ted's - you wil not get error
  resNew := students.add_student('Ted');  
  -- delete a student with NULL name - you get error as name cannot be null
  students.delete_student(NULL);
  
  -- change ted's name using old student number - you wil get error as it is deleted
  students.change_name(res0, 'Tedj');
  -- change ted's name using new student number to existing name - you wil get error as the name is used
  students.change_name(resNew, 'Jake');  
  -- change ted's name using new student number to NULL - you wil get error as name cannot be null
  students.change_name(resNew, NULL);
  -- change ted's name using new student number to non-existing name - you wil not get error
  students.change_name(resNew, 'Tedi');
 
  -- change ted's address using old student number - you wil get error as its is deleted
  students.change_address(res0, '123 Fakestreet0.');
  -- change ted's address using new student number - you wil not get error
  students.change_address(resNew, '123 Fakestreet0.');
  -- change jake's address using student number - you wil not get error
  students.change_address(res1, '123 Fakestreet1.');
  
  -- enroll ted using old student number - you wil get error as its is deleted
  students.enroll_course(res0, 'c0');
  -- enroll ted using in a non existing course - you wil get error as course does not exist
  students.enroll_course(resNew, 'c11');
  -- re-enroll ted using his new student number in a course - you wil not get error as he is another student now
  students.enroll_course(resNew, 'c0');
  -- enroll ted using his new student number in a course - you wil not get error
  students.enroll_course(resNew, 'c9');
  -- re-enroll jake in a course - you wil get error as he is re-enrolling
  students.enroll_course(res1, 'c1');
  -- enroll jake in a course of same days/times - you will get error as he has time conflict
  students.enroll_course(res1, 'c9');
  
  -- corner cases of courses
    
  -- add a course with NULL name - you wil get error as course name can not be null
  courses.add_course('c10', NULL);  
  -- add another math course - you wil get error as it already exists
  courses.add_course('c10', 'math');  
  -- add another course with invalid days - you wil get error as invalid days
  courses.add_course('c10', 'poli', 'b2', 'TF', '8:35-9:25');
  -- add another course with invalid time - you wil get error as invalid time
  courses.add_course('c10', 'poli', 'b2', 'MWF', '8:30-9:20');  
  -- add another course with time conflict - you wil get error as time conflict
  courses.add_course('c10', 'poli', 'b2', 'MWF', '8:35-9:25');
  -- add another course with no conflict - you wil not get error
  courses.add_course('c10', 'poli', 'b2', 'MWF', '10:35-11:25');

  -- change non-existing courses location to b1, you will get error as course does not exist
  courses.change_loc('c11', 'b1');
  -- change courses location to b1, you will get error as there is a time conflict
  courses.change_loc('c10', 'b1');
  -- change courses location to b2, you will not get error
  courses.change_loc('c3', 'b2');
  -- change it back, you will not get error
  courses.change_loc('c3', 'b1');
  -- change it to same thing again, you will not get error
  courses.change_loc('c3', 'b1');

  -- delete a course with NULL name - you get error as name cannot be null
  courses.delete_course(NULL);
  -- delete a non-existing course - you get error as course does not exist
  courses.delete_course('notacourse');
  -- delete a course with students enrolled - you get error as course has students enrolled
  courses.delete_course('math');
  -- delete a course with no students enrolled - you get no error
  courses.delete_course('poli');
  
  -- give mark to non-enrolled student - you will get error as student is not enrolled
  courses.give_mark(resNew, 'c8', 100);
  -- give mark to non-existing student - you will get error as student does not exist
  courses.give_mark(res0, 'c8', 100);
  -- give mark to a student in a non existing course - you will get error as course does not exist
  courses.give_mark(resNew, 'c11', 100);
  -- give mark to a enrolled existing student in an existing course - you will get no error
  courses.give_mark(resNew, 'c9', 99);
  -- give mark to a enrolled existing student in an existing course - you will get no error
  courses.give_mark(resNew, 'c9', 100);
  
END;
/