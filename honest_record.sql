drop table if exists accusation;

drop table if exists accuse_type;

drop table if exists decision_paper;

drop table if exists department;

drop table if exists faculty;

drop table if exists log;

drop table if exists notice;

drop table if exists power;

drop table if exists problem_field;

drop table if exists problem_type;

drop table if exists role;

drop table if exists role_power;

/*==============================================================*/
/* Table: accusation                                            */
/*==============================================================*/
create table accusation
(
   a_id                 int not null auto_increment comment '举报信息编号',
   pt_id                int comment '问题分类',
   pf_id                int comment '问题领域',
   at_id                int comment '举报类型',
   accused_user_id      int comment '被举报人职工号',
   accuser_name         varchar(50) comment '举报人姓名',
   accuser_phone        varchar(50) comment '举报人联系方式',
   accuse_date          date comment '举报时间',
   deal_date            date comment '受理时间',
   dealer_user_id       int comment '受理人编号',
   create_time          date comment '创建时间',
   update_time          date comment '修改时间',
   check_state          tinyint comment '审核状态',
   deleted              tinyint comment '删除状态',
   primary key (a_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table accusation comment '举报信息';

/*==============================================================*/
/* Index: create_time_index                                     */
/*==============================================================*/
create index create_time_index on accusation
(
   create_time
);

/*==============================================================*/
/* Index: update_time_index                                     */
/*==============================================================*/
create index update_time_index on accusation
(
   update_time
);

/*==============================================================*/
/* Table: accuse_type                                           */
/*==============================================================*/
create table accuse_type
(
   at_id                int not null auto_increment comment '举报类型编号',
   at_name              varchar(50) comment '举报类型名称',
   create_time          date comment '创建时间',
   update_time          date comment '修改时间',
   deleted              tinyint comment '删除状态',
   primary key (at_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table accuse_type comment '举报类型';

/*==============================================================*/
/* Table: decision_paper                                        */
/*==============================================================*/
create table decision_paper
(
   dp_id                int not null auto_increment comment '决策书编号',
   title                varchar(50) comment '决策书标题',
   reply_name           varchar(50) comment '回复单位名称',
   content              varchar(2000) comment '回复内容',
   inscribe_name        varchar(50) comment '落款单位',
   inscribe_time        date comment '落款时间',
   created_user_id      int comment '创建人编号',
   create_time          date comment '模板创建时间',
   update_time          date comment '模板修改时间',
   primary key (dp_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table decision_paper comment '决策书';

/*==============================================================*/
/* Table: department                                            */
/*==============================================================*/
create table department
(
   d_id                 int not null auto_increment comment '部门编号',
   d_name               varchar(50) comment '部门名称',
   d_phone              varchar(50) comment '联系方式',
   d_address            varchar(100) comment '联系地址',
   create_time          date comment '部门信息创建时间',
   update_time          date comment '部门信息修改时间',
   deleted              tinyint comment '删除状态',
   primary key (d_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table department comment '部门';

/*==============================================================*/
/* Table: faculty                                               */
/*==============================================================*/
create table faculty
(
   user_id              int not null auto_increment comment '职工号',
   d_id                 int comment '所属部门编号',
   user_name            varchar(50) comment '姓名',
   password             varchar(50) comment '密码',
   gender               int comment '性别',
   id_card              char(18) comment '身份证号',
   phone                char(11) comment '联系电话',
   position             varchar(50) comment '职务',
   work_start_time      int comment '参加工作起始年份',
   work_state           tinyint comment '在职状态',
   r_id                 int comment '所属角色编号',
   create_time          date comment '用户创建时间',
   update_time          date comment '用户修改时间',
   check_state          tinyint comment '审核状态',
   deleted              tinyint comment '删除状态',
   primary key (user_id)
)
auto_increment = 100000
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table faculty comment '教职工';

/*==============================================================*/
/* Index: name_index                                            */
/*==============================================================*/
create index name_index on faculty
(
   user_name
);

/*==============================================================*/
/* Table: log                                                   */
/*==============================================================*/
create table log
(
   l_id                 bigint not null auto_increment comment '日志编号',
   operator_name        varchar(200) comment '操作名称',
   operator_time        date comment '操作时间',
   operatoer_user_id    int comment '操作人编号',
   host_ip              varchar(50) comment '操作主机ip',
   log_type             varchar(50) comment '日志类型',
   primary key (l_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table log comment '日志';

/*==============================================================*/
/* Index: operate_time_index                                    */
/*==============================================================*/
create index operate_time_index on log
(
   operator_time
);

/*==============================================================*/
/* Table: notice                                                */
/*==============================================================*/
create table notice
(
   n_id                 int not null auto_increment comment '通知编号',
   publish_user_id      int comment '通知发布人',
   content              varchar(2000) comment '通知内容',
   create_time          date comment '创建时间',
   update_time          date comment '修改时间',
   title                varchar(50) comment '通知标题',
   n_type               tinyint comment '通知类型',
   primary key (n_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table notice comment '通知公告';

/*==============================================================*/
/* Table: power                                                 */
/*==============================================================*/
create table power
(
   p_id                 char(3) not null comment '权限编号',
   p_name               varchar(50) comment '权限名称',
   pp_id                char(3) comment '父权限编号',
   create_time          date comment '权限创建时间',
   update_time          date comment '权限修改时间',
   primary key (p_id)
);

alter table power comment '权限';

/*==============================================================*/
/* Table: problem_field                                         */
/*==============================================================*/
create table problem_field
(
   pf_id                int not null auto_increment comment '问题领域编号',
   pf_name              varchar(50) comment '问题领域名称',
   create_time          date comment '创建时间',
   update_time          date comment '修改时间',
   deleted              tinyint comment '删除状态',
   primary key (pf_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table problem_field comment '问题领域';

/*==============================================================*/
/* Table: problem_type                                          */
/*==============================================================*/
create table problem_type
(
   pt_id                int not null auto_increment comment '问题分类编号',
   pt_name              varchar(50) comment '问题分类名称',
   create_time          date comment '创建时间',
   update_time          date comment '修改时间',
   deleted              tinyint comment '删除状态',
   primary key (pt_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table problem_type comment '问题分类';

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   r_id                 int not null auto_increment comment '角色编号',
   r_name               varchar(50) comment '角色名称',
   create_time          date comment '角色创建时间',
   update_time          date comment '角色修改时间',
   primary key (r_id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

alter table role comment '角色';

/*==============================================================*/
/* Table: role_power                                            */
/*==============================================================*/
create table role_power
(
   r_id                 int not null comment '角色编号',
   p_id                 char(3) not null comment '权限编号',
   primary key (r_id, p_id)
);

alter table role_power comment '角色权限';


/*==============================================================*/
/* 权限表power初始数据                                           */
/*==============================================================*/

insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('100','用户管理',NULL,NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('200','档案管理',NULL,NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('300','权限管理',NULL,NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('400','分析决策',NULL,NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('500','系统维护',NULL,NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('600','通知公告',NULL,NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('700','日志管理',NULL,NOW(),NOW());

insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('101','添加新用户','100',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('102','删除用户','100',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('103','更新用户基本信息','100',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('104','查询用户信息','100',NOW(),NOW());

insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('201','添加举报档案','200',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('202','删除举报档案','200',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('203','更新举报档案','200',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('204','查询举报档案','200',NOW(),NOW());

insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('301','新增角色','300',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('302','删除角色','300',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('303','更新角色权限','300',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('304','查询角色权限','300',NOW(),NOW());

insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('401','新增决策模板','400',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('402','删除决策模板','400',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('403','修改决策模板','400',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('404','查询决策模板','400',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('405','生成决策书','400',NOW(),NOW());

insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('501','部门管理维护','500',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('502','举报类型管理维护','500',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('503','问题分类管理维护','500',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('504','问题领域管理维护','500',NOW(),NOW());

insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('601','发布新通知','600',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('602','删除通知','600',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('603','修改通知','600',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('604','查询通知','600',NOW(),NOW());

insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('701','登录日志查询','700',NOW(),NOW());
insert into power(p_id,p_name,pp_id,create_time,update_time) VALUES('702','操作日志查询','700',NOW(),NOW());




