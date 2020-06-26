CREATE TABLE Article
(
  `id`        int(11)                                              NOT NULL AUTO_INCREMENT,
  `title`     varchar(30) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL,
  `author`    varchar(30) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL,
  `content`   mediumtext CHARACTER SET gbk COLLATE gbk_chinese_ci  NULL,
  `file_name` varchar(30) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL,
  `state`     smallint(2)                                          NULL DEFAULT 1 COMMENT '状态',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = gbk
  COLLATE = gbk_chinese_ci
  AUTO_INCREMENT = 11
  ROW_FORMAT = COMPACT;