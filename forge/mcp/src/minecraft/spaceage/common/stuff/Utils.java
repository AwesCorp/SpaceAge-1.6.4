package spaceage.common.stuff;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class Utils
{
    public static String BinaryToAscii(String binary)
    {
        // Convert binary string into ASCII.
        String output = "";
        for (int i = 0; i <= binary.length() - 8; i += 8)
        {
            int k = Integer.parseInt(binary.substring(i, i + 8), 2);
            output += (char) k;
        }
        return output.toLowerCase().trim();
    }

    public static String AsciiToBinary(String input)
    {
        // Converts ASCII string to binary.
        byte[] bytes = input.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    @SuppressWarnings("unused")
    public static String getWrittenBookContents(NBTTagCompound nbt)
    {
        if (nbt == null)
        {
            return null;
        }
        else if (!nbt.hasKey("pages"))
        {
            return null;
        }
        else
        {
            NBTTagList nbttaglist = (NBTTagList) nbt.getTag("pages");

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagString nbttagstring = (NBTTagString) nbttaglist.tagAt(i);

                if (nbttagstring.data == null)
                {
                    return null;
                }

                if (nbttagstring.data.length() > 256)
                {
                    return null;
                }

                return nbttagstring.data.toLowerCase().trim();
            }

            return null;
        }
    }

    public static String getMD5String(String unencodedText)
    {
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        md.update(unencodedText.getBytes());

        byte byteData[] = md.digest();

        return getHexString(byteData);
    }

    public static String getSHA1String(String unencodedText)
    {
        // Creates SHA1 String of given text for comparison purposes over network.
        MessageDigest md = null;

        try
        {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        byte[] unencodedBytes = null;
        try
        {
            unencodedBytes = unencodedText.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return getHexString(unencodedBytes);
    }

    private static String getHexString(byte[] raw)
    {
        String HEXES = "0123456789ABCDEF";

        // Converts a given UTF-8 byte array into properly encoded SHA1 equivalent.
        if (raw == null)
        {
            return null;
        }

        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw)
        {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }

        return hex.toString();
    }

    public static List<String> splitStringPerWord(String string, int wordsPerLine)
    {
        String[] words = string.split(" ");
        List<String> lines = new ArrayList<String>();

        for (int lineCount = 0; lineCount < Math.ceil((float) words.length / (float) wordsPerLine); lineCount++)
        {
            String stringInLine = "";

            for (int i = lineCount * wordsPerLine; i < Math.min(wordsPerLine + lineCount * wordsPerLine, words.length); i++)
            {
                stringInLine += words[i] + " ";
            }

            lines.add(stringInLine.trim());
        }

        return lines;
    }

    public static List<Integer> splitIntegerPerDigit(int i)
    {
        // Splits an integer into corresponding array of numbers.
        // http://stackoverflow.com/questions/5196186/split-int-value-into-seperate-digits
        List<Integer> digits = new ArrayList<Integer>();
        while (i > 0)
        {
            digits.add(i % 10);
            i /= 10;
        }
        return digits;
    }
}