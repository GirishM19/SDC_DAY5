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







![Screenshot 2025-06-20 143437](https://github.com/user-attachments/assets/bb2fc18e-c68c-4763-962a-911900a38557)

![Screenshot 2025-06-20 144253](https://github.com/user-attachments/assets/4332b35f-a19d-4c01-9d1e-91cfa9355ee3)

![Screenshot 2025-06-20 144436](https://github.com/user-attachments/assets/0604d0d7-a42b-4ca7-9137-8c142dad9a09)

![Screenshot 2025-06-20 144458](https://github.com/user-attachments/assets/c2ebc81f-bf16-4593-a38d-7cb1c9977d18)

![Screenshot 2025-06-20 144515](https://github.com/user-attachments/assets/02df7a48-7ea0-4a29-8d6a-018055ace41b)

![Screenshot 2025-06-20 144530](https://github.com/user-attachments/assets/3ddbcb11-341b-42f2-9f26-ebce39c20b23)
