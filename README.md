The Flight Reservation System is a Java-based console application designed to manage flight bookings efficiently. It allows users to book tickets, cancel reservations, change seat assignments, and retrieve booking details. The system leverages a linked list to keep track of passenger information dynamically.

Key Features:
Booking Tickets:

Users can enter their personal information, including first name, last name, AADHAR ID, and phone number.
The system validates the AADHAR ID to ensure it is a 12-digit numeric value and checks that the phone number is a 10-digit numeric value.
Passengers can choose their preferred seat type (Window, Aisle, Middle) from available options.
After seat selection, users can choose their food preferences (Veg, Non-Veg, No Food).
Each booking generates a unique reservation number, allowing easy retrieval of booking information.

Canceling Reservations:

Passengers can cancel their bookings by providing their reservation number.
The system frees the previously booked seat and removes the passenger's details from the linked list.

Changing Reservations:

Users can change their seat assignments by providing their current seat number.
The system prompts for a new preferred seat type and allows users to select from available seats.
Retrieving Passenger Details:

Passengers can view their booking details by entering their reservation number. This includes personal information and seat allocation.

Viewing Booking Details:

The system can list all current bookings, displaying each passenger’s reservation number, personal details, seat number, and food preferences.

User-Friendly Interface:

The system provides a simple command-line interface with clear prompts and instructions, making it easy for users to navigate through various functionalities.

Data Structure:
Linked List: The system uses a linked list to manage passenger records, allowing dynamic memory allocation for each new booking. Each node in the list represents a passenger and contains:

Personal information (first name, last name, AADHAR ID, phone number)
Food preference
Seat number
Reservation number
A pointer to the next passenger node
Seat Management: An array of integers is used to keep track of seat availability. Each seat is marked as:

0: Available
-1: Booked
Enum for Seat Types:
The system defines an enum called SeatType to represent the different seat types: WINDOW, AISLE, and MIDDLE.
Example Flow:
Upon starting the application, users are greeted with a welcome message and presented with a menu of options.
Users can choose to book a ticket, cancel a ticket, change a reservation, view passenger details, or exit the application.
Each action is handled through dedicated methods in the FlightReservationSystem class, ensuring organized and manageable code.
Conclusion
The Flight Reservation System provides a straightforward and efficient way to manage flight bookings. By utilizing Java's object-oriented principles and linked list data structures, it ensures flexibility and ease of use for passengers. The system can be expanded with additional features such as payment processing, seat selection maps, or integration with airline databases for real-time flight information, enhancing its utility further.
