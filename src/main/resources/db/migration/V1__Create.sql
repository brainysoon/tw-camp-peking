create table product(
	id int unsigned auto_increment primary key,
	name varchar(64) not null,
	description varchar(264) not null,
	price double not null,
	count int unsigned default '0' not null,
	modifiedTime datetime not null,
	createTime datetime not null,
	status tinyint unsigned not null,
	constraint product_id_uindex unique (id)
)engine=InnoDB DEFAULT CHARSET = utf8;

create table `order`
(
	id int unsigned auto_increment primary key,
	userId int unsigned not null,
	total double not null,
	modifiedTime datetime not null,
	createTime datetime not null,
	status tinyint unsigned not null,
	constraint order_id_uindex unique (id)
)engine=InnoDB DEFAULT CHARSET = utf8;

create table order_info
(
	id int unsigned auto_increment primary key,
	productId int unsigned not null,
	purchaseCount smallint(5) unsigned not null,
	modifiedTime datetime not null,
	createTime datetime not null,
	status tinyint unsigned not null,
	constraint order_info_id_uindex unique (id)
)engine=InnoDB DEFAULT CHARSET = utf8;

create table logistics_records
(
	id int unsigned auto_increment primary key,
	deliveryMan varchar(16) not null,
	outboundTime datetime null,
	signedTime datetime null,
	modifiedTime datetime not null,
	createTime datetime not null,
	logisticsStatus tinyint unsigned not null,
	constraint order_info_id_uindex unique (id)
)engine=InnoDB DEFAULT CHARSET = utf8;

