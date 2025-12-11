# Java-Client-Server-Chat-Application

The system consists of:
A Chat Server that accepts and manages multiple clients
A Chat Client that connects to the server and exchanges messages
Multiple clients can communicate simultaneously, and messages are broadcast to all connected users.

🎯 Features

✔ Real-time chat messaging
✔ Supports multiple clients using multithreading
✔ Server broadcasts each message to all connected users
✔ Simple, lightweight, and easy to run
✔ Uses pure Java (no external libraries)

🏗️ Technologies Used

Java (JDK 8 or above)
Socket Programming
Multithreading
Input/Output Streams

📂 Project Structure
ChatApplication/
│
├── ChatServer.java     → Handles multiple client connections
├── ChatClient.java     → Connects to server & sends/receives messages
└── README.md           → Documentation

⚙️ How the Server Works

The server runs on port 5000
Waits for incoming client connections
Each client is managed using a separate thread
Any message received from a client is broadcast to all clients

💬 How the Client Works

Connects to the server (localhost:5000)
Sends user input to the server
Continuously listens for broadcast messages
Displays received messages in real-time

▶️ How to Run the Project
Step 1 — Compile both files
javac ChatServer.java ChatClient.java

Step 2 — Start the server
java ChatServer


Expected output:

💬 Chat Server started...

Step 3 — Start one or more clients

Open multiple terminals and run:

java ChatClient


You will see:

Connected to Chat Server!
Type your messages:

Step 4 — Begin Chatting

Type in any client window:

Client 1:

Hello everyone!


Client 2 receives:

Hello everyone!


Server output:

Received: Hello everyone!

🧪 Sample Output
Client Terminal
Connected to Chat Server!
Type your messages:
Hi!
Hello!
How are you?

Server Terminal
💬 Chat Server started...
New user connected: Socket[addr=/127.0.0.1,port=xxxx]
Received: Hi!
Received: Hello!
Received: How are you?



