create database demo;
use demo;

-- 用户模块
create table user
(
    user_id       bigint auto_increment primary key comment '用户id',
    real_name     varchar(255) not null comment '真实姓名',
    user_password varchar(255) not null comment '密码',
    user_role     tinyint      not null default 0 comment '用户角色, 0 普通用户 1 管理员',
    email         varchar(255) not null comment '邮箱，账号为邮箱地址',
    org           varchar(255) not null comment '所属组织'
) comment '用户表';

-- 项目管理模块
create table project
(
    project_id      bigint auto_increment primary key comment '项目id',
    project_name    varchar(255)  not null comment '项目名',
    project_type_id bigint        not null comment '项目分类id',
    target          varchar(1024) not null comment '项目目的',
    create_time     datetime      not null default current_timestamp comment '创建时间'
) comment '项目管理表';

create table project_type
(
    project_type_id bigint auto_increment primary key comment '项目分类id',
    type_name       varchar(255) not null comment '分类名'
) comment '项目分类表';

create table project_user
(
    project_user_id bigint auto_increment primary key comment '项目用户联系id',
    project_id      bigint  not null comment '项目id',
    user_id         bigint  not null comment '所属成员id',
    user_job        tinyint not null comment '项目组成员职责'
) comment '项目用户联系表';

create table project_device
(
    project_device_id bigint auto_increment primary key comment '项目设备联系id',
    project_id        bigint       not null comment '项目id',
    device_ids        varchar(255) not null comment '项目使用设备id数组，使用英文逗号分隔'
) comment '项目设备联系表';

-- 设备管理模块
create table device
(
    device_id                 bigint auto_increment primary key comment '设备id',
    device_type_id            bigint       not null comment '设备分类id',
    device_name               varchar(255) not null comment '设备名称',
    lab_id                    bigint       not null comment '所属实验室',
    device_func               varchar(255) comment '设备功能',
    operation_instruction_url varchar(255) comment '设备使用说明书存储路径',
    is_in_use                 tinyint      not null default 0 comment '是否被使用 0 不是， 1 是',
    is_under_maintenance      tinyint      not null default 0 comment '是否被维护 0 不是， 1 是'
) comment '设备信息表';

create table device_type
(
    device_type_id bigint auto_increment primary key comment '设备分类id',
    type_name      varchar(255) not null comment '设备分类名称'
) comment '设备分类表';

create table device_maintenance
(
    device_maintenance_id bigint auto_increment primary key comment '设备维护id',
    device_id             bigint   not null comment '设备id',
    maintenance_staff     varchar(512) comment '维护人员列表，用英文,分隔',
    content               varchar(1024) comment '维护内容',
    remark                varchar(1024) comment '备注',
    start_time            datetime not null comment '开始时间',
    expected_end_time     datetime not null comment '预期结束时间',
    actual_end_time       datetime comment '实际结束时间'
) comment '设备维护表';
-- 工艺管理模块

-- 预约管理模块
create table device_order
(
    order_id     bigint auto_increment primary key comment '设备预约id',
    user_id      bigint   not null comment '预约用户id',
    device_id    bigint not null comment '设备id',
    start_time   datetime not null comment '开始时间',
    end_time     datetime comment '结束时间',
    create_time  datetime not null default current_timestamp comment '开始时间',
    audit_result tinyint  not null comment '审核结果：0 成功 1 失败'
) comment '设备预约表';

-- 培训管理模块
create table train_program
(
    train_program_id     bigint auto_increment primary key comment '培训项目编号',
    program_name         varchar(255)  not null comment '培训项目名称',
    program_introduction varchar(1024) not null comment '培训项目介绍'
) comment '项目培训表';

-- 测试任务

-- 系统管理模块
create table lab
(
    lab_id   bigint auto_increment primary key comment '实验室id',
    lab_name varchar(255) not null comment '实验室名称',
    linkman  varchar(255) not null comment '联系人',
    tel      varchar(255) not null comment '联系电话',
    layout   varchar(255) not null comment '实验室布局'
) comment '实验室表';

create table send_mail
(
    send_mail_id bigint auto_increment primary key comment '发送邮件记录id',
    receiver varchar(1024) not null comment '接收人邮箱，以,分隔',
    subject varchar(255) not null comment '邮件主题',
    content text comment '邮件内容',
    send_time datetime not null default current_timestamp comment '发送时间',
    filename varchar(512) comment '附件名，可以为空，如果有多个附件，请用逗号分隔'
) comment '发送邮件记录表';

