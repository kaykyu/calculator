# calculator
Reverse Polish notation (RPN) calculator server that is multi-threaded.
MTServer starts the server to connect to clients from Client.
Client is console-based and numbers and operators can be passed to Server for calculation in RPN.
Example: 
Client > 5 20 / ----> returns [4.00] from Server.
Client > 6 + ----> returns [10.00] from Server.
Client > 15 3 6 ----> returns [10.00, 15.00, 3.00, 6.00] from Server, where no operator was passed.
Client > * ----> returns [10.00, 15.00, 18.00] from Server.
