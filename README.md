🚀 SwiftBot Navigation Project (Java)
A Java-based robot control system that converts a decimal number into its binary, hexadecimal, and octal equivalents to determine the movement, speed, and duration of a SwiftBot robot.

📌 Project Overview
This project controls the SwiftBot robot using QR code input. The program reads a decimal number and a color from the QR code, then:

Converts the number into binary, hexadecimal, and octal.
Uses binary to determine movement direction.
Uses octal to determine speed.
Uses hexadecimal to determine movement duration.
Uses the color to blink the SwiftBot underlights before and after execution.
✅ Key Features:

Reads a QR code containing a number and a color.
Converts the decimal number to binary, hexadecimal, and octal.
Moves forward and turns 90 degrees based on the binary value.
Adjusts speed based on the octal value.
Moves for 1-2 seconds based on the hexadecimal value.
Blinks underlights in the specified color before and after movement.
Logs movement details to a text file.
Allows repeated execution until the user chooses to stop.
📌 How It Works
1️⃣ User Input via QR Code
The QR code contains:

A decimal number (0 - 100).
A color (Red, Green, Blue, White).
2️⃣ Number Conversions
The decimal number is converted into:

Binary → Determines movement direction (clockwise/anti-clockwise turns).
Octal → Determines speed.
Hexadecimal → Determines movement duration.
3️⃣ SwiftBot Movement
Binary (1) → Move forward and turn 90° clockwise.
Binary (0) → Move forward and turn 90° anti-clockwise.
4️⃣ Color Blinking & Logging
The SwiftBot blinks its underlights in the specified color before & after movement.
The movement details are logged to a text file.
5️⃣ Repeated Execution
The user can scan another QR code (Y button).
The program terminates (X button), and the log file is saved.
📌 Example Input & Output
Example 1
🔹 QR Code Scanned: 100:Red
🔹 Converted Values:

Binary: 1100100
Hexadecimal: 64
Octal: 144
🔹 SwiftBot Movements:

mathematica
Copy
[BLINK] Underlights RED
[START] Moving at speed: 100
→ Forward 2s, Turn 90° Anti-clockwise
→ Forward 2s, Turn 90° Anti-clockwise
→ Forward 2s, Turn 90° Clockwise
→ Forward 2s, Turn 90° Anti-clockwise
→ Forward 2s, Turn 90° Anti-clockwise
→ Forward 2s, Turn 90° Clockwise
→ Forward 2s, Turn 90° Clockwise
[BLINK] Underlights RED
[LOG] Saved to movement_log.txt
Example 2
🔹 QR Code Scanned: 15:White
🔹 Converted Values:

Binary: 1111
Hexadecimal: F
Octal: 17
🔹 SwiftBot Movements:

csharp
Copy
[BLINK] Underlights WHITE
[START] Moving at speed: 67
→ Forward 1s, Turn 90° Clockwise
→ Forward 1s, Turn 90° Clockwise
→ Forward 1s, Turn 90° Clockwise
→ Forward 1s, Turn 90° Clockwise
[BLINK] Underlights WHITE
[LOG] Saved to movement_log.txt
📌 Installation & Setup
Clone the repository:
sh
Copy
git clone https://github.com/MustafaMuk/SwiftBot-Navigation.git
cd SwiftBot-Navigation
Ensure you have Java installed (JDK 11+ recommended).
Compile & Run the Program:
sh
Copy
javac SwiftBotNavigation.java
java SwiftBotNavigation
Scan a QR Code and watch the SwiftBot move!
📌 Technologies Used
Java for programming logic.
SwiftBot API for robot interaction.
QR Code Scanning for input.
File Handling for logging movement data.
Error Handling to manage invalid inputs.
📌 Skills Gained
✅ Algorithm Design – Implemented logic for movement based on number conversion.
✅ Embedded Systems Programming – Controlled SwiftBot movement via Java.
✅ File Handling – Logged data to a text file.
✅ User Interaction – Implemented error handling for invalid inputs.
✅ Debugging – Fixed errors related to movement, conversions, and input validation.
