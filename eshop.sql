drop database if exists eshop;
create database eshop;
use eshop;



/*用户表*/
create table tb_user(
user_id int primary key auto_increment comment '用户自增逐渐',
user_name varchar(25) not null unique comment '用户名',
reg_time datetime not null default now() comment '注册时间',
avatar_url varchar(50) not null default 'http://localhost:8080/static/imgs/aval/default.png' comment '头像url',
pwd varchar(64) not null comment '加密后的密码',
status tinyint not null default 0 comment '状态码，0正常，1异常，2 逻辑删除'
)auto_increment =999;

#测试账户
insert into tb_user (user_name,pwd) values('test','123456');



/*配送地址列表*/
create table tb_address(
address_id int primary key auto_increment comment '地址Id',
user_id int not null comment '关联用户id',
address varchar(80) not null comment '配送地址',
tag varchar(10) comment '标签',
status tinyint not null default 0  comment '地址状态，0 普通，1 默认地址，2 逻辑删除'
);



/*商品类型表*/
create table tb_goods_type(
type_id int primary key auto_increment comment '商品类型id',
type_name varchar(25) not null comment '商品类型名称',
type_desc varchar(255) comment '商品类型描述',
parent_type_id int not null default 0 comment '父类类型',
is_delete tinyint default 0 comment '逻辑删除标志位，0 正常，1 逻辑删除'
);


/*商品表*/
create table tb_goods(
goods_id int primary key auto_increment comment '商品id',
goods_name varchar(25) not null comment '商品名称',
goods_desc varchar(255) comment '商品描述',
goods_type_id int not null default 0 comment '商品类型id，暂不考虑属于多种类型的情况',
goods_stock int not null default 0 comment '库存',
goods_sold int not null default 0 comment '已售',
goods_price decimal(12,4) not null default 0 comment '单价',
status tinyint default 0 comment '状态标志位，0 待上架，1 已上架  2 已下架 ，3 逻辑删除'
);

/*商品图片表*/
create table tb_goods_img(
img_id int primary key auto_increment comment '图片id',
goods_id int not null comment '商品id',
img_url varchar(50) not null comment '商品url',
is_delete varchar(255) not null default 0 comment '逻辑删除标志位,0 正常 ，1 已删除'
);



/*商品活动表*/
create table tb_activity(
activity_id int primary key auto_increment comment '活动id',
activity_name varchar(25) not null comment '活动名称',
activity_desc varchar(255) comment '活动描述',
activity_poster_url varchar(50) comment '活动海报url',
start_time datetime not null default now() comment '活动开始时间',
end_time datetime not null default now() comment '活动结束时间',
activity_type tinyint not null default 1 comment '活动类型 0001 打折 ，0010 满减 ，附送 0100 ,如几种同时存在，则按位或',
status tinyint not null default 0 comment '活动状态 0 待开始 ，1 进行中，2 已结束，3 逻辑删除 '
);


/*商品活动关联表*/
create table tb_activity_goods(
activity_id int not null comment '活动id',
goods_id int not null comment '活动关联商品id'
);



/*打折活动规则表*/
create table tb_rule_dicount(
activity_id int not null comment '活动id',
discount_limit float not null default 0 comment '打折限制 0 代表无限制',
discount float not null default 1 comment '折扣'
);


/*满减活动规则表*/
create table tb_rule_full_reduction(
activity_id int not null comment '活动id',
reduction_limit float not null default 0 comment '满减限制',
reduction float not null default 1 comment '折扣'
);

/*附送活动规则表*/
create table tb_rule_attach(
activity_id int not null comment '活动id',
attach_limit float not null default 0 comment '赠送限制，0代表无限制',
attach_id decimal(12,4) not null default 0 comment '赠送商品id'
);



/*购物车*/
create table tb_shop_cart(
user_id int not null comment '关联用户id',
goods_id int not null comment '关联商品',
amount int not null default 0 comment '商品数量',
is_delete tinyint not null default 0 comment '0正常,1逻辑删除'
); 



/*商品订单表*/
create table tb_order(
order_number varchar(15) not null comment '订单编号',
user_id int not null comment '关联用户id',
create_time datetime not null default now() comment '订单创建时间',
finish_time datetime not null default now() comment '订单完成时间, 已收货或已退款、已关闭时更新结束时间',
activity_ids varchar(50) comment '关联活动id,逗号分隔',
freight float not null default 0 comment '运费',
total_discount decimal(12,5) not null default 0 comment '折扣', 
total_price decimal(12,5) not null default 0 comment '总价(应付)',
total_pay decimal(12,5) not null comment '实付',
status tinyint not null default 0 comment '订单状态 0 待付款，1 待发货 ，2 待收货 ，3 待退款 ，4 已收货，5 已退款 ，6 已关闭 ,7 逻辑删除'
);



/*商品订单商品清单*/
create table tb_order_item(
order_number varchar(15) not null comment '关联订单编号',
goods_id int not null comment '关联商品',
amount int not null default 0 comment '商品数量',
is_delete tinyint not null default 0 comment '0正常,1逻辑删除'
);












