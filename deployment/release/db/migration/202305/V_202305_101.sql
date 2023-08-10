/**
 * 目的：新增表字段存储过程
 */
# 添加字段存储过程
DROP PROCEDURE IF EXISTS `prc_add_column`;
CREATE PROCEDURE IF NOT EXISTS `prc_add_column` (IN `var_db_name` VARCHAR ( 64 ),
                                                 IN `var_table_name` VARCHAR ( 64 ),
												 IN `var_column_name` VARCHAR ( 64 ),
												 IN `var_sql` VARCHAR ( 1024 ))
BEGIN
    -- 当前数据库是否指定的数据库
	DECLARE db_count INT DEFAULT 0;
	declare num int default 5;
	SET @db_count = 0;
	SET @p_sql = 'SELECT DATABASE() INTO @db_count;'
	PREPARE stmt FROM @p_sql;
    EXECUTE stmt;
    -- 查询该数据库下的数据库表是否存在
    -- 查询表字段是否存在
END;

# 删除字段存储过程
# 修改字段存储过程
# 添加索引存储过程
# 删除索引存储过程
# 修改索引存储过程

select count(table_name)into @p_tablenum from information_schema.tables where table_schema=database() and table_name='t_monkey_article'
select count(table_name) from information_schema.tables where table_schema=database() and table_name='t_monkey_article'
select table_name from information_schema.tables where table_schema=database() and table_name='t_monkey_article'

SELECT database();
SELECT DATABASE() INTO @db_count;
SELECT @db_count