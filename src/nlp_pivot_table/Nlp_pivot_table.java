/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp_pivot_table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.in;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author idrees
 */
public class Nlp_pivot_table {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("======>We have only 6 documents");
        System.out.println("Enter no of documents");
        int num_of_doc = in.nextInt();
        ArrayList<String> word_list[] = new ArrayList[num_of_doc];
        ArrayList<word_with_count> unique_word_list[] = new ArrayList[num_of_doc];

        for (int i = 0; i < num_of_doc; i++) {
            word_list[i] = new ArrayList<>();
            unique_word_list[i] = new ArrayList<>();
        }
        for (int i = 0; i < num_of_doc; i++) {
            File file = new File("doc" + (i + 1) + ".txt");
            wordBreak(file, word_list[i]);
            unique_word_list(unique_word_list[i], word_list[i]);
        }
        ArrayList<pivot_table_row> rows = new ArrayList<>();
        get_rows(rows, unique_word_list);
        // System.out.println("rows" + rows.size());
        pivot_table table = new pivot_table(rows);
        table.sort_by_weight();
        table.write_into_file();
        in.close();

        // TODO code application logic here
    }

    private static void wordBreak(File file, ArrayList word_list) throws FileNotFoundException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(file.getName())));
        String temp = "";
        for (int j = 0; j < content.length(); j++) {
            if (content.charAt(j) == '.' || content.charAt(j) == ',' || content.charAt(j) == 32 || content.charAt(j) == '('
                    || content.charAt(j) == '-' || content.charAt(j) == ')'|| content.charAt(j) == '\'' || content.charAt(j) == ':' || content.charAt(j) == ';' || content.charAt(j) == '"') {
                if (!"".equals(temp) && !" ".equals(temp) && temp.length() > 0) {
                    //System.out.println(temp);
                    if (temp.length() > 0 && !"".equals(temp) && !" ".equals(temp) && !"s".equals(temp)&&!"\n".equals(temp)) {
                        word_list.add(temp);
                        System.out.println(temp);
                    }
                    word_list.add(temp.toLowerCase());
                    // System.out.println(temp);
                } else {
                    //System.out.println("waste"+temp);
                }

                temp = "";

            } else {
                temp = temp + content.charAt(j);
            }
        }
        //System.out.println(temp.length());
        if (temp.length() > 0) {
            word_list.add(temp);
        }
        //  temp = "";

    }

    private static void unique_word_list(ArrayList<word_with_count> unique_word_list, ArrayList<String> word_list) {
        for (String word_list1 : word_list) {
            int index = find(word_list1, unique_word_list);
            if (index != -1) {
                unique_word_list.get(index).count++;
            } else {
                unique_word_list.add(new word_with_count(word_list1));
            }
        }

    }

    private static int find(String word, ArrayList<word_with_count> word_list) {
        for (int i = 0; i < word_list.size(); i++) {
            if (word_list.get(i).word == null ? word == null : word_list.get(i).word.equals(word)
                    || word_list.get(i).word == word + "" || word_list.get(i).word == word + " ") {
                return i;
            }
        }
        return -1;
    }

    private static void get_rows(ArrayList<pivot_table_row> rows, ArrayList<word_with_count>[] unique_word_list) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        pivot_table_row row;
        for (int i = 0; i < unique_word_list.length; i++) {
            for (int j = 0; j < unique_word_list[i].size(); j++) {
                String word = unique_word_list[i].get(j).word;
                if (!IsExist(word, rows)) {
                    row = new pivot_table_row(word, unique_word_list.length);
                    for (int k = i; k < unique_word_list.length; k++) {
                        row.term_frequency[k] = get_term_freq(word, unique_word_list[k]);
                        if (row.term_frequency[k] > 0) {
                            row.doc_freq++;
                            row.total_count += row.term_frequency[k];
                        }

                    }
                    // row.weight=double(row.total_count)/row.doc_freq;
                    if (row.total_count != 0) {
                        row.weight = ((double) row.total_count) / row.doc_freq;
                    } else {
                        row.weight = 0;
                    }
                    rows.add(row);
                    //row = null;
                }

            }
        }

    }

    private static int get_term_freq(String word, ArrayList<word_with_count> unique_word_list) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for (word_with_count unique_word_list1 : unique_word_list) {
            if (unique_word_list1.word == null ? word == null : unique_word_list1.word.equals(word)) {
                return unique_word_list1.count;
            }
        }
        return 0;
    }

    private static boolean IsExist(String word, ArrayList<pivot_table_row> rows) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for (pivot_table_row row : rows) {
            if (row.word == null ? word == null : row.word.equals(word)) {
                return true;
            }
        }
        return false;
    }

}
