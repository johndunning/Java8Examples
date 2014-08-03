/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package java8examples;

import java.util.stream.LongStream;

/**
 *
 * @author johndunning
 */
public class Java8Examples
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // Compute PI using the Taylor Series
        // pi = 4 * (1 - 1/3 + 1/5 - 1/7 + ...)
        
        System.out.println(
                LongStream.range(0L, 1_000_000_000L)
                .parallel()
                .map( i -> ((i&1)==0?1:-1) * (2*i+1))
                .mapToDouble(i -> 4.0 / i)
                .sum());
    }
    
}
