//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Hello world!");
//    }
//}
import java.util.Scanner;

class FlightReservationSystem {
    private static final int TOTAL_NUMBER_OF_SEATS = 100;
    private static final int[] seats = new int[TOTAL_NUMBER_OF_SEATS]; // 0 for available, -1 for booked
    private static int reserveSeats = 0; // Counter for reservations

    private enum SeatType { WINDOW, AISLE, MIDDLE }

    private class Passenger {
        String firstName;
        String lastName;
        String aadharID;
        String phoneNumber;
        String foodMenu;
        int seatNumber;
        int reservationNumber;
        Passenger next;

        Passenger(String fname, String lname, String aadhar, String phone, String food, int seat, int reservation) {
            this.firstName = fname;
            this.lastName = lname;
            this.aadharID = aadhar;
            this.phoneNumber = phone;
            this.foodMenu = food;
            this.seatNumber = seat;
            this.reservationNumber = reservation;
            this.next = null;
        }
    }

    private Passenger start; // Start of the linked list

    public FlightReservationSystem() {
        this.start = null;
    }

    private boolean validateAadharID(String id) {
        return id.length() == 12 && id.chars().allMatch(Character::isDigit);
    }

    private boolean validatePhoneNumber(String phone) {
        return phone.length() == 10 && phone.chars().allMatch(Character::isDigit);
    }

    private void showAvailableSeats(SeatType type) {
        System.out.println("Available seats (marked with |seat number|):");
        for (int i = 0; i < TOTAL_NUMBER_OF_SEATS; i++) {
            if (seats[i] == 0) {
                if (type == SeatType.WINDOW && (i % 6 == 0 || (i % 6 == 5 && i != 0))) {
                    System.out.print("| " + (i + 1) + " |");
                } else if (type == SeatType.AISLE && (i % 6 == 1 || i % 6 == 4)) {
                    System.out.print("| " + (i + 1) + " |");
                } else if (type == SeatType.MIDDLE && (i % 6 == 2 || i % 6 == 3)) {
                    System.out.print("| " + (i + 1) + " |");
                }
            }
            if ((i + 1) % 6 == 0) {
                System.out.println(); // New row of seats
            }
        }
        System.out.println();
    }

    private int chooseSeat(SeatType type) {
        showAvailableSeats(type);
        Scanner scanner = new Scanner(System.in);
        int seatNumber;

        while (true) {
            System.out.print("Enter seat number: ");
            seatNumber = scanner.nextInt();
            if (seatNumber < 1 || seatNumber > TOTAL_NUMBER_OF_SEATS) {
                System.out.println("Invalid seat number, enter again.");
            } else if (seats[seatNumber - 1] == -1) {
                System.out.println("Seat already booked, choose another seat.");
            } else {
                seats[seatNumber - 1] = -1; // Mark seat as booked
                return seatNumber;
            }
        }
    }

    public void bookTicket() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        // Validate AADHAR ID
        String aadharID;
        do {
            System.out.print("Enter your AADHAR ID (12 digits): ");
            aadharID = scanner.nextLine();
            if (!validateAadharID(aadharID)) {
                System.out.println("Invalid AADHAR ID. Please enter a 12-digit number.");
            }
        } while (!validateAadharID(aadharID));

        // Validate phone number
        String phoneNumber;
        do {
            System.out.print("Enter your phone number (10 digits): ");
            phoneNumber = scanner.nextLine();
            if (!validatePhoneNumber(phoneNumber)) {
                System.out.println("Invalid phone number. Please enter a 10-digit number.");
            }
        } while (!validatePhoneNumber(phoneNumber));

        // Choose seat type
        System.out.println("Choose your preferred seat type:");
        System.out.println("1. Window");
        System.out.println("2. Aisle");
        System.out.println("3. Middle");
        int seatTypeChoice = scanner.nextInt();
        SeatType chosenType;

        switch (seatTypeChoice) {
            case 1: chosenType = SeatType.WINDOW; break;
            case 2: chosenType = SeatType.AISLE; break;
            case 3: chosenType = SeatType.MIDDLE; break;
            default: System.out.println("Invalid choice, assigning middle seat by default."); chosenType = SeatType.MIDDLE;
        }

        // Choose seat number based on type and availability
        int seatNumber = chooseSeat(chosenType);

        // Choose food option
        System.out.println("Choose your food preference:");
        System.out.println("1. Veg");
        System.out.println("2. Non-Veg");
        System.out.println("3. No Food");
        int foodChoice;
        do {
            foodChoice = scanner.nextInt();
            if (foodChoice < 1 || foodChoice > 3) {
                System.out.println("Invalid choice, enter again: ");
            }
        } while (foodChoice < 1 || foodChoice > 3);

        String foodMenu = (foodChoice == 1) ? "Veg" : (foodChoice == 2) ? "Non-Veg" : "No Food";

        reserveSeats++; // Increment reservation count
        Passenger newPassenger = new Passenger(firstName, lastName, aadharID, phoneNumber, foodMenu, seatNumber, reserveSeats);

        // Add to linked list
        if (start == null) {
            start = newPassenger;
        } else {
            Passenger temp = start;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newPassenger;
        }

        // Show reservation number and seat allocation
        System.out.println("Your reservation number is: " + reserveSeats);
        System.out.println("Your seat number is: " + seatNumber);
    }

    public void cancelTicket() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your reservation number: ");
        int reservationNumber = scanner.nextInt();

        Passenger currentPassenger = start;
        Passenger previousPassenger = null;

        while (currentPassenger != null) {
            if (currentPassenger.reservationNumber == reservationNumber) {
                if (previousPassenger == null) { // Cancel first node
                    start = currentPassenger.next;
                } else { // Cancel middle or last node
                    previousPassenger.next = currentPassenger.next;
                }

                // Free seat and delete passenger record
                seats[currentPassenger.seatNumber - 1] = 0;
                System.out.println("Reservation cancelled successfully.");
                return;
            }
            previousPassenger = currentPassenger;
            currentPassenger = currentPassenger.next;
        }

        System.out.println("Reservation not found.");
    }

    public void changeReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your current seat number: ");
        int currentSeat = scanner.nextInt();

        Passenger currentPassenger = start;
        while (currentPassenger != null) {
            if (currentPassenger.seatNumber == currentSeat) {
                break;
            }
            currentPassenger = currentPassenger.next;
        }

        if (currentPassenger == null) {
            System.out.println("Seat not found.");
            return;
        }

        // Ask for the preferred seat type
        System.out.println("Choose your preferred seat type for the new seat:");
        System.out.println("1. Window");
        System.out.println("2. Aisle");
        System.out.println("3. Middle");
        int seatTypeChoice = scanner.nextInt();
        SeatType chosenType;

        switch (seatTypeChoice) {
            case 1: chosenType = SeatType.WINDOW; break;
            case 2: chosenType = SeatType.AISLE; break;
            case 3: chosenType = SeatType.MIDDLE; break;
            default: System.out.println("Invalid choice, assigning middle seat by default."); chosenType = SeatType.MIDDLE;
        }

        // Choose a new seat
        int newSeat = chooseSeat(chosenType);

        // Update seat assignment
        seats[currentPassenger.seatNumber - 1] = 0;
        currentPassenger.seatNumber = newSeat;
        seats[newSeat - 1] = -1;

        System.out.println("Seat changed successfully to seat number: " + newSeat);
    }

    public void passengerDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your reservation number: ");
        int reservationNumber = scanner.nextInt();

        Passenger currentPassenger = start;
        while (currentPassenger != null) {
            if (currentPassenger.reservationNumber == reservationNumber) {
                System.out.println("Reservation Number: " + currentPassenger.reservationNumber);
                System.out.println("First Name: " + currentPassenger.firstName);
                System.out.println("Last Name: " + currentPassenger.lastName);
                System.out.println("AADHAR ID: " + currentPassenger.aadharID);
                System.out.println("Phone Number: " + currentPassenger.phoneNumber);
                System.out.println("Seat Number: " + currentPassenger.seatNumber);
                System.out.println("Food Preference: " + currentPassenger.foodMenu);
                return;
            }
            currentPassenger = currentPassenger.next;
        }

        System.out.println("Passenger not found.");
    }

    public void getBookingDetails() {
        Passenger currentPassenger = start;
        System.out.println("Booking details:");
        while (currentPassenger != null) {
            System.out.println("Reservation Number: " + currentPassenger.reservationNumber);
            System.out.println("First Name: " + currentPassenger.firstName);
            System.out.println("Last Name: " + currentPassenger.lastName);
            System.out.println("AADHAR ID: " + currentPassenger.aadharID);
            System.out.println("Phone Number: " + currentPassenger.phoneNumber);
            System.out.println("Seat Number: " + currentPassenger.seatNumber);
            System.out.println("Food Preference: " + currentPassenger.foodMenu);
            System.out.println("----------------------------");
            currentPassenger = currentPassenger.next;
        }
    }

    public static void main(String[] args) {
        FlightReservationSystem flightReservation = new FlightReservationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\t\t|----------------------------------------------|");
            System.out.println("\t\t|  WELCOME TO TRAVELMANIA FLIGHT RESERVATION   |");
            System.out.println("\t\t|----------------------------------------------|");
            System.out.println("\t\t| 1. BOOK TICKET                              |");
            System.out.println("\t\t| 2. CANCEL TICKET                            |");
            System.out.println("\t\t| 3. CHANGE RESERVATION                       |");
            System.out.println("\t\t| 4. PASSENGER DETAILS                        |");
            System.out.println("\t\t| 5. GET BOOKING DETAILS                      |");
            System.out.println("\t\t| 6. EXIT                                     |");
            System.out.println("\t\t|----------------------------------------------|");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    flightReservation.bookTicket();
                    break;
                case 2:
                    flightReservation.cancelTicket();
                    break;
                case 3:
                    flightReservation.changeReservation();
                    break;
                case 4:
                    flightReservation.passengerDetails();
                    break;
                case 5:
                    flightReservation.getBookingDetails();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

