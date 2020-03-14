# Connect to mysql and run as root user
#Create Databases
CREATE DATABASE osh_dev;
CREATE DATABASE osh_prod;

#Create database service accounts
CREATE USER 'osh_dev_user'@'localhost' IDENTIFIED BY 'gwassy';
CREATE USER 'osh_prod_user'@'localhost' IDENTIFIED BY 'gwassy';
CREATE USER 'osh_dev_user'@'%' IDENTIFIED BY 'gwassy';
CREATE USER 'osh_prod_user'@'%' IDENTIFIED BY 'gwassy';

#Database grants
GRANT SELECT ON osh_dev.* to 'osh_dev_user'@'localhost';
GRANT INSERT ON osh_dev.* to 'osh_dev_user'@'localhost';
GRANT DELETE ON osh_dev.* to 'osh_dev_user'@'localhost';
GRANT UPDATE ON osh_dev.* to 'osh_dev_user'@'localhost';
GRANT SELECT ON osh_prod.* to 'osh_prod_user'@'localhost';
GRANT INSERT ON osh_prod.* to 'osh_prod_user'@'localhost';
GRANT DELETE ON osh_prod.* to 'osh_prod_user'@'localhost';
GRANT UPDATE ON osh_prod.* to 'osh_prod_user'@'localhost';
GRANT SELECT ON osh_dev.* to 'osh_dev_user'@'%';
GRANT INSERT ON osh_dev.* to 'osh_dev_user'@'%';
GRANT DELETE ON osh_dev.* to 'osh_dev_user'@'%';
GRANT UPDATE ON osh_dev.* to 'osh_dev_user'@'%';
GRANT SELECT ON osh_prod.* to 'osh_prod_user'@'%';
GRANT INSERT ON osh_prod.* to 'osh_prod_user'@'%';
GRANT DELETE ON osh_prod.* to 'osh_prod_user'@'%';
GRANT UPDATE ON osh_prod.* to 'osh_prod_user'@'%';