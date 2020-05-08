DELIMITER $$

DROP VIEW IF EXISTS `view_available_questionslib` $$

create view view_available_questionslib as(
      /**********查询全部题库的试题************/ 
 SELECT  DISTINCT tvq.`id` AS 'questionId',
             tvq.*,
             tsq.`school_id`,
             tkq.knowledge_id,
             tck.`version_id` AS 'knowledge_version_id'
           FROM  table_version_question tvq,table_school_questionlib tsq,table_knowledge_question tkq,table_course_knowledge tck
         WHERE tvq.questionlib_id=tsq.`questionlib_id`
           AND tkq.question_id=tvq.id
           AND tkq.`knowledge_id`=tck.`id`
   AND tvq.`del_flag`='0' AND tsq.`del_flag`='0'
   AND NOW() BETWEEN tsq.valid_start_date AND tsq.valid_end_date 
   GROUP BY tvq.id, tsq.`school_id`,tvq.version_id
   /*group by tvq.`id`  having count(distinct tvq.`id`)>1*/
) $$


DROP VIEW IF EXISTS `view_course_class`$$
CREATE   VIEW `view_course_class` AS (
SELECT
  `tc`.`id`         AS `id`,
  `tc`.`teacher_id` AS `teacher_id`,
  `tc`.`class_id`   AS `class_id`,
  `tc`.`course_id`  AS `course_id`,
  `tc`.`verson_id`  AS `verson_id`,
  `su`.`name`       AS `teacher_name`,
  `tcr`.`title`     AS `title`,
  `tcc`.`title`     AS `className`,
  `tcv`.`title`     AS `versionName`
FROM `table_teacher_class_course` `tc`,
      `sys_user` `su`,
      `table_course` `tcr`,
      `table_course_vesion` `tcv`,
      `table_class` `tcc`
WHERE  `tc`.`teacher_id` = `su`.`id` 
       AND  `tc`.`course_id` = `tcr`.`id`
       AND `tc`.`verson_id` = `tcv`.`id`
       AND `tc`.`class_id` = `tcc`.`id`
)$$




DROP VIEW IF EXISTS `view_course_question`$$

CREATE  VIEW `view_course_question` AS (
SELECT
  `tc`.`id`           AS `id`,
  `tc`.`title`        AS `title`,
  `tc`.`specialty_id` AS `specialty_id`,
  `tc`.`level`        AS `level`,
  `tc`.`phase`        AS `phase`,
  `tc`.`couse_system` AS `couse_system`,
  `tc`.`create_by`    AS `create_by`,
  `tc`.`create_date`  AS `create_date`,
  `tc`.`update_by`    AS `update_by`,
  `tc`.`update_date`  AS `update_date`,
  `tv`.`id`           AS `version_id`,
  `tv`.`title`        AS `version_name`,
  `tck`.`id`          AS `knowledge_id`,
  `tck`.`title`       AS `knowledge_name`,
  `tkq`.`question_id` AS `question_id`,
  `tvq`.`title`       AS `question_name`,
  `tvq`.`ques_type`   AS `ques_type`,
  `tvq`.`office_id`   AS `office_id`
FROM ((((`table_course` `tc`
      JOIN `table_course_vesion` `tv`)
     JOIN `table_course_knowledge` `tck`)
    JOIN `table_knowledge_question` `tkq`)
   JOIN `table_version_question` `tvq`)
WHERE ((`tc`.`id` = `tv`.`course_id`)
       AND (`tv`.`id` = `tck`.`version_id`)
       AND (`tck`.`id` = `tkq`.`knowledge_id`)
       AND (`tkq`.`question_id` = `tvq`.`id`)
       and (tvq.del_flag='0')
       )
)$$



DROP VIEW IF EXISTS `view_exam_class`$$

CREATE  VIEW `view_exam_class` AS (
SELECT
  `te`.`id`                    AS `id`,
  `te`.`title`                 AS `title`,
  `te`.`exam_type`             AS `exam_type`,
  `te`.`teacher_id`            AS `teacher_id`,
  `te`.`version_id`            AS `version_id`,
  `te`.`exam_place`            AS `exam_place`,
  `te`.`begin_time`            AS `begin_time`,
  `te`.`end_time`              AS `end_time`,
  `te`.`isab`                  AS `isab`,
  `te`.`total_score`           AS `total_score`,
  `te`.`del_flag`              AS `del_flag`,
  `te`.`isNew`                 AS `isNew`,
  `te`.`exam_mode`             AS `exam_mode`,
  `te`.`question_radio`        AS `question_radio`,
  `te`.`question_multiple`     AS `question_multiple`,
  `te`.`question_blank`        AS `question_blank`,
  `te`.`question_short_answer` AS `question_short_answer`,
  `te`.`question_compute`      AS `question_compute`,
  `te`.`remarks`               AS `remarks`,
  `te`.`difficult`             AS `difficult`,
  `te`.`simple`                AS `simple`,
  `te`.`general`               AS `general`,
  `te`.`exam_hours`            AS `exam_hours`,
  `te`.`create_date`           AS `create_date`,
  `tcc`.`class_id`             AS `class_id`,
  `tcc`.`course_id`            AS `course_id`,
  `tcc`.`verson_id`            AS `verson_id`
FROM ((`table_examination` `te`
    JOIN `table_exam_class` `tec`)
   JOIN `table_teacher_class_course` `tcc`)
WHERE ((`te`.`id` = `tec`.`exam_id`)
       AND (`tec`.`class_id` = `tcc`.`class_id`))
         
)$$


DROP VIEW IF EXISTS `view_exam_question`$$

CREATE  VIEW `view_exam_question` AS (
SELECT
  `te`.`id`              AS `exam_id`,
  `te`.`title`           AS `exam_name`,
  `tvq`.`id`             AS `id`,
  `tvq`.`exam_code`      AS `exam_code`,
  `tvq`.`ques_type`      AS `ques_type`,
  `tvq`.`ques_level`     AS `ques_level`,
  `tvq`.`ques_point`     AS `ques_point`,
  `tvq`.`title`          AS `title`,
  `tvq`.`choice0`        AS `choice0`,
  `tvq`.`choice1`        AS `choice1`,
  `tvq`.`choice2`        AS `choice2`,
  `tvq`.`choice3`        AS `choice3`,
  `tvq`.`choice4`        AS `choice4`,
  `tvq`.`choice5`        AS `choice5`,
  `tvq`.`choice6`        AS `choice6`,
  `tvq`.`choice7`        AS `choice7`,
  `tvq`.`choice8`        AS `choice8`,
  `tvq`.`choice9`        AS `choice9`,
  `tvq`.`answer0`        AS `answer0`,
  `tvq`.`answer1`        AS `answer1`,
  `tvq`.`answer2`        AS `answer2`,
  `tvq`.`answer3`        AS `answer3`,
  `tvq`.`answer4`        AS `answer4`,
  `tvq`.`answer5`        AS `answer5`,
  `tvq`.`answer6`        AS `answer6`,
  `tvq`.`answer7`        AS `answer7`,
  `tvq`.`answer8`        AS `answer8`,
  `tvq`.`answer9`        AS `answer9`,
  `tvq`.`description`    AS `description`,
  `tvq`.`count`          AS `count`,
  `tvq`.`writer`         AS `writer`,
  `tvq`.`checker`        AS `checker`,
  `tvq`.`office_id`      AS `office_id`,
  `tvq`.`tearch_id`      AS `tearch_id`,
  `tvq`.`version_id`     AS `version_id`,
  `tvq`.`questionlib_id` AS `questionlib_id`,
  `tvq`.`create_by`      AS `create_by`,
  `tvq`.`create_date`    AS `create_date`,
  `tvq`.`update_by`      AS `update_by`,
  `tvq`.`update_date`    AS `update_date`,
  `tvq`.`del_flag`       AS `del_flag`
FROM (((`table_examination` `te`
     JOIN `table_exam_detail` `ted`)
    JOIN `table_exam_detail_question` `tedq`)
   JOIN `table_version_question` `tvq`)
WHERE ((`te`.`id` = `ted`.`exam_id`)
       AND (`ted`.`id` = `tedq`.`exam_detail_id`)
       AND (`tedq`.`question_id` = `tvq`.`id`))
)$$


DROP VIEW IF EXISTS `view_questions`$$

CREATE  VIEW `view_questions` AS 
SELECT
  `a`.`id`             AS `id`,
  `a`.`exam_code`      AS `examCode`,
  `a`.`ques_type`      AS `quesType`,
  `a`.`ques_level`     AS `quesLevel`,
  `a`.`ques_point`     AS `quesPoint`,
  `a`.`title`          AS `title`,
  `a`.`question_code`  AS `questionCode`,
  `a`.`answer0`        AS `answer0`,
  `a`.`description`    AS `description`,
  `a`.`writer`         AS `writer`,
  `a`.`checker`        AS `checker`,
  `a`.`office_id`      AS `office.id`,
  `a`.`tearch_id`      AS `tearchId`,
  `a`.`version_id`     AS `versionId`,
  `a`.`questionlib_id` AS `questionlibId`,
  `a`.`create_by`      AS `createBy.id`,
  `a`.`create_date`    AS `createDate`,
  `a`.`update_by`      AS `updateBy.id`,
  `a`.`update_date`    AS `updateDate`,
  `a`.`del_flag`       AS `delFlag`,
  `b`.`knowledge_id`   AS `knowledgeId`
FROM (`table_knowledge_question` `b`
   JOIN `table_version_question` `a`)
WHERE ((`b`.`question_id` = `a`.`id`)
       AND (`a`.`del_flag` = _utf8'0')
)$$


 
DROP VIEW IF EXISTS `view_student`$$

CREATE  VIEW `view_student` AS (
SELECT
  `ts`.`id`           AS `id`,
  `ts`.`name`         AS `name`,
  `ts`.`class_id`     AS `class_id`,
  `ts`.`std_code`     AS `std_code`,
  `ts`.`std_sex`      AS `std_sex`,
  `ts`.`std_age`      AS `std_age`,
  `ts`.`std_phone`    AS `std_phone`,
  `ts`.`std_email`    AS `std_email`,
  `ts`.`std_password` AS `std_password`,
  `ts`.`create_by`    AS `create_by`,
  `ts`.`create_date`  AS `create_date`,
  `ts`.`update_by`    AS `update_by`,
  `ts`.`update_date`  AS `update_date`,
  `ts`.`del_flag`     AS `del_flag`,
  `tc`.`title`        AS `className`,
  `so`.`id`           AS `schoolId`,
  `so`.`name`         AS `schoolName`
FROM ((`table_student` `ts`
    JOIN `table_class` `tc`)
   JOIN `sys_office` `so`)
WHERE ((`so`.`id` = `tc`.`company_id`)
       AND (`tc`.`id` = `ts`.`class_id`))
)$$      

DELIMITER ;