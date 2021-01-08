drop database if exists eshop;
create database eshop;
use eshop;



/*�û���*/
create table tb_user(
user_id int primary key auto_increment comment '�û�������',
user_name varchar(25) not null unique comment '�û���',
reg_time datetime not null default now() comment 'ע��ʱ��',
avatar_url varchar(50) not null default 'http://localhost:8080/static/imgs/aval/default.png' comment 'ͷ��url',
pwd varchar(64) not null comment '���ܺ������',
status tinyint not null default 0 comment '״̬�룬0������1�쳣��2 �߼�ɾ��'
)auto_increment =999;

#�����˻�
insert into tb_user (user_name,pwd) values('test','123456');



/*���͵�ַ�б�*/
create table tb_address(
address_id int primary key auto_increment comment '��ַId',
user_id int not null comment '�����û�id',
address varchar(80) not null comment '���͵�ַ',
tag varchar(10) comment '��ǩ',
status tinyint not null default 0  comment '��ַ״̬��0 ��ͨ��1 Ĭ�ϵ�ַ��2 �߼�ɾ��'
);



/*��Ʒ���ͱ�*/
create table tb_goods_type(
type_id int primary key auto_increment comment '��Ʒ����id',
type_code varchar (11) not null comment '��Ʒ����code',
type_name varchar(25) not null comment '��Ʒ��������',
type_desc varchar(255) comment '��Ʒ��������',
parent_type_code varchar (11) not null default '0' comment '��������code',
is_delete tinyint default 0 comment '�߼�ɾ����־λ��0 ������1 �߼�ɾ��'
);


/*��Ʒ��*/
create table tb_goods(
goods_id int primary key auto_increment comment '��Ʒid',
goods_name varchar(25) not null comment '��Ʒ����',
goods_desc varchar(255) comment '��Ʒ����',
goods_type_id int not null default 0 comment '��Ʒ����id���ݲ��������ڶ������͵����',
goods_stock int not null default 0 comment '���',
goods_sold int not null default 0 comment '����',
goods_price decimal(12,4) not null default 0 comment '����',
status tinyint default 0 comment '״̬��־λ��0 ���ϼܣ�1 ���ϼ�  2 ���¼� ��3 �߼�ɾ��'
);

/*��ƷͼƬ��*/
create table tb_goods_img(
img_id int primary key auto_increment comment 'ͼƬid',
goods_id int not null comment '��Ʒid',
img_url varchar(255) not null comment '��Ʒurl',
is_delete tinyint not null default 0 comment '�߼�ɾ����־λ,0 ���� ��1 ��ɾ��'
);



/*��Ʒ���*/
create table tb_activity(
activity_id int primary key auto_increment comment '�id',
activity_name varchar(25) not null comment '�����',
activity_desc varchar(255) comment '�����',
activity_poster_url varchar(50) comment '�����url',
start_time datetime not null default now() comment '���ʼʱ��',
end_time datetime not null default now() comment '�����ʱ��',
status tinyint not null default 0 comment '�״̬ 0 ����ʼ ��1 �����У�2 �ѽ�����3 �߼�ɾ�� '
);


/*������*/
create table tb_activity_rule(
rule_id int not null primary key comment '����id',
rule_type tinyint not null default 0 comment '0,���ۣ�1������2����',
rule_limit float not null default 0 comment '��������,0 ��ʾ������',
rule_val float not null comment '����ֵ������Ϊ�������ӣ�����Ϊ����ֵ�����ͣ�Ϊ������Ʒid',
relation_type tinyint not null default 0 comment '��Ʒ�������ͣ�0 �ض���Ʒ�����ڹ�������,1 ������Ʒ����',
activity_id int not null comment '�id'
);

/*�������������*/
create table tb_rule_goods(
rule_goods_id int primary key auto_increment comment '����id',
rule_id int not null comment '����id',
goods_id int not null comment '�������Ʒid'
);


/*���ﳵ*/
create table tb_shop_cart(
cart_id int primary key auto_increment comment '����',
user_id int not null comment '�����û�id',
goods_id int not null comment '������Ʒ',
amount int not null default 0 comment '��Ʒ����'
); 



/*��Ʒ������*/
create table tb_order(
order_number varchar(15) not null comment '�������',
user_id int not null comment '�����û�id',
create_time datetime not null default now() comment '��������ʱ��',
finish_time datetime not null default now() comment '�������ʱ��, ���ջ������˿�ѹر�ʱ���½���ʱ��',
activity_ids varchar(50) comment '�����id,���ŷָ�',
goods_img_url varchar(255) not null comment '��������ͼ',
total_freight decimal(12,5) not null default 0 comment '�˷�',
total_discount decimal(12,5) not null default 0 comment '�ۿ�', 
total_price decimal(12,5) not null default 0 comment '�ܼ�(Ӧ��)',
total_pay decimal(12,5) not null comment 'ʵ��',
status tinyint not null default 0 comment '����״̬ 0 �����1 ������ ��2 ���ջ� ��3 ���˿� ��4 ���ջ���5 ���˿� ��6 �ѹر� ,7 �߼�ɾ��'
);



/*��Ʒ������Ʒ�嵥*/
create table tb_order_item(
order_number varchar(15) not null comment '�����������',
goods_id int not null comment '������Ʒ',
address_id int not null comment '���͵�ַ',
freight decimal(12,5) not null default 0 comment '�˷�',
amount int not null default 0 comment '��Ʒ����',
is_delete tinyint not null default 0 comment '0����,1�߼�ɾ��'
);












