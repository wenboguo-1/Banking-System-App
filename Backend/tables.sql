create table customer
(
  ID		int NOT NULL AUTO_INCREMENT,
  Name		varchar(15) not null,
  Gender	char not null,
  Age		int not null,
  Pin		int not null,
  primary key (ID)
);

create table account
(
  Number	int not null AUTO_INCREMENT ,
  customerId int not null,
  Balance	int not null,
  Type		char not null,
  Status	char not null,
  FOREIGN KEY (customerId) REFERENCES customer(id),
  primary key (Number)
);

CREATE TABLE Open_History(
   
    customerId int NOT NULL,
    accountId int NOT NULL,
    date DATETIME NOT NULL,
     
    FOREIGN KEY (customerId) REFERENCES customer(id),
	FOREIGN KEY (accountId) REFERENCES account(accountId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Close_History(
    customerId int NOT NULL,
    accountId int NOT NULL,
    date DATETIME NOT NULL,
     
    FOREIGN KEY (customerId) REFERENCES customer(id) ,
	FOREIGN KEY (accountId) REFERENCES account(accountId) ON UPDATE CASCADE ON DELETE CASCADE 
);

CREATE TABLE Deposit_History(
    customerId int NOT NULL,
    accountId int NOT NULL,
    amount int NOT NULL,
    date DATETIME NOT NULL,
     
    FOREIGN KEY (customerId) REFERENCES customer(id) ,
	FOREIGN KEY (accountId) REFERENCES account(accountId) ON UPDATE CASCADE ON DELETE CASCADE 
);

CREATE TABLE Withdraw_History(
    customerId int NOT NULL,
    accountId int NOT NULL,
    amount int NOT NULL,
    date DATETIME NOT NULL,
     
    FOREIGN KEY (customerId) REFERENCES customer(id),
	FOREIGN KEY (accountId) REFERENCES account(accountId) ON UPDATE CASCADE ON DELETE CASCADE 
);

CREATE TABLE Transfer_History(
    customerId int NOT NULL,
    accountSrcId int NOT NULL,
    accountDesId int NOT NULL,
    amount int NOT NULL,
    date DATETIME NOT NULL,
     
    FOREIGN KEY (customerId) REFERENCES customer(id),
	FOREIGN KEY (accountSrcId) REFERENCES account(accountId) ON UPDATE CASCADE ON DELETE CASCADE ,
);
