   **Librarian Task**
   ____
    
    The main goal of the task is to sort out all the students and books by their data. Apart from that, by searching student
    program should also return all books the student has borrowed. Same function is applicable for books as well.
    Searching within the program is flexible.

    
   **Run the program**
   
   ____
    
    Firstly, we have to get the project from the master branch. As a result, a new database called testdb is created. 
    The username is "user" and the password is "user". The project is running on the port 8080. 
        
    I also added Swagger UI and you can check the work of all APIs via url http://localhost:8080/swagger-ui.html.
    
   ****REST API description****
   
   ____
   
   
   **Creating Book : [host:port]/books/create**
    
    POST - REST request to save a new book. The api receives BooksDTO as a request body. 
    Then this DTO is converted into an entity via BidirectionalMapper and then is saved by book repository. 
    It is not mandatory to write an id, because the database has an autoincrement functionality.
    
   **Creating new Student : [host:port]/students/create**
    
    POST - REST request to save a new student. The api receives StudentDTO as a request body. 
    Then this DTO is converted into an entity via BidirectionalMapper and then is saved by book repository. 
    It is not mandatory to write an id, because the database has an autoincrement functionality.
    
  
   **Getting Book by id : [host:port]/books/{id}**
         
    GET - REST request to get a book by id. 
    The api returns BookDTO if the book is found, otherwise IllegalArgumentException is thrown.
         
    For example : [localhost:8080]/books/1
      
   **Getting Student by id : [host:port]/students/{id}**
          
    GET - REST request to get a student by id. 
    The api returns StudentDTO if the student is found, otherwise IllegalArgumentException is thrown.
          
    For example : [localhost:8080]/students/1
           
    
   **Getting all Books : [host:port]/books**
    
    GET - REST request to get all books.
    
    For example : [localhost:8080]/books
     
   **Getting all Students : [host:port]/students**
        
    GET - REST request to get all students.
        
    For example : [localhost:8080]/students
         
     
   **Getting all Students by criteria : [host:port]/students/search**
    
    POST - REST request to get all students by the criteria. The api receives StudentDTO as a RequestBody. 
    For searching by criteria I have used Specification interface, 
    which receives as a parameter Predicate interface with three parameters : Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder.
    For a flexible search I have used criteriaBuilder.like() method and added literal '%' character to end of the criteria. 
        
    Also, the main goal is to get all books that the student has borrowed by searching his or her data. The solution 
    I have found in creating StudentSearchDTO where StudentDTO and the list of BooksDTOs are passed as a parameter in constructor.
    As a result, the method returns list of students that are converted to the list of StudentSearchDTO objects via Mapper.
        
    For example : [localhost:8080]/students/search
    
   **Getting all Books by criteria : [host:port]/books/search**
     
    POST - REST request to get all books by the criteria. The api receives BookDTO as a RequestBody. 
    For searching by criteria i have used Specification interface, 
    which receives as a parameter Predicate interface with three parameters : Root<Student> root, CriteriaQuery<?> criteriaQuery CriteriaBuilder criteriaBuilder. 
    For a flexible search I have used criteriaBuilder.like() method and added literal '%' character to end of the criteria. 
     
    Also, the main goal is to get all students that have borrowed the book. The solution I have found in creating
    BookSearchDTO where BookDTO and list of StudentDTOs are passed as a parameter in constructor. 
    As a result, the method returns list of books that are converted to the list of BookSearchDTO objects via Mapper.
     
    For example : **[localhost:8080]/books/search
  
   **Deleting Student by id : [host:port]/students/{id}**
   
    DELETE - REST request to delete a student by id.
   
    For example : [localhost:8080]/students/1
   
   **Deleting Book by id : [host:port]/books/{id}**
    
    DELETE - REST request to delete a book by id.
      
    For example : [localhost:8080]/books/1
    
    
   **Updating Student : [host:port]/students**
   
    PUT - REST request to update a student. The api receives StudentDTO as a RequestBody.
    
    For example : [localhost:8080]/students
    
   **Updating Book : [host:port]/books**
    
    PUT - REST request to update a book. The api receives BookDTO as a RequestBody.
    
    For example : [localhost:8080]/books
    
   **Adding Book to the Student's borrowed books list : [host:port]/students/{studentId}/book/{bookId}**
    
    POST - REST request to add book to the student's bookList. The api receives studentId and bookId as a PathVariable.
    
    For example : [localhost:8080]/students/1/book/1
    
   **Deleting Book in the Student's borrowed books list : [host:port]/students/{studentId}/book/{bookId}**
        
    DELETE - REST request to delete book in the student's bookList. The api receives studentId and bookId as a PathVariable.
        
    For example : [localhost:8080]/students/1/book/1