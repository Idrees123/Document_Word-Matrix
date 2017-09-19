/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp_pivot_table;

/**
 *
 * @author idrees
 */
public class pivot_table_row {

    String word;
    int[] term_frequency;
    int doc_freq;
    int total_count;
    double weight;

    public pivot_table_row(String word, int num_of_docs) {
        this.word = word;
        term_frequency = new int[num_of_docs];
        total_count = 0;
        doc_freq = 0;
    }

}
