docker run -v /Users/akoira/_data/furman/mysql:/var/lib/mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=whatsup -cap-add=sys_nice mysql:8.0.19

docker exec -i 70925ead0fa9 mysql -pwhatsup < ~/Downloads/_furman/Dump20200812.sql
