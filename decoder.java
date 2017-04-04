import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Shaan on 004, 4 Apr.
 */
public class decoder {

    Node dec_root;
    PrintWriter DW = null;
    String encoded_path, code_table_path;
    int max_len;

    public decoder(String inp1, String inp2)
    {
        this.encoded_path = inp1;
        this.code_table_path = inp2;
    }

    public void make_dec_tree(Node n, int val, String code)
    {
        if (code.length() == 0)
        {
            n.f = 0;
            n.v = val;
        }
        else if (code.charAt(0) == '0')
        {
            if (n.l1 == null)
            {
                Node temp = new Node();
                n.l1 = temp;
            }
            make_dec_tree(n.l1, val, code.substring(1));
        }
        else
        {
            if (n.r1 == null)
            {
                Node temp = new Node();
                n.r1 = temp;
            }
            make_dec_tree(n.r1, val, code.substring(1));
        }
    }

    public String dec_parse(Node n, String code)
    {
        if (n.f == 0)
        {
            //System.out.println(n.v);
            DW.print(n.v + "\n");
            return code;
        }
        else if (code.charAt(0) == '0')
        {
            code = dec_parse(n.l1, code.substring(1));
        }
        else
            code = dec_parse(n.r1, code.substring(1));
        return code;
    }

    public void dec_start_decoding() throws IOException
    {
        FileInputStream fis = null;


        try
        {
            byte[] data = new byte[1];
            String bin = "";
            String temp;
            fis = new FileInputStream(encoded_path);
            DW = new PrintWriter("decoded.txt");
            int k = 0;
            //fis.read(data);
            //for(byte b:data) {
            while(fis.read(data) != -1)
            {
                int c = (int)data[0];
                if (c < 0)
                    c = 256 + c;
                temp = Integer.toBinaryString(c);
                while (temp.length() != 8)
                    temp = "0" + temp;
                bin += temp;
                if (bin.length() >= max_len * 2) {
                    bin = dec_parse(dec_root, bin);
                }
            }
            while (bin.length() != 0)
                bin = dec_parse(dec_root, bin);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            fis.close();
            DW.close();
        }
    }

    public void dec_code_table()
    {
        Scanner scan = null;
        try
        {
            dec_root = new Node();
            scan = new Scanner(new BufferedInputStream(new FileInputStream(code_table_path)));
            int firstI = scan.nextInt();
            String firstS = scan.next();
            max_len = firstS.length();
            make_dec_tree(dec_root, firstI, firstS);

            while(scan.hasNext()) {
                int temp1 = scan.nextInt();
                String temp2 = scan.next();
                if (temp2.length() > max_len)
                    max_len = temp2.length();
                make_dec_tree(dec_root, temp1, temp2);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            scan.close();
        }
    }

    public static void main(String[] args) {

        try {
            decoder dec;
            if (args.length == 2) {
                dec = new decoder(args[0], args[1]);
                dec.dec_code_table();
                dec.dec_start_decoding();
            }
            else
                System.out.println("Please enter the filenames!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
