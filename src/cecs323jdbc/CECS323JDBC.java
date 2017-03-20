
package cecs323javaprj;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Charles Gioffredi 012045603
 */
public class CECS323JDBC
{
//  Database credentials
    static String USER = "C";
    static String PASS = "C";
    static String DBNAME = "323";
    static  String displayFormat="%-30s%-30s%-30s%-30s%-30s\n";
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
    static Connection conn;
    static Statement stmt;
    static Scanner in;
    static PreparedStatement pstmt;
    //sql commands
    static String listGroups ="SELECT groupName FROM WritingGroups";
    static String listPublishers ="SELECT publisherName FROM Publishers";
    static String listBooks = "SELECT bookTitle FROM Books";
    static String listSpecifiedGroup = "SELECT * FROM WritingGroups WHERE groupName = ?";
    static String listSpecifiedPub = "SELECT * FROM Publishers WHERE publisherName = ?";
    static String listSpecifiedBook = "SELECT * FROM Books WHERE bookTitle = ?";
    static String insertNewBook = "INSERT INTO Books (bookTitle, yearPublished, numberPages,"
            + " publisherName, groupName) VALUES (?,?,?,?,?)";
    static String insertNewPub = "INSERT INTO Publishers (publisherName, publisherAddress,"
            + "publisherPhone, publisherEmail) VALUES (?,?,?,?)";
    static String updatePub = "UPDATE Books SET publisherName = ? WHERE publisherName = ?";
    static String deleteOldPub = "DELETE FROM Publishers WHERE publisherName = ?";
    static String deleteBook = "DELETE FROM Books WHERE bookTitle = ?";
    static String booksPub = "SELECT * FROM Books WHERE Publishers = ?";
        
    public static String dispNull (String input) 
    {
        
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    public static void main(String[] args) 
    {
        in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        conn = null; 
        stmt = null;  
        try 
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            boolean exit = false;
          
            while (!exit) 
            {
                menu();
                int choice = 10; 
                try 
                {
                    choice = in.nextInt();
                } 
                catch (InputMismatchException ime) 
                {
                    System.out.println("Incorrect Input. Please enter a value from 0-9");
                    in.nextLine();
                    menu();
                }
                in.nextLine();
                switch(choice)
                {
                    case 1: listGroups();
                        break;
                    case 2: listSpecifiedGroup();
                        break;
                    case 3:listPublishers();
                        break;
                    case 4: listSpecifiedPub();
                        break;
                    case 5:listBooks();
                        break;
                    case 6:listSpecifiedBook();
                        break;
                    case 7:insertNewBook();
                        break;
                    case 8:insertNewPub();
                        break;
                    case 9:deleteBook();
                        break;
                    case 0: exit = true;
                        break;
                    default:
                        System.out.println("Incorrect Input. Please enter a value from 0-9"); 
                        break;
                }
            }
        
        } 
        catch (SQLException se) 
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } 
        finally 
        {
            //finally block used to close resources
            try 
            {
                if (stmt != null) 
                {
                    stmt.close();
                }
            } 
            catch (SQLException se2) 
            {
            }
            try 
            {
                if (conn != null) 
                {
                    conn.close();
                }
            } 
            catch (SQLException se) 
            {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
   //menu method
    public static void menu()
    {
        System.out.println("--------------------------------------------------");
        System.out.println("Menu:");
        System.out.println("(1) List all the groups.");
        System.out.println("(2) List group data specified by user.");
        System.out.println("(3) List all the publishers.");
        System.out.println("(4) List all publisher data specified by user.");
        System.out.println("(5) List all books.");
        System.out.println("(6) List all book data specified by user.");
        System.out.println("(7) Insert a new book.");
        System.out.println("(8) Insert a new publisher and update all books published by one publisher to be published by the new publisher.");
        System.out.println("(9) Remove specified book, by the user.");
        System.out.println("(0) Exit.");
        System.out.print("Select a number to begin: ");
    }
    public static void listGroups()
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(listGroups);
            System.out.println("Group Name: ");
            while (rs.next()) 
            {
                String name;
                name = rs.getString("groupName");
                System.out.println(name);
            }
            rs.close();   
        }
        catch(SQLException e )
        {
             e.printStackTrace();
        }
   
    }
    public static void listPublishers()
    {
        try{
            stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(listPublishers);
            System.out.println("Pubishers: ");
            while(rs.next())
            {
                String name;
                name = rs.getString("publisherName");
                System.out.println(name);
            }
            rs.close();
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    public static void listBooks()
    {
        try{
            stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(listBooks);
            System.out.println("Book Title: ");
            while(rs.next())
            {
                String title;
                title = rs.getString("bookTitle");
                System.out.println(title);
            }
            rs.close();
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    public static void listSpecifiedGroup()
    {
        try{
            System.out.println("Enter Group name: ");
            String groupName;
            groupName = in.nextLine();
            pstmt = conn.prepareStatement(listSpecifiedGroup);
            pstmt.setString(1, groupName);
            ResultSet rs;
            rs = pstmt.executeQuery();
            System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
            while (rs.next()) 
            {
                
                String name = rs.getString("groupName");
                String head = rs.getString("headWriter");
                String year = rs.getString("yearFormed");
                String subject = rs.getString("subject");
                System.out.printf(displayFormat,
                        dispNull(name), dispNull(head), dispNull(year), dispNull(subject));
            }    
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void listSpecifiedPub()
    {
        try{
            System.out.println("Enter Publisher name: ");
            String publisherName;
            publisherName = in.nextLine();
            pstmt = conn.prepareStatement(listSpecifiedPub);
            pstmt.setString(1, publisherName);
            ResultSet rs;
            rs = pstmt.executeQuery();
            System.out.printf(displayFormat, "Publisher Name", "Address", "Phone Number", "Email");
            while (rs.next()) 
            {
                
                String name = rs.getString("publisherName");
                String address = rs.getString("publisherAddress");
                String phone = rs.getString("publisherPhone");
                String email = rs.getString("publisherEmail");
                System.out.printf(displayFormat,
                        dispNull(name), dispNull(address), dispNull(phone), dispNull(email));
            }   
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void listSpecifiedBook()
    {
        try{
            System.out.println("Enter Book Title: ");
            String bookTitle;
            bookTitle = in.nextLine();
            pstmt = conn.prepareStatement(listSpecifiedBook);
            pstmt.setString(1, bookTitle);
            ResultSet rs;
            rs = pstmt.executeQuery();
            System.out.printf(displayFormat, "Book Title", "Year Published",
                    "Number of Pages", "Group Name", "Publisher Name");
            while (rs.next()) 
            { 
                String title = rs.getString("bookTitle");
                String year = rs.getString("yearPublished");
                String pages = rs.getString("numberPages");
                String groupname = rs.getString("groupName");
                String pubname = rs.getString("publisherName");
                System.out.printf(displayFormat,
                        dispNull(title), dispNull(year), dispNull(pages), dispNull(groupname),
                        dispNull(pubname));
            }   
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void insertNewBook()
    {
        try
        {
            String bookTitle, publisherName, groupName;
            int yearPub, numberPages;
            System.out.println("Enter book title: ");
            bookTitle = in.nextLine();
            System.out.println("Enter year of publishing: ");
            yearPub = in.nextInt();
            System.out.println("Enter number of pages for book: ");
            numberPages = in.nextInt();
            System.out.println("Enter the name of publisher: ");
            publisherName = in.nextLine();
            System.out.println("Enter name of group: ");
            groupName = in.nextLine();
            pstmt = conn.prepareStatement(insertNewBook);
            pstmt.setString(1, bookTitle);
            pstmt.setInt(2, yearPub);
            pstmt.setInt(3, numberPages);
            pstmt.setString(4, groupName);
            pstmt.setString(5, publisherName);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(listSpecifiedBook);
            pstmt.setString(1, bookTitle);
            ResultSet rs;
            rs = pstmt.executeQuery();
            System.out.printf(displayFormat, "Book Title", "Year Published",
                    "Number of Pages", "Group Name", "Publisher Name");
            while (rs.next()) 
            { 
                String title = rs.getString("bookTitle");
                String year = rs.getString("yearPublished");
                String pages = rs.getString("numberPages");
                String groupname = rs.getString("groupName");
                String pubname = rs.getString("publisherName");
                System.out.printf(displayFormat,
                        dispNull(title), dispNull(year), dispNull(pages), dispNull(groupname),
                        dispNull(pubname));
            }   
            rs.close();
            System.out.println("New book addition complete.");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void insertNewPub()
    {
        try
        {
            String oldPubName,newPubName, pubAddress, pubPhone, pubEmail;
            System.out.println("Enter old publisher's name: ");
            oldPubName = in.nextLine();
            System.out.println("Enter new publisher's name: ");
            newPubName = in.nextLine();
            System.out.println("Enter publisher's address: ");
            pubAddress = in.nextLine();
            System.out.println("Enter publisher's phone number: ");
            pubPhone = in.nextLine();
            System.out.println("Enter publisher's Email: ");
            pubEmail = in.nextLine(); 
            pstmt = conn.prepareStatement(insertNewPub);
            pstmt.setString(1,newPubName);
            pstmt.setString(2, pubAddress);
            pstmt.setString(3, pubPhone);
            pstmt.setString(4, pubEmail);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(updatePub);
            pstmt.setString(1, oldPubName);
            pstmt.setString(2, newPubName);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(deleteOldPub);
            pstmt.setString(1, oldPubName);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(booksPub);
            pstmt.setString(1, oldPubName);
            ResultSet rs;
            rs = pstmt.executeQuery();
            System.out.printf(displayFormat, "Publisher Name", "Address", "Phone Number", "Email");
            while (rs.next()) 
            { 
                String name = rs.getString("publisherName");
                String address = rs.getString("publisherAddress");
                String phone = rs.getString("publisherPhone");
                String email = rs.getString("publisherEmail");
                System.out.printf(displayFormat,
                        dispNull(name), dispNull(address), dispNull(phone), dispNull(email));
            }   
            rs.close();
            System.out.println("New Publisher added and updated.");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void deleteBook()
    {
        try
        {
            System.out.println("Enter Book Title to delete: ");
            String bookTitle;
            bookTitle = in.nextLine();
            pstmt = conn.prepareStatement(deleteBook);
            pstmt.setString(1, bookTitle);
            pstmt.executeUpdate();
            System.out.println("Book successfully deleted.");
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}




