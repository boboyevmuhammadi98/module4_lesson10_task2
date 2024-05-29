package tasks.task2;

import logging_practis.PracticesLogging;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Task2 {

    private static Logger logger = Logger.getLogger(PracticesLogging.class.getSimpleName());
    private static final String userDataPath = "src/main/resources/chatAppUser.txt";
    private static final String chatDataPath = "src/main/resources/chatAppMessage.txt";
    private static final String loggerDataPath = "src/main/resources/logging.properties";

    public static ChatAppUser currentUser = null;
    static Scanner text = new Scanner(System.in);
    static Scanner number = new Scanner(System.in);

    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream(loggerDataPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("welcome to the our chat app");
        while (true) {
            if (currentUser == null) {
                System.out.print("choose one the section below\n" +
                        "1 - register user : \n" +
                        "2 - login chat app : ");
                int i = number.nextInt();
                if (i == 1) {
                    System.out.print("enter email : ");
                    String email = text.nextLine();
                    registerUser(email);
                } else if (i == 2) {
                    System.out.print("enter email : ");
                    String email = text.nextLine();
                    loginUser(email);
                }
            } else {
                System.out.print("choose one the section below\n" +
                        "3 - send message : \n" +
                        "4 - view all user : \n" +
                        "5 - view all message : \n" +
                        "6 - logout : ");
                int i = number.nextInt();
                if (i == 3) {
                    System.out.print("enter message : ");
                    String message = text.nextLine();
                    sendMessage(message);
                } else if (i == 4) {
                    viewAllUser();
                } else if (i == 5) {
                    viewAllMessage();
                } else if (i == 6) {
                    currentUser = null;
                    logger.info("you logged out");
                }
            }
        }


    }

    private static void viewAllMessage() throws InterruptedException {
        System.out.println("--------------------------------------------------------------------------------");
        Chat chat;
        if (Path.of(chatDataPath).toFile().exists()) {
            try (
                    FileInputStream in = new FileInputStream(chatDataPath);
            ) {
                try {
                    while (true) {
                        ObjectInputStream readChatAppUserData = new ObjectInputStream(in);
                        chat = (Chat) readChatAppUserData.readObject();
                        System.out.println(chat);
                    }
                } catch (EOFException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info("no chat for view");
            Thread.sleep(500);
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void viewAllUser() throws InterruptedException {
        System.out.println("--------------------------------------------------------------------------------");
        ChatAppUser chatAppUser;
        if (Path.of(userDataPath).toFile().exists()) {
            try (
                    FileInputStream in = new FileInputStream(userDataPath);
            ) {
                try {
                    while (true) {
                        ObjectInputStream readChatAppUserData = new ObjectInputStream(in);
                        chatAppUser = (ChatAppUser) readChatAppUserData.readObject();
                        System.out.println(chatAppUser);
                    }
                } catch (EOFException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info("no user for view");
            Thread.sleep(500);
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void registerUser(String mail) throws ClassNotFoundException, InterruptedException {
        ChatAppUser chatAppUser = null;
        if (mail.matches("^((\\w{3,})@([\\w-]+)\\.(\\w{2,4}))$")) {
            if (Path.of(userDataPath).toFile().exists()) {
                try (FileInputStream in = new FileInputStream(userDataPath);
                ) {
                    try {
                        while (true) {
                            ObjectInputStream objectInputStream = new ObjectInputStream(in);
                            chatAppUser = (ChatAppUser) objectInputStream.readObject();
                            if (chatAppUser.getEmail().equalsIgnoreCase(mail)) {
                                logger.info("email already exists choose another email");
                                return;
                            }
                        }
                    } catch (EOFException e) {
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            logger.info("invalid email. please enter valid email");
            Thread.sleep(500);
            return;
        }
        chatAppUser = new ChatAppUser(mail);
        try (
                FileOutputStream out = new FileOutputStream(userDataPath, true);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        ) {
            objectOutputStream.writeObject(chatAppUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("new user registered with email : " + mail);
    }

    public static void loginUser(String mail) throws IOException, ClassNotFoundException {
        ChatAppUser chatAppUser = null;
        if (mail.matches("^((\\w{3,})@([\\w-]+)\\.(\\w{2,4}))$")) {
            if (Path.of(userDataPath).toFile().exists()) {
                try (
                        FileInputStream in = new FileInputStream(userDataPath);
                ) {
                    try {
                        while (true) {
                            ObjectInputStream readChatAppUserData = new ObjectInputStream(in);
                            chatAppUser = (ChatAppUser) readChatAppUserData.readObject();
                            if (chatAppUser.getEmail().equalsIgnoreCase(mail)) {
                                logger.info("you logged in our system");
                                Thread.sleep(500);
                                currentUser = chatAppUser;
                                return;
                            }
                        }
                    } catch (EOFException e) {
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            logger.info("invalid email. please enter valid email");
            return;
        }
        logger.info("user with mail not found");
    }

    public static void sendMessage(String message) throws IOException, ClassNotFoundException, InterruptedException {
        Chat chat = new Chat(currentUser, message);
        try (
                FileOutputStream out = new FileOutputStream(chatDataPath);
                ObjectOutputStream writeMessageData = new ObjectOutputStream(out);
        ) {
            writeMessageData.writeObject(chat);
            logger.info("messages sent");
            Thread.sleep(500);
        }
    }
}
