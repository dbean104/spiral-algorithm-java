# spiral-algorithm-java

### Background
The spiral algorithm is a method for determining the number of isomers of [fullerenes](https://en.wikipedia.org/wiki/Fullerene) with a given number of carbon atoms (the nuclearity)

A full explanation of the spiral algorithm, how it works, and its limitations, is given in the book *An Atlas of Fullerenes* by P. W. Fowler and D. E. Manolopoulos.

The book is available to buy [here](https://store.doverpublications.com/0486453626.html).

The authors include a FORTRAN implementation of the spiral algorithm in the appendices of the book. The initial purpose of this coding project was to re-implement the algorithm in Java.

### Project Structure

The code ported from Fortran is located in the *com.dbean104.spiral.atlas* package. This code has been re-factored to (hopefully) make it clearer, reduce duplication and make it easier to test. I have also used recursion to iterate through the candidate fullerene spirals, whereas the original FORTRAN code uses 12 nested do-loops.

Other packages include interfaces, utility classes and functionality for formatting the output.

The program can be run using the **RunSpiralAlgorithm** class, which contains the main method. Alternatively, one can look at the test classes.

### Further work

The next steps will be to attempt to re-write the algorithm from scratch. The purposes of doing this would be to:
- Write the code in a more 'object-orientated way' to remove the procedural nature of the FORTRAN version
- Look for performance optimisations
- Make the process multi-threaded
