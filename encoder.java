import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.*;

/**
 * Created by Shaan on 003, 3 Apr.
 */
public class encoder {
    public HashMap<Integer, Integer> freq_table;
    public HashMap<String, String> code_table;
    public ArrayList<Node> Tree;
    public dHeap heap;
    Node root;
    PrintWriter PW = null;
    String input_path;


    public encoder()
    {
        this.input_path = "sample_input_small.txt";
    }

    public encoder(String inp1)
    {
        this.input_path = inp1;
    }

    public void make_freq_table()
    {
        try{
            freq_table = new HashMap<Integer, Integer>();
            code_table = new HashMap<>();
            heap = new dHeap();

            Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(input_path)));

            int data, temp;

            while(scan.hasNext()){
                int temp1 = scan.nextInt();
                if(freq_table.containsKey(temp1))
                {
                    freq_table.put(temp1, freq_table.get(temp1) + 1);
                }
                else
                    freq_table.put(temp1, 1);
            }

        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
            System.exit(0);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void write_code_table(Node node, String path)
    {
        if(node != null) {
            if (node.v != -1) {
                PW.print(node.v + " " + path + "\n");
                code_table.put(Integer.toString(node.v), path);
            }
            else {
                if (node.l1 != null)
                    write_code_table(node.l1, path + "0");
                if (node.r1 != null)
                    write_code_table(node.r1, path + "1");
            }
        }

    }

    public void code_table_init()
    {
        //PrintWriter PW = null;
        try {
            PW = new PrintWriter("code_table.txt");
            write_code_table(root, "");
            //Debug

            //System.out.println("Code table done");
            //for (String  value: code_table.keySet())
            //    PW.print(value + " " + code_table.get(value)+ "\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (PW != null)
                PW.close();
        }
    }

    public void build_tree() {
        try {
            //Tree = new ArrayList<>();
            //Iterator it =
            for (int value : freq_table.keySet()) {
                {
                    Node n1 = new Node();
                    n1.v = value;
                    n1.f = freq_table.get(value);
                    heap.insert(n1);
                }
            }
            freq_table.clear();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void build_huffman()
    {
        int size = heap.curr_size - 3;
        //for (int i = 0; i< size; i++)

        while (heap.curr_size >= 4)
        {
            Node n1 = heap.getMin();
            Node n2 = heap.getMin();
            Node n = new Node();
            n.f = n1.f + n2.f;
            n.l1 = n1;
            n.r1 = n2;
            heap.insert(n);
        }
        root = heap.getMin();

    }
    public void begin_encoding() throws IOException
    {
        FileOutputStream fos = null;
        try{

            Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(input_path)));

            fos = new FileOutputStream("encoded.bin");

            int data, temp;
            String buff = "";

            while(scan.hasNext()){
                int temp1 = scan.nextInt();
                buff += code_table.get(Integer.toString(temp1));
                while (buff.length() >= 8)
                {
                    byte next = 0x00;
                    String to_write = buff.substring(0, 8);
                    //int num = Integer.parseInt(to_write, 2);
                    for (int i = 0; i<8 ; i++) {
                        next = (byte) (next << 1);
                        next += to_write.charAt(i) == '0' ? 0x0 : 0x1;
                    }
                    //String numS = Integer.toString(num);
                    //int hex = Integer.parseInt(numS, 16);
                    fos.write(next);


                    //Integer.parseInt(to_write)
                    //String hex = toHex(to_write);

                    buff = buff.substring(8);
                }

            }

            while (buff.length() >= 8) {
                String to_write = buff.substring(0, 8);
                int num = Integer.parseInt(to_write, 2);
                //String numS = Integer.toString(num);
                //int hex = Integer.parseInt(numS, 16);
                fos.write(num);


                //Integer.parseInt(to_write)
                //String hex = toHex(to_write);

                buff = buff.substring(8);
            }

        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
            System.exit(0);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            fos.close();
        }
    }


    public static void main(String[] args) {

        try {
            long startTime = System.currentTimeMillis();
            encoder enc;
            if (args.length == 1) {
                enc = new encoder(args[0]);
                dHeap heap = new dHeap();
                enc.make_freq_table();
                enc.build_tree();
                enc.build_huffman();
                enc.code_table_init();
                enc.begin_encoding();
                long endTimeA   = System.currentTimeMillis();
                //System.out.println("Time taken : " + (endTimeA - startTime)/1000);
            }
            else
                System.out.println("Please enter filename!");

        }
        catch (Exception e)
        {

        }
    }
}
