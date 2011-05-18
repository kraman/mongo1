CREATE DATABASE if not exists msgs;

--  Cleanup any old/existing users.
--  mysql doesn't support drop user if exists, so do a grant and drop.
GRANT USAGE ON msgs.* TO 'mongo1'@'localhost';
DROP USER 'mongo1'@'localhost';

GRANT USAGE ON msgs.* TO 'mongo1'@'%';
DROP USER 'mongo1'@'%';

--  Create ait users and grant all permissions.
CREATE USER 'mongo1'@'localhost' IDENTIFIED BY 'mongo1';
GRANT ALL ON msgs.* TO 'mongo1'@'localhost';

CREATE USER 'mongo1'@'%' IDENTIFIED BY 'mongo1';
GRANT ALL ON msgs.* TO 'mongo1'@'%';

