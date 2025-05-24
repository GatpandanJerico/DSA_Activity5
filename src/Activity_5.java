import java.io.File;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Activity_5 {
    // Create 3 LinkeList to store data of each field
    static LinkedList<String> nameList = new LinkedList<String>();
    static LinkedList<String> ageList = new LinkedList<String>();
    static LinkedList<String> genderList = new LinkedList<String>();

    public static void main(String[] args) throws Exception {
        File file = new File("records.txt");

        try (Scanner inputScanner = new Scanner(System.in)) {
            // Check if the file exist and scan that file
            if (file.exists()) {
                nameList.clear();
                ageList.clear();
                genderList.clear();
                try (Scanner fileScanner = new Scanner(file)) {
                    storeData(fileScanner, nameList, ageList, genderList);
                }
            }
            while (true) {
                // Main menu of the program
                System.out.println("\n========= MAIN MENU =========");
                System.out.println("[1] Add Record to First\r\n" +
                        "[2] Add Record to Last \r\n" +
                        "[3] Remove First Record\r\n" +
                        "[4] Remove Last Record\r\n" +
                        "[5] Display All Record\r\n" +
                        "[6] Display Specific Record\r\n" +
                        "[7] Edit  Record\r\n" +
                        "[0] Exit");
                System.out.println("=============================");
                System.out.print("Enter your choice: ");

                // Check if the choice is valid number and within the range of the choices
                if (!inputScanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 0 and 7.");
                    inputScanner.nextLine();
                    continue;
                }
                int menuChoice = inputScanner.nextInt();
                inputScanner.nextLine();

                switch (menuChoice) {
                    case 1:
                        addRecord("first", inputScanner);
                        break;
                    case 2:
                        addRecord("last", inputScanner);
                        break;
                    case 3:
                        removeRecord("first");
                        break;
                    case 4:
                        removeRecord("last");
                        break;
                    case 5:
                        displayAllRecord();
                        break;
                    case 6:
                        displaySpecificRecord(inputScanner);
                        break;
                    case 7:
                        editRecord(inputScanner);
                        break;
                    case 0:
                        System.out.println("Exiting... Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
        }

    }

    // Method that get the user input and add it to the correct position
    private static void addRecord(String position, Scanner inputScanner) throws Exception {
        String[] fields = { "name", "age", "gender" };
        String[] records = new String[3];

        // Get the user input based on the field type
        for (int j = 0; j < records.length; j++) {
            records[j] = getRecordInput(inputScanner, fields[j]);
        }

        // Add the user input in its correct position
        if (position.equals("first")) {
            nameList.addFirst(records[0]);
            ageList.addFirst(records[1]);
            genderList.addFirst(records[2]);
        } else {
            nameList.addLast(records[0]);
            ageList.addLast(records[1]);
            genderList.addLast(records[2]);
        }
        // Call the updateFile method that write the new record into the txt file
        updateFile();
        System.out.println("Record added successfully.");
    }

    // Method that remove a record based on chosen position
    private static void removeRecord(String position) {
        if (nameList.isEmpty()) {
            System.out.println("No record to remove.");
            return;
        }

        if (position.equals("first")) {
            nameList.removeFirst();
            ageList.removeFirst();
            genderList.removeFirst();
        } else {
            nameList.removeLast();
            ageList.removeLast();
            genderList.removeLast();
        }

        // Call the updateFile method that write the new record into the txt file
        updateFile();
        System.out.println("Record removed successfully.");
    }

    // Print all records
    private static void displayAllRecord() {
        System.out.println("\n===== All Records =====");
        if (nameList.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        // Print all content of the linkelist using for each loop
        int index = 0;
        for (String record : nameList) {
            System.out.printf("%d. Name: %s | Age: %s | Gender: %s%n", index + 1, record, ageList.get(index),
                    genderList.get(index));
            index++;
        }
    }

    // Display specific record
    private static void displaySpecificRecord(Scanner inputScanner) {
        if (nameList.isEmpty()) {
            System.out.println("No records available.");
            return;
        }
        while (true) {
            try {
                System.out.print("Enter record number to display: ");
                int index = inputScanner.nextInt();
                inputScanner.nextLine();

                if (index >= 1 && index <= nameList.size()) {
                    System.out.println("Record #" + index + ": " +
                            nameList.get(index - 1) + ", " +
                            ageList.get(index - 1) + ", " +
                            genderList.get(index - 1));
                            break;
                } else {
                    System.out.println("Invalid record number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a line number between 1 to " + nameList.size() + ".");
                break;
            }
        }

    }

    // Method that edit the record based on record number
    private static void editRecord(Scanner inputScanner) throws Exception {
        if (nameList.isEmpty()) {
            System.out.println("No records available to edit.");
            return;
        }
        System.out.print("Enter record number to edit: ");
        int index = inputScanner.nextInt();
        inputScanner.nextLine();

        // Condition that check if the inputted number is within the range of the
        // linkedlist size
        if (index >= 1 && index <= nameList.size()) {
            index -= 1;
            System.out.println("Editing Record #" + (index + 1));
            System.out.printf("Name: %s | Age: %s | Gender: %s%n",
                    nameList.get(index), ageList.get(index), genderList.get(index));

            // Prompt the user for new details
            System.out.println("Enter new details:");

            System.out.print("New name: ");
            nameList.set(index, inputScanner.nextLine().trim());

            System.out.print("New age: ");
            ageList.set(index, inputScanner.nextLine().trim());

            System.out.print("New gender (Male/Female): ");
            String genderInput = inputScanner.nextLine().trim();

            // Check if the input is valid
            if (!genderInput.equalsIgnoreCase("male") && !genderInput.equalsIgnoreCase("female")) {
                System.out.println("Invalid gender. Please enter Male or Female.");
                return;
            }

            // Format gender to become Sentence case (maLe -> Male or femAle -> Female)
            String formattedGender = genderInput.substring(0, 1).toUpperCase() + genderInput.substring(1).toLowerCase();
            genderList.set(index, formattedGender);

            // Call the updateFile method that write the new record into the txt file
            updateFile();
            System.out.println("Record updated successfully.");

        } else {
            System.out.println("Invalid record number.");
        }

    }

    private static String getRecordInput(Scanner inputScanner, String recordType) {
        while (true) {
            System.out.print("Enter " + recordType + ": ");
            String input = inputScanner.nextLine().trim();
            if (recordType.equals("gender")) {
                if (!input.equalsIgnoreCase("male") && !input.equalsIgnoreCase("female")) {
                    System.out.println("Invalid gender. Please enter Male, Female only.");
                    continue;
                }
                input = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
            }
            // Check if age only have numeric value
            if (recordType.equals("age")) {
                if (!input.matches("\\d+")) {
                    System.out.println("Invalid age. Please enter a numeric value.");
                    continue;
                }
            } else if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }
            return input;
        }
    }

    private static void storeData(Scanner fileScanner, LinkedList<String> nameList, LinkedList<String> ageList,
            LinkedList<String> genderList) {
        int lineNumber = 0;
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine().trim();
            lineNumber++;
            if (!line.isEmpty()) {
                String[] token = line.split(",");

                // Check if line is invalid and skip it
                if (token.length != 3) {
                    System.out.println("Skipping invalid line " + lineNumber + ": " + line);
                    continue;
                }

                nameList.add(token[0]);
                ageList.add(token[1]);
                genderList.add(token[2]);
            }
        }
    }

    // Method to write to the txt file
    private static void updateFile() {
        try (FileWriter writer = new FileWriter("records.txt")) {
            for (int i = 0; i < nameList.size(); i++) {
                writer.write(nameList.get(i) + "," + ageList.get(i) + "," + genderList.get(i) + "\n");
            }
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
