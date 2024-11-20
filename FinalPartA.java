/*
 * Use The hashing function Quadratic Probing
 * Read a file
 * Apply the hash function to every word (convert the words to integer values)
 * Store a count of the number of times each position is hashed to
 * Store a count of the number of probes
 * Track the time taken for one search operation
 * Test with a prime number table size
 * Test with a Non-Prime number table size
 * Test with a maximum load factor of 0.5
 * Test with a maximum load factor of 0.7
 */

package Final;
import java.io.*;
import java.util.*;

public class FinalPartA 
{
    //This is a method of which will read the file from the users computer
    public static void readFile(String fname, String arr[]) throws FileNotFoundException
    {
        File file = new File(fname);
        if (!file.exists())
        {
            System.out.println("No such file.");
            return;
        }
        Scanner inFile = new Scanner(file); 
        
        int index=0; //Initialize the index to start at index 0
        
            while(inFile.hasNextLine()) //While the file has a next line
        {
            String word= inFile.nextLine().trim();
        if(word.length()> 0 ) //If the word has a length greater than 0
        {
            arr[index]=word; //Hold the word at this position in the array
            index++; //Increment the index by one
        }
        }
        inFile.close(); //close the file
        System.out.println("The number of words that are in the file are: "+ index); //Print the line
    }

    //This is a method of which will convert the strings values to integer values using their ASCII values
    public static int stringToInteger(String str)
   {
       int sum=0; //Initialize the sum as zero
       for(char c: str.toCharArray()) 
       {
           sum += (int) c;
       }
       return sum; //Sum is the sum of all of the ASCII characters of the string
   }

   //This is a method of which will store the array of strings, as now an array of integers 
   public static int[] convertToIntegerArray(String[] arr)
   {
        int intArray[]= new int[arr.length]; //Create an integer array of same length as the string array

        //Loop through the string array, and convert each word to an integer
        for(int i=0;i<arr.length;i++)
        {
            if(arr[i]!=null) 
            {
                intArray[i]=stringToInteger(arr[i]); //Convert the string to integer and store it
            }
        }
        return intArray; //return the integer array. This holds all of the values of the words as integer values
   }

   //This is a method for Hashing using Quadratic Probing
   public static void hashing 
   (int hash_table[], int hash_table_size, int integer_array[], int integer_array_size, int countOfPositionsHashedTo[], int numberOfProbes[], double maxLoadFactor)
   {
    int maximumElements= (int) (hash_table_size*maxLoadFactor); //The max elements that can be inserted based upon load factor

    for (int i = 0; i < integer_array_size && i<maximumElements; i++) {
 
        // Computing the hash value using hash function (HF= 2*(x mod hash_table_size))
        int hv = 2*(integer_array[i] % hash_table_size);
        countOfPositionsHashedTo[hv]++; //Increment the count of the position hashed to


        // Insert the data in the table if there is no collision
        if (hash_table[hv] == -1) //If it is equal to -1, then there is no data stored at that particular index. If it does not =-1, then there is a collision
        {
            hash_table[hv] = integer_array[i]; //Hash value is used as an index
            numberOfProbes[i] = 1; //At the position (index) i, there was only 1 probe done
        } 
        else //There is a collision at the index. Track the number of probes done, as well as the # of positions hashed to
        {
            int probes=1;
            boolean inserted= false;

            // If there is a collision iterating through all possible quadratic values
            for (int j = 0; j < hash_table_size; j++) 
            {
                // Computing the new hash value 
                int t = (hv + j * j) % hash_table_size; //t is the new hash value
                countOfPositionsHashedTo[t]++;

                //If the slot is empty, insert the value
                if (hash_table[t] == -1) 
                {
                    hash_table[t] = integer_array[i]; 
                    numberOfProbes[i]=probes+1; //Store the total number of probes for this word (including this one)
                    inserted=true;
                    break; //Break the for loop (come out of the loop)
                }
                probes++; //Increment the probe count
            }
        }
    }
   }
   
   //This is a method that is used for searching
   public static void search(int hash_table[], int key)
   {
        int hv= 2*(key % hash_table.length);
        int probes = 1;
        
        if(hash_table[hv] == key)
        {
            System.out.println("Key was found with 1 probe at postion: "+hv);
        }
        else
        {
            for(int j=0; j<hash_table.length; j++)
            {
                int t= (hv+j*j)% hash_table.length;
                if(hash_table[t]== key)
                {
                    System.out.println("The key was found with "+(probes+1)+" probes at position: "+t);
                    break;
                }
                probes++;
            }
        }

   }



   public static void main(String[] args) throws FileNotFoundException
   {

        String arrayOfStrings[]= new String[466551]; //An array that will hold all of the words read from the file
        int N=466551; //This is the number of words that are in the file being read

        //Read the file
        readFile("C:/Users/jptas/OneDrive/Desktop/words.txt", arrayOfStrings);

        //Convert the array of strings to an array of integers
        int integer_array[]= convertToIntegerArray(arrayOfStrings);

        //The size of the hash table is L
        //We will change this to a prime number, and non-prime number as needed throughout our testing scenarios
        int L=933102;
        int hash_table[]= new int[L];

        //Initialize the hash table with a default value of -1
        for(int i=0; i<L; i++)
        {
            hash_table[i]=-1;
        }

        int C= 933102; //The table size of the array containing the count of positions hashed to is the same size as the hash table
        //This array will store the # of times each position (index) is hashed to in the hash_table
        int countOfPositionsHashedTo[] = new int[C];

        //Initialize the count table with a default value of 0
        for(int i=0; i<C; i++)
        {
            countOfPositionsHashedTo[i]=0;
        }

        //This is an array that will store the number of probes that happen at each position
        int P= 933102;
        int numberOfProbes[] = new int[P];

        //Initialize the probe array with default values of 0
        for(int i=0; i<P; i++)
        {
            numberOfProbes[i]=0;
        }

        //This is the maximum Load Factor. Change as needed during testing
        double maxLoadFactor=0.5;

        //Complete the hashing
       hashing(hash_table, L, integer_array, N, countOfPositionsHashedTo, numberOfProbes, maxLoadFactor);

       //Begin the search process 
       long startSearch1Word = System.nanoTime(); //This is the inital time before the search process

       //You can insert however many searches you want here, just copy and paste this line and change the stringToInteger string value 
       search(hash_table, stringToInteger("paracephalus"));

       long endSearch1Word = System.nanoTime(); //This is the time after the search process
       System.out.println("The time it took to search for 1 word was: "+(endSearch1Word-startSearch1Word)+ " Nano Seconds"); //The search time is the end - start times

       //Search for 10 words
       long startSearch10Word= System.nanoTime();
       search(hash_table, stringToInteger("zoanthropy"));
       search(hash_table, stringToInteger("Achaeus"));
       search(hash_table, stringToInteger("geomedicine"));
       search(hash_table, stringToInteger("unresolved"));
       search(hash_table, stringToInteger("lease-purchase"));
       search(hash_table, stringToInteger("lateralize"));
       search(hash_table, stringToInteger("pachymenia"));
       search(hash_table, stringToInteger("confusive"));
       search(hash_table, stringToInteger("dislimax"));
       search(hash_table, stringToInteger("Menobranchus"));
       long endSearch10Word = System.nanoTime();
       System.out.println("The time it took to search for 10 words was: "+(endSearch10Word-startSearch10Word)+ " Nano Seconds");

       //Search for 20 words
       long startSearch20Word = System.nanoTime();
       search(hash_table, stringToInteger("codefendant"));
       search(hash_table, stringToInteger("nonsubstantive"));
       search(hash_table, stringToInteger("cricotracheotomy"));
       search(hash_table, stringToInteger("cryoscopy"));
       search(hash_table, stringToInteger("automatization"));
       search(hash_table, stringToInteger("gynecopathy"));
       search(hash_table, stringToInteger("generousness"));
       search(hash_table, stringToInteger("basileus"));
       search(hash_table, stringToInteger("chaises"));
       search(hash_table, stringToInteger("Chaetosomidae"));
       search(hash_table, stringToInteger("pressurizers"));
       search(hash_table, stringToInteger("fyttes"));
       search(hash_table, stringToInteger("cut-under"));
       search(hash_table, stringToInteger("Sherm"));
       search(hash_table, stringToInteger("tetrarch"));
       search(hash_table, stringToInteger("Oryctolagus"));
       search(hash_table, stringToInteger("celibate"));
       search(hash_table, stringToInteger("yackety-yakked"));
       search(hash_table, stringToInteger("benzo"));
       search(hash_table, stringToInteger("airmailed"));
       long endSearch20Word = System.nanoTime();
       System.out.println("The time it took to search for 20 words was: "+(endSearch20Word-startSearch20Word)+ " Nano Seconds");

       //Search for 30 words
       long startSearch30Word = System.nanoTime();
       search(hash_table, stringToInteger("spadmen"));
       search(hash_table, stringToInteger("plonk"));
       search(hash_table, stringToInteger("sulfantimonide"));
       search(hash_table, stringToInteger("preservations"));
       search(hash_table, stringToInteger("trickers"));
       search(hash_table, stringToInteger("unmenial"));
       search(hash_table, stringToInteger("overpiteus"));
       search(hash_table, stringToInteger("footstalk"));
       search(hash_table, stringToInteger("unencored"));
       search(hash_table, stringToInteger("mezquites"));
       search(hash_table, stringToInteger("odiumproof"));
       search(hash_table, stringToInteger("bounty-fed"));
       search(hash_table, stringToInteger("masculonucleus"));
       search(hash_table, stringToInteger("disguise"));
       search(hash_table, stringToInteger("Proto-mycenean"));
       search(hash_table, stringToInteger("epicyclic"));
       search(hash_table, stringToInteger("latke"));
       search(hash_table, stringToInteger("sweepforward"));
       search(hash_table, stringToInteger("unedacious"));
       search(hash_table, stringToInteger("hylogenesis"));
       search(hash_table, stringToInteger("unamenableness"));
       search(hash_table, stringToInteger("scramblingly"));
       search(hash_table, stringToInteger("newts"));
       search(hash_table, stringToInteger("fatbirds"));
       search(hash_table, stringToInteger("pentacarbon"));
       search(hash_table, stringToInteger("contrapposto"));
       search(hash_table, stringToInteger("infrastapedial"));
       search(hash_table, stringToInteger("outyelled"));
       search(hash_table, stringToInteger("hemicardiac"));
       search(hash_table, stringToInteger("devotionate"));
       long endSearch30Word = System.nanoTime();
       System.out.println("The time it took to search for 30 words was: "+(endSearch30Word-startSearch30Word)+ " Nano Seconds");

       //Search for 40 words
       long startSearch40Word = System.nanoTime();
       search(hash_table, stringToInteger("Steff"));
       search(hash_table, stringToInteger("Burkburnett"));
       search(hash_table, stringToInteger("hypotrophies"));
       search(hash_table, stringToInteger("liparid"));
       search(hash_table, stringToInteger("physiocratism"));
       search(hash_table, stringToInteger("Cambeva"));
       search(hash_table, stringToInteger("Meares"));
       search(hash_table, stringToInteger("sloe-eyed"));
       search(hash_table, stringToInteger("customable"));
       search(hash_table, stringToInteger("whale-tailed"));
       search(hash_table, stringToInteger("woodlands"));
       search(hash_table, stringToInteger("mymecology"));
       search(hash_table, stringToInteger("protalbumose"));
       search(hash_table, stringToInteger("cathodograph"));
       search(hash_table, stringToInteger("Laaland"));
       search(hash_table, stringToInteger("dun-olive"));
       search(hash_table, stringToInteger("Cellvibrio"));
       search(hash_table, stringToInteger("succubine"));
       search(hash_table, stringToInteger("nonmythically"));
       search(hash_table, stringToInteger("duologs"));
       search(hash_table, stringToInteger("twitchfire"));
       search(hash_table, stringToInteger("ramequin"));
       search(hash_table, stringToInteger("Zahl"));
       search(hash_table, stringToInteger("mightn't"));
       search(hash_table, stringToInteger("cosignatories"));
       search(hash_table, stringToInteger("corynebacterial"));
       search(hash_table, stringToInteger("Cathlamet"));
       search(hash_table, stringToInteger("Dartford"));
       search(hash_table, stringToInteger("democratizes"));
       search(hash_table, stringToInteger("heterotrich"));
       search(hash_table, stringToInteger("well-saved"));
       search(hash_table, stringToInteger("jabules"));
       search(hash_table, stringToInteger("discophoran"));
       search(hash_table, stringToInteger("all-beautiful"));
       search(hash_table, stringToInteger("skoot"));
       search(hash_table, stringToInteger("leverage"));
       search(hash_table, stringToInteger("Dodonean"));
       search(hash_table, stringToInteger("discincrustant"));
       search(hash_table, stringToInteger("Belinda"));
       search(hash_table, stringToInteger("electrotechnics"));
       long endSearch40Word = System.nanoTime();
    System.out.println("The time it took to search for 40 words was: "+(endSearch40Word-startSearch40Word)+ " Nano Seconds");

       //Search for 50 words
       long startSearch50Word = System.nanoTime();
       search(hash_table, stringToInteger("Steff"));
       search(hash_table, stringToInteger("Burkburnett"));
       search(hash_table, stringToInteger("hypotrophies"));
       search(hash_table, stringToInteger("liparid"));
       search(hash_table, stringToInteger("physiocratism"));
       search(hash_table, stringToInteger("Cambeva"));
       search(hash_table, stringToInteger("Meares"));
       search(hash_table, stringToInteger("sloe-eyed"));
       search(hash_table, stringToInteger("customable"));
       search(hash_table, stringToInteger("whale-tailed"));
       search(hash_table, stringToInteger("woodlands"));
       search(hash_table, stringToInteger("mymecology"));
       search(hash_table, stringToInteger("protalbumose"));
       search(hash_table, stringToInteger("cathodograph"));
       search(hash_table, stringToInteger("Laaland"));
       search(hash_table, stringToInteger("dun-olive"));
       search(hash_table, stringToInteger("Cellvibrio"));
       search(hash_table, stringToInteger("succubine"));
       search(hash_table, stringToInteger("nonmythically"));
       search(hash_table, stringToInteger("duologs"));
       search(hash_table, stringToInteger("twitchfire"));
       search(hash_table, stringToInteger("ramequin"));
       search(hash_table, stringToInteger("Zahl"));
       search(hash_table, stringToInteger("mightn't"));
       search(hash_table, stringToInteger("cosignatories"));
       search(hash_table, stringToInteger("corynebacterial"));
       search(hash_table, stringToInteger("Cathlamet"));
       search(hash_table, stringToInteger("Dartford"));
       search(hash_table, stringToInteger("democratizes"));
       search(hash_table, stringToInteger("heterotrich"));
       search(hash_table, stringToInteger("well-saved"));
       search(hash_table, stringToInteger("jabules"));
       search(hash_table, stringToInteger("discophoran"));
       search(hash_table, stringToInteger("all-beautiful"));
       search(hash_table, stringToInteger("skoot"));
       search(hash_table, stringToInteger("leverage"));
       search(hash_table, stringToInteger("Dodonean"));
       search(hash_table, stringToInteger("discincrustant"));
       search(hash_table, stringToInteger("Belinda"));
       search(hash_table, stringToInteger("electrotechnics"));
       search(hash_table, stringToInteger("zoanthropy"));
       search(hash_table, stringToInteger("Achaeus"));
       search(hash_table, stringToInteger("geomedicine"));
       search(hash_table, stringToInteger("unresolved"));
       search(hash_table, stringToInteger("lease-purchase"));
       search(hash_table, stringToInteger("lateralize"));
       search(hash_table, stringToInteger("pachymenia"));
       search(hash_table, stringToInteger("confusive"));
       search(hash_table, stringToInteger("dislimax"));
       search(hash_table, stringToInteger("Menobranchus"));
       long endSearch50Word = System.nanoTime();
       System.out.println("The time it took to search for 50 words was: "+(endSearch50Word-startSearch50Word)+ " Nano Seconds");

       
   }
}
