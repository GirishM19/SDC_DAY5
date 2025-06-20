DAY 5 TASKS:

Objective:
Build an Employee Management Portal using Java and MongoDB that provides
functionality to:
● Add employee records
● Update and delete employee records
● Filter and search employees by various fields
● Perform aggregation-based queries (e.g., count per department)
Core Tasks
1. Add Employee
● Insert a new document into the employees collection.
● Validate that the employee’s email is unique before insertion.
2. Update Employee
● Update specific fields of an employee document (e.g., skills, department).
● Ensure that only the specified fields are updated and the rest of the document
remains unchanged.
3. Delete Employee
● Delete an employee document using either:
● Email, or
● Employee ID (_id field in MongoDB)
4. Search Employees
Provide filtering and searching capabilities based on:
● By Name: Use regex for partial match (e.g., find names containing “john”).
● By Department: Filter employees belonging to a specific department.
● By Skill: Match if any skill in the skill array matches the input.
● By Joining Date Range: Filter employees who joined between specified dates
(e.g., all who joined in the year 2023)
5.List with Pagination
● Paginate the results to return a fixed number of employees per page (e.g., 5
employees per page).
● Allow sorting by:
○ Name
○ Joining Date
6. Department Statistics
● Perform an aggregation query to group employees by department.
● Count the number of employees in each department.

-------------------------------SCREENSHOTS-----------------------------------------


![77](https://github.com/user-attachments/assets/7f0e7714-7935-4e1c-9da6-313ec0e86ca9)

![11](https://github.com/user-attachments/assets/40c7b358-33e2-4573-a1a5-86c2f851d59d)

![22](https://github.com/user-attachments/assets/3d096b0c-b044-4625-a53a-225b51e9c68d)

![33](https://github.com/user-attachments/assets/135e7074-2f36-43db-b3dd-3f31398580ea)

![55](https://github.com/user-attachments/assets/d634f9c1-f2f9-451b-8e47-e54bb24237d0)

![66](https://github.com/user-attachments/assets/4ce3efd8-6528-46a4-a2ff-0efdf62ecde5)

