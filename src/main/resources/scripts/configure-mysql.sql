# Connect to mysql and run as root user
#Create Databases
CREATE DATABASE osh_dev;

#Create database service accounts
CREATE USER 'osh_dev_user'@'localhost' IDENTIFIED BY 'gwassy';
CREATE USER 'osh_dev_user'@'%' IDENTIFIED BY 'gwassy';

#Database grants
GRANT SELECT ON osh_dev.* to 'osh_dev_user'@'localhost';
GRANT INSERT ON osh_dev.* to 'osh_dev_user'@'localhost';
GRANT DELETE ON osh_dev.* to 'osh_dev_user'@'localhost';
GRANT UPDATE ON osh_dev.* to 'osh_dev_user'@'localhost';
GRANT SELECT ON osh_dev.* to 'osh_dev_user'@'%';
GRANT INSERT ON osh_dev.* to 'osh_dev_user'@'%';
GRANT DELETE ON osh_dev.* to 'osh_dev_user'@'%';
GRANT UPDATE ON osh_dev.* to 'osh_dev_user'@'%';
