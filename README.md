[![version_badge](/res/svg/version_badge.svg)]()
[![license_badge](/res/svg/license_badge.svg)](/LICENSE)

# Multithreaded Server Library for Java
This library for Java makes it easy to implement multithreaded servers and singlethreaded clients into your projects. It's designed to be utilizable in many different types of applications.

## Table of Contents
+ [Getting Started](#getting-started)
	- [How to install](#how-to-install)
		* [Build the library](#build-the-library)
		* [Use the library](#use-the-library)
		* [Import the project into Eclipse](#import-the-project-into-eclipse)
	- [How to use](#how-to-use)
		* [Creating a server](#creating-a-server)
		* [Creating a client](#creating-a-client)
		* [The PacketElement class](#the-packetelement-class)
		* [The PacketHandler class](#the-packethandler-class)
+ [License](#license)

## Getting Started
+ [How to install](#how-to-install)
+ [How to use](#how-to-use)


## How to install
+ [Build the library](#build-the-library)
+ [Use the library](#use-the-library)
+ [Import the project into Eclipse](#import-the-project-into-eclipse)


### Build the library

First, you need to download or clone this repository. To build the library you need to open a terminal in your local version of the repository and type in the following command. <br />
`gradlew build` <br />
**OR** If you have a local installation of gradle: `gradle build` <br />
This will compile the library, the javadoc and the source attachments. 

---

### Use the library

After you [built the library]() there should be a new folder called **'build'** in the root folder of the repository. The JARs are inside of a folder called **'libs'** (**\\..\\multithreaded-server\\build\\libs\\**). <br />

#### Import the library in Eclipse
Make a right click on your project and navigate to **'Properties'**. Select **'Java Build Path'** and go to the **'Libraries'** tab. Press the **'Add External JARs...'** button and select the library JAR. <br />
Now the library should show up in the list. If you want to attach the sources and the javadoc, you can do this by folding up the library in the list.

---

### Import the project into Eclipse

This repository is set up to be easily importable into Eclipse. You can import it in Eclipse as a gradle project. In Eclipse you need to navigate to: **'File > Import... > Gradle > Existing Gradle Project'** <br />
Click on the **'Browse...'** button next to the **'Project root directory'**. Next, you need to select the root folder of your local version of this repository. Click on **'Finish'** to finish the import.



## How to use
+ [Creating a server](#creating-a-server)
+ [Creating a client](#creating-a-client)
+ [The PacketElement class](#the-packetelement-class)
+ [The PacketHandler class](#the-packethandler-class)

### Creating a server

First, you need to create a class which derives from the **BasicServer** class. You can achieve this by adding `extends BasicServer` as shown in the example below.

```java
public class ExampleServer extends BasicServer {
}
```

Next, you have to add the unimplemented methods inherited by the **BasicServer** class.

```java
public class ExampleServer extends BasicServer {
	
	@Override
	public void clientConnected(int clientID) {
	}

	@Override
	public void messageFromClient(int clientID, PacketElement[] elements) {
	}

}
```

+ **clientConnected** <br />
  This method gets called every time a client connects to the server. It also  provides the corresponding `clientID` as a parameter.

+ **messageFromClient** <br />
  This method gets called each time the server receives new data from one of the clients. Again the `clientID` is provided as a parameter. The received data (message) can be accessed through the `elements` array which consists of [PacketElements](#the-packetelement-class).

**Some useful methods:**

```java
this.startServer(int port);
this.stopServer();
this.sendToClient(int clientID, PacketElement[] elements);
this.sendToAllClients(PacketElement[] elements);
this.removeClient(int clientID);
this.setMaxClients(int maxClients);
```

Here is a simple example of a server that first pings the data a client sent to it back and then disconnects it.

```java
public class ExampleServer extends BasicServer {
	
	public ExampleServer() {
		this.startServer(7070); //Starting the server on port 7070
	}
    
	@Override
	public void clientConnected(int clientID) {
		System.out.println("New client!"); //Printing some text when a new client connects to the server
	}

	@Override
	public void messageFromClient(int clientID, PacketElement[] elements) {
		this.sendToClient(clientID, elements); //Sending the data back to the client
		this.removeClient(clientID); //Disconnecting the client from the server
	}

}
```

More examples can be found here. **(COMING SOON)**

---
### Creating a client

First, you need to create a class which derives from the **BasicClient** class. You can achieve this by adding `extends BasicClient` as shown in the example below.

```java
public class ExampleClient extends BasicClient {
}
```

Next, you have to add the unimplemented methods inherited by the **BasicClient** class.

```java
public class ExampleClient extends BasicClient {
	
	@Override
	public void messageFromServer(PacketElement[] elements) {
	}
    
	@Override
	public void disconnectedFromServer() {
	}

	@Override
	public void unableToConnect() {
	}
    
}
```

+ **messageFromServer** <br />
  This method gets called each time the client receives new data from server. The received data (message) can be accessed through the `elements` array which consists of [PacketElements](#the-packetelement-class).

+ **disconnectedFromServer** <br />
  This method gets called when the client gets disconnected from the server.
  
+ **unableToConnect** <br />
  This method gets called when the client is unable to connect to the server.

**Some useful methods:**

```java
this.connectToServer(int port, String ip);
this.stopClient();
this.sendToServer(PacketElement[] elements);
```

Here is a simple example of a client that always sends the first element from all received data back to the server.

```java
public class ExampleClient extends BasicClient {
	
	public ExampleClient() {
		this.connectToServer(7070, "localhost"); //Connecting to the server at localhost on port 7070
	}
    
	@Override
	public void messageFromServer(PacketElement[] elements) {
		sendToServer(new PacketElement[] { elements[0] }); //Sending the first element of the elements array back to the server
	}
    
	@Override
	public void disconnectedFromServer() {
	}

	@Override
	public void unableToConnect() {
	}
    
}
```

More examples can be found here. **(COMING SOON)**

----

### The PacketElement class

Packet elements are objects containing binary data and other information. These objects can be packed with a [PacketHandler](#the-packethandler-class) and afterwards sent from a client to a server and vice versa. The library does most of the [PacketHandler](#the-packethandler-class) jobs for you so you don't need to manually pack all your packet elements.

```java
PacketElement packetElement = new PacketElement(); //Initializing the packet element
String text = "This is a string of text.";

packetElement.setData(text.getBytes()); //Giving the string in bytes to the packet element
packetElement.setType(PacketElement.STRING_TEXT); //Setting the data type to STRING_TEXT
```

**At the moment there are six different data types:**
+ `SERVER_MESSAGE` String text that can be interpreted as a command
+ `STRING_TEXT` String text
+ `PNG_IMAGE` PNG Image
+ `JPG_IMAGE` JPG Image
+ `BMP_IMAGE` BMP Image
+ `OTHER` Everything else

---

### The PacketHandler class

The packet handler converts one or multiple packet elements to one byte array and vice versa.

```java
PacketHandler packetHandler = new PacketHandler(); //Initializing the packet handler
//Adding some packet elements to the packet handler
packetHandler.addElement(new PacketElement("Element 1".getBytes(), PacketElement.STRING_TEXT));
packetHandler.addElement(new PacketElement("Element 2".getBytes(), PacketElement.STRING_TEXT));

byte[] data = packetHandler.createPacket(); //Creating the packet

PacketElement[] elements = packetHandler.decodePacket(data); //Decoding the packet
```

## License
This project is licensed under the terms of the MIT license. <br />
For further information please read the [LICENSE](/LICENSE) file.
