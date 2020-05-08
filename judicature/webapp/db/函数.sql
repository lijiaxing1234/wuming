DELIMITER $$
/****得到所有子节点****/
CREATE FUNCTION getCourseKnowledgeChildList(rootId VARCHAR(32))
RETURNS VARCHAR(4000)
 BEGIN
   DECLARE sTemp VARCHAR(4000);
   DECLARE sTempChd VARCHAR(4000);

     SET sTemp = '$';
     SET sTempChd =rootId;

     WHILE sTempChd IS NOT NULL DO
     SET sTemp = CONCAT(sTemp,',',sTempChd);
      SELECT GROUP_CONCAT(id) INTO sTempChd FROM table_course_knowledge  WHERE del_flag ='0'   AND  FIND_IN_SET(parent_id,sTempChd)>0;
     END WHILE;
    RETURN sTemp;
 END $$

DELIMITER $$     
SET GLOBAL log_bin_trust_function_creators=1;


/****得到所有父节点****/
DELIMITER $$
CREATE FUNCTION getCourseKnowledgeParentList(rootId VARCHAR(32))
RETURNS varchar(4000)
BEGIN
  DECLARE sTemp varchar(4000);
  DECLARE sTempChd varchar(4000);
  
  SET sTemp = '$';
  SET sTempChd =rootId; /*CAST(rootId AS CHAR)*/

  WHILE sTempChd is not null DO 
    SET sTemp = concat(sTemp,',',sTempChd);
    SELECT group_concat(parent_id) INTO sTempChd FROM table_course_knowledge where FIND_IN_SET(id,sTempChd)>0;
  END WHILE;
  RETURN sTemp;
END $$ 
DELIMITER $$   


/*********专业**********/
DELIMITER $$

USE `questionlib`$$

DROP FUNCTION IF EXISTS `getSpecialtyChildList`$$

CREATE  FUNCTION `getSpecialtyChildList`(rootId VARCHAR(32)) RETURNS varchar(4000)
BEGIN
   DECLARE sTemp VARCHAR(4000);
   DECLARE sTempChd VARCHAR(4000);
     SET sTemp = '$';
     SET sTempChd =rootId;
     WHILE sTempChd IS NOT NULL DO
     SET sTemp = CONCAT(sTemp,',',sTempChd);
      SELECT GROUP_CONCAT(id) INTO sTempChd FROM table_specialty  WHERE del_flag ='0'   AND  FIND_IN_SET(parent_id,sTempChd)>0;
     END WHILE;
    RETURN sTemp;
 END$$

DELIMITER ;