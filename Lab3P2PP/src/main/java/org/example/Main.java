package org.example;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import java.util.*;

public class Main
{

    public static void main(String[] args)
    {

        String python_code =
                        "import re\n" +
                        "def procText():\n" +
                        "   with open('in.txt', 'r') as file:\n" +
                        "       text = file.read()\n" +
                        "   text = re.sub(r' +', \" \", text)\n" +
                        "   text = re.sub(r'\\n+' , \"\\n\" , text)\n" +
                        "   text = re.sub(r'\\n\\s*\\d+\\s*\\n' , '\\n',text)\n" +
                        "   return text\n" +
                        "print(procText())";

        try (Context polyglot = Context.newBuilder().allowAllAccess(true).build())
        {
            Value rez = polyglot.eval("python", python_code);
            System.out.println(rez.asString());
        }
    }
}
