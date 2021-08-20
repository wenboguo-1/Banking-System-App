
CREATE DEFINER=`root`@`localhost` PROCEDURE `CUST_CRT`(IN p_name CHAR(15), IN p_gender CHAR(1), IN p_age INTEGER, IN p_pin INTEGER, OUT id INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
BEGIN
    IF p_gender != 'M' AND p_gender != 'F' THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid gender';
    ELSEIF p_age <= 0 THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid age';
    ELSEIF p_pin < 0 THEN
      SET sql_code = -100;
      SET err_msg = 'Invalid pin';
    ELSE
      INSERT INTO USERINFO.customer(p_name,p_gender,p_age,p_pin) VALUES(p_name,p_gender,p_age, p_pin);
      SET err_msg = '-';
      SET sql_code = 0;
      select max(USERINFO.customer.id) into id from USERINFO.customer;
	END IF;
END


CREATE DEFINER=`root`@`localhost` PROCEDURE `CUST_LOGIN`(IN p_id INTEGER, IN p_pin INTEGER, OUT VALID INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
BEGIN
  DECLARE pin INTEGER;  
   select customer.p_pin into pin from USERINFO.customer where USERINFO.customer.id = p_id;
   
   IF pin IS NULL OR pin != p_pin THEN
     SET err_msg = 'Pin or id is not correct';
     SET sql_code = -100;
     SET VALID = 0;
   ElSE
     SET sql_code = 0;
     SET VALID =  123;
     SET err_msg = 'Pin or id is correct';
    END IF;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `PRIVATE_ID`(IN p_id INTEGER, IN accountId INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
BEGIN
  IF (SELECT USERINFO.account.id FROM USERINFO.account WHERE USERINFO.account.id = p_id AND USERINFO.account.accountId = accountId ) IS NULL THEN 
           
           SET err_msg = 'The account you entered is either not correct or does not belong to your account ID';
           SET sql_code = -100;
        ELSE 
           SET sql_code = 0;

        END IF;

END

CREATE DEFINER=`root`@`localhost` PROCEDURE `USER_INFO`(IN user_id INT)
BEGIN
  
   SELECT * from USERINFO.customer where user_id = USERINFO.customer.id;

END

CREATE DEFINER=`root`@`localhost` PROCEDURE `ACCT_OPN`(IN p_id INTEGER, IN p_amount INTEGER, IN p_type CHAR(1), OUT accountId INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
BEGIN
     
    IF p_amount < 0 THEN
     SET err_msg = 'Invalid amount';
     SET sql_code = -100;
    ELSEIF p_type != 'C' AND p_type != 'S' THEN
     SET err_msg = 'Invalid type';
     SET sql_code = -100;
    ELSEIF (select USERINFO.customer.id from USERINFO.customer where USERINFO.customer.id = p_id) is NULL THEN
      SET err_msg = 'ID is not correct';
      SET sql_code = -100;
    ELSE 
       SET sql_code = 0;
       INSERT INTO USERINFO.account(id,balance,type,status) VALUES (p_id,p_amount,p_type,'A');
       select max(USERINFO.account.accountId) into accountId from USERINFO.account;
  END IF;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `ACCT_CLS`(IN p_number INTEGER,IN cus_id INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
BEGIN
 IF (SELECT USERINFO.account.accountId FROM USERINFO.account WHERE USERINFO.account.accountId= p_number AND USERINFO.account.id = cus_id) IS NULL THEN
           SET sql_code = -100;
           SET err_msg = 'Ivalid account number or this account number does not belong to this customer id';
        ELSE 
           UPDATE USERINFO.account SET balance = 0,status = 'I' WHERE USERINFO.account.accountId = p_number;
           SET sql_code = 0;

        END IF;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `ACCT_DEP`(IN p_number INTEGER, IN p_amount INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
BEGIN
   
   
        IF (SELECT USERINFO.account.accountId FROM USERINFO.account WHERE USERINFO.account.accountId = p_number AND USERINFO.account.status != 'I') IS NULL THEN
            SET err_msg = 'Invalid account number';
            SET sql_code = -100;
        ELSEIF p_amount < 0 THEN
          SET err_msg = 'Invalid amount, must be greater than 0';
          SET sql_code = -100;

        ElSE
          UPDATE USERINFO.account SET USERINFO.account.balance = USERINFO.account.balance + p_amount WHERE USERINFO.account.accountId = p_number;
          SET sql_code = 0;

        END IF;

END

CREATE DEFINER=`root`@`localhost` PROCEDURE `ACCT_WTH`(IN p_number INTEGER, IN p_amount INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
BEGIN
 DECLARE balance INTEGER;
           IF p_amount < 0 THEN 
              SET sql_code = -100;
              SET err_msg = 'Invalid amount: Must be greate than 0';
          ELSEIF (SELECT USERINFO.account.accountId FROM USERINFO.account WHERE USERINFO.account.accountId = p_number AND USERINFO.account.status != 'I'   ) IS NULL THEN 
              SET sql_code = -100;
              SET err_msg = 'The account number your entered is not correct or was closed';
		
          ELSE 
              SELECT USERINFO.account.balance into balance FROM USERINFO.account WHERE USERINFO.account.accountId = p_number;
              IF ( balance - p_amount) < 0 THEN 
                  SET sql_code = -100;
                  SET err_msg = 'The balance in this account is not enough for this transiction';
              ELSE 
                UPDATE USERINFO.account SET USERINFO.account.balance = USERINFO.account.balance - p_amount WHERE USERINFO.account.accountId = p_number;
                SET sql_code = 0;

              END IF;
          END IF;

END

CREATE DEFINER=`root`@`localhost` PROCEDURE `ACCT_TRX`(IN from_number INTEGER, IN to_number INTEGER, IN p_amount INTEGER, OUT sql_code INTEGER, OUT err_msg CHAR(100))
BEGIN
    CALL USERINFO.ACCT_WTH(from_number,p_amount,sql_code,err_msg);
		IF(sql_code != -100) THEN 
	CALL USERINFO.ACCT_DEP(to_number,p_amount,sql_code,err_msg);
	 END IF;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `CUSTOMER_ACCTS`(IN id INT)
BEGIN
   select * from USERINFO.account WHERE id = USERINFO.account.id;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `USER_INFO`(IN user_id INT)
BEGIN
  
   SELECT * from USERINFO.customer where user_id = USERINFO.customer.id;

END

CREATE DEFINER=`root`@`localhost` PROCEDURE `NEW_CLOSED_HIS`(IN customerId INTEGER,IN accountId INTEGER,IN dates DATETIME )
BEGIN
     INSERT INTO Close_History (USERINFO.Close_History.customerId, USERINFO.Close_History.accountId, 
      USERINFO.Close_History.date ) values (customerId,accountId,dates);
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `NEW_DEPOSIT_HISTORY`(IN customerId INTEGER, IN accountId INTEGER, IN amount INTEGER,IN dates DATETIME)
BEGIN

     INSERT INTO USERINFO.Deposit_History( USERINFO.Deposit_History.customerId,USERINFO.Deposit_History.accountId,
                                           USERINFO.Deposit_History.amount,USERINFO.Deposit_History.date) VALUES(customerId,accountId,amount,dates);
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `NEW_TRANSFER_HISTORY`(IN customerId INTEGER, IN accountSrcId INTEGER, IN accountDesId INTEGER, IN amount INTEGER, IN date DATETIME )
BEGIN
    INSERT INTO USERINFO.Transfer_History(Transfer_History.customerId,Transfer_History.accountSrcId,Transfer_History.accountDesId,Transfer_History.amount,Transfer_History.date) 
    VALUES(customerId,accountSrcId,accountDesId,amount,date);
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `NEW_TRANSFER_HISTORY`(IN customerId INTEGER, IN accountSrcId INTEGER, IN accountDesId INTEGER, IN amount INTEGER, IN date DATETIME )
BEGIN
    INSERT INTO USERINFO.Transfer_History(Transfer_History.customerId,Transfer_History.accountSrcId,Transfer_History.accountDesId,Transfer_History.amount,Transfer_History.date) 
    VALUES(customerId,accountSrcId,accountDesId,amount,date);
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `NEW_WITHDRAW_HISTORY`(IN customerId INTEGER, IN accountId INTEGER, IN amount INTEGER,IN dates DATETIME)
BEGIN
 INSERT INTO USERINFO.Withdraw_History( USERINFO.Withdraw_History.customerId,USERINFO.Withdraw_History.accountId,
                                           USERINFO.Withdraw_History.amount,USERINFO.Withdraw_History.date) VALUES(customerId,accountId,amount,dates);
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_CLOSED_HIS`(IN customerId INTEGER)
BEGIN
  SELECT * from USERINFO.Close_History WHERE USERINFO.Close_History.customerId = customerId;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_DEPOSIT_HISTORY`(IN customerId INTEGER)
BEGIN
   
    SELECT * FROM Deposit_History WHERE Deposit_History.customerId = customerId;

END

CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_OPEN_HIS`(IN customerId INTEGER)
BEGIN
  SELECT * from USERINFO.Open_History WHERE USERINFO.Open_History.customerId = customerId;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_TRANSFER_HISTORY`(IN customerId INTEGER)
BEGIN
   SELECT * FROM USERINFO.Transfer_History WHERE Transfer_History.customerId = customerId;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_WITHDRAW_HISTORY`(IN customerId INTEGER)
BEGIN
   
    SELECT * FROM Withdraw_History WHERE Withdraw_History.customerId = customerId;

END