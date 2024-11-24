-- 创建模块表
CREATE TABLE taskModule (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '模块ID',
                        skill_id BIGINT NOT NULL COMMENT '归属技能',
                        module_name VARCHAR(255) NOT NULL COMMENT '模块名称',
                        objective VARCHAR(255) COMMENT '模块目标',
                        create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

-- 创建任务表
CREATE TABLE task (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '任务ID',
                      module_id BIGINT NOT NULL COMMENT '归属模块ID',
                      task_name VARCHAR(255) NOT NULL COMMENT '任务名称',
                      description VARCHAR(500) COMMENT '任务描述',
                      difficulty BIGINT COMMENT '任务难度',
                      token_reward BIGINT COMMENT '代币奖励',
                      experience_reward BIGINT COMMENT '经验奖励',
                      assignment VARCHAR(500) COMMENT '任务作业',
                      create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                      CONSTRAINT fk_module FOREIGN KEY (module_id) REFERENCES taskModule(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE task_completion (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '任务完成记录ID',
                                 task_id BIGINT NOT NULL COMMENT '任务ID',
                                 user_id BIGINT NOT NULL COMMENT '用户ID',
                                 text_content VARCHAR(1000) COMMENT '文字内容',
                                 image_url VARCHAR(500) COMMENT '图片URL',
                                 feedback VARCHAR(1000) COMMENT '任务提交反馈',
                                 score DECIMAL(5, 2) COMMENT '任务完成分数',
                                 is_completion BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否完成',
                                 create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
);

