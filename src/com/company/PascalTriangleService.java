package com.company;

import java.util.Arrays;

public class PascalTriangleService {

    public int[] getPascalRow(int n)
    {
        int[] table = new int[n];
        Arrays.fill(table,1);

        for(int i=1; i<n;i++)

        {
            int C=1;
            for(int j=1;j<=i;j++)
            {
                C=C*(i-j)/j;
                table[j]=C;
            }
        }

        return table;
    }
}
