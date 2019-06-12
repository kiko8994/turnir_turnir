package com.example.turnirmk;

import java.util.Comparator;

abstract class Sortbyroll implements Comparator<Strijelac>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Strijelac a, Strijelac b)
    {
        return a.getBrojGolova() - b.getBrojGolova();
    }
}
