
truncate table sys_log;

truncate table table_version_question;

truncate table table_teacher_questionslib_split;

truncate table table_teacher_questions_record;

truncate table table_teacher_questions_percent;

truncate table table_teacher_knowledge;

truncate table table_teacher_class_course;

truncate table table_student_task;

truncate table table_student_review;

truncate table  table_student_answer;

truncate table table_student;

truncate table  table_course;

truncate table table_specialty; /**/

truncate table table_school_version;

truncate table table_school_questionlib;

truncate table table_resource;

truncate table  table_questionlib_import_apply;

truncate table  table_questionlib_import;

truncate table  table_question;
truncate table  table_message_student_copy;
truncate table  table_message_student;
truncate table  table_message;
truncate table  table_knowledge_question_detail;
truncate table  table_knowledge_question;
truncate table  table_import_question;
truncate table  table_examination;
truncate table  table_exam_specility;
truncate table  table_exam_question_score;
truncate table  table_exam_knowledge;
truncate table  table_exam_detail_question;
truncate table  table_exam_detail;
truncate table  table_exam_class;
truncate table  table_course_vesion;
truncate table  table_course_questionlib;
truncate table  table_course_knowledge; /**/

truncate table  table_class ;
truncate table  table_book_version;

truncate table  table_answer;

/*初始化专业和知识点*/

INSERT INTO `table_specialty`(`id`,`title`, `desciption`,`specialty_code`,`create_by`,`create_date`,`update_by`,`update_date`,`del_flag`,`parent_id`,`parent_ids`)
VALUES ('1','根节点','根节点','','1','2016-12-08 11:04:28','1','2016-12-08 11:04:28','0','0','0,');

INSERT INTO `table_course_knowledge`(`id`,`knowledge_code`,`title`,`credit_hours`,`version_id`,`level`,`parent_id`,`parent_ids`,`create_by`,`create_date`,`update_by`,`update_date`,`del_flag`)
VALUES ('1','000000','根节点','','','','0','0,','1','2016-12-08 11:04:28','1','2016-12-08 11:04:28','0');




DELETE FROM sys_dict WHERE del_flag='1';
DELETE FROM sys_menu WHERE del_flag='1';
DELETE  from sys_menu where id='85';
DELETE  from sys_menu where id='24';

TRUNCATE TABLE  sys_log;
TRUNCATE TABLE  sys_area;
TRUNCATE TABLE  sys_office;
TRUNCATE TABLE  sys_role;
TRUNCATE TABLE  sys_user;
TRUNCATE TABLE  sys_user_role;
TRUNCATE TABLE  sys_role_office; 
TRUNCATE TABLE  sys_role_menu;

INSERT  INTO `sys_area`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`,sort)
VALUES ('1','0','0,','100000','中国','1','1','2016-12-08 11:04:28','1','2016-12-08 11:04:28',NULL,'0',1),
       ('2','1','0,1,','110000','北京市','2','1','2016-12-08 11:04:28','1','2016-12-08 11:04:28',NULL,'0',2);

INSERT  INTO  sys_office(`id`,`parent_id`,`parent_ids`,`area_id`,`code`,`name`,`useable`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`,sort) 
VALUES ('1','0','0,','2','100000','教学辅助平台','1','1','1',NULL,NULL,NULL,NULL,NULL,NULL,'1','2016-12-08 11:04:28','1','2016-12-08 11:04:28',NULL,'0',1),
		('2','1','0,1,','2','100001','化工出版社','1','1','1',NULL,NULL,NULL,NULL,NULL,NULL,'1','2016-12-08 11:04:28','1','2016-12-08 11:04:28',NULL,'0',2),
		('3','1','0,1,','2','100002','数字出版中心','1','2','1',NULL,NULL,NULL,NULL,NULL,NULL,'1','2016-12-08 11:04:28','1','2016-12-08 11:04:28',NULL,'0',3);

		
INSERT  INTO `sys_role`(`id`,`office_id`,`name`,`data_scope`,`useable`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`)
VALUES ('1','1','系统管理员','1','1','1','2016-12-08 11:04:28','1','2016-12-08 11:04:28',NULL,'0'),
('2','3','数字出版中心管理员','2','1','1','2016-12-08 11:04:28','1','2016-12-08 11:04:28',NULL,'0');

INSERT  INTO `sys_role_menu`(`role_id`,`menu_id`)
SELECT '1',id FROM sys_menu   ORDER BY id;


INSERT  INTO `sys_user`(`id`,`company_id`,`office_id`,`login_name`,`password`,`no`,`name`,`email`,`phone`,`mobile`,`user_type`,`login_ip`,`login_date`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) 
VALUES ('1','1','1','super','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','0001','super','2927700371@qq.com','8675','8675',NULL,NULL,NULL,'1','2016-12-08 11:04:28','1','2016-12-08 11:04:28','最高管理员','0');

INSERT  INTO `sys_user_role`(`user_id`,`role_id`) VALUES ('1','1');
