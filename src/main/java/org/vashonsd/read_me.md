
### A Heads Up!

This is a project that I'm doing to help learn more about Machine Learning and computer science in general.
I'd prefer not to use external libraries for this so that I can learn more complicated stuff that I might otherwise miss.
This also means that I'll probably ask a lot of questions to about any suggested fixes, as I would like to learn what the
specific problem was, and how I can fix it if I ever run into it again.

### The Roadmap:

* Create an extremely basic genetic algorithm with a fixed topology to play TicTacToe
* Add more types of FitnessTests to test the algorithm
* At some point, I'm hoping to turn this into a Topology and Weight Evolving Artificial Neural Network (TWEANN)
so if the structure looks a bit unorthodox for a fixed topology one, now you know why. 
The long term plan is to create my own implementation of a NEAT Algorithm.
* At this point, the Machine learning aspect will probably be finished, from here on I might add visuals, so you can see how each network looks.
I'll probably also do some other testing and create more fitness tests. Like for instance, making a visual enviroment for the network to interact with.

### Where it's at right now

Still at step one, it can play TicTacToe, but it doesn't ever seem to learn anything after hundreds of Generations.

### Structure

There are two packages for now, a Machine learning one and a Fitness Test one. Everything that the algorithm uses
and requires in order to learn is found in the MachineLearning package. The FitnessTest package contains the interfaces
that fitness tests must implement in order to be used by a neural network as well as the actual fitness tests that I've created so far.
Outside those, there is the main, which should just run some other bit of code that determines what tests the algorithm should run.
For example, all main does right now is start the playTicTacToe class which then creates an algorithm and teaches it to play TicTacToe.
But I could also switch the main to start SolveGrandmas, and it would run a different fitness test.

### How are the Neural Networks structured

In short, probably extremely poorly. Each neural network has a couple Arraylists of Nodes to represent each layer in the Network.
There are three different types of nodes:

* Simple Nodes (These are used for input nodes, they get a value and they hold it)
* Bias Nodes (These always have a value of 1.0)
* Connected Nodes (These are hidden and output nodes)

The Connected nodes have a HashMap with other nodes as keys and Double to represent the weight of the connection as a value.
These connected nodes add up all the values of the previous layer (Each value multiplied by the weight associated with the node it's coming from)
and then runs the sum through a sigmoid function to get a value between 0.0 - 1.0. The network then finds the output node with the highest value
and suggests it as the next move in the fitness test.