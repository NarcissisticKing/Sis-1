package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 19235);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("shape type (Circle, Rectangle) or 'Q' to quit:");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("Q")) {
                    oos.writeObject(null); //close the connection
                    System.out.println("Closing client...");
                    break;
                }

                Shape shape = null;
                if (input.equalsIgnoreCase("Circle")) {
                    System.out.print("Enter radius: ");
                    double radius = scanner.nextDouble();
                    shape = new Circle(radius);
                } else if (input.equalsIgnoreCase("Rectangle")) {
                    System.out.print("Enter width: ");
                    double width = scanner.nextDouble();
                    System.out.print("Enter height: ");
                    double height = scanner.nextDouble();
                    shape = new Rectangle(width, height);
                } else {
                    System.out.println("Invalid shape type ");
                    continue;
                }

                scanner.nextLine(); // Clear buffer

                oos.writeObject(shape);
                double area = (double) ois.readObject();
                System.out.println("Area of the shape: " + area);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
