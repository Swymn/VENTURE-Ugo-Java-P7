# Use the official MySQL image as the base image
FROM mysql:8.0

# Set environment variables
ENV MYSQL_DATABASE=demo
ENV MYSQL_ROOT_PASSWORD=root

# Add your custom SQL script
ADD ../doc/data.sql /docker-entrypoint-initdb.d

# Expose port 3306 to access MySQL
EXPOSE 3306