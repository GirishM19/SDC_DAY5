package Employee;

import java.util.Scanner;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;

public class EmployeeManagement {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Create one MongoClient and use it throughout
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("employeeDB");
            MongoCollection<Document> collection = database.getCollection("employees");

            while (running) {
                System.out.println("\n--------- MENU ----------");
                System.out.println("1. Add employee details");
                System.out.println("2. Update employee details");
                System.out.println("3. delete employee by email");
                System.out.println("4. List employees with pagination");
                System.out.println("5. Department statistics");
                System.out.println("6. EXIT");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        addEmployeeDetails(scanner, collection);
                        break;
                    case "2":
                        updateEmployee(scanner, collection);
                        break;
                    case "3":
                        deleteEmployee(scanner, collection);
                        break;
                    case "4":
                        searchEmployee(scanner, collection);
                        break;

                    case "5":
                        listEmployees(scanner, collection);
                        break;
                    case "6":
                        departmentStatistics(collection);
                        break;
                    case "7":
                        running = false;
                        System.out.println("👋 Exiting...");
                        break;
                    default:
                        System.out.println("❌ Invalid choice.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ MongoDB connection failed.");
        } finally {
            scanner.close();
        }
    }

    private static void addEmployeeDetails(Scanner scanner, MongoCollection<Document> collection) {
        System.out.print("Enter employee name: ");
        String emp_name = scanner.nextLine();

        System.out.print("Enter employee email: ");
        String emp_email = scanner.nextLine();

        System.out.print("Enter employee department: ");
        String emp_dept = scanner.nextLine();

        System.out.print("Enter employee skill: ");
        String emp_skill = scanner.nextLine();

        System.out.print("Enter employee joining date (YYYY-MM-DD): ");
        String emp_join_date = scanner.nextLine();

        Bson emailFilter = Filters.eq("email", emp_email);
        Document existingEmployee = collection.find(emailFilter).first();

        if (existingEmployee != null) {
            System.out.println("❌ Error: An employee with this email already exists.");
        } else {
            Document newEmployee = new Document("name", emp_name)
                    .append("email", emp_email)
                    .append("department", emp_dept)
                    .append("skill", emp_skill)
                    .append("joining_date", emp_join_date);

            collection.insertOne(newEmployee);
            System.out.println("✅ Employee added successfully.");
        }
    }

    private static void updateEmployee(Scanner scanner, MongoCollection<Document> collection) {
        System.out.print("Enter employee email to update: ");
        String emp_email = scanner.nextLine();

        Bson filter = Filters.eq("email", emp_email);
        Document employee = collection.find(filter).first();

        if (employee == null) {
            System.out.println("❌ Employee not found.");
            return;
        }

        System.out.println("Enter new details (leave blank to keep current value):");

        System.out.print("Name [" + employee.getString("name") + "]: ");
        String newName = scanner.nextLine();
        System.out.print("Department [" + employee.getString("department") + "]: ");
        String newDept = scanner.nextLine();
        System.out.print("Skill [" + employee.getString("skill") + "]: ");
        String newSkill = scanner.nextLine();
        System.out.print("Joining Date [" + employee.getString("joining_date") + "]: ");
        String newJoinDate = scanner.nextLine();

        Bson updates = Updates.combine();

        if (!newName.isBlank()) {
            updates = Updates.combine(updates, Updates.set("name", newName));
        }
        if (!newDept.isBlank()) {
            updates = Updates.combine(updates, Updates.set("department", newDept));
        }
        if (!newSkill.isBlank()) {
            updates = Updates.combine(updates, Updates.set("skill", newSkill));
        }
        if (!newJoinDate.isBlank()) {
            updates = Updates.combine(updates, Updates.set("joining_date", newJoinDate));
        }

        if (updates == null) {
            System.out.println("No updates entered.");
            return;
        }

        collection.updateOne(filter, updates);
        System.out.println("✅ Employee updated successfully.");
    }

    private static void deleteEmployee(Scanner scanner, MongoCollection<Document> collection) {
        System.out.print("Enter employee email to delete: ");
        String emp_email = scanner.nextLine();

        Bson filter = Filters.eq("email", emp_email);
        Document employee = collection.find(filter).first();


        System.out.print("Are you sure you want to delete this employee? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            collection.deleteOne(filter);
            System.out.println("✅ Employee deleted successfully.");
        } else {
            System.out.println("❌ Delete operation canceled.");
        }

    }

    private static void searchEmployee(Scanner scanner, MongoCollection<Document> collection) {
        System.out.println("\nSearch Options:");
        System.out.println("1. By Name (partial match)");
        System.out.println("2. By Department");
        System.out.println("3. By Skill");
        System.out.println("4. By Joining Date Range");
        System.out.print("Enter your search option: ");
        String option = scanner.nextLine().trim();

        Bson filter = null;

        switch (option) {
            case "1": // By Name using regex
                System.out.print("Enter part of the name to search: ");
                String namePart = scanner.nextLine();
                filter = Filters.regex("name", namePart, "i"); // case-insensitive
                break;

            case "2": // By Department
                System.out.print("Enter department name: ");
                String department = scanner.nextLine();
                filter = Filters.eq("department", department);
                break;

            case "3": // By Skill
                System.out.print("Enter skill to search for: ");
                String skill = scanner.nextLine();
                filter = Filters.eq("skill", skill);
                break;

            case "4": // By Joining Date Range
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startDate = scanner.nextLine();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endDate = scanner.nextLine();
                filter = Filters.and(
                        Filters.gte("joining_date", startDate),
                        Filters.lte("joining_date", endDate)
                );
                break;

            default:
                System.out.println("❌ Invalid search option.");
                return;
        }

        FindIterable<Document> results = collection.find(filter);

        System.out.println("\n🔍 Search Results:");
        int count = 0;
        for (Document doc : results) {
            System.out.println(doc.toJson());
            count++;
        }

        if (count == 0) {
            System.out.println("No matching employees found.");
        }
    }


    private static void listEmployees(Scanner scanner, MongoCollection<Document> collection) {
        final int pageSize = 5;

        System.out.print("Enter page number (starting from 1): ");
        int pageNumber = Integer.parseInt(scanner.nextLine());

        System.out.println("Sort by:");
        System.out.println("1. Name (ascending)");
        System.out.println("2. Joining Date (ascending)");
        String sortChoice = scanner.nextLine();

        Bson sortField;
        switch (sortChoice) {
            case "1":
                sortField = Sorts.ascending("name");
                break;
            case "2":
                sortField = Sorts.ascending("joining_date");
                break;
            default:
                System.out.println("❌ Invalid sort choice. Defaulting to sorting by name.");
                sortField = Sorts.ascending("name");
        }

        int skip = (pageNumber - 1) * pageSize;

        FindIterable<Document> employees = collection.find()
                .sort(sortField)
                .skip(skip)
                .limit(pageSize);

        System.out.println("\n📄 Page " + pageNumber + " (showing " + pageSize + " employees):");
        int count = 0;
        for (Document emp : employees) {
            System.out.println(emp.toJson());
            count++;
        }

        if (count == 0) {
            System.out.println("No employees found on this page.");
        }
    }

    private static void departmentStatistics(MongoCollection<Document> collection) {
        System.out.println("\n📊 Department Statistics:");

        AggregateIterable<Document> stats = collection.aggregate(
                java.util.Arrays.asList(
                        new Document("$group", new Document("_id", "$department")
                                .append("count", new Document("$sum", 1))),
                        new Document("$sort", new Document("count", -1)) // Optional: sort by count descending
                )
        );

        for (Document stat : stats) {
            String deptName = stat.getString("_id");
            int count = stat.getInteger("count");
            System.out.println("📁 " + deptName + ": " + count + " employee(s)");
        }
    }
}