create database trekk character set = 'utf8';
GRANT ALL PRIVILEGES ON trekk.* to 'trekk'@'localhost' identified by 'foo' with grant option;
flush privileges;
