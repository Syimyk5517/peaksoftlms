INSERT INTO courses(id,
                    created_at,
                    description,
                    final_date,
                    image,
                    name)
VALUES (1,
        '2022-10-03',
        'Java - Core for Java groups',
        '2023-06-30',
        'default.img',
        'Java - Core'),
       (2,
        '2022-10-03',
        'JavaScript - Core for JS groups',
        '2023-06-30',
        'default.img',
        'JavaScript - Core'),
       (3,
        '2022-10-03',
        'Technical English for Java and JS groups',
        '2023-06-30',
        'default.img',
        'Technical English'),
       (4,
        '2022-10-03',
        'SoftSkills for Java and JS groups',
        '2023-06-30',
        'default.img',
        'SoftSkills'),
       (5,
        '2023-04-03',
        'Java Spring for Java group',
        '2023-06-30',
        'default.img',
        'Spring Framework'),
       (6,
        '2023-04-03',
        'JavaScript React for JS group',
        '2023-06-30',
        'default.img',
        'React');
INSERT INTO groups(id,
                   created_at,
                   description,
                   final_date,
                   image,
                   name)
VALUES (1,
        '2022-10-03',
        'Java - 8 group',
        '2023-06-30',
        'default.img',
        'Java - 8'),
       (2,
        '2022-10-03',
        'JavaScript - 8 group',
        '2023-06-30',
        'default.img',
        'JavaScript - 8'),
       (3,
        '2023-01-01',
        'Java - 9 group',
        '2023-09-30',
        'default.img',
        'Java - 9'),
       (4,
        '2023-01-01',
        'JavaScript - 9 group',
        '2023-09-30',
        'default.img',
        'JavaScript - 9'),
       (5,
        '2023-04-01',
        'Java - 10 group',
        '2023-12-31',
        'default.img',
        'Java - 10'),
       (6,
        '2023-04-01',
        'JavaScript - 10 group',
        '2023-12-31',
        'default.img',
        'JavaScript - 10');
INSERT INTO instructors(id,
                        special)
VALUES (1, 'ONLINE'),
       (2, 'OFFLINE'),
       (3, 'ONLINE'),
       (4, 'ONLINE'),
       (5, 'OFFLINE'),
       (6, 'OFFLINE');
INSERT INTO students(id,
                     form_learning,
                     is_blocked,
                     rating,
                     group_id)
VALUES (1,
        'ONLINE',
        'FALSE',
        4,
        1),
       (2,
        'OFFLINE',
        'FALSE',
        4,
        1),
       (3,
        'OFFLINE',
        'FALSE',
        4,
        1),
       (4,
        'OFFLINE',
        'FALSE',
        5,
        1),
       (5,
        'OFFLINE',
        'FALSE',
        3,
        1),
       (6,
        'ONLINE',
        'TRUE',
        5,
        2);
INSERT INTO users(id,
                  first_name,
                  last_name,
                  email,
                  password,
                  phone_number,
                  role,
                  instructor_id,
                  student_id)
VALUES (1,
        'Asan',
        'Bakytov',
        'admin@gmail.com',
        '$2a$12$GnXo2eH1LorDSaRh/Nw5rOpjUqUrYpH9IkN5EZLmKigGnKBxYP93C',
        '+996700112233',
        'ADMIN',
        null,
        null),
       (2,
        'Syimyk',
        'Bekbolsun uulu',
        'student@gmail.com',
        '$2a$12$hNGCRxefPP0XSB5UBPAUXu59ZL2p/UqQEhv3JKjrwyDsiaixZ9xle',
        '+996700113323',
        'STUDENT',
        null,
        2),
       (3,
        'Nuradil',
        'Amanov',
        'instructor@gmail.com',
        '$2a$12$.CCc.PIIEJ9.BPPv/BrXXeXt2BDvQ1Lx6VB9R0y29w3INa.kBQxgq',
        '+996700112244',
        'INSTRUCTOR',
        1,
        3),
       (4,
        'Bolot',
        'Sulaimanov',
        'bolot12@gmail.com',
        'Bolot00',
        '+996700112211',
        'INSTRUCTOR',
        1,
        null),
       (5,
        'Dosbol',
        'Orozaliev',
        'dosbol00@gmail.com',
        'Dosbol00',
        '+996700112255',
        'STUDENT',
        null,
        5),
       (6,
        'Nurkyz',
        'Baatyr kyzy',
        'nurkyz12@gmail.com',
        'Nurkyz00',
        '+996700112266',
        'INSTRUCTOR',
        2,
        null),
       (7,
        'Jamila',
        'Maadalieva',
        'jamila00@gmail.com',
        'Jamila00',
        '+996700112277',
        'STUDENT',
        null,
        6);
INSERT INTO lessons(id,
                    created_at,
                    name,
                    course_id)
VALUES (1,
        '2023-01-03',
        'Introduction',
        1),
       (2,
        '2023-01-05',
        'Git & GitHub',
        1),
       (3,
        '2023-01-10',
        'SQL & PostgreSQL',
        2),
       (4,
        '2023-01-12',
        'DDL,DML,TCL,DCL',
        3),
       (5,
        '2023-01-17',
        'Spring Framework',
        2),
       (6,
        '2023-01-20',
        'Spring Security',
        1),
       (7,
        '2023-01-15',
        'Spring Boot',
        1);
INSERT INTO lesson_link(lesson_id,
                        link,
                        link_key)
VALUES (1,
        'https://youtu.be/j9VNCI9Xo80',
        1),
       (2,
        'https://youtu.be/j9VNCI9Xo80',
        2),
       (3,
        'https://youtu.be/j9VNCI9Xo80',
        3),
       (4,
        'https://youtu.be/j9VNCI9Xo80',
        4),
       (5,
        'https://youtu.be/j9VNCI9Xo80',
        5),
       (6,
        'https://youtu.be/j9VNCI9Xo80',
        6);
INSERT INTO video_lessons(id,
                          description,
                          link,
                          name,
                          lesson_id)
VALUES (1,
        'Introduction for Java middle level',
        'https://youtu.be/j9VNCI9Xo80',
        'Introduction',
        1),
       (2,
        'Git and GitHub for Java middle level',
        'https://youtu.be/j9VNCI9Xo80',
        'Git & GitHub',
        2),
       (3,
        'SQL for Java middle level',
        'https://youtu.be/j9VNCI9Xo80',
        'SQL/PostgreSQL',
        3),
       (4,
        'JDBC for Java middle level',
        'https://youtu.be/j9VNCI9Xo80',
        'JDBC',
        4),
       (5,
        'HTML for JavaScript junior level',
        'https://youtu.be/j9VNCI9Xo80',
        'HTML',
        5),
       (6,
        'CSS for JavaScript junior level',
        'https://youtu.be/j9VNCI9Xo80',
        'CSS',
        6);
INSERT INTO presentations(id,
                          description,
                          formatppt,
                          name,
                          lesson_id)
VALUES (1,
        'Introduction presentation 2023-04-01',
        'ppt format',
        'Introduction presentation',
        1),
       (2,
        'Git presentation 2023-04-03',
        'ppt format',
        'Git presentation',
        2),
       (3,
        'SQL presentation 2023-04-05',
        'ppt format',
        'SQL presentation',
        3),
       (4,
        'Spring presentation 2023-04-09',
        'ppt format',
        'Spring presentation',
        4),
       (5,
        'Spring Security presentation 2023-04-11',
        'ppt format',
        'Spring Security presentation',
        5),
       (6,
        'Validation presentation 2023-04-12',
        'ppt format',
        'Validation presentation',
        6);
INSERT INTO tests(id,
                  date_test,
                  name,
                  lesson_id)
VALUES (1,
        '2023-04-12',
        'Spring test',
        1),
       (2,
        '2023-04-11',
        'JDBC test',
        2),
       (3,
        '2023-04-10',
        'Hibernate test',
        3),
       (4,
        '2023-04-09',
        'Git & GitHub test',
        1),
       (5,
        '2023-04-08',
        'JWT Token test',
        5),
       (6,
        '2023-04-07',
        'Validation test',
        6);
INSERT INTO questions(id,
                      option_type,
                      question_name,
                      test_id)
VALUES (1,
        'SINGLETON',
        'Java tili kaisy jyly negizdelgen?',
        1),
       (2,
        'SINGLETON',
        'Javada kancha tip bar?',
        2),
       (3,
        'MULTIPLE',
        'HTML kandai chechmelenet?',
        3),
       (4,
        'SINGLETON',
        'CSS degen emne?',
        4),
       (5,
        'MULTIPLE',
        'BackEnd programmistter kimder?',
        5),
       (6,
        'SINGLETON',
        'FrontEnd programmistter kimder?',
        6);
INSERT INTO tasks(id,
                  deadline,
                  description,
                  file,
                  name,
                  lesson_id)
VALUES (1,
        '2023-04-10',
        'Introduction task for JAVA - 8',
        'diagram.file',
        'Introduction task',
        1),
       (2,
        '2023-04-11',
        'Git task for JAVA - 8',
        'diagram.file',
        'Git task',
        2),
       (3,
        '2023-04-12',
        'SQL task for JAVA - 8',
        'sql.file',
        'SQL task',
        3),
       (4,
        '2023-04-13',
        'PostgreSQL options task for JAVA - 8',
        'diagram.file',
        'PostgreSQL options task',
        4),
       (5,
        '2023-04-14',
        'Swagger task for JAVA - 8',
        'swagger.file',
        'Swagger task',
        5),
       (6,
        '2023-04-15',
        'Postman task for JAVA - 8',
        'diagram.file',
        'Postman task',
        5);
INSERT INTO result_of_tests(id,
                            count_correct,
                            count_in_correct,
                            student_id,
                            test_id)
VALUES (1,
        4,
        6,
        1,
        1),
       (2,
        5,
        5,
        2,
        2),
       (3,
        6,
        4,
        3,
        3),
       (4,
        7,
        3,
        4,
        4),
       (5,
        8,
        2,
        5,
        5),
       (6,
        10,
        0,
        6,
        5);
INSERT INTO options(id,
                    is_true,
                    text,
                    question_id)
VALUES (1,
        false,
        'this is test"s text',
        1),
       (2,
        false,
        'this is test"s text',
        2),
       (3,
        true,
        'this is test"s text',
        3),
       (4,
        true,
        'this is test"s text',
        4),
       (5,
        false,
        'this is test"s text',
        5),
       (6,
        true,
        'this is test"s text',
        6),
       (7,
        true,
        'this is test"s text',
        6);