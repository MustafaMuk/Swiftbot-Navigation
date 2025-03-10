import swiftbot.*;
import swiftbot.Button;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resit1 {
    private static SwiftBotAPI botAPI;

    public static void main(String[] args) throws IOException {
        showHeader();

        // Initialize SwiftBotAPI once
        if (botAPI == null) {
            try {
                botAPI = new SwiftBotAPI();
            } catch (Exception e) {
                System.err.println("Initialization error for SwiftBotAPI: " + e.getMessage());
                return; // Exit if initialization fails
            }
        }

        AtomicBoolean buttonPressed = new AtomicBoolean(false);
        AtomicBoolean keepScanning = new AtomicBoolean(false);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        while (true) {
            try {
                botAPI.disableButton(Button.A);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Enable Button A and set flag when pressed
            botAPI.enableButton(Button.A, () -> {
                System.out.println("Button A was clicked!");
                buttonPressed.set(true);
            });

            System.out.println("Click Button A to move forward");

            // Wait for Button A to be pressed
            while (!buttonPressed.get()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("Kindly scan the QR code");

            // Get and decode QR code image
            BufferedImage img = botAPI.getQRImage();
            String decodedText;
            try {
                decodedText = botAPI.decodeQRImage(img);
            } catch (IllegalArgumentException e) {
                System.out.println("QR code not found. Please scan again.");
                continue;
            }

            if (decodedText != null && !decodedText.isEmpty()) {
                System.out.println("QR code scanned: " + decodedText);
                handleQRCode(decodedText, timeFormatter, buttonPressed, keepScanning);
            } else {
                System.out.println("Couldn't detect QR code. Attempt scanning once more.");
            }
        }
    }

    private static void handleQRCode(String decodedText, DateTimeFormatter timeFormatter,
                                     AtomicBoolean buttonPressed, AtomicBoolean keepScanning) {
        // Regular expression to match QR code format
        String regex = "(\\d{1,3}):([a-zA-Z]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(decodedText);

        if (matcher.matches()) {
            int value1 = Integer.parseInt(matcher.group(1));
            String value2 = matcher.group(2).toLowerCase();

            // Validate QR code values
            if (value1 >= 0 && value1 <= 100 &&
                    (value2.equals("red") || value2.equals("green") || value2.equals("blue") || value2.equals("white"))) {
                processValidQRCode(value1, value2, timeFormatter, buttonPressed, keepScanning);
            } else {
                System.out.println("QR code contains invalid values. Scan another code.");
            }
        } else {
            System.out.println("QR code format is invalid. Try another scan.");
        }
    }

    private static void processValidQRCode(int value1, String value2, DateTimeFormatter timeFormatter,
                                           AtomicBoolean buttonPressed, AtomicBoolean keepScanning) {
        System.out.println("Value 1: " + value1);
        System.out.println("Value 2: " + value2);

        // Convert value1 to different numeral systems
        String octalValue = toOctal(value1);
        String hexValue = toHexadecimal(value1);
        String binaryValue = toBinary(value1);

        System.out.println("Octal value: " + octalValue);
        System.out.println("Hexadecimal value: " + hexValue);
        System.out.println("Binary value: " + binaryValue);

        // Determine speed and duration based on octal and hex values
        int speed = Math.min((Integer.parseInt(octalValue, 8) < 50 ? Integer.parseInt(octalValue, 8) + 50 : Integer.parseInt(octalValue, 8)), 100);
        System.out.println("Speed: " + speed);

        int duration = (hexValue.length() == 1) ? 1000 : 2000;
        System.out.println("Duration: " + duration + " ms");

        // Illuminate underlights based on color
        int[] color = parseColor(value2);
        illuminateUnderlights(color);

        String startTime = LocalTime.now().format(timeFormatter);
        System.out.println("Start Time: " + startTime);

        // Perform movements based on binary value
        performMovement(binaryValue, speed, duration);

        String endTime = LocalTime.now().format(timeFormatter);
        System.out.println("End Time: " + endTime);

        illuminateUnderlights(color);

        awaitUserResponse(buttonPressed, keepScanning);
    }

    private static void illuminateUnderlights(int[] color) {
        try {
            botAPI.fillUnderlights(color);
            Thread.sleep(1000);
            botAPI.disableUnderlights();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void performMovement(String binaryPattern, int speed, int duration) {
        for (int i = binaryPattern.length() - 1; i >= 0; i--) {
            executeMovementStep(binaryPattern.charAt(i), speed, duration);
        }
    }

    private static void executeMovementStep(char bit, int speed, int duration) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (bit == '1') {
            botAPI.move(speed, speed, duration); // Forward
            botAPI.move(speed, -speed, 500); // Turn clockwise
        } else {
            botAPI.move(speed, speed, duration); // Forward
            botAPI.move(-speed, speed, 500); // Turn counterclockwise
        }
        botAPI.stopMove(); // Stop after each step
    }

    private static void awaitUserResponse(AtomicBoolean buttonPressed, AtomicBoolean keepScanning) {
        System.out.println("Would you like to scan another QR code? (Press 'Y' for yes, 'X' for no)");

        try {
            botAPI.disableButton(Button.Y);
            botAPI.disableButton(Button.X);
        } catch (Exception e) {
            e.printStackTrace();
        }

        botAPI.enableButton(Button.Y, () -> {
            System.out.println("Button Y was clicked... Restarting...");
            keepScanning.set(true);
        });

        botAPI.enableButton(Button.X, () -> {
            System.out.println("Button X was clicked... Exiting program!");
            System.exit(0);
        });

        while (!keepScanning.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        buttonPressed.set(false);
        keepScanning.set(false);
    }

    private static String toOctal(int decimal) {
        return Integer.toOctalString(decimal);
    }

    private static String toHexadecimal(int decimal) {
        return Integer.toHexString(decimal);
    }

    private static String toBinary(int decimal) {
        return Integer.toBinaryString(decimal);
    }

    private static int[] parseColor(String color) {
        switch (color.toLowerCase()) {
            case "red":
                return new int[]{255, 0, 0};
            case "green":
                return new int[]{0, 255, 0};
            case "blue":
                return new int[]{0, 0, 255};
            case "white":
                return new int[]{255, 255, 255};
            default:
                throw new IllegalArgumentException("Invalid color: " + color);
        }
    }

    private static void showHeader() {
        System.out.println("");
        System.out.println("@@@@@@@   @@@@@@@@   @@@@@@   @@@  @@@@@@@");
        System.out.println("@@@@@@@@  @@@@@@@@  @@@@@@@   @@@  @@@@@@@");
        System.out.println("@@!  @@@  @@!       !@@       @@!    @@!   ");
        System.out.println("!@!  @!@  !@!       !@!       !@!    !@!   ");
        System.out.println("@!@!!@!   @!!!:!    !!@@!!    !!@    @!!");
        System.out.println("!!@!@!    !!!!!:     !!@!!!   !!!    !!!");
        System.out.println("!!: :!!   !!:            !:!  !!:    !!:");
        System.out.println(":!:  !:!  :!:           !:!   :!:    :!: ");
        System.out.println("::   :::   :: ::::  :::: ::    ::     :: ");
        System.out.println(" :   : :  : :: ::   :: : :    :       :   ");
        System.out.println("");
    }
}