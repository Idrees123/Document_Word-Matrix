/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp_pivot_table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author idrees
 */
public class pivot_table {
  

    pivot_table_row[] table;
    int total=0;
    

    public pivot_table(ArrayList<pivot_table_row> rows) {
        this.table = new pivot_table_row[rows.size()];
        for (int i = 0; i < rows.size(); i++) {
            table[i] = rows.get(i);
        }
    }

    public void write_into_file() throws FileNotFoundException {
        File output = new File("pivot_table"+Math.random()+".csv");
        java.io.PrintWriter print = new PrintWriter(output);
        print.print("Word,");
        for (int i = 0; i < table[0].term_frequency.length; i++) {
            print.print("Doc" + (i + 1) + "count,");
        }
        print.print("total count,");
        print.print("Doc_Freq,");
        print.print("Weight");
        print.println();
        for (int j = 0; j < table.length; j++) {
            print.print(table[j].word+",");
            for (int i = 0; i < table[i].term_frequency.length; i++) {
                print.print(table[j].term_frequency[i]+",");
            }
            print.print(table[j].total_count+",");
            total+=table[j].total_count;
            print.print(table[j].doc_freq+",");
            print.print(table[j].weight);
            print.println();
          
        }
          System.out.println("total"+total);
          System.out.println("Output File: "+output.getName());

        print.close();
        

    }
    public void sort_by_weight(){
        for(int i=0;i<table.length;i++){
            for(int j=i+1;j<table.length;j++){
                if(table[i].weight<table[j].weight){
                   pivot_table_row temp=table[i];
                   table[i]=table[j];
                   table[j]=temp;
                }
            }
        }
    }
}
