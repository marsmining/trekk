create database webtrekk character set = 'utf8';
GRANT ALL PRIVILEGES ON webtrekk.* to 'webtrekk'@'localhost' identified by 'foo' with grant option;
flush privileges;
