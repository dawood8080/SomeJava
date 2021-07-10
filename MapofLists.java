
/*

CSV to map of lists
Map of lists to CSV or whatever

This code for importing a CSV file to a map of lists ( Map<String, ArrayList<String>> ), and then return it to put it in a file.
I used this code to call and modify columns easily by their header names.
I will try to make the code clear for you:
1- I created a list of strings that holds headers' names to make it easier to reach.
2- I wanted to make a code that reads every line and then appends each data to their won headers.

ReadCSV method: opens the file and moves line by line - I made a variable called rowNum to tell the next method that this is not
the header - and of course, I split the cells with "," and put them in an array. And finally, giving the next method (MakingData)
 the array of strings( a row in CSV file ) and rowNum.


Making Data: taking the row content as an array and the row number, if the row number is 0, then we will go through the array, and make
a new map for each of them as you see the line "". And add them to the main List of strings which contains headers names ( I called
it Headers ).
And if the row number not 0, then it will add every string in the array to his header.
In other words, when the row number is 0, we will add the keys of the map, otherwise, we will push the row which contains the data to
their headers,  each string to his header. The same order of the headers, the same order of row data, that what makes me creating
"Headers" lists.

creatAndRearrange: the method taking the data to make them ready by separate them with "," in the file in the given destination. First
it writes the headers' names and then writes the data or cells after, separated with their true positions.


Notice: I used exceptions to avoid errors.

special thanks to Zaid Aljundi
 */





import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapofLists {

    ArrayList<String> Headers = new ArrayList<>();
    Map<String, ArrayList<String>> Data = new HashMap<>();


    public void makingData(String[] s,int rowNo){
        if(rowNo == 0) { // Checking if the row is the row of the headers
            for (String value : s) {
                Data.put(value, new ArrayList<>()); // Making ever header as a new key for the map
                Headers.add(value); // Adding the header to the main variable of headers
            }
        }
        else{
            for(int cell=0;cell<s.length;cell++)
              //MainData.get(string of header of index number "cell") . adding the string
                Data.get(Headers.get(cell)).add(s[cell]); //
        }
    }


    public void ReadCsv(Path csvPath) throws IOException {
        try(BufferedReader BR = new BufferedReader(new FileReader(String.valueOf(csvPath)))){ //opening csv file and read it
            String line ;
            int rowNum = 0;
            while((line = BR.readLine())!=null)//iterate the rows of the file
            {
                String[] arr = line.split(",");
                makingData(arr,rowNum);// sending each row to "makingData" method with rowNum

                rowNum++;
            }
        }
        catch(FileNotFoundException ex){
            throw new IllegalArgumentException("file not found");
        }
    }

    public void creatAndRearrange(Path dest) {
        try(FileWriter writer= new FileWriter(String.valueOf(dest), true)){// open the file to write in

            //First, writing the headers because it's the first row, seperated by (,)
            for (int head = 0 ; head < Headers.size() ; head++) {
                writer.write(Headers.get(head));
                if(head != Headers.size()-1)// this to not adding (,) to the last header
                    writer.write(",");
            }

            // This outer loop is moving on the rows, the inner loop is to move on the value of each map key
            for(int row = 0 ; row < Data.get(Headers.get(0)).size() ; row++)
            {
                writer.write("\n");
                for(int col = 0 ; col < Headers.size() ; col++) {
                    //write (   Data.get(string of the key, the desired column or header) . get ( row )
                    writer.write(Data.get(Headers.get(col)).get(row));
                    if(col != Headers.size()-1)
                        writer.write(",");
                }
            }

        } catch(FileNotFoundException e){
            throw new IllegalArgumentException("the destination file does not exists");
        } catch (IOException e) {
            throw new IllegalArgumentException("the destination is not a file");
        }

    }

    public void main(String[] args) throws IOException{
        Path path = Paths.get("C:xxx//xxxx....");
        Path destPath = Paths.get("C:shimatta//aregato...");
        ReadCsv(path);
        creatAndRearrange(destPath);
    }


}
