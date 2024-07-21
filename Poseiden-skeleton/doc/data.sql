CREATE TABLE bid_list (
  BidListId TINYINT(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bidQuantity DOUBLE,
  askQuantity DOUBLE,
  bid DOUBLE,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bidListDate TIMESTAMP(6),
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP(6),
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP(6),
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (BidListId)
);

CREATE TABLE trade (
  TradeId TINYINT(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buyQuantity DOUBLE,
  sellQuantity DOUBLE,
  buyPrice DOUBLE,
  sellPrice DOUBLE,
  tradeDate TIMESTAMP(6),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP(6),
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP(6),
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (TradeId)
);

CREATE TABLE curve_point (
  Id TINYINT(4) NOT NULL AUTO_INCREMENT,
  CurveId TINYINT,
  asOfDate TIMESTAMP(6),
  term DOUBLE,
  value DOUBLE,
  creationDate TIMESTAMP(6),
  PRIMARY KEY (Id)
);

CREATE TABLE rating (
  Id TINYINT(4) NOT NULL AUTO_INCREMENT,
  moodysRating VARCHAR(125),
  sandPRating VARCHAR(125),
  fitchRating VARCHAR(125),
  orderNumber TINYINT,
  PRIMARY KEY (Id)
);

CREATE TABLE rule_name (
  Id TINYINT(4) NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sqlStr VARCHAR(125),
  sqlPart VARCHAR(125),
  PRIMARY KEY (Id)
);

CREATE TABLE user (
  Id TINYINT(4) NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),
  PRIMARY KEY (Id)
);

INSERT INTO user(fullname, username, password, role) VALUES
("Administrator", "admin", "$2a$10$Xbfm2PdeKS6LbfdpTC0SieGY2VDp6PD.HPHNPZ35BGHKqN0bcFYxi", "ADMIN"),
("User", "user", "$2a$10$Xbfm2PdeKS6LbfdpTC0SieGY2VDp6PD.HPHNPZ35BGHKqN0bcFYxi", "USER");