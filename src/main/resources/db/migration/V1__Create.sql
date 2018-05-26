create table Product(
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

create table `Order`
(
	id int unsigned auto_increment primary key,
	userId int unsigned not null,
``	totalPrice double not null,
	modifiedTime datetime not null,
	createTime datetime not null,
	status tinyint unsigned not null,
	constraint order_id_uindex unique (id)
)engine=InnoDB DEFAULT CHARSET = utf8;

create table OrderInfo
(
	id int unsigned auto_increment primary key,
	productId int unsigned not null,
	orderId int unsigned not null,
	purchaseCount smallint(5) unsigned not null,
	modifiedTime datetime not null,
	createTime datetime not null,
	status tinyint unsigned not null,
	constraint order_info_id_uindex unique (id)
)engine=InnoDB DEFAULT CHARSET = utf8;

create table LogisticsRecords
(
	id int unsigned auto_increment primary key,
	deliveryMan varchar(16) null,
	outboundTime datetime null,
	signedTime datetime null,
	modifiedTime datetime not null,
	createTime datetime not null,
	logisticsStatus tinyint unsigned not null,
	constraint order_info_id_uindex unique (id)
)engine=InnoDB DEFAULT CHARSET = utf8;