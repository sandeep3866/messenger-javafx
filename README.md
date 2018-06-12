# JavaFX Messenger Application

A simple **JavaFX-based client–server chat application**. 
The objective of this project was to understand **JavaFX GUI development**, **socket programming**, and **multithreaded communication** in Java.

---

## Overview

This application enables real-time message exchange between a client and a server using TCP sockets.  
Both the client and server are implemented as JavaFX desktop applications with basic message styling and timestamps.

---

## Objectives

- Learn JavaFX UI design and event handling
- Implement client–server communication using sockets
- Handle network operations using multithreading
- Safely update JavaFX UI from background threads

---

## Architecture

The system follows a **client–server architecture**:

- **Server**
  - Listens on a specified port
  - Accepts a single client connection
  - Receives and displays incoming messages

- **Client**
  - Connects to the server using IP address and port
  - Sends messages to the server
  - Displays received messages in real time

Networking logic runs on background threads, while UI updates are executed on the JavaFX Application Thread using `Platform.runLater()`.

---

