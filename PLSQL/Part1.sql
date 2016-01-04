-----------------------------------------
-- Author: Ankush Varshneya (100853074)
-- Assignment#3 Part#1
-- This PL/SQL script creates 3 tables.
-----------------------------------------
DECLARE
  -- Command to create the Student table
  createStudent VARCHAR(400) :=
    'CREATE TABLE Student (
      s#        VARCHAR(4)    PRIMARY KEY,
      name      VARCHAR2(10)  NOT NULL UNIQUE,
      birthdate DATE,
      address   VARCHAR2(25)
    )';
  -- Command to create the Course table
  createCourse VARCHAR(400) :=
    'CREATE TABLE Course (
      c#        VARCHAR(4)    PRIMARY KEY,
      name      VARCHAR2(10)  NOT NULL UNIQUE,
      location  VARCHAR2(10),
      days      VARCHAR2(3)   CHECK(days IN (''MWF'', ''TR'')),
      times     VARCHAR2(11)  CHECK(times IN (''8:35-9:25'', ''10:35-11:25'', ''1:35-2:25'', ''3:35-4:25''))
    )';
  -- Command to create the Grade table
  createGrade VARCHAR(400) :=
    'CREATE TABLE Grade (
      s#        VARCHAR(4),
      c#        VARCHAR(4),
      mark      float,
      PRIMARY KEY (s#,c#),
      FOREIGN KEY (s#) REFERENCES Student(s#) ON DELETE CASCADE,
      FOREIGN KEY (c#) REFERENCES Course(c#) ON DELETE CASCADE
    )';
   -- Command to create the Stu Num Sequence
  createStuNumSequence VARCHAR(400) :=
    'CREATE SEQUENCE stuNumSeq
      MINVALUE       0
      START WITH     0
      INCREMENT BY   1
      NOCACHE
      NOCYCLE';
BEGIN
  EXECUTE IMMEDIATE createStudent;
  EXECUTE IMMEDIATE createCourse;
  EXECUTE IMMEDIATE createGrade;
  EXECUTE IMMEDIATE createStuNumSequence;
END;
/