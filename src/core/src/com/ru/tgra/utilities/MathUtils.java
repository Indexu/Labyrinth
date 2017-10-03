package com.ru.tgra.utilities;

public class MathUtils
{
    public static float clamp(float number, float min, float max)
    {
        if (number < min)
        {
            return min;
        }

        if (max < number)
        {
            return max;
        }

        return number;
    }

    public static boolean isBetween(float number, float lower, float higher)
    {
        return (lower <= number && number <= higher);
    }
}
