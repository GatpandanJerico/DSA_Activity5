import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class Activity_5 {
    public static void main(String[] args) throws Exception {
        File file = new File("records.txt");
        try (Scanner inputScanner = new Scanner(System.in)) {
            LinkedList<String> nameList = new LinkedList<String>();
            LinkedList<String> ageList = new LinkedList<String>();
            LinkedList<String> genderList = new LinkedList<String>();

            if (file.exists()) {
                try (Scanner fileScanner = new Scanner(file)) {
                    storeData(fileScanner, nameList, ageList, genderList);
                }
            }

            System.out.println("\nMenu");
            System.out.println("[1] Add Record to First\r\n" + //
                            "[2] Add Record to Last \r\n" + //
                            "[3] Remove First Record\r\n" + //
                            "[4] Remove Last Record\r\n" + //
                            "[5] Display All Record\r\n" + //
                            "[6] Display Specific Record\r\n" + //
                            "[7] Edit  Record\r\n" + //
                            "");
            System.out.print("Enter your choice: ");
            int menuChoice = inputScanner.nextInt();
            inputScanner.nextLine();

            switch (menuChoice) {
                case 1:
                    addRecord("first", inputScanner, nameList, ageList, genderList);
                    break;
                case 2:
                    addRecord("last",inputScanner, nameList, ageList, genderList);
                    break;    
                case 3: 
                    removeRecord("first");
                    break;
                case 4:
                    removeRecord("last");
                    break;
                case 5: 
                    displayRecord("all");
                    break;
                case 6: 
                    displayRecord("specific");
                    break;
                case 7: 
                    editRecord();
                    break;    

                default:
                    break;
            }

            for (String string : nameList) {
                System.out.print(string + " ");
            }
            System.out.println();
            for (String string : genderList) {
                System.out.print(string + " ");
            }
            System.out.println();
            for (String string : genderList) {
                System.out.print(string + " ");
            }
        }
    }

    private static void addRecord(String i, Scanner inputScanner, LinkedList<String> nameList, LinkedList<String> ageList, LinkedList<String> genderList) {
        String[] recordType = {"name", "age", "gender"};
        String[] records = new String[3];
        for (int j = 0; j < records.length; j++) {
            records[j] = getRecord(inputScanner, recordType[j]);
            
        }
        if (i.equals("first")) {
            nameList.addFirst(records[0]);
            ageList.addFirst(records[1]);
            genderList.addFirst(records[2]);
        } else {
             nameList.addLast(records[0]);
            ageList.addLast(records[1]);
            genderList.addLast(records[2]);
        }
}

    private static void removeRecord(String i) {
        
    }
    
    private static void displayRecord(String i) {
        
    }

    private static void editRecord() {
        
    }

    private static String getRecord(Scanner inputScanner, String recordType) {
        String record;
        System.out.print("Enter " + recordType + ": ");
        record = inputScanner.nextLine().trim();
        return record;

    }


    private static void storeData(Scanner fileScanner, LinkedList<String> nameList, LinkedList<String> ageList,
            LinkedList<String> genderList) {

        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] token = line.split(",");
                nameList.add(token[0]);
                ageList.add(token[1]);
                genderList.add(token[2]);
            }
        }
    }
}
