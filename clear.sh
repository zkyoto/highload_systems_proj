docker-compose -f docker-compose.yml down
docker rmi -f $(docker images -aq)
docker rm -vf $(docker ps -aq)
