DROP TABLE IF EXISTS TBL_LOANS;
 
CREATE TABLE TBL_LOANS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  amount DOUBLE NOT NULL,
  interest DOUBLE DEFAULT NULL,
  years INTEGER NOT NULL,
  installment DOUBLE DEFAULT NULL

);
