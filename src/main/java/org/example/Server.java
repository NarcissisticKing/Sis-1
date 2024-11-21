package org.example;

import org.example.Shape;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(19235)) {
            System.out.println("Server is running...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

                    Shape shape = (Shape) ois.readObject();
                    if (shape == null) {
                        System.out.println("Server shutting down...");
                        break;
                    }

                    double area = shape.calculateArea();
                    oos.writeObject(area);
                    System.out.println("Processed shape: " + shape.getClass().getSimpleName() + ", Area: " + area);
                } catch (EOFException e) {
                    System.out.println("Connection closed by client.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
