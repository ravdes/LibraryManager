# Library Manager

Library Manager is a simple GUI program to manage your books. It allows you to perform crud operations
on MySQL database which is being run on a Docker container.

# Tools and technologies used in the project

*Hibernate, MySQL, Docker, JavaFX, Junit5, Mockito, Git*



<a href="https://hibernate.org/">
<img src="https://kosiorowski.net/wp-content/uploads/2013/11/hibernate1.png" alt="MySQL" width="45" height="45"/>
</a>
<a href="https://www.mysql.com/">
<img src="https://cdn4.iconfinder.com/data/icons/logos-3/181/MySQL-512.png" alt="Docker" width="50" height="50"/>
</a>
<a href="https://www.docker.com/">
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original.svg" alt="MySQL" width="50" height="50"/>
</a>
<a href="https://www.java.com/en/javafx/">
<img src="https://www.qfs.de/fileadmin/Webdata/logos-icons/JavaFX.png" alt="MySQL" width="50" height="50"/>
</a>
<a href="https://junit.org/">

<img src="https://junit.org/junit5/assets/img/junit5-logo.png" alt="MySQL" width="45" height="45"/>
</a>
<a href="https://site.mockito.org/">

<img src="https://static.javatpoint.com/tutorial/mockito/images/mockito.png" alt="MySQL" width="45" height="45"/>
</a>
<a href="https://git-scm.com/">
<img src="https://git-scm.com/images/logos/downloads/Git-Icon-1788C.png" alt="MySQL" width="50" height="50"/>
</a>

# Prerequisites

1. Java 21
2. Docker Desktop - [download](https://www.docker.com/products/docker-desktop/)

# Utilities


![github image](https://github.com/ravdes/LibraryManager/assets/107648518/14cfae4a-0270-4221-9604-5004c5b45822)

# Search

This field allows you to query through the table to find the book. It supports querying through Id, Title, Author,
Genre,
Pages.

# Buttons

### Clear

This button removes text from fields: Title, Author, Genre Pages, and unclicks selected book.

### New

This button deletes the current table and creates a new one by changing ***hibernate.hbm2ddl.auto property***.

### Insert

This button inserts a new book into the table with specified parameters.

### Update

This button updates the selected book in the table with specified parameters.

### Delete

This button deletes selected books from the table.

# How to run?

1. Clone this repository

```bash
git clone https://github.com/ravdes/LibraryManager.git
```

2. Set variables in .env file

```env
CONTAINER_NAME=mysql 
MYSQL_ROOT_PASSWORD=root 
MYSQL_DB_NAME=librarydb 
MYSQL_PORT = 3307
```

3. Open Docker Desktop on your computer.


4. Run in your terminal

````yaml
docker-compose up
````

5. Run the **App.java** file in your IDE it's located in librarymanagement folder.
